package entities;

public class PriceModifier {
    
    private int id;
    private String name;
    private int val;

    public PriceModifier(){}

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getVal(){
        return val;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setVal(int val){
        this.val = val;
    }


}
