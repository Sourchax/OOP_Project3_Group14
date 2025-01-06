package entities;

import java.sql.Blob;

public class Product {
    
    private String name;
    private String type; 
    private int stock;
    private float price;
    private Blob image;

    public Product(){}


    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public int getStock(){
        return stock;
    }

    public float getPrice(){
        return price;
    }

    public Blob getImage(){
        return image;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public void setPrice(float price){
        this.price = price;
    }

    public void setImage(Blob image){
        this.image = image;
    }


}
