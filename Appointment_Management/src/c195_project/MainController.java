/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package c195_project;

import DAO.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Appointment;
import models.Customer;
import models.User;
import utils.DBConnection;
import views.AppointmentAddController;
import views.AppointmentModifyController;
import views.CustomerAddController;
import views.CustomerModifyController;
import views.ReportAppointmentTypeController;
import views.ReportContactScheduleController;
import views.ReportCustomerScheduleController;

/**
 * This is the main screen of the application. 
 * This houses table views for both appointment customer data and offers buttons to navigate to all other scenes besides the login screen.
 * I implemented lambda expressions for the weekly and monthly appointment filters for the two radio buttons.
 * The use of a lambda in this scenario eliminated the need for additional db connections and, thus, greatly reduced the load times 
 * from several seconds each time the table view was updated to virtually instantly.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class MainController implements Initializable {
    Stage stage;
    Parent scene;
    User user;
    
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, Integer> customerId;
    @FXML
    private TableColumn<Customer, String> customerName;
    @FXML
    private TableColumn<Customer, String> customerDivision;
    @FXML
    private TableColumn<Customer, String> customerCountry;
    @FXML
    private TableColumn<Customer, String> customerPhone;
    @FXML
    private TableColumn<Customer, String> customerAddress;
    @FXML
    private TableColumn<Customer, Integer> customerPostalCode;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, String> appointmentType;
    @FXML
    private TableColumn<Appointment, String> appointmentLocation;
    @FXML
    private TableColumn<Appointment, String> appointmentStart;
    @FXML
    private TableColumn<Appointment, String> appointmentEnd;
    @FXML
    private TableColumn<Appointment, Integer> appointmentId;
    @FXML
    private TableColumn<Appointment, String> appointmentTitle;
    @FXML
    private TableColumn<Appointment, String> appointmentDescription;
    @FXML
    private TableColumn<Appointment, String> appointmentContact;
    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerId;
    @FXML
    private RadioButton weekApptButton;
    @FXML
    private RadioButton monthApptButton;
    @FXML
    private Label userIdLabel;
    
    private ToggleGroup apptFilterToggleGroup;
    @FXML
    private Button deleteCustButton;
    @FXML
    private Button modifyCustButton;
    @FXML
    private Button deleteApptButton;
    @FXML
    private Button modifyApptButton;
    
    ObservableList<Appointment> apptList = FXCollections.observableArrayList();
    ObservableList<Customer> custList = FXCollections.observableArrayList();
    
    /**
     * 
     * INITIALIZES THE CONTROLLER CLASS
     * @param url
     * @param rb 
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();
        
        apptFilterToggleGroup = new ToggleGroup();
        this.weekApptButton.setToggleGroup(apptFilterToggleGroup);
        this.monthApptButton.setToggleGroup(apptFilterToggleGroup);
        
        customerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerDivision.setCellValueFactory(new PropertyValueFactory<>("Division"));
        customerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        CustomerDAO custDAO = new CustomerDAO();
        AppointmentDAO apptDAO = new AppointmentDAO();
        apptList.addAll(apptDAO.findAll());
        custList.addAll(custDAO.findAll());
        mainUpdateAppointmentTableView(apptList);
        mainUpdateCustomerTableView(custList);
    } 
    
    /**
     * Toggle group to filter the appointment table view to either weekly or monthly appointments
     * @param event Radio button selected
     * @throws SQLException 
     */
    @FXML
    private void radioButtonChanged(ActionEvent event) throws SQLException {
        if(this.apptFilterToggleGroup.getSelectedToggle().equals(this.weekApptButton)) {
            findApptByWeek();
        }
        if(this.apptFilterToggleGroup.getSelectedToggle().equals(this.monthApptButton)) {
            findApptByMonth();
        }
    }
    
    /**
     * Navigates user to add appointment screen.
     * @param event Add appointment button selected
     * @throws IOException 
     */
    @FXML
    private void addApointment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/AppointmentAdd.fxml"));
        loader.load();

        String userId = userIdLabel.getText();
        AppointmentAddController apptAddController = loader.getController();
        apptAddController.setCurrentUserId(userId);
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Navigates. to modify appointment screen for selected appointment.
     * This will prompt with an alert if no appointment is selected.
     * This also passes through a appointment object to populate the text fields and combo boxes in that scene
     * @param event Modify appointment button selected
     * @throws IOException 
     */
    @FXML
    private void modifyAppointment(ActionEvent event) throws IOException {
        // Get selected appointment
        Appointment selectedAppt = appointmentTableView.getSelectionModel().getSelectedItem();
        // Prompt with warning if nothing is selected
        if(selectedAppt == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No appointments selected to modify!");
            alert.setTitle("No Appointment Selected");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/AppointmentModify.fxml"));
            loader.load();

            String userId = userIdLabel.getText();
            AppointmentModifyController ModifyApptController = loader.getController();
            ModifyApptController.setCurrentUserId(userId);
            ModifyApptController.setAppointment(appointmentTableView.getSelectionModel().getSelectedItem());
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
    /**
     * Deletes the selected appointment.
     * This will prompt with an alert if no appointment is selected.
     * @param event Delete appointment button selected
     */
    @FXML
    private void deleteAppointment(ActionEvent event) {
        // Get selected appointment
        Appointment selectedAppt = appointmentTableView.getSelectionModel().getSelectedItem();
        // Prompt with warning if nothing is selected
        if (selectedAppt == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No appointment selected to delete!");
            alert.setTitle("No Appointment Selected");
            alert.showAndWait();
        }
        else {
        int selApptId = selectedAppt.getId();
        String selType = selectedAppt.getType();
            int appointmentId = selectedAppt.getId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setContentText("Are you sure you want to delete this \"" + selType + "\" appointment, ID # " + selApptId + "?");
            Optional<ButtonType> alertType = alert.showAndWait();
            
            if(alertType.get() == ButtonType.OK) {
                AppointmentDAO apptDAO = new AppointmentDAO();
                apptDAO.delete(appointmentId);
                apptList.clear();
                apptList.addAll(apptDAO.findAll());
                mainUpdateAppointmentTableView(apptList);
            }
        }
    }
    
    /**
     * Navigates to the add customer screen.
     * @param event Add customer button selected
     * @throws IOException 
     */
    @FXML
    private void addCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/CustomerAdd.fxml"));
        loader.load();

        String userId = userIdLabel.getText();
        CustomerAddController custAddController = loader.getController();
        custAddController.setCurrentUserId(userId);
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    // switch to modify customer scene for the selected customer
    // this also passes through a customer object to populate the text fields and comboboxes in that scene
    /**
     * Navigates. to modify customer screen for selected customer.
     * This will prompt with an alert if no customer is selected.
     * This also passes through a customer object to populate the text fields and combo boxes in that scene
     * @param event Modify customer button selected
     * @throws IOException 
     */
    @FXML
    private void modifyCustomer(ActionEvent event) throws IOException {
        // Get selected customer
        Customer selectedCustomer = this.customerTableView.getSelectionModel().getSelectedItem();
        // Prompt with warning if nothing is selected
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No customer selected to modify!");
            alert.setTitle("No Customer Selected");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/CustomerModify.fxml"));
            loader.load();

            String userId = userIdLabel.getText();
            CustomerModifyController ModifyCustController = loader.getController();
            ModifyCustController.setCurrentUserId(userId);
            ModifyCustController.setCustomer(customerTableView.getSelectionModel().getSelectedItem());
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
    // deletes selected customer
    /**
     * This will prompt with an alert if no appointment is selected.
     * First deletes an appointments with matching Customer_ID.
     * Deletes the selected customer after deleting the associated appointments.
     * @param event Delete customer button selected
     * @throws SQLException 
     */
    @FXML
    private void deleteCustomer(ActionEvent event) throws SQLException {
        // Get selected customer
        Customer selectedCustomer = this.customerTableView.getSelectionModel().getSelectedItem();
        // Prompt with warning if nothing is selected
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No customer selected to delete!");
            alert.setTitle("No Customer Selected");
            alert.showAndWait();
        }
        else {
            String selName = selectedCustomer.getName();
            int customerId = selectedCustomer.getId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setContentText("Are you sure you want to delete this customer, " + selName + "?");
            Optional<ButtonType> alertType = alert.showAndWait();
            
            if(alertType.get() == ButtonType.OK) {
                int custId = selectedCustomer.getId();
                AppointmentDAO apptDAO = new AppointmentDAO();
                apptDAO.deleteByCustId(custId);
                CustomerDAO custDAO = new CustomerDAO();
                custDAO.delete(customerId);
                apptList.clear();
                custList.clear();
                apptList.addAll(apptDAO.findAll());
                custList.addAll(custDAO.findAll());
                mainUpdateAppointmentTableView(apptList);
                mainUpdateCustomerTableView(custList);
            }
        }
    }

    /**
     * Populates the customer table view.  Called in initialize.
     * @param customers 
     */
    private void mainUpdateCustomerTableView(ObservableList<Customer> customers) {
        customerTableView.setItems(customers);
    }

    /**
     * Populates the appointment table view.  Called in initialize.
     * @param appointments 
     */
    private void mainUpdateAppointmentTableView(ObservableList<Appointment> appointments) {
        appointmentTableView.setItems(appointments);
    }

    /**
     * Closes db connection and exits the application.
     * @param event Exit button selected.
     */
    @FXML
    private void exitApplication(ActionEvent event) {
        DBConnection.closeConnection();
        Platform.exit();
        System.exit(0);
    }

    /**
     * Lambda expression to greatly reduce the load time and eliminates the need for a db connection.
     */
    private void findApptByWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus7 = now.plusDays(7);
        FilteredList<Appointment> filtList = new FilteredList<>(apptList);
        filtList.setPredicate(row -> {
            LocalDateTime rowDate = row.getStart();
            return rowDate.isAfter(now) && rowDate.isBefore(nowPlus7);
        });
        appointmentTableView.setItems(filtList);
    }

    /**
     * Lambda expression to greatly reduce the load time and eliminates the need for a db connection.
     */
    private void findApptByMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlusMonth = now.plusMonths(1);
        FilteredList<Appointment> filtList = new FilteredList<>(apptList);
        filtList.setPredicate(row -> {
            LocalDateTime rowDate = row.getStart();
            return rowDate.isAfter(now) && rowDate.isBefore(nowPlusMonth);
        });
        appointmentTableView.setItems(filtList);
    }
    
    /**
     * Removes the monthly or weekly filter from the radio buttons and again shows all appointments.
     */
    @FXML
    private void viewAllAppts(ActionEvent event) {
        apptFilterToggleGroup.getSelectedToggle().setSelected(false);
        mainUpdateAppointmentTableView(apptList);
    }
    
    /**
     * This method is used through the application to set the User ID label with the User ID of the currently logged in user. 
     * @param user 
     */
    public void setCurrentUserId(User user) {
        this.user = user;
        userIdLabel.setText(Integer.toString(user.getUserId()));
    }
    
    /**
     * This method is used to populate the user id label with the user from the login screen.
     * @param userId 
     */
    public void setUserId(String userId) {
        userIdLabel.setText(userId);
    }

    /**
     * Change scene to the Month/Type Count Report.
     * @param event Appointment types button selected
     * @throws IOException 
     */
    @FXML
    private void typeAppointment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ReportAppointmentType.fxml"));
        loader.load();

        String userId = userIdLabel.getText();
        ReportAppointmentTypeController repTypeController = loader.getController();
        repTypeController.setCurrentUserId(userId);
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Change scene to the Contact Schedule Report
     * @param event Contact schedule report button selected.
     * @throws IOException 
     */
    @FXML
    private void contactSchedule(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ReportContactSchedule.fxml"));
        loader.load();

        String userId = userIdLabel.getText();
        ReportContactScheduleController contSchedController = loader.getController();
        contSchedController.setCurrentUserId(userId);
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Change scene to the Customer Schedule Report
     * @param event Customer appointments button selected.
     * @throws IOException 
     */
    @FXML
    private void customerAppointments(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ReportCustomerSchedule.fxml"));
        loader.load();

        String userId = userIdLabel.getText();
        ReportCustomerScheduleController custSchedController = loader.getController();
        custSchedController.setCurrentUserId(userId);
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
