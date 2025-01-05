package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class CashierStage1Controller {

    @FXML
    private TextField searchField;

    @FXML
    private Button searchByGenreButton;

    @FXML
    private Button searchByPartialTitleButton;

    @FXML
    private Button searchByFullTitleButton;

    @FXML
    private Label selectedMovieLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private ListView<String> searchResultsListView;

    @FXML
    private ImageView selectedMoviePoster;

    @FXML
    private Label selectedMovieTitle;

    @FXML
    private Label selectedMovieGenres;

    @FXML
    private Label selectedMovieSummary;

    @FXML
    private void initialize(){
        System.out.println("initial");
        confirmButton.setOnAction(event -> handleConfirmSelection());
    }

    @FXML
    void handleSearchByGenre() {
        // Method to handle search by genre
    }

    @FXML
    void handleSearchByPartialTitle() {
        // Method to handle search by partial title
    }

    @FXML
    void handleSearchByFullTitle() {
        // Method to handle search by full title
    }

    @FXML
    private void handleConfirmSelection() {
        System.out.println("Confirm");
        cashierParent.getParent().handleScenes("cashierStage2");
    }

    @FXML
    void handleLogOut() {
        // Method to handle logout
    }
}
