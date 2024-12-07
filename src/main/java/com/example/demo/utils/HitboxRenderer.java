package com.example.demo.utils;

import javafx.geometry.Bounds; // Import for defining the boundaries of the hitbox
import javafx.scene.Group;    // Import for adding the hitbox to the game scene
import javafx.scene.paint.Color; // Import for color definitions
import javafx.scene.shape.Rectangle; // Import for rendering the hitbox as a rectangle

/**
 * HitboxRenderer is a utility class for rendering hitboxes in the game.
 * This class separates debugging-related tasks from game logic and promotes reusability.
 */
public class HitboxRenderer {

    /**
     * Renders a hitbox on the game scene for debugging purposes.
     * This method is used to visualize the collision bounds of an object during development.
     *
     * @param bounds The Bounds object defining the area of the hitbox.
     *               This includes the X and Y coordinates, width, and height of the rectangle.
     * @param root The Group object representing the root node of the game scene.
     *             The rendered hitbox will be added as a child to this Group.
     */
    public static void renderHitbox(Bounds bounds, Group root) {
        // Create a rectangle to represent the hitbox with the same dimensions as the given bounds
        Rectangle hitbox = new Rectangle(
            bounds.getMinX(),       // The X position of the top-left corner of the hitbox
            bounds.getMinY(),       // The Y position of the top-left corner of the hitbox
            bounds.getWidth(),      // The width of the hitbox
            bounds.getHeight()      // The height of the hitbox
        );

        // Set the fill color of the hitbox rectangle to be transparent
        hitbox.setFill(Color.TRANSPARENT);

        // Set the stroke color of the hitbox rectangle to red for visibility
        hitbox.setStroke(Color.RED);

        // Set the stroke width of the hitbox rectangle for better visibility in the game scene
        hitbox.setStrokeWidth(2);

        // Add the hitbox rectangle to the game scene's root node for rendering
        root.getChildren().add(hitbox);
    }
}
