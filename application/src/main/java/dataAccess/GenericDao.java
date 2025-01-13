package dataAccess;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utilities.Regex;
/**
 * Generic database access library
 * <p>
 * Utilizes a generic design to access database for the entities. 
 * It is an abstract class that needs to be implemented and configured by a class that specializes for the related entity.
 * configurations needed: 
 *  - tableName field needs to be set with the table name of the database that entity will be connected to.
 *  - mapToObj abstract function to map the values taken from the database to the related entity.
 * </p>
 */
public abstract class GenericDao<T> {
    protected String tableName;

    /**
     * Abstract function to map values from database to entity.
     * @param resultSet Object holding values from database.
     * @return an object of related entity.
     * @throws SQLException 
     */
    public abstract T mapToObj(ResultSet resultSet) throws SQLException;

    /**
     * Prepares the given PreparedStatement with the values in the given Object array.
     * @param statement PreparedStatement to be prepared.
     * @param vararg Object array that contains the values.
     * @throws SQLException
     */
    public void mapToStatement(PreparedStatement statement, Object[] vararg) throws SQLException {
        for(int i = 1; i <= vararg.length; i++){
            statement.setObject(i, vararg[i-1]);
        }
    }

    /**
     * Prepares the given PreparedStatement with the values of the given object.
     * utilizes reflection to read the fields and methods of the given object and invoke.
     * @param statement PreparedStatement to be prepared.
     * @param obj Object to take the values from.
     * @throws Exception
     */
    public void mapToStatement(PreparedStatement statement, T obj) throws Exception {
        Class<?> cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        int i = 1;
        for(Field f : fields){
            String fName = f.getName();
            if(fName != "id"){
                Method m = cla.getMethod("get" + fName.substring(0, 1).toUpperCase() + fName.substring(1));
                statement.setObject(i++, m.invoke(obj));
            }
        }
    }

    /**
     * Inserts fields of the given object to the table specified in the child class.
     * @param obj Object to take values from.
     * @see #mapToStatement(PreparedStatement, Object)
     * @see #Regex.formatColumns(obj)
     */
    public void insert(T obj){
        String[] queryParts = Regex.formatColumns(obj);
        String query = "INSERT INTO " + tableName + queryParts[0] + " VALUES " + queryParts[1] + ";";
        try{
            Connection connection = DBConnector.createConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            mapToStatement(statement, obj);
            statement.executeUpdate();
        
        } catch(Exception e) {
            //need error handler
            e.printStackTrace();
        }
    }

    /**
     * Deletes the entry with the given id from the database.
     * @param id id of the entry.
     */
    public void deleteById(int id){
        String query = "DELETE FROM " + tableName + " WHERE id = ?;";

        try{
            Connection connection = DBConnector.createConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Updates the entry with the given id of the database. 
     * Columns that will be edited and the values they will be updated with need to be provided.
     * Columns are provided in the following format: "column1, column2, column3"
     * Values are provided as parameters for the variable argument.
     * @param id id of the entry
     * @param columnUpdate Columns of the database to be upgraded as a string
     * @param vararg Variable argument containing the values to overwrite with.
     * @see #mapToStatement(PreparedStatement, Object[])
     */
    public void updateById(int id, String columnUpdate, Object... vararg){
        
        try{
            String query = "UPDATE " + tableName + " SET " + Regex.formatColumns(columnUpdate, "update") + " WHERE id = ?;";
            
            System.out.println(query);
            Connection connection = DBConnector.createConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            mapToStatement(statement, vararg);
            statement.setInt(vararg.length+1, id);
            statement.executeUpdate();
        
        } catch(Exception e) {
            //need error handler
            e.printStackTrace();
        }
    }

    /**
     * Gets all entries of the related database table and return as a list of objects
     * @return list of objects with the entry data.
     * @see #mapToObj(ResultSet)
     */
    public List<T> getList(){
        String query = "SELECT * FROM " + tableName + ";";
        
        try{
            Connection connection = DBConnector.createConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            List<T> arr = new ArrayList<>();
            while(resultSet.next()){
                arr.add(mapToObj(resultSet));
            }
            
            return arr;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets all entries of the related database table that fits the provided filter and return as a list of objects
     * Columns that will be filtered with and the values to filter with need to be provided.
     * Columns are provided in the following format: "column1, column2, column3"
     * Values are provided as parameters for the variable argument.
     * @param columns columns of the table in given string format.
     * @param vararg variable argument that contains the values for filter.
     * @return list of objects with the entry data.
     * @see #mapToStatement(PreparedStatement, Object[])
     * @see #mapToObj(ResultSet)
     */
    public List<T> getListByFilter(String columns, Object... vararg){

        try{
            String query = "SELECT * FROM " + tableName + " WHERE " + Regex.formatColumns(columns, "filter") + ";";
            
            Connection connection = DBConnector.createConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            mapToStatement(statement, vararg);
            ResultSet resultSet = statement.executeQuery();
            
            List<T> arr = new ArrayList<>();
            while(resultSet.next()){
                arr.add(mapToObj(resultSet));
            }
            
            return arr;
            
        } catch (Exception e){
            //need error handler
            e.printStackTrace();
            return null;
        }
        
    }

    /**
     * Get a single entry from the related database table that fits the provided filter and return it.
     * Columns that will be filtered with and the values to filter with need to be provided.
     * Columns are provided in the following format: "column1, column2, column3"
     * Values are provided as parameters for the variable argument.
     * Prints an error message if the result set of the query contains more than one row.
     * @param columns columns of the table in given string format.
     * @param vararg variable argument that contains the values for filter.
     * @return object with the values from the entry.
     */
    public T getByFilter(String columns, Object... vararg){

        try{
            String query = "SELECT * FROM " + tableName + " WHERE " + Regex.formatColumns(columns, "filter") + ";";
            
            Connection connection = DBConnector.createConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            mapToStatement(statement, vararg);
            ResultSet resultSet = statement.executeQuery();
            
            T obj;
            if(resultSet.next())
                obj = mapToObj(resultSet);
            else
                return null;

            if(resultSet.next())
                throw new Exception("ERROR: This query returned multiple results!");
            
            return obj;
            
        } catch (Exception e){
            //need error handler
            e.printStackTrace();
            return null;
        }
    }
    
}
