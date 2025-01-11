package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.PriceModifier;

public class PriceModifiersDao extends GenericDao<PriceModifier>{

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
