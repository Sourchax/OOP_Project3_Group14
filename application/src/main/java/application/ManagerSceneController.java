package application;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ManagerSceneController {

    @FXML
    private ImageView logoImageView;

    @FXML
    private Label usernameLabel;
    
    @FXML
    private Label roleLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label dateTimeLabel;

    @FXML
    private Button logOutButton;

    @FXML
    private BorderPane mainPane;

    @FXML
    private void initialize(){
        ManagerParent.setParent(this);
        System.out.println("manager scene");
        usernameLabel.setText(currentUser.getUsername());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateTimeLabel.setText(java.time.LocalDateTime.now().format(formatter));
        handleScenes("fxml/manager/inventory.fxml");
    }

    @FXML
    void onClickInventory(MouseEvent event) {
        handleScenes("fxml/manager/inventory.fxml");
    }
    @FXML
    void onClickEmployees(MouseEvent event) {
        handleScenes("fxml/manager/employees.fxml");
    }

    @FXML
    void goFinanceInfoStage(MouseEvent event) {
        System.out.println("Revenue tax section clicked");
        handleScenes("fxml/manager/finance.fxml");
    }

    public void handleScenes(String path){
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
    void onLogoutClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
        BorderPane root = loader.load();
        LoginController loginController = loader.getController();
    
        // Get the current stage based on the event
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
        // Set the scene for the stage
        Scene loginScene = new Scene(root, 1200, 768);
        currentStage.setTitle("Login Page"); 
        currentStage.setScene(loginScene);   
        currentStage.show();
    }
}