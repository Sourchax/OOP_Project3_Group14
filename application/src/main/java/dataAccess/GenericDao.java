package dataAccess;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utilities.Regex;

public abstract class GenericDao<T> {
    protected String tableName;

    public abstract T mapToObj(ResultSet resultSet) throws SQLException;

    public void mapToStatement(PreparedStatement statement, Object[] vararg) throws SQLException {
        for(int i = 0; i < vararg.length-1; i++){
            statement.setObject(i, vararg[i]);
        }
    }

    public void mapToStatement(PreparedStatement statement, T obj) throws Exception {
        Class<?> cla = obj.getClass();
        Method[] methods = cla.getDeclaredMethods();
        int i = 0;
        for(Method m : methods){
            if(m.getName().contains("get"))
                statement.setObject(i++, m.invoke(obj));
        }
    }

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

    public void deleteById(int id){
        String query = "DELETE FROM " + tableName + " WHERE id = ?;";

        try{
            Connection connection = DBConnector.createConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(0, id);
            statement.executeUpdate();
        
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateById(int id, String columnUpdate, Object... vararg){
        
        try{
            String query = "UPDATE " + tableName + " SET " + Regex.formatColumns(columnUpdate, "update") + " WHERE id = ?;";
            
            Connection connection = DBConnector.createConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            mapToStatement(statement, vararg);
            statement.setInt(vararg.length, id);
            statement.executeUpdate();
        
        } catch(Exception e) {
            //need error handler
            e.printStackTrace();
        }
    }

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

}
