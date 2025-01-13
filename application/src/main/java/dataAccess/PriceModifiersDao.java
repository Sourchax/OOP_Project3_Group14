package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.PriceModifier;

/**
 * Database Access Object to provide database functionalities for {@link entities.PriceModifier} entity
 * <p>
 * This class extends the abstract definitions of {@link dataAccess.GenericDao} class.
 * </p>
 */
public class PriceModifiersDao extends GenericDao<PriceModifier>{

    /**
     * Constructor for initializing with the table name.
     */
    public PriceModifiersDao(){
        this.tableName = "price_modifiers";
    }

    @Override
    public PriceModifier mapToObj(ResultSet resultSet) throws SQLException {
        PriceModifier priceModifier = new PriceModifier();
        priceModifier.setId(resultSet.getInt("id"));
        priceModifier.setName(resultSet.getString("name"));
        priceModifier.setVal(resultSet.getInt("val"));
        return priceModifier;
    }
    


}
