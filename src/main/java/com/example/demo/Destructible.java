package com.example.demo;

/**
 * The Destructible interface represents objects that can take damage 
 * and be destroyed. It provides two core methods:
 * - `takeDamage()`: To apply damage to the object.
 * - `destroy()`: To permanently destroy or disable the object.
 * 
 * Implement this interface for any class that requires damage mechanics 
 * and destruction behavior, such as game objects, structures, or entities.
 */
public interface Destructible {

    /**
     * Method to apply damage to the object. 
     * This can be implemented to reduce health, integrity, or another 
     * metric representing the object's state.
     */
    void takeDamage();

    /**
     * Method to destroy the object. 
     * Implementations should define how the object is permanently 
     * removed or disabled, such as removing it from the game world.
     */
    void destroy();
    
} 
