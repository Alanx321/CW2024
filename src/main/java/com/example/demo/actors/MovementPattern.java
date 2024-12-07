package com.example.demo.actors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the movement pattern for the boss.
 */
public class MovementPattern {

    private final List<Integer> pattern; // The movement pattern
    private final int maxConsecutiveFrames; // Maximum frames for the same move
    private int consecutiveFrames; // Tracks consecutive frames with the same move
    private int currentIndex; // Current index in the movement pattern

    /**
     * Constructor for the MovementPattern class.
     *
     * @param velocity Vertical velocity of the boss.
     * @param cycles Number of movement cycles to generate.
     * @param maxConsecutiveFrames Maximum allowed consecutive frames for a single move.
     */
    public MovementPattern(int velocity, int cycles, int maxConsecutiveFrames) {
        this.pattern = new ArrayList<>();
        this.maxConsecutiveFrames = maxConsecutiveFrames;
        this.consecutiveFrames = 0;
        this.currentIndex = 0;
        initializePattern(velocity, cycles);
    }

    /**
     * Initializes the movement pattern by alternating up, down, and stationary movements.
     *
     * @param velocity Vertical velocity of the boss.
     * @param cycles Number of movement cycles.
     */
    private void initializePattern(int velocity, int cycles) {
        for (int i = 0; i < cycles; i++) {
            pattern.add(velocity); // Move up
            pattern.add(-velocity); // Move down
            pattern.add(0); // Stationary (optional, reduce frequency if needed)
        }
        Collections.shuffle(pattern); // Shuffle the pattern for randomness
    }

    /**
     * Retrieves the next movement value in the pattern.
     *
     * @return The next movement value.
     */
    public int getNextMove() {
        int move = pattern.get(currentIndex); // Get the current move
        consecutiveFrames++;

        // If the move has been applied for too many consecutive frames, switch to the next one
        if (consecutiveFrames >= maxConsecutiveFrames) {
            consecutiveFrames = 0; // Reset the consecutive frame counter
            currentIndex = (currentIndex + 1) % pattern.size(); // Move to the next index, loop back if needed
        }

        return move; // Return the movement value
    }
}
