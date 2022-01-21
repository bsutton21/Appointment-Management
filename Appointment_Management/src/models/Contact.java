/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package models;

/**
 * This is used to build, hold, and return Contact objects.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class Contact {
    
    private int contactId;
    private String contactName;
    private String email;
    
    /**
     * Constructor.
     * @param contactId
     * @param contactName
     * @param email 
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Empty constructor.
     */
    public Contact() {
        // empty constructor
    }
    
    // SETTERS
    /**
     * Set contact id.
     * @param contactId 
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    
    /**
     * Set contact name.
     * @param contactName 
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    /**
     * Set email.
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    // GETTERS
    /**
     * Get contact id.
     * @return contactId
     */
    public int getContactId() {
        return contactId;
    }
    
    /**
     * Get contact name.
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }
    
    /**
     * Get email.
     * @return email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Used to populate Contact combo boxes with string values of the contactName field.
     * @return contact
     */
    public String toString() {
        return contactName;
    }
    
}
