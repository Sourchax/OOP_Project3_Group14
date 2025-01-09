package entities;

import java.sql.Date;
import java.sql.Time;

public class Session {
    
    private int id;
    private String hall;
    private long seats;
    private String movie;
    private Date sessionDate;
    private Time sessionTime;

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

    public Date getSessionDate(){
        return sessionDate;
    }

    public Time getSessionTime(){
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

    public void setSessionDate(Date sessionDate){
        this.sessionDate = sessionDate;
    }

    public void setSessionTime(Time sessionTime){
        this.sessionTime = sessionTime;
    }
}
