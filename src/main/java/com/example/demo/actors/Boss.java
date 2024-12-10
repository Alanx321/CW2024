package com.example.demo.actors;

import com.example.demo.levels.LevelBoss;
import com.example.demo.projectiles.BossProjectile;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import com.example.demo.ui.ShieldImage;

/**
 * Represents the boss plane in the game with movement, shields, and firing capabilities.
 */
public class Boss extends FighterPlane {

    // Image file name for the boss plane
    private static final String IMAGE_NAME = "bossplane.png";

    // Initial position of the boss plane
    private static final double INITIAL_X_POSITION = 1000.0; // Starting X position
    private static final double INITIAL_Y_POSITION = 400.0;  // Starting Y position

    // Visual and gameplay parameters for the boss plane
    private static final int IMAGE_HEIGHT = 300; // Height of the boss plane's image
    private static final int VERTICAL_VELOCITY = 8; // Speed of vertical movement
    private static final int HEALTH = 50; // Initial health of the boss plane
    private static final boolean DEBUG_HITBOXES = true; // Enable or disable hitbox visualization

    // Dynamic movement boundaries based on the game screen
    private final double Y_POSITION_UPPER_BOUND; // Upper boundary for vertical movement
    private final double Y_POSITION_LOWER_BOUND; // Lower boundary for vertical movement

    // Reference to the LevelBoss instance
    private final LevelBoss levelBoss;

    // Manager for the boss's shield
    private final ShieldManager shieldManager;

    // Manager for the boss's movement pattern
    private final MovementPattern movementPattern;

    /**
     * Constructs a new Boss instance.
     *
     * @param levelBoss The level containing this boss.
     * @param screenHeight The height of the game screen.
     */
    public Boss(LevelBoss levelBoss, double screenHeight) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH); // Initialize parent class
        this.levelBoss = levelBoss; // Set reference to the level
        this.shieldManager = new ShieldManager(levelBoss); // Initialize shield manager
        this.movementPattern = new MovementPattern(VERTICAL_VELOCITY, 5, 10); // Initialize movement pattern manager

        // Dynamically calculate movement boundaries
        this.Y_POSITION_UPPER_BOUND = 0; // Top of the screen
        this.Y_POSITION_LOWER_BOUND = screenHeight - IMAGE_HEIGHT; // Bottom of the screen, adjusted for boss size
    }

    /**
     * Updates the position of the boss and synchronizes the shield.
     */
    @Override
    public void updatePosition() {

        // Get the next move value from the movement pattern and move vertically
        moveVertically(movementPattern.getNextMove());

        // Ensure the boss stays within vertical boundaries
        double currentPosition = getLayoutY() + getTranslateY();

        // Correct position if out of bounds
        if (currentPosition < Y_POSITION_UPPER_BOUND) {
            setTranslateY(Y_POSITION_UPPER_BOUND - getLayoutY()); // Reset to upper boundary
        } else if (currentPosition > Y_POSITION_LOWER_BOUND) {
            setTranslateY(Y_POSITION_LOWER_BOUND - getLayoutY()); // Reset to lower boundary
        }

        synchronizeShieldPosition(); // Ensure shield stays aligned with the boss
    }


    /**
     * Synchronizes the shield's position with the boss's position.
     */
    private void synchronizeShieldPosition() {
        if (levelBoss != null && levelBoss.getLevelViewBoss() != null) {
            ShieldImage shieldImage = levelBoss.getLevelViewBoss().getShieldImage(); // Get the shield image
            if (shieldImage != null) {
                shieldImage.setLayoutX(getLayoutX() + 30); // Position shield horizontally relative to the boss
                shieldImage.setLayoutY(getLayoutY() + getTranslateY() - 50); // Position shield vertically relative to the boss
            }
        }
    }

    /**
     * Updates the boss, including movement and shield logic.
     */
    @Override
    public void updateActor() {
        updatePosition(); // Update position of the boss
        shieldManager.updateShield(); // Update shield state
    }

    /**
     * Fires a projectile if the fire condition is met.
     *
     * @return A new BossProjectile or null if no projectile is fired.
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        // Fire a projectile with a 4% chance per frame
        if (Math.random() < 0.04) {
            return new BossProjectile(getLayoutY() + getTranslateY()); // Create and return the projectile
        }
        return null; // Return null if no projectile is fired
    }

    /**
     * Handles damage taken by the boss. Damage is blocked if the shield is active.
     */
    @Override
    public void takeDamage() {
        if (!shieldManager.isShieldActive()) { // Check if shield is inactive
            super.takeDamage(); // Reduce health using the parent class method
            levelBoss.updateBossHealthDisplay(getHealth()); // Notify LevelBoss to update the health display
        }
    }

    /**
     * Renders the boss's hitbox for debugging purposes.
     *
     * @param root The root group to which the hitbox will be added.
     */
    public void renderHitbox(Group root) {
        if (!DEBUG_HITBOXES) return; // Skip rendering if debugging is disabled

        Bounds bounds = getReducedBounds(); // Get reduced hitbox bounds

        // Create a rectangle representing the hitbox
        Rectangle hitbox = new Rectangle(
            bounds.getMinX(),
            bounds.getMinY(),
            bounds.getWidth(),
            bounds.getHeight()
        );
        hitbox.setFill(Color.TRANSPARENT); // Make the fill transparent
        hitbox.setStroke(Color.RED); // Set a red border for the hitbox
        hitbox.setStrokeWidth(2); // Set stroke width

        root.getChildren().add(hitbox); // Add the hitbox to the root group
    }

    /**
     * Retrieves a reduced hitbox for collision detection.
     *
     * @return A smaller bounding box for more precise collision detection.
     */
    @Override
    public Bounds getReducedBounds() {
        Bounds originalBounds = this.getBoundsInLocal(); // Get original bounds
        double margin = 30; // Margin to reduce the hitbox size
        return new BoundingBox(
            originalBounds.getMinX() + margin,
            originalBounds.getMinY() + margin,
            originalBounds.getWidth() - 2 * margin,
            originalBounds.getHeight() - 2 * margin
        );
    }

    /**
     * Checks if the boss currently has an active shield.
     *
     * @return True if the shield is active, otherwise false.
     */
    public boolean isShielded() {
        return shieldManager.isShieldActive(); // Delegate shield status to ShieldManager
    }
    
 // This method returns the current state of the DEBUG_HITBOXES flag.
 // It indicates whether debug hitboxes are enabled in the application.
 public static boolean isDebugHitboxesEnabled() {
     return DEBUG_HITBOXES;
 }
}