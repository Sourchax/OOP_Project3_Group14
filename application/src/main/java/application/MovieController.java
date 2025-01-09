package application;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import entities.Movie;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MovieController {

    private Movie movie;

    @FXML
    private ImageView moviePoster;

    private MovieListener movieListener;

    @FXML
    private void click(MouseEvent mouseEvent) {
        System.out.println("movie clicked: " + movie.getName());
        movieListener.clickListener(movie);
            
    }
    public void setmovieData(Movie movie, MovieListener movieListener) {
        this.movie = movie;
        this.movieListener = movieListener;
        //nameLabel.setText(movie.getName());
        updateImage(movie.getPoster());
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
