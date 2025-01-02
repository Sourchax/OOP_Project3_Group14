package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InputToOutputController {

    @FXML
    private TextField inputField;

    @FXML
    private Button copyButton;

    @FXML
    private Label outputLabel;

    @FXML
    private void initialize() {
        // Set up button action
        copyButton.setOnAction(event -> copyText());
    }

    private void copyText() {
        // Get text from input field and set it to the label
        String inputText = inputField.getText();
        if (inputText.isEmpty()) {
            outputLabel.setText("Please enter some text.");
        } else {
            outputLabel.setText(inputText);
        }
    }
}