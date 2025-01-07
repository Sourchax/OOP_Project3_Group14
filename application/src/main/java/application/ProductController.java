
package application;

import entities.Product;
import javafx.event.ActionEvent;
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

    @FXML
    private void click(MouseEvent mouseEvent) {
        System.out.println("product clicked: " + product.getName());
    }

    public void setData(Product product) {
        this.product = product;
        nameLabel.setText(product.getName());
        priceLabel.setText(String.valueOf(product.getPrice()));
        //Image image = new Image(getClass().getResourceAsStream(product.getImgSrc()));
        //productImage.setImage(image);
    }
}
