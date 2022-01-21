/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package DAO;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import models.Appointment;
import models.Contact;
import models.Customer;
import utils.DBConnection;

/**
 * Holds appointment related SQL.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class AppointmentDAO implements Initializable {
    
    
    private static final String INSERT = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ONE = "SELECT Apointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM Appointments WHERE Appoint_ID = ?";
    private static final String UPDATE = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
    private static final String DELETE = "DELETE FROM appointments WHERE Appointment_ID = ?";
    private static final String DELETE_BY_CUST = "DELETE FROM appointments WHERE Customer_ID = ?";

    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * Returns the current date time formatted to yyyy-MM-dd HH:mm:ss.
     * @return 
     */
    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }
    
    /**
     * Empty constructor.
     */
    public AppointmentDAO() {
    }
    
    /**
     * Returns all appointments associated with a given appointment ID in an observable list.
     * @param id
     * @return 
     */
    public ObservableList<Appointment> findById(int id) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        
        Connection connection = DBConnection.startConnection();
        try(PreparedStatement statement = connection.prepareStatement(GET_ONE);) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {                
                int appId = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDesc = rs.getString("Description");
                String appLoc = rs.getString("Location");
                String appType = rs.getString("Type");
                LocalDateTime appStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEnd = rs.getTimestamp("End").toLocalDateTime();
                Timestamp appLastUpd = rs.getTimestamp("Last_Update");
                String appLastUpdBy = rs.getString("Last_Updated_By");
                int appCustId = rs.getInt("Customer_ID");
                int appUserId = rs.getInt("User_ID");
                int appContactId = rs.getInt("Contact_ID");
                String appContact = getContact(rs.getInt("Contact_ID"));
                
                Appointment appointment = new Appointment(appId, appTitle, appDesc, appLoc, appType, appStart, appEnd, appLastUpd, appLastUpdBy, appCustId, appUserId, appContactId, appContact);
                
                appointments.add(appointment);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return appointments;
    }

    /**
     * Returns all appointments in an observable list.
     * @return 
     */
    public ObservableList<Appointment> findAll() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM appointments ap "
                + "INNER JOIN customers cu ON cu.Customer_ID = ap.Customer_ID " 
                + "INNER JOIN users u ON u.User_ID = ap.User_ID";
        Connection connection = DBConnection.startConnection();        
        try(PreparedStatement statement = connection.prepareStatement(query);){
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                int appId = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDesc = rs.getString("Description");
                String appLoc = rs.getString("Location");
                String appType = rs.getString("Type");
                LocalDateTime appStartDb = rs.getTimestamp("Start").toLocalDateTime();
                ZonedDateTime startLocZdt = ZonedDateTime.of(appStartDb, ZoneId.systemDefault());
                LocalDateTime appStart = startLocZdt.toLocalDateTime();
                LocalDateTime appEndDb = rs.getTimestamp("End").toLocalDateTime();
                ZonedDateTime endLocZdt = ZonedDateTime.of(appEndDb, ZoneId.systemDefault());
                LocalDateTime appEnd = endLocZdt.toLocalDateTime();
                Timestamp appLastUpd = rs.getTimestamp("Last_Update");
                String appLastUpdBy = rs.getString("Last_Updated_By");
                int appCustId = rs.getInt("Customer_ID");
                int appUserId = rs.getInt("User_ID");
                int appContactId = rs.getInt("Contact_ID");
                String appContact = getContact(rs.getInt("Contact_ID"));
                
                Appointment appointment = new Appointment(appId, appTitle, appDesc, appLoc, appType, appStart, appEnd, appLastUpd, appLastUpdBy, appCustId, appUserId, appContactId, appContact);
                
                appointments.add(appointment);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return appointments;
    }

    /**
     * Updates appointment in db.
     * @param appointment 
     */
    public void update(Appointment appointment) {
        Connection connection = DBConnection.startConnection();
        try (PreparedStatement ps = connection.prepareStatement(UPDATE);) {
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
            ps.setInt(7, appointment.getCustomerId());
            ps.setInt(8, appointment.getUserId());
            ps.setInt(9, appointment.getContactId());
            ps.setInt(10, appointment.getId());
            ps.execute();
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserts appointment into db.
     * @param appointment 
     */
    public void create(Appointment appointment) {
        Connection connection = DBConnection.startConnection();
        try(PreparedStatement ps = connection.prepareStatement(INSERT);) {
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
            ps.setInt(7, appointment.getCustomerId());
            ps.setInt(8, appointment.getUserId());
            ps.setInt(9, appointment.getContactId());
            ps.execute();
            //int id = getLastVal(CUSTOMER_SEQUENCE);
            //return (Appointment) this.findById(id);
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes appointment from db.
     * @param id 
     */
    public void delete(int id) {
        Connection connection = DBConnection.startConnection();
        try(PreparedStatement statement = connection.prepareStatement(DELETE);) {
            statement.setInt(1, id);
            statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Deletes appointment by customer id.
     * Used when deleting customers to ensure that no foreign key issues.
     * @param custId 
     */
    public void deleteByCustId(int custId) {
        Connection conn = DBConnection.startConnection();
        try(PreparedStatement ps = conn.prepareStatement(DELETE_BY_CUST);) {
            ps.setInt(1, custId);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Filters appointments by month for the radio buttons on Main.
     * @return monthly appointments
     * @throws SQLException 
     */
    public ObservableList<Appointment> getMonthlyAppts() throws SQLException {
        ObservableList<Appointment> getMonthAppts = FXCollections.observableArrayList();
        String queryString = "SELECT * FROM appointments WHERE start BETWEEN NOW() AND ADDDATE(CURRENT_DATE,INTERVAL 1 MONTH)";
        Connection conn = DBConnection.startConnection();
        PreparedStatement prepStatement = null;
        prepStatement = conn.prepareStatement(queryString);
        ResultSet rs = prepStatement.executeQuery();
        while(rs.next()) {
            Appointment monthAppts = new Appointment();
            monthAppts.setId(rs.getInt("Appointment_ID"));
            monthAppts.setTitle(rs.getString("Title"));
            monthAppts.setDescription(rs.getString("Description"));
            monthAppts.setLocation(rs.getString("Location"));
            monthAppts.setContactId(rs.getInt("Contact_ID"));
            monthAppts.setType(rs.getString("Type"));
            monthAppts.setCustomerId(rs.getInt("Customer_ID"));
            monthAppts.setStart(rs.getTimestamp("Start").toLocalDateTime());
            monthAppts.setEnd(rs.getTimestamp("Start").toLocalDateTime());
            monthAppts.setContact(getContact(rs.getInt("Contact_ID")));
            
            getMonthAppts.add(monthAppts);
        }
        return getMonthAppts;
    }

    /**
     * Filters appointments by week for the radio buttons on Main.
     * @return weekly appointments
     * @throws SQLException 
     */
    public ObservableList<Appointment> getWeeklyAppts() throws SQLException {
        ObservableList<Appointment> getWeekAppts = FXCollections.observableArrayList();
        String queryString = "SELECT * FROM appointments WHERE START BETWEEN NOW() AND ADDDATE(now(),INTERVAL 7 DAY);";
        Connection conn = DBConnection.startConnection();
        PreparedStatement prepStatement = null;
        prepStatement = conn.prepareStatement(queryString);
        ResultSet rs = prepStatement.executeQuery();
        while(rs.next()) {
            Appointment weekAppts = new Appointment();
            weekAppts.setId(rs.getInt("Appointment_ID"));
            weekAppts.setTitle(rs.getString("Title"));
            weekAppts.setDescription(rs.getString("Description"));
            weekAppts.setLocation(rs.getString("Location"));
            weekAppts.setContactId(rs.getInt("Contact_ID"));
            weekAppts.setType(rs.getString("Type"));
            weekAppts.setCustomerId(rs.getInt("Customer_ID"));
            weekAppts.setStart(rs.getTimestamp("Start").toLocalDateTime());
            weekAppts.setEnd(rs.getTimestamp("Start").toLocalDateTime());
            weekAppts.setContact(getContact(rs.getInt("Contact_ID")));
            
            getWeekAppts.add(weekAppts);
        }
        return getWeekAppts;
    }
    
    /**
     * Initializes the class.
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Returns contact from contact id.
     * @param contactId
     * @return contact
     */
    private String getContact(int contactId) {
        String queryString = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";
        Connection conn = DBConnection.startConnection();
        String contact = null;
        try(PreparedStatement ps = conn.prepareStatement(queryString)) {
            ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                contact = rs.getString("Contact_Name");
            }
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return contact;
    }
    
    /**
     * Returns appointment count.
     * @param timeStart
     * @param timeEnd
     * @return 
     */
    public int getAppointmentCount(String timeStart, String timeEnd) {
        String queryString = "SELECT count(*) FROM appointments WHERE (End BETWEEN ? AND ?);";
        Connection conn = DBConnection.startConnection();
        int count = 0;
        try(PreparedStatement ps = conn.prepareStatement(queryString)) {
            ps.setString(1, timeStart);
            ps.setString(2, timeEnd);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                count = rs.getInt(1);
            }
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }
    
    /**
     * Returns appointments that begin in the next 15 minutes.  Used by the login screen before transitioning to Main.
     * @param timeStart
     * @param timeEnd
     * @return 
     */
    public Appointment upcomingAppointments(String timeStart, String timeEnd) {
        Appointment upAppts = new Appointment();
        String queryString = "SELECT * FROM appointments WHERE (Start BETWEEN ? AND ?);";
        Connection conn = DBConnection.startConnection();
        try(PreparedStatement ps = conn.prepareStatement(queryString)) {
            ps.setString(1, timeStart);
            ps.setString(2, timeEnd);
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while(rs.next()) {
                upAppts.setId(rs.getInt("Appointment_ID"));
                LocalDateTime dbTime = rs.getTimestamp("Start").toLocalDateTime();
                ZoneId myZoneId = ZoneId.systemDefault();
                ZonedDateTime myZDT = ZonedDateTime.of(dbTime, myZoneId);
                LocalDateTime myLDT = myZDT.toLocalDateTime();
                upAppts.setStart(myLDT);
                i++;
            }
            if(i == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointments");
                alert.setHeaderText("No Upcoming Appointments");
                alert.setContentText("You have no appointments in the next 15 minutes.");
                Optional<ButtonType> alertType = alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointments");
                alert.setHeaderText("Upcoming Appointments");
                alert.setContentText("You have " + i + " upcoming appointment(s):\n " + "\n Appointment ID: " + upAppts.getId() + "\n" + "at " + upAppts.getStart().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
                Optional<ButtonType> alertType = alert.showAndWait();
            }
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return upAppts;
    }
    
    /**
     * Returns the count of appointments by month.  Used in the ReportAppointmentType screen.
     * @param month
     * @return count
     */
    public String getApptCountByMonth(String month) {
        String queryString = "SELECT COUNT(*) FROM appointments WHERE EXTRACT(MONTH FROM Start) = ?";
        Connection conn = DBConnection.startConnection();
        String count = null;
        try(PreparedStatement ps = conn.prepareStatement(queryString)) {
            if (month.equals("January")) {
                ps.setString(1, "1");
            }
            if (month.equals("February")) {
                ps.setString(1, "2");
            }
            if (month.equals("March")) {
                ps.setString(1, "3");
            }
            if (month.equals("April")) {
                ps.setString(1, "4");
            }
            if (month.equals("May")) {
                ps.setString(1, "5");
            }
            if (month.equals("June")) {
                ps.setString(1, "6");
            }
            if (month.equals("July")) {
                ps.setString(1, "7");
            }
            if (month.equals("August")) {
                ps.setString(1, "8");
            }
            if (month.equals("September")) {
                ps.setString(1, "9");
            }
            if (month.equals("October")) {
                ps.setString(1, "10");
            }
            if (month.equals("November")) {
                ps.setString(1, "11");
            }
            if (month.equals("December")) {
                ps.setString(1, "12");
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                count = rs.getString(1);
            }
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }
    
    /**
     * Returns the count of appointments by type.  Used in the ReportAppointmentType screen.
     * @param type
     * @return 
     */
    public String getApptCountByType(String type) {
        String queryString = "SELECT COUNT(*) FROM appointments WHERE Type = '" + type + "';";
        Connection conn = DBConnection.startConnection();
        String count = null;
        try(PreparedStatement ps = conn.prepareStatement(queryString)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                count = rs.getString(1);
            }
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        // return list of types for combobox
        return count;
    }

    /**
     * Returns distinct types from appointments. Used to populate the type combo box in the ReportAppointmenType screen.
     * @return appType
     */
    public ObservableList<Appointment> getType() {
        ObservableList<Appointment> apptType = FXCollections.observableArrayList();
        String queryString = "SELECT DISTINCT type FROM appointments";
        Connection conn = DBConnection.startConnection();
        try(PreparedStatement ps = conn.prepareStatement(queryString)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Appointment appt = new Appointment();
                appt.setType(rs.getString("Type"));
                apptType.add(appt);
            }
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        // return list of types for combobox
        return apptType;
    }
    
    /**
     * Returns appointments by contact.
     * @param contact
     * @return appointments
     */
    public ObservableList<Appointment> getApptsByContact(Contact contact) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String queryString = "SELECT a.* FROM contacts c "
                + "INNER JOIN appointments a ON a.Contact_ID = c.Contact_ID "
                + " WHERE a.Contact_ID = ?;";
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement ps = conn.prepareStatement(queryString);){
            ps.setInt(1, contact.getContactId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Appointment appts = new Appointment();
                appts.setId(rs.getInt("Appointment_ID"));
                appts.setTitle(rs.getString("Title"));
                appts.setDescription(rs.getString("Description"));
                appts.setLocation(rs.getString("Location"));
                appts.setContactId(rs.getInt("Contact_ID"));
                appts.setType(rs.getString("Type"));
                appts.setCustomerId(rs.getInt("Customer_ID"));
                appts.setStart(rs.getTimestamp("Start").toLocalDateTime());
                appts.setEnd(rs.getTimestamp("Start").toLocalDateTime());
                appts.setContact(getContact(rs.getInt("Contact_ID")));
                appointments.add(appts);
            }
        } 
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointments;
    }
    
    /**
     * Returns appointments by customer.
     * @param customer
     * @return appointments
     */
     public ObservableList<Appointment> getApptsByCustomer(Customer customer) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String queryString = "SELECT a.* FROM customers c "
                + "INNER JOIN appointments a ON a.Customer_ID = c.Customer_ID "
                + " WHERE a.Customer_ID = ?;";
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement ps = conn.prepareStatement(queryString);){
            ps.setInt(1, customer.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Appointment appts = new Appointment();
                appts.setId(rs.getInt("Appointment_ID"));
                appts.setTitle(rs.getString("Title"));
                appts.setDescription(rs.getString("Description"));
                appts.setLocation(rs.getString("Location"));
                appts.setContactId(rs.getInt("Contact_ID"));
                appts.setType(rs.getString("Type"));
                appts.setCustomerId(rs.getInt("Customer_ID"));
                appts.setStart(rs.getTimestamp("Start").toLocalDateTime());
                appts.setEnd(rs.getTimestamp("Start").toLocalDateTime());
                appts.setContact(getContact(rs.getInt("Contact_ID")));
                appointments.add(appts);
            }
        } 
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointments;
    }
}
