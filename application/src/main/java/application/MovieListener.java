package application;

import entities.Movie;
/**
 * Interface to define a listener for handling movie-related interactions.
 * This interface provides a method to handle click events on a movie item.
 * 
 * <p>Implement this interface to define the behavior when a movie is clicked.</p>
 * 
 * @see Movie
 */

public interface MovieListener {
     /**
     * Called when a movie item is clicked.
     * 
     * @param movie the {@link Movie} object associated with the clicked item.
     */
    public void clickListener(Movie movie);
}
