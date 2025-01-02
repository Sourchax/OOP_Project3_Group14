package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Button nextQuoteButton;

    @FXML
    private void initialize() {
        // Initialize the controller and set up event handlers if needed
        signInButton.setOnAction(event -> handleSignIn());
        nextQuoteButton.setOnAction(event -> handleNextQuote());
    }

    private void handleSignIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Email or Password cannot be empty!");
        } else {
            // Add your authentication logic here
            System.out.println("Signing in with Email: " + username);
        }
    }

    private void handleNextQuote() {
        System.out.println("Next Quote clicked!");
        // Add logic to display the next testimonial or quote
    }
}