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

    private void login(MouseEvent event) {
        String usernameEntry = usernameField.getText().trim();
        String passwordEntry = passwordField.getText().trim();
        System.out.println(usernameEntry + " " + passwordEntry);

        //get employee based on username and passwd filters that matched with the user entries 
        EmployeesDao employeeDatabase = new EmployeesDao();
        List<Employee> matchedEmployees = employeeDatabase.getListByFilter("username, passwd", usernameEntry, passwordEntry);
        if (!matchedEmployees.isEmpty()) {
            currentEmployee = matchedEmployees.get(0);
            currentUser.setUsername(usernameEntry);
            currentEmployee.displayNonProfile();

            try {
                goRelatedPage(currentEmployee.getRole(), event);
            }
            catch (IOException e){
                e.printStackTrace();
            }
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
}