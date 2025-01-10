package entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Session {
    
    private int id;
    private String hall;
    private long seats;
    private String movie;
    private LocalDate sessionDate;
    private LocalTime sessionTime;

    public Session(){}

    public int getId(){
        return id;
    }

    public String getHall(){
        return hall;
    }

    public long getSeats(){
        return seats;
    }

    public String getMovie(){
        return movie;
    }

    public LocalDate getSessionDate(){
        return sessionDate;
    }

    public LocalTime getSessionTime(){
        return sessionTime;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setHall(String hall){
        this.hall = hall;
    }

    public void setSeats(long seats){
        this.seats = seats;
    }

    public void setMovie(String movie){
        this.movie = movie;
    }

    public void setSessionDate(LocalDate sessionDate){
        this.sessionDate = sessionDate;
    }

    public void setSessionTime(LocalTime sessionTime){
        this.sessionTime = sessionTime;
    }
}
