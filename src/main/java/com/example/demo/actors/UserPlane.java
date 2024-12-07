package com.example.demo.actors;

import com.example.demo.projectiles.ProjectileFiringBehavior;
import com.example.demo.projectiles.DefaultProjectileFiringBehavior;
import com.example.demo.utils.KillCountTracker;
import com.example.demo.utils.HitboxRenderer;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;

/**
 * UserPlane represents the player's aircraft in the game.
 * It handles movement, firing projectiles, and tracks the number of kills.
 */
public class UserPlane extends FighterPlane {

    // Constants for the user plane's configuration
    private static final String IMAGE_NAME = "userplane.png"; // Image file name for the user plane
    private static final double Y_UPPER_BOUND = -40;         // Movement upper boundary
    private static final double Y_LOWER_BOUND = 600.0;       // Movement lower boundary
    private static final double INITIAL_X_POSITION = 5.0;    // Initial X position
    private static final double INITIAL_Y_POSITION = 300.0;  // Initial Y position
    private static final int IMAGE_HEIGHT = 150;             // Height of the plane's image
    private static final int VERTICAL_VELOCITY = 8;         // Speed of vertical movement
    private static final boolean DEBUG_HITBOXES = true;      // Enable/disable hitbox visualization

    // Instance variables for plane behavior and tracking
    private int velocityMultiplier;                          // -1 for up, 1 for down, 0 for stationary
    private final KillCountTracker killCountTracker;         // Tracks the number of kills
    private final ProjectileFiringBehavior projectileFiringBehavior; // Handles projectile creation and firing

    /**
     * Constructor for UserPlane.
     * Initializes the user plane with its position, health, and dependencies.
     *
     * @param initialHealth The starting health of the user plane.
     */
    public UserPlane(int initialHealth) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
        this.velocityMultiplier = 0; // Initialize as stationary
        this.killCountTracker = new KillCountTracker();

        // Initialize the projectile firing behavior for this plane
        this.projectileFiringBehavior = new DefaultProjectileFiringBehavior(
            "projectile.png", 20, 15
        );

        ((DefaultProjectileFiringBehavior) this.projectileFiringBehavior).setFiringActor(this);
    }

    /**
     * Updates the position of the user plane based on its current velocity multiplier.
     * Ensures the plane stays within the defined movement boundaries.
     */
    @Override
    public void updatePosition() {
        if (isMoving()) { // Check if the plane is moving
            double newTranslateY = getTranslateY() + VERTICAL_VELOCITY * velocityMultiplier;

            // Apply boundary checks
            if (newTranslateY + getLayoutY() < Y_UPPER_BOUND) {
                newTranslateY = Y_UPPER_BOUND - getLayoutY();
            } else if (newTranslateY + getLayoutY() > Y_LOWER_BOUND) {
                newTranslateY = Y_LOWER_BOUND - getLayoutY();
            }

            setTranslateY(newTranslateY); // Update translateY
            System.out.println("UserPlane - Updated translateY: " + newTranslateY); // Debug log
        }
    }

    /**
     * Updates the state of the user plane. Called in each game loop iteration.
     */
    @Override
    public void updateActor() {
        updatePosition();

        // Explicitly ensure the layout is updated for JavaFX rendering
        setTranslateY(getTranslateY());

        // Trigger a visual refresh
        this.setManaged(false); // Ensure it's not relying on container layout
        this.setVisible(false); // Temporarily hide to force refresh
        this.setVisible(true);  // Show it again

        // Debugging output
        System.out.println("UserPlane Position: X=" + (getLayoutX() + getTranslateX()) +
                           ", Y=" + (getLayoutY() + getTranslateY()));
    }



    /**
     * Fires a projectile from the user plane's current position.
     *
     * @return A new ActiveActorDestructible projectile instance.
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        double currentX = getLayoutX() + getTranslateX() + 110; // Adjust offset for alignment
        double currentY = getLayoutY() + getTranslateY();       // Sync with updated Y position
        return projectileFiringBehavior.fireProjectile(currentX, currentY);
    }

    /**
     * Checks if the user plane is currently moving.
     *
     * @return True if the plane is moving, otherwise false.
     */
    private boolean isMoving() {
        return velocityMultiplier != 0; // Movement occurs if velocity multiplier is not zero
    }

    /**
     * Initiates upward movement for the user plane.
     */
    public void moveUp() {
        velocityMultiplier = -1; // Set velocity multiplier for upward movement
        System.out.println("UserPlane - Move Up initiated.");
    }

    /**
     * Initiates downward movement for the user plane.
     */
    public void moveDown() {
        velocityMultiplier = 1; // Set velocity multiplier for downward movement
        System.out.println("UserPlane - Move Down initiated.");
    }

    /**
     * Stops the user plane's movement.
     */
    public void stop() {
        velocityMultiplier = 0; // Set velocity multiplier to zero to stop movement
        System.out.println("UserPlane - Movement stopped.");
    }

    /**
     * Gets the number of kills made by the user plane.
     *
     * @return The current kill count.
     */
    public int getNumberOfKills() {
        return killCountTracker.getKillCount(); // Delegate to KillCountTracker
    }

    /**
     * Increments the kill count by 1.
     */
    public void incrementKillCount() {
        killCountTracker.incrementKillCount(); // Delegate to KillCountTracker
    }

    /**
     * Resets the kill count to zero.
     */
    public void resetKillCount() {
        killCountTracker.resetKillCount(); // Delegate to KillCountTracker
    }

    /**
     * Retrieves the reduced bounds for collision detection.
     * This reduces the effective hitbox of the user plane for better gameplay experience.
     *
     * @return A Bounds object representing the reduced hitbox.
     */
    @Override
    public Bounds getReducedBounds() {
        Bounds originalBounds = this.getBoundsInLocal();
        double margin = 20;
        return new BoundingBox(
            originalBounds.getMinX() + margin,
            originalBounds.getMinY() + margin,
            originalBounds.getWidth() - 2 * margin,
            originalBounds.getHeight() - 2 * margin
        );
    }

    /**
     * Renders the hitbox for debugging purposes.
     * Delegates the task to the HitboxRenderer utility.
     *
     * @param root The Group object representing the game scene's root node.
     */
    public void renderHitbox(Group root) {
        if (DEBUG_HITBOXES) {
            HitboxRenderer.renderHitbox(getReducedBounds(), root);
            System.out.println("Rendering hitbox at: " + getReducedBounds());
        }
    }

    /**
     * Provides a string representation of the UserPlane.
     * Useful for debugging or logging purposes.
     *
     * @return A string representing the UserPlane's state.
     */
    @Override
    public String toString() {
        return "UserPlane{" +
               "velocityMultiplier=" + velocityMultiplier +
               ", killCount=" + killCountTracker.getKillCount() +
               ", positionX=" + getLayoutX() +
               ", positionY=" + getLayoutY() +
               '}';
    }
}
