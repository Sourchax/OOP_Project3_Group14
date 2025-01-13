package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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


/**
 * Controller class for adding a movie to the application.
 * It provides methods to upload images, select genres, and save movie details to the database.
 */
public class AddMovieController {

    /**
     * Text field for entering the title of the movie.
     */
    @FXML
    private TextField titleField;

    /**
     * Label displaying the selected genres for the movie.
     */
    @FXML
    private Label genreField;

    /**
     * Text field for entering a brief summary of the movie.
     */
    @FXML
    private TextField summaryField;

    /**
     * Text field for entering the year of release for the movie.
     */
    @FXML
    private TextField yearField;

    /**
     * ImageView for displaying the movie poster after selecting an image file.
     */
    @FXML
    private ImageView imageView;

    /**
     * Button that opens the file chooser to upload an image for the movie poster.
     */
    @FXML
    private Button uploadImageButton;

    /**
     * Button that opens a pop-up window to select genres for the movie.
     */
    @FXML
    private Button addGenreButton;

    /**
     * The file object representing the selected image file for the movie poster.
     */
    private File selectedImageFile;

    /**
     * Initializes the AddMovieController.
     * Sets up text field formatting for the year and genre selection button action.
     */
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

     /**
     * Handles image file selection and displays the selected image in the image view.
     */
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

    /**
     * Adds a movie to the database with the provided details.
     * Validates the inputs before saving the movie and its image to the database.
     */
    @FXML
    private void onAddMovie() {
        String title = titleField.getText();
        String genre = genreField.getText();
        String summary = summaryField.getText();
        String year = yearField.getText();
        
        if (title.trim().length() == 0 || genre.isEmpty() || summary.trim().length() == 0 || year.isEmpty() || selectedImageFile == null) {
            cancelProcess("Cannot be empty!");
    
            return;
        }

        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        int yearIn = Integer.parseInt(year);
        if(yearIn < 1888 || yearIn > currentYear){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Year");
            alert.setHeaderText("Current Process Canceled");
            alert.setContentText("Year is invalid!");
            alert.showAndWait();
            return;
        }
        
        Movie newMovie = new Movie();
        newMovie.setName(title);
        newMovie.setGenre(genre);
        newMovie.setYear(year);
        newMovie.setSummary(summary);
        try {
            MoviesDao database = new MoviesDao();

            if(!database.getListByFilter("name", titleField.getText()).isEmpty()){
                cancelProcess("title");
                return;
            }
            
            if( !database.getListByFilter("summary", summaryField.getText()).isEmpty()){
                cancelProcess("summary");
                return;
            }
            
            FileInputStream file = new FileInputStream(selectedImageFile);
            
            byte[] imageBytes = file.readAllBytes();
            Blob imageBlob = new SerialBlob(imageBytes);
            
            if( !database.getListByFilter("poster", imageBlob).isEmpty()){
                cancelProcess("poster");
                return;
            }

            newMovie.setPoster(imageBlob);
    
            database.insert(newMovie);
            
        } catch (Exception e) {
            // TODO: handle exception
        }

        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    /**
     * Closes the current window without saving any data.
     */
    @FXML
    private void onCancel() {
        // Close the window without saving
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    /**
     * Opens a popup window for selecting genres.
     */
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

    /**
     * Updates the genre field with the selected genres.
     *
     * @param genres A string builder containing the selected genres.
     */
    public void updateSelectedGenres(StringBuilder genres) {
        if(genres.length()!=0)
            genreField.setText(genres.toString());
    }

    /**
     * Displays an error alert and cancels the current process.
     *
     * @param errorMessage The error message to be displayed in the alert.
     */
    private void cancelProcess(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        if(errorMessage.length() > 10){
            alert.setTitle(errorMessage);
            alert.setHeaderText("Current Process Canceled");
            alert.setContentText("All fields must be filled!");
            alert.showAndWait();
        }
        else{
            alert.setTitle("Existing " + errorMessage);
            alert.setHeaderText("Current Process Canceled");
            alert.setContentText("Movie " + errorMessage + " existing");
            alert.showAndWait();
        }
    }
}