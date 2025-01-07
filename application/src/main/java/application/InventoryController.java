package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entities.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class InventoryController implements Initializable{
    @FXML
    private void initialize(){
        System.out.println("initial manager inventory");
    }

    @FXML
    private VBox chosenProductCard;

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
    private GridPane productsGrid;

    @FXML
    private ScrollPane productsScrollPane;

    @FXML
    private VBox sideBar;

    private List<Product> products = new ArrayList<>();
    private Image image;
    //private MyListener myListener;

    private Product assignProduct(String name, float price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        //product.setImgSrc("/img/kiwi.png");
        return product;
    }

    private List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        String[] names = {"toy", "popcorn"};
        Float[] prices = { (float) 12.9, (float) 4.0};

        for (int i = 0; i < 45; i++) {
            products.add(assignProduct("toy",(float)12.9));
        }
        return products;
    }

    private void setChosenProduct(Product product) {
        productNameLabel.setText(product.getName());
        productPriceLabel.setText(String.valueOf(product.getPrice()));
        //image = new Image(getClass().getResourceAsStream(product.getImgSrc()));
        //productImgage.setImage(image);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.products = getProducts();
        System.out.println(products.size());

        // select the first product if there is any product
        if (products.size() > 0) {
            setChosenProduct(products.get(0));
        }

        // Define the number of gridColumnNumber (for the GridPane)
        int gridColumnNumber = 4;
        int columnIndex = 0;
        int rowIndex = 1;

        try {
            // Load and display each product in the GridPane
            for (int i = 0; i < products.size(); i++) {
                // Check if the FXML file is found before attempting to load it
                URL productFXMLUrl = ProductController.class.getResource("fxml/manager/product.fxml");
                System.out.println("Resource URL: " + productFXMLUrl);

                if (productFXMLUrl != null) {
                    // Load the FXML for each product
                    FXMLLoader fxmlLoader = new FXMLLoader(productFXMLUrl);
                    AnchorPane anchorPane = fxmlLoader.load();  

                    // Get the controller for the loaded FXML and set the product data
                    ProductController productController = fxmlLoader.getController();
                    productController.setData(products.get(i));

                    // Manage gridColumnNumber and rows for GridPane layout
                    // Move to the next row after 'gridColumnNumber' number of items
                    if (columnIndex == gridColumnNumber) {  
                        columnIndex = 0;
                        rowIndex++;
                    }

                    // Add the loaded anchorPane (product view) to the grid at the calculated position
                    productsGrid.add(anchorPane, columnIndex++, rowIndex);
                } else {
                    // Handle the case where the FXML resource is not found
                    System.out.println("FXML file not found: fxml/manager/product.fxml");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}