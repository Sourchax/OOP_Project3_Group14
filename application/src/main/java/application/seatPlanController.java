package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Session;

public class seatPlanController {

    @FXML
    private GridPane seatGrid;

    @FXML
    private Button confirmButton;

    @FXML
    private Button backButton;

    private List<Integer> selectedNums = new ArrayList<>();

    private final Map<Button, String> seatStatus = new HashMap<>();

    // Icons for seat statuses
    private final Image availableSeatIcon = new Image(getClass().getResource("/application/fxml/icons/availableSeat.png").toExternalForm());
    private final Image selectedSeatIcon = new Image(getClass().getResource("/application/fxml/icons/selectedSeat.png").toExternalForm());
    private final Image occupiedSeatIcon = new Image(getClass().getResource("/application/fxml/icons/occupiedSeat.png").toExternalForm());
    

    // Initialize the seat layout dynamically
    @FXML
    public void initialize() {

        confirmButton.setOnAction(event -> handleConfirm());
        backButton.setOnAction(event -> handleBack());

        Session movieSession = StaticSelection.staticSession;
        long seatCrypted = movieSession.getSeats();
        confirmButton.setDisable(true);

        Integer seatNumber = 0;
        if(movieSession.getHall().equals("A")){
            for(int i = 0; i<6; i++){
                for(int j = 0; j<8; j++){
                    Button seat = new Button();
                    seat.setPrefSize(10, 30);
                    if((seatCrypted & 1) == 1){
                        ImageView seatIcon = new ImageView(occupiedSeatIcon);
                        seatIcon.setFitHeight(30.0);
                        seatIcon.setFitWidth(30.0);
                        seat.setGraphic(seatIcon);
                        seat.getProperties().put("seatId", seatNumber);
                        seat.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                        seatStatus.put(seat, "occupied");
                    }
                    else{
                        ImageView seatIcon = new ImageView(availableSeatIcon);
                        seatIcon.setFitHeight(30.0);
                        seatIcon.setFitWidth(30.0);
                        seat.setGraphic(seatIcon);
                        seat.getProperties().put("seatId", seatNumber);
                        seat.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                        seatStatus.put(seat, "available");
                    }
                    seatCrypted = seatCrypted >> 1;
                    seat.setOnAction(event -> handleSeatSelection(seat));
                    seatNumber++;
                    seatGrid.add(seat, j, i);
                }
            }
        }
        else{
            for(int i = 0; i<4; i++){
                for(int j = 0; j<4; j++){
                    Button seat = new Button();
                    seat.setPrefSize(10, 30);
                    if((seatCrypted & 1) == 1){
                        ImageView seatIcon = new ImageView(occupiedSeatIcon);
                        seatIcon.setFitHeight(30.0);
                        seatIcon.setFitWidth(30.0);
                        seat.setGraphic(seatIcon);
                        seat.getProperties().put("seatId", seatNumber);
                        seat.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                        seatStatus.put(seat, "occupied");
                    }
                    else{
                        ImageView seatIcon = new ImageView(availableSeatIcon);
                        seatIcon.setFitHeight(30.0);
                        seatIcon.setFitWidth(30.0);
                        seat.setGraphic(seatIcon);
                        seat.getProperties().put("seatId", seatNumber);
                        seat.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                        seatStatus.put(seat, "available");
                    }

                    seatCrypted = seatCrypted >> 1;
                    seat.setOnAction(event -> handleSeatSelection(seat));
                    seatNumber++;
                    seatGrid.add(seat, j, i);
                }
            }
        }          
    }

    private void handleSeatSelection(Button seat) {
        String currentStatus = seatStatus.get(seat);

        switch (currentStatus) {
            case "available":
                ImageView seatIcon1 = new ImageView(selectedSeatIcon);
                seatIcon1.setFitHeight(30.0);
                seatIcon1.setFitWidth(30.0);
                seat.setGraphic(seatIcon1);
                seat.setPrefSize(30, 30);
                seat.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                seatStatus.put(seat, "selected");
                handleSeatSale(seat, true);
                break;

            case "selected":
                ImageView seatIcon2 = new ImageView(availableSeatIcon);
                seatIcon2.setFitHeight(30.0);
                seatIcon2.setFitWidth(30.0);
                seat.setGraphic(seatIcon2);
                seat.setPrefSize(30, 30);
                seat.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                seatStatus.put(seat, "available");
                handleSeatSale(seat, false);
                break;

            case "occupied":
                break;
        }
    }

    public void markSeatsAsOccupied(int[][] occupiedSeats) {
        for (int[] seat : occupiedSeats) {
            int row = seat[0];
            int col = seat[1];

            Button seatButton = getSeatButton(row, col);
            if (seatButton != null) {
                seatButton.setGraphic(new ImageView(occupiedSeatIcon));
                seatStatus.put(seatButton, "occupied");
            }
        }
    }

    private Button getSeatButton(int row, int col) {
        for (javafx.scene.Node node : seatGrid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (Button) node;
            }
        }
        return null;
    }

    private void handleSeatSale(Button seat, boolean isNew){
        Integer a = (Integer) seat.getProperties().get("seatId");
        if(isNew){
            selectedNums.add(a);
        }
        else{
            selectedNums.remove(a);
        }
        if(selectedNums.size() > 0){
            confirmButton.setDisable(false);
        }
        else{
            confirmButton.setDisable(true);
        }
        StaticSelection.staticSeatIndeces = selectedNums;
        List<String> seatValues = new ArrayList<>();
        for(Integer b: selectedNums)
            seatValues.add(convertIndex(b));
        StaticSelection.staticSeatValues = seatValues;
        cashierParent.getParent().ticketsAdded(0, false);
    }

    private void handleConfirm(){
        StaticSelection.staticSeatIndeces = selectedNums;
        List<String> seatValues = new ArrayList<>();
        for(Integer a: selectedNums)
            seatValues.add(convertIndex(a));

        StaticSelection.staticSeatValues = seatValues;
        cashierParent.getParent().handleScenes("cashierStage4");
    }

    private void handleBack(){
        cashierParent.getParent().ticketsAdded(-1,false);
        cashierParent.getParent().handleScenes("cashierStage2"); 
    }

    private String convertIndex(Integer num){
        int seatChar;
        int seatIndex;
        String ans = "";
        if(StaticSelection.staticSession.getHall().equals("B")){
            seatChar = num/4;
            seatIndex = num%4;


            ans += (char)( seatChar+'A');
            ans += String.valueOf(seatIndex+1);
        }
        else{
            seatChar = num/8;
            seatIndex = num%8;


            ans += (char) ( seatChar+'A');
            ans += String.valueOf(seatIndex+1);
        }

        System.out.println(ans);
        return ans;
    }
}
