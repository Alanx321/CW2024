package com.example.demo.actors; // Declare package for actors

import com.example.demo.Destructible; // Import Destructible interface

/**
 * Abstract class representing an active actor that can be destroyed.
 * Extends the ActiveActor class and implements the Destructible interface.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

    // Flag indicating whether the actor has been destroyed
    private boolean isDestroyed;

    /**
     * Constructor for ActiveActorDestructible.
     * Initializes the actor's image, position, and destruction state.
     *
     * @param imageName    Name of the image file representing the actor
     * @param imageHeight  Height of the image (will scale proportionally)
     * @param initialXPos  Initial X position of the actor on the screen
     * @param initialYPos  Initial Y position of the actor on the screen
     */
    public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        super(imageName, imageHeight, initialXPos, initialYPos); // Initialize superclass properties
        isDestroyed = false; // Initialize the destroyed flag as false
    }

    /**
     * Abstract method to be implemented by subclasses to update the position of the actor.
     * Defines specific movement logic for each destructible actor type.
     */
    @Override
    public abstract void updatePosition(); // Define abstract method for position updates

    /**
     * Abstract method to update the state or behavior of the actor.
     * Intended to handle any additional updates beyond position changes.
     */
    public abstract void updateActor(); // Define abstract method for general updates

    /**
     * Abstract method to handle damage logic for the actor.
     * Subclasses must define how the actor takes damage.
     */
    @Override
    public abstract void takeDamage(); // Define abstract method for damage handling

    /**
     * Marks the actor as destroyed and updates its state.
     */
    @Override
    public void destroy() {
        setDestroyed(true); // Set the destroyed flag to true
    }

    /**
     * Sets the destruction state of the actor.
     * 
     * @param isDestroyed Boolean flag indicating whether the actor is destroyed
     */
    protected void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed; // Update the destroyed flag with the provided value
    }

    /**
     * Checks if the actor is destroyed.
     * 
     * @return true if the actor is destroyed, false otherwise
     */
    public boolean isDestroyed() {
        return isDestroyed; // Return the current destruction state
    }
}
