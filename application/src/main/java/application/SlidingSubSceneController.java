package application;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class SlidingSubSceneController {

    @FXML
    private HBox root;

    @FXML
    private Button slideButton;

    private boolean isSubSceneVisible = false;

    private Image arrowUpIcon;
    private Image arrowDownIcon;

    @FXML
    public void initialize() {
        // Set button action
        slideButton.setOnAction(event -> toggleSubScene());
        arrowUpIcon = new Image(getClass().getResource("/application/fxml/icons/arrow-up.png").toExternalForm());
        arrowDownIcon = new Image(getClass().getResource("/application/fxml/icons/arrow-down.png").toExternalForm());
        ImageView icon = new ImageView(arrowUpIcon);
        slideButton.setText("Slide Up");
        icon.setFitHeight(20);
        icon.setFitWidth(20);
        slideButton.setGraphic(icon);
    }

    public void toggleSubScene() {

        if (isSubSceneVisible) {
            root.setMinHeight(0);
            ImageView icon = new ImageView(arrowDownIcon);
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            System.out.println("Help me!");
            slideButton.setText("Slide Up");
            slideButton.setGraphic(icon);
        } else {
            root.setMinHeight(100);
            ImageView icon = new ImageView(arrowUpIcon);
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            slideButton.setText("Slide Down");
            slideButton.setGraphic(icon);

        }

        isSubSceneVisible = !isSubSceneVisible;
    }
}