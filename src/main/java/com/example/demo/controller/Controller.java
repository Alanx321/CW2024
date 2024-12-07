package com.example.demo.controller;

// Importing necessary classes
import com.example.demo.levels.LevelManager;
import com.example.demo.listeners.EventListener;
import com.example.demo.utils.ErrorHandler;
import javafx.stage.Stage;

/**
 * Controller class orchestrates the game's lifecycle and setup.
 * It manages the application's main stage and initiates level transitions.
 */
public class Controller {

    // Primary stage of the application where scenes are displayed
    private final Stage stage;

    // Handles loading and transitioning between levels
    private final LevelManager levelManager;

    // Listens for property changes to facilitate level transitions
    private final EventListener eventListener;

    /**
     * Constructor for the Controller class.
     *
     * @param stage The primary stage of the application.
     */
    public Controller(Stage stage) {
        // Assigning the main stage
        this.stage = stage;

        // Initialize the LevelManager with the main stage
        this.levelManager = new LevelManager(stage);

        // Create the EventListener, passing the LevelManager as a dependency
        this.eventListener = new EventListener(levelManager);

        // Assign the EventListener to the LevelManager
        this.levelManager.setEventListener(eventListener);
    }

    /**
     * Launches the game by displaying the main stage and starting the first level.
     */
    public void launchGame() {
        try {
            // Display the main stage
            stage.show();

            // Start the game by transitioning to the first level
            levelManager.goToLevel("com.example.demo.levels.LevelOne");
        } catch (Exception e) {
            // Handle any unexpected errors that occur during game launch
            ErrorHandler.showError(
                "Unexpected Error",
                "An unexpected error occurred during game launch",
                e
            );
        }
    }
}
