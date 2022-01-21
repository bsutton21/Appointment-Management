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
import models.Contact;
import utils.DBConnection;

/**
 * Holds contact related SQL.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class ContactDAO {
    
    /**
     * Returns all contacts.
     * @return allContacts
     */
    public ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String queryString = "SELECT * FROM contacts;";
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement ps = conn.prepareStatement(queryString);){
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Contact allCont = new Contact();
                allCont.setContactName(rs.getString("Contact_Name"));
                allCont.setContactId(rs.getInt("Contact_ID"));
                allContacts.add(allCont);
            }
        } 
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allContacts;
    }
    
    /**
     * Returns contact id by contact.
     * @param contact
     * @return contId
     */
    public int getcontactIdBycontact(String contact) {
        String queryString = "SELECT Contact_ID FROM contacts WHERE Contact_Name = '" + contact + "';";
        Connection conn = DBConnection.startConnection();
        int contId = 0;
        try(PreparedStatement ps = conn.prepareStatement(queryString)) {
            // ps.setString(1, division);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                contId = rs.getInt("Contact_ID");
            }
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return contId;
    }
}
