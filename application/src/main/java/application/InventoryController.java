package application;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dataAccess.ProductsDao;
import entities.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class InventoryController implements Initializable{
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
    private Label productTypeLabel;

    @FXML
    private GridPane productsGrid;

    @FXML
    private ScrollPane productsScrollPane;

    @FXML
    private VBox sideBar;

    public Product selectedProduct;
    
    private ProductsDao productsDatabase;

    private List<Product> products =  new ArrayList<>();

    private CardClickListener cardClickListener;
    
    @FXML
    private void initialize(){
        System.out.println("initial manager inventory");
    }

    public void setChosenProduct(Product product) {
        productNameLabel.setText(product.getName());
        updateImage(product.getImage());
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initial inventory with parameters");
        productsDatabase = new ProductsDao();
        this.products = productsDatabase.getList();
        for (int i = 0; i < products.size(); i++) {
            System.out.println(products.get(i).getName());
            System.out.println(products.get(i).getStock());
        }
        System.out.println(products.size());

        // select the first product if there is any product
        if (products.size() > 0) {
            selectedProduct = products.get(0);
            setChosenProduct(products.get(0));
            cardClickListener = new CardClickListener() {
                @Override
                public void clickListener(Product product) {
                    setChosenProduct(product);
                }
            };
        }

        //  number of gridColumnNumber for gridPane
        int maxGridColumnNumber = 4;
        int columnIndex = 0;
        int rowIndex = 1;

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
                    if (columnIndex == maxGridColumnNumber) {  
                        columnIndex = 0;
                        rowIndex++;
                    }

                    // add the loaded fxml to the grid
                    productsGrid.add(anchorPane, columnIndex++, rowIndex);
                } else {
                    System.out.println("No such fxml");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}