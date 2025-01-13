package application;
/**
 * A utility class to manage a shared instance of {@link ManagerSceneController}.
 * This class provides static methods to set and retrieve a common 
 * {@link ManagerSceneController} instance.
 * 
 * <p>Use this class to centralize access to a single {@link ManagerSceneController} 
 * object, acting as a parent or manager for related operations.</p>
 * 
 * @see ManagerSceneController
 */

public class ManagerParent {
     // The shared ManagerSceneController instance
    private static ManagerSceneController managerParent;
    /**
     * Sets the shared instance of {@link ManagerSceneController}.
     * 
     * @param controller the {@link ManagerSceneController} instance to be shared.
     */

    public static void setParent(ManagerSceneController controller){
        managerParent = controller;
    }
     /**
     * Retrieves the shared instance of {@link ManagerSceneController}.
     * 
     * @return the shared {@link ManagerSceneController} instance.
     */

    public static ManagerSceneController getParent(){
        return managerParent;
    }
}
