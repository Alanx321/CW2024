package com.example.demo.levels;

import com.example.demo.actors.UserPlane;

/**
 * GameProgressManager is responsible for managing the player's progress in the game.
 * It tracks win/loss conditions and handles level transitions or game termination.
 */
public class GameProgressManager {

    // The number of kills required for the player to advance to the next level
    private final int killsToAdvance;

    // The fully qualified class name of the next level to transition to
    private final String nextLevel;

    /**
     * Constructor for GameProgressManager.
     * Initializes the manager with the required kills and the next level to transition to.
     *
     * @param killsToAdvance The number of kills the player must achieve to advance.
     * @param nextLevel      The fully qualified class name of the next level.
     */
    public GameProgressManager(int killsToAdvance, String nextLevel) {
        this.killsToAdvance = killsToAdvance; // Set the kill count required to advance
        this.nextLevel = nextLevel; // Set the name of the next level
    }

    /**
     * Checks the player's progress and handles the game's win/loss conditions.
     * If the player meets the win condition (kill target), the level transitions to the next one.
     * If the player meets the loss condition (destroyed), the game ends in a loss.
     *
     * @param userPlane The player's UserPlane instance representing their in-game entity.
     * @param level     The current level instance, used to manage game state transitions or termination.
     */
    public void checkGameProgress(UserPlane userPlane, LevelParent level) {
        // Check if the player's plane is destroyed
        if (userPlane.getHealth() <= 0) {
            level.loseGame(); // End the game with a loss
            System.out.println("Game over! Player's plane was destroyed.");
        } 
        // Check if the player's kill count meets or exceeds the target to advance
        else if (userPlane.getNumberOfKills() >= killsToAdvance) {
            level.goToNextLevel(nextLevel); // Transition to the next level
            System.out.println("Congratulations! Advancing to the next level: " + nextLevel);
        } 
        // Debugging output: Log the player's current progress
        else {
            System.out.println("Player progress: " + userPlane.getNumberOfKills() + "/" + killsToAdvance + " kills.");
        }
    }

    /**
     * Retrieves the number of kills required to advance to the next level.
     *
     * @return The number of kills required to transition to the next level.
     */
    public int getKillsToAdvance() {
        return killsToAdvance;
    }

    /**
     * Retrieves the name of the next level to transition to.
     *
     * @return The fully qualified class name of the next level.
     */
    public String getNextLevel() {
        return nextLevel;
    }

    /**
     * Provides a string representation of the GameProgressManager.
     * Useful for debugging or logging purposes.
     *
     * @return A string containing the killsToAdvance and nextLevel values.
     */
    @Override
    public String toString() {
        return "GameProgressManager{" +
               "killsToAdvance=" + killsToAdvance +
               ", nextLevel='" + nextLevel + '\'' +
               '}';
    }
}
