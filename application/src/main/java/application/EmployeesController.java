package application;

import java.util.List;

import dataAccess.EmployeesDao;
import entities.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class EmployeesController {
    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField roleField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private VBox chosenProductCard;

    @FXML
    private Button editButton;

    @FXML
    private TableView<Employee> employeesTableView;

    @FXML
    private TableColumn<Employee, Integer> idColumn;

    @FXML
    private TableColumn<Employee, String> mailColumn;

    @FXML
    private BorderPane mainPane;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> phoneColumn;

    @FXML
    private TableColumn<Employee, String> roleColumn;

    @FXML
    private TableColumn<Employee, String> passwordColumn;

    @FXML
    private VBox sideBar;

    @FXML
    private TableColumn<Employee, String> surnameColumn;

    @FXML
    private TableColumn<Employee, String> usernameColumn;

    @FXML
    private ComboBox<String> roleComboBox;

    private ObservableList<Employee> employeeData;

    private EmployeesDao employeesDataBase;

    private Employee selectedEmployee;

    private ObservableList<String> roles = FXCollections.observableArrayList("manager", "cashier", "admin");
    
    /**
     * Regular expression for validating names.
     */
    private static final String Regex_NAME = "^[A-Za-zÇçĞğİıÖöŞşÜü]{2,20}(?: [A-Za-zÇçĞğİıÖöŞşÜü]{2,20})*$";

    /**
     * Regular expression for validating surnames.
     */
    private static final String Regex_SURNAME = "^[A-Za-zÇçĞğİıÖöŞşÜü]{2,20}(?: [A-Za-zÇçĞğİıÖöŞşÜü]{2,20})*$";

    /**
     * Regular expression for validating usernames.
     */
    private static final String Regex_USERNAME = "^[A-Za-zÇçĞğİıÖöŞşÜü0-9._-]{3,20}$";

    /**
     * Regular expression for validating passwords.
     */

    private static final String Regex_PASSWORD = "^(?=.*[a-zA-Z])(?=.*\\d).{6,}$";

    /**
     * 
     */
    @FXML
    private void initialize() {        
        editButton.setOnAction(event -> editEmployeeDetails());
        deleteButton.setOnAction(event -> fireEmployee());
        roleComboBox.setItems(roles);
        employeesDataBase = new EmployeesDao();
         /*Check logged in employee username show employees except that */
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("passwd"));

        loadEmployeeData();

        employeesTableView.setOnMouseClicked(this::handleEmployeeSelection);

        selectedEmployee = employeeData.get(0);
        populateEmployeeDetails(selectedEmployee);
    }

    /**
     * Takes employee as parameter and sets fields based on the employee values
     * @param employee
     */
    private void populateEmployeeDetails(Employee employee) {
        nameField.setText(employee.getName());
        surnameField.setText(employee.getSurname());
        usernameField.setText(employee.getUsername());
        passwordField.setText(employee.getPasswd());
        roleComboBox.setValue(employee.getRole());
    }


    private void loadEmployeeData() {
        List<Employee> employees = employeesDataBase.getList();
    
        if (employees == null || employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
    
        // remove current Employee
        employees.removeIf(employee -> employee.getUsername().equals(currentUser.getUsername()));
    
        employeeData = FXCollections.observableArrayList(employees);
        employeesTableView.setItems(employeeData);
    }
    

    private void fireEmployee(){
        Employee selectedEmployee = employeesTableView.getSelectionModel().getSelectedItem();
        
        if (selectedEmployee != null) {

            /*Alert box check again*/
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to fire this employee?");

            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.YES) {
                employeeData.remove(selectedEmployee);
                employeesDataBase.deleteById(selectedEmployee.getId());
            }

            employeeData.remove(selectedEmployee);
            employeesDataBase.deleteById(selectedEmployee.getId());

        } else {
            System.out.println("Select an employee");
        }

        resetTextFields();
    }

    private void editEmployeeDetails(){
        String name = nameField.getText();
        String surname = surnameField.getText();
        String username = usernameField.getText();
        String role = roleComboBox.getValue();
        String password = passwordField.getText();
        int id = selectedEmployee.getId();
        if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || role.isEmpty()) {
            
            showAlert(Alert.AlertType.WARNING, "Warning", "Fill all the fields");
            return;
        }
        else if (!name.matches(Regex_NAME) || !surname.matches(Regex_SURNAME) || !username.matches(Regex_USERNAME))
        {
            showAlert(Alert.AlertType.WARNING, "Warning", "Enter valid values");
            return;
        }
        else if (!password.matches(Regex_PASSWORD)) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Password should contain at least one character and digit.\n And it should be at least 6 character long.");
            return;
        }

        try {
            //check if the username already exists
            boolean isValid = true;
            List<Employee> matchedEmployees = employeesDataBase.getListByFilter("username", username);
            System.out.println(matchedEmployees.size());
            if (!matchedEmployees.isEmpty()) {
                isValid = false;
            }

            //also checks if it is username of the selected employee
            if(isValid == false) {
                if (username.equals(selectedEmployee.getUsername()))
                    isValid = true;
            }

            if (isValid) {
                employeesDataBase.updateById(id, "name, surname, username, role, passwd", name, surname, username, role, password);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null); // No header text
                alert.setContentText("Already exist username try another one");
                alert.showAndWait();
            }
        
        } catch (Exception e) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAA");
        }
        
        loadEmployeeData();
        resetTextFields();
    }

    @FXML
    private void handleEmployeeSelection(MouseEvent event) {
        int index = employeesTableView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            selectedEmployee = employeeData.get(index);
            populateEmployeeDetails(selectedEmployee);
        }
    }

    @FXML
    void hireEmployee(MouseEvent event) {
        String name = nameField.getText();
        String username = usernameField.getText();
        String surname = surnameField.getText();
        String role = roleComboBox.getValue();
        String password = passwordField.getText();

        //check valid case
        if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || role.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Fill all the fields");
            resetTextFields();
            return;
        }
        else if (!name.matches(Regex_NAME) || !surname.matches(Regex_SURNAME) || !username.matches(Regex_USERNAME))
        {
            showAlert(Alert.AlertType.WARNING, "Warning", "Enter valid values");
            resetTextFields();
            return;
        }
        else if (!password.matches(Regex_PASSWORD)) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Password should contain at least one character and digit and 6 character long.");
            resetTextFields();
            return;
        }

        List<Employee> matchedEmployees = employeesDataBase.getListByFilter("username", username);
        System.out.println(matchedEmployees.size());
        if (!matchedEmployees.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Employee Hired", "Already exist username try another one");
            resetTextFields();
            return;
        }

        // if employee not exists insert new employee to db
        Employee hiredEmployee = new Employee(name, surname, username, password, role);
        employeesDataBase.insert(hiredEmployee);
        loadEmployeeData();
        resetTextFields();
    }

    private void resetTextFields() {
        nameField.setText("");
        usernameField.setText("");
        surnameField.setText("");
        roleComboBox.setValue("");
        passwordField.setText("");
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}