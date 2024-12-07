package com.example.demo.projectiles;

import com.example.demo.actors.ActiveActorDestructible;
import javafx.geometry.Bounds;
import javafx.geometry.BoundingBox;

/**
 * StraightProjectile represents a projectile that moves in a straight line.
 * Extends ActiveActorDestructible for movement, collision, and destruction behavior.
 */
public class StraightProjectile extends ActiveActorDestructible {

    // Speed of the projectile in the Y direction
    private final double speed;

    /**
     * Constructor for StraightProjectile.
     *
     * @param imageName     The file name of the image representing the projectile.
     * @param imageHeight   The height of the projectile image.
     * @param initialXPos   The initial X position of the projectile.
     * @param initialYPos   The initial Y position of the projectile.
     * @param speed         The speed at which the projectile moves.
     */
    public StraightProjectile(String imageName, int imageHeight, double initialXPos, double initialYPos, double speed) {
        super(imageName, imageHeight, initialXPos, initialYPos); // Call the parent class constructor
        this.speed = speed; // Set the speed of the projectile
    }

    /**
     * Updates the position of the projectile during the game loop.
     * Moves the projectile in a straight line based on its speed.
     */
    @Override
    public void updatePosition() {
        // Move the projectile upwards or downwards based on the speed
        setTranslateY(getTranslateY() + speed);
    }

    /**
     * Implements the updateActor method required by ActiveActorDestructible.
     * Called during each frame to update the projectile's state.
     */
    @Override
    public void updateActor() {
        // Update the position of the projectile
        updatePosition();

        // Check if the projectile is out of bounds
        if (isOutOfBounds()) {
            // Destroy the projectile if it goes out of the game area
            destroy();
            System.out.println("StraightProjectile went out of bounds and was destroyed."); // Debug message
        }
    }

    /**
     * Handles damage taken by the projectile.
     * Projectiles are immediately destroyed when they take damage.
     */
    @Override
    public void takeDamage() {
        // Destroy the projectile upon taking damage
        destroy();
        System.out.println("StraightProjectile took damage and was destroyed."); // Debug message
    }

    /**
     * Checks if the projectile has moved out of the game boundaries.
     * This is an example and should be replaced with actual boundary logic.
     *
     * @return True if the projectile is out of bounds, otherwise false.
     */
    public boolean isOutOfBounds() {
        // Replace with actual game boundary conditions as needed
        Bounds bounds = this.getBoundsInParent(); // Get the current bounds in parent coordinate space
        return bounds.getMinY() < 0 || bounds.getMaxY() > 800; // Example: 0 to 800 represents the screen height
    }

    /**
     * Retrieves the reduced collision bounds of the projectile.
     * Used for more precise collision detection with a smaller hitbox.
     *
     * @return Reduced bounds as a BoundingBox.
     */
    @Override
    public Bounds getReducedBounds() {
        // Get the original bounds of the projectile
        Bounds originalBounds = this.getBoundsInLocal();

        // Reduce the bounds by applying a margin
        return new BoundingBox(
            originalBounds.getMinX() + 5, // Add a margin to the X position
            originalBounds.getMinY() + 5, // Add a margin to the Y position
            originalBounds.getWidth() - 10, // Reduce the width by 10
            originalBounds.getHeight() - 10 // Reduce the height by 10
        );
    }

    /**
     * Retrieves a string representation of the StraightProjectile object.
     *
     * @return A string containing details about the projectile.
     */
    @Override
    public String toString() {
        // Return a string with the projectile's properties for debugging
        return "StraightProjectile{" +
                "speed=" + speed +
                ", positionX=" + getLayoutX() +
                ", positionY=" + getLayoutY() +
                '}';
    }

    /**
     * Example main method to demonstrate the usage of the StraightProjectile class.
     * This is for testing purposes only and can be removed in production.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create a StraightProjectile object with test parameters
        StraightProjectile projectile = new StraightProjectile("projectile.png", 20, 100, 500, -5.0);

        // Print the initial state of the projectile
        System.out.println("Initial Projectile: " + projectile);

        // Simulate a simple game loop to update the projectile's position
        for (int i = 0; i < 20; i++) {
            projectile.updateActor(); // Update the projectile's state
            System.out.println("Updated Projectile: " + projectile); // Print the updated state

            // Break the loop if the projectile is destroyed
            if (projectile.isDestroyed()) {
                System.out.println("Projectile has been destroyed.");
                break;
            }
        }
    }
}
