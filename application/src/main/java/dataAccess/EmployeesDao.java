package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Employee;

public class EmployeesDao extends GenericDao<Employee>{

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
