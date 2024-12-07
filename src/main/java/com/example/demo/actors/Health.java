package com.example.demo.actors;

/**
 * Health class manages the health of an object.
 * Responsible for tracking health, taking damage, and checking if health is zero or less.
 */
public class Health {

    // Variable to store the current health of the object
    private int health;

    /**
     * Constructor to initialize the Health component with a specific initial health value.
     *
     * @param initialHealth The initial health value to be set for the object.
     *                      Must be a non-negative integer.
     * @throws IllegalArgumentException if initialHealth is negative
     */
    public Health(int initialHealth) {
        if (initialHealth < 0) {
            throw new IllegalArgumentException("Initial health cannot be negative."); // Ensure valid input
        }
        this.health = initialHealth; // Set the initial health value
    }

    /**
     * Reduces the health of the object by one.
     * Ensures that health does not go below zero.
     */
    public void takeDamage() {
        if (health > 0) { // Only reduce health if it is greater than zero
            this.health--; // Decrease health by 1
        }
    }

    /**
     * Checks if the health of the object has reached zero or less.
     *
     * @return True if the current health is zero or less, otherwise false.
     */
    public boolean isZero() {
        return this.health <= 0; // Return true if health is zero or below
    }

    /**
     * Retrieves the current health value of the object.
     *
     * @return The current health value as an integer.
     */
    public int getHealth() {
        return this.health; // Return the current health value
    }

    /**
     * Increases the health of the object by a specified value.
     * Ensures that the health value remains within a valid range.
     *
     * @param healthToAdd The amount of health to add. Must be a positive integer.
     * @throws IllegalArgumentException if healthToAdd is negative
     */
    public void increaseHealth(int healthToAdd) {
        if (healthToAdd < 0) {
            throw new IllegalArgumentException("Health to add cannot be negative."); // Ensure valid input
        }
        this.health += healthToAdd; // Add the specified amount to the current health
    }

    /**
     * Resets the health of the object to a specified value.
     *
     * @param newHealth The new health value to reset to. Must be a non-negative integer.
     * @throws IllegalArgumentException if newHealth is negative
     */
    public void resetHealth(int newHealth) {
        if (newHealth < 0) {
            throw new IllegalArgumentException("Health value cannot be negative."); // Ensure valid input
        }
        this.health = newHealth; // Reset the health to the specified value
    }

    /**
     * Checks if the object is "alive" (health greater than zero).
     *
     * @return True if the current health is greater than zero, otherwise false.
     */
    public boolean isAlive() {
        return this.health > 0; // Return true if health is greater than zero
    }

    /**
     * Overrides the toString method to provide a string representation of the current health state.
     *
     * @return A string representing the current health value.
     */
    @Override
    public String toString() {
        return "Health{" +
                "currentHealth=" + health +
                '}'; // Return a string representation of the health component
    }

    /**
     * Example main method to demonstrate the usage of the Health class.
     * This is optional and can be removed in production.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a Health object with initial health of 10
        Health health = new Health(10);

        // Print the initial health
        System.out.println("Initial health: " + health.getHealth());

        // Take damage and print the updated health
        health.takeDamage();
        System.out.println("After taking damage: " + health.getHealth());

        // Increase health and print the updated value
        health.increaseHealth(5);
        System.out.println("After increasing health: " + health.getHealth());

        // Reset health and print the updated value
        health.resetHealth(20);
        System.out.println("After resetting health: " + health.getHealth());

        // Check if the object is alive
        System.out.println("Is alive: " + health.isAlive());

        // Take damage until health is zero
        for (int i = 0; i < 20; i++) {
            health.takeDamage();
        }
        System.out.println("After multiple damage: " + health.getHealth());
        System.out.println("Is alive: " + health.isAlive());
    }
}
