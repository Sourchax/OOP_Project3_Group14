package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String DB_URL = "jdbc:mysql://192.168.1.20:3306/oop_cinema";
    private static final String USER = "myuser";
    private static final String PASSWD = "1234";
    
    /**
     * Creates connection with the database for the other operations
     * @return Database connection object of type Connection
     * @throws SQLException
     */
    protected static Connection createConnection() throws SQLException{
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWD);
            return connection;
    }
}
