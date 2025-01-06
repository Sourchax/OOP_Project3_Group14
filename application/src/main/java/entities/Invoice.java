package entities;

import java.sql.Blob;
import java.time.LocalDateTime;

public class Invoice {
    
    private String name;
    private String surname;
    private LocalDateTime purhcaseDate;
    private Session session;
    private Blob pdf;

    public Invoice(){}

    /* Getters */

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public LocalDateTime getPurchaseDate(){
        return purhcaseDate;
    }

    public Session getSession(){
        return session;
    }

    public Blob getPdf(){
        return pdf;
    }

    /* Setters */


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purhcaseDate = purchaseDate;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setPdf(Blob pdf) {
        this.pdf = pdf;
    }

}
