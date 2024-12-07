package com.example.demo.actors;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract class representing an active actor in the game.
 * Focused on graphical representation and basic movement functionality.
 */
public abstract class ActiveActor extends ImageView {

    // Constant for the directory where actor images are stored
    private static final String IMAGE_LOCATION = "/com/example/demo/images/";

    // Constant for hitbox margin, reducing the size for collision detection
    private static final int HITBOX_MARGIN = 40;

    /**
     * Constructor for ActiveActor. 
     * Initializes the actor's image, size, and starting position.
     *
     * @param imageName    Name of the image file for the actor
     * @param imageHeight  Height to scale the image to
     * @param initialXPos  Initial X position of the actor on the screen
     * @param initialYPos  Initial Y position of the actor on the screen
     */
    public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        // Set the actor's image using the utility method for loading images
        this.setImage(loadImage(imageName));

        // Set the actor's initial position on the screen
        this.setLayoutX(initialXPos);
        this.setLayoutY(initialYPos);

        // Scale the image while maintaining the aspect ratio
        this.setFitHeight(imageHeight);
        this.setPreserveRatio(true);
    }

    /**
     * Abstract method for updating the actor's position.
     * Subclasses must define their specific movement logic.
     */
    public abstract void updatePosition();

    /**
     * Moves the actor horizontally by the given amount.
     *
     * @param horizontalMove The distance to move horizontally
     */
    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(getTranslateX() + horizontalMove);
    }

    /**
     * Moves the actor vertically by the given amount.
     *
     * @param verticalMove The distance to move vertically
     */
    protected void moveVertically(double verticalMove) {
        this.setTranslateY(getTranslateY() + verticalMove);
    }

    /**
     * Calculates a reduced bounding box for collision detection.
     *
     * @return A {@link Bounds} object representing the smaller hitbox
     */
    public Bounds getReducedBounds() {
        // Retrieve the original bounds of the actor's image
        Bounds originalBounds = this.getBoundsInLocal();

        // Create and return a smaller bounding box for better collision detection
        return new BoundingBox(
            originalBounds.getMinX() + HITBOX_MARGIN,  // Left margin adjustment
            originalBounds.getMinY() + HITBOX_MARGIN,  // Top margin adjustment
            originalBounds.getWidth() - 2 * HITBOX_MARGIN, // Reduce width
            originalBounds.getHeight() - 2 * HITBOX_MARGIN // Reduce height
        );
    }

    /**
     * Loads an image for the actor from the specified location.
     *
     * @param imageName The name of the image file to load
     * @return A {@link Image} object representing the actor's image
     */
    private Image loadImage(String imageName) {
        // Create and return the image using the specified path
        return new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm());
    }
}
