package application;

import java.util.HashMap;
import java.util.List;

import entities.Movie;
import entities.Product;
import entities.Session;

/**
 * A utility class that provides static references to various application-level 
 * data and selections, such as the selected movie, session details, seat selection, 
 * products, and customer information. 
 * These static fields are used across different parts of the application to 
 * store and access important state-related information.
 */
public class StaticSelection {

    /**
     * The selected movie for the current session or transaction.
     */
    public static Movie staticMovie = new Movie();

    /**
     * The selected session for the current transaction.
     */
    public static Session staticSession = new Session();

    /**
     * List of indices of selected seats during a session.
     */
    public static List<Integer> staticSeatIndeces;

    /**
     * List of seat values corresponding to the selected seat indices.
     */
    public static List<String> staticSeatValues;

    /**
     * A map that holds the selected products and their quantities.
     * The key is the product, and the value is the quantity.
     */
    public static HashMap<Product, Integer> selectedProducts;

    /**
     * The name of the customer who made the reservation or purchase.
     */
    public static String customerName;

    /**
     * The surname of the customer who made the reservation or purchase.
     */
    public static String customerSurname;

    /**
     * List of all ticket identifiers for the current transaction.
     */
    public static List<String> allTickets;

    /**
     * List of all product identifiers for the current transaction.
     */
    public static List<String> allProducts;

    /**
     * The total amount for the current transaction at the end
     */
    public static double totalAmount;

    /**
     * The total tax amount for the current transaction.
     */
    public static double totalTax;
}
