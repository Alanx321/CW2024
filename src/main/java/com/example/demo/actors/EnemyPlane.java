package com.example.demo.actors;

import com.example.demo.projectiles.EnemyProjectile;
import com.example.demo.utils.HitboxRenderer;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;

/**
 * EnemyPlane represents an enemy aircraft in the game.
 * It extends FighterPlane and includes logic for movement, firing projectiles, and collision handling.
 */
public class EnemyPlane extends FighterPlane {

    // Constants for configuring the enemy plane
    private static final String IMAGE_NAME = "enemyplane.png"; // File name for the enemy plane's image
    private static final int IMAGE_HEIGHT = 150; // Height of the image for scaling
    private static final int HORIZONTAL_VELOCITY = -6; // Speed of horizontal movement
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0; // X offset for projectile spawn
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;   // Y offset for projectile spawn
    private static final int INITIAL_HEALTH = 1; // Initial health of the enemy plane
    private static final double FIRE_RATE = 0.04; // Probability of firing a projectile each frame
    private static final boolean DEBUG_HITBOXES = true; // Debug flag to control hitbox rendering

    /**
     * Constructor for EnemyPlane.
     *
     * @param initialXPos The initial X position of the enemy plane.
     * @param initialYPos The initial Y position of the enemy plane.
     */
    public EnemyPlane(double initialXPos, double initialYPos) {
        // Call the parent class constructor with the necessary parameters
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    /**
     * Updates the position of the enemy plane.
     * Moves the plane horizontally to simulate flight.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY); // Move the plane left at a constant velocity
    }

    /**
     * Fires a projectile from the enemy plane with a certain probability.
     *
     * @return A new EnemyProjectile instance if fired, otherwise null.
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) { // Check if a random chance allows firing
            // Calculate the position for the projectile
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            // Return a new projectile created at the calculated position
            return new EnemyProjectile(projectileXPosition, projectileYPosition);
        }
        return null; // Return null if no projectile is fired
    }

    /**
     * Updates the state of the enemy plane.
     * Includes movement and other periodic updates.
     */
    @Override
    public void updateActor() {
        updatePosition(); // Update the position of the enemy plane
        // Add additional state updates if needed in the future
    }

    /**
     * Retrieves the reduced bounds for collision detection.
     * Shrinks the hitbox for more forgiving gameplay.
     *
     * @return A BoundingBox representing the reduced hitbox.
     */
    @Override
    public Bounds getReducedBounds() {
        // Get the original bounds from the plane's current position
        Bounds originalBounds = this.getBoundsInLocal();
        double margin = 25; // Define how much to shrink the hitbox
        // Return a reduced bounding box with margins applied
        return new BoundingBox(
            originalBounds.getMinX() + margin,
            originalBounds.getMinY() + margin,
            originalBounds.getWidth() - 2 * margin,
            originalBounds.getHeight() - 2 * margin
        );
    }

    /**
     * Renders the reduced hitbox for debugging purposes.
     *
     * @param root The Group object representing the game scene's root node.
     */
    public void renderHitbox(Group root) {
        if (DEBUG_HITBOXES) { // Only render the hitbox if debugging is enabled
            // Delegate the rendering task to the HitboxRenderer utility
            HitboxRenderer.renderHitbox(getReducedBounds(), root);
        }
    }
}
