package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

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
        System.out.println("Naber");
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
    void handleLogOut() {
        // Method to handle logout
    }
}
