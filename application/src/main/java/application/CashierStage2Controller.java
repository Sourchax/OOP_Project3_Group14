package application;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dataAccess.SessionDao;
import entities.Movie;
import entities.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CashierStage2Controller {

    @FXML
    private Label selectedMovieLabel;

    @FXML
    private ComboBox<String> daysComboBox;

    @FXML
    private ComboBox<String> sessionsComboBox;

    @FXML
    private ComboBox<String> sessionsComboBox1;

    @FXML
    private Button confirmSelectionButton;

    @FXML
    private Button backToSearchButton;

    
    @FXML
    private Label movieGenresLabel;

    @FXML
    private Label releaseYearLabel;

    @FXML
    private ImageView movieImage;

    @FXML
    private Label movieTitleLabel;

    @FXML
    private Label vacantSeats;

    @FXML
    private Label movieSummaryLabel;

    private SessionDao database;

    private List<Session> schedules;

    private Session selectedSession; 

    @FXML
    private void initialize() {
        
        movieGenresLabel.setText(StaticSelection.staticMovie.getGenre());
        movieTitleLabel.setText(StaticSelection.staticMovie.getName());
        movieSummaryLabel.setText(StaticSelection.staticMovie.getSummary());
        releaseYearLabel.setText(StaticSelection.staticMovie.getYear());

        database = new SessionDao();
        schedules = database.getListByFilter("movie", StaticSelection.staticMovie.getName());
        sessionsComboBox.setDisable(true);
        sessionsComboBox1.setDisable(true);

        confirmSelectionButton.setDisable(true);

        daysComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("User selected: " + newValue);
                sessionsComboBox.setDisable(false);
                sessionsComboBox1.setValue(null);
                populateSessions();
            }
        });

        sessionsComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("User selected: " + newValue);
                sessionsComboBox1.setDisable(false);
                populateHalls();
            }
            else{
                sessionsComboBox1.setDisable(true);
            }
        });

        sessionsComboBox1.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("User selected: " + newValue);
                calculateSeats();
            }
            else{
                confirmSelectionButton.setDisable(true);
                vacantSeats.setText("");
            }
        });

        populateDays();

        if(StaticSelection.staticMovie.getPoster() == null){
            movieImage.setImage(null);
            return;
        }

        InputStream inputStream;
        try {
            Blob imageBlob = StaticSelection.staticMovie.getPoster();
            inputStream = imageBlob.getBinaryStream();
            Image image = new Image(inputStream);
            movieImage.setImage(image);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void populateDays(){

        ObservableList<String> dates = FXCollections.observableArrayList();
        for(Session ses: schedules){
            if(!dates.contains(ses.getSessionDate().toString())){
                dates.add(ses.getSessionDate().toString());
            }
        }
        daysComboBox.setItems(dates);
    }

    @FXML
    private void handleConfirmSelection() {
        StaticSelection.staticSession = selectedSession;
        cashierParent.getParent().handleScenes("seatPlanStage");
    }

    private void populateSessions(){
        ObservableList<String> sessionTimes = FXCollections.observableArrayList();
        for(Session ses: schedules){
            if(ses.getSessionDate().toString().equals(daysComboBox.getValue())){
                sessionTimes.add(ses.getSessionTime().toString());
            }
        }
        sessionsComboBox.setItems(sessionTimes);
    }

    private void populateHalls(){
        ObservableList<String> sessionHalls = FXCollections.observableArrayList();
        for(Session ses: schedules){
            if(ses.getSessionDate().toString().equals(daysComboBox.getValue()) && ses.getSessionTime().toString().equals(sessionsComboBox.getValue())){
                sessionHalls.add(ses.getHall());
            }
        }
        sessionsComboBox1.setItems(sessionHalls);
    }

    @FXML
    private void handleBackToSearch() {
        System.out.println("Back to Search");
        cashierParent.getParent().handleScenes("cashierStage1");  
    }

    private void calculateSeats(){

        LocalDate scheduleDate = LocalDate.parse(daysComboBox.getValue());

        LocalTime scheduleTime = LocalTime.parse(sessionsComboBox.getValue());

        String hall = sessionsComboBox1.getValue();

        selectedSession = database.getByFilter("sessionDate, sessionTime, hall", scheduleDate, scheduleTime, hall);

        long seatCrypted = selectedSession.getSeats();
        Integer seats;
        if(hall.equals("A")){
            seats = 48;
            while(seatCrypted > 0){
                if((seatCrypted & 1) == 1){
                    seats--;
                }
                seatCrypted = seatCrypted>>1;
            }           
        }
        else{
            seats = 16;
            while(seatCrypted > 0){
                if((seatCrypted & 1) == 1){
                    seats--;
                }
                seatCrypted = seatCrypted>>1;
            }
        }

        String a = seats.toString();
        if((hall.equals("A") && seats>30) || (hall.equals("B") && seats > 12)){
            vacantSeats.setText(a);
            vacantSeats.setStyle("-fx-text-fill: green;");
            confirmSelectionButton.setDisable(false);
        }
        else if((hall.equals("A") && seats>20) || (hall.equals("B") && seats > 5)){
            vacantSeats.setText(a);
            vacantSeats.setStyle("-fx-text-fill: yellow;");
            confirmSelectionButton.setDisable(false);
        }
        else if(seats > 0){
            vacantSeats.setText(a);
            vacantSeats.setStyle("-fx-text-fill: orange;");
            confirmSelectionButton.setDisable(false);
        }
        else{
            vacantSeats.setText("No seats available!");
            vacantSeats.setStyle("-fx-text-fill: red;");
            confirmSelectionButton.setDisable(true);
        }
    }
}