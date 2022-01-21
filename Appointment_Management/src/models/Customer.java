/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package models;

/**
 * This is used to build, hold, and return Customer objects.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class Customer {
    
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;
    private String division;
    private String country;
    private int countryId;
    private int lastUpdatedBy;
    private String createdBy;
    private String lastUpdated;
    
    // DECLARE CONSTRUCTOR

    /**
     * Constructor.
     * @param id
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionId 
     */
    public Customer(int id, String name, String address, String postalCode, String phone,  int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /**
     * Empty constructor.
     */
    public Customer() {
    }
    
    // SETTERS
    
    /**
     * Set id.
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Set name.
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Set address.
     * @param address 
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * Set postal code.
     * @param postalCode 
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    /**
     * Set phone.
     * @param phone 
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
     * Set last updated by.
     * @param lastUpdatedBy 
     */
    public void setLastUpdatedBy(int lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    /**
     * Set last updated.
     * @param lastUpdated 
     */
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
   
   // GETTERS
    
    /**
     * Get id.
     * @return id 
     */
    public int getId() {
        return id;
    }
    
    /**
     * Get name.
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get address.
     * @return address
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * Get postal code.
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }
    
    /**
     * Get phone.
     * @return phone
     */
    public String getPhone() {
        return phone;
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
     * Get country id.
     * @return countryId
     */
    public int getCountryId() {
        return countryId;
    }
    
    /**
     * Get country.
     * @return country
     */
    public String getCountry() {
        return country;
    }
    
    /**
     * Get last updated by.
     * @return lastUpdatedBy
     */
    public int getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
    /**
     * Get last updated.
     * @return lastUpdated
     */
    public String getLastUpdated() {
        return lastUpdated;
    }
    
    /**
     * Get created by.
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }
    
    /**
     * Used to populate Customer combo boxes with string values of the name field.
     * @return name
     */
    public String toString() {
        return name;
    }
}
