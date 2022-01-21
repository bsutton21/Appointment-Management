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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import models.Country;
import utils.DBConnection;

/**
 * Holds country related SQL.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class CountryDAO implements Initializable {
    
    /**
     * Returns all countries.
     * @return allCountries
     */
    public ObservableList<Country> getAllCountries() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        String queryString = "SELECT * FROM countries";
        Connection conn = DBConnection.startConnection();
        PreparedStatement prepStatement = null;
        try {
            prepStatement = conn.prepareStatement(queryString);
            
            ResultSet rs = prepStatement.executeQuery();
            while(rs.next()) {
                Country country = new Country();
                country.setDivisionId(rs.getInt("Country_ID"));
                country.setCountry(rs.getString("Country"));
                allCountries.add(country);
            }
        } 
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCountries;
    }
    
    /**
     * Returns countries by division id.
     * @param divisionId
     * @return country
     * @throws SQLException 
     */
    public static String getCountry(int divisionId) throws SQLException {
        String queryString = "SELECT Country FROM countries WHERE Country_ID = (SELECT COUNTRY_ID FROM first_level_divisions WHERE Division_ID = ?)";
        Connection conn = DBConnection.startConnection();
        String country = null;
        try(PreparedStatement ps = conn.prepareStatement(queryString);) {
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                country = rs.getString("Country");
            }
        }
        return country;
    }
    
    /**
     * Empty constructor.
     */
    public CountryDAO() {}
    
    /**
     * Initializes the class.
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
