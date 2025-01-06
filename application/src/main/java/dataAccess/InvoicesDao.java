package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Invoice;

public class InvoicesDao extends GenericDao<Invoice>{

    InvoicesDao(){
        this.tableName = "invoices";
    }

    @Override
    public Invoice mapToObj(ResultSet resultSet) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setName(resultSet.getString("name"));
        invoice.setSurname(resultSet.getString("surname"));
        invoice.setPurchaseDate(null); //change this
        invoice.setPdf(resultSet.getBlob("pdf"));
        invoice.setSession(null); //change this
        return invoice;
    }
    
}
