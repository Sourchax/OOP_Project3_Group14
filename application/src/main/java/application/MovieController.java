package application;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import entities.Movie;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Controller class for handling movie-related actions and displaying movie data.
 * This class is responsible for displaying the movie poster and handling user interaction with the movie.
 */
public class MovieController {

    private Movie movie;  // The movie object associated with this controller.

    @FXML
    private ImageView moviePoster;  // The ImageView that displays the movie poster.

    @FXML
    private VBox movieBox;  // The VBox that contains the movie poster.

    private MovieListener movieListener;  // The listener to handle movie click events.

    /**
     * Handles the click event on the movie poster.
     * 
     * @param mouseEvent The MouseEvent triggered by the click.
     */
    @FXML
    private void click(MouseEvent mouseEvent) {
        System.out.println("movie clicked: " + movie.getName());
        movieListener.clickListener(movie);
            
    }

    /**
     * Sets the movie data and listener for the movie controller.
     * 
     * @param movie The movie object to be displayed.
     * @param movieListener The listener to handle movie click events.
     */
    public void setmovieData(Movie movie, MovieListener movieListener) {
        this.movie = movie;
        this.movieListener = movieListener;
        updateImage(movie.getPoster());
    }

    /**
     * Updates the movie poster image in the ImageView.
     * 
     * @param imageBlob The Blob containing the image data.
     */
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
