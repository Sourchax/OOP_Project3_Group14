package entities;

import java.sql.Date;
// import utilities.Authentication;

/**
 * Abstract Employee class 
 * Inherited by {@link users.RegularEmployee} and {@link users.Manager} classes.
 */
public /*abstract*/ class Employee {
    private int id;
    private String name;
    private String surname;
    private String username;
    private String role;
    private String passwd;


    // Default Constructor
    public Employee() {
        
    }

    ////  Constructor with parameters
    public Employee(String name, String surname, String username, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.passwd = password;
        this.role = role;
    }

    // -- Getters --

    /** 
    * Return the name of the employee object. 
    * @return String
    */
    public String getName() {
        return this.name;
    }

    /** 
    * Return the surname of the employee object. 
    * @return String
    */
    public String getSurname() {
        return this.surname;
    }
    
    /** 
    * Return the username of the employee object. 
    * @return String
    */
    public String getUsername(){
        return this.username;
    }

    /** 
    * Return the role of the employee object. 
    * @return String
    */
    public String getRole() {
        return role;
    }

    /** 
    * Return the password of the employee object. 
    * @return String
    */
    public String getPasswd() {
        return passwd;
    }

    /** 
    * Return the id number of the employee object. 
    * @return int
    */
    public int getId() {
        return id;
    }    

    // -- Setters --

    /** 
    * Set the name of the employee object. 
    * @param String
    */
    public void setName(String name) {
        this.name = name;
    }

    /** 
    * Set the surname of the employee object. 
    * @param String
    */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /** 
    * Set the username of the employee object. 
    * @param String
    */
    public void setUsername(String username) {
        this.username = username;
    }

    /** 
    * Set the role of the employee object. 
    * @param String
    */
    public void setRole(String role) {
        this.role = role;
    }

    /** 
    * Set the id of the employee object. 
    * @param String
    */
    public void setId(int id) {
        this.id = id;
    }

    /** 
    * Set the password of the employee object. 
    * @param String
    */
    public void setPasswd(String password) {
        this.passwd = password;
    }


    /** 
    * Prints the non-profile fields  
    * @see #getName()
    * @see #getId()
    * @see #getSurname()
    * @see #getUsername()
    * @see #getDateOfBirth()
    * @see #getDateOfStart()
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
