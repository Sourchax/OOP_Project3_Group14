package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MoviesTabController {

    @FXML
    private Button addNewMovieButton;
    @FXML
    private Button editDetailsButton;
    @FXML
    private TableView<?> movieTable;
    @FXML
    private TableColumn<?, ?> titleColumn;
    @FXML
    private TableColumn<?, ?> genreColumn;
    @FXML
    private TableColumn<?, ?> summaryColumn;
    @FXML
    private ImageView movieImageView;

    @FXML
    private void initialize() {
        addNewMovieButton.setOnAction(event -> onAddNewMovie());
        editDetailsButton.setOnAction(event -> editMovieDetails());
    }

    @FXML
    private void onAddNewMovie() {
        System.out.println("Add New Movie button clicked!");
        
        // Open the Add Movie modal window when the button is clicked
        openAddMovieWindow();
    }

    private void openAddMovieWindow() {
        try {
            // Load the FXML for the Add Movie window (assuming you have AddMovie.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/AddMovieScene.fxml"));
            Parent root = loader.load();

            // Create a new stage for the Add Movie window
            Stage stage = new Stage();
            stage.setTitle("Add New Movie");
            stage.initModality(Modality.APPLICATION_MODAL);  // Block interaction with the main window
            stage.setScene(new Scene(root));
            stage.showAndWait();  // Wait until the modal window is closed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editMovieDetails(){
        System.out.println("Naber");
    }

    // Additional methods for interacting with the TableView, if necessary
    private void populateTableWithMovies() {
        // You can populate the table with movie data here
        // For example, creating a list of movies and setting it in the TableView
    }

    private void updateImage(String imagePath) {
        // Update the ImageView with a new image
        Image image = new Image("file:" + imagePath);
        movieImageView.setImage(image);
    }
}