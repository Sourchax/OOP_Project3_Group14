package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import dataAccess.GenericDao;
import dataAccess.MoviesDao;
import entities.*;


import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;


public class MoviesTabController {

    @FXML
    private Button addMovieButton;

    @FXML
    private Button editDetailsButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button genreButton;

    @FXML
    private Button selectNewPosterButton;

    @FXML
    private ListView<String> moviesList;

    @FXML
    private TextField titleField;

    @FXML
    private TextField yearField;

    @FXML
    private Label genreField;

    @FXML
    private TextArea summaryField;

    @FXML
    private ImageView moviePoster;

    private ObservableList<Movie> movieData;

    private Movie selectedMovie;

    private Blob selectedImageBlob;
    
    private MoviesDao database;

    @FXML
    private void initialize() {
        addMovieButton.setOnAction(event -> onAddNewMovie());
        editDetailsButton.setOnAction(event -> editMovieDetails());
        selectNewPosterButton.setOnAction(event -> selectNewPoster());
        genreButton.setOnAction(event -> showGenreSelectionPopup());
        deleteButton.setOnAction(event -> handleDeleteMovie());


        yearField.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            String newText = change.getControlNewText();
        
            if (newText.matches("\\d{0,4}")) {
                return change; 
            }
            return null;
        }));
        
        
        database = new MoviesDao();
        
        populateTableWithMovies();
        moviesList.setOnMouseClicked(this::handleMovieSelection);

        selectedMovie = movieData.get(0);
        selectedImageBlob = movieData.get(0).getPoster();
        populateMovieDetails(selectedMovie);
    }

    @FXML
    private void handleMovieSelection(MouseEvent event) {
        int index = moviesList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            selectedMovie = movieData.get(index);
            populateMovieDetails(selectedMovie);
        }
        selectedImageBlob = movieData.get(index).getPoster();
    }

    private void showGenreSelectionPopup() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/GenreSelection.fxml"));
            AnchorPane popupRoot = loader.load();

            GenreSelectionController controller = loader.getController();

            controller.setGenreSelectionCallback(this::updateSelectedGenres);

            Stage popupStage = new Stage();
            popupStage.setTitle("Select Genres");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(popupRoot));
            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectNewPoster(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));

        File selectedPoster = fileChooser.showOpenDialog(null);
        if (selectedPoster != null) {
            try {
                Image image = new Image(new FileInputStream(selectedPoster));
                moviePoster.setImage(image);
                FileInputStream file = new FileInputStream(selectedPoster);
            
                byte[] imageBytes = file.readAllBytes();
                selectedImageBlob = new SerialBlob(imageBytes);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {              
                e.printStackTrace();
            } catch (SerialException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onAddNewMovie() {
        System.out.println("Add New Movie button clicked!");
        
        openAddMovieWindow();
    }

    private void openAddMovieWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/AddMovieScene.fxml"));
            Parent root = loader.load();


            Stage stage = new Stage();
            stage.setTitle("Add New Movie");
            stage.initModality(Modality.APPLICATION_MODAL);  // Block interaction with the main window
            stage.setScene(new Scene(root));
            stage.showAndWait();  // Wait until the modal window is closed
            populateTableWithMovies();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editMovieDetails(){
        String title = titleField.getText();
        String genre = genreField.getText();
        String year = yearField.getText();
        String summary = summaryField.getText();

        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        int yearIn = Integer.parseInt(year);
        if(yearIn < 1888 || yearIn > currentYear){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Year");
            alert.setHeaderText("Current Process Canceled");
            alert.setContentText("Year is invalid!");
            populateMovieDetails(selectedMovie);
            alert.showAndWait();
            return;
        }

        if(!title.equals(selectedMovie.getName())){
            if(!database.getListByFilter("name", titleField.getText()).isEmpty()){
                cancelProcess("title");
                populateMovieDetails(selectedMovie);
                return;
            }
        }

        if(!summary.equals(selectedMovie.getSummary())){
            if( !database.getListByFilter("summary", summaryField.getText()).isEmpty()){
                cancelProcess("summary");
                populateMovieDetails(selectedMovie);
                return;
            }
        }

        if(selectedImageBlob != selectedMovie.getPoster()){
            if( !database.getListByFilter("poster", selectedImageBlob).isEmpty()){
                cancelProcess("poster");
                populateMovieDetails(selectedMovie);
                return;
            }
        }

        if (title.trim().length() == 0 || genre.isEmpty() || summary.trim().length() == 0 || year.isEmpty() || selectedImageBlob == null) {
            cancelProcess("Cannot be empty!"); 
            return;
        }

        database.updateById(selectedMovie.getId(), "name, year, genre, summary, poster", title, year, genre, summary, selectedImageBlob);

        
        populateTableWithMovies();

    }

    private void populateTableWithMovies() {
        List<Movie> movieList = database.getList();
        for(Movie a: movieList){
            System.out.println(a.getName());
            System.out.println(a.getId());
        }
        movieData = FXCollections.observableArrayList(movieList);

        moviesList.setItems(FXCollections.observableArrayList(
            movieData.stream().map(Movie::getName).collect(Collectors.toList())
        ));

    }

    private void populateMovieDetails(Movie movie) {
        titleField.setText(movie.getName());
        updateImage(movie.getPoster());
        genreField.setText(movie.getGenre());
        summaryField.setText(movie.getSummary());
        yearField.setText(movie.getYear());
    }

    private void updateImage(Blob imageBlob) {
        // Update the ImageView with a new image
        if(imageBlob == null){
            moviePoster.setImage(null);
            return;
        }
        InputStream inputStream;
        try {
            inputStream = imageBlob.getBinaryStream();
            Image image = new Image(inputStream);
            moviePoster.setImage(image);
            selectedImageBlob = imageBlob;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSelectedGenres(StringBuilder genres) {
        if(genres.length()!=0)
            genreField.setText(genres.toString());
    }

    private void handleDeleteMovie() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete Movie");
        confirmation.setHeaderText("Delete " + selectedMovie.getName());
        confirmation.setContentText("Are you sure you want to delete this movie?");

        database.deleteById(selectedMovie.getId());

        if (confirmation.showAndWait().get() == ButtonType.OK) {
            /* if (database.deleteById(selectedMovie.getID())) {
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Delete Failed");
                error.setContentText("Could not delete the movie. It may be scheduled for showing.");
                error.show();
            } */
        }
        populateTableWithMovies();
        selectedMovie = movieData.get(0);
        selectedImageBlob = movieData.get(0).getPoster();
        populateMovieDetails(selectedMovie);
    }

    private void cancelProcess(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        if(errorMessage.length() > 10){
            alert.setTitle(errorMessage);
            alert.setHeaderText("Current Process Canceled");
            alert.setContentText("All fields must be filled!");
            alert.showAndWait();
        }
        else{
            alert.setTitle("Existing " + errorMessage);
            alert.setHeaderText("Current Process Canceled");
            alert.setContentText("Movie " + errorMessage + " existing");
            alert.showAndWait();
        }
    }
}