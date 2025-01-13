package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Invoice;

/**
 * Database Access Object to provide database functionalities for {@link entities.Invoice} entity
 * <p>
 * This class extends the abstract definitions of {@link dataAccess.GenericDao} class.
 * </p>
 */
public class InvoicesDao extends GenericDao<Invoice>{

    /**
     * Constructor for initializing with the table name.
     */
    public InvoicesDao(){
        this.tableName = "invoices";
    }

    @Override
    public Invoice mapToObj(ResultSet resultSet) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setName(resultSet.getString("name"));
        invoice.setSurname(resultSet.getString("surname"));
        invoice.setPurchaseDate(resultSet.getTimestamp("purchaseDate").toLocalDateTime()); //change this
        invoice.setPdf(resultSet.getBlob("pdf"));
        invoice.setSession(resultSet.getInt("session")); //change this
        invoice.setTotalSpend(resultSet.getDouble("totalSpend"));
        invoice.setTotalTax(resultSet.getDouble("totalTax"));
        invoice.setID(resultSet.getInt("id"));
        return invoice;
    }
    
}
