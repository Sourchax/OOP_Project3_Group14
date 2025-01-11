package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FinanceInformationController {

    @FXML
    private TextField discountRateField;

    @FXML
    private TextField ticketPriceField;

    @FXML
    private Label totalRevenue;

    @FXML
    private Label totalTax;


    @FXML
    private Button editButton;

    //database instance
    
    @FXML
    private void initialize() {
        editButton.setOnAction(event -> edit(event));
        //set total revenue total tax
    }

    @FXML
    void edit(ActionEvent event) {
        Integer discountRate = Integer.parseInt(discountRateField.getText());
        Float ticketPrice = Float.parseFloat(ticketPriceField.getText());

        if (discountRate != 0 || ticketPrice != 0 || discountRateField.getText().isEmpty() || ticketPriceField.getText().isEmpty()) {
            System.out.println("Please fill all fields without zero"); //make this a warning like an alert
            return;
        }

        try {
            System.out.println("db update");
            //employeesDataBase.updateById(selectedEmployee.getId(), "name, surname, username, role, passwd", name, surname, username, role, password);
            
        } catch (Exception e) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAA");
        }
    }
}