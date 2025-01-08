package application;

import java.util.List;
import java.util.function.Consumer;

import javafx.fxml.FXML;
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

    public void setGenreSelectionCallback(Consumer<StringBuilder> callback) {
        this.genreSelectionCallback = callback;
    }

    @FXML
    public void handleSubmit() {
        // Collect selected genres
        StringBuilder selectedGenres = new StringBuilder("");

        if (actionCheckBox.isSelected()) selectedGenres.append("Action, ");
        if (adventureCheckBox.isSelected()) selectedGenres.append("Adventure, ");
        if (comedyCheckBox.isSelected()) selectedGenres.append("Comedy, ");
        if (dramaCheckBox.isSelected()) selectedGenres.append("Drama, ");
        if (horrorCheckBox.isSelected()) selectedGenres.append("Horror, ");
        if (romanceCheckBox.isSelected()) selectedGenres.append("Romance, ");
        if (sciFiCheckBox.isSelected()) selectedGenres.append("Science-Fiction, ");
        if (thrillerCheckBox.isSelected()) selectedGenres.append("Thriller, ");
        if (fantasyCheckBox.isSelected()) selectedGenres.append("Fantasy, ");
        if (animationCheckBox.isSelected()) selectedGenres.append("Animation, ");
        if (crimeCheckBox.isSelected()) selectedGenres.append("Crime, ");
        if (musicalCheckBox.isSelected()) selectedGenres.append("Musical, ");
        if (historicalCheckBox.isSelected()) selectedGenres.append("Historical, ");
        if (westernCheckBox.isSelected()) selectedGenres.append("Western, ");
        if (mysteryCheckBox.isSelected()) selectedGenres.append("Mystery, ");

        // Remove trailing comma and space
        if (selectedGenres.length() > 0) {
            selectedGenres.setLength(selectedGenres.length() - 2);
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