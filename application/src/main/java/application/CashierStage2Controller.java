package application;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CashierStage2Controller {

    @FXML
    private Label selectedMovieLabel;

    @FXML
    private ComboBox<String> daysComboBox;

    @FXML
    private ComboBox<String> sessionsComboBox;

    @FXML
    private ComboBox<String> sessionsComboBox1;

    @FXML
    private Button confirmSelectionButton;

    @FXML
    private Button backToSearchButton;

    
    @FXML
    private Label movieGenresLabel;

    @FXML
    private ImageView movieImage;

    @FXML
    private Label movieTitleLabel;

    @FXML
    private TextArea summaryTextArea;

    @FXML
    private void initialize() {
        movieGenresLabel.setText(StaticMovie.staticMovie.getGenre());
        movieTitleLabel.setText(StaticMovie.staticMovie.getName());
        summaryTextArea.setText(StaticMovie.staticMovie.getSummary());

        if(StaticMovie.staticMovie.getPoster() == null){
            movieImage.setImage(null);
            return;
        }

        InputStream inputStream;
        try {
            Blob imageBlob = StaticMovie.staticMovie.getPoster();
            inputStream = imageBlob.getBinaryStream();
            Image image = new Image(inputStream);
            movieImage.setImage(image);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    @FXML
    private void handleConfirmSelection() {
        cashierParent.getParent().handleScenes("seatPlanStage");
    }

    @FXML
    private void handleBackToSearch() {
        System.out.println("Back to Search");
        cashierParent.getParent().handleScenes("cashierStage1");   
    }
}