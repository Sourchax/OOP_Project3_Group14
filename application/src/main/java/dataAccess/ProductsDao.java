package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Product;

public class ProductsDao extends GenericDao<Product>{

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
        product.setImage(resultSet.getBlob("image"));
        return product;
    }
    
}
