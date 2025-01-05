package application;

public class cashierParent {
    private static CashierSceneController cashierParent;

    public static void setParent(CashierSceneController pController){
        cashierParent = pController;
    }

    public static CashierSceneController getParent(){
        return cashierParent;
    }
}
