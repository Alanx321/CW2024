package com.example.demo.projectiles;

import com.example.demo.actors.UserPlane;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * UserProjectile represents the projectiles fired by the player's plane.
 * It moves horizontally to the right with a specified velocity and has a reduced hitbox for better precision.
 * The projectile's position is dynamically updated to follow the UserPlane if a reference is provided.
 */
public class UserProjectile extends Projectile {

    // Constants for projectile properties
    private static final String IMAGE_NAME = "projectile.png";  // Image representing the projectile
    private static final int IMAGE_HEIGHT = 125;               // Height of the projectile image
    private static final int HORIZONTAL_VELOCITY = 15;         // Speed of horizontal movement
    private static final int HITBOX_MARGIN = 30;               // Margin to reduce the hitbox size
    private static final boolean DEBUG_HITBOXES = false;       // Enable/disable hitbox visualization

    private final UserPlane userPlane; // Reference to the user plane (can be null for static projectiles)

    /**
     * Constructor for UserProjectile using a UserPlane reference.
     * The projectile will follow the UserPlane dynamically.
     *
     * @param userPlane The UserPlane instance firing this projectile.
     */
    public UserProjectile(UserPlane userPlane) {
        super(IMAGE_NAME, IMAGE_HEIGHT, userPlane.getLayoutX() + 110, userPlane.getLayoutY());
        this.userPlane = userPlane; // Store the reference to the UserPlane
    }

    /**
     * Constructor for UserProjectile using static positions.
     * The projectile will not follow a UserPlane dynamically.
     *
     * @param initialXPos The initial X position of the projectile.
     * @param initialYPos The initial Y position of the projectile.
     */
    public UserProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
        this.userPlane = null; // No reference to a UserPlane (static projectile)
    }

    /**
     * Updates the position of the projectile.
     * Synchronizes the projectile's position with the UserPlane's position if a reference exists.
     * Otherwise, it moves horizontally at a constant velocity.
     */
    @Override
    public void updatePosition() {
        if (userPlane != null) {
            // Dynamically align with the UserPlane
            setTranslateX(userPlane.getLayoutX() + 110);
            setTranslateY(userPlane.getLayoutY());
        } else {
            // Move horizontally without syncing (static projectile behavior)
            moveHorizontally(HORIZONTAL_VELOCITY);
        }
    }

    /**
     * Updates the actor's state. In this case, it updates the projectile's position.
     */
    @Override
    public void updateActor() {
        updatePosition(); // Call the updatePosition method to update the projectile
    }

    /**
     * Calculates the reduced hitbox bounds for the projectile.
     * This reduces the size of the hitbox for more precise collision detection.
     *
     * @return A BoundingBox representing the reduced hitbox of the projectile.
     */
    @Override
    public Bounds getReducedBounds() {
        Bounds originalBounds = this.getBoundsInLocal(); // Get the original bounds
        return new BoundingBox(
            originalBounds.getMinX() + HITBOX_MARGIN,    // Add a margin to the left
            originalBounds.getMinY() + HITBOX_MARGIN,    // Add a margin to the top
            originalBounds.getWidth() - 2 * HITBOX_MARGIN,  // Reduce the width by twice the margin
            originalBounds.getHeight() - 2 * HITBOX_MARGIN  // Reduce the height by twice the margin
        );
    }

    /**
     * Optional: Render the hitbox for debugging purposes.
     * Adds a rectangle to the game scene to visualize the reduced hitbox.
     *
     * @param root The Group object representing the game scene's root node.
     */
    public void renderHitbox(Group root) {
        if (!DEBUG_HITBOXES) return; // Skip rendering if debugging is disabled

        // Get the reduced hitbox bounds
        Bounds bounds = getReducedBounds();

        // Create a rectangle to represent the hitbox
        Rectangle hitbox = new Rectangle(
            bounds.getMinX(),
            bounds.getMinY(),
            bounds.getWidth(),
            bounds.getHeight()
        );
        hitbox.setFill(Color.TRANSPARENT); // Transparent fill for the hitbox
        hitbox.setStroke(Color.RED);       // Red outline for visibility
        root.getChildren().add(hitbox);    // Add the hitbox rectangle to the game scene
    }

    /**
     * Handles damage taken by the projectile.
     * Projectiles are immediately destroyed when they take damage.
     */
    @Override
    public void takeDamage() {
        // Destroy the projectile upon taking damage
        destroy();
        System.out.println("UserProjectile took damage and was destroyed."); // Debug message
    }

    /**
     * Retrieves a string representation of the UserProjectile.
     * Useful for debugging or logging purposes.
     *
     * @return A string containing details about the projectile.
     */
    @Override
    public String toString() {
        return "UserProjectile{" +
                "positionX=" + getLayoutX() +
                ", positionY=" + getLayoutY() +
                ", followsUserPlane=" + (userPlane != null) +
                '}';
    }
}
