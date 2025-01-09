package application;

import java.util.List;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class GenreSelectionController {

    @FXML
    private CheckBox actionCheckBox;

    @FXML
    private CheckBox animationCheckBox;

    @FXML
    private CheckBox adventureCheckBox;

    @FXML
    private CheckBox comedyCheckBox;

    @FXML
    private CheckBox dramaCheckBox;

    @FXML
    private CheckBox horrorCheckBox;

    @FXML
    private CheckBox romanceCheckBox;

    @FXML
    private CheckBox sciFiCheckBox;

    @FXML
    private CheckBox thrillerCheckBox;

    @FXML
    private CheckBox fantasyCheckBox;

    @FXML
    private CheckBox crimeCheckBox;

    @FXML
    private CheckBox musicalCheckBox;

    @FXML
    private CheckBox historicalCheckBox;

    @FXML
    private CheckBox westernCheckBox;

    @FXML
    private CheckBox mysteryCheckBox;

    private Consumer<StringBuilder> genreSelectionCallback;

    private int selection;

    public void setGenreSelectionCallback(Consumer<StringBuilder> callback) {
        this.genreSelectionCallback = callback;
    }

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