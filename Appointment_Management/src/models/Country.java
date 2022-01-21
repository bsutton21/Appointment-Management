/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package models;

import java.time.LocalDateTime;

/**
 * This is used to build, hold, and return Country objects.
 * @author Blake Sutton, Student ID: #001109490.
 */

public class Country {
    
    private int countryId;
    private String country;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastUpdated;
    private String lastUpdatedBy;
    private int divisionId;
    private String division;
    
    /**
     * Constructor.
     * @param countryId
     * @param country
     * @param createdDate
     * @param createdBy
     * @param lastUpdated
     * @param lastUpdatedBy 
     */
    public Country(int countryId, String country, LocalDateTime createdDate, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createdDate  = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Empty constructor.
     */
    public Country() {
    }
    
    // SETTERS
    /**
     * Set country id.
     * @param countryId 
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    
    /**
     * Set country.
     * @param country 
     */
    public void setCountry(String country) {
        this.country = country;
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
    
    // GETTERS
    /**
     * Get country id.
     * @return 
     */
    public int getCountryId() {
        return countryId;
    }
    
    /**
     * Get country.
     * @return 
     */
    public String getCountry() {
        return country;
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
     * Get last update by.
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
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
     * Used to populate Country combo boxes with string values of the country field.
     * @return country
     */
    public String toString() {
        return country;
    }
    
}
