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

    @Override
    public void start(Stage primaryStage) {
        // Set up the background image
        Image backgroundImage = new Image(getClass().getResource("/com/example/demo/images/menu_background.jpg").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1300);
        backgroundView.setFitHeight(750);

        // Set up the title text
        Text titleText = new Text("SPACE BATTLE");
        titleText.setFont(new Font("Arial", 50));
        titleText.setStyle("-fx-fill: white;");

        // Set up the background music
        Media backgroundMusic = new Media(getClass().getResource("/com/example/demo/sounds/menu_background.wav").toExternalForm());
        backgroundMusicPlayer = new MediaPlayer(backgroundMusic);
        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMusicPlayer.play();

        // Set up the START button
        Button startButton = new Button("START");
        startButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 20px;");
        startButton.setOnAction(e -> {
            playButtonClickSound();
            backgroundMusicPlayer.stop();
            try {
                Controller controller = new Controller(primaryStage);
                controller.launchGame();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Set up the HOW TO PLAY button
        Button howToPlayButton = new Button("HOW TO PLAY");
        howToPlayButton.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-size: 20px;");
        howToPlayButton.setOnAction(e -> {
            playButtonClickSound();
            howToPlayWindow.display();
        });

        // Set up the QUIT button
        Button quitButton = new Button("QUIT");
        quitButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        quitButton.setOnAction(e -> {
            playButtonClickSound();
            backgroundMusicPlayer.stop();
            primaryStage.close();
        });

        // Arrange buttons and title in a vertical layout
        VBox vbox = new VBox(20, titleText, startButton, howToPlayButton, quitButton);
        vbox.setAlignment(Pos.CENTER);

        // Create the root layout with background and vbox
        StackPane root = new StackPane(backgroundView, vbox);

        // Set up the scene and primary stage
        Scene scene = new Scene(root, 1300, 750);
        primaryStage.setTitle("Space Battle");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Plays the button click sound effect.
     */
    private void playButtonClickSound() {
        try {
            Media clickSound = new Media(getClass().getResource("/com/example/demo/sounds/button_click.wav").toExternalForm());
            MediaPlayer clickPlayer = new MediaPlayer(clickSound);
            clickPlayer.play();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Getter for the background music player.
     * Allows external classes (e.g., tests) to access the music player.
     *
     * @return The background music MediaPlayer instance.
     */
    public MediaPlayer getBackgroundMusicPlayer() {
        return backgroundMusicPlayer;
    }

    /**
     * Entry point for launching the application.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
