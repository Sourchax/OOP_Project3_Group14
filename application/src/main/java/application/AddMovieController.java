package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
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
    private TextField genreField;
    @FXML
    private TextField summaryField;
    @FXML
    private TextField yearField;
    @FXML
    private ImageView imageView;
    @FXML
    private Button uploadImageButton;

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
}