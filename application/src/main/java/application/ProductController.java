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

    private Product product;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView productImage;

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