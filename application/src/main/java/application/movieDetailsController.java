package application;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class movieDetailsController {

    @FXML
    private AnchorPane titleField;

    @FXML
    private ImageView moviePoster;

    @FXML
    private Label movieTitle;

    @FXML
    private Label movieYear;

    @FXML
    private Label movieGenres;

    @FXML
    private Label summary;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void initialize() {

        confirmButton.setOnAction(event -> handleConfirmButtonAction());
        cancelButton.setOnAction(event -> handleCancelButtonAction());

        InputStream inputStream;
        try {
            inputStream = StaticSelection.staticMovie.getPoster().getBinaryStream();
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

    @FXML
    private void handleConfirmButtonAction() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancelButtonAction() {
        StaticSelection.staticMovie.setGenre(null);

        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
}
