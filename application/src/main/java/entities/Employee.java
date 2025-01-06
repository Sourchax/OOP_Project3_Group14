package entities;

import java.sql.Date;
// import utilities.Authentication;

/**
 * Abstract Employee class 
 * Inherited by {@link users.RegularEmployee} and {@link users.Manager} classes.
 */
public /*abstract*/ class Employee {

    private String name;
    private String surname;
    private String username;
    private String role;
    private Date dateOfBirth;
    private Date dateOfStart;
    private int id;
    private Profile pr = new Profile();


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
    * Return the birth date of the employee object. 
    * @return Date
    */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /** 
    * Return the start date of the employee object. 
    * @return Date
    */
    public Date getDateOfStart() {
        return dateOfStart;
    }

    /** 
    * Return the id number of the employee object. 
    * @return int
    */
    public int getId() {
        return id;
    }    

    /** 
    * Return the profile part of the employee object. 
    * @return Profile
    */
    public Profile getProfile() {
        return pr;
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
    * Set the birth date of the employee object. 
    * @param Date
    */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /** 
    * Set the start date of the employee object. 
    * @param Date
    */
    public void setDateOfStart(Date dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    /** 
    * Set the id of the employee object. 
    * @param String
    */
    public void setId(int id) {
        this.id = id;
    }

    /** 
    * Set the profile of the employee object. 
    * @param String
    */
    public void setProfile(Profile pr) {
        this.pr = pr;
    }

    /** 
    * Prints the profile fields  
    * @see users.Profile#getEmail()
    * @see users.Profile#getPhoneNumber()
    * @see utilities.Authentication#getCurrentPassword()
    */
    public void displayProfile() {
        System.out.println("Email: " + this.pr.getEmail());
        System.out.println("Phone Number: " + this.pr.getPhoneNumber());
        System.out.println("Password: "/* + Authentication.getCurrentPassword()*/);
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
        System.out.println("Birth Date: " + this.getDateOfBirth());
        System.out.println("Role: " + this.getRole());
        System.out.println("Start Date: " + this.getDateOfStart());
    }
}
