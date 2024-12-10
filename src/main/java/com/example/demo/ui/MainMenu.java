package com.example.demo.ui;

import com.example.demo.controller.Controller;
import com.example.demo.controller.HowToPlayWindow;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Main menu for the Space Battle application.
 * Displays options to start the game, view "How To Play", or quit.
 */
public class MainMenu extends Application {

    protected MediaPlayer backgroundMusicPlayer; // Background music player
    private final HowToPlayWindow howToPlayWindow; // Reference to the HowToPlayWindow instance

    /**
     * Default constructor for production use.
     * Initializes with a default HowToPlayWindow instance.
     */
    public MainMenu() {
        this.howToPlayWindow = new HowToPlayWindow();
    }

    /**
     * Constructor for dependency injection.
     * Allows passing a custom HowToPlayWindow instance (e.g., for testing).
     *
     * @param howToPlayWindow The HowToPlayWindow instance to use.
     */
    public MainMenu(HowToPlayWindow howToPlayWindow) {
        this.howToPlayWindow = howToPlayWindow;
    }

    /**
     * Initializes and displays the main menu of the game.
     * Sets up the background, title text, background music, and a START button to begin the game.
     *
     * @param primaryStage The primary stage for the application's UI.
     */
    @Override
    public void start(Stage primaryStage) {
        // Set up the background image for the menu
        Image backgroundImage = new Image(getClass().getResource("/com/example/demo/images/menu_background.jpg").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1300); // Set the width of the background
        backgroundView.setFitHeight(750); // Set the height of the background

        // Set up the title text for the main menu
        Text titleText = new Text("SPACE BATTLE");
        titleText.setFont(new Font("Arial", 50)); // Set the font and size for the title
        titleText.setStyle("-fx-fill: white;"); // Apply a white color style to the text

        // Set up the background music for the menu
        Media backgroundMusic = new Media(getClass().getResource("/com/example/demo/sounds/menu_background.wav").toExternalForm());
        backgroundMusicPlayer = new MediaPlayer(backgroundMusic);
        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music indefinitely
        backgroundMusicPlayer.play(); // Start playing the background music

        // Set up the START button to begin the game
        Button startButton = new Button("START");
        startButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 20px;"); // Style the button
        startButton.setOnAction(e -> { 
            playButtonClickSound(); // Play a click sound effect
            backgroundMusicPlayer.stop(); // Stop the background music when starting the game
            try {
                Controller controller = new Controller(primaryStage); // Initialize the game controller
                controller.launchGame(); // Launch the game
            } catch (Exception ex) {
                ex.printStackTrace(); // Handle any exceptions during game launch
            }
        });
   


     // Set up the HOW TO PLAY button to display gameplay instructions
        Button howToPlayButton = new Button("HOW TO PLAY");
        howToPlayButton.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-size: 20px;"); // Style the button
        howToPlayButton.setOnAction(e -> {
            playButtonClickSound(); // Play a click sound effect
            howToPlayWindow.display(); // Open the "How to Play" window
        });

        // Set up the QUIT button to close the game
        Button quitButton = new Button("QUIT");
        quitButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;"); // Style the button
        quitButton.setOnAction(e -> {
            playButtonClickSound(); // Play a click sound effect
            backgroundMusicPlayer.stop(); // Stop the background music
            primaryStage.close(); // Close the application
        });

        // Arrange buttons and title text in a vertical layout using VBox
        VBox vbox = new VBox(20, titleText, startButton, howToPlayButton, quitButton); // Add title and buttons with spacing
        vbox.setAlignment(Pos.CENTER); // Center the layout on the screen

        // Create the root layout with the background image and VBox
        StackPane root = new StackPane(backgroundView, vbox); // Combine the background and menu layout

        // Set up the scene with the root layout and configure the primary stage
        Scene scene = new Scene(root, 1300, 750); // Set the scene dimensions
        primaryStage.setTitle("Space Battle"); // Set the title of the primary stage
        primaryStage.setScene(scene); // Attach the scene to the primary stage
        primaryStage.setResizable(false); // Prevent resizing of the game window
        primaryStage.show(); // Display the main menu
    }

    /**
     * Plays the button click sound effect.
     * Used to provide auditory feedback when a button is clicked in the UI.
     * Handles any exceptions that might occur during media playback.
     */
    private void playButtonClickSound() {
        try {
            Media clickSound = new Media(getClass().getResource("/com/example/demo/sounds/button_click.wav").toExternalForm());
            MediaPlayer clickPlayer = new MediaPlayer(clickSound);
            clickPlayer.play(); // Play the button click sound effect
        } catch (Exception ex) {
            ex.printStackTrace(); // Log any exceptions for debugging
        }
    }

    /**
     * Getter for the background music player.
     * Provides access to the background music MediaPlayer instance,
     * enabling external classes (e.g., for testing) to interact with or modify it.
     *
     * @return The background music MediaPlayer instance.
     */
    public MediaPlayer getBackgroundMusicPlayer() {
        return backgroundMusicPlayer; // Return the background music player instance
    }

    /**
     * Entry point for launching the application.
     * Initializes and starts the JavaFX application.
     *
     * @param args Command-line arguments (currently unused).
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}