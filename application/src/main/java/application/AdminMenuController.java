package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;

public class AdminMenuController {

    @FXML
    private ImageView userPicture;

    @FXML
    private Label roleField;

    @FXML
    private Label usernameField;

    @FXML
    private Label dateTimeField;

    @FXML
    private Button logOutButton;

    @FXML
    private Button moviesButton;

    @FXML
    private Button schedulesButton;

    @FXML
    private Button requestsButton;

    @FXML
    private BorderPane mainPane;
    
    // Initialize method to set up initial state
    @FXML
    private void initialize() {
        updateDateTimeField();
        updateUserInfo("ErkDemirel", "Adminiş", "file:fxml\\deneme.jpg");
    }

    @FXML
    private void handleLogOutAction() {
        System.out.println("Log Out button clicked.");
        // Add logic to log out user
    }

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

    @FXML
    private void handleRequestsButtonAction() {
        System.out.println("Requests button clicked.");
        // Add logic to navigate to requests section
    }

    // Example method to dynamically update the dateTimeField
    public void updateDateTimeField() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateTimeField.setText(java.time.LocalDateTime.now().format(formatter));
    }

    // Example method to update user information dynamically
    public void updateUserInfo(String username, String role, String imagePath) {
        usernameField.setText(username);
        roleField.setText(role);
        //userPicture.setImage(new Image(imagePath));
    }
}