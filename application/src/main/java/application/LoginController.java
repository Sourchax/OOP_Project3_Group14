package application;

import java.io.IOException;
import java.util.List;

import dataAccess.EmployeesDao;
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

    private Employee currentEmployee;

    @FXML
    private Label loginError;

    public void setMainPane(BorderPane borderPane) {
        this.mainPane = borderPane;
    }


    @FXML
    private void initialize(){
        loginError.setVisible(false);
    }

    @FXML
    void onClickLogin(MouseEvent event) {
        login(event);
    }

    @FXML
    void onExit(MouseEvent event) {
        System.exit(0);
    }

    private void login(MouseEvent event) {
        String usernameEntry = usernameField.getText().trim();
        String passwordEntry = passwordField.getText().trim();
        System.out.println(usernameEntry + " " + passwordEntry);
        loginError.setVisible(false);
        //get employee based on username and passwd filters that matched with the user entries 
        EmployeesDao employeeDatabase = new EmployeesDao();
        List<Employee> matchedEmployees = employeeDatabase.getListByFilter("username, passwd", usernameEntry, passwordEntry);
        if (!matchedEmployees.isEmpty()) {
            currentEmployee = matchedEmployees.get(0);
            currentUser.setUsername(usernameEntry);
            currentUser.setRole(currentEmployee.getRole());
            System.out.println(currentUser.getRole());
            currentEmployee.displayNonProfile();

            goRelatedPage(currentEmployee.getRole(), event);

        }
        else{
            loginError.setVisible(true);
        }
    }

    private void createStage(String path, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            ManagerSceneController controller = loader.getController();
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200, 768);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    //Employee currentEmployee
    public void goRelatedPage(String currentUserRole, MouseEvent event)  {
        switch (currentUserRole) {
            case "manager": {
                System.out.println("You are a manager");
                createStage(("fxml/manager/managerScene.fxml"), event);

                break;
            }
            case "cashier": {
                System.out.println("You are a cashier");
                createStage("fxml/cashier/cashierScene.fxml", event);

                break;
            }
            case "admin": {
                System.out.println("You are an admin");
                createStage("fxml/newAdminMenu.fxml", event);
                break;
            }
            default: {
                System.out.println("No such role");
            }
        }
    }
}