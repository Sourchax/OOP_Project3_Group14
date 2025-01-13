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

/**
 * Controller class responsible for managing the seat selection functionality for a movie session.
 * It allows the user to select available seats, view seat status (available, selected, or occupied), and proceed with the confirmation or go back to the previous screen.
 */
public class seatPlanController {

    @FXML
    private GridPane seatGrid;  // GridPane for displaying seat layout.

    @FXML
    private Button confirmButton;  // Button to confirm seat selection.

    @FXML
    private Button backButton;  // Button to navigate back to the previous screen.

    private List<Integer> selectedNums = new ArrayList<>();  // List to hold selected seat numbers.

    private final Map<Button, String> seatStatus = new HashMap<>();  // Mapping of each seat button to its status (available, selected, occupied).

    // Icons for seat statuses
    private final Image availableSeatIcon = new Image(getClass().getResource("/application/fxml/icons/availableSeat.png").toExternalForm());
    private final Image selectedSeatIcon = new Image(getClass().getResource("/application/fxml/icons/selectedSeat.png").toExternalForm());
    private final Image occupiedSeatIcon = new Image(getClass().getResource("/application/fxml/icons/occupiedSeat.png").toExternalForm());

    /**
     * Initializes the seat plan grid based on the selected movie session.
     * Dynamically displays the available, selected, or occupied seats.
     */
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


    /**
     * Handles the seat selection action.
     * It changes the seat's status between available, selected, and occupied.
     * 
     * @param seat The button representing the selected seat.
     */
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

    /**
     * Marks seats as occupied based on the provided array of occupied seat coordinates.
     * 
     * @param occupiedSeats A 2D array containing the row and column indices of occupied seats.
     */
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

    /**
     * Gets the seat button at the specified row and column.
     * 
     * @param row The row index of the seat.
     * @param col The column index of the seat.
     * @return The seat button at the specified position, or null if not found.
     */
    private Button getSeatButton(int row, int col) {
        for (javafx.scene.Node node : seatGrid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (Button) node;
            }
        }
        return null;
    }

    /**
     * Handles the sale (or removal) of a seat, updating the list of selected seats and enabling or disabling the confirm button.
     * 
     * @param seat The seat button.
     * @param isNew Whether the seat is being newly selected (true) or unselected (false).
     */
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

    /**
     * Handles the confirm action to finalize the seat selection and proceed to the next screen.
     */
    private void handleConfirm(){
        StaticSelection.staticSeatIndeces = selectedNums;
        List<String> seatValues = new ArrayList<>();
        for(Integer a: selectedNums)
            seatValues.add(convertIndex(a));

        StaticSelection.staticSeatValues = seatValues;
        cashierParent.getParent().handleScenes("cashierStage4");
    }

    /**
     * Handles the back action to navigate back to the previous screen.
     */
    private void handleBack(){
        cashierParent.getParent().ticketsAdded(-1,false);
        cashierParent.getParent().handleScenes("cashierStage2"); 
    }

    /**
     * Converts a seat number to a human-readable format (e.g., "A1", "B4").
     * 
     * @param seatId The seat ID to convert.
     * @return The seat identifier as a string (e.g., "A1", "B4").
     */
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
