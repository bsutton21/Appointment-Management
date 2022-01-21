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
import utils.DBConnection;

/**
 * Holds user related SQL.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class UserDAO {
    
    /**
     * Returns userid from username.
     * @param username
     * @return user_Id
     */
    public static int getCurrentUserId(String username) {
        String queryString = "SELECT User_ID FROM users WHERE user_name = ?";
        Connection conn = DBConnection.startConnection();
        int user_Id = -1;
        try(PreparedStatement ps = conn.prepareStatement(queryString);) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                user_Id = rs.getInt("User_ID");
            }
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return user_Id;
    }
}

