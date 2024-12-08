package com.example.demo.actors;

import com.example.demo.levels.LevelBoss;
import java.lang.reflect.Field;

import javafx.application.Platform;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the Boss class.
 */
class BossTest {

    private Boss boss;
    private LevelBoss mockLevelBoss;
    private double screenHeight = 800.0;

    /**
     * Initialize JavaFX runtime before running any tests.
     */
    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> latch.countDown()); // Initialize JavaFX platform
        latch.await(); // Wait for JavaFX initialization to complete
    }

    /**
     * Set up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        mockLevelBoss = mock(LevelBoss.class);
        boss = new Boss(mockLevelBoss, screenHeight);
    }

    /**
     * Test that the boss initializes at the correct position.
     */
    @Test
    void testInitialPosition() {
        assertEquals(1000.0, boss.getLayoutX());
        assertEquals(400.0, boss.getLayoutY());
    }

    /**
     * Test that the boss updates its position within vertical bounds.
     */
    @Test
    void testUpdatePositionWithinBounds() {
        boss.updatePosition();
        double yPos = boss.getLayoutY() + boss.getTranslateY();
        assertTrue(yPos >= 0 && yPos <= screenHeight - 300);
    }

    /**
     * Test that the boss resets position when it moves out of vertical bounds.
     */
    @Test
    void testUpdatePositionOutOfBounds() {
        // Mock the MovementPattern to control movement behavior
        MovementPattern mockPattern = mock(MovementPattern.class);
        when(mockPattern.getNextMove()).thenReturn(0); // Ensure no movement is applied during the test

        // Inject the mocked MovementPattern into the Boss
        Boss bossWithMockPattern = new Boss(mockLevelBoss, screenHeight);
        try {
            Field movementPatternField = Boss.class.getDeclaredField("movementPattern");
            movementPatternField.setAccessible(true); // Allow access to the private field
            movementPatternField.set(bossWithMockPattern, mockPattern);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to inject mocked MovementPattern: " + e.getMessage());
        }

        // Simulate moving out of the upper bound
        bossWithMockPattern.setTranslateY(-1000); // Set Y translation to a value outside the upper boundary
        bossWithMockPattern.updatePosition();
        double yPos = bossWithMockPattern.getLayoutY() + bossWithMockPattern.getTranslateY();
        assertTrue(yPos >= 0, "The boss's position should be corrected to stay within the upper boundary.");

        // Simulate moving out of the lower bound
        bossWithMockPattern.setTranslateY(screenHeight + 1000); // Set Y translation to a value outside the lower boundary
        bossWithMockPattern.updatePosition();
        yPos = bossWithMockPattern.getLayoutY() + bossWithMockPattern.getTranslateY();
        assertTrue(yPos <= screenHeight - 300, "The boss's position should be corrected to stay within the lower boundary.");
    }




    /**
     * Test that the boss fires projectiles at random intervals.
     */
    @Test
    void testFireProjectile() {
        boolean projectileFired = false;
        for (int i = 0; i < 100; i++) { // Simulate multiple frames
            if (boss.fireProjectile() != null) {
                projectileFired = true;
                break;
            }
        }
        assertTrue(projectileFired, "Boss should fire a projectile at least once in 100 frames.");
    }

    /**
     * Test that the boss takes damage when no shield is active.
     */
    @Test
    void testTakeDamageWithoutShield() {
        when(mockLevelBoss.getLevelViewBoss()).thenReturn(null); // No shield active
        int initialHealth = boss.getHealth();
        boss.takeDamage();
        assertEquals(initialHealth - 1, boss.getHealth());
    }

    /**
     * Test that the boss does not take damage when the shield is active.
     */
    @Test
    void testTakeDamageWithShield() {
        // Mock the ShieldManager
        ShieldManager mockShieldManager = mock(ShieldManager.class);
        when(mockShieldManager.isShieldActive()).thenReturn(true); // Force the shield to be active

        // Inject the mocked ShieldManager into the Boss object
        Boss bossWithMockShield = new Boss(mockLevelBoss, screenHeight);
        try {
            Field shieldManagerField = Boss.class.getDeclaredField("shieldManager");
            shieldManagerField.setAccessible(true); // Allow access to the private field
            shieldManagerField.set(bossWithMockShield, mockShieldManager);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to inject mocked ShieldManager: " + e.getMessage());
        }

        // Assume the initial health of the boss
        int initialHealth = bossWithMockShield.getHealth();

        // Simulate the boss taking damage
        bossWithMockShield.takeDamage();

        // Verify that health remains the same since the shield is active
        assertEquals(initialHealth, bossWithMockShield.getHealth(), "Health should remain constant when the shield is active");
    }


    /**
     * Test that the boss correctly detects when the shield is active.
     */
    @Test
    void testIsShielded() {
        // Mock the ShieldManager
        ShieldManager mockShieldManager = mock(ShieldManager.class);
        when(mockShieldManager.isShieldActive()).thenReturn(true); // Force the shield to be active

        // Inject the mocked ShieldManager into the Boss object
        Boss bossWithMockShield = new Boss(mockLevelBoss, screenHeight);
        try {
            Field shieldManagerField = Boss.class.getDeclaredField("shieldManager");
            shieldManagerField.setAccessible(true);
            shieldManagerField.set(bossWithMockShield, mockShieldManager);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to inject mocked ShieldManager: " + e.getMessage());
        }

        // Perform the test
        assertTrue(bossWithMockShield.isShielded(), "Boss should detect shield as active when ShieldManager reports it as active.");
    }


    /**
     * Test that the hitbox is correctly rendered depending on debug mode.
     */
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS) // Prevent hanging tests
    void testRenderHitbox() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // Run the test on the JavaFX Application Thread
        Platform.runLater(() -> {
            try {
                Group root = new Group(); // Simulate a JavaFX root node
                boss.renderHitbox(root);

                if (Boss.isDebugHitboxesEnabled()) {
                    assertFalse(root.getChildren().isEmpty(), "Hitbox should be added to the group when debugging is enabled");
                } else {
                    assertTrue(root.getChildren().isEmpty(), "No hitbox should be added when debugging is disabled");
                }
            } finally {
                latch.countDown(); // Signal that the test is complete
            }
        });

        // Wait for the JavaFX thread to complete the test
        latch.await();
    }
}
