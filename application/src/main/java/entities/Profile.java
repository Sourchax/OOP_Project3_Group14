package entities;

/**
 * Profile class to hold profile data of the {@link users.Employee} class
 */
public class Profile {
    private String phoneNumber;
    private String email;

    /**
     * Default Constructor
     */
    public Profile() {
        this.phoneNumber = null;
        this.email = null;
    }

    /**
     * Parametrized Constructor
     * @param phoneNumber Employee's phone number
     * @param email Employee's email
     */
    public Profile(String phoneNumber, String password, String email) {
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // -- Getters --

    /**
     * Return the phone number of the profile object
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Return the email of the profile object
     * @return
     */
    public String getEmail() {
        return email;
    }

    // -- Setters --

    /**
     * Set the phone number of the profile object
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Set the email of the profile object
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
