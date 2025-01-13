package entities;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Session entity
 * Represents a session for a movie screening.
 * Contains details such as session ID, hall, number of seats, movie name, session date, and session time.
 */
public class Session {
    
    private int id;
    private String hall;
    private long seats;
    private String movie;
    private LocalDate sessionDate;
    private LocalTime sessionTime;

    /**
     * Default constructor for the Session class.
     */
    public Session(){}

    /**
     * Gets the unique identifier of the session.
     * @return the session ID.
     */
    public int getId(){
        return id;
    }

    /**
     * Gets the hall where the session is held.
     * @return the hall name.
     */
    public String getHall(){
        return hall;
    }

    /**
     * Gets the number of seats available for the session.
     * @return the number of seats.
     */
    public long getSeats(){
        return seats;
    }

    /**
     * Gets the name of the movie being screened in the session.
     * @return the movie name.
     */
    public String getMovie(){
        return movie;
    }

    /**
     * Gets the date of the session.
     * @return the session date.
     */
    public LocalDate getSessionDate(){
        return sessionDate;
    }

    /**
     * Gets the time of the session.
     * @return the session time.
     */
    public LocalTime getSessionTime(){
        return sessionTime;
    }

    /**
     * Sets the unique identifier of the session.
     * @param id the session ID to set.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Sets the hall where the session is held.
     * @param hall the hall name to set.
     */
    public void setHall(String hall){
        this.hall = hall;
    }

    /**
     * Sets the number of seats available for the session.
     * @param seats the number of seats to set.
     */
    public void setSeats(long seats){
        this.seats = seats;
    }

    /**
     * Sets the name of the movie being screened in the session.
     * @param movie the movie name to set.
     */
    public void setMovie(String movie){
        this.movie = movie;
    }

    /**
     * Sets the date of the session.
     * @param sessionDate the session date to set.
     */
    public void setSessionDate(LocalDate sessionDate){
        this.sessionDate = sessionDate;
    }

    /**
     * Sets the time of the session.
     * @param sessionTime the session time to set.
     */
    public void setSessionTime(LocalTime sessionTime){
        this.sessionTime = sessionTime;
    }
}
