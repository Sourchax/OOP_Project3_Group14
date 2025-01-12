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

public class CashierSceneController {

    @FXML
    private ImageView logoImageView;

    @FXML
    private Label cashierLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label dateTimeField;

    @FXML
    private Button logOutButton;

    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView movieSelectionIcon;

    @FXML
    private ImageView sessionSelectionIcon;

    @FXML
    private ImageView seatSelectionIcon;

    @FXML
    private ImageView productSelectionIcon;

    @FXML
    private ImageView ticketSelectionIcon;

    @FXML
    private Line MtoS;

    @FXML
    private Line StoS;

    @FXML
    private Line StoP;

    @FXML
    private Line PtoT;

    @FXML
    private SlidingSubSceneController controller;

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

    public void productAdded(){
        controller.productGrid.getChildren().clear();
        controller.populateProductGrid();
        controller.row = 0;
        controller.editTotal();
    }

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
