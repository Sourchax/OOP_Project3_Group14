package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import dataAccess.DBConnector;

/**
 * Main application class for the JavaFX cinema center application. 
 * Responsible for initializing and launching the application window and managing the initial scene.
 */
public class App extends Application {

    private static Scene scene;

    /**
     * Starts the JavaFX application by setting up the main stage (window) and loading the initial scene.
     * The scene is set to display the login page, and the window's title and size are configured.
     *
     * @param stage The primary stage (window) for the JavaFX application.
     * @throws IOException If an error occurs while loading the FXML file for the initial scene.
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("fxml/login"), 1200, 768);
        
        stage.setScene(scene);
        stage.setMinHeight(768);
        stage.setMinWidth(1200);
        stage.setTitle("Group14 Cinema Center");
        stage.show();
    }

    /**
     * Loads an FXML file and returns the corresponding parent node.
     *
     * @param fxml The path to the FXML file to load.
     * @return The root node from the FXML file.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    /**
     * The main method that launches the JavaFX application.
     *
     * @param args Command-line arguments (not used in this application).
     */    public static void main(String[] args) {
        launch();
    }

}