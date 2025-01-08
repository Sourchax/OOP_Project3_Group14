package application;

import java.io.IOException;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.SubScene;

public class CashierSceneController {

    @FXML
    private ImageView logoImageView;

    @FXML
    private Label cashierLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Button logOutButton;

    @FXML
    private BorderPane mainPane;


    @FXML
    private void initialize(){
        cashierParent.setParent(this);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/slidingSubScene.fxml"));
        HBox subSceneContent;
        try {
            subSceneContent = loader.load();
            mainPane.setBottom(subSceneContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        handleScenes("cashierStage1");
    }

    @FXML
    void handleSearchByGenre() {
        // Method to handle search by genre
    }

    @FXML
    void handleSearchByPartialTitle() {
        // Method to handle search by partial title
    }

    @FXML
    void handleSearchByFullTitle() {
        // Method to handle search by full title
    }

    public void handleScenes(String child){
        String path = "fxml/cashier/" + child + ".fxml"; 
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(path));
        try {
            Node temp = fxmlLoader.load();
            if(mainPane.getCenter() != temp){
                mainPane.setCenter(temp);         
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogOut(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
        BorderPane root = loader.load();

        LoginController loginController = loader.getController();//loginController

        // Get the current stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene loginScene = new Scene(root, 640, 480);
        currentStage.setTitle("Login Page"); 
        currentStage.setScene(loginScene); 
        currentStage.show();

        //  pass any data to the LoginController
        //loginController.setSomeData(someData);
    }
}
