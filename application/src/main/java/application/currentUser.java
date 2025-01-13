package application;

/**
 * The {@code currentUser} class represents the current user's information in the application.
 * It provides static methods to set and get the username and role of the user.
 */
public class currentUser {

    /**
     * The username of the current user.
     */
    private static String username;

    /**
     * The role of the current user (e.g., admin, user, etc.).
     */
    private static String role;

    /**
     * Sets the username of the current user.
     *
     * @param user1 the username to be set
     */
    public static void setUsername(String user1) {
        username = user1;
    }

    /**
     * Retrieves the username of the current user.
     *
     * @return the current user's username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Retrieves the role of the current user.
     *
     * @return the current user's role
     */
    public static String getRole() {
        return role;
    }

    /**
     * Sets the role of the current user.
     *
     * @param role the role to be set
     */
    public static void setRole(String role) {
        currentUser.role = role;
    }
}