package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import dataAccess.MoviesDao;
import entities.Movie;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller class for the Cashier Stage 1 scene in the application.
 * This class manages the search, selection, and confirmation of movies in the interface.
 * It handles user interactions with the UI components and communicates with the MoviesDao
 * to fetch and filter movies based on user input.
 */
public class CashierStage1Controller {

    /**
     * TextField for entering search terms.
     * Used by the user to input keywords for movie search.
     */
    @FXML
    private TextField searchField;

    /**
     * Button for searching movies by their genres.
     * Triggers a genre-based search when clicked.
     */
    @FXML
    private Button searchByGenreButton;

    /**
     * Button for searching movies by a partial title.
     * Allows the user to find movies containing the input text in their titles.
     */
    @FXML
    private Button searchByPartialTitleButton;

    /**
     * Button for searching movies by their full title.
     * Finds movies with titles that exactly match the input text.
     */
    @FXML
    private Button searchByFullTitleButton;

    /**
     * Label displaying the name of the currently selected movie.
     * Updates when a movie is selected from the search results.
     */
    @FXML
    private Label selectedMovieLabel;

    /**
     * Button to confirm the selected movie.
     * Enables the user to proceed to the next stage with the selected movie.
     */
    @FXML
    private Button confirmButton;

    /**
     * ListView displaying the search results as a list of movie titles.
     * Updates dynamically based on the search criteria.
     */
    @FXML
    private ListView<String> searchResultsListView;

    /**
     * ImageView displaying the poster of the currently selected movie.
     * Updates when a movie is selected.
     */
    @FXML
    private ImageView selectedMoviePoster;

    /**
     * Label displaying the title of the currently selected movie.
     * Updates when a movie is selected.
     */
    @FXML
    private Label selectedMovieTitle;

    /**
     * Label displaying the genres of the currently selected movie.
     * Shows a comma-separated list of genres associated with the movie.
     */
    @FXML
    private Label selectedMovieGenres;

    /**
     * Label displaying a summary of the currently selected movie.
     * Provides a brief description or plot summary.
     */
    @FXML
    private Label selectedMovieSummary;

    /**
     * ComboBox for selecting the search method.
     * Allows the user to choose between "Genre(s)", "Partial Title", or "Full Title" search options.
     */
    @FXML
    private ComboBox<String> searchMethodsSelector;

    /**
     * ScrollPane containing the movies grid.
     * Provides scrollable access to the movie cards displayed in a grid layout.
     */
    @FXML
    private ScrollPane moviesScrollPane;

    /**
     * Button to initiate the search process.
     * Triggers the search logic based on the selected method and input text.
     */
    @FXML
    private Button searchButton;

    /**
     * GridPane displaying movie cards in a grid format.
     * Each card represents a movie and provides details such as the poster and title.
     */
    @FXML
    private GridPane moviesGrid;

    /**
     * The currently selected movie object.
     * Holds the data of the movie that the user has chosen.
     */
    public Movie selectedMovie;

    /**
     * Data Access Object for retrieving movie data.
     * Provides methods to fetch and filter the list of movies.
     */
    private MoviesDao moviesDatabase;

    /**
     * List of movies retrieved from the database.
     * Updated based on the search criteria and used to populate the grid or list view.
     */
    private List<Movie> movies =  new ArrayList<>();

    /**
     * Listener interface for handling movie selection events.
     * Used to respond to user interactions with movie cards.
     */
    private MovieListener movieListener;

    /**
     * ObservableList containing the search method options.
     * Populates the ComboBox with available search methods: "Genre(s)", "Partial Title", "Full Title".
     */
    private ObservableList<String> searchMethods = FXCollections.observableArrayList("Genre(s)", "Partial Title", "Full Title");

    /**
     * Initializes the controller class. This method is automatically called after the FXML
     * file has been loaded. It sets up listeners, initializes the movie database, and populates UI components.
     */
    @FXML
    private void initialize(){
        
        confirmButton.setDisable(true);

        searchButton.setDisable(true);

        searchField.setDisable(true);
        
        searchMethodsSelector.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                searchField.setDisable(false);
                searchButton.setDisable(false);
            }
            else{
                searchField.setDisable(true);
            }
        });
        
        ObservableList<String> naber = FXCollections.observableArrayList("Full Title", "Partial Title", "Genre(s)");
        
        searchMethodsSelector.setItems(naber);
        
        
        
        searchButton.setOnAction(event -> searchMovies());
        
        searchMethodsSelector.setItems(searchMethods);
        
        confirmButton.setOnAction(event -> handleConfirmSelection());

        
        moviesDatabase = new MoviesDao();
        this.movies = moviesDatabase.getList();
    
        if (movies.size() > 0) {
            movieListener = new MovieListener() {
                @Override
                public void clickListener(Movie movie) {
                    setChosenMovie(movie);
                }
            };
        }
    }
    
    /**
     * Handles the action of confirming the selected movie.
     * This method is triggered when the "Confirm" button is clicked.
     */
    @FXML
    private void handleConfirmSelection() {
        System.out.println("Confirm");
        cashierParent.getParent().handleScenes("cashierStage2");
    }

    /**
     * Triggered when the search button is clicked. Currently empty, 
     * but can be used for additional handling during the search click.
     * 
     * @param event The mouse event that triggers this method.
     */
    @FXML
    void onSearchClicked(MouseEvent event) {
        
    }   

    /**
     * Handles searching movies by their genre(s). 
     * Logs a message to the console indicating the method was triggered.
     */
    @FXML
    void handleSearchByGenre() {
        System.out.println("handleSearchByGenre");
    }

    /**
     * Handles searching movies by a partial title. 
     * Logs a message to the console indicating the method was triggered.
     */
    @FXML
    void handleSearchByPartialTitle() {
        System.out.println("handleSearchByPartialTitle");
    }

    /**
     * Handles searching movies by their full title. 
     * Logs a message to the console indicating the method was triggered.
     */
    @FXML
    void handleSearchByFullTitle() {
        System.out.println("handleSearchByFullTitle");
    }

    /**
     * Displays the details of the selected movie in a modal dialog.
     * Enables or disables the confirm button based on the movie selection.
     * 
     * @param movie The movie selected by the user.
     */
    public void setChosenMovie(Movie movie) {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/cashier/movieDetails.fxml"));
        Parent root;
        try {
            StaticSelection.staticMovie.setName(movie.getName());
            StaticSelection.staticMovie.setPoster(movie.getPoster());
            StaticSelection.staticMovie.setGenre(movie.getGenre());
            StaticSelection.staticMovie.setSummary(movie.getSummary());
            StaticSelection.staticMovie.setYear(movie.getYear());

            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Movie Details");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if(StaticSelection.staticMovie.getGenre() != null){
                confirmButton.setDisable(false);
            }
            else{
                confirmButton.setDisable(true);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches for movies based on the selected search method and the input text.
     * Populates the grid with matching movies, filtering by full title, 
     * partial title, or genres as specified by the user.
     */
    private void searchMovies(){
        
        int maxGridColumnNumber = 3;
        int columnIndex = 0;
        int rowIndex = 1;

        moviesGrid.getChildren().clear();

        String searchingMethod = searchMethodsSelector.getValue();

        String text = searchField.getText();

        if(searchingMethod.equals("Full Title")){

            movies = moviesDatabase.getListByFilter("name", text);
        }
        else if (searchingMethod.equals("Genre(s)")) {
            movies = moviesDatabase.getList();
        
            List<String> genreList = handleGenre(searchField.getText());
        
            Iterator<Movie> iterator = movies.iterator();
        
            while (iterator.hasNext()) {
                Movie movie = iterator.next();
                
                List<String> movieGenres = handleGenre(movie.getGenre());
        
                boolean matchesAnyGenre = false;
                for (String searchGenre : genreList) {
                    for (String movieGenre : movieGenres) {
                        if (movieGenre.equalsIgnoreCase(searchGenre)) {
                            matchesAnyGenre = true;
                            break;
                        }
                    }
                    if (matchesAnyGenre) {
                        break;
                    }
                }

                if (!matchesAnyGenre) {
                    iterator.remove();
                }
            }
        }
        else if(searchingMethod.equals("Partial Title")){
            movies = moviesDatabase.getList();
            for(int i = movies.size()-1; i>=0; i--){
                if(!movies.get(i).getName().toLowerCase().contains(searchField.getText().toLowerCase())){
                    movies.remove(i);
                }
            }
        }

        for (int i = 0; i < movies.size(); i++) {

            {
                URL movieFXMLUrl = MovieController.class.getResource("fxml/cashier/movie.fxml");
                if (movieFXMLUrl != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(movieFXMLUrl);
                    try {
                        AnchorPane anchorPane = fxmlLoader.load();  
        
                        MovieController movieController = fxmlLoader.getController();
                        movieController.setmovieData(movies.get(i), movieListener);
        
                        if (columnIndex == maxGridColumnNumber) {  
                            columnIndex = 0;
                            rowIndex++;
                        }
        
                        moviesGrid.add(anchorPane, columnIndex++, rowIndex);
                        
                    } catch (Exception e) {
                    }
                } else {
                    System.out.println("No such fxml");
                }

            }
        }
    }

    /**
     * Splits a given text into a list of genres. 
     * Genres are separated by commas, and spaces are ignored.
     * 
     * @param text The text containing genres to be parsed.
     * @return A list of individual genre strings.
     */
    private List<String> handleGenre(String text){

        List<String> ans = new ArrayList<>();
        String temp = "";
        for(int i = 0; i<text.length(); i++){
            if(text.charAt(i) == ','){
                ans.add(temp);
                temp = "";
            }
            else if(text.charAt(i) == ' '){
                
            }
            else{
                temp += text.charAt(i);
            }
        }

        ans.add(temp);

        return ans;
    }
}
