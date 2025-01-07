package application;

import java.io.IOException;

import entities.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ManagerSceneController {

    @FXML
    private ImageView logoImageView;

    @FXML
    private Label managerLabel;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label roleLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Button logOutButton;

    @FXML
    private BorderPane mainPane;

    private Employee currentEmployee;

    // Set the current employee before initialize is called
    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
    }


    public void setMainPane(BorderPane borderPane) {
        this.mainPane = borderPane;
    }


    @FXML
    private void initialize(){
        ManagerParent.setParent(this);
        System.out.println("manager scene");
        //usernameLabel.setText(this.currentEmployee.getUsername());
        handleScenes("fxml/manager/inventory.fxml");
    }

    @FXML
    void onClickInventory(MouseEvent event) {
        System.out.println("Inventory section clicked");
    }
    @FXML
    void onClickEmployees(MouseEvent event) {
        System.out.println("Employees section clicked");
    }
    @FXML
    void onClickTickets(MouseEvent event) {
        System.out.println("Tickets section clicked");
    }
    @FXML
    void onCLickRevenueTax(MouseEvent event) {
        System.out.println("Revenue tax section clicked");
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
    
        // Get the login screen controller
        LoginController loginController = loader.getController();
    
        // Get the current stage based on the event
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
        // Set the scene for the stage
        Scene loginScene = new Scene(root, 1200, 768);
        currentStage.setTitle("Login Page"); 
        currentStage.setScene(loginScene);   
        currentStage.show();
    
        // pass any data to the LoginController
        // loginController.setSomeData(someData);
    }
}