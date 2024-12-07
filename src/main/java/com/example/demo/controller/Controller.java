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
        this.stage = stage; // Assigning the stage
        this.levelManager = new LevelManager(stage); // Initializing LevelManager with the stage
        this.eventListener = new EventListener(levelManager); // Initializing EventListener with LevelManager
    }

    /**
     * Launches the game by displaying the main stage and starting the first level.
     */
    public void launchGame() {
        try {
            stage.show(); // Display the stage to the user
            levelManager.goToLevel("com.example.demo.levels.LevelOne"); // Start with Level One
        } catch (Exception e) {
            // Handle any unexpected errors that occur during the game launch
            ErrorHandler.showError("Unexpected Error", "An unexpected error occurred during game launch", e);
        }
    }
}
