package entities;

import java.sql.Blob;
import java.time.LocalDateTime;

public class Invoice {
    
    private int id;
    private String name;
    private String surname;
    private LocalDateTime purchaseDate;
    private int session;
    private double totalSpend;
    private double totalTax;

    private Blob pdf;

    public Invoice(){}

    /* Getters */
    

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public LocalDateTime getPurchaseDate(){
        return purchaseDate;
    }

    public int getSession(){
        return session;
    }

    public Blob getPdf(){
        return pdf;
    }

    public double getTotalSpend() {
        return totalSpend;
    }

    public double getTotalTax() {
        return totalTax;
    }

    /* Setters */

    public void setID(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public void setPdf(Blob pdf) {
        this.pdf = pdf;
    }

    public void setTotalSpend(double totalSpend) {
        this.totalSpend = totalSpend;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

}
