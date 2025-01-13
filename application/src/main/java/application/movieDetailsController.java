package application;

import java.io.InputStream;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller class responsible for displaying movie details in the movie details view.
 * This includes the movie's poster, title, year, genre, and summary.
 * The controller allows the user to either confirm or cancel the selection of the movie.
 */
public class movieDetailsController {

    /**
     * AnchorPane that holds the title and other UI elements in the view.
     */
    @FXML
    private AnchorPane titleField;

    /**
     * ImageView that displays the movie poster.
     */
    @FXML
    private ImageView moviePoster;

    /**
     * Label that displays the movie title.
     */
    @FXML
    private Label movieTitle;

    /**
     * Label that displays the movie year.
     */
    @FXML
    private Label movieYear;

    /**
     * Label that displays the movie genres.
     */
    @FXML
    private Label movieGenres;

    /**
     * Label that displays the movie summary.
     */
    @FXML
    private Label summary;

    /**
     * Button that confirms the selection and closes the window.
     */
    @FXML
    private Button confirmButton;

    /**
     * Button that cancels the selection and closes the window.
     */
    @FXML
    private Button cancelButton;

    /**
     * Initializes the controller by setting up event handlers for the confirm and cancel buttons
     * and populating the UI elements with the selected movie details.
     */
    @FXML
    private void initialize() {
        // Set up button actions
        confirmButton.setOnAction(event -> handleConfirmButtonAction());
        cancelButton.setOnAction(event -> handleCancelButtonAction());

        // Load movie details
        try {
            InputStream inputStream = StaticSelection.staticMovie.getPoster().getBinaryStream();
            Image image = new Image(inputStream);
            moviePoster.setImage(image);

            movieTitle.setText(StaticSelection.staticMovie.getName());
            movieYear.setText(StaticSelection.staticMovie.getYear());
            movieGenres.setText(StaticSelection.staticMovie.getGenre());
            summary.setText(StaticSelection.staticMovie.getSummary());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the confirm button. It closes the movie details window.
     */
    @FXML
    private void handleConfirmButtonAction() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the action of the cancel button. It resets the movie genre and closes the movie details window.
     */
    @FXML
    private void handleCancelButtonAction() {
        // Reset the movie genre before closing
        StaticSelection.staticMovie.setGenre(null);

        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
}
