package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.EnemyPlane;

/**
 * EnemySpawner is responsible for handling the logic of spawning enemy units in a game level.
 */
public class EnemySpawner {

    // Maximum number of enemies allowed at one time in the level
    private final int totalEnemies;

    // Probability of spawning an enemy unit on each game frame
    private final double spawnProbability;

    /**
     * Constructor for EnemySpawner.
     * Initializes the spawner with the total number of enemies allowed and the probability of spawning enemies.
     *
     * @param totalEnemies     The maximum number of enemies allowed in the level at one time.
     * @param spawnProbability The probability of spawning an enemy unit on each game frame (0.0 to 1.0).
     */
    public EnemySpawner(int totalEnemies, double spawnProbability) {
        this.totalEnemies = totalEnemies; // Set the maximum number of enemies
        this.spawnProbability = spawnProbability; // Set the probability of spawning
    }

    /**
     * Spawns enemies in the level based on the probability and the current number of enemies in the game.
     *
     * @param level                The current level instance, used to manage the game state and add enemy units.
     * @param screenWidth          The width of the game screen, used to position enemies horizontally.
     * @param enemyMaximumYPosition The maximum Y position allowed for enemies (vertical positioning).
     */
    public void spawnEnemies(LevelParent level, double screenWidth, double enemyMaximumYPosition) {
        // Get the current number of enemies in the level
        int currentNumberOfEnemies = level.getCurrentNumberOfEnemies();

        // Calculate the number of enemies that can still be spawned
        int enemiesToSpawn = totalEnemies - currentNumberOfEnemies;

        // Loop through the number of spawn opportunities left
        for (int i = 0; i < enemiesToSpawn; i++) {
            // Check if this spawn attempt should succeed based on the spawn probability
            if (Math.random() < spawnProbability) {
                // Generate a random vertical (Y) position for the new enemy
                double newEnemyInitialYPosition = Math.random() * enemyMaximumYPosition;

                // Create a new enemy unit with a starting position
                ActiveActorDestructible newEnemy = new EnemyPlane(screenWidth, newEnemyInitialYPosition);

                // Add the new enemy unit to the level
                level.addEnemyUnit(newEnemy);
            }
        }
    }

    /**
     * Retrieves the maximum number of enemies allowed in the level.
     *
     * @return The total number of enemies that can be present in the level at one time.
     */
    public int getTotalEnemies() {
        return totalEnemies;
    }

    /**
     * Retrieves the spawn probability for enemies.
     *
     * @return The probability (0.0 to 1.0) of spawning an enemy on a given game frame.
     */
    public double getSpawnProbability() {
        return spawnProbability;
    }

    /**
     * Provides a string representation of the EnemySpawner instance, useful for debugging.
     *
     * @return A string containing the totalEnemies and spawnProbability values.
     */
    @Override
    public String toString() {
        return "EnemySpawner{" +
               "totalEnemies=" + totalEnemies +
               ", spawnProbability=" + spawnProbability +
               '}';
    }
}
