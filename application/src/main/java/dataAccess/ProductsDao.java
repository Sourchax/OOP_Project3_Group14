package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Product;

/**
 * Database Access Object to provide database functionalities for {@link entities.Product} entity
 * <p>
 * This class extends the abstract definitions of {@link dataAccess.GenericDao} class.
 * </p>
 */
public class ProductsDao extends GenericDao<Product>{

    /**
     * Constructor for initializing with the table name.
     */
    public ProductsDao(){
        this.tableName = "products";
    }

    @Override
    public Product mapToObj(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setID(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setType(resultSet.getString("type"));
        product.setStock(resultSet.getInt("stock"));
        product.setPrice(resultSet.getFloat("price"));
        product.setImage(resultSet.getBlob("picture"));
        return product;
    }
    
}
