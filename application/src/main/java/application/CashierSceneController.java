package application;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.SubScene;

/**
 * Controller class for handling cashier-related functionalities and UI transitions in the cinema system.
 * The class manages UI elements for movie selection, session selection, seat selection, product selection,
 * and ticket management.
 */
public class CashierSceneController {

    /**
     * The ImageView that displays the cashier's logo.
     */
    @FXML
    private ImageView logoImageView;

    /**
     * The label displaying the cashier text on the UI.
     */
    @FXML
    private Label cashierLabel;

    /**
     * The label displaying the username of the current cashier.
     */
    @FXML
    private Label usernameLabel;

    /**
     * The label that displays the current date and time.
     */
    @FXML
    private Label dateTimeField;

    /**
     * The button that allows the cashier to log out.
     */
    @FXML
    private Button logOutButton;

    /**
     * The main pane where different scenes are loaded.
     */
    @FXML
    private BorderPane mainPane;

    /**
     * ImageViews for the icons of different selection types in the cashier scene (e.g., movie, session, etc.).
     */
    @FXML
    private ImageView movieSelectionIcon, sessionSelectionIcon, seatSelectionIcon, productSelectionIcon, ticketSelectionIcon;

    /**
     * Lines for visual representation of transitions between different selection steps in the cashier's workflow.
     */
    @FXML
    private Line MtoS, StoS, StoP, PtoT;

    /**
     * Controller for the shoppng cart used in the cashier interface.
     */
    @FXML
    private SlidingSubSceneController controller;

    /**
     * Initializes the CashierSceneController by setting up the UI components,
     * displaying the current username, and loading the current date.
     * It also loads the sub-scene that will be used for further UI transitions.
     */
    @FXML
    private void initialize(){
        cashierParent.setParent(this);
        usernameLabel.setText(currentUser.getUsername());
        Image iconPlace = new Image(getClass().getResource("/application/fxml/icons/cashier-logo.png").toExternalForm());
        logoImageView.setImage(iconPlace);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateTimeField.setText(java.time.LocalDateTime.now().format(formatter));
    
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/slidingSubScene.fxml"));
            AnchorPane subSceneContent = loader.load();
    
            mainPane.setBottom(subSceneContent);
    
            // Initialize the controller
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        handleScenes("cashierStage1");
    }

    /**
     * Handles the scene transitions within the cashier interface.
     * Loads a new FXML scene and sets it in the center of the main pane.
     * 
     * @param child The name of the child scene to be loaded.
     */
    public void handleScenes(String child){
        String path = "fxml/cashier/" + child + ".fxml"; 
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(path));
        if(child .equals("invoice")){
            controller.root.setVisible(false);
        }
        else{
            controller.root.setVisible(true);
        }
        try {
            Node temp = fxmlLoader.load();
            if(mainPane.getCenter() != temp){
                mainPane.setCenter(temp);        
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the ticket grid when tickets are added.
     * It clears any previous ticket entries and populates the grid based on the discount number and whether a discount is applied.
     * 
     * @param discountNum The number of tickets that should be discounted.
     * @param isDiscountApplied Boolean indicating whether a discount should be applied to the tickets.
     */
    public void ticketsAdded(Integer discountNum, boolean isDiscountApplied){
        controller.shipGrid.getChildren().clear();
        StaticSelection.allTickets= new ArrayList<>();
        controller.totalTaxTicket = 0.0;
        controller.totalPriceTicket = 0.0;
        if(discountNum != -1){
            int i = 0;
            for(i = 0; i<discountNum; i++){
                controller.populateTicketGrid(i, isDiscountApplied);
            }
            while(i<StaticSelection.staticSeatValues.size()){
                controller.populateTicketGrid(i, false);
                i++;
            }
            controller.row = 0;

        }
        controller.editTotal();

    }  

    /**
     * Updates the product grid when products are added.
     * Clears the previous product entries and repopulates the grid.
     */
    public void productAdded(){
        controller.productGrid.getChildren().clear();
        controller.populateProductGrid();
        controller.row = 0;
        controller.editTotal();
    }

    /**
     * Logs out the current user and redirects them to the login screen.
     * 
     * @param event The MouseEvent that triggered the log out action.
     * @throws IOException If there is an issue loading the login scene.
     */
    @FXML
    void handleLogOut(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
        BorderPane root = loader.load();


        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene loginScene = new Scene(root, 640, 480);
        currentStage.setTitle("Login Page"); 
        currentStage.setScene(loginScene); 
        currentStage.show();

        currentUser.setUsername("");

    }
}
