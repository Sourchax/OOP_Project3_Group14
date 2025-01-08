package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.HashMap;
import java.util.Map;

public class seatPlanController {

    @FXML
    private GridPane seatGrid;

    // Map to hold the seat buttons and their statuses
    private final Map<Button, String> seatStatus = new HashMap<>();

    // Icons for seat statuses
    private final Image availableSeatIcon = new Image(getClass().getResource("/application/fxml/icons/availableSeat.png").toExternalForm());
    private final Image selectedSeatIcon = new Image(getClass().getResource("/application/fxml/icons/selectedSeat.png").toExternalForm());
    private final Image occupiedSeatIcon = new Image(getClass().getResource("/application/fxml/icons/occupiedSeat.png").toExternalForm());
    

    // Initialize the seat layout dynamically
    @FXML
    public void initialize() {
        int rows = 5;  // Example row count
        int cols = 10; // Example column count

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Button seat = new Button();
                ImageView seatIcon = new ImageView(availableSeatIcon);
                seatIcon.setFitHeight(30.0);
                seatIcon.setFitWidth(30.0);
                seat.setGraphic(seatIcon);
                seat.setPrefSize(10, 30);

                seat.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                // Default seat status to "available"
                seatStatus.put(seat, "available");

                // Set seat click handler
                seat.setOnAction(event -> handleSeatSelection(seat));

                seatGrid.add(seat, j, i);
            }
        }
    }

    // Handle seat selection logic
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
                break;

            case "selected":
                ImageView seatIcon2 = new ImageView(availableSeatIcon);
                seatIcon2.setFitHeight(30.0);
                seatIcon2.setFitWidth(30.0);
                seat.setGraphic(seatIcon2);
                seat.setPrefSize(30, 30);
                seat.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                seatStatus.put(seat, "available");
                break;

            case "occupied":
                // Do nothing; occupied seats cannot be selected
                break;
        }
    }

    // Example method to mark seats as occupied
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

    // Helper method to get a seat button by grid position
    private Button getSeatButton(int row, int col) {
        for (javafx.scene.Node node : seatGrid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (Button) node;
            }
        }
        return null;
    }
}
