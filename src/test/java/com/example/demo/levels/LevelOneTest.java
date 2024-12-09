package com.example.demo.levels;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import com.example.demo.ui.GameOverImage;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the LevelOne game level.
 * Includes tests for initialization, enemy spawning, game over conditions,
 * and advancing to the next level.
 */
public class LevelOneTest extends ApplicationTest {

    private LevelOne levelOne;

    @Override
    public void start(Stage stage) {
        // Initialize the LevelOne instance with a mock Stage
        levelOne = new LevelOne(600, 800, stage);
    }

    /**
     * Test to verify that the user's plane is initialized and added to the scene.
     */
    @Test
    public void testInitializeFriendlyUnits() {
        // Initialize friendly units
        levelOne.initializeFriendlyUnits();

        // Assert the user plane is not null
        assertNotNull(levelOne.getUser(), "User plane should be initialized.");
        
        // Assert the user plane is part of the root scene graph
        assertTrue(levelOne.getRoot().getChildren().contains(levelOne.getUser()),
                "User plane should be added to the scene graph.");
    }

    /**
     * Test to verify that enemy units are spawned within allowed limits.
     */
    @Test
    public void testEnemySpawning() {
        // Ensure no enemies exist initially
        assertEquals(0, levelOne.getCurrentNumberOfEnemies(), 
                "No enemies should be present initially.");

        // Call spawnEnemyUnits and check if enemies are added
        levelOne.spawnEnemyUnits();
        int enemiesSpawned = levelOne.getCurrentNumberOfEnemies();

        // Assert the number of enemies is within the allowed range
        assertTrue(enemiesSpawned >= 0 && enemiesSpawned <= 5, 
                "Enemy count should be between 0 and 5 after spawning.");
    }

    /**
     * Test to verify the game-over logic when the user's plane is destroyed.
     */
    @Test
    public void testCheckIfGameOver_UserDestroyed() {
        // Step 1: Simulate destruction of the user's plane by reducing health to zero
        for (int i = 0; i < 5; i++) { // Assuming 5 hits destroy the user
            levelOne.getUser().takeDamage();
        }

        // Step 2: Assert that the user's plane is destroyed
        assertTrue(levelOne.getUser().isDestroyed(), "User plane should be destroyed after taking damage.");

        // Step 3: Trigger the checkIfGameOver method to simulate the game-over condition
        levelOne.checkIfGameOver();

        // Step 4: Check if the "Game Over" image is added to the scene
        boolean gameOverImagePresent = levelOne.getRoot().getChildren().stream()
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
        for (int i = 0; i < 10; i++) {
            levelOne.getUser().incrementKillCount();
        }

        // Verify the user's kill count
        assertEquals(10, levelOne.getUser().getNumberOfKills(), 
                "User should have 10 kills to advance to the next level.");

        // Call checkIfGameOver to test level transition
        levelOne.checkIfGameOver();

        // Since actual transition logic relies on external classes, verify no exceptions
        assertDoesNotThrow(() -> levelOne.checkIfGameOver(),
                "Level transition should not throw exceptions.");
    }

    /**
     * Test to verify that the LevelView instance is correctly instantiated.
     */
    @Test
    public void testLevelViewInitialization() {
        // Test if LevelView is correctly instantiated
        LevelView levelView = levelOne.instantiateLevelView();

        // Assert that LevelView is not null
        assertNotNull(levelView, "LevelView should be initialized.");
    }

    

    /**
     * Test to verify enemy spawning limits are respected over multiple updates.
     */
    @Test
    public void testEnemySpawningLimits() {
        for (int i = 0; i < 10; i++) {
            levelOne.spawnEnemyUnits();
        }

        // Verify enemy count never exceeds the limit
        assertTrue(levelOne.getCurrentNumberOfEnemies() <= 5, 
                "Enemy count should not exceed the maximum limit of 5.");
    }
}
