/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package views;

import DAO.AppointmentDAO;
import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.DivisionDAO;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Country;
import models.Customer;
import models.Division;

/**
 * FXML Controller class
 * This scene allows the user to create new customers.
 * I implemented a lambda expression when the "Cancel" button is clicked to prompt with an alert and listen for a confirmation to improve readability.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class CustomerAddController implements Initializable {

    Stage stage;
    Parent scene;
    
    @FXML
    private ComboBox<Country> countryCombo;
    @FXML
    private ComboBox<Division> divCombo;
    @FXML
    private TextField customerIdText;
    @FXML
    private TextField customerNameText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField postalCodeText;
    @FXML
    private TextField phoneNumberText;
    @FXML
    private Label userIdLabel;
    
    ObservableList<Country> countries = FXCollections.observableArrayList();
    ObservableList<Division> divisions = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CountryDAO countryDAO = new CountryDAO();
        // populates the country combobox with all countries
        countryCombo.setItems(countryDAO.getAllCountries());
        // deactivates the division combobox -- it is activated after a country is selected at the combobox is populated with filtered data
        divCombo.setDisable(true);
    }    
    
    /**
     * This method populates the division combo box with data filtered by the country selected in the country combo box.
     * This also activates the division combo box once it is populated with the filtered data.
     * @param event Country selected from combo box
     */
    @FXML
    private void countryChange(ActionEvent event) {
        divCombo.getItems().clear();
        String selected = String.valueOf(countryCombo.getValue());
        DivisionDAO divisionDAO = new DivisionDAO();
        divCombo.getItems().addAll(divisionDAO.getDivisionsByCountry(selected));
        divCombo.setDisable(false);
    }
    
    /**
     * This method saves the customer data as a new customer object to be used in CustomerDAO in the create statement.
     * @param event Save button selected
     * @throws IOException 
     */
    @FXML
    private void handleSave(ActionEvent event) throws IOException {
        if(customerIdText.getText().isEmpty() || customerNameText.getText().isEmpty() || addressText.getText().isEmpty() || postalCodeText.getText().isEmpty() 
                || phoneNumberText.getText().isEmpty() || countryCombo.getValue() == null || divCombo.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Fields!");
            alert.setContentText("Please fill in all fields");
            alert.setGraphic(null);
            alert.showAndWait();
        }
        // builds the customer object to pass through to CustomerDAO
        else {
            Customer newCust = new Customer();
            DivisionDAO divDAO = new DivisionDAO();
            newCust.setName(customerNameText.getText());
            newCust.setAddress(addressText.getText());
            newCust.setCountry(String.valueOf(countryCombo.getValue()));
            newCust.setDivisionId(divDAO.getDivisionIdByDivision(String.valueOf(divCombo.getValue())));
            newCust.setPostalCode(postalCodeText.getText());
            newCust.setPhone(phoneNumberText.getText());

            CustomerDAO custDAO = new CustomerDAO();
            custDAO.create(newCust);
            
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

    /**
     * This method changes back to Main scene.
     * I used a lambda expression to prompt with an alert and listen for a confirmation to improve readability.
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
     * This method is used throughout the application to set the User ID label with the User ID of the currently logged in user.
     * @param userId 
     */
    public void setCurrentUserId(String userId) {
        userIdLabel.setText(userId);
    }
}