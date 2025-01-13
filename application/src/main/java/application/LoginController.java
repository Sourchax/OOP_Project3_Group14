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

/**
 * LoginController for loading fxml based on role
 */
public class LoginController {

    /**
     * The PasswordField for entering the user's password.
     * This field is used for inputting the password during login.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * The TextField for entering the user's username.
     * This field is used for inputting the username during login.
     */
    @FXML
    private TextField usernameField;

    /**
     * The Label displaying the role of the current user.
     * This label is used to show the role (e.g., manager, cashier) of the user after login.
     */
    @FXML
    private Label roleLabel;

    /**
     * The Label displaying the username text.
     * This label is used to provide a description for the username field.
     */
    @FXML
    private Label usernameLabel;

    /**
     * The Button to initiate the login process.
     * This button triggers the login process when clicked by the user.
     */
    @FXML
    private Button loginButton;

    /**
     * The BorderPane layout that holds the main scene.
     * This is the main container that manages the layout of the login scene.
     */
    @FXML
    private BorderPane mainPane;

    /**
     * The current Employee object representing the logged-in user.
     * This object holds the details of the currently logged-in employee, including their role.
     */
    private Employee currentEmployee;

    /**
     * The Label that displays the login error message.
     * This label is used to show an error message when the login credentials are invalid.
     */
    @FXML
    private Label loginError;
    
    /**
     * set login error message as invinsible
     */
    @FXML
    private void initialize(){
        loginError.setVisible(false);
    }

    /**
     * login click
     * @param event
     */
    @FXML
    void onClickLogin(MouseEvent event) {
        login(event);
    }

    /**
     * exit app
     * @param event
     */
    @FXML
    void onExit(MouseEvent event) {
        System.exit(0);
    }

    /**
     * login to application set currentUser fields  
     * @param event
     */
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

    /**
     * Create stage based on role
     * @param path of fxml
     * @param event mouse event
     */
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
    

    /**
     * load fxml based on current role
     * @param currentUserRole
     * @param event
     */
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