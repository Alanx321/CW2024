package com.example.demo.listeners;

// Importing necessary classes
import com.example.demo.levels.LevelManager;
import com.example.demo.utils.ErrorHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * EventListener class listens for property changes and triggers appropriate actions, 
 * such as transitioning to the next level in the game.
 */
public class EventListener implements PropertyChangeListener {

    // LevelManager instance to handle level transitions
    private final LevelManager levelManager;

    /**
     * Constructor for EventListener.
     *
     * @param levelManager The LevelManager instance used for transitioning levels.
     */
    public EventListener(LevelManager levelManager) {
        this.levelManager = levelManager; // Assigning LevelManager to handle transitions
    }

    /**
     * Responds to property change events.
     *
     * @param evt The property change event containing details of the change.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Check if the event corresponds to a level transition
        if ("levelTransition".equals(evt.getPropertyName())) {
            try {
                // Extract the new level's class name from the event's new value
                String nextLevelClassName = (String) evt.getNewValue();

                // Trigger the transition to the next level using LevelManager
                levelManager.goToLevel(nextLevelClassName);

            } catch (Exception e) {
                // Handle errors during level transitions
                ErrorHandler.showError(
                    "Level Transition Error", 
                    "An error occurred during the level transition", 
                    e
                );
            }
        }
    }
}
