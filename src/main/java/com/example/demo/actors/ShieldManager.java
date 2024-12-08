package com.example.demo.actors;

import com.example.demo.levels.LevelBoss;

/**
 * Manages the shield state for the boss, including activation and deactivation logic.
 */
public class ShieldManager {

    private static final int MAX_FRAMES_WITH_SHIELD = 300; // Maximum duration of the shield
    private static final double SHIELD_ACTIVATION_PROBABILITY = 0.01; // Probability of activation per frame
    private boolean isActive; // Indicates if the shield is currently active
    private int activeFrames; // Tracks the duration of shield activation
    private final LevelBoss levelBoss; // Reference to the level for shield updates

    /**
     * Constructor for the ShieldManager class.
     *
     * @param levelBoss The level associated with the boss.
     */
    public ShieldManager(LevelBoss levelBoss) {
        this.levelBoss = levelBoss;
        this.isActive = false;
        this.activeFrames = 0;
    }

    /**
     * Updates the shield's state, including activation and deactivation.
     */
    public void updateShield() {
        if (isActive) {
            activeFrames++; // Increment the duration the shield has been active
            if (activeFrames >= MAX_FRAMES_WITH_SHIELD) {
                deactivateShield(); // Deactivate shield if duration exceeded
            }
        } else if (shouldActivateShield()) {
            activateShield(); // Activate shield based on probability
        }
    }

    /**
     * Checks whether the shield should activate.
     *
     * @return True if the shield should activate, otherwise false.
     */
    private boolean shouldActivateShield() {
        return Math.random() < SHIELD_ACTIVATION_PROBABILITY;
    }

    /**
     * Activates the shield.
     */
    private void activateShield() {
        isActive = true;
        activeFrames = 0; // Reset active duration
        levelBoss.updateShieldState(); // Notify level to update visuals
    }

    /**
     * Deactivates the shield.
     */
    private void deactivateShield() {
        isActive = false;
        levelBoss.updateShieldState(); // Notify level to hide visuals
    }

    /**
     * Gets the shield's active state.
     *
     * @return True if the shield is active, otherwise false.
     */
    public boolean isShieldActive() {
        return isActive;
    }
}
