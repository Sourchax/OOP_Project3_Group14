package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;

/**
 * Controller class for the admin menu. Provides functionality for updating user information,
 * handling button actions such as logging out, viewing movies, schedules, and requests.
 */
public class AdminMenuController {

     /**
     * ImageView that displays the profile picture of the current user.
     */
    @FXML
    private ImageView userPicture;

    /**
     * Label that displays the role of the current user (e.g., admin, user).
     */
    @FXML
    private Label roleField;

    /**
     * Label that displays the username of the current user.
     */
    @FXML
    private Label usernameField;

    /**
     * Label that displays the current date in the format "yyyy-MM-dd".
     */
    @FXML
    private Label dateTimeField;

    /**
     * Button that logs out the current user and redirects to the login page.
     */
    @FXML
    private Button logOutButton;

    /**
     * Button that loads and displays the movies tab when clicked.
     */
    @FXML
    private Button moviesButton;

    /**
     * Button that loads and displays the schedules tab when clicked.
     */
    @FXML
    private Button schedulesButton;

    /**
     * Button that loads and displays the requests tab for admin actions when clicked.
     */
    @FXML
    private Button requestsButton;

    /**
     * BorderPane that serves as the main layout for the admin menu and is used to display different tabs.
     */
    @FXML
    private BorderPane mainPane;

    /**
     * Initializes the AdminMenuController by updating the date and time display,
     * and user information on the screen.
     */
    @FXML
    private void initialize() {
        updateDateTimeField();
        updateUserInfo(currentUser.getUsername(), currentUser.getRole(), "file:fxml\\deneme.jpg");
    }

    /**
     * Handles the log out action. Loads the login page and transitions the current window to the login scene.
     *
     * @param event The mouse event that triggers the log out action.
     * @throws IOException If an error occurs while loading the login page.
     */
    @FXML
    private void handleLogOutAction(MouseEvent event) throws IOException {
        System.out.println("Log Out button clicked.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
        BorderPane root = loader.load();

        LoginController loginController = loader.getController();//loginController

        // Get the current stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene loginScene = new Scene(root, 640, 480);
        currentStage.setTitle("Login Page"); 
        currentStage.setScene(loginScene); 
        currentStage.show();
    }

    /**
     * Handles the action when the movies button is clicked. Loads and displays the movies tab in the main pane.
     */
    @FXML
    private void handleMoviesButtonAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/moviesTab.fxml"));
        try {
            Node temp = fxmlLoader.load();
            if(mainPane.getCenter() != temp)
                mainPane.setCenter(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the schedules button is clicked. Loads and displays the schedules tab in the main pane.
     */
    @FXML
    private void handleSchedulesButtonAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/schedulesTab.fxml"));
        try {
            Node temp = fxmlLoader.load();
            if(mainPane.getCenter() != temp)
                mainPane.setCenter(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the requests button is clicked. Loads and displays the admin cancel tab in the main pane.
     */
    @FXML
    private void handleRequestsButtonAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/adminCancel.fxml"));
        try {
            Node temp = fxmlLoader.load();
            if(mainPane.getCenter() != temp)
                mainPane.setCenter(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the dateTimeField with the current date in the format "yyyy-MM-dd".
     */
    public void updateDateTimeField() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateTimeField.setText(java.time.LocalDateTime.now().format(formatter));
    }

    /**
     * Updates the user information displayed on the screen, including username, role, and profile picture.
     *
     * @param username The username to be displayed.
     * @param role The role of the user to be displayed.
     * @param imagePath The path to the user's profile picture.
     */
    public void updateUserInfo(String username, String role, String imagePath) {
        usernameField.setText(username);
        roleField.setText(role);
        //userPicture.setImage(new Image(imagePath));
    }
}