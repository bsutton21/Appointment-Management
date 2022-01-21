/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package views;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import c195_project.MainController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Appointment;
import models.Customer;

/**
 * FXML Controller class
 * This controls the Customer Schedule Report scene.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class ReportCustomerScheduleController implements Initializable {

    Stage stage;
    Parent scene;
    
    @FXML
    private ComboBox<Customer> customerCombo;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, String> contactCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, String> startCol;
    @FXML
    private TableColumn<Appointment, String> endCol;
    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;
    @FXML
    private Label userIdLabel;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        setCustomerCombo();
    }    

    /**
     * This populates the table based on the selected contact in the combo box.
     * @param event 
     */
    @FXML
    private void changeContact(ActionEvent event) {
        Customer selectedCustomer = customerCombo.getSelectionModel().getSelectedItem();
        AppointmentDAO apptDAO = new AppointmentDAO();
        appointmentTableView.setItems(apptDAO.getApptsByCustomer(selectedCustomer));
    }

    /**
     * This method navigates the user back to the Main scene.  It also passes through the User ID.
     * @param event Back button selected
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
    
    /**
     * This method is used throughout the application to set the User ID label with the User ID of the currently logged in user.
     * @param userId 
     */
    public void setCurrentUserId(String userId) {
        userIdLabel.setText(userId);
    }
    
    /**
     * This populates the combobox with all customer options.
     */
    private void setCustomerCombo() {
        CustomerDAO type = new CustomerDAO();
        customerCombo.setItems(type.findAll());
    }
    
}
