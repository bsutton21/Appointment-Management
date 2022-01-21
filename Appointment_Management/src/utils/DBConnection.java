/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class manages the db connection.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class DBConnection {
    // Create Fields
    private static String currentUser;
    private static PreparedStatement prepStatement;
    private static PreparedStatement createStatement;
    
    // URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String URL = "//wgudb.ucertify.com/WJ06Jc7";
    
    // JDBC URL
    private static final String jpbcURL = protocol + vendorName + URL;
    
    // Driver and connection interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;
    
    private static final String username = "U06Jc7"; // username
    private static String password = "53688779719"; // password
    
    /**
     * Initiates the db connection.
     * @return 
     */
    public static Connection startConnection() 
    {
        try
        {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jpbcURL, username, password);
            System.out.println("Connection successful!");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        
        return conn;
    }
    
    /**
     * Closes the db connection.
     */
    public static void closeConnection()
    {
        try
        {
            conn.close();
            System.out.println("Connection closed!"); 
        }
        catch(SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    /**
     * Prepares sql statement.
     * @param query
     * @return prepStatement
     */
    public static PreparedStatement prepareStatement(String query) {
        startConnection();
        try {
            prepStatement = conn.prepareStatement(query);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return prepStatement;
    }
    
    /**
     * Creates statement.
     * @return 
     */
    public static PreparedStatement createStatement() {
        startConnection();
        try {
            createStatement = (PreparedStatement) conn.createStatement();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return createStatement;
    }

    /**
     * Validates user credentials on login attempt.
     * @param username
     * @param password
     * @return boolean
     * @throws SQLException 
     */
    public static boolean validateUser(String username, String password) throws SQLException{
        String queryString = "SELECT * FROM users WHERE User_Name = ?";
        Connection conn = DBConnection.startConnection();
        PreparedStatement ps = conn.prepareStatement(queryString);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()) {
            return false;
        }
        if (password.matches(rs.getString("Password"))) {
            return true;
        }
        return false;
    }

    /**
     * Returns user id from username.
     * @param username
     * @return userId
     */
    private static int getUserId(String username) {
        int userId = -1;
        String query = "SELECT User_ID FROM users WHERE User_Name = ? ";
        prepareStatement(query);
        
        try {
            prepStatement.setString(1, username);         
            // Get the userId from the username from the db
            ResultSet userIdSet = prepStatement.executeQuery();
            
            // Set the userId to the value from tthe result set
            if (userIdSet.next()) {
                userId = userIdSet.getInt("User_ID");
            }
            userIdSet.close();
            prepStatement.close();
            conn.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userId;
    }

    /**
     * Checks password.
     * @param userId
     * @param password
     * @return boolean
     */
    private static boolean checkPassword(int userId, String password) {
        String passwordDB = null;
        String query = "SELECT Password FROM users WHERE User_ID = ? ";
        
        prepareStatement(query);
        try {
            prepStatement.setInt(1, userId);
            
            // Get the password for the user based on user ID from the db
            ResultSet passwordSet = prepStatement.executeQuery();
            if (passwordSet.next()) {
                passwordDB = passwordSet.getString("Password");
            }
            else {
                return false;
            }
            passwordSet.close();
            prepStatement.close();
            conn.close();
            
            if (passwordDB.equals(password)) {
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
}
