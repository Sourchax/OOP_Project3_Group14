package application;

import java.util.List;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

/**
 * The {@code GenreSelectionController} class handles the genre selection process for a movie application.
 * This controller manages the interaction between the user and the genre checkboxes in the user interface.
 * It validates the selected genres and notifies the caller through a callback function.
 */
public class GenreSelectionController {

    /**
     * The CheckBox for selecting the "Action" genre.
     * When selected, it indicates that the "Action" genre is chosen by the user.
     */
    @FXML
    private CheckBox actionCheckBox;

    /**
     * The CheckBox for selecting the "Animation" genre.
     * When selected, it indicates that the "Animation" genre is chosen by the user.
     */
    @FXML
    private CheckBox animationCheckBox;

    /**
     * The CheckBox for selecting the "Adventure" genre.
     * When selected, it indicates that the "Adventure" genre is chosen by the user.
     */
    @FXML
    private CheckBox adventureCheckBox;

    /**
     * The CheckBox for selecting the "Comedy" genre.
     * When selected, it indicates that the "Comedy" genre is chosen by the user.
     */
    @FXML
    private CheckBox comedyCheckBox;

    /**
     * The CheckBox for selecting the "Drama" genre.
     * When selected, it indicates that the "Drama" genre is chosen by the user.
     */
    @FXML
    private CheckBox dramaCheckBox;

    /**
     * The CheckBox for selecting the "Horror" genre.
     * When selected, it indicates that the "Horror" genre is chosen by the user.
     */
    @FXML
    private CheckBox horrorCheckBox;

    /**
     * The CheckBox for selecting the "Romance" genre.
     * When selected, it indicates that the "Romance" genre is chosen by the user.
     */
    @FXML
    private CheckBox romanceCheckBox;

    /**
     * The CheckBox for selecting the "Science-Fiction" genre.
     * When selected, it indicates that the "Science-Fiction" genre is chosen by the user.
     */
    @FXML
    private CheckBox sciFiCheckBox;

    /**
     * The CheckBox for selecting the "Thriller" genre.
     * When selected, it indicates that the "Thriller" genre is chosen by the user.
     */
    @FXML
    private CheckBox thrillerCheckBox;

    /**
     * The CheckBox for selecting the "Fantasy" genre.
     * When selected, it indicates that the "Fantasy" genre is chosen by the user.
     */
    @FXML
    private CheckBox fantasyCheckBox;

    /**
     * The CheckBox for selecting the "Crime" genre.
     * When selected, it indicates that the "Crime" genre is chosen by the user.
     */
    @FXML
    private CheckBox crimeCheckBox;

    /**
     * The CheckBox for selecting the "Musical" genre.
     * When selected, it indicates that the "Musical" genre is chosen by the user.
     */
    @FXML
    private CheckBox musicalCheckBox;

    /**
     * The CheckBox for selecting the "Historical" genre.
     * When selected, it indicates that the "Historical" genre is chosen by the user.
     */
    @FXML
    private CheckBox historicalCheckBox;

    /**
     * The CheckBox for selecting the "Western" genre.
     * When selected, it indicates that the "Western" genre is chosen by the user.
     */
    @FXML
    private CheckBox westernCheckBox;

    /**
     * The CheckBox for selecting the "Mystery" genre.
     * When selected, it indicates that the "Mystery" genre is chosen by the user.
     */
    @FXML
    private CheckBox mysteryCheckBox;

    /**
     * A callback function that will be called when the genre selection is submitted.
     */
    private Consumer<StringBuilder> genreSelectionCallback;

     /**
     * The number of selected genres.
     */
    private int selection;

    /**
     * Sets the callback function that will handle the selected genres.
     * 
     * @param callback The callback function to be executed with the selected genres.
     */
    public void setGenreSelectionCallback(Consumer<StringBuilder> callback) {
        this.genreSelectionCallback = callback;
    }

    /**
     * Handles the submission of selected genres. 
     * It collects the selected genres, validates that no more than three genres are selected, 
     * and then triggers the callback function with the selected genres.
     * If more than three genres are selected, an error alert is shown, and the selection is reset.
     */
    @FXML
    public void handleSubmit() {
        // Collect selected genres
        StringBuilder selectedGenres = new StringBuilder("");

        selection = 0;
        if (actionCheckBox.isSelected()){
            selectedGenres.append("Action, ");
            selection++;
        } 
        if (adventureCheckBox.isSelected()){
            selectedGenres.append("Adventure, ");
            selection++;
        } 
        if (comedyCheckBox.isSelected()){
            selectedGenres.append("Comedy, ");
            selection++;
        } 
        if (dramaCheckBox.isSelected()){
            selectedGenres.append("Drama, ");
            selection++;
        } 
        if (horrorCheckBox.isSelected()){
            selectedGenres.append("Horror, ");
            selection++;
        } 
        if (romanceCheckBox.isSelected()){
            selectedGenres.append("Romance, ");
            selection++;
        } 
        if (sciFiCheckBox.isSelected()){
            selectedGenres.append("Science-Fiction, ");
            selection++;
        } 
        if (thrillerCheckBox.isSelected()){
            selectedGenres.append("Thriller, ");
            selection++;
        } 
        if (fantasyCheckBox.isSelected()){
            selectedGenres.append("Fantasy, ");
            selection++;
        } 
        if (animationCheckBox.isSelected()){
            selectedGenres.append("Animation, ");
            selection++;
        } 
        if (crimeCheckBox.isSelected()){
            selectedGenres.append("Crime, ");
            selection++;
        } 
        if (musicalCheckBox.isSelected()){
            selectedGenres.append("Musical, ");
            selection++;
        } 
        if (historicalCheckBox.isSelected()){
            selectedGenres.append("Historical, ");
            selection++;
        } 
        if (westernCheckBox.isSelected()){
            selectedGenres.append("Western, ");
            selection++;
        } 
        if (mysteryCheckBox.isSelected()){
            selectedGenres.append("Mystery, ");
            selection++;
        } 

        // Remove trailing comma and space
        if (selectedGenres.length() > 0) {
            selectedGenres.setLength(selectedGenres.length() - 2);
        }

        if(selection > 3){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Multiple Genres Selected!");
            alert.setHeaderText("Current Process Canceled");
            alert.setContentText("There cannot be more than 3 genres!");
            alert.showAndWait();
            selectedGenres = new StringBuilder("");
        }

        if (genreSelectionCallback != null) {
            genreSelectionCallback.accept(selectedGenres);
        }
        System.out.println(selectedGenres.toString());

        // Close the pop-up window
        Stage stage = (Stage) actionCheckBox.getScene().getWindow();
        stage.close();
    }
}