package com.example.demo.utils; // Define the package for the utility class

/**
 * KillCountTracker is a utility class for tracking the number of kills
 * made by a specific actor (e.g., UserPlane) in the game.
 * It provides methods for incrementing, retrieving, and resetting the kill count.
 */
public class KillCountTracker {

    // Field to store the total number of kills
    private int killCount;

    /**
     * Constructor for KillCountTracker.
     * Initializes the kill count to zero.
     */
    public KillCountTracker() {
        this.killCount = 0; // Set the initial kill count to zero
    }

    /**
     * Gets the current kill count.
     *
     * @return The number of kills tracked by this instance.
     */
    public int getKillCount() {
        return killCount; // Return the current value of the kill count
    }

    /**
     * Increments the kill count by one.
     * This method is called whenever a kill is successfully registered.
     */
    public void incrementKillCount() {
        killCount++; // Increment the kill count by one
    }

    /**
     * Resets the kill count to zero.
     * This method can be called to clear the kill count, for example,
     * when starting a new game or level.
     */
    public void resetKillCount() {
        this.killCount = 0; // Reset the kill count to zero
    }

    /**
     * Checks if the kill count has reached a specific milestone.
     * This can be used to trigger rewards or achievements in the game.
     *
     * @param milestone The milestone number to check against.
     * @return True if the kill count equals or exceeds the milestone, otherwise false.
     */
    public boolean hasReachedMilestone(int milestone) {
        return killCount >= milestone; // Return true if the kill count meets or exceeds the milestone
    }

    /**
     * Provides a string representation of the KillCountTracker.
     * This can be useful for debugging or displaying the kill count in logs.
     *
     * @return A string representing the current state of the KillCountTracker.
     */
    @Override
    public String toString() {
        return "KillCountTracker{" +
               "killCount=" + killCount +
               '}'; // Include the current kill count in the string representation
    }

    /**
     * Example usage of KillCountTracker.
     * This main method is for testing purposes only and can be removed in production.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create an instance of KillCountTracker
        KillCountTracker tracker = new KillCountTracker();

        // Test incrementing the kill count
        tracker.incrementKillCount();
        tracker.incrementKillCount();
        System.out.println("Kill Count after 2 increments: " + tracker.getKillCount()); // Should print 2

        // Test resetting the kill count
        tracker.resetKillCount();
        System.out.println("Kill Count after reset: " + tracker.getKillCount()); // Should print 0

        // Test milestone check
        tracker.incrementKillCount();
        tracker.incrementKillCount();
        tracker.incrementKillCount();
        System.out.println("Has reached milestone 3: " + tracker.hasReachedMilestone(3)); // Should print true
        System.out.println("Has reached milestone 5: " + tracker.hasReachedMilestone(5)); // Should print false

        // Display the string representation of the tracker
        System.out.println(tracker.toString()); // Should print KillCountTracker{killCount=3}
    }
}
