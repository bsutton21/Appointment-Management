/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package models;

import java.time.LocalDateTime;

/**
 * This is used to build, hold, and return User objects.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class User {
    
    private int userId;
    private String username;
    private String password;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastUpdated;
    private String lastUpdatedBy;
    
    /**
     * Constructor.
     * @param userId
     * @param username
     * @param password
     * @param createdDate
     * @param createdBy
     * @param lastUpdated
     * @param lastUpdatedBy 
     */
    public User (int userId, String username, String password, LocalDateTime createdDate, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    /**
     * Empty constructor.
     */
    public User() {}
    
    // SETTERS
    
    /**
     * Set user id.
     * @param userId 
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    /**
     * Set username.
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Set password.
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Set created date.
     * @param createdDate 
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    /**
     * Set created by.
     * @param createdBy 
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    /**
     * Set last updated.
     * @param lastUpdated 
     */
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    /**
     * Set last updated by.
     * @param lastUpdatedBy 
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    // GETTERS
    
    /**
     * Get user id.
     * @return userId
     */
    public int getUserId() {
        return userId;
    }
    
    /**
     * Get username.
     * @return username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Get password.
     * @return password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Get created date.
     * @return createdDate
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    /**
     * Get created by.
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }
    
    /**
     * Get last updated.
     * @return lastUpdated
     */
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    
    /**
     * Get last updated by.
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
}
