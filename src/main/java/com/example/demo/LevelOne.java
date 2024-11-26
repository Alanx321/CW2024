package com.example.demo;

import javafx.stage.Stage;

public class LevelOne extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/space.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.LevelBoss";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 10;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    public LevelOne(double screenHeight, double screenWidth, Stage stage) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage);
    }

    @Override
    protected void checkIfGameOver() {
        // Check if the player is destroyed
        if (userIsDestroyed()) {
            loseGame();
        }
        // Check if the player has reached the kill target to transition to the next level
        else if (userHasReachedKillTarget()) {
            goToNextLevel(NEXT_LEVEL);
        }
        System.out.println("Kill Target Reached: " + getUser().getNumberOfKills());
    }

    @Override
    protected void initializeFriendlyUnits() {
        // Add the user plane to the game root
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        // Create a LevelView specific to this level
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    private boolean userHasReachedKillTarget() {
        // Check if the user's kill count has reached the target to advance to the next level
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}
