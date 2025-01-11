package application;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import entities.Movie;
import entities.Product;
import entities.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

public class CashierStage5Controller {

    @FXML
    private Label movieName;
    @FXML
    private Label Hall;
    @FXML
    private Label sessionDate;
    @FXML
    private Label sessionTime;
    @FXML
    private Label transactionDate;
    @FXML
    private Label seats;
    @FXML
    private Pane products;
    @FXML
    private Label name;
    @FXML
    private Label surname;

    @FXML
    private Label ticketMovieName;
    @FXML
    private Label ticketHall;
    @FXML
    private Label ticketDate;
    @FXML
    private Label ticketTime;
    @FXML
    private Label ticketSeats;

    @FXML
    private Button confirmButton;
    @FXML
    private Button backButton;

    private LocalDateTime now;

}
