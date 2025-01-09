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
    private VBox sideBar;

    @FXML
    private TableColumn<Employee, String> surnameColumn;

    @FXML
    private TableColumn<Employee, String> usernameColumn;

    private ObservableList<Employee> employeeData;

    private EmployeesDao employeesDataBase;

    private Employee selectedEmployee;

    @FXML
    private void initialize() {        
        editButton.setOnAction(event -> editEmployeeDetails());
        deleteButton.setOnAction(event -> fireEmployee());
        employeesDataBase = new EmployeesDao();
         /*Check logged in employee username show employees except that */
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadEmployeeData();

        employeesTableView.setOnMouseClicked(this::handleEmployeeSelection);

        selectedEmployee = employeeData.get(0);
        populateEmployeeDetails(selectedEmployee);
    }

    private void populateEmployeeDetails(Employee employee) {
        nameField.setText(employee.getName());
        surnameField.setText(employee.getSurname());
        usernameField.setText(employee.getUsername());
        roleField.setText(employee.getRole());
    }


    private void loadEmployeeData() {
        /*Check logged in employee username show employees except that */
        List<Employee> employees = employeesDataBase.getList();
        employeeData = FXCollections.observableArrayList(employees);
        employeesTableView.setItems(employeeData);
    }

    private void fireEmployee(){
        Employee selectedEmployee = employeesTableView.getSelectionModel().getSelectedItem();
        
        if (selectedEmployee != null) {

            /*Alert box check again*/
            // Alert alert = new Alert(AlertType.CONFIRMATION);
            // alert.setTitle("Confirm Deletion");
            // alert.setHeaderText("Are you sure you want to fire this employee?");

            // ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            // if (result == ButtonType.YES) {
            //     employeeData.remove(selectedEmployee);
            //     employeesDataBase.deleteById(selectedEmployee.getId());
            // }

            employeeData.remove(selectedEmployee);
            employeesDataBase.deleteById(selectedEmployee.getId());

        } else {
            System.out.println("Select an employee");
        }
    }

    private void editEmployeeDetails(){
        String name = nameField.getText();
        String surname = surnameField.getText();
        String username = usernameField.getText();
        String role = roleField.getText();

        if (name.isEmpty() || name.isEmpty() || name.isEmpty()) {
            System.out.println("Please fill all fields to update the employee"); //make this a warning like an alert
            return;
        }

        try {
            employeesDataBase.updateById(selectedEmployee.getId(), "name, surname, username, role", name, surname, username, role);
            
        } catch (Exception e) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAA");
        }
        
        loadEmployeeData();
    }

    @FXML
    private void handleEmployeeSelection(MouseEvent event) {
        int index = employeesTableView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            selectedEmployee = employeeData.get(index);
            populateEmployeeDetails(selectedEmployee);
        }
    }
}