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

public abstract class GenericDao<T> {
    protected String tableName;

    public abstract T mapToObj(ResultSet resultSet) throws SQLException;

    public void mapToStatement(PreparedStatement statement, Object[] vararg) throws SQLException {
        for(int i = 1; i <= vararg.length; i++){
            statement.setObject(i, vararg[i-1]);
        }
    }

    public void mapToStatement(PreparedStatement statement, T obj) throws Exception {
        Class<?> cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        int i = 1;
        for(Field f : fields){
            String fName = f.getName();
            if(fName != "id"){
                Method m = cla.getMethod("get" + fName.substring(0, 1).toUpperCase() + fName.substring(1));
                System.out.println(i++ + " - " + m.invoke(obj));
                //statement.setObject(i++, m.invoke(obj));
            }
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
            statement.setInt(1, id);
            statement.executeUpdate();
        
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }

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
