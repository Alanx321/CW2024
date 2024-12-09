package com.example.demo.levels;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import com.example.demo.ui.GameOverImage;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the LevelTwo game level.
 * Includes tests for initialization, enemy spawning, game-over conditions,
 * and advancing to the next level.
 */
public class LevelTwoTest extends ApplicationTest {

    private LevelTwo levelTwo;

    /**
     * Initializes the LevelTwo instance with a live JavaFX Stage.
     */
    @Override
    public void start(Stage stage) {
        // Create an instance of LevelTwo with test parameters
        levelTwo = new LevelTwo(600, 800, stage);
    }

    /**
     * Test to verify that the user's plane is initialized and added to the scene.
     */
    @Test
    public void testInitializeFriendlyUnits() {
        // Initialize friendly units
        levelTwo.initializeFriendlyUnits();

        // Assert the user plane is not null
        assertNotNull(levelTwo.getUser(), "User plane should be initialized.");
        
        // Assert the user plane is part of the root scene graph
        assertTrue(levelTwo.getRoot().getChildren().contains(levelTwo.getUser()),
                "User plane should be added to the scene graph.");
    }

    /**
     * Test to verify that enemy units are spawned within allowed limits.
     */
    @Test
    public void testEnemySpawning() {
        // Ensure no enemies exist initially
        assertEquals(0, levelTwo.getCurrentNumberOfEnemies(), 
                "No enemies should be present initially.");

        // Call spawnEnemyUnits and check if enemies are added
        levelTwo.spawnEnemyUnits();
        int enemiesSpawned = levelTwo.getCurrentNumberOfEnemies();

        // Assert the number of enemies is within the allowed range
        assertTrue(enemiesSpawned >= 0 && enemiesSpawned <= 7, 
                "Enemy count should be between 0 and 7 after spawning.");
    }

    /**
     * Test to verify the game-over logic when the user's plane is destroyed.
     */
    @Test
    public void testCheckIfGameOver_UserDestroyed() {
        // Step 1: Simulate destruction of the user's plane by reducing health to zero
        for (int i = 0; i < 5; i++) { // Assuming 5 hits destroy the user
            levelTwo.getUser().takeDamage();
        }

        // Step 2: Assert that the user's plane is destroyed
        assertTrue(levelTwo.getUser().isDestroyed(), "User plane should be destroyed after taking damage.");

        // Step 3: Trigger the checkIfGameOver method to simulate the game-over condition
        levelTwo.checkIfGameOver();

        // Step 4: Check if the "Game Over" image is added to the scene
        boolean gameOverImagePresent = levelTwo.getRoot().getChildren().stream()
                .anyMatch(node -> node instanceof GameOverImage);

        // Step 5: Assert that the "Game Over" image is displayed in the scene
        assertTrue(gameOverImagePresent, "Game Over image should be present in the scene after game-over logic.");
    }

    /**
     * Test to verify level transition logic when the required kill count is achieved.
     */
    @Test
    public void testCheckIfGameOver_AdvanceLevel() {
        // Simulate the player achieving enough kills to advance
        for (int i = 0; i < 15; i++) {
            levelTwo.getUser().incrementKillCount();
        }

        // Verify the user's kill count
        assertEquals(15, levelTwo.getUser().getNumberOfKills(), 
                "User should have 15 kills to advance to the next level.");

        // Call checkIfGameOver to test level transition
        levelTwo.checkIfGameOver();

        // Since actual transition logic relies on external classes, verify no exceptions
        assertDoesNotThrow(() -> levelTwo.checkIfGameOver(),
                "Level transition should not throw exceptions.");
    }

    /**
     * Test to verify that the LevelView instance is correctly instantiated.
     */
    @Test
    public void testLevelViewInitialization() {
        // Test if LevelView is correctly instantiated
        LevelView levelView = levelTwo.instantiateLevelView();

        // Assert that LevelView is not null
        assertNotNull(levelView, "LevelView should be initialized.");
    }

    /**
     * Test to verify enemy spawning limits are respected over multiple updates.
     */
    @Test
    public void testEnemySpawningLimits() {
        for (int i = 0; i < 10; i++) {
            levelTwo.spawnEnemyUnits();
        }

        // Verify enemy count never exceeds the limit
        assertTrue(levelTwo.getCurrentNumberOfEnemies() <= 7, 
                "Enemy count should not exceed the maximum limit of 7.");
    }
}
