package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.EnemyPlane;
import javafx.stage.Stage;

/**
 * LevelOne represents the first level of the game.
 * It manages the player's progress, enemy spawning, and level-specific logic.
 */
public class LevelOne extends LevelParent {

    // Background image for the level
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/space.jpg";

    // Class name of the next level to transition to
    private static final String NEXT_LEVEL = "com.example.demo.levels.LevelTwo";

    // Total number of enemies allowed in the level at one time
    private static final int TOTAL_ENEMIES = 5;

    // Kill count required to advance to the next level
    private static final int KILLS_TO_ADVANCE = 10;

    // Probability of spawning an enemy on each frame
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;

    /**
     * Constructor for LevelOne.
     * Initializes the level with specific configurations.
     *
     * @param screenHeight Height of the game screen.
     * @param screenWidth  Width of the game screen.
     * @param stage        The main game stage.
     */
    public LevelOne(double screenHeight, double screenWidth, Stage stage) {
        // Call the parent class constructor with specific parameters for LevelOne
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, 5, stage);
    }

    /**
     * Checks if the game is over based on the player's status or kill target.
     * Ends the game if the player is destroyed or transitions to the next level if the kill target is met.
     */
    @Override
    protected void checkIfGameOver() {
        // Check if the player is destroyed
        if (userIsDestroyed()) {
            loseGame(); // End the game with a loss
        }
        // Check if the player's kill count has reached the target to advance
        else if (getUser().getNumberOfKills() >= KILLS_TO_ADVANCE) {
            goToNextLevel(NEXT_LEVEL); // Transition to the next level
        }
    }

    /**
     * Initializes the friendly units for the level (e.g., the player's plane).
     * Adds the user's plane to the game root.
     */
    @Override
    protected void initializeFriendlyUnits() {
        // Add the user's plane to the root node of the game scene
        getRoot().getChildren().add(getUser());
        getUser().toFront(); // Bring to the front for visibility
    }

    /**
     * Spawns enemy units for the level based on spawn probability.
     * Ensures the number of enemies does not exceed the total allowed.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies(); // Get the current number of enemies
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) { // Check spawn probability
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition(); // Random Y position
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition); // Create a new enemy
                addEnemyUnit(newEnemy); // Add the enemy to the game
            }
        }
    }

    /**
     * Instantiates the LevelView for LevelOne.
     * Creates and configures the view specific to this level.
     *
     * @return A LevelView instance for LevelOne.
     */
    @Override
    protected LevelView instantiateLevelView() {
        // Create and return a LevelView object for this level, with the player's initial health
        return new LevelView(getRoot(), 5);
    }

    /**
     * Called on each frame update. Updates the game state, user plane, and checks game status.
     */
    @Override
    protected void updateScene() {
        super.updateScene(); // Ensure parent logic is also executed

        // Debugging log for user plane position
        System.out.println("UserPlane Position: X=" + (getUser().getLayoutX() + getUser().getTranslateX()) +
                           ", Y=" + (getUser().getLayoutY() + getUser().getTranslateY()));
    }
}
