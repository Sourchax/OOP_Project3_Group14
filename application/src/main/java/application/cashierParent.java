package application;

/**
 * The `cashierParent` class provides a static reference to the `CashierSceneController`.
 * This allows the `CashierSceneController` instance to be accessed globally within the application.
 */
public class cashierParent {

    /**
     * A static reference to the `CashierSceneController` instance.
     */
    private static CashierSceneController cashierParent;

    /**
     * Sets the parent `CashierSceneController` instance.
     * This method is used to initialize the static reference to the controller.
     *
     * @param pController The `CashierSceneController` instance to set as the parent.
     */
    public static void setParent(CashierSceneController pController){
        cashierParent = pController;
    }

    /**
     * Gets the current parent `CashierSceneController` instance.
     *
     * @return The static reference to the `CashierSceneController` instance.
     */
    public static CashierSceneController getParent(){
        return cashierParent;
    }
}
