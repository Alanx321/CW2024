package com.example.demo.actors;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Abstract class representing a fighter plane in the game.
 * This class provides common functionality for all planes, such as health, collision bounds, and firing capabilities.
 * Subclasses must define specific behaviors like firing projectiles and updating during the game loop.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

    // Health of the fighter plane
    private int health;

    // Debug flag to enable or disable hitbox visualization
    private static final boolean DEBUG_HITBOXES = true;

    /**
     * Constructor for FighterPlane.
     * Initializes the plane's image, position, and health.
     *
     * @param imageName    Name of the image file representing the fighter plane.
     * @param imageHeight  Height of the fighter plane's image.
     * @param initialXPos  Initial X position of the fighter plane.
     * @param initialYPos  Initial Y position of the fighter plane.
     * @param health       Initial health of the fighter plane.
     */
    public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
        super(imageName, imageHeight, initialXPos, initialYPos); // Call the parent class constructor
        this.health = health; // Set the initial health
    }

    /**
     * Abstract method for firing projectiles.
     * Subclasses must implement this method to define specific firing behavior.
     *
     * @return An ActiveActorDestructible projectile if fired, otherwise null.
     */
    public abstract ActiveActorDestructible fireProjectile();

    /**
     * Reduces the health of the fighter plane by one when it takes damage.
     * If the health reaches zero, the plane is destroyed.
     */
    @Override
    public void takeDamage() {
        health--; // Decrease health by 1
        System.out.println("FighterPlane took damage. Current health: " + health);
        if (health <= 0) { // Check if health is zero or less
            this.destroy(); // Destroy the plane
            System.out.println("FighterPlane destroyed.");
        }
    }

    /**
     * Retrieves the current health of the fighter plane.
     *
     * @return The current health value.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Retrieves reduced bounds for collision detection.
     * The reduced bounds create a smaller hitbox for more precise collisions.
     *
     * @return A BoundingBox representing the reduced hitbox.
     */
    @Override
    public Bounds getReducedBounds() {
        // Get the original bounds of the plane
        Bounds originalBounds = this.getBoundsInLocal();

        // Create a smaller bounding box with a margin for more precise collision
        return new BoundingBox(
            originalBounds.getMinX() + 30, // Add margin to the left
            originalBounds.getMinY() + 30, // Add margin to the top
            originalBounds.getWidth() - 60, // Reduce width by twice the margin
            originalBounds.getHeight() - 60 // Reduce height by twice the margin
        );
    }

    /**
     * Calculates the X position for firing a projectile.
     * The position is relative to the fighter plane's current X position.
     *
     * @param xPositionOffset Offset from the fighter plane's X position.
     * @return The calculated X position for the projectile.
     */
    protected double getProjectileXPosition(double xPositionOffset) {
        return getLayoutX() + getTranslateX() + xPositionOffset;
    }

    /**
     * Calculates the Y position for firing a projectile.
     * The position is relative to the fighter plane's current Y position.
     *
     * @param yPositionOffset Offset from the fighter plane's Y position.
     * @return The calculated Y position for the projectile.
     */
    protected double getProjectileYPosition(double yPositionOffset) {
        return getLayoutY() + getTranslateY() + yPositionOffset;
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
     * Abstract method to update the fighter plane's behavior during the game loop.
     * Subclasses must define specific logic for updating the plane's position, firing, etc.
     */
    @Override
    public abstract void updateActor();

    /**
     * Provides detailed debug information about the fighter plane.
     *
     * @return A string containing information about the fighter plane.
     */
    @Override
    public String toString() {
        return "FighterPlane{" +
                "health=" + health +
                ", layoutX=" + getLayoutX() +
                ", layoutY=" + getLayoutY() +
                ", translateX=" + getTranslateX() +
                ", translateY=" + getTranslateY() +
                '}';
    }
}
