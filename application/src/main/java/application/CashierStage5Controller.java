package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import dataAccess.InvoicesDao;
import dataAccess.ProductsDao;
import dataAccess.SessionDao;
import entities.Invoice;
import entities.Product;

public class CashierStage5Controller {

    @FXML private Label movieName;
    @FXML private Label Hall;
    @FXML private Label sessionDate;
    @FXML private Label sessionTime;
    @FXML private Label transactionDate;
    @FXML private Label seats;
    @FXML private Label name;
    @FXML private Label surname;
    @FXML private Pane products;
    @FXML private Label ticketMovieName;
    @FXML private Label ticketHall;
    @FXML private Label ticketDate;
    @FXML private Label ticketTime;
    @FXML private Label ticketSeats;

    @FXML 
    private Button confirmButton;
    @FXML 
    private Button backButton;

    private ProductsDao database = new ProductsDao();

    private InvoicesDao invocies = new InvoicesDao();

    private SessionDao sessionDB = new SessionDao();

    private String cryptedData;

    @FXML
    public void initialize() {
        movieName.setText(StaticSelection.staticMovie.getName());
        Hall.setText(StaticSelection.staticSession.getHall());
        sessionDate.setText(StaticSelection.staticSession.getSessionDate().toString());
        sessionTime.setText(StaticSelection.staticSession.getSessionTime().toString());

        String ans = "";
        for(String a: StaticSelection.staticSeatValues){
            ans += a;
            ans += " ";
        }

        seats.setText(ans);
        transactionDate.setText(getCurrentDateTime());
        
        ticketMovieName.setText(StaticSelection.staticMovie.getName());
        ticketHall.setText(StaticSelection.staticSession.getHall());
        ticketDate.setText(StaticSelection.staticSession.getSessionDate().toString());
        ticketTime.setText(StaticSelection.staticSession.getSessionTime().toString());
        ticketSeats.setText(ans);
        products.getChildren().clear();

        VBox labelContainer = new VBox(10);  
        for(String ticket : StaticSelection.allTickets){
            addLabelToContainer(labelContainer, ticket);
        }
        
        
        if(StaticSelection.allProducts != null){
            for(String product : StaticSelection.allProducts){
                addLabelToContainer(labelContainer, product);
            }

        }
    
        products.getChildren().add(labelContainer);
    
        name.setText(StaticSelection.customerName);
        surname.setText(StaticSelection.customerSurname);

        confirmButton.setOnAction(event -> handleConfirm());
        backButton.setOnAction(event -> handleBack());

    }

    private void addLabelToContainer(VBox container, String text) {
        Label newLabel = new Label(text);
        container.getChildren().add(newLabel);
    }

    private void handleConfirm() {
        String htmlContent = createHTML();
        saveToFile("CinemaReceipt.html", htmlContent);

        Invoice x = new Invoice();

        x.setName(StaticSelection.customerName);
        x.setSurname(StaticSelection.customerSurname);
        x.setPurchaseDate(LocalDateTime.now());
        x.setSession(StaticSelection.staticSession.getId());
        x.setTotalSpend(StaticSelection.totalAmount);

        byte[] htmlBytes;
        try {
            htmlBytes = htmlContent.getBytes("UTF-8");
            Blob htmlF = new SerialBlob(htmlBytes);

            x.setPdf(htmlF);

            invocies.insert(x);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SerialException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        


        for(Product a : StaticSelection.selectedProducts.keySet()){
            int newStock = a.getStock()-StaticSelection.selectedProducts.get(a);
            database.updateById(a.getId(), "stock", newStock);
        }

        handleSeatSales();

        cashierParent.getParent().handleScenes("cashierStage1");
        StaticSelection.selectedProducts.clear();
        cashierParent.getParent().ticketsAdded(-1, false);
        cashierParent.getParent().productAdded();
    }

    private void handleBack() {
        cashierParent.getParent().handleScenes("cashierStage4");
    }

    private String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    private String createHTML(){
        StringBuilder html = new StringBuilder();

            html.append("<!DOCTYPE html>")
            .append("<!-- ").append(cryptedData()).append(" -->\n")
            .append("<html lang=\"en\">")
            .append("<head>")
            .append("<meta charset=\"UTF-8\">")
            .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
            .append("<title>Cinema Center Receipt</title>")
            .append("<style>")
            .append("body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; color: #333; }")
            .append(".receipt-container { width: 80%; margin: 20px auto; background-color: #ffffff; padding: 20px; border: 1px solid #ccc; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }")
            .append(".header, .footer { text-align: center; margin-bottom: 20px; }")
            .append(".header h1 { font-size: 2em; color: #2c3e50; }")
            .append(".receipt-details, .customer-details { margin-bottom: 20px; }")
            .append(".receipt-details table, .customer-details table { width: 100%; border-collapse: collapse; }")
            .append(".receipt-details th, .customer-details th, .receipt-details td, .customer-details td { border: 1px solid #ccc; padding: 8px; text-align: left; }")
            .append(".receipt-details th, .customer-details th { background-color: #2c3e50; color: white; }")
            .append(".receipt-details td { background-color: #f9f9f9; }")
            .append(".total { font-weight: bold; }")
            .append(".footer p { font-size: 0.8em; color: #7f8c8d; }")
            .append("</style>")
            .append("</head>")
            .append("<body>")
            .append("<div class=\"receipt-container\">")
            .append("<div class=\"header\">")
            .append("<h1>Group 14 Cinema Center</h1>")
            .append("<p>Receipt - Cinema Ticket Purchase</p>")
            .append("</div>")
            .append("<div class=\"customer-details\">")
            .append("<h3>Customer Details:</h3>")
            .append("<table>")
            .append("<tr><th>Name</th><td>").append(StaticSelection.customerName).append("</td></tr>")
            .append("<tr><th>Surname</th><td>").append(StaticSelection.customerSurname).append("</td></tr>")
            .append("<tr><th>Receipt Date</th><td>").append(getCurrentDateTime()).append("</td></tr>")
            .append("</table>")
            .append("</div>")
            .append("<div class=\"receipt-details\">")
            .append("<h3>Movie Details:</h3>")
            .append("<table>")
            .append("<tr><th>Movie Name</th><td>").append(StaticSelection.staticMovie.getName()).append("</td></tr>")
            .append("<tr><th>Hall</th><td>").append(StaticSelection.staticSession.getHall()).append("</td></tr>")
            .append("<tr><th>Session Date</th><td>").append(StaticSelection.staticSession.getSessionDate().toString()).append("</td></tr>")
            .append("<tr><th>Session Time</th><td>").append(StaticSelection.staticSession.getSessionTime().toString()).append("</td></tr>")
            .append("</table>")
            .append("</div>")
            .append("<div class=\"receipt-details\">")
            .append("<h3>Ticket Details:</h3>")
            .append("<table>");
        
        for (String ticket : StaticSelection.allTickets) {
            html.append("<tr>")
                .append("<td>").append(ticket).append("</td>")
                .append("</tr>");
        }
        html.append("<h3>Product Details:</h3>")
        .append("<table>");
        if(StaticSelection.allProducts != null){
            for (String product : StaticSelection.allProducts) {
                html.append("<tr>")
                    .append("<td>").append(product).append("</td>")
                    .append("</tr>");
            }
        }
        html.append("</table>")
            .append("</div>")
            .append("<div class=\"footer\">")
            .append("<p class=\"total\">Total Amount: $").append(String.format("%.2f", StaticSelection.totalAmount)).append("</p>")
            .append("</div>")
            .append("<div class=\"footer\">")
            .append("<p>*** This receipt is for informational purposes and has no financial value. ***</p>")
            .append("</div>")
            .append("</div>")
            .append("</body>")
            .append("</html>");

        return html.toString();
    }

    public static void saveToFile(String filename, String content) {
        try {
            File file = new File(filename);
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(content);
            writer.close();
            System.out.println("Receipt saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving the receipt: " + e.getMessage());
        }
    }

    private void handleSeatSales(){
        long ans = 0;
        for(Integer i: StaticSelection.staticSeatIndeces){
            ans += Math.pow(2,i);
        }
        ans += StaticSelection.staticSession.getSeats();
        sessionDB.updateById(StaticSelection.staticSession.getId(), "seats", ans);
    }

    private String cryptedData(){
        String ans = "";

        for(Integer a: StaticSelection.staticSeatIndeces){
            ans+= a.toString();
            ans+=",";
        }
        ans+= "a";
        if(StaticSelection.selectedProducts.keySet() != null){
            for(Product a: StaticSelection.selectedProducts.keySet()){
                if(StaticSelection.selectedProducts.get(a) != 0){
                    
                    ans+= String.valueOf(a.getId());
                    ans+= ",";
                    ans+= StaticSelection.selectedProducts.get(a);
                    ans+=",";

                }
            }
        }
        ans+="b";
        return ans;
    }

}
