package com.example.demo.levels;

// Importing necessary classes
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.lang.reflect.Constructor;
import com.example.demo.listeners.EventListener;

/**
 * LevelManager class is responsible for managing level transitions in the game.
 * It handles loading levels dynamically and initializing their scenes on the stage.
 */
public class LevelManager {

    // Primary stage of the application where levels are displayed
    private final Stage stage;

    /**
     * Constructor for LevelManager.
     *
     * @param stage The primary stage of the application.
     */
    public LevelManager(Stage stage) {
        this.stage = stage; // Assign the stage for displaying levels
    }

    /**
     * Loads and transitions to the specified level using reflection.
     *
     * @param className Fully qualified class name of the level to load.
     * @throws Exception If the class is not found, the constructor is missing, or instantiation fails.
     */
    public void goToLevel(String className) throws Exception {
        try {
            // Dynamically load the level class by its name
            Class<?> levelClass = Class.forName(className);

            // Get the constructor of the level class with parameters: (double, double, Stage)
            Constructor<?> constructor = levelClass.getConstructor(double.class, double.class, Stage.class);

            // Create an instance of the level using the constructor
            LevelParent level = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth(), stage);

            // Register an EventListener as a property change listener for the level
            level.addPropertyChangeListener(new EventListener(this));

            // Initialize the level's scene
            Scene scene = level.initializeScene();

            // Set the initialized scene on the stage
            stage.setScene(scene);

            // Start the game for the newly loaded level
            level.startGame();
        } catch (Exception e) {
            // Handle any errors that occur during level loading or transition
            throw new Exception("Failed to load level: " + className, e);
        }
    }
}
