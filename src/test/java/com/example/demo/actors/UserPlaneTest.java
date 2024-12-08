package com.example.demo.actors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the UserPlane class.
 * This test suite verifies the behavior of the UserPlane class,
 * including movement, health management, firing projectiles,
 * and other functionalities.
 */
class UserPlaneTest {

    private UserPlane userPlane;

    /**
     * Initializes the JavaFX toolkit before all tests run.
     * This is necessary for JavaFX-based components to function correctly in a test environment.
     */
    @BeforeAll
    static void initJavaFX() {
        try {
            javafx.application.Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Ignore if JavaFX is already initialized
        }
    }

    /**
     * Sets up the test environment before each test.
     * Initializes a UserPlane instance with a default health of 10.
     */
    @BeforeEach
    void setUp() {
        System.setProperty("java.awt.headless", "true"); // Enable headless mode for testing
        userPlane = new UserPlane(10); // Initialize the UserPlane with 10 health
    }

    /**
     * Verifies the initial health of the UserPlane.
     */
    @Test
    void testInitialHealth() {
        assertEquals(10, userPlane.getHealth(), "Initial health should be 10.");
    }

    /**
     * Tests the initial position of the UserPlane.
     */
    @Test
    void testInitialPosition() {
        assertEquals(300.0, userPlane.getLayoutY(), "Initial Y position should be 300.");
        assertEquals(5.0, userPlane.getLayoutX(), "Initial X position should be 5.");
    }

    /**
     * Tests upward movement of the UserPlane.
     * Ensures the Y-translation decreases when moving up.
     */
    @Test
    void testMoveUp() {
        userPlane.moveUp();
        userPlane.updatePosition();
        assertTrue(userPlane.getTranslateY() < 0, "TranslateY should decrease when moving up.");
    }

    /**
     * Tests downward movement of the UserPlane.
     * Ensures the Y-translation increases when moving down.
     */
    @Test
    void testMoveDown() {
        userPlane.moveDown();
        userPlane.updatePosition();
        assertTrue(userPlane.getTranslateY() > 0, "TranslateY should increase when moving down.");
    }

    /**
     * Tests stopping the movement of the UserPlane.
     * Ensures the velocity multiplier is reset, and no movement occurs.
     */
    @Test
    void testStopMovement() {
        userPlane.moveDown();
        userPlane.stop();
        userPlane.updatePosition();
        assertEquals(0, userPlane.getTranslateY(), "TranslateY should remain unchanged after stop.");
    }

    /**
     * Tests firing a projectile from the UserPlane.
     * Ensures a projectile is created and not null.
     */
    @Test
    void testFireProjectile() {
        var projectile = userPlane.fireProjectile();
        assertNotNull(projectile, "Projectile should not be null when fired.");
    }

    /**
     * Verifies the kill count increments correctly.
     */
    @Test
    void testKillCountIncrement() {
        int initialKills = userPlane.getNumberOfKills();
        userPlane.incrementKillCount();
        assertEquals(initialKills + 1, userPlane.getNumberOfKills(), "Kill count should increment by 1.");
    }

    /**
     * Verifies the kill count resets correctly.
     */
    @Test
    void testKillCountReset() {
        userPlane.incrementKillCount();
        userPlane.resetKillCount();
        assertEquals(0, userPlane.getNumberOfKills(), "Kill count should reset to 0.");
    }

    /**
     * Tests the reduced bounds for collision detection.
     * Ensures the bounds are smaller than the original size of the plane.
     */
    @Test
    void testReducedBounds() {
        var bounds = userPlane.getReducedBounds();
        var originalBounds = userPlane.getBoundsInLocal();
        double margin = 20;

        // Assert reduced width and height
        assertTrue(bounds.getWidth() < originalBounds.getWidth(), "Reduced bounds width should be less than original width.");
        assertTrue(bounds.getHeight() < originalBounds.getHeight(), "Reduced bounds height should be less than original height.");

        // Assert exact calculations
        assertEquals(originalBounds.getWidth() - 2 * margin, bounds.getWidth(), "Width reduction mismatch.");
        assertEquals(originalBounds.getHeight() - 2 * margin, bounds.getHeight(), "Height reduction mismatch.");
    }

    /**
     * Verifies health is reduced when the UserPlane takes damage.
     */
    @Test
    void testHealthReduction() {
        int initialHealth = userPlane.getHealth();
        userPlane.takeDamage();
        assertEquals(initialHealth - 1, userPlane.getHealth(), "Health should decrease by 1 when damaged.");
    }

    /**
     * Tests that the UserPlane is destroyed when its health reaches zero.
     * Also verifies the UserPlane is inactive upon destruction.
     */
    @Test
    void testDestroyPlaneOnZeroHealth() {
        while (userPlane.getHealth() > 0) {
            userPlane.takeDamage();
        }
        assertEquals(0, userPlane.getHealth(), "Health should be 0 after sufficient damage.");
        assertFalse(userPlane.isActive(), "Plane should be inactive when health is zero.");
    }

    /**
     * Tests if the position updates correctly during each game loop iteration.
     */
    @Test
    void testUpdatePosition() {
        userPlane.moveUp();
        userPlane.updatePosition();
        assertTrue(userPlane.getTranslateY() < 0, "TranslateY should be updated correctly during movement.");
    }

    /**
     * Tests the toString method of UserPlane.
     * Ensures the returned string contains relevant state information.
     */
    @Test
    void testToString() {
        String planeString = userPlane.toString();
        assertTrue(planeString.contains("velocityMultiplier"), "toString should include velocityMultiplier.");
        assertTrue(planeString.contains("killCount"), "toString should include killCount.");
        assertTrue(planeString.contains("positionX"), "toString should include positionX.");
        assertTrue(planeString.contains("positionY"), "toString should include positionY.");
    }
}
