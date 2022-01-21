/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package views;

import DAO.AppointmentDAO;
import c195_project.MainController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Appointment;

/**
 * FXML Controller class
 * This scene allows users to see how the number of appointments scheduled by month or type.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class ReportAppointmentTypeController implements Initializable {

    Stage stage;
    Parent scene;
    ObservableList<String> months = FXCollections.observableArrayList();
    @FXML
    private TextField countTotal;
    @FXML
    private ComboBox<String> monthCombo;
    @FXML
    private ComboBox<Appointment> typeCombo;
    @FXML
    private Label userIdLabel;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        months.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        monthCombo.setItems(months);
        AppointmentDAO apptDAO = new AppointmentDAO();
        setTypeCombo();
        
    }    

    /**
     * This method determines the selected month and displays the appointment count for that month.
     * @param event Month selected in the combo box.
     */
    @FXML
    private void changeMonth(ActionEvent event) {
        countTotal.clear();
        AppointmentDAO apptDAO = new AppointmentDAO();
        String month = monthCombo.getValue();
        countTotal.setText(apptDAO.getApptCountByMonth(month));
    }

    /**
     * This method determines the selected type and displays the appointment count for that type.
     * @param event Type selected in the combo box.
     */
    @FXML
    private void changeType(ActionEvent event) {
        countTotal.clear();
        monthCombo.setValue("");
        AppointmentDAO apptDAO = new AppointmentDAO();
        String type = String.valueOf(typeCombo.getValue());
        countTotal.setText(apptDAO.getApptCountByType(type));
    }

    /**
     * This populates the type combo box.
     */
    private void setTypeCombo() {
        AppointmentDAO type = new AppointmentDAO();
        typeCombo.setItems(type.getType());
    }
    
    /**
     * This method is used throughout the application to set the User ID label with the User ID of the currently logged in user.
     * @param userId 
     */
    public void setCurrentUserId(String userId) {
        userIdLabel.setText(userId);
    }

    /**
     * This method navigates the user back to the Main scene. It also passes through the User ID.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void backButton(ActionEvent event) throws IOException {
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
