package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Employee;

/**
 * Database Access Object to provide database functionalities for {@link entities.Employee} entity
 * <p>
 * This class extends the abstract definitions of {@link dataAccess.GenericDao} class.
 * </p>
 */
public class EmployeesDao extends GenericDao<Employee>{

    /**
     * Constructor for initializing with the table name.
     */
    public EmployeesDao(){
        this.tableName = "employees";
    }

    @Override
    public Employee mapToObj(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.setName(resultSet.getString("name"));
        employee.setSurname(resultSet.getString("surname"));
        employee.setUsername(resultSet.getString("username"));
        employee.setRole(resultSet.getString("role"));
        employee.setPasswd(resultSet.getString("passwd"));
        return employee;
    }
    
}
