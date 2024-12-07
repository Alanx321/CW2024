package com.example.demo.projectiles;

import com.example.demo.actors.ActiveActorDestructible;

/**
 * Interface for defining projectile firing behavior.
 * Provides a method to implement custom firing mechanisms for different objects.
 */
public interface ProjectileFiringBehavior {

    /**
     * Fires a projectile based on the object's position.
     *
     * @param xPosition X coordinate for the projectile's starting position.
     * @param yPosition Y coordinate for the projectile's starting position.
     * @return An ActiveActorDestructible projectile if fired, otherwise null.
     */
    ActiveActorDestructible fireProjectile(double xPosition, double yPosition);
}
