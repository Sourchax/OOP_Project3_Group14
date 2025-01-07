package application;

public class ManagerParent {
    private static ManagerSceneController managerParent;

    public static void setParent(ManagerSceneController controller){
        managerParent = controller;
    }

    public static ManagerSceneController getParent(){
        return managerParent;
    }
}
