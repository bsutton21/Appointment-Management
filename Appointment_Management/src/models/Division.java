/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package models;

import java.time.LocalDateTime;

/**
 * This is used to build, hold, and return Division objects.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class Division {
    
    private int divisionId;
    private String division;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastUpdated;
    private String lastUpdatedBy;
    private int countryId;
    
    /**
     * Constructor.
     * @param divisionId
     * @param division
     * @param createdDate
     * @param createdBy
     * @param lastUpdated
     * @param lastUpdatedBy 
     */
    public Division(int divisionId, String division, LocalDateTime createdDate, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy) {
        this.divisionId = divisionId;
        this.division = division;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Empty constructor.
     */
    public Division() {
    }
    
    // SETTERS
    
    /**
     * Set division id.
     * @param divisionId 
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
    
    /**
     * Set division.
     * @param division 
     */
    public void setDivision(String division) {
        this.division = division;
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
     * Set last updated by.
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
    
    /**
     * Set country id.
     * @param countryId 
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    
    // GETTERS
    
    /**
     * Get division id.
     * @return divisionId
     */
    public int getDivisionId() {
        return divisionId;
    }
    
    /**
     * Get division.
     * @return division
     */
    public String getDivision() {
        return division;
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
    
    /**
     * Get country id.
     * @return countryId
     */
    public int getCountryId() {
        return countryId;
    }
    
    /**
     * Used to populate Division combo boxes with string values of the division field.
     * @return division
     */
    public String toString() {
        return division;
    }
    
}
