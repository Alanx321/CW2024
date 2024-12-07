package com.example.demo.controller;

// Import necessary JavaFX and custom packages
import com.example.demo.ui.MainMenu; // Import the MainMenu class
import javafx.application.Application; // Import JavaFX application base class
import javafx.stage.Stage; // Import JavaFX stage class

/**
 * The Main class serves as the entry point for the Sky Battle game.
 * It initializes the application lifecycle and delegates responsibilities.
 */
public class Main extends Application {

    // Constants for the application window
    private static final int SCREEN_WIDTH = 1300; // Define the width of the application window
    private static final int SCREEN_HEIGHT = 750; // Define the height of the application window
    private static final String TITLE = "Sky Battle"; // Define the title of the application window

    /**
     * The start method is the entry point for the JavaFX application lifecycle.
     * It delegates the setup and menu display to other classes.
     *
     * @param primaryStage The primary stage for the application window.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create an instance of the ApplicationConfigurator class
        ApplicationConfigurator configurator = new ApplicationConfigurator();

        // Configure the primary stage properties
        configurator.configureStage(primaryStage, TITLE, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Launch the MainMenu
        MainMenuLauncher menuLauncher = new MainMenuLauncher();
        try {
            menuLauncher.launchMainMenu(primaryStage); // Start the main menu
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions that occur during menu launch
        }
    }

    /**
     * The main method is the entry point of the application.
     * It starts the JavaFX application lifecycle.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        launch(); // Launch the JavaFX application
    }
}

/**
 * The ApplicationConfigurator class is responsible for configuring the application window.
 */
class ApplicationConfigurator {

    /**
     * Configures the primary stage properties for the application.
     *
     * @param stage  The stage to configure.
     * @param title  The title of the application window.
     * @param width  The width of the application window.
     * @param height The height of the application window.
     */
    public void configureStage(Stage stage, String title, int width, int height) {
        stage.setTitle(title); // Set the window title
        stage.setResizable(false); // Disable window resizing
        stage.setWidth(width); // Set the window width
        stage.setHeight(height); // Set the window height
    }
}

/**
 * The MainMenuLauncher class is responsible for initializing and launching the main menu.
 */
class MainMenuLauncher {

    /**
     * Launches the main menu interface.
     *
     * @param stage The stage on which the main menu will be displayed.
     * @throws Exception If an error occurs while launching the menu.
     */
    public void launchMainMenu(Stage stage) throws Exception {
        MainMenu mainMenu = new MainMenu(); // Create an instance of the main menu
        mainMenu.start(stage); // Start the main menu interface
    }
}
