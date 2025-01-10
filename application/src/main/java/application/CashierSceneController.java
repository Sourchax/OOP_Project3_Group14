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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
    private ImageView movieSelectionIcon;

    @FXML
    private ImageView sessionSelectionIcon;

    @FXML
    private ImageView seatSelectionIcon;

    @FXML
    private ImageView productSelectionIcon;

    @FXML
    private ImageView ticketSelectionIcon;

    @FXML
    private Line MtoS;

    @FXML
    private Line StoS;

    @FXML
    private Line StoP;

    @FXML
    private Line PtoT;

    @FXML
    private void initialize(){
        cashierParent.setParent(this);
        usernameLabel.setText(currentUser.getUsername());
        Image iconPlace = new Image(getClass().getResource("/application/fxml/icons/cashier-logo.png").toExternalForm());

        logoImageView.setImage(iconPlace);

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
                //changeProgress(child);         
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*     public void changeProgress(String stage){

        if(stage.equals("cashierStage1")){

            
        }
        else if(stage.equals("cashierStage2")){
            
            
        }
    }  */

    @FXML
    void handleLogOut(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
        BorderPane root = loader.load();


        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene loginScene = new Scene(root, 640, 480);
        currentStage.setTitle("Login Page"); 
        currentStage.setScene(loginScene); 
        currentStage.show();

        currentUser.setUsername("");

    }
}
