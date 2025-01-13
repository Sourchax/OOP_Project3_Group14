package entities;

import java.sql.Blob;

public class Product {
    
    private int id;
    private String name;
    private String type; 
    private int stock;
    private float price;
    private Blob image;

    /**
     * Default Constructor
     */
    public Product(){}

    /**
     * get id
     * @return id
     */
    public int getId(){
        return id;
    }

        /**
     * get Name
     * @return Name
     */
    public String getName(){
        return name;
    }


    /**
     * get type
     * @return type
     */
    public String getType(){
        return type;
    }

     /**
     * get stock
     * @return stock
     */
    public int getStock(){
        return stock;
    }

    /**
     * get price
     * @return price
     */
    public float getPrice(){
        return price;
    }
    
    /**
     * get image
     * @return image
     */
    public Blob getImage(){
        return image;
    }

    /**
     * set id
     * @param id to set
     */
    public void setID(int id){
        this.id = id;
    }

    /**
     * set name
     * @param name to set
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * set type
     * @param type to set
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * set stock
     * @param stock to set
     */
    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     * set price
     * @param price to set
     */
    public void setPrice(float price){
        this.price = price;
    }

    /**
     * set image
     * @param image to set
     */
    public void setImage(Blob image){
        this.image = image;
    }
}
