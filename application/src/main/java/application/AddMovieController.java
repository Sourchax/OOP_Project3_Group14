package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AddMovieController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField yearField;
    @FXML
    private ImageView imageView;
    @FXML
    private Button uploadImageButton;

    private File selectedImageFile;

    // Method to open FileChooser and select an image
    @FXML
    private void onUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));

        // Show open file dialog and get the selected file
        selectedImageFile = fileChooser.showOpenDialog(null);
        if (selectedImageFile != null) {
            try {
                // Display the selected image in the ImageView
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
        String year = yearField.getText();

        if (title.isEmpty() || genre.isEmpty() || year.isEmpty() || selectedImageFile == null) {
            System.out.println("Please fill all fields and select an image.");
            return;
        }

        // Save the movie logic here (e.g., add to a list, database, etc.)
        System.out.println("Movie Added: " + title + " (" + genre + ", " + year + ")");
        
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