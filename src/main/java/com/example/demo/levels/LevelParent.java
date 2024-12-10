package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.FighterPlane;
import com.example.demo.actors.UserPlane;
import com.example.demo.ui.MainMenu;
import com.example.demo.ui.PauseMenu;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LevelParent is an abstract class that serves as the foundation for all game levels.
 * It encapsulates common functionalities such as:
 * - Managing the game loop with a timeline
 * - Handling user input and controls
 * - Spawning and managing enemy units and projectiles
 * - Detecting collisions and updating the game state
 * - Transitioning between levels or back to the main menu
 *
 * The class provides a structure for subclasses to define level-specific behavior.
 */
public abstract class LevelParent {

    // Constants for screen adjustment, frame delay, and default background music
    private static final double SCREEN_HEIGHT_ADJUSTMENT = 150; // Adjustment factor for screen height
    private static final int MILLISECOND_DELAY = 50; // Delay between game loop frames
    private static final String DEFAULT_LEVEL_MUSIC = "/com/example/demo/sounds/level_music.wav"; // Default music file path

    // Screen dimensions and enemy positioning
    private final double screenHeight; // Height of the screen
    private final double screenWidth; // Width of the screen
    private final double enemyMaximumYPosition; // Maximum Y-coordinate for enemy movement

    // Core game elements
    private final Group root; // Root group for the scene graph
    private final Timeline timeline; // Timeline managing the game loop
    private final UserPlane user; // User-controlled plane
    private final Scene scene; // The game scene
    private final ImageView background; // Background image for the level

    // Lists for managing game entities
    private final List<ActiveActorDestructible> friendlyUnits; // List of friendly units
    private final List<ActiveActorDestructible> enemyUnits; // List of enemy units
    private final List<ActiveActorDestructible> userProjectiles; // List of user-fired projectiles
    private final List<ActiveActorDestructible> enemyProjectiles; // List of enemy-fired projectiles

    // Game state variables
    private int currentNumberOfEnemies; // Current count of active enemies
    private LevelView levelView; // UI and visual representation of the level
    private int score; // Player's current score
    private Text scoreDisplay; // UI element displaying the score

    // Pause and music controls
    private PauseMenu pauseMenu; // Pause menu interface
    private boolean isPaused; // Flag indicating if the game is paused
    private final Stage stage; // The main game stage
    private MediaPlayer backgroundMusicPlayer; // Background music player

    // Listener support for property changes
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this); // Listener management


    /**
     * Constructor for the LevelParent class.
     * Initializes the core components and game state required for a level.
     *
     * @param backgroundImageName Name of the background image file to be used for the level.
     * @param screenHeight        The height of the game screen.
     * @param screenWidth         The width of the game screen.
     * @param playerInitialHealth The initial health value for the user's plane.
     * @param stage               The primary stage on which the game will be displayed.
     */
    public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, Stage stage) {
        this.root = new Group(); // Create the root group for the scene graph
        this.scene = new Scene(root, screenWidth, screenHeight); // Initialize the game scene
        this.timeline = new Timeline(); // Set up the game timeline
        this.user = new UserPlane(playerInitialHealth); // Create the user-controlled plane with initial health
        this.friendlyUnits = new ArrayList<>(); // Initialize the list of friendly units
        this.enemyUnits = new ArrayList<>(); // Initialize the list of enemy units
        this.userProjectiles = new ArrayList<>(); // Initialize the list of user projectiles
        this.enemyProjectiles = new ArrayList<>(); // Initialize the list of enemy projectiles

        // Set up the background image for the level
        this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));

        // Set screen dimensions and enemy position limits
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT; // Define the maximum Y position for enemies

        // Initialize the level view
        this.levelView = instantiateLevelView();

        this.currentNumberOfEnemies = 0; // Initialize enemy count
        this.stage = stage; // Assign the primary game stage

        this.score = 0; // Initialize player score
        this.scoreDisplay = new Text(screenWidth - 150, 50, "Score: 0"); // Create the score display text
        scoreDisplay.setStyle("-fx-font-size: 24px; -fx-fill: white;"); // Set the style for the score text
        getRoot().getChildren().add(scoreDisplay); // Add the score display to the root group
        scoreDisplay.toFront(); // Ensure the score display is visible at the front

        // Initialize the game timeline and add the user plane to the friendly units
        initializeTimeline();
        friendlyUnits.add(user);

        // Initialize the background music with the default music file
        initializeBackgroundMusic(DEFAULT_LEVEL_MUSIC);
    }


    /**
     * Initializes and adds the UserPlane to the scene graph.
     * Ensures that the UserPlane is part of the game environment and visible to the player.
     */
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(user); // Ensures UserPlane is in the scene graph
    }

    /**
     * Abstract method to check if the game is over.
     * Must be implemented by subclasses to define the specific conditions for game over.
     */
    protected abstract void checkIfGameOver();

    /**
     * Abstract method to spawn enemy units into the game.
     * Must be implemented by subclasses to determine the logic for enemy creation and placement.
     */
    protected abstract void spawnEnemyUnits();

    /**
     * Abstract method to create and return an instance of LevelView.
     * Must be implemented by subclasses to initialize the visual representation of the level.
     *
     * @return A LevelView object representing the current level.
     */
    protected abstract LevelView instantiateLevelView();


    /**
     * Initializes the background music for the level.
     * Loads a media file from the specified path and sets up a MediaPlayer to play it.
     * The music will loop indefinitely during gameplay.
     * If an error occurs while loading the music, an error message is printed to the console.
     *
     * @param musicPath The relative path to the background music file.
     */
    private void initializeBackgroundMusic(String musicPath) {
        try {
            Media backgroundMusic = new Media(getClass().getResource(musicPath).toExternalForm());
            backgroundMusicPlayer = new MediaPlayer(backgroundMusic);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        } catch (Exception e) {
            System.err.println("Error loading level background music: " + musicPath);
            e.printStackTrace();
        }
    }


    /**
     * Initializes the game scene by setting up the background, friendly units, and UI elements.
     * Adds functionality to handle key presses, such as toggling pause with the 'P' key.
     * 
     * @return The initialized Scene object ready for gameplay.
     */
    public Scene initializeScene() {
        initializeBackground(); // Set up the game background
        initializeFriendlyUnits(); // Add friendly units to the scene
        levelView.showHeartDisplay(); // Display player health or lives

        // Configure key press event handling for pausing the game
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.P) {
                togglePause();
            }
        });

        return scene;
    }


    /**
     * Starts the game by playing background music (if available) and initiating the game timeline.
     * Also ensures the game background receives focus to capture user inputs.
     */
    public void startGame() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.play(); // Play the background music
        }
        background.requestFocus(); // Set focus to the game background for input handling
        timeline.play(); // Start the game timeline for animations and game updates
    }


    /**
     * Handles the transition to the next level in the game.
     * Stops the current game timeline, logs the level transition, and notifies listeners of the change.
     *
     * @param levelName The name of the next level to transition to.
     */
    public void goToNextLevel(String levelName) {
        try {
            System.out.println("Transitioning to next level: " + levelName); // Debug statement for tracking level changes
            timeline.stop(); // Stop the current game timeline
            propertyChangeSupport.firePropertyChange("levelTransition", null, levelName); // Notify observers about the level transition
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions during the transition
        }
    }


    /**
     * Updates the game scene by handling all game logic and interactions during each frame.
     * This includes updating actors, spawning enemies, managing collisions, and checking game state.
     * If the game is paused, the update logic is skipped.
     */
    protected void updateScene() {
        if (isPaused) return; // Skip updates if the game is paused

        user.updateActor(); // Update the state and position of the user-controlled actor
        getRoot().layout(); // Re-render the scene graph to reflect changes

        spawnEnemyUnits(); // Spawn new enemy units as needed
        updateActors(); // Update all active actors in the scene
        generateEnemyFire(); // Handle enemy firing logic
        updateNumberOfEnemies(); // Track the current number of enemies in play
        handleEnemyPenetration(); // Manage logic when enemies pass through certain boundaries
        handleUserProjectileCollisions(); // Check and process collisions between user projectiles and enemies
        handleEnemyProjectileCollisions(); // Check and process collisions between enemy projectiles and the user
        handlePlaneCollisions(); // Handle collisions between planes (e.g., user vs. enemy)
        removeAllDestroyedActors(); // Remove actors marked for destruction
        updateKillCount(); // Update the player's kill count
        updateLevelView(); // Update the level's UI and visual state
        checkIfGameOver(); // Determine if the game over condition has been met
    }


    /**
     * Initializes the game timeline for managing the main game loop.
     * Sets the timeline to run indefinitely and defines a keyframe that triggers the update logic
     * at regular intervals based on the specified delay.
     */
    private void initializeTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE); // Set the timeline to run continuously
        KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene()); // Define the game loop keyframe
        timeline.getKeyFrames().add(gameLoop); // Add the keyframe to the timeline
    }


    /**
     * Initializes the game background and sets up input handling for user interactions.
     * Configures the background's dimensions and key event listeners for controlling the user plane.
     */
    private void initializeBackground() {
        background.setFocusTraversable(true); // Allow the background to receive focus for key events
        background.setFitHeight(screenHeight); // Set the background height to match the screen
        background.setFitWidth(screenWidth); // Set the background width to match the screen

        // Handle key press events for user actions
        background.setOnKeyPressed(e -> {
            KeyCode kc = e.getCode();
            if (kc == KeyCode.UP) user.moveUp(); // Move user plane up
            if (kc == KeyCode.DOWN) user.moveDown(); // Move user plane down
            if (kc == KeyCode.SPACE) fireProjectile(); // Fire a projectile
        });

        // Handle key release events to stop movement
        background.setOnKeyReleased(e -> {
            KeyCode kc = e.getCode();
            if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop(); // Stop user plane movement
        });

        root.getChildren().add(background); // Add the background to the scene's root
    }


    /**
     * Fires a projectile from the user-controlled actor.
     * If the user successfully fires a projectile, it is added to the scene and tracked.
     */
    private void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile(); // Create a new projectile from the user
        if (projectile != null) {
            root.getChildren().add(projectile); // Add the projectile to the scene graph
            userProjectiles.add(projectile); // Track the projectile in the list of user projectiles
        }
    }


    /**
     * Generates projectiles fired by enemy units.
     * Iterates through all enemy units and spawns projectiles if an enemy fires.
     */
    private void generateEnemyFire() {
        enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile())); 
        // Casts each enemy to FighterPlane and invokes its fireProjectile method
    }

    /**
     * Adds a projectile fired by an enemy to the scene and tracks it.
     * Ensures the projectile is properly rendered and managed in the game environment.
     *
     * @param projectile The projectile fired by an enemy, or null if no projectile is fired.
     */
    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            root.getChildren().add(projectile); // Add the projectile to the scene graph
            enemyProjectiles.add(projectile); // Track the projectile in the list of enemy projectiles
        }
    }


    /**
     * Updates all active actors in the game.
     * This includes friendly units, enemy units, user projectiles, and enemy projectiles.
     * Each actor's update logic is executed, allowing for movement or state changes.
     */
    private void updateActors() {
        friendlyUnits.forEach(ActiveActorDestructible::updateActor); // Update all friendly units
        enemyUnits.forEach(ActiveActorDestructible::updateActor); // Update all enemy units
        userProjectiles.forEach(ActiveActorDestructible::updateActor); // Update all user projectiles
        enemyProjectiles.forEach(ActiveActorDestructible::updateActor); // Update all enemy projectiles
    }

    /**
     * Removes all actors that are marked as destroyed from their respective lists.
     * This includes friendly units, enemy units, and projectiles. Helps maintain a clean game state.
     */
    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits); // Remove destroyed friendly units
        removeDestroyedActors(enemyUnits); // Remove destroyed enemy units
        removeDestroyedActors(userProjectiles); // Remove destroyed user projectiles
        removeDestroyedActors(enemyProjectiles); // Remove destroyed enemy projectiles
    }


    /**
     * Removes all actors that are marked as destroyed from the specified list and the scene.
     * Ensures destroyed actors are no longer rendered or tracked in the game.
     *
     * @param actors The list of actors to check and remove if destroyed.
     */
    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream()
                .filter(ActiveActorDestructible::isDestroyed) // Find all destroyed actors
                .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors); // Remove destroyed actors from the scene
        actors.removeAll(destroyedActors); // Remove destroyed actors from the list
    }

    /**
     * Handles collisions between friendly units and enemy units (e.g., planes).
     * Detects and processes interactions between these two groups.
     */
    private void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits); // Check for collisions between friendly and enemy units
    }

    /**
     * Handles collisions between user projectiles and enemy units.
     * Processes interactions where user projectiles hit enemies.
     */
    private void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyUnits); // Check for collisions between user projectiles and enemies
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     * Processes interactions where enemy projectiles hit friendly units.
     */
    private void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits); // Check for collisions between enemy projectiles and friendlies
    }


    /**
     * Detects and handles collisions between two lists of actors.
     * If a collision is detected, both actors involved take damage.
     *
     * @param actors1 The first list of actors to check for collisions.
     * @param actors2 The second list of actors to check for collisions.
     */
    private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors1) {
            for (ActiveActorDestructible otherActor : actors2) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) { // Check for collision
                    actor.takeDamage(); // Damage the first actor
                    otherActor.takeDamage(); // Damage the second actor
                }
            }
        }
    }

    /**
     * Handles the scenario where enemy units penetrate the user's defenses.
     * If an enemy breaches the defenses, the user takes damage, and the enemy is destroyed.
     */
    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemyHasPenetratedDefenses(enemy)) { // Check if the enemy has penetrated defenses
                user.takeDamage(); // Damage the user
                enemy.destroy(); // Destroy the enemy unit
            }
        }
    }


    /**
     * Updates the level view by adjusting the display of hearts based on the user's current health.
     * Removes hearts from the display to reflect the user's remaining health.
     */
    private void updateLevelView() {
        levelView.removeHearts(user.getHealth()); // Update the heart display to match user's health
    }

    /**
     * Updates the kill count and player score based on the number of defeated enemies.
     * Calculates the difference between the initial and current number of enemies,
     * increments the score for each kill, and updates the score display.
     */
    private void updateKillCount() {
        int kills = currentNumberOfEnemies - enemyUnits.size(); // Calculate the number of kills
        if (kills > 0) {
            for (int i = 0; i < kills; i++) {
                score += 100; // Add points for each kill
                getUser().incrementKillCount(); // Update the user's kill count
            }
            currentNumberOfEnemies = enemyUnits.size(); // Update the current enemy count
            updateScoreDisplay(); // Refresh the score display
        }
    }

    /**
     * Updates the score display to reflect the current score.
     * Ensures the score is visible and shown at the front of the UI.
     */
    private void updateScoreDisplay() {
        scoreDisplay.setText("Score: " + score); // Update the score text
        scoreDisplay.toFront(); // Bring the score display to the front
    }


    /**
     * Retrieves the current game score.
     *
     * @return The player's current score.
     */
    public int getScore() {
        return score; // Return the player's score
    }

    /**
     * Checks if an enemy has penetrated the player's defenses.
     * An enemy is considered to have penetrated if its horizontal position exceeds the screen width.
     *
     * @param enemy The enemy actor to check.
     * @return true if the enemy has breached defenses, false otherwise.
     */
    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth; // Check if the enemy is off-screen
    }

    /**
     * Handles the logic for when the player wins the game.
     * Stops the background music, halts the game timeline, and displays a win image.
     */
    protected void winGame() {
        stopBackgroundMusic(); // Stop the background music
        timeline.stop(); // Halt the game timeline
        levelView.showWinImage(); // Display the win image
    }

    /**
     * Handles the logic for when the player loses the game.
     * Stops the background music, halts the game timeline, and displays a game over image.
     */
    protected void loseGame() {
        stopBackgroundMusic(); // Stop the background music
        timeline.stop(); // Halt the game timeline
        levelView.showGameOverImage(); // Display the game over image
    }

    /**
     * Stops the background music if it is currently playing.
     * Ensures that the MediaPlayer is not null before attempting to stop the music.
     */
    protected void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop(); // Stop the background music
        }
    }

    /**
     * Retrieves the user-controlled plane.
     *
     * @return The UserPlane object representing the player's controlled plane.
     */
    protected UserPlane getUser() {
        return user; // Return the user-controlled plane
    }

    /**
     * Retrieves the root group of the scene graph.
     *
     * @return The root Group object containing all nodes in the scene.
     */
    protected Group getRoot() {
        return root; // Return the root group of the scene
    }

    /**
     * Gets the current number of enemy units in the game.
     *
     * @return The number of enemies currently active in the game.
     */
    protected int getCurrentNumberOfEnemies() {
        return enemyUnits.size(); // Return the size of the enemy units list
    }


    /**
     * Adds an enemy unit to the game.
     * The enemy is added to the list of enemy units and also to the scene graph for rendering.
     *
     * @param enemy The enemy unit to be added.
     */
    protected void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy); // Add the enemy to the list of enemy units
        root.getChildren().add(enemy); // Add the enemy to the scene graph
    }

    /**
     * Retrieves the maximum Y-coordinate position for enemy units.
     *
     * @return The maximum Y position allowed for enemies on the screen.
     */
    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition; // Return the maximum Y position for enemies
    }

    /**
     * Retrieves the width of the game screen.
     *
     * @return The width of the screen in pixels.
     */
    protected double getScreenWidth() {
        return screenWidth; // Return the screen width
    }

    /**
     * Checks if the user-controlled plane has been destroyed.
     *
     * @return true if the user's plane is destroyed, false otherwise.
     */
    protected boolean userIsDestroyed() {
        return user.isDestroyed(); // Check if the user plane is destroyed
    }


    /**
     * Updates the count of current enemies in the game.
     * Synchronizes the `currentNumberOfEnemies` variable with the size of the enemy units list.
     */
    private void updateNumberOfEnemies() {
        currentNumberOfEnemies = enemyUnits.size(); // Update the enemy count
    }

    /**
     * Toggles the game's pause state.
     * Pauses the game if it is currently running, or resumes it if it is paused.
     */
    private void togglePause() {
        if (isPaused) {
            resumeGame(); // Resume the game if currently paused
        } else {
            pauseGame(); // Pause the game if currently running
        }
    }

    /**
     * Pauses the game by stopping the timeline and displaying the pause menu.
     * Sets the game state to paused and adds the pause menu to the scene.
     */
    private void pauseGame() {
        isPaused = true; // Set the game state to paused
        timeline.stop(); // Stop the game timeline

        // Initialize and display the pause menu
        pauseMenu = new PauseMenu(stage, this::resumeGame, this::goToMainMenu, this::restartGame);
        getRoot().getChildren().add(pauseMenu.getRoot()); // Add the pause menu to the scene
    }


    /**
     * Resumes the game by playing the timeline and removing the pause menu from the scene.
     * Sets the game state to running.
     */
    private void resumeGame() {
        isPaused = false; // Set the game state to running
        timeline.play(); // Resume the game timeline

        if (pauseMenu != null) {
            getRoot().getChildren().remove(pauseMenu.getRoot()); // Remove the pause menu from the scene
        }
    }

    /**
     * Restarts the game by resetting the current level.
     * Stops and clears the timeline, resets the user's stats, and initializes a new instance of the current level.
     */
    private void restartGame() {
        try {
            stopBackgroundMusic(); // Stop any playing background music
            timeline.stop(); // Halt the current game timeline
            timeline.getKeyFrames().clear(); // Clear all keyframes from the timeline

            user.resetKillCount(); // Reset the user's kill count
            currentNumberOfEnemies = 0; // Reset the current enemy count

            // Create a new instance of the current level using reflection
            Constructor<?> constructor = this.getClass().getConstructor(double.class, double.class, Stage.class);
            LevelParent newLevel = (LevelParent) constructor.newInstance(screenHeight, screenWidth, stage);

            // Transfer property change listeners to the new level
            newLevel.addPropertyChangeListener(propertyChangeSupport.getPropertyChangeListeners()[0]);

            // Initialize and start the new level
            Scene newScene = newLevel.initializeScene();
            stage.setScene(newScene); // Set the new scene on the stage
            newLevel.startGame(); // Start the new level
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions during level restart
        }
    }


    /**
     * Transitions the game to the main menu.
     * Stops the background music and timeline, and initializes the main menu scene.
     */
    private void goToMainMenu() {
        stopBackgroundMusic(); // Stop any playing background music
        timeline.stop(); // Halt the game timeline

        MainMenu mainMenu = new MainMenu(); // Create a new MainMenu instance
        try {
            mainMenu.start(stage); // Start the main menu scene
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions during the menu transition
        }
    }

    /**
     * Adds a property change listener to the object.
     * Listeners will be notified when properties change.
     *
     * @param listener The listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener); // Register the listener
    }

    /**
     * Removes a property change listener from the object.
     * Prevents the listener from receiving further property change notifications.
     *
     * @param listener The listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener); // Unregister the listener
    }

}
