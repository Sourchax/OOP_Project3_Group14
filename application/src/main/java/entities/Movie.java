package entities;

import java.sql.Blob;

public class Movie {
    
    private String name;
    private String genre;
    private String summary;
    private Blob poster;

    public Movie(){}


    public String getName(){
        return name;
    }

    public String getGenre(){
        return genre;
    }

    public String getSummary(){
        return summary;
    }

    public Blob getPoster(){
        return poster;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public void setPoster(Blob poster){
        this.poster = poster;
    }
}
