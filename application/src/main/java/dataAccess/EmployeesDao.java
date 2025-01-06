package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Employee;
import entities.Profile;

public class EmployeesDao extends GenericDao<Employee>{

    EmployeesDao(){
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
        employee.setDateOfBirth(resultSet.getDate("date_of_birth"));
        employee.setDateOfStart(resultSet.getDate("date_of_start"));

        Profile profile = new Profile();
        profile.setEmail(resultSet.getString("mail"));
        profile.setPhoneNumber(resultSet.getString("phone_num"));
        employee.setProfile(profile);
        return employee;
    }
    
}
