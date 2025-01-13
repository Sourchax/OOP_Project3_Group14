package entities;

/**
 * Represents an employee entity within the company.
 * <p>
 * This class encapsulates various details about an employee, such as their
 * name, username, role, and more. It provides methods to access and modify
 * these details.
 * </p>
 */
public class Employee {
    private int id;
    private String name;
    private String surname;
    private String username;
    private String role;
    private String passwd;

    /**
     * Default constructor.
     * <p>
     * Initializes a new instance of the Employee class with default values.
     * </p>
     */
    public Employee() {
    }

    /**
     * Constructs an Employee object with the specified details.
     *
     * @param name     the first name of the employee
     * @param surname  the last name of the employee
     * @param username the username for the employee's account
     * @param password the password for the employee's account
     * @param role     the role of the employee (e.g., Manager, Regular Employee)
     */
    public Employee(String name, String surname, String username, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.passwd = password;
        this.role = role;
    }

    // -- Getters --

    /**
     * Gets the first name of the employee.
     *
     * @return the first name of the employee
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the last name of the employee.
     *
     * @return the last name of the employee
     */
    public String getSurname() {
        return this.surname;
    }

    /**
     * Gets the username of the employee's account.
     *
     * @return the username of the employee
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the role of the employee.
     *
     * @return the role of the employee
     */
    public String getRole() {
        return role;
    }

    /**
     * Gets the password of the employee's account.
     *
     * @return the password of the employee
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * Gets the unique ID of the employee.
     *
     * @return the unique ID of the employee
     */
    public int getId() {
        return id;
    }

    // -- Setters --

    /**
     * Sets the first name of the employee.
     *
     * @param name the first name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the last name of the employee.
     *
     * @param surname the last name to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Sets the username of the employee's account.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the role of the employee.
     *
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Sets the unique ID of the employee.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the password of the employee's account.
     *
     * @param password the password to set
     */
    public void setPasswd(String password) {
        this.passwd = password;
    }

    /**
     * Displays non-sensitive details of the employee.
     * <p>
     * Prints the following details of the employee to the console:
     * ID, name, surname, username, and role.
     * </p>
     *
     * @see #getId()
     * @see #getName()
     * @see #getSurname()
     * @see #getUsername()
     * @see #getRole()
     */
    public void displayNonProfile() {
        System.out.println("ID: " + this.getId());
        System.out.println("Name: " + this.getName());
        System.out.println("Surname: " + this.getSurname());
        System.out.println("Username: " + this.getUsername());
        System.out.println("Role: " + this.getRole());
    }
}
