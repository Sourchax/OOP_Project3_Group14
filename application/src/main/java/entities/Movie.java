package entities;

import java.sql.Blob;

public class Movie {
    
    private int id;
    private String name;
    private String year;
    private String genre;
    private String summary;
    private Blob poster;

    public Movie(){}


    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getYear(){
        return year;
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

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setYear(String releaseYear){
        this.year = releaseYear;
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
