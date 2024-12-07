package com.example.demo.projectiles;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.UserPlane;

/**
 * DefaultProjectileFiringBehavior implements the behavior for firing a single projectile
 * in a straight line. This class is responsible for creating and returning a projectile
 * instance based on the specified parameters.
 */
public class DefaultProjectileFiringBehavior implements ProjectileFiringBehavior {

    // The image file name for the projectile
    private final String projectileImage;

    // The height of the projectile's image
    private final int projectileHeight;

    // The speed of the projectile
    private final double projectileSpeed;

    // Reference to the firing actor
    private ActiveActorDestructible firingActor;

    /**
     * Constructor for DefaultProjectileFiringBehavior.
     *
     * @param projectileImage  The file name of the image representing the projectile.
     * @param projectileHeight The height of the projectile image.
     * @param projectileSpeed  The speed at which the projectile moves.
     */
    public DefaultProjectileFiringBehavior(String projectileImage, int projectileHeight, double projectileSpeed) {
        this.projectileImage = projectileImage; // Set the projectile image name
        this.projectileHeight = projectileHeight; // Set the projectile height
        this.projectileSpeed = projectileSpeed; // Set the projectile speed
    }

    /**
     * Sets the firing actor for this projectile firing behavior.
     *
     * @param firingActor The actor that will fire the projectile.
     */
    public void setFiringActor(ActiveActorDestructible firingActor) {
        this.firingActor = firingActor;
    }

    /**
     * Fires a single projectile based on the firing actor's current position.
     * Creates a UserProjectile if the firing actor is a UserPlane.
     *
     * @param xPosition The X coordinate for the projectile's starting position.
     * @param yPosition The Y coordinate for the projectile's starting position.
     * @return An instance of ActiveActorDestructible representing the fired projectile.
     * @throws IllegalArgumentException if the firing actor is not supported.
     */
    @Override
    public ActiveActorDestructible fireProjectile(double xPosition, double yPosition) {
        return new UserProjectile(xPosition, yPosition);
    }


    /**
     * Retrieves the projectile image file name used by this firing behavior.
     *
     * @return The image file name of the projectile.
     */
    public String getProjectileImage() {
        return projectileImage; // Return the projectile image name
    }

    /**
     * Retrieves the height of the projectile image used by this firing behavior.
     *
     * @return The height of the projectile image.
     */
    public int getProjectileHeight() {
        return projectileHeight; // Return the projectile height
    }

    /**
     * Retrieves the speed of the projectile used by this firing behavior.
     *
     * @return The speed of the projectile.
     */
    public double getProjectileSpeed() {
        return projectileSpeed; // Return the projectile speed
    }

    /**
     * Provides a string representation of the DefaultProjectileFiringBehavior object.
     *
     * @return A string describing the projectile firing behavior.
     */
    @Override
    public String toString() {
        // Return a string with the projectile firing behavior details
        return "DefaultProjectileFiringBehavior{" +
                "projectileImage='" + projectileImage + '\'' +
                ", projectileHeight=" + projectileHeight +
                ", projectileSpeed=" + projectileSpeed +
                '}';
    }

    /**
     * Example main method to demonstrate the usage of DefaultProjectileFiringBehavior.
     * This is for testing purposes only and can be removed in production.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create a DefaultProjectileFiringBehavior instance
        DefaultProjectileFiringBehavior firingBehavior = new DefaultProjectileFiringBehavior(
            "projectile.png", 20, -5.0
        );

        // Simulate setting a firing actor
        UserPlane userPlane = new UserPlane(100); // Example user plane with 100 health
        firingBehavior.setFiringActor(userPlane);

        // Fire a projectile
        ActiveActorDestructible projectile = firingBehavior.fireProjectile(userPlane.getLayoutX(), userPlane.getLayoutY());

        // Print details of the created projectile
        System.out.println("Fired Projectile: " + projectile);

        // Check properties of the firing behavior
        System.out.println("Firing Behavior: " + firingBehavior);
    }
}
