/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package DAO;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import models.Customer;
import utils.DBConnection;

/**
 * Holds customer related SQL.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class CustomerDAO implements Initializable {
    
    protected final static String LAST_VAL ="SELECT last_value FROM ";
    protected final static String CUSTOMER_SEQUENCE = ""; //TO DO
    
    Connection connection = DBConnection.startConnection();
    
    protected int getLastVal(String sequence) {
        int key = 0;
        String sql = LAST_VAL + sequence;
        Connection connection = DBConnection.startConnection();
        try(Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                key = rs.getInt(1);
            }
            return key;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static final String INSERT = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
    private static final String DELETE = "DELETE FROM customers WHERE Customer_ID = ?";
    
    /**
     * Empty constructor.
     */
    public CustomerDAO() {
    }

    /**
     * Returns all customers.
     * @return customers
     */
    public ObservableList<Customer> findAll() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM customers";
        
        try(PreparedStatement statement = this.connection.prepareStatement(query);){
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Customer customer = new Customer();
                CountryDAO countryDAO = new CountryDAO();
                customer.setId(rs.getInt("Customer_ID"));
                customer.setName(rs.getString("Customer_Name"));
                customer.setAddress(rs.getString("Address"));
                customer.setPostalCode(rs.getString("Postal_Code"));
                customer.setPhone(rs.getString("Phone"));
                customer.setCountry(countryDAO.getCountry(rs.getInt("Division_ID")));
                customer.setDivision(getDivision(rs.getInt("Division_ID")));
                customers.add(customer);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customers;   
    }

    /**
     * Updates existing customer in the db.
     * @param customer 
     */
    public void update(Customer customer) {
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE);) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPostalCode());
            statement.setString(4, customer.getPhone());
            statement.setInt(5, customer.getDivisionId());
            statement.setInt(6, customer.getId());
            statement.execute();
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserts new customer data into the db.
     * @param customer
     */
    public void create(Customer customer) {
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT);) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPostalCode());
            statement.setString(4, customer.getPhone());
            statement.setInt(5, customer.getDivisionId());
            statement.execute();
            // int id = this.getLastVal(CUSTOMER_SEQUENCE);
            // return (Customer) this.findById(id);
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete customer from db.
     * @param id 
     */
    public void delete(int id) {
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE);) {
            statement.setInt(1, id);
            statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Returns division from division id.
     * @param divisionId
     * @return division
     * @throws SQLException 
     */
    public String getDivision(int divisionId) throws SQLException {
        String queryString = "SELECT Division FROM first_level_divisions WHERE Division_Id = ?";
        Connection conn = DBConnection.startConnection();
        String division = null;
        try(PreparedStatement ps = conn.prepareStatement(queryString)) {
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                division = rs.getString("Division");
            }
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return division;
    }

    /**
     * Initializes the class.
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}