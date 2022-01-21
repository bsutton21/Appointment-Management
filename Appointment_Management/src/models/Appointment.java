/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.beans.value.ObservableValue;

/**
 * This is used to build, hold, and return Appointment objects.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class Appointment {
    
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private String createdBy;
    private Timestamp createdDate;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;
    private String contact;
    private String startString;
    private String endString;
    
    /**
     * Constructor.
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param lastUpdated
     * @param lastUpdatedBy
     * @param customerId
     * @param userId
     * @param contactId
     * @param contact 
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Timestamp lastUpdated, String lastUpdatedBy, int customerId, int userId, int contactId, String contact) {
        this.id = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contact = contact;
    }

    /**
     * Empty constructor.
     */
    public Appointment() {
    }
    
    // SETTERS
    
    /**
     * Appointment ID setter.
     * @param appointmentId 
     */
    public void setId(int appointmentId) {
        this.id = appointmentId;
    }
    
    /**
     * Title setter.
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Description setter.
     * @param description 
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Location setter.
     * @param location 
     */
    public void setLocation (String location) {
        this.location = location;
    }
    
    /**
     * Type setter.
     * @param type 
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * Start setter.
     * @param start 
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    
    /**
     * End setter.
     * @param end 
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    
    /**
     * Created by setter.
     * @param createdBy 
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    /**
     * Created date setter.
     * @param createdDate 
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
    
    /**
     * Updated by setter.
     * @param lastUpdated 
     */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    /**
     * Last updated setter.
     * @param lastUpdatedBy 
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    /**
     * Customer ID setter.
     * @param customerId 
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    /**
     * User ID setter.
     * @param userId 
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    /**
     * Contact ID setter.
     * @param contactId 
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    
    /**
     * Start string setter.
     * @param startString 
     */
    public void setStartString(String startString) {
        this.startString = startString;
    }
    
    /**
     * End string setter.
     * @param endString 
     */
    public void setEndString(String endString) {
        this.endString = endString;
    }
    
    /**
     * Contact setter.
     * @param contact 
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    // GETTERS
    
    /**
     * Id getter.
     * @return id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Title getter.
     * @return title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Description getter.
     * @return description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Location getter
     * @return location
     */
    public String getLocation() {
        return location;
    }
    
    /**
     * Type getter.
     * @return type
     */
    public String getType() {
        return type;
    }
    
    /**
     * Start getter.
     * @return start
     */
    public LocalDateTime getStart() {
        return start;
    }
    
    /**
     * End getter.
     * @return end
     */
    public LocalDateTime getEnd() {
        return end;
    }
    
    /**
     * Created by getter.
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }
    
    /**
     * Created date getter.
     * @return createdDate
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }
    
    /**
     * Last updated getter.
     * @return lastUpdated
     */
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }
    
    /**
     * Last updated by getter.
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
    /**
     * Customer ID getter.
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }
    
    /**
     * User ID getter.
     * @return userId
     */
    public int getUserId() {
        return userId;
    }
    
    /**
     * Contact ID getter.
     * @return contactId
     */
    public int getContactId() {
        return contactId;
    }
    
    /**
     * Start string getter.
     * @return startString
     */
    public String getStartString() {
        return startString;
    }
    
    
    /**
     * End String getter.
     * @return endString
     */
    public String getEndString() {
        return endString;
    }
    
    /**
     * Contact getter.
     * @return contact
     */
    public String getContact() {
        return contact;
    }
    
    /**
     * Used to populate Appointment combo boxes with string values of the type field.
     * @return type
     */
    public String toString() {
        return type;
    }
}
