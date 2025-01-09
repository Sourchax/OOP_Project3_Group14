package application;

public class currentUser{
    
    private static String username;

    public static void setUsername(String user1){
        username = user1;
    }

    public static String getUsername(){
        return username;
    }
}