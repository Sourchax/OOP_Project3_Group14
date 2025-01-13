package entities;

import java.sql.Blob;
import java.time.LocalDateTime;

/**
 * Invoice entity
 * Represents an invoice with details about a purchase.
 * Contains information such as the purchaser's name, purchase date, session ID,
 * total spend, total tax, and an optional PDF representation of the invoice.
 */
public class Invoice {
    
    private int id;
    private String name;
    private String surname;
    private LocalDateTime purchaseDate;
    private int session;
    private double totalSpend;
    private double totalTax;
    private Blob pdf;

    /**
     * Default constructor for the Invoice class.
     */
    public Invoice(){}

    /* Getters */

    /**
     * Gets the unique identifier of the invoice.
     * @return the invoice ID.
     */
    public int getId(){
        return id;
    }

    /**
     * Gets the name of the purchaser.
     * @return the purchaser's name.
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the surname of the purchaser.
     * @return the purchaser's surname.
     */
    public String getSurname(){
        return surname;
    }

    /**
     * Gets the date and time of the purchase.
     * @return the purchase date and time.
     */
    public LocalDateTime getPurchaseDate(){
        return purchaseDate;
    }

    /**
     * Gets the session ID associated with the purchase.
     * @return the session ID.
     */
    public int getSession(){
        return session;
    }

    /**
     * Gets the PDF representation of the invoice.
     * @return the invoice as a Blob.
     */
    public Blob getPdf(){
        return pdf;
    }

    /**
     * Gets the total amount spent in the purchase.
     * @return the total spend amount.
     */
    public double getTotalSpend() {
        return totalSpend;
    }

    /**
     * Gets the total tax applied to the purchase.
     * @return the total tax amount.
     */
    public double getTotalTax() {
        return totalTax;
    }

    /* Setters */

    /**
     * Sets the unique identifier of the invoice.
     * @param id the invoice ID to set.
     */
    public void setID(int id){
        this.id = id;
    }

    /**
     * Sets the name of the purchaser.
     * @param name the purchaser's name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the surname of the purchaser.
     * @param surname the purchaser's surname to set.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Sets the date and time of the purchase.
     * @param purchaseDate the purchase date and time to set.
     */
    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Sets the session ID associated with the purchase.
     * @param session the session ID to set.
     */
    public void setSession(int session) {
        this.session = session;
    }

    /**
     * Sets the PDF representation of the invoice.
     * @param pdf the invoice Blob to set.
     */
    public void setPdf(Blob pdf) {
        this.pdf = pdf;
    }

    /**
     * Sets the total amount spent in the purchase.
     * @param totalSpend the total spend amount to set.
     */
    public void setTotalSpend(double totalSpend) {
        this.totalSpend = totalSpend;
    }

    /**
     * Sets the total tax applied to the purchase.
     * @param totalTax the total tax amount to set.
     */
    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }
}
