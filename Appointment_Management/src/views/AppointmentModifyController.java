/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package views;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import c195_project.MainController;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Appointment;
import models.Contact;

/**
 * FXML Controller class
 * This scene serves the purpose of modifying existing appointment data.
 * I implemented a lambda expression when the "Cancel" button is clicked to prompt with an alert and listen for a confirmation to improve readability.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class AppointmentModifyController implements Initializable {

    Stage stage;
    Parent scene;
    Appointment appointment;
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();
    
    @FXML
    private ComboBox<String> startHour;
    @FXML
    private ComboBox<String> startMinute;
    @FXML
    private ComboBox<String> endHour;
    @FXML
    private ComboBox<String> endMinute;
    @FXML
    private Label userIdLabel;
    @FXML
    private TextField appointmentIdText;
    @FXML
    private TextField userIdText;
    @FXML
    private TextField customerIdText;
    @FXML
    private TextField titleText;
    @FXML
    private TextField descriptionText;
    @FXML
    private TextField locationText;
    @FXML
    private TextField typeText;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Contact> contactCombo;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // populates the hours and minutes comboboxes
        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        startHour.setItems(hours);
        startMinute.setItems(minutes);
        endHour.setItems(hours);
        endMinute.setItems(minutes);
        ContactDAO contactDAO = new ContactDAO();
        contactCombo.setItems(contactDAO.getAllContacts());
    }    

    /**
     * This method saves the appointment data as a new appointment object to be used in AppointmentDAO in the update statement. 
     * @param event Save button selected
     * @throws IOException 
     */
    @FXML
    private void handleSave(ActionEvent event) throws IOException {
        if(customerIdText.getText().isEmpty() || titleText.getText().isEmpty() || descriptionText.getText().isEmpty() || locationText.getText().isEmpty() 
                || contactCombo.getValue() == null || typeText.getText().isEmpty() || datePicker.getValue() == null || startHour.getValue() == null 
                || startMinute.getValue() == null || endHour.getValue() == null || endHour.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Fields!");
            alert.setContentText("Please fill in all fields");
            alert.setGraphic(null);
            alert.showAndWait();
        }
        else {
            // builds appointment object to pass through to AppointmentDAO
            Appointment newAppt = new Appointment();
            ContactDAO contactDAO = new ContactDAO();
            newAppt.setId(Integer.valueOf(appointmentIdText.getText()));
            newAppt.setUserId(Integer.valueOf(userIdText.getText()));
            newAppt.setCustomerId(Integer.valueOf(customerIdText.getText()));
            newAppt.setTitle(titleText.getText());
            newAppt.setDescription(descriptionText.getText());
            newAppt.setLocation(locationText.getText());
            newAppt.setContactId(contactDAO.getcontactIdBycontact(String.valueOf(contactCombo.getValue())));
            newAppt.setType(typeText.getText());
            LocalDate date = datePicker.getValue();
            String starthr = startHour.getValue();
            String startmin = startMinute.getValue();
            String endhr = endHour.getValue();
            String endmin = endMinute.getValue();
            // building LocalDateTime object
            LocalDateTime startldt = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), Integer.parseInt(starthr), Integer.parseInt(startmin));
            ZonedDateTime startLocZdt = ZonedDateTime.of(startldt, ZoneId.systemDefault());
            ZonedDateTime startUtcZdt = startLocZdt.withZoneSameInstant(ZoneOffset.UTC);
            LocalDateTime timestamp = startUtcZdt.toLocalDateTime();
            newAppt.setStart(timestamp);
            // building LocalDateTime object
            LocalDateTime endldt = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), Integer.parseInt(endhr), Integer.parseInt(endmin));
            ZonedDateTime endLocZdt = ZonedDateTime.of(endldt, ZoneId.systemDefault());
            ZonedDateTime endUtcZdt = endLocZdt.withZoneSameInstant(ZoneOffset.UTC);
            LocalDateTime timestamp2 = endUtcZdt.toLocalDateTime();
            newAppt.setEnd(timestamp2);
            
            // verifies that the requested appointment time is within business hours
            if(!isBusinessHours(startUtcZdt, endUtcZdt)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Outside Business Hours!");
                alert.setContentText("The selected times are outside business hours.  Please select a new time.");
                alert.setGraphic(null);
                alert.showAndWait();
            }
            // verifies that the requested appointment time isn't overlapping an existing appointment
            else if(!isOverlapping(startUtcZdt, endUtcZdt)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Overlapping Appointments!");
                    alert.setContentText("The selected times are overlapping an existing appointment.  Please select a new time.");
                    alert.setGraphic(null);
                    alert.showAndWait();
            }
            // if appointment time is good, passes customer object through to update sql statement, then changes back to Main scene
            else {
                AppointmentDAO apptDAO = new AppointmentDAO();
                apptDAO.update(newAppt);
                
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/c195_project/Main.fxml"));
                loader.load();

                String userId = userIdLabel.getText();
                MainController mainController = loader.getController();
                mainController.setUserId(userId);
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    /**
     * This method cancels the modification process and goes back to Main scene.
     * Used lambda expression to prompt with an alert and listen for a confirmation before cancelling.
     * @param event Cancel button selected
     * @throws IOException 
     */
    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Cancelling will lose all unsaved data.");
        alert.setHeaderText("Do you wish to continue?");
        alert.setTitle("Cancel");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/c195_project/Main.fxml"));
                    loader.load();
                    
                    String userId = userIdLabel.getText();
                    MainController mainController = loader.getController();
                    mainController.setUserId(userId);
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = loader.getRoot();
                    stage.setScene(new Scene(scene));
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    /**
     * This method is called on and uses the appointment object created in Main scene to populate the text fields and combo boxes.
     * This also initializes this.appointment with the appointment object from Main.
     * @param appointment 
     */
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
        
        appointmentIdText.setText(Integer.toString(appointment.getId()));
        userIdText.setText(Integer.toString(appointment.getUserId()));
        customerIdText.setText(Integer.toString(appointment.getCustomerId()));
        titleText.setText(appointment.getTitle());
        descriptionText.setText(appointment.getDescription());
        locationText.setText(appointment.getLocation());
        contactCombo.setPromptText(appointment.getContact());
        typeText.setText(appointment.getType());
        LocalDate startDate = appointment.getStart().toLocalDate();
        datePicker.setValue(startDate);
        Integer startHr = appointment.getStart().getHour();
        startHour.setValue(String.valueOf(startHr));
        Integer startMin = appointment.getStart().getMinute();
        startMinute.setValue(String.valueOf(startMin));
        Integer endHr = appointment.getEnd().getHour();
        endHour.setValue(String.valueOf(endHr));
        Integer endMin = appointment.getEnd().getMinute();
        endMinute.setValue(String.valueOf(endMin));
    }

    /**
     * This method is used throughout the application to set the User ID label with the User ID of the currently logged in user.
     * @param userId 
     */
    public void setCurrentUserId(String userId) {
        userIdLabel.setText(userId);
    }
    
    /**
     * This boolean method is used to verify that the requested appointment time is within business hours.
     * @param start
     * @param end
     * @return 
     */
    private boolean isBusinessHours(ZonedDateTime start, ZonedDateTime end) {
        start = start.withZoneSameInstant(ZoneId.of("America/New_York"));
        end = end.withZoneSameInstant(ZoneId.of("America/New_York"));
        
        ZonedDateTime startLim1 = ZonedDateTime.of(start.toLocalDate(), LocalTime.of(7, 59), ZoneId.of("America/New_York"));
        ZonedDateTime stopLim1 = ZonedDateTime.of(start.toLocalDate(), LocalTime.of(22, 1), ZoneId.of("America/New_York"));
        ZonedDateTime startLim2 = ZonedDateTime.of(end.toLocalDate(), LocalTime.of(7, 59), ZoneId.of("America/New_York"));
        ZonedDateTime stopLim2 = ZonedDateTime.of(end.toLocalDate(), LocalTime.of(22, 1), ZoneId.of("America/New_York"));

        if(start.getDayOfWeek() == DayOfWeek.SATURDAY) return false;
        if(start.getDayOfWeek() == DayOfWeek.SUNDAY) return false;
        if(end.getDayOfWeek() == DayOfWeek.SATURDAY) return false;
        if(start.getDayOfWeek() == DayOfWeek.SUNDAY) return false;
        return startLim1.isBefore(start) && stopLim1.isAfter(start) && startLim2.isBefore(end) && stopLim2.isAfter(end);
    }
    
    /**
     * This is a boolean method used to verify that the requested appointment time isn't overlapping an existing appointment
     * @param start
     * @param end
     * @return 
     */
     private boolean isOverlapping(ZonedDateTime start, ZonedDateTime end){
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        String startStr = start.withZoneSameInstant(ZoneId.of("UTC+0")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String endStr = end.withZoneSameInstant(ZoneId.of("UTC+0")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int i = 0;
        i = appointmentDAO.getAppointmentCount(startStr, endStr);
        if(i>0) {
            return false;
        }
        return true;
    }
    
}
