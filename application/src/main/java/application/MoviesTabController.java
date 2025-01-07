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
import javafx.scene.control.Button;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import dataAccess.GenericDao;
import dataAccess.MoviesDao;
import entities.*;


import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;


public class MoviesTabController {

    @FXML
    private Button addMovieButton;

    @FXML
    private Button editDetailsButton;
    @FXML
    private Button selectNewPosterButton;

    @FXML
    private ListView<String> moviesList;

    @FXML
    private TextField titleField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField genreField;

    @FXML
    private TextArea summaryField;

    @FXML
    private ImageView moviePoster;

    private ObservableList<Movie> movieData;

    private Movie selectedMovie;

    private File selectedPoster;
    
    private MoviesDao database;

    @FXML
    private void initialize() {
        addMovieButton.setOnAction(event -> onAddNewMovie());
        editDetailsButton.setOnAction(event -> editMovieDetails());
        selectNewPosterButton.setOnAction(event -> selectNewPoster());

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
        populateMovieDetails(selectedMovie);
    }

    @FXML
    private void handleMovieSelection(MouseEvent event) {
        int index = moviesList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            selectedMovie = movieData.get(index);
            populateMovieDetails(selectedMovie);
        }
        selectedPoster = null;
    }

    @FXML
    private void selectNewPoster(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));

        selectedPoster = fileChooser.showOpenDialog(null);
        if (selectedPoster != null) {
            try {
                Image image = new Image(new FileInputStream(selectedPoster));
                moviePoster.setImage(image);
            } catch (FileNotFoundException e) {
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
        String summary = summaryField.getText();

        if (title.isEmpty() || genre.isEmpty() || summary.isEmpty() || selectedPoster == null) {
            System.out.println("Please fill all fields and select an image.");

            return;
        }

        try {
            FileInputStream file = new FileInputStream(selectedPoster);
        
            byte[] imageBytes = file.readAllBytes();
            Blob imageBlob = new SerialBlob(imageBytes);
            database.updateById(selectedMovie.getID(), "name, genre, summary, poster", title, genre, summary, imageBlob);
            
        } catch (Exception e) {
            System.out.println("HELP ME!");
        }
        
        populateTableWithMovies();

    }

    private void populateTableWithMovies() {
        List<Movie> movieList = database.getList();
        for(Movie a: movieList){
            System.out.println(a.getName());
            System.out.println(a.getID());
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
        yearField.setText(movie.getReleaseYear());
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}