package application;

import java.util.HashMap;
import java.util.List;

import entities.Movie;
import entities.Product;
import entities.Session;
public class StaticSelection {
    public static Movie staticMovie = new Movie();
    public static Session staticSession = new Session();
    public static List<Integer> staticSeatIndeces;
    public static List<String> staticSeatValues;
    public static HashMap<Product, Integer> selectedProducts;
    public static String customerName;
    public static String customerSurname;
    public static List<String> allTickets;
    public static List<String> allProducts;
    public static double totalAmount;
    public static double totalTax;
}
