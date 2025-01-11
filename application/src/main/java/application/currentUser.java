package application;

public class currentUser{
    
    private static String username;
    private static String role;

    public static void setUsername(String user1){
        username = user1;
    }

    public static String getUsername(){
        return username;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        currentUser.role = role;
    }
}