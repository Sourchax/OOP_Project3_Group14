package application;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataAccess.EmployeesDao;
import dataAccess.ProductsDao;
import entities.Employee;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InventoryController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label productPriceLabel;

    @FXML
    private Label productQuantityLabel;

    @FXML
    private Label productTypeLabel;

    @FXML
    private GridPane productsGrid;

    @FXML
    private ScrollPane productsScrollPane;

    @FXML
    private VBox sideBar;

    @FXML
    private ComboBox<Integer> stockComboBox;

    @FXML
    private Button addButton;

    @FXML
    private Button buyButton;

    @FXML
    private HBox cashierPrice;

    @FXML
    private Button editButton;

    @FXML
    private HBox managerPrice;

    @FXML
    private TextField productPriceField;



    public Product selectedProduct;
    
    private ProductsDao productsDatabase;

    private List<Product> products =  new ArrayList<>();

    private CardClickListener cardClickListener;




    private ObservableList<Integer> stockIncreaseValues = FXCollections.observableArrayList(1, 10, 25, 50, 100);
    
    @FXML
    private void initialize(){
        if(currentUser.getRole().equals("manager")) {
            addButton.setVisible(true);
            addButton.setDisable(false);
            buyButton.setVisible(false);
            buyButton.setDisable(true);

            addButton.setOnAction(event -> increaseStock());

            stockComboBox.setVisible(true);
            stockComboBox.setDisable(false);
            stockComboBox.setItems(stockIncreaseValues);
            stockComboBox.setValue(1);

            cashierPrice.setVisible(false);
            cashierPrice.setDisable(true);

            managerPrice.setVisible(true);
            managerPrice.setDisable(false);

            productsScrollPane.setPrefHeight(500);
            productsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }
        else if (currentUser.getRole().equals("cashier")) {
            buyButton.setVisible(true);
            buyButton.setDisable(false);

            addButton.setVisible(false);
            addButton.setDisable(true);

            stockComboBox.setVisible(false);
            stockComboBox.setDisable(true);

            cashierPrice.setVisible(true);
            cashierPrice.setDisable(false);

            managerPrice.setVisible(false);
            managerPrice.setDisable(true);

            productsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            productsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            productsScrollPane.setPrefHeight(250);

            buyButton.setOnAction(event -> buyProduct(event));

            
        }
        initGrid();
    }

    private void initGrid() {
        productsDatabase = new ProductsDao();
        this.products = productsDatabase.getList();
        for (int i = 0; i < products.size(); i++) {
            System.out.println(products.get(i).getName());
            System.out.println(products.get(i).getStock());
        }

        // select the first product if there is any product
        if (products.size() > 0) {
            selectedProduct = products.get(0);
            setChosenProduct(products.get(0));
            cardClickListener = new CardClickListener() {
                @Override
                public void clickListener(Product product) {
                    selectedProduct = product;
                    setChosenProduct(product);
                }
            };
        }

        //  number of gridColumnNumber for gridPane
        int maxNumber = 3;
        int columnIndex = 0;
        int rowIndex = 0;

        try {
            // Load fxml for each product
            for (int i = 0; i < products.size(); i++) {
                URL productFXMLUrl = ProductController.class.getResource("fxml/manager/product.fxml");
                System.out.println("Resource URL: " + productFXMLUrl);

                if (productFXMLUrl != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(productFXMLUrl);
                    AnchorPane anchorPane = fxmlLoader.load();  

                    // product controller
                    ProductController productController = fxmlLoader.getController();
                    productController.setProductData(products.get(i), cardClickListener);

                    // reset the column index when it becomes the maximumGridColumnNumber
                    if (managerPrice.isVisible()) {
                        if (columnIndex == maxNumber) {  
                            columnIndex = 0;
                            rowIndex++;
                        }
                        productsGrid.add(anchorPane, columnIndex++, rowIndex);
                    }
                    //cashier part
                    else {
                        if (rowIndex == maxNumber) {  
                            rowIndex = 0;
                            columnIndex++;
                        }
                        productsGrid.add(anchorPane, columnIndex++, rowIndex);
                    }

                    // add the loaded fxml to the grid

                    
                } else {
                    System.out.println("No such fxml");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setChosenProduct(Product product) {
        productNameLabel.setText(product.getName());
        updateImage(product.getImage());
        productPriceField.setText(String.valueOf(product.getPrice()));
        productPriceLabel.setText(String.valueOf(product.getPrice()));
        productQuantityLabel.setText(String.valueOf(product.getStock()));
        productTypeLabel.setText(product.getType());
    }

    private void updateImage(Blob imageBlob) {
        // Update the ImageView with a new image
        if(imageBlob == null){
            productImage.setImage(null);
            return;
        }
        InputStream inputStream;
        try {
            inputStream = imageBlob.getBinaryStream();
            Image image = new Image(inputStream);
            productImage.setImage(image);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void increaseStock() {
        try {
            int currentStock = selectedProduct.getStock();
            selectedProduct.setStock(currentStock + stockComboBox.getValue());
            setChosenProduct(selectedProduct);
            productsDatabase.updateById(selectedProduct.getId(), "stock", currentStock + stockComboBox.getValue());
        } catch (Exception e) {
            System.out.println("Error updating stock: " + e.getMessage());
        }
        
    }

    @FXML
    void buyProduct(ActionEvent event) {
        int currentStock = selectedProduct.getStock();
        if (selectedProduct.getStock() > 0) {
            selectedProduct.setStock(currentStock - 1);
            setChosenProduct(selectedProduct);
            productsDatabase.updateById(selectedProduct.getId(), "stock", currentStock - 1);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Out of stock");
            alert.showAndWait();
            return;
        }
    }


    @FXML
    void editPrice(ActionEvent event) {

        String priceInput = productPriceField.getText().trim();
        priceInput = priceInput.replace(',', '.');
    
        // check for valid input
        if (!priceInput.matches("^[+]?\\d*(\\.\\d+)?$")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Enter a valid number");
            alert.showAndWait();
            return;
        }

        try {
            float newPrice = Float.parseFloat(priceInput);
            if (newPrice < 0 || priceInput.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Enter a valid number");
                alert.showAndWait();
                return;
            }

            Product copySelectedProduct = selectedProduct;
            selectedProduct.setPrice(newPrice);
            setChosenProduct(selectedProduct);
            productsDatabase.updateById(selectedProduct.getId(), "price", selectedProduct.getPrice());
            initGrid();
            setChosenProduct(copySelectedProduct);
            
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText("Enter a valid number with valid format.");
            alert.showAndWait();
        }

    }
}