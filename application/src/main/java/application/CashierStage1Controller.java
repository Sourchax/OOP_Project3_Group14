package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dataAccess.MoviesDao;
import entities.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class CashierStage1Controller implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private Button searchByGenreButton;

    @FXML
    private Button searchByPartialTitleButton;

    @FXML
    private Button searchByFullTitleButton;

    @FXML
    private Label selectedMovieLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private ListView<String> searchResultsListView;

    @FXML
    private ImageView selectedMoviePoster;

    @FXML
    private Label selectedMovieTitle;

    @FXML
    private Label selectedMovieGenres;

    @FXML
    private Label selectedMovieSummary;

    @FXML
    private ComboBox<String> searchMethodsSelector;

    @FXML
    private ScrollPane moviesScrollPane;

    @FXML
    private Button searchButton;

    @FXML
    private GridPane moviesGrid;

    public Movie selectedMovie;

    private MoviesDao moviesDatabase;

    private List<Movie> movies =  new ArrayList<>();

    private MovieListener movieListener;

    private ObservableList<String> searchMethods = FXCollections.observableArrayList("Genre", "Partial Title", "Full Title");

    @FXML
    private void initialize(){
        System.out.println("initial");
        
        searchMethodsSelector.setItems(searchMethods);

        confirmButton.setOnAction(event -> handleConfirmSelection());

        searchMethodsSelector.setOnAction(event -> onSelection());
    }

    @FXML
    private void handleConfirmSelection() {
        System.out.println("Confirm");
        cashierParent.getParent().handleScenes("cashierStage2");
    }
    @FXML
    void onSelection() {
        String searchMethod = searchMethodsSelector.getValue();
        switch (searchMethod) {
            case "Genre":
                handleSearchByGenre();
                break;
            case "Partial Title":
                handleSearchByPartialTitle();
                break;
            case "Full Title":
                handleSearchByFullTitle();
                break;
            default:
                System.out.println("Unknown search method: " + searchMethod);
                break;
        }
    }


    @FXML
    void onSearchClicked(MouseEvent event) {
        
    }   

    @FXML
    void handleSearchByGenre() {
        System.out.println("handleSearchByGenre");
    }

    @FXML
    void handleSearchByPartialTitle() {
        System.out.println("handleSearchByPartialTitle");
    }

    @FXML
    void handleSearchByFullTitle() {
        System.out.println("handleSearchByFullTitle");
    }

    public void setChosenMovie(Movie movie) {
            
        StaticMovie.staticMovie.setName(movie.getName());
        StaticMovie.staticMovie.setPoster(movie.getPoster());
        StaticMovie.staticMovie.setGenre(movie.getGenre());
        StaticMovie.staticMovie.setSummary(movie.getSummary());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initial stage1 with parameters");
        moviesDatabase = new MoviesDao();
        this.movies = moviesDatabase.getList();
        System.out.println(movies.size());

        // select the first movie if there is any movie
        if (movies.size() > 0) {
            selectedMovie = movies.get(0);
            setChosenMovie(movies.get(0));
            movieListener = new MovieListener() {
                @Override
                public void clickListener(Movie movie) {
                    setChosenMovie(movie);
                }
            };
        }

        //  number of gridColumnNumber for gridPane
        int maxGridColumnNumber = 3;
        int columnIndex = 0;
        int rowIndex = 1;

        try {
            // Load fxml for each movie
            for (int i = 0; i < movies.size(); i++) {
                URL movieFXMLUrl = MovieController.class.getResource("fxml/cashier/movie.fxml");
                System.out.println("Resource URL: " + movieFXMLUrl);

                if (movieFXMLUrl != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(movieFXMLUrl);
                    AnchorPane anchorPane = fxmlLoader.load();  

                    // movie controller
                    MovieController movieController = fxmlLoader.getController();
                    movieController.setmovieData(movies.get(i), movieListener);

                    // reset the column index when it becomes the maximumGridColumnNumber
                    if (columnIndex == maxGridColumnNumber) {  
                        columnIndex = 0;
                        rowIndex++;
                    }

                    // add the loaded fxml to the grid
                    moviesGrid.add(anchorPane, columnIndex++, rowIndex);
                } else {
                    System.out.println("No such fxml");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogOut() {
        // Method to handle logout
    }
}
