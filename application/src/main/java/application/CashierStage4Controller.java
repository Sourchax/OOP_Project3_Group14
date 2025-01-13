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

/**
 * The {@code CashierStage4Controller} class manages the cashier interface, handling interactions
 * with the product grid, ticket grid, and user inputs. It includes functionalities for 
 * managing discounts, products, and user details for invoice generation.
 */
public class CashierStage4Controller {

    // FXML Components
    /**
     * TextField for the customer's first name.
     */
    @FXML
    private TextField nameField;

    /**
     * TextField for the customer's last name.
     */
    @FXML
    private TextField surnameField;

    /**
     * CheckBox for applying a discount.
     */
    @FXML
    private CheckBox discountCheck;

    /**
     * Button to decrease the discount amount.
     */
    @FXML
    private Button minusDiscount;

    /**
     * Label showing the current discount amount.
     */
    @FXML
    private Label discountAmount;

    /**
     * Label displaying the selected product's name.
     */
    @FXML
    private Label prodNameField;

    /**
     * Button to add the selected product to the cart.
     */
    @FXML
    private Button addProd;

    /**
     * Button to remove the selected product from the cart.
     */
    @FXML
    private Button removeProd;

    /**
     * Button to increase the discount amount.
     */
    @FXML
    private Button plusDiscount;

    /**
     * Button to apply the discount to the ticket.
     */
    @FXML
    private Button applyButton;

    /**
     * Button to navigate back to the previous screen.
     */
    @FXML
    private Button backButton;

    /**
     * Button to confirm and proceed to the invoice generation.
     */
    @FXML
    private Button confirmButton;

    /**
     * GridPane displaying the tickets purchased by the customer.
     */
    @FXML
    private GridPane ticketGrid;

    /**
     * GridPane displaying the available products for purchase.
     */
    @FXML
    private GridPane productGrid;

    // Fields
    private List<Product> productList;
    private double ticketPrice;
    private Product selectedProduct;

    /**
     * Initializes the controller. Sets up the input fields, buttons, and grids.
     */
    @FXML
    private void initialize() {

        StaticSelection.selectedProducts = new HashMap<>();
        cashierParent.getParent().productAdded();

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
    

        /**
        * Validates text input for name and surname fields.
        * 
        * @param change the text change to validate
        * @return the validated change, or null if invalid
        */
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

    /**
     * Decreases the discount amount, if possible.
     */
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


    /**
     * Increases the discount amount, ensuring it doesn't exceed the number of seats.
     */
    private void increaseDiscount() {
        Integer discountInt = Integer.parseInt(discountAmount.getText());
        Integer seatNum = StaticSelection.staticSeatIndeces.size();
        if (discountInt != seatNum) {
            discountInt++;
            discountAmount.setText(String.valueOf(discountInt));
            applyButton.setDisable(false);
        }
        
    }

    /**
     * Populates the product grid with available products.
     */
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

    /**
     * Populates the Ticket grid with available products.
     */
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

    /**
     * Proceeds to the next stage of the cashier process if valid customer details are provided.
     * Displays an error alert if the name or surname fields are empty.
     */
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

    /**
     * Navigates back to the previous stage of the cashier process.
     * Resets the selected products and updates the parent scene to "seatPlanStage".
     */
    private void goBack() {
        cashierParent.getParent().ticketsAdded(-1, false);
        StaticSelection.selectedProducts = new HashMap<>();
        cashierParent.getParent().productAdded();
        cashierParent.getParent().handleScenes("seatPlanStage"); 
    }

    /**
     * Applies the discount specified in the discount amount field to the tickets.
     * Updates the parent component with the discount information.
     */
    private void handleApply(){
        Integer discountNum = Integer.parseInt(discountAmount.getText());

        cashierParent.getParent().ticketsAdded(discountNum, true);
    }


    /**
     * Updates the image based on the provided SQL Blob object.
     * 
     * @param imageBlob the SQL Blob containing image data
     * @return an Image object if the Blob is valid; otherwise, returns null
     */
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

    /**
    * Adds the currently selected product to the cart.
    * Increments the product's count in the selection map and updates the parent component.
    */
    private void addProductToCart(){
        Integer a = StaticSelection.selectedProducts.get(selectedProduct);
        StaticSelection.selectedProducts.put(selectedProduct, ++a);

        cashierParent.getParent().productAdded();
        removeProd.setDisable(false);

    }

    /**
    * Updates the selected product based on the given index.
    * Updates the UI to reflect the selected product's details and enables or disables buttons
    * depending on the product's stock and selection count.
    * 
     * @param a the index of the selected product in the product list
    */
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

    /**
    * Removes the currently selected product from the cart.
    * Decrements the product's count in the selection map and updates the parent component.
    * Disables the remove button if the product count reaches zero.
    */
    private void removeProductFromCart(){
        Integer a = StaticSelection.selectedProducts.get(selectedProduct);
        StaticSelection.selectedProducts.put(selectedProduct, --a);
        if(StaticSelection.selectedProducts.get(selectedProduct) == 0){
            removeProd.setDisable(true);
        }
        cashierParent.getParent().productAdded();

    }
}


