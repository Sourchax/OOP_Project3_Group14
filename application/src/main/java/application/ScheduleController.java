package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class ScheduleController {

    @FXML
    private Label schedulesLabel;

    @FXML
    private Button createScheduleButton;

    @FXML
    private Button updateScheduleButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Schedule> scheduleTableView;

    @FXML
    private TableColumn<Schedule, String> hallColumn;

    @FXML
    private TableColumn<Schedule, LocalDate> dateColumn;

    @FXML
    private TableColumn<Schedule, String> movieColumn;

    @FXML
    private void initialize() {
        System.out.println("initial");
        createScheduleButton.setOnAction(event -> createSchedule());
        updateScheduleButton.setOnAction(event -> updateSchedule());
    }

    private void createSchedule() {
        System.out.println("Naber");
    }

    private void updateSchedule() {

    }

    
    public static class Schedule {
        private String hall;
        private LocalDate date;
        private String movie;

        public Schedule(String hall, LocalDate date, String movie) {
            this.hall = hall;
            this.date = date;
            this.movie = movie;
        }

        public String getHall() {
            return hall;
        }

        public void setHall(String hall) {
            this.hall = hall;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public String getMovie() {
            return movie;
        }

        public void setMovie(String movie) {
            this.movie = movie;
        }
    }
}