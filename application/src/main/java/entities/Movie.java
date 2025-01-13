package entities;

import java.sql.Blob;

/**
 * Movie entity
 * Represents a movie with details such as its ID, name, release year, genre, summary, and poster image.
 */
public class Movie {
    
    private int id;
    private String name;
    private String year;
    private String genre;
    private String summary;
    private Blob poster;

    /**
     * Default constructor for the Movie class.
     */
    public Movie(){}

    /**
     * Gets the unique identifier of the movie.
     * @return the movie ID.
     */
    public int getId(){
        return id;
    }

    /**
     * Gets the name of the movie.
     * @return the movie name.
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the release year of the movie.
     * @return the release year of the movie.
     */
    public String getYear(){
        return year;
    }

    /**
     * Gets the genre of the movie.
     * @return the genre of the movie.
     */
    public String getGenre(){
        return genre;
    }

    /**
     * Gets a brief summary of the movie.
     * @return the summary of the movie.
     */
    public String getSummary(){
        return summary;
    }

    /**
     * Gets the poster of the movie as a Blob.
     * @return the movie poster.
     */
    public Blob getPoster(){
        return poster;
    }

    /**
     * Sets the unique identifier of the movie.
     * @param id the movie ID to set.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Sets the name of the movie.
     * @param name the movie name to set.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the release year of the movie.
     * @param releaseYear the release year of the movie to set.
     */
    public void setYear(String releaseYear){
        this.year = releaseYear;
    }

    /**
     * Sets the genre of the movie.
     * @param genre the movie genre to set.
     */
    public void setGenre(String genre){
        this.genre = genre;
    }

    /**
     * Sets a brief summary of the movie.
     * @param summary the movie summary to set.
     */
    public void setSummary(String summary){
        this.summary = summary;
    }

    /**
     * Sets the poster of the movie as a Blob.
     * @param poster the movie poster to set.
     */
    public void setPoster(Blob poster){
        this.poster = poster;
    }
}
