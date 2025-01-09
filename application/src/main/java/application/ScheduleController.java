package application;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import dataAccess.MoviesDao;
import dataAccess.SessionDao;
import entities.Movie;
import entities.Session;

public class ScheduleController {

    private SessionDao sessionDao = new SessionDao();
    private MoviesDao moviesDao = new MoviesDao();

    @FXML
    private Label schedulesLabel;

    @FXML
    private TableView<Session> tableView;

    @FXML
    private TableColumn<Session, String> sHall;

    @FXML
    private TableColumn<Session, String> sMovie;

    @FXML
    private TableColumn<Session, String> sTime;

    @FXML
    private TableColumn<Session, Integer> sTickets;

    @FXML
    private Button createScheduleButton;

    @FXML
    private Button updateScheduleButton;

    @FXML
    private Button deleteScheduleButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label warningLabel;

    @FXML
    private ChoiceBox<String> movieBox;

    @FXML
    private ChoiceBox<String> timeBox;

    @FXML
    private ChoiceBox<String> hallBox;

    private List<String> movies = NameifyMovies();
    private String[] times = {"10:00:00", "12:00:00", "14:00:00", "16:00:00", "18:00:00", "20:00:00"};
    private String[] halls = {"A", "B"};

    private Session selectedSession = null;
    private ObservableList<Session> sessionData;
    private BooleanProperty selectionBind = new SimpleBooleanProperty(false);
    private Date date = null;

    private int index;

    @FXML
    private void initialize() {
        createScheduleButton.setDisable(true);
        updateScheduleButton.setDisable(true);
        setTableColumns();
        populateTable(null);
        choiceBoxSetter();

        createScheduleButton.disableProperty().bind(
            Bindings.createBooleanBinding(
                () -> hallBox.getValue() == null || movieBox.getValue() == null ||
                      timeBox.getValue() == null || datePicker.getValue() == null,
                hallBox.valueProperty(), movieBox.valueProperty(),
                timeBox.valueProperty(), datePicker.valueProperty()
            )
        );

        updateScheduleButton.disableProperty().bind(
            Bindings.createBooleanBinding(
                () -> hallBox.getValue() == null || movieBox.getValue() == null ||
                      timeBox.getValue() == null || datePicker.getValue() == null ||
                      selectionBind.getValue() == false,
                hallBox.valueProperty(), movieBox.valueProperty(),
                timeBox.valueProperty(), datePicker.valueProperty(),
                selectionBind
            )
        );

        deleteScheduleButton.disableProperty().bind(selectionBind.not());

        tableView.setOnMouseClicked(this::prepareFields);
        createScheduleButton.setOnAction(event -> createSchedule());
        updateScheduleButton.setOnAction(event -> updateSchedule());
        deleteScheduleButton.setOnMouseClicked(event -> {deleteSchedule(event); prepareFields(event);});
        datePicker.setOnAction(event -> {
            date = Date.valueOf(datePicker.getValue());
            populateTable(date);
            selectionBind.setValue(false);
        });
    }

    @FXML
    private void prepareFields(MouseEvent event){
        index = tableView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            selectedSession = sessionData.get(index);
            selectionBind.setValue(true);
            movieBox.setValue(selectedSession.getMovie());
            hallBox.setValue(selectedSession.getHall());
            timeBox.setValue(selectedSession.getSessionTime().toString());
            warningLabel.setText(null);
        }
        else{
            movieBox.setValue(null);
            hallBox.setValue(null);
            timeBox.setValue(null);
            selectionBind.setValue(false);
        }
    }

    private void setTableColumns(){
        sHall.setCellValueFactory(new PropertyValueFactory<Session, String>("hall"));
        sMovie.setCellValueFactory(new PropertyValueFactory<Session, String>("movie"));
        sTime.setCellValueFactory(cd -> {
            Time sessionTime = cd.getValue().getSessionTime();
            return new SimpleStringProperty(sessionTime.toString());
        });
        sTickets.setCellValueFactory(cd -> {
            long seats = cd.getValue().getSeats();
            int tickets = calcTickets(seats);
            return new SimpleIntegerProperty(tickets).asObject();
        });
    }

    private void populateTable(Date date){
        if(date == null)
            sessionData = FXCollections.observableArrayList(sessionDao.getList());
        else
            sessionData = FXCollections.observableArrayList(sessionDao.getListByFilter("sessionDate", date));
        tableView.setItems(sessionData);
    }

    private void choiceBoxSetter(){
        if(movies != null)
            movieBox.getItems().addAll(movies);
        timeBox.getItems().addAll(times);
        hallBox.getItems().addAll(halls);
    }

    private void createSchedule() {
        int dateCheck = date.compareTo(Date.valueOf(LocalDate.now()));
        Time time = Time.valueOf(timeBox.getValue());
        int timeCheck = time.compareTo(Time.valueOf(LocalTime.now()));

        if(dateCheck < 0 && (dateCheck == 0 && timeCheck < 0)){
            warningLabel.setText("Cannot create a session for past date");
            return;
        }

        String hall = hallBox.getValue();

        for(Session s : sessionData){
            if(s.getSessionTime().equals(time) && s.getHall().equals(hall)){
                warningLabel.setText("Cannot create, This hall is occupied at this time.");
                return;
            }
        }

        Session session = new Session();
        session.setMovie(movieBox.getValue());
        session.setHall(hall);
        session.setSessionTime(time);
        session.setSessionDate(date);
        session.setSeats(0);
        sessionDao.insert(session);
        populateTable(date);
        warningLabel.setText("New session added");
    }

    private void updateSchedule() {
        int tickets = calcTickets(selectedSession.getSeats());
        int dateCheck = date.compareTo(Date.valueOf(LocalDate.now()));
        Time time = Time.valueOf(timeBox.getValue());
        int timeCheck = time.compareTo(Time.valueOf(LocalTime.now()));

        if(dateCheck < 0 && (dateCheck == 0 && timeCheck < 0)){
            warningLabel.setText("Cannot update a past session");
            return;
        }
        else if(tickets != 0){
            warningLabel.setText("Cannot update, too many tickets sold.");
            return;
        }
        
        String hall = hallBox.getValue();
        int id = selectedSession.getId();

        for(Session s : sessionData){
            if(s.getId() != id && s.getSessionTime().equals(time) && s.getHall().equals(hall)){
                warningLabel.setText("Cannot update, this hall is occupied at this time.");
                return;
            }
        }
        
        String movie = movieBox.getValue();
        sessionDao.updateById(id, "hall, movie, sessionTime, sessionDate", hall, movie, time, date);
        warningLabel.setText("Session updated.");
        populateTable(date);
    }

    @FXML
    private void deleteSchedule(MouseEvent event){
        tableView.getSelectionModel().clearSelection(index);
        tableView.getItems().remove(index);
        sessionDao.deleteById(selectedSession.getId());
        selectedSession = null;
        selectionBind.setValue(false);
        warningLabel.setText("Session deleted");
    }

    private List<String> NameifyMovies(){
        List<Movie> list = moviesDao.getList();
        if(list != null && !list.isEmpty()){
            List<String> names = new ArrayList<String>();
            for(Movie m : list){
                names.add(m.getName());
            }
            return names;
        }
        return null;
        
    }

    private int calcTickets(long seats){
        int tickets = 0;
        while(seats != 0){
            if((seats & 1L) == 1L)
                tickets++;
            seats = seats >> 1;
        }
        return tickets;
    }
}