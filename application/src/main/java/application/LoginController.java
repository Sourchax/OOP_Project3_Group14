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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label roleLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button loginButton;

    @FXML
    private BorderPane mainPane;

    public void setMainPane(BorderPane borderPane) {
        this.mainPane = borderPane;
    }

    @FXML
    void onClickLogin(MouseEvent event) {
        login(event);
    }

    @FXML
    void onExit(MouseEvent event) {
        System.exit(0);
    }

    /*Fix after db */
    // private void login(MouseEvent event) {
    //     String usernameEntry = usernameField.getText().trim();
    //     String passwordEntry = passwordField.getText().trim();
    //     System.out.println(usernameEntry + " " + passwordEntry);
    //     Employee currentEmployee = login(usernameEntry, passwordEntry);
    //     if (currentEmployee != null) {
    //         try {
    //             getData.username = currentEmployee.getUsername();
    //             getData.role = currentEmployee.getRole();
    //             goRelatedPage(currentEmployee, event);
    //         }
    //         catch (IOException e){
    //             e.printStackTrace();
    //         }
    //     }
    //     else {
    //         //make a pop up element 
    //         System.out.println("Invalid userame or password"); 
    //     }
    // }

    private void login(MouseEvent event) {
        String usernameEntry = usernameField.getText().trim();
        String passwordEntry = passwordField.getText().trim();
        System.out.println(usernameEntry + " " + passwordEntry);
        if (usernameEntry.equals("manager1") && passwordEntry.equals("manager1")) {
            try {
                getData.username = usernameEntry;
                getData.role = "manager";
                goRelatedPage("manager", event);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else if (usernameEntry.equals("cashier1") && passwordEntry.equals("cashier1")) {
            try {
                getData.username = usernameEntry;
                getData.role = "cashier";
                goRelatedPage("cashier", event);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else if (usernameEntry.equals("admin1") && passwordEntry.equals("admin1")) {
            try {
                getData.username = usernameEntry;
                getData.role = "admin";
                goRelatedPage("admin", event);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else {
            //make a pop up element 
            System.out.println("Invalid userame or password"); 
        }
    }
    
    //Employee currentEmployee
    public void goRelatedPage(String currentUserRole, MouseEvent event) throws IOException {
        switch (currentUserRole) {
            case "manager": {
                System.out.println("You are a manager");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/manager/managerScene.fxml"));
                ManagerSceneController controller = loader.getController();
                //controller.setCurrentEmployee(currentEmployee);
                Parent root = loader.load();
                Scene scene = new Scene(root, 1200, 768);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

                break;
            }
            case "cashier": {
                System.out.println("You are a cashier");
                //loginButton.getScene().getWindow().hide();
                // changeScene("fxml/cashier/cashierScene.fxml");

                //changeScene("fxml/cashier/cashierScene.fxml", event);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/cashier/cashierScene.fxml"));
                CashierSceneController controller = loader.getController();
                //controller.setCurrentEmployee(currentEmployee);
                Parent root = loader.load();
                Scene scene = new Scene(root, 1200, 768);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                break;
            }
            case "admin": {
                System.out.println("You are an admin");
                
                //loginButton.getScene().getWindow().hide();

                // changeScene("fxml/admin/adminScene.fxml", event);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/newAdminMenu.fxml"));
                AdminMenuController controller = loader.getController();
                //controller.setCurrentEmployee(currentEmployee);
                Parent root = loader.load();
                Scene scene = new Scene(root, 1200, 768);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                break;
            }
            default: {
                System.out.println("No such role");
            }
        }
    }

    /* change after db */
    // public void goRelatedPage(User currentUser, MouseEvent event) throws IOException {
    //     currentUser.displayNonProfile();
    //     switch (currentUser.getRole()) {
    //         case "manager": {
    //             System.out.println("You are a manager");
                
    //             loginButton.getScene().getWindow().hide();

    //             // changeScene("manager.fxml");

    //             FXMLLoader loader = new FXMLLoader(getClass().getResource("manager.fxml"));
        
    //             Parent root = loader.load();
    //             ManagerController controller = loader.getController();
    //             stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    //             controller.setStage(stage);
               
    //             Scene scene = new Scene(root);
    //             stage.setTitle("Manager Page");
    //             stage.setScene(scene);
    //             stage.show();

    //         }
    //         case "cashier": {
    //             System.out.println("You are a cashier");
                
    //             loginButton.getScene().getWindow().hide();
    //             changeScene("cashier.fxml");
                
    //         }
    //         case "admin":{
    //             System.out.println("You are an admin");
                
    //             loginButton.getScene().getWindow().hide();

    //             changeScene("admin.fxml");
    //         }
    //         default: {
    //             System.out.println("No such role");
    //         }
    //     }
    // }
}