package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.sql.Blob;
import javax.sql.rowset.serial.SerialBlob;

import dataAccess.MoviesDao;
import entities.Movie;

public class AddMovieController {

    @FXML
    private TextField titleField;
    @FXML
    private Label genreField;
    @FXML
    private TextField summaryField;
    @FXML
    private TextField yearField;
    @FXML
    private ImageView imageView;
    @FXML
    private Button uploadImageButton;
    @FXML
    private Button addGenreButton;


    private File selectedImageFile;

    @FXML
    private void initialize(){
        yearField.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            String newText = change.getControlNewText();
        
            if (newText.matches("\\d{0,4}")) {
                return change; 
            }
            return null;
        }));
        addGenreButton.setOnAction(event -> showGenreSelectionPopup());
    }

    @FXML
    private void onUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));

        selectedImageFile = fileChooser.showOpenDialog(null);
        if (selectedImageFile != null) {
            try {
                Image image = new Image(new FileInputStream(selectedImageFile));
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Add movie logic, e.g., save movie details to a database or a list
    @FXML
    private void onAddMovie() {
        String title = titleField.getText();
        String genre = genreField.getText();
        String summary = summaryField.getText();
        String year = yearField.getText();

        if (title.isEmpty() || genre.isEmpty() || summary.isEmpty() || year.isEmpty()) {
            System.out.println("Please fill all fields and select an image.");

            return;
        }

        // Save the movie logic here (e.g., add to a list, database, etc.)
        System.out.println("Movie Added: " + title + " (" + genre + ", " + summary + ")");
        
        Movie newMovie = new Movie();
        newMovie.setName(title);
        newMovie.setGenre(genre);
        newMovie.setReleaseYear(year);
        newMovie.setSummary(summary);
        try {

            FileInputStream file = new FileInputStream(selectedImageFile);
    
            byte[] imageBytes = file.readAllBytes();
            Blob imageBlob = new SerialBlob(imageBytes);
            newMovie.setPoster(imageBlob);
    
            MoviesDao database = new MoviesDao();
            database.insert(newMovie);
            
        } catch (Exception e) {
            // TODO: handle exception
        }

        // Close the window after adding the movie (optional)
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCancel() {
        // Close the window without saving
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    private void showGenreSelectionPopup() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/GenreSelection.fxml"));
            AnchorPane popupRoot = loader.load();

            // Get the controller for Genre Selection
            GenreSelectionController controller = loader.getController();

            // Set a callback to receive the selected genres
            controller.setGenreSelectionCallback(this::updateSelectedGenres);

            Stage popupStage = new Stage();
            popupStage.setTitle("Select Genres");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with the main stage
            popupStage.setScene(new Scene(popupRoot));
            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSelectedGenres(StringBuilder genres) {
        if(genres.length()!=0)
            genreField.setText(genres.toString());
    }
}