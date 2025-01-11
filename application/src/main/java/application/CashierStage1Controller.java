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

public class CashierStage1Controller {

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

    private ObservableList<String> searchMethods = FXCollections.observableArrayList("Genre(s)", "Partial Title", "Full Title");

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
    
    @FXML
    private void handleConfirmSelection() {
        System.out.println("Confirm");
        cashierParent.getParent().handleScenes("cashierStage2");
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
            
        StaticSelection.staticMovie.setName(movie.getName());
        StaticSelection.staticMovie.setPoster(movie.getPoster());
        StaticSelection.staticMovie.setGenre(movie.getGenre());
        StaticSelection.staticMovie.setSummary(movie.getSummary());
        StaticSelection.staticMovie.setYear(movie.getYear());
        confirmButton.setDisable(false);
    }

    private void searchMovies(){
        
        int maxGridColumnNumber = 3;
        int columnIndex = 0;
        int rowIndex = 1;

        moviesGrid.getChildren().clear();

        String searchingMethod = searchMethodsSelector.getValue();

        String text = searchField.getText();

        if(searchingMethod.equals("Full Title")){

            movies = moviesDatabase.getListByFilter("name", text);
            System.out.println(movies.size());
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
                System.out.println("Resource URL: " + movieFXMLUrl);
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
