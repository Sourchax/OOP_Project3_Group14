package application;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataAccess.InvoicesDao;
import dataAccess.ProductsDao;
import dataAccess.SessionDao;
import entities.Invoice;
import entities.Product;
import entities.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class CancelletionsController {


    @FXML
    private Button showReceipt;

    @FXML
    private Button confirmCancel;

    @FXML
    private Button goBack;

    @FXML
    private TableView<Invoice> invoiceTable;    

    @FXML
    private AnchorPane printReceipe;

    @FXML
    private TextField researchBar;

    @FXML
    private DatePicker selectDate;

    private List<Integer> seats;

    private List<Integer> products;

    private InvoicesDao invoicesDao = new InvoicesDao();
    private ObservableList<Invoice> invoiceList = FXCollections.observableArrayList();

    @FXML
    public void initialize() 
    {
        TableColumn<Invoice, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Invoice, String> surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Invoice, LocalDate> dateColumn = new TableColumn<>("Purchase Date");
        dateColumn.setCellValueFactory(invoice -> 
        {
            LocalDateTime purchaseDate = invoice.getValue().getPurchaseDate();
            return new javafx.beans.property.SimpleObjectProperty<>(purchaseDate.toLocalDate());
        });

        invoiceTable.getColumns().add(nameColumn);
        invoiceTable.getColumns().add(surnameColumn);
        invoiceTable.getColumns().add(dateColumn);

        loadInvoices();
    }

    // Function to handle "Show Receipt" action
    @FXML
    private void showReceipt() {

       /*  Invoice selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();

        try (InputStream inputStream = selectedInvoice.getPdf().getBinaryStream()) {
            File tempFile = File.createTempFile("invoice-",".html");
            tempFile.deleteOnExit();
            
            try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            openInBrowser(tempFile);
        } catch (SQLException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */

    }

    private void loadInvoices() 
    {
        List<Invoice> invoices = invoicesDao.getList();
        invoiceList.setAll(invoices);
        invoiceTable.setItems(invoiceList);
    }

    @FXML
    void searchInvoice(ActionEvent event) 
    {
        String keyword = researchBar.getText().toLowerCase();
        if (keyword.isEmpty()) 
        {
            loadInvoices(); 
            return;
        }

        ObservableList<Invoice> filteredList = invoiceList.filtered(invoice->invoice.getName().toLowerCase().contains(keyword) || invoice.getSurname().toLowerCase().contains(keyword));
        invoiceTable.setItems(filteredList);
    }

    @FXML
    private void filterByDate(ActionEvent event) 
    {
        LocalDate selectedDate = selectDate.getValue();
        if (selectedDate == null) 
        {
            loadInvoices();
            return;
        }

        ObservableList<Invoice> filteredList = invoiceList.filtered(invoice->selectedDate.equals(invoice.getPurchaseDate().toLocalDate()));
        invoiceTable.setItems(filteredList);
    }

    @FXML
    private void confirmCancel() 
    {
        Invoice selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();
        if (selectedInvoice == null) 
        {
            showError("Please select an invoice to cancel.");
            return;
        }

        
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancellation");
        alert.setHeaderText("Are you sure you want to cancel this invoice?");
        alert.setContentText("This action cannot be undone.");
        
        alert.showAndWait().ifPresent(response -> 
        {
            if (response == ButtonType.OK) 
            {
                decrypt(selectedInvoice.getPdf());
                SessionDao dbS = new SessionDao();
                Session s = dbS.getByFilter("id",selectedInvoice.getSession());
                long a = s.getSeats();
                for(int i : seats){
                    a -= Math.pow(2,i);                
                }
                dbS.updateById(selectedInvoice.getSession(), "seats", a);

                if(products != null && products.size() > 0){
                    ProductsDao pdS = new ProductsDao();
                    for(int i = 0; i<products.size(); i+=2){
                        Product p = pdS.getByFilter("id",products.get(i));
                        System.out.println(p.getName());
                        p.setStock(p.getStock()+products.get(i+1));
                        System.out.println(p.getStock());
                        pdS.updateById(p.getId(), "stock", p.getStock());
                    }
                }

                invoicesDao.deleteById(selectedInvoice.getId());
                invoiceList.remove(selectedInvoice);
                showInfo("Invoice successfully cancelled.");
            }
        });
    }
    private void showError(String message) 
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) 
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void openInBrowser(File file) {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            String command = "";
            if (os.contains("win")) {
                command = "start " + file.getAbsolutePath();
            } else if (os.contains("mac")) {
                command = "open " + file.getAbsolutePath();
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                command = "xdg-open " + file.getAbsolutePath();
            }

            // Execute the command to open the file in the browser
            if (!command.isEmpty()) {
                Process process = Runtime.getRuntime().exec(command);
                process.waitFor();
            } else {
                System.out.println("Unsupported OS");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error opening the file: " + e.getMessage());
        }
    }

    private void decrypt(Blob data){
        try (InputStream inputStream = data.getBinaryStream()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            
            inputStream.close();

            String comment = outputStream.toString("UTF-8");
            int i = 18;
            String seatData ="";
            String productData = "";
            while (comment.charAt(i) != 'a') {
                seatData+=comment.charAt(i++);
            }
            i++;
            Pattern pattern = Pattern.compile("-?\\d+");
            Matcher matcher = pattern.matcher(seatData);

            seats = new ArrayList<>();
            
            while (matcher.find()) {
                seats.add(Integer.parseInt(matcher.group()));
            }

            while(comment.charAt(i) != 'b'){
                productData+=comment.charAt(i++);
            }


            products = new ArrayList<>();
            String[] parts = productData.split(",");
            
            for(String part: parts)
                System.out.println(part);

            for (int j = 0; j < parts.length; j++) {
                products.add(Integer.parseInt(parts[j].trim())); // Trim in case there are spaces
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}