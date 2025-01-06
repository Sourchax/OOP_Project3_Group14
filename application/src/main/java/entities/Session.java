package entities;

import java.time.LocalDateTime;

public class Session {
    
    private char hall;
    private long seats;
    private String movie;
    private LocalDateTime sessionTime;

    public Session(){}

    public char getHall(){
        return hall;
    }

    public long getSeats(){
        return seats;
    }

    public String getMovie(){
        return movie;
    }

    public LocalDateTime getSessionTime(){
        return sessionTime;
    }

    public void setHall(char hall){
        this.hall = hall;
    }

    public void setSeats(long seats){
        this.seats = seats;
    }

    public void setMovie(String movie){
        this.movie = movie;
    }
    public void setSessionTime(LocalDateTime sessionTime){
        this.sessionTime = sessionTime;
    }
}
