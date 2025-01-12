package application;

import java.io.InputStream;
import java.lang.Thread.State;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import dataAccess.PriceModifiersDao;
import dataAccess.ProductsDao;
import entities.PriceModifier;
import entities.Product;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CashierStage4Controller {

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private CheckBox discountCheck;

    @FXML
    private Button minusDiscount;

    @FXML
    private Label discountAmount;

    @FXML
    private Label prodNameField;

    @FXML
    private Button addProd;

    @FXML
    private Button removeProd;

    @FXML
    private Button plusDiscount;

    @FXML
    private Button applyButton;

    @FXML
    private Button backButton;

    @FXML
    private Button confirmButton;

    @FXML
    private GridPane ticketGrid;

    @FXML
    private GridPane productGrid;

    private List<Product> productList;

    private double ticketPrice;

    private double discountRatio;

    private double productTax;

    private double ticketTax;

    private Product selectedProduct;

    @FXML
    private void initialize() {

        StaticSelection.selectedProducts = new HashMap<>();

        plusDiscount.setDisable(true);
        minusDiscount.setDisable(true);
        applyButton.setDisable(true);
        removeProd.setDisable(true);
        addProd.setDisable(true);

        nameField.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            String newText = change.getControlNewText();
        
            if (newText.matches( "^[A-Za-zÇçĞğİıÖöŞşÜü]{0,20}$")) {
                return change; 
            }
            return null;
        }));
    

        surnameField.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            String newText = change.getControlNewText();
        
            if (newText.matches( "^[A-Za-zÇçĞğİıÖöŞşÜü]{0,20}$")) {
                return change; 
            }
            return null;
        }));

        ProductsDao products = new ProductsDao();

        PriceModifiersDao a = new PriceModifiersDao();

        List<PriceModifier> pfs = a.getList();

        ticketPrice = pfs.get(0).getVal();
        discountRatio = pfs.get(1).getVal();
        productTax = pfs.get(2).getVal();
        ticketTax = pfs.get(3).getVal();

        productList = products.getList();

        minusDiscount.setOnAction(event -> decreaseDiscount());
        plusDiscount.setOnAction(event -> increaseDiscount());
        confirmButton.setOnAction(event -> proceed());
        backButton.setOnAction(event -> goBack());
        applyButton.setOnAction(event -> handleApply());
        discountAmount.setText(String.valueOf(0));

        addProd.setOnAction(event -> addProductToCart());
        removeProd.setOnAction(event -> removeProductFromCart());

        populateTicketGrid();
        populateProductGrid();

        discountCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                plusDiscount.setDisable(false);
                minusDiscount.setDisable(false);
            } else {
                discountAmount.setText(String.valueOf(0));
                plusDiscount.setDisable(true);
                minusDiscount.setDisable(true);
                applyButton.setDisable(true);
            }
        });
    }

    private void decreaseDiscount() {
        Integer discountInt = Integer.parseInt(discountAmount.getText());
        if (discountInt != 0) {
            discountInt--;
            discountAmount.setText(String.valueOf(discountInt));
        }
        if(discountInt != 0){
            applyButton.setDisable(false);
        }
        else{
            applyButton.setDisable(true);
        }
    }

    private void increaseDiscount() {
        Integer discountInt = Integer.parseInt(discountAmount.getText());
        Integer seatNum = StaticSelection.staticSeatIndeces.size();
        if (discountInt != seatNum) {
            discountInt++;
            discountAmount.setText(String.valueOf(discountInt));
            applyButton.setDisable(false);
        }
        
    }


    private void populateProductGrid() {
        productGrid.getChildren().clear();
        productGrid.setHgap(10);
        productGrid.setVgap(10);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(productGrid);
        scrollPane.setFitToWidth(true);

        scrollPane.setStyle("-fx-background-color:rgb(183, 71, 228);");

        for (int i = 0; i < productList.size(); i++) {

            Product product = productList.get(i);
            StaticSelection.selectedProducts.put(product, 0);
            String productName = product.getName();
            int productStock = product.getStock();
            double productPrice = product.getPrice();
            Image productImage = updateImage(product.getImage());

            // Create a VBox for each product
            VBox productBox = new VBox();
            productBox.setSpacing(10);
            productBox.setAlignment(Pos.CENTER);
            productBox.setStyle(
                "-fx-background-color:rgb(213, 230, 122); " +
                "-fx-border-color: #ccc; " +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5; " +
                "-fx-padding: 10;"
            );
            productBox.setPrefSize(150, 100); // Adjust size based on layout

            final int a = i;

            productBox.setOnMouseClicked((MouseEvent event) -> {
                System.out.println("Product clicked: " + productName);
            
                addToSelectedProduct(a);
            });
            
            ImageView productImageView = new ImageView(productImage);
            productImageView.setFitWidth(60);
            productImageView.setFitHeight(60);
            productImageView.setPreserveRatio(true);


            Label nameLabel = new Label(productName);
            nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");


            Label stockLabel = new Label("Stock: " + productStock);
            stockLabel.setStyle("-fx-font-size: 12px;");


            Label priceLabel = new Label("Price: ₺" + productPrice);
            priceLabel.setStyle("-fx-font-size: 12px;");


            productBox.getChildren().addAll(productImageView, nameLabel, stockLabel, priceLabel);

            productGrid.add(productBox, i, 0);
        }

    }

    private void populateTicketGrid() {
        ticketGrid.getChildren().clear();
        ticketGrid.setHgap(10);
        ticketGrid.setVgap(10);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(ticketGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #f9f9f9;");

        for (int i = 0; i < StaticSelection.staticSeatValues.size(); i++) {
            String seatNumber = StaticSelection.staticSeatValues.get(i);
            String movieName = StaticSelection.staticMovie.getName();

            VBox ticketBox = new VBox();
            ticketBox.setSpacing(5);
            ticketBox.setStyle(
                "-fx-background-color:rgb(213, 230, 122); " +
                "-fx-border-color: #ccc; " +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5; " +
                "-fx-padding: 10;"
            );
            ticketBox.setPrefSize(150, 70);

            Label movieLabel = new Label(movieName);
            movieLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Label seatLabel = new Label("Seat: " + seatNumber);
            seatLabel.setStyle("-fx-font-size: 12px;");

            Label priceLabel = new Label("Price: $" + ticketPrice);
            priceLabel.setStyle("-fx-font-size: 12px;");

            ticketBox.getChildren().addAll(movieLabel, seatLabel, priceLabel);

            ticketGrid.add(ticketBox, i, 0);
        }
    }

    private void proceed() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        if(name.equals(null) || name.equals("")|| surname.equals("") || surname.equals(null)){
            Alert alert = new Alert(AlertType.ERROR);
    
            alert.setTitle("Invalid name or surname!");
            alert.setHeaderText("Current Process Cancelled");
            alert.setContentText("Please enter customer's identification!");
            alert.showAndWait();
        }
        else{
            StaticSelection.customerName = name;
            StaticSelection.customerSurname = surname;
            cashierParent.getParent().handleScenes("invoice");
        }
    }

    private void goBack() {
        cashierParent.getParent().ticketsAdded(-1, false);
        StaticSelection.selectedProducts = new HashMap<>();
        cashierParent.getParent().productAdded();
        cashierParent.getParent().handleScenes("seatPlanStage"); 
    }

    private void handleApply(){
        Integer discountNum = Integer.parseInt(discountAmount.getText());

        cashierParent.getParent().ticketsAdded(discountNum, true);
    }

    private Image updateImage(Blob imageBlob) {

        if(imageBlob == null){
            return null;
        }
        InputStream inputStream;
        try {
            inputStream = imageBlob.getBinaryStream();
            Image image = new Image(inputStream);
            return image;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addProductToCart(){
        Integer a = StaticSelection.selectedProducts.get(selectedProduct);
        StaticSelection.selectedProducts.put(selectedProduct, ++a);

        cashierParent.getParent().productAdded();
        removeProd.setDisable(false);

    }

    private void addToSelectedProduct(int a){
        selectedProduct = productList.get(a);

        prodNameField.setText(selectedProduct.getName());
        if(selectedProduct.getStock() - StaticSelection.selectedProducts.get(selectedProduct) > 0)
            addProd.setDisable(false);
        if(StaticSelection.selectedProducts.get(selectedProduct) > 0){
            removeProd.setDisable(false);
        }
        else{
            removeProd.setDisable(true);
        }
    }

    private void removeProductFromCart(){
        Integer a = StaticSelection.selectedProducts.get(selectedProduct);
        StaticSelection.selectedProducts.put(selectedProduct, --a);
        if(StaticSelection.selectedProducts.get(selectedProduct) == 0){
            removeProd.setDisable(true);
        }
        cashierParent.getParent().productAdded();

    }
}


