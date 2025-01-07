package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

import entities.Session;

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
    private void initialize() {
        createScheduleButton.setOnAction(event -> createSchedule());
        updateScheduleButton.setOnAction(event -> updateSchedule());
    }

    private void createSchedule() {
        System.out.println("Naber");
    }

    private void updateSchedule() {

    }

}