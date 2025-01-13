package application;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controller class for handling manager methods
 * This class is responsible for update inventory, employee and see financial stuff
 */
public class ManagerSceneController {

    /**
     * The ImageView displaying the logo.
     * This is used to display the logo image in the manager scene.
     */
    @FXML
    private ImageView logoImageView;

    /**
     * The Label displaying the username of the logged-in user.
     * This label is used to show the username of the current user (manager).
     */
    @FXML
    private Label usernameLabel;
    
    /**
     * The Label displaying the role of the logged-in user.
     * This label is used to display the role (e.g., Manager) of the current user.
     */
    @FXML
    private Label roleLabel;

    /**
     * The Label displaying the date.
     * This label is used to show the current date in the manager scene.
     */
    @FXML
    private Label dateLabel;

    /**
     * The Label displaying the current date and time.
     * This label is used to display the current date and time formatted as "yyyy-MM-dd".
     */
    @FXML
    private Label dateTimeLabel;

    /**
     * The Button to log out of the application.
     * This button triggers the log out process and redirects the user to the login page.
     */
    @FXML
    private Button logOutButton;

     /**
     * The BorderPane layout that holds the main scene.
     * This is the main container that manages the layout of the manager scene.
     */
    @FXML
    private BorderPane mainPane;

    /**
     * Set date and load inventory
     */
    @FXML
    private void initialize(){
        ManagerParent.setParent(this);
        System.out.println("manager scene");
        usernameLabel.setText(currentUser.getUsername());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateTimeLabel.setText(java.time.LocalDateTime.now().format(formatter));
        handleScenes("fxml/manager/inventory.fxml");
    }

    /**
     * Go to inventory section
     * @param event mouseevent
     */
    @FXML
    void onClickInventory(MouseEvent event) {
        handleScenes("fxml/manager/inventory.fxml");
    }

    /**
     * Go to employees section
     * @param event mouseevent
     */
    @FXML
    void onClickEmployees(MouseEvent event) {
        handleScenes("fxml/manager/employees.fxml");
    }

    /**
     * Go to Revenue tax section  section
     * @param event mouseevent
     */
    @FXML
    void goFinanceInfoStage(MouseEvent event) {
        System.out.println("Revenue tax section clicked");
        handleScenes("fxml/manager/finance.fxml");
    }

    /**
     * load fxml based on path
     * @param path fxml path
     */
    public void handleScenes(String path){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(path));
        try {
            Node temp = fxmlLoader.load();
            if(mainPane.getCenter() != temp){
                mainPane.setCenter(temp);         
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * logout the application
     * @param event mouse event
     * @throws IOException if path not found
     */
    @FXML
    void onLogoutClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
        BorderPane root = loader.load();
        LoginController loginController = loader.getController();
    
        // Get the current stage based on the event
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
        // Set the scene for the stage
        Scene loginScene = new Scene(root, 1200, 768);
        currentStage.setTitle("Login Page"); 
        currentStage.setScene(loginScene);   
        currentStage.show();
    }
}