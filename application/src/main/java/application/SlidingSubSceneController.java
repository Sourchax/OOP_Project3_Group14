package application;

import java.util.List;

import dataAccess.PriceModifiersDao;
import entities.PriceModifier;
import entities.Product;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class SlidingSubSceneController {

    @FXML
    public AnchorPane root;

    @FXML
    private Button slideButton;

    @FXML
    private Label totalAmount;
    
    @FXML
    public GridPane shipGrid;

    @FXML
    public GridPane productGrid;

    private boolean isSubSceneVisible = false;

    private Image arrowUpIcon;
    private Image arrowDownIcon;

    private List<PriceModifier> pModifiers;

    public int row = 0;

    public double totalPriceTicket = 0.0;
    public double totalPriceProduct = 0.0;

    @FXML
    public void initialize() {

        arrowUpIcon = new Image(getClass().getResource("/application/fxml/icons/arrow-up.png").toExternalForm());
        arrowDownIcon = new Image(getClass().getResource("/application/fxml/icons/arrow-down.png").toExternalForm());

        PriceModifiersDao database = new PriceModifiersDao();
        pModifiers = database.getList();

        ImageView icon = new ImageView(arrowDownIcon);
        icon.setFitHeight(20);
        icon.setFitWidth(20);
        slideButton.setText("Show Cart");
        slideButton.setGraphic(icon);
        //populateTicketGrid(0);


        root.setTranslateY(120);

        slideButton.setOnAction(event -> toggleSubScene());
    }

    public void toggleSubScene() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), root);
        if (isSubSceneVisible) {
            transition.setToY(120);
            ImageView icon = new ImageView(arrowDownIcon);
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            slideButton.setText("Show Cart");
            slideButton.setGraphic(icon);
        } else {
            transition.setToY(0);
            ImageView icon = new ImageView(arrowUpIcon);
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            slideButton.setText("Hide Cart");
            slideButton.setGraphic(icon);
        }

        transition.play();
        isSubSceneVisible = !isSubSceneVisible;
    }


    public void populateTicketGrid(int i, boolean isDiscountApplied) {

        int discount = 0;

        if(isDiscountApplied){
            discount=pModifiers.get(1).getVal();
        }

        String title = StaticSelection.staticMovie.getName();
        String date = StaticSelection.staticSession.getSessionDate().toString();
        String time = StaticSelection.staticSession.getSessionTime().toString();
        String hall = StaticSelection.staticSession.getHall();
        String seat = StaticSelection.staticSeatValues.get(i);

        String tax = String.valueOf(pModifiers.get(2).getVal());

        double lastPrice = (pModifiers.get(0).getVal()*((100-discount)/100.0))*((100+pModifiers.get(2).getVal())/100.0) ;

        String ticketData = String.format("%s, %s, %s, %s, %s, %s, %s, %s %f%s",
                title, date, time, hall, seat, "Discount: %"+String.valueOf(discount), "Tax:%"+tax, "Price:",lastPrice,"₺");
        addBlockToGrid(true, ticketData, 0, row);
        totalPriceTicket+=lastPrice;
        row++;
    }


    public void populateProductGrid() {
        int row = 0;

        totalPriceProduct = 0.0;
        for (Product product : StaticSelection.selectedProducts.keySet()) {

            Integer quantity = StaticSelection.selectedProducts.get(product);

            int tax = pModifiers.get(3).getVal();

            double lastPrice = (product.getPrice()*((100+tax)/100.0)*quantity);

            if(quantity != 0){

                String productData = String.format("%s, %s%d, %s, %d, %s %f%s", product.getName(), "Quantity: ",quantity, "Tax:%",tax , "Total Price:",lastPrice,"₺");
                addBlockToGrid(false, productData, 0, row);

            }
            totalPriceProduct+=lastPrice;
            row++;
        }
    } 

    private void addBlockToGrid(boolean isTicket, String data, int column, int row) {
        VBox dataBlock = new VBox();
        Label label = new Label(data);
        dataBlock.getChildren().add(label);

        dataBlock.setStyle("-fx-padding: 5px; -fx-background-color: lightgray; -fx-border-color: black;");

        // Add the data block to the grid
        GridPane.setColumnIndex(dataBlock, column);
        GridPane.setRowIndex(dataBlock, row);
        if(isTicket)
            shipGrid.getChildren().add(dataBlock);
        else{
            productGrid.getChildren().add(dataBlock);
        }
    }

    public void editTotal(){

        if(shipGrid.getChildren().isEmpty()){
            totalPriceTicket = 0.0;
        }
        if(productGrid.getChildren().isEmpty()){
            totalPriceProduct = 0.0;
        }

        double totalPriceAll = totalPriceProduct + totalPriceTicket;

        totalAmount.setText(String.valueOf(totalPriceAll));
    }
}