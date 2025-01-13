package application;

import java.util.ArrayList;
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

/**
 * Controller for handling the sliding subscene that displays the shopping cart.
 * It allows toggling the visibility of the cart, populating the ticket and product grids, 
 * and calculating and displaying the total amount and tax for the cart's contents.
 */
public class SlidingSubSceneController {

    /**
     * Root anchor pane for the sliding subscene.
     */
    @FXML
    public AnchorPane root;

    /**
     * Button to toggle the visibility of the sliding subscene.
     */
    @FXML
    private Button slideButton;

    /**
     * Label displaying the total amount in the cart.
     */
    @FXML
    private Label totalAmount;
    
    /**
     * Grid pane displaying ticket information.
     */
    @FXML
    public GridPane shipGrid;

    /**
     * Grid pane displaying product information.
     */
    @FXML
    public GridPane productGrid;

    /**
     * Flag to track whether the subscene is visible or not.
     */
    private boolean isSubSceneVisible = false;

    /**
     * Image for the upward arrow icon (used when the subscene is visible).
     */
    private Image arrowUpIcon;

    /**
     * Image for the downward arrow icon (used when the subscene is hidden).
     */
    private Image arrowDownIcon;

    /**
     * List of price modifiers fetched from the database.
     */
    private List<PriceModifier> pModifiers;

    /**
     * Row index used for placing elements in the grid.
     */
    public int row = 0;

    /**
     * Total price of the tickets.
     */
    public double totalPriceTicket = 0.0;

    /**
     * Total price of the products.
     */
    public double totalPriceProduct = 0.0;

    /**
     * Total tax for the products.
     */
    public double totalTaxProduct = 0.0;

    /**
     * Total tax for the tickets.
     */
    public double totalTaxTicket = 0.0;

    /**
     * Initializes the controller by loading the icons and fetching the price modifiers 
     * from the database. It also sets up the action for the slide button.
     */
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

    /**
     * Toggles the visibility of the sliding subscene and changes the icon and text 
     * of the slide button accordingly.
     */
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

    /**
     * Populates the ticket grid with information about the selected ticket, 
     * including its price after applying discounts and tax.
     * 
     * @param i the index of the selected seat
     * @param isDiscountApplied flag indicating whether a discount should be applied
     */
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
        double taxVal = lastPrice-(pModifiers.get(0).getVal()*((100-discount)/100.0));
        String ticketData = String.format("%s, %s, %s, %s, %s, %s, %s, %s %.2f%s",
                title, date, time, hall, seat, "Discount: %"+String.valueOf(discount), "Tax:%"+tax, "Price:",lastPrice,"₺");
        addBlockToGrid(true, ticketData, 0, row);
        totalPriceTicket+=lastPrice;
        totalTaxTicket+=taxVal;
        StaticSelection.allTickets.add(ticketData);
        row++;
    }

    /**
     * Populates the product grid with information about the selected products, 
     * including their total price and tax.
     */
    public void populateProductGrid() {
        int row = 0;
        totalPriceProduct = 0.0;
        totalTaxProduct = 0.0;
        StaticSelection.allProducts= new ArrayList<>();
        if(StaticSelection.selectedProducts.keySet() != null){
            for (Product product : StaticSelection.selectedProducts.keySet()) {
    
                Integer quantity = StaticSelection.selectedProducts.get(product);
    
                int tax = pModifiers.get(3).getVal();
    
                double lastPrice = (product.getPrice()*((100+tax)/100.0)*quantity);
                double taxVal = (lastPrice-(product.getPrice()*quantity));
                if(quantity != 0){
    
                    String productData = String.format("%s, %s%d, %s %d, %s %.2f%s", product.getName(), "Quantity: ",quantity, "Tax:%",tax , "Total Price:",lastPrice,"₺");
                    addBlockToGrid(false, productData, 0, row);
                    StaticSelection.allProducts.add(productData);
    
                }
                totalPriceProduct+=lastPrice;
                totalTaxProduct+=taxVal;
                row++;
            }
        }
    } 

    /**
     * Adds a data block (representing either a ticket or product) to the appropriate grid.
     * 
     * @param isTicket flag indicating whether the data represents a ticket (true) or a product (false)
     * @param data the data to be added in the block
     * @param column the column index in the grid
     * @param row the row index in the grid
     */
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

    /**
     * Updates the total amount and total tax based on the current contents of the grids. 
     * This method is called whenever there is a change in the cart.
     */
    public void editTotal() {

        if(shipGrid.getChildren().isEmpty()){
            totalPriceTicket = 0.0;
            totalTaxTicket = 0.0;
        }
        if(productGrid.getChildren().isEmpty()){
            totalPriceProduct = 0.0;
            totalTaxProduct = 0.0;
        }

        double totalPriceAll = totalPriceProduct + totalPriceTicket;
        double totalTaxAll = totalTaxProduct + totalTaxTicket;

        totalAmount.setText(String.valueOf(totalPriceAll));
        StaticSelection.totalAmount = totalPriceAll;
        StaticSelection.totalTax = totalTaxAll;
    }
}
