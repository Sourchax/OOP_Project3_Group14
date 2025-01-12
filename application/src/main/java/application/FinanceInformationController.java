package application;
import java.util.List;

import dataAccess.InvoicesDao;
import dataAccess.PriceModifiersDao;
import entities.Employee;
import entities.Invoice;
import entities.PriceModifier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    private PriceModifiersDao database;
    
    private List<PriceModifier> pModifiers;

    private InvoicesDao invoicesDao;
    
    @FXML
    private void initialize() {
        editButton.setOnAction(event -> edit(event));

        invoicesDao = new InvoicesDao();
        List<Invoice> invoices = invoicesDao.getList();
        double total = 0.0;
        for (int i = 0; i < invoices.size(); i++) {
            total += invoices.get(i).getTotalSpend();
        }
        totalRevenue.setText(String.valueOf(total));
        
        
        database = new PriceModifiersDao();

        discountRateField.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            String newText = change.getControlNewText();
        
            if (newText.matches("\\d{0,2}")) {
                return change; 
            }
            return null;
        }));

        pModifiers = database.getList();
        discountRateField.setText(String.valueOf(pModifiers.get(1).getVal()));
        ticketPriceField.setText(String.valueOf(pModifiers.get(0).getVal()));
    }

    @FXML
    void edit(ActionEvent event) {
        if(discountRateField.getText() == null  || discountRateField.getText().trim() == "" || ticketPriceField.getText() == null || ticketPriceField.getText().trim() == ""){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid number!");
            alert.setHeaderText("Current Process Canceled");
            alert.setContentText("Please enter a valid number!");
            alert.showAndWait();
            return;
        }
        Integer discountRate = Integer.parseInt(discountRateField.getText());
        Float ticketPrice = Float.parseFloat(ticketPriceField.getText());

        try {
            database.updateById(1, "val",ticketPrice);
            database.updateById(2, "val", discountRate);
            
        } catch (Exception e) {
        }
    }
}