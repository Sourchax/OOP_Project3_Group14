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

public class ProductController {

    private Product product;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView productImage;

    private CardClickListener cardListener;

    @FXML
    private void click(MouseEvent mouseEvent) {
        System.out.println("Product clicked: " + product.getName());
        cardListener.clickListener(product);
            
    }
    public void setProductData(Product product, CardClickListener cardListener) {
        this.product = product;
        this.cardListener = cardListener;
        nameLabel.setText(product.getName());
        priceLabel.setText(String.valueOf(product.getPrice()));
        updateImage(product.getImage());
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
}