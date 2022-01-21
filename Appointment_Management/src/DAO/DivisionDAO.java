/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Division;
import utils.DBConnection;

/**
 * Holds division related SQL.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class DivisionDAO {
        
    /**
     * Returns all divisions.
     * @return allDivisions
     */
    public ObservableList<Division> getAllDivisions() {
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        String queryString = "SELECT * FROM first_level_divisions;";
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement ps = conn.prepareStatement(queryString);){
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Division allDivision = new Division();
                allDivision.setDivision(rs.getString("Division"));
                allDivision.setCountryId(rs.getInt("COUNTRY_ID"));
                allDivisions.add(allDivision);
            }
        } 
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allDivisions;
    }
        
    /**
     * Returns divisions by country.
     * @param country
     * @return allDivisions
     */
    public ObservableList<Division> getDivisionsByCountry(String country) {
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        String queryString = "SELECT d.Division FROM first_level_divisions d, countries c "
                        + "WHERE d.COUNTRY_ID = c.Country_ID "
                        + "AND c.Country = '" + country + "';";
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement ps = conn.prepareStatement(queryString);){
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Division tempDiv = new Division();
                tempDiv.setDivision(rs.getString(1));
                allDivisions.add(tempDiv);
            }
        } 
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allDivisions;
    }

    /**
     * Returns division id by division.
     * @param division
     * @return tempDiv
     */
    public int getDivisionIdByDivision(String division) {
        String queryString = "SELECT Division_ID FROM first_level_divisions WHERE Division = '" + division + "';";
        Connection conn = DBConnection.startConnection();
        int tempDiv = 0;
        try(PreparedStatement ps = conn.prepareStatement(queryString)) {
            // ps.setString(1, division);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                tempDiv = rs.getInt("Division_ID");
            }
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return tempDiv;
    }
}
