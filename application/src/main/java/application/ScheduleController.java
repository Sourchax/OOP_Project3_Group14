package application;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    private TableColumn<Session, String> colHall;

    @FXML
    private TableColumn<Session, String> colMovie;

    @FXML
    private TableColumn<Session, String> colTime;

    @FXML
    private TableColumn<Session, String> colDate;

    @FXML
    private TableColumn<Session, Integer> colTickets;

    @FXML
    private Button createButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private DatePicker realDatePicker;

    @FXML
    private Label warningLabel;

    @FXML
    private ComboBox<String> movieBox;

    @FXML
    private ComboBox<String> timeBox;

    @FXML
    private ComboBox<String> hallBox;

    private List<String> movies = NameifyMovies();
    private String[] times = {"10:00", "12:00", "14:00", "16:00", "18:00", "20:00", "22:00"};
    private String[] halls = {"A", "B"};

    private Session selectedSession = null;
    private ObservableList<Session> sessionData;
    private BooleanProperty selectionBind = new SimpleBooleanProperty(false);
    private LocalDate filterDate = null;

    private int index;

    @FXML
    private void initialize() {
        createButton.setDisable(true);
        updateButton.setDisable(true);
        setTableColumns();
        populateTable(null);
        choiceBoxSetter();

        createButton.disableProperty().bind(
            Bindings.createBooleanBinding(
                () -> hallBox.getValue() == null || movieBox.getValue() == null ||
                      timeBox.getValue() == null || realDatePicker.getValue() == null,
                hallBox.valueProperty(), movieBox.valueProperty(),
                timeBox.valueProperty(), realDatePicker.valueProperty()
            )
        );

        updateButton.disableProperty().bind(
            Bindings.createBooleanBinding(
                () -> hallBox.getValue() == null || movieBox.getValue() == null ||
                      timeBox.getValue() == null || realDatePicker.getValue() == null ||
                      selectionBind.getValue() == false,
                hallBox.valueProperty(), movieBox.valueProperty(),
                timeBox.valueProperty(), realDatePicker.valueProperty(),
                selectionBind
            )
        );

        deleteButton.disableProperty().bind(selectionBind.not());

        tableView.setOnMouseClicked(this::prepareFields);
        createButton.setOnAction(event -> createSchedule());
        updateButton.setOnAction(event -> updateSchedule());
        deleteButton.setOnMouseClicked(event -> {deleteSchedule(event); prepareFields(event);});
        datePicker.setOnAction(event -> {
            filterDate = datePicker.getValue();
            populateTable(filterDate);
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
            realDatePicker.setValue(selectedSession.getSessionDate());
            warningLabel.setText(null);
        }
        else{
            movieBox.setValue(null);
            hallBox.setValue(null);
            timeBox.setValue(null);
            realDatePicker.setValue(null);
            selectionBind.setValue(false);
        }
    }

    private void setTableColumns(){
        colHall.setCellValueFactory(new PropertyValueFactory<Session, String>("hall"));
        colMovie.setCellValueFactory(new PropertyValueFactory<Session, String>("movie"));
        colTime.setCellValueFactory(//new PropertyValueFactory<Session, LocalTime>("sessionTime")
            cd -> {
            LocalTime sessionTime = cd.getValue().getSessionTime();
            return new SimpleStringProperty(sessionTime.toString());
        }
        );
        colDate.setCellValueFactory(//new PropertyValueFactory<Session, LocalDate>("sessionDate")
            cd -> {
            LocalDate sessionDate = cd.getValue().getSessionDate();
            return new SimpleStringProperty(sessionDate.toString());
        }
        );
        colTickets.setCellValueFactory(cd -> {
            long seats = cd.getValue().getSeats();
            int tickets = calcTickets(seats);
            return new SimpleIntegerProperty(tickets).asObject();
        });
    }

    private void populateTable(LocalDate date){
        List<Session> sessionList = sessionDao.getList();
        if(date != null){
            List<Session> dateFiltered = new ArrayList<>();
            for(Session s : sessionList){
                if(s.getSessionDate().getYear() == date.getYear() && s.getSessionDate().getMonth().equals(date.getMonth())){
                    dateFiltered.add(s);
                }
            }
            sessionData = FXCollections.observableArrayList(dateFiltered);
        }
        else{
            sessionData = FXCollections.observableArrayList(sessionList);
        }
        tableView.setItems(sessionData);
    }

    private void choiceBoxSetter(){
        if(movies != null)
            movieBox.getItems().addAll(movies);
        timeBox.getItems().addAll(times);
        hallBox.getItems().addAll(halls);
    }

    private void createSchedule() {
        LocalDate date = realDatePicker.getValue();
        int dateCheck = date.compareTo(LocalDate.now());
        LocalTime time = LocalTime.parse(timeBox.getValue());
        int timeCheck = time.compareTo(LocalTime.now());

        if(dateCheck < 0 || (dateCheck == 0 && timeCheck < 0)){
            warningLabel.setText("Cannot create a session for past date");
            return;
        }

        String hall = hallBox.getValue();
        String movie = movieBox.getValue();

        for(Session s : sessionData){
            if(s.getSessionDate().equals(date) && s.getSessionTime().equals(time) && s.getHall().equals(hall)){
                warningLabel.setText("Cannot create, This hall is occupied at this time.");
                return;
            }
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Create Session");
        confirmation.setHeaderText("Are you sure you want to create this session?");
        confirmation.setContentText("Movie: " + movie + "\n" + "Date: " + date.toString() + "\n" + "Time: " + time.toString() + "\n" + "Hall: " + hall + "\n");

        if (confirmation.showAndWait().get() == ButtonType.OK) {
            Session session = new Session();
            session.setMovie(movie);
            session.setHall(hall);
            session.setSessionTime(time);
            session.setSessionDate(date);
            session.setSeats(0);
            sessionDao.insert(session);
    
            populateTable(date);
            warningLabel.setText("New session added");
            selectionBind.setValue(false);
            return;
        }
        warningLabel.setText("Process canceled!");

    }

    private void updateSchedule() {
        LocalDate date = realDatePicker.getValue();
        int tickets = calcTickets(selectedSession.getSeats());
        int dateCheck = date.compareTo(LocalDate.now());
        LocalTime time = LocalTime.parse(timeBox.getValue());
        int timeCheck = time.compareTo(LocalTime.now());

        if(dateCheck < 0 || (dateCheck == 0 && timeCheck < 0)){
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
            if(s.getId() != id && s.getSessionDate().equals(date) && s.getSessionTime().equals(time) && s.getHall().equals(hall)){
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
        int tickets = calcTickets(selectedSession.getSeats());
        if(tickets != 0){
            warningLabel.setText("Cannot delete, too many tickets sold.");
            return;
        }

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