package application;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import entities.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Controller class for handling product-related actions and displaying product data.
 */
public class ProductController {

    /**
     * The Product object representing the product to be displayed.
     * This object contains the data of the product such as its name, price, and image.
     */
    private Product product;

    /**
     * The Label displaying the name of the product.
     * This label is used to show the product's name in the product view.
     */
    @FXML
    private Label nameLabel;

    /**
     * The Label displaying the price of the product.
     * This label is used to show the product's price in the product view.
     */
    @FXML
    private Label priceLabel;

    /**
     * The ImageView used to display the product's image.
     * This ImageView shows the image of the product, which is fetched from the Blob data.
     */
    @FXML
    private ImageView productImage;

    /**
     * The CardClickListener that handles the product click events.
     * This listener is used to define actions when the product card is clicked.
     */
    private CardClickListener cardListener;

    /**
     * Handles the click event on the product poster.
     * 
     * @param mouseEvent The MouseEvent triggered by the click.
     */
    @FXML
    private void click(MouseEvent mouseEvent) {
        System.out.println("Product clicked: " + product.getName());
        cardListener.clickListener(product);
            
    }

    /**
     * Sets the product data and listener for the product controller.
     * 
     * @param product The product object to be displayed.
     * @param cardListener The listener to handle product click events.
     */
    public void setProductData(Product product, CardClickListener cardListener) {
        this.product = product;
        this.cardListener = cardListener;
        nameLabel.setText(product.getName());
        priceLabel.setText(String.valueOf(product.getPrice()));
        updateImage(product.getImage());
    }

    /**
     * Updates the product image in the ImageView.
     * 
     * @param imageBlob The Blob containing the image data.
     */
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
}