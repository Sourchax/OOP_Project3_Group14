package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class CashierStage2Controller {

    @FXML
    private Label selectedMovieLabel;

    @FXML
    private ComboBox<String> daysComboBox;

    @FXML
    private ComboBox<String> sessionsComboBox;

    @FXML
    private ComboBox<String> sessionsComboBox1;

    @FXML
    private Button confirmSelectionButton;

    @FXML
    private Button backToSearchButton;

    @FXML
    private void handleConfirmSelection() {
        // Method to handle confirming the selection
    }

    @FXML
    private void handleBackToSearch() {
        System.out.println("Back to Search");
        cashierParent.getParent().handleScenes("cashierStage1");   
    }
}