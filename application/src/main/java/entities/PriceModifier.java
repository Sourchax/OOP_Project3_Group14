package entities;

/**
 * PriceModifier entity
 * Represents a price modifier that can adjust pricing values.
 * Contains an identifier, a name for the modifier, and the value of the modification.
 */
public class PriceModifier {
    
    private int id;
    private String name;
    private int val;

    /**
     * Default constructor for the PriceModifier class.
     */
    public PriceModifier(){}

    /**
     * Gets the unique identifier of the price modifier.
     * @return the price modifier ID.
     */
    public int getId(){
        return id;
    }

    /**
     * Gets the name of the price modifier.
     * @return the name of the price modifier.
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the value of the price modifier.
     * @return the value of the price modifier.
     */
    public int getVal(){
        return val;
    }

    /**
     * Sets the unique identifier of the price modifier.
     * @param id the price modifier ID to set.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Sets the name of the price modifier.
     * @param name the name of the price modifier to set.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the value of the price modifier.
     * @param val the value of the price modifier to set.
     */
    public void setVal(int val){
        this.val = val;
    }
}
