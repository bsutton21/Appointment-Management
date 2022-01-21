/*
 * Blake Sutton
 * Student ID: #001109490
 * C195 - Software II Performance Assessment
 */
package views;

import DAO.AppointmentDAO;
import DAO.UserDAO;
import c195_project.MainController;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Platform;
import models.User;
import utils.DBConnection;


/**
 * FXML Controller class
 * This controls the login screen and includes functionality to translate based on locale and to verify user credentials with an appropriate associated error message.
 * @author Blake Sutton, Student ID: #001109490.
 */
public class LoginScreenController implements Initializable {

    Stage stage;
    Parent scene;
    
    @FXML
    private TextField userField;
    @FXML
    private TextField passField;
    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label langLabel;
    @FXML
    private Label userLabel;
    @FXML
    private Label passLabel;

    private String errorHeader;
    private String errorTitle;
    private String errorText;
    @FXML
    private Label zoneIdLabel;
    
    /**
     * This method takes the entered credentials and verifies against data in the users table in the db.
     * Displays alert if the credentials are incorrect.
     * Assuming successful user verification and login, it will then display an alert with upcoming appointments.
     * @param event Login button clicked
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    private void handleLogin(ActionEvent event) throws IOException, SQLException {
        if (userField.getText().isEmpty() || passField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorTitle);
            alert.setHeaderText(errorHeader);
            alert.setContentText(errorText);
            alert.showAndWait();
        }
        else {
            String username = userField.getText();
            String password = passField.getText();
            boolean validUser = DBConnection.validateUser(username, password);
            String filename = "login_activity.txt";
            FileWriter fileWriter = new FileWriter(filename, true);
            PrintWriter outputFile = new PrintWriter(fileWriter);
            if(validUser) {
                String loginActivity = "Successful Login Attempt: " + username + " Date/Time: " + LocalDateTime.now();
                outputFile.println(loginActivity);
                outputFile.close();
                
                AppointmentDAO apptDAO = new AppointmentDAO();
                ZonedDateTime startView = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
                ZonedDateTime endView = startView.plusMinutes(15);
                String begin = startView.format(DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss") );
                String end = endView.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                apptDAO.upcomingAppointments(begin, end);
                
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/c195_project/Main.fxml"));
                loader.load();
                
                User currentUser = new User();
                MainController mainController = loader.getController();
                currentUser.setUsername(username);
                currentUser.setUserId(UserDAO.getCurrentUserId(username));
                mainController.setCurrentUserId(currentUser);
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else {
                String loginActivity = "FAILED LOGIN ATTEMPT: " + username + " Date/Time: " + LocalDateTime.now();
                outputFile.println(loginActivity);
                outputFile.close();            
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(errorTitle);
                alert.setHeaderText(errorHeader);
                alert.setContentText(errorText);
                alert.showAndWait();
            }
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("languages/login", locale);
        userLabel.setText(rb.getString("Username"));
        passLabel.setText(rb.getString("Password"));
        loginButton.setText(rb.getString("Login"));
        exitButton.setText(rb.getString("Exit"));
        langLabel.setText(rb.getString("Language"));
        errorHeader = rb.getString("Error");
        errorTitle = rb.getString("ErrorTitle");
        errorText = rb.getString("ErrorText");
        zoneIdLabel.setText(ZoneId.systemDefault().toString());
    }    

    /**
     * This method closes the db connection and exits the application.
     * @param event Exit button clicked
     */
    @FXML
    private void handleExit(ActionEvent event) {
        DBConnection.closeConnection();
        Platform.exit();
        System.exit(0);
    }
    
}
