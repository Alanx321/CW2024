# ReadMe.md For "1942 Retro Game - Maintenance and Enhancements"

## Brief Description Of Project 
This project is a reimagined version of the classic arcade game 1942, built using JavaFX for the graphical interface and written in Java. 
The game features enhanced gameplay mechanics, including multiple levels, a challenging boss battle, dynamic enemy spawning, and a scoring system. 
Players navigate their plane to defeat enemies, avoid projectiles, and progress through increasingly difficult levels.
As part of this project, existing code was maintained and extended with new functionalities such as improved hitbox accuracy, shield mechanics, pause and main menus, and enhanced user interface elements. 
The project also incorporates modern software development principles, including modular design, test-driven development, and error handling for a smoother gameplay experience.

## Table Of Contents
- [GitHub Repository](#github-repository)
- [Compilation Instructions](#compilation-instructions)
- [Implemented and Working Features](#implemented-and-working-features)
- [Implemented but Not Fully Functional Features](#implemented-but-not-fully-functional-features)
- [Features Not Implemented](#features-not-implemented)
- [New Java Classes](#new-java-classes)
- [Modified Java Classes](#modified-java-classes)
- [Unexpected Challenges](#unexpected-challenges)
- [Additional Files](#additional-files)

## GitHub Repository
https://github.com/Alanx321/CW2024.git

## Compilation Instructions
### Dependencies:

### Core Dependencies
#### JavaFX:
- javafx-controls: For UI components like buttons and sliders.
- javafx-fxml: For FXML-based UI layouts.
- javafx-base: Base functionality for JavaFX (used in tests).
- Version: 19.0.2

#### Testing Dependencies
JUnit 5:
- junit-jupiter-api: For writing unit tests.
- junit-jupiter-engine: Required to run the JUnit 5 tests.
- Version: 5.9.2

Mockito:
- mockito-core: For creating mock objects in unit tests.
- mockito-junit-jupiter: Integrates Mockito with JUnit 5.
- Version: 5.5.0

Byte Buddy:
- Enables dynamic proxy creation for Mockito.
- Version: 1.14.7

TestFX:
- testfx-core: For testing JavaFX UI components.
- testfx-junit5: Integrates TestFX with JUnit 5.
- Version: 4.0.16-alpha

Build Tools
Maven Compiler Plugin:
- Compiles the project using Java 21.
- Version: 3.11.0

JavaFX Maven Plugin:
- For running and packaging JavaFX applications.
- Version: 0.0.8

Environment Requirements
- JDK Version: Java SE 21 (ensure JAVA_HOME points to JDK 21).
- Maven Version: 3.8+.
- JavaFX SDK: Ensure the JavaFX SDK is downloaded and properly configured.

Steps to Compile and Run in Eclipse
1. Clone the Repository:
   - Clone the project repository to your local machine:
   - git clone https://github.com/Alanx321/CW2024.git
   - cd CW2024

2. Import the Project into Eclipse:
   - Open Eclipse and navigate to File > Import.
	 - Select Maven > Existing Maven Projects and click Next.
	 - Browse to the folder where you cloned the repository and select it.
	 - Click Finish to import the project.

3. Configure JavaFX in Eclipse:
   - Right-click on the project and select Properties.
	 - Go to Java Build Path > Libraries and add the JavaFX SDK library:
	 - Click Add External JARs.
	 - Select the lib folder from your JavaFX SDK installation directory.
   - Go to Run Configurations:
   - Under the Arguments tab, add the following to VM arguments:

     --module-path <path-to-javafx-sdk>/lib 
	   --add-modules javafx.controls,javafx.media
	   -Xms1024m -Xmx4096m
	   --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED
	   --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED
     
	  - Replace <path-to-javafx-sdk> with the path to your JavaFX SDK 
        	installation.

4. Build the Project:
	 - Eclipse will automatically build the project when you import it.
	 - If not, go to Project > Build Automatically.

5. Run the Application:
   - Right-click on the Main class (located at src/main/java/com/example/demo/Main.java).
	 - Select Run As > Java Application.
	 - The application should launch successfully.
  
## Implemented and Working Features
Core Features
	
  1. Basic Gameplay
		- Players control a plane using the arrow keys to navigate and the spacebar to fire projectiles.
		- Objective: Shoot down enemy planes and progress through levels.

  2. Level Progression
		- Added multiple levels with unique gameplay mechanics.
 		- Levels increase in difficulty, with more enemies and faster attack rates.

  3. Score System
		- A scoring system that tracks the player’s performance.
		- Players earn points for every enemy plane destroyed.

  4. Boss Level
		- A challenging boss battle with unique mechanics (e.g., special projectiles and health display).
		- Successfully added shield mechanics and a dedicated health bar for the boss.

UI Features
  
  5. Main Menu
		- A starting menu with options to play the game, view instructions, or quit.

  6. Pause Menu
		- Added a functional pause menu, allowing players to pause gameplay and choose to resume or quit.

  7. How to Play Instructions
		- A dedicated "How to Play" screen providing users with gameplay instructions.

Enhancements
	
  8. Hitbox Adjustments
		- Refactored hitboxes for all planes to improve collision accuracy.

  9. Projectile Behavior
		- Enhanced projectile positioning and adjusted fire rates for better gameplay balance.

  10. Audio Features
		- Added music to the main menu and gameplay levels.
		- Dynamic background music for each level enhances the player’s experience.

  11. Level Transition Fixes
		- Fixed issues with transitions between levels to ensure seamless gameplay.

Refactoring for Improved Functionality
	
  12. Observer Pattern Replacement
		- Replaced deprecated Observer/Observable with PropertyChangeListener for better maintainability.

  13. Code Refactoring
		- Reorganized project structure to adhere to modularity and maintainability principles.
		- Applied the Single Responsibility Principle (SRP) to key classes, reducing complexity and improving code readability.

## Implemented but Not Fully Functional Features
1. Restart Functionality
Issue:
- After restarting the game, some visual elements (e.g., score, player health) are not reset correctly.
Cause:
- State variables for UI elements are not being reinitialized when the game restarts.
Attempted Fix:
- Manually reset critical game variables in the restart function.
Planned Solution:
- Implement a centralized reset function that ensures all game state and UI components are refreshed upon restart.

2. Boss Hitbox is Too Large
Issue:
- The boss's hitbox is significantly larger than its visual representation, causing projectiles to hit the boss unfairly.
Cause:
- The hitbox dimensions were incorrectly calculated during the boss's initialization.
Attempted Fix:
- Adjusted the width and height of the hitbox to better align with the boss sprite. Added debug visuals to confirm alignment.
Planned Solution:
- Use a precise bounding box calculation method or implement a polygon-based hitbox for better accuracy.

3. Overlapping Background Music During Level Transitions
Issue:
- When transitioning to a new level, the background music from the previous level sometimes continues to play, overlapping with the new track.
Cause:
- The previous music track is not being stopped before the new one starts.
Attempted Fix:
- Added a call to stop the current track in the level transition logic.
Planned Solution:
- Implement a global audio manager to ensure all music tracks are stopped or switched cleanly during level transitions.

4. Projectiles Hitting Multiple Enemies
Issue:
- A single projectile can sometimes damage multiple enemy planes in its path, which was not intended behavior.
Cause:
- The collision detection logic does not properly "consume" the projectile after hitting its first target.
Attempted Fix:
- Added a flag to destroy the projectile upon the first collision. However, synchronization issues caused occasional errors during gameplay.
Planned Solution:
- Refactor the collision detection logic to ensure the projectile is removed from the game world immediately upon impact, and thoroughly test for edge cases.

## Features Not Implemented
1. High Score Saving
Planned Feature:
- A system to save and display high scores across gameplay sessions.
Reason Not Implemented:
- The implementation required creating a persistent storage mechanism (e.g., file I/O or a database). This was deprioritized in favor of core gameplay features like level progression and boss mechanics.

2. Multiplayer Mode
Planned Feature:
- A local multiplayer mode where two players could control separate planes and collaborate.
Reason Not Implemented:
- The complexity of implementing synchronized player controls and UI updates within the given timeline exceeded the project scope.

3. Pause Menu Enhancements
Planned Feature:
- Adding options in the pause menu to adjust sound settings and view game stats.
Reason Not Implemented:
- Focus was placed on ensuring basic pause/resume functionality worked correctly before expanding its scope.

4. Advanced AI for Enemies
Planned Feature:
- Enemies with more dynamic movement and attack patterns (e.g., evading projectiles, coordinated attacks).
Reason Not Implemented:
- The current AI logic was sufficient for meeting the gameplay requirements, and advanced behaviors were deprioritized to focus on bug fixes and level design.

5. Power-Ups
Planned Feature:
- Adding collectible power-ups to enhance the player's abilities, such as increased fire rate or temporary invincibility.
Reason Not Implemented:
- Designing, integrating, and balancing power-ups would require extensive testing, which could not be completed within the project timeline.

6. Dynamic Difficulty Adjustment
Planned Feature:
- A system that adjusts game difficulty in real-time based on the player’s performance.
Reason Not Implemented:
- Designing and testing the algorithm for dynamic difficulty adjustment proved too complex for the given time constraints

## New Java Classes
1. LevelManager.java
Location: src/main/java/com/example/demo/levels/LevelManager.java
Purpose:
- Manages transitions between levels, tracks level completion, and coordinates loading of level-specific assets

2. GameProgressManager.java
Location: src/main/java/com/example/demo/levels/GameProgressManager.java
Purpose:
- Maintains the overall progress of the game, such as current score, level number, and player performance metrics.

3. EnemySpawner.java
Location: src/main/java/com/example/demo/levels/EnemySpawner.java
Purpose:
- Handles spawning of enemies dynamically during gameplay, scaling the difficulty by spawning tougher enemies in later levels.

4. DefaultProjectileFiringBehavior.java
Location: src/main/java/com/example/demo/projectiles/DefaultProjectileFiringBehavior.java
Purpose:
- Provides a generic firing behavior for entities that fire projectiles, enabling reusable and modular projectile behavior.

5. StraightProjectile.java
Location: src/main/java/com/example/demo/projectiles/StraightProjectile.java
Purpose:
- Represents a simple projectile type that moves in a straight line, used by both the player and enemies.

6. KillCountTracker.java
Location: src/main/java/com/example/demo/utils/KillCountTracker.java
Purpose:
- Tracks the number of enemies destroyed by the player, contributing to score calculation and level progression.

7. HitboxRenderer.java
Location: src/main/java/com/example/demo/utils/HitboxRenderer.java
Purpose:
- Renders hitboxes on screen for debugging, allowing developers to visually verify collision detection accuracy.

8. ShieldManager.java
Location: src/main/java/com/example/demo/actors/ShieldManager.java
Purpose:
- Manages the shield mechanics for entities like the boss, including activation, deactivation, and damage absorption.

9. PauseMenu.java
Location: src/main/java/com/example/demo/ui/PauseMenu.java
Purpose:
- Adds a pause menu functionality, allowing players to pause the game and choose options like resume or quit.

10. HowToPlayWindow.java
Location: src/main/java/com/example/demo/controller/HowToPlayWindow.java
Purpose:
- Displays instructions on how to play the game, providing a better onboarding experience for players.

11. ErrorHandler.java
Location: src/main/java/com/example/demo/utils/ErrorHandler.java
Purpose:
- A utility class for handling errors gracefully during runtime, ensuring smooth gameplay even when issues occur.

l2. ProjectileFiringBehavior.java
Location: src/main/java/com/example/demo/projectiles/ProjectileFiringBehavior.java
Purpose:
- Defines an interface for firing behaviors, enabling flexible and interchangeable firing mechanisms for entities.

13. MainMenu.java
Location: src/main/java/com/example/demo/ui/MainMenu.java
Purpose:
- Implements the main menu interface, allowing players to start the game, view instructions, or exit. It serves as the entry point for the user interface.

14. EventListener.java
Location: src/main/java/com/example/demo/listeners/EventListener.java
Purpose:
- Provides a system to handle custom events within the game, enabling better modularity for user input and gameplay interactions.

15. Health.java
Location: src/main/java/com/example/demo/actors/Health.java
Purpose:
- Manages the health of game entities (e.g., player, enemies, and boss). Tracks damage taken and determines when an entity is destroyed.

16. Controller.java
Location: src/main/java/com/example/demo/controller/Controller.java
Purpose:
- Manages the game's overall flow, including level transitions, error handling, and coordinating the game's various components.

17. LevelBoss.java
Location: src/main/java/com/example/demo/levels/LevelBoss.java
Purpose:
- Implements the boss level logic, including boss-specific mechanics such as shield management, projectile firing, and unique gameplay elements.

## Modified Java Classes
1. ActiveActor.java
Improved Collision Accuracy:
- The original ActiveActor.java used the default bounding box, which often led to inaccurate collision detections. Introducing a reduced bounding box ensures collisions align more closely with the visual representation of actors.
Enhanced Code Readability and Maintainability:
- Adding JavaDoc comments and restructuring methods (like loadImage()) improves readability for collaborators and future developers.
- Centralizing the image loading logic eliminates redundancy and potential inconsistencies.
Modular and Configurable Design:
- Introducing constants like HITBOX_MARGIN makes the class more configurable. Adjusting collision sensitivity now requires changing only one value.
Debugging and Development Efficiency:
- The new getReducedBounds() method is especially helpful for debugging collision logic during gameplay.

2. ActiveActorDestructible.java
Improved Documentation and Readability:
- Adding JavaDoc comments makes the code self-explanatory and developer-friendly, facilitating easier onboarding for collaborators.
Enhanced Code Organization:
- Moving the file to the com.example.demo.actors package aligns with the modular structure of the project and ensures consistency with related actor classes.
Clarity in Responsibilities:
- By clearly describing abstract methods like updateActor and takeDamage, the new implementation provides a clear contract for subclasses to follow.
Code Consistency and Maintainability:
- Explicitly importing the Destructible interface and adhering to consistent formatting improves maintainability and ensures clarity in dependencies.
Debugging and Development Efficiency:
- By providing a clear structure for managing destruction states and responsibilities, debugging issues related to destructible actors becomes easier.

3. Boss.java
Enhanced Gameplay Mechanics:
- Shield Mechanics: The introduction of shield management adds a strategic layer to the boss fight, making gameplay more dynamic and challenging.
- Precise Movement Boundaries: Ensures that the boss stays within the playable area, preventing visual inconsistencies.
Improved Debugging Capabilities:
- Hitbox Rendering: The addition of hitbox visualization aids developers in testing and debugging collision logic, ensuring accurate interactions.
Better Collision Accuracy:
- Reduced Hitbox: A smaller hitbox provides a fairer gameplay experience by preventing unexpected collisions.
Clean and Modular Code:
- Refactoring logic into dedicated classes like ShieldManager and MovementPattern makes the code easier to read, maintain, and extend.
Consistency with Game Mechanics:
- Synchronizing the shield’s position with the boss ensures visual consistency and enhances immersion during gameplay.

4. BossProjectile.java
Dynamic and Challenging Gameplay:
- Zigzag Motion: Adds complexity to the boss's projectiles, making them harder for the player to avoid.
- Faster Speed: Increases difficulty and aligns with the boss's aggressive behavior during gameplay.
Improved Collision Accuracy:
- Reduced Image Height: Ensures the projectile's visual size better matches its collision box, avoiding unfair hits.
Code Clarity and Maintainability:
- JavaDoc Comments: Provide clear documentation for future developers or collaborators.
- Consistent Structure: Ensures readability and aligns with modern coding practices.
Enhanced Visual Appeal:
- The zigzag motion makes the projectile more visually engaging, creating a polished and professional gameplay experience.

5. Destructible.java
Improved Readability and Usability:
- The addition of JavaDoc comments makes the interface more self-explanatory, enabling developers to quickly understand its purpose and how to implement it.
Enhanced Maintainability:
- Clear documentation ensures that the intent of the interface is preserved, even as the codebase evolves.
Consistency with Project Standards:
- Aligns with the documentation style and quality maintained throughout the project, ensuring consistency and professionalism.

6. EnemyPlane.java
Improved Gameplay Mechanics:
- Reduced Hitbox: Ensures that players experience fair collisions, avoiding frustration from oversized hitboxes.
- Increased Fire Rate: Adds difficulty and makes enemy planes more engaging and threatening.
Enhanced Debugging Capabilities:
- Hitbox Rendering: Allows developers to visualize collision boundaries, ensuring that hitboxes align with visual assets.
Better Documentation and Readability:
- Adding JavaDoc comments and inline explanations makes the codebase easier to maintain and extend, especially in a collaborative environment.
Future Proofing:
- The modular design and well-documented methods provide a foundation for adding additional behaviors or debugging tools in the future.

7. EnemyProjectile.java
Enhanced Gameplay Dynamics:
- Adjustable Speed: Allows the same enemy type to fire projectiles with different speeds, introducing variability and increasing difficulty in higher levels.
- Smaller Projectile Size: Provides fairer collision boundaries for the player, improving gameplay balance.
Improved Maintainability:
- Overloaded Constructor: Makes the class reusable across different game scenarios without duplicating logic.
- JavaDoc Comments: Ensures future developers can easily understand and extend the class.
Debugging and Testing Flexibility:
- With adjustable speeds, testing various projectile behaviors and balancing the game becomes more straightforward.

8. FighterPlane
Improved Collision Accuracy:
- The reduced hitbox ensures that collisions align better with the visual appearance of the plane, preventing unfair gameplay scenarios.
Enhanced Debugging and Testing:
- The renderHitbox and toString methods make it easier to debug and verify the functionality of fighter planes during development.
Consistency Across Gameplay:
- Standardizing hitbox handling and debugging ensures that all fighter planes (player and enemies) behave predictably and consistently.
Code Readability and Maintainability:
- Detailed documentation and structured methods improve the maintainability of the codebase, especially for future developers.

9. GameOverImage.java
Improved Code Organization:
- Moving the class to the ui package aligns it with other UI-related components like MainMenu and PauseMenu, improving maintainability.
Enhanced Readability:
- Adding JavaDoc comments ensures the class's purpose and usage are immediately clear to anyone reading the code.
Simplified Logic:
- Removing unused or commented-out code streamlines the class, making it easier to understand and maintain.

10. HeartDisplay.java
Improved Project Structure:
- Moving the class to the ui package ensures that all UI-related components are logically grouped, making the project easier to navigate and maintain.
Enhanced Readability:
- Adding JavaDoc comments clarifies the purpose and functionality of the HeartDisplay class, aiding developers in understanding and using the class effectively.
Better Coding Practices:
- Enforcing encapsulation and consistent code style aligns with best practices, improving the quality and maintainability of the codebase.

11. LevelOne.java
Improved Project Organization:
- Grouping level-related classes under com.example.demo.levels improves maintainability and logical structure.
Enhanced Readability:
- Adding documentation and improving method organization makes the class easier to understand for collaborators and future developers.
Better Debugging Tools:
- Adding position logging provides real-time feedback on user plane interactions, helping to identify gameplay issues during testing.
Consistency Across Levels:
- Updating the constructor and initialization methods aligns the class with other level classes, ensuring uniform behavior.
Gameplay Improvements:
- The toFront() method and refined enemy spawning logic ensure a smoother and more visually coherent gameplay experience.

12. LevelParent.java
Improved User Experience:
- Pause Menu and Background Music: Adds essential features for a polished game, ensuring better player control and immersion.
- Score Tracking: Enhances gameplay feedback and makes progress measurable.
Better Project Organization:
- Grouping levels into a levels package and splitting long methods improves code clarity and maintainability.
Easier Debugging:
- Console logs and modular methods make it easier to trace issues and test specific features.
Future-Proof Design:
- Abstract methods and encapsulated behaviors (e.g., level-specific spawning logic) ensure that new levels can be added without affecting existing functionality.

13. LevelTwo.java
Improved Gameplay Experience:
- Shield and health mechanics add depth to the boss fight, making it more challenging and rewarding.
- Enhanced visuals with LevelViewBoss and a unique background create a distinct atmosphere for the boss level.
Better User Feedback:
- Real-time updates for the boss's health and shield state keep players informed, improving engagement.
Consistency Across Levels:
- Standardizing stage interaction and extending LevelParent ensures consistent gameplay flow and easier level management.
Simplified Debugging and Testing:
- Console logging helps identify potential issues during the boss level

14. LevelView.java
Improved Gameplay Feedback:
- Score Display: Keeps the player informed about their progress, enhancing engagement and competitiveness.
- Heart Display: Updates the health visualizations dynamically, improving gameplay immersion.
Better Project Structure:
- Moving to com.example.demo.levels ensures that all level-related components are grouped logically, reducing complexity.
Consistency and Clarity:
- Adding JavaDoc comments and organizing code logically ensures the class is easier to maintain and extend.
Enhanced Aesthetics:
- Applying styling to the score display improves the overall presentation and professionalism of the game.

15. LevelViewTwo.java
Enhanced Gameplay Feedback:
- The addition of the boss's health display keeps players informed about their progress during the boss fight.
Improved Immersion:
- The shield image, dynamically shown and hidden, adds a layer of strategy and visual feedback.
Better Code Clarity:
- JavaDoc comments and logical grouping of elements ensure the class is easier to understand and maintain.
Alignment with Gameplay Needs:
- Reflecting the boss-specific requirements through the renaming and added elements ensures consistency and extensibility for future boss levels.
    
16. Projectile.java
Improved Gameplay Mechanics:
- The reduced hitbox ensures that collisions are accurate, aligning with the visual representation of the projectile.
Better Code Readability:
- Detailed documentation and organized code make the class easier to understand and extend.
Debugging and Maintenance:
- The getReducedBounds() method simplifies debugging of collision-related issues, making it easier to test and refine projectile behaviors.

17. ShieldImage.java
Better Code Organization:
- Moving the class to the ui package ensures all UI-related components are logically grouped, improving project structure.
Enhanced Readability and Maintainability:
- Detailed JavaDoc comments make the code easy to understand and maintain for future developers.
Resolved Resource Loading Issues:
- Correcting the image path ensures the shield image loads reliably, preventing runtime errors.
Improved Visual Behavior:
- The toFront() call in showShield() ensures proper layering of game elements, avoiding graphical glitches.

18. UserPlane.java
Improved Gameplay Accuracy:
- Reduced Hitbox: Aligns collision detection with visual elements, ensuring fair and intuitive gameplay.
Better Debugging and Testing:
- Hitbox Rendering and Detailed Logs: Provide clear feedback during development to identify and resolve issues.
Enhanced Modularity:
- KillCountTracker and ProjectileFiringBehavior: Decouple functionalities, making the code easier to maintain and extend.
Consistency and Readability:
- JavaDoc comments and method organization improve code clarity and consistency, making it easier for collaborators to work on the project

19. UserProjectile.java
Improved Gameplay Mechanics:
- Dynamic Synchronization: Allows projectiles to either follow the user or behave independently, adding versatility to the gameplay.
Fair and Precise Collisions:
- Reduced Hitbox: Ensures collisions are accurate, aligning with the visual representation of the projectile.
Debugging and Testing:
- Hitbox Visualization: Helps developers verify collision logic and detect inconsistencies during development.
- Logging: Provides insights into projectile behavior during gameplay for easier debugging.
Maintainability and Readability:
- JavaDoc comments and structured code make the class easy to understand and extend.

20. WinImage.java
Better Project Structure:
- Moving the class to the ui package ensures that all UI-related components are logically grouped, reducing complexity and improving maintainability.
Improved Readability:
- Detailed JavaDoc comments and organized code make the class self-explanatory and easier to maintain.
Resource Management Consistency:
- Using getClass().getResource() for loading the image aligns with the project's conventions, preventing runtime errors and improving reliability.

21. Module-info.java
Feature Integration:
- Adding required modules like javafx.media supports new features such as background music and audio effects.
Maintainability:
- Detailed comments and organized declarations make the module configuration easier to understand and update.
Improved Accessibility:
- Exporting critical packages and properly opening others ensures that all project components interact seamlessly.

22. Pom.xml
Java Version Compatibility:
- Updating to Java SE 21 ensures the project can utilize the latest Java features and aligns with the runtime environment.
Enhanced Testing Capabilities:
- Adding TestFX dependencies enables automated UI testing, improving project reliability and maintainability.
Improved Maven Build Process:
- Configurations for maven-surefire-plugin and javafx-maven-plugin ensure smooth build and execution of JavaFX applications.

## Unexpected Challenges
1. Issue with Boss Hitbox Size
What Went Wrong:
- The boss character's hitbox was significantly larger than its visual representation, leading to unfair collisions and a frustrating gameplay experience.
Resolution:
- Introduced the getReducedBounds() method to calculate a smaller, visually accurate hitbox. This method reduced the bounding box by a fixed margin to match the boss's appearance. Debugging tools such as hitbox visualization were also implemented to verify the accuracy.

2. Background Music Overlapping
What Went Wrong:
- When transitioning between levels, background music from the previous level sometimes continued playing, resulting in overlapping tracks.
Resolution:
- Added stopBackgroundMusic() methods in the level classes to explicitly stop the current track before transitioning. Verified the behavior using debug logs to ensure that only one track played at a time.

3. Multiple Planes Hit by a Single Projectile
What Went Wrong:
- A single projectile could damage multiple enemy planes in one frame due to overlapping hitboxes.
Resolution:
- Refined the collision detection logic to flag and remove projectiles after their first collision. This ensured that projectiles would only impact one enemy plane at a time.


## Unexpected Challenges
Below are the key challenges encountered during the development process, along with their explanations and solutions:

1. Issue with Boss Hitbox Size
What Went Wrong:
- The boss character's hitbox was significantly larger than its visual representation, leading to unfair collisions and a frustrating gameplay experience.
Resolution:
- Introduced the getReducedBounds() method to calculate a smaller, visually accurate hitbox. This method reduced the bounding box by a fixed margin to match the boss's appearance. Debugging tools such as hitbox visualization were also implemented to verify the accuracy.

2. Background Music Overlapping
What Went Wrong:
- When transitioning between levels, background music from the previous level sometimes continued playing, resulting in overlapping tracks.
Resolution:
- Added stopBackgroundMusic() methods in the level classes to explicitly stop the current track before transitioning. Verified the behavior using debug logs to ensure that only one track played at a time.

3. Multiple Planes Hit by a Single Projectile
What Went Wrong:
- A single projectile could damage multiple enemy planes in one frame due to overlapping hitboxes.
Resolution:
- Refined the collision detection logic to flag and remove projectiles after their first collision. This ensured that projectiles would only impact one enemy plane at a time.

4. Resource Path Issues
What Went Wrong:
- Several resources (e.g., images, sounds) failed to load correctly because the paths were incorrectly specified, particularly for resources packaged within the JAR file.
Resolution:
- Standardized resource paths using getClass().getResource() for consistent loading in all environments. Conducted tests to verify resource loading after packaging.

5. UserProjectile Behavior
What Went Wrong:
- The UserProjectile moved inconsistently due to incorrect initial positions and an unhandled edge case where it could move outside the visible screen.
Resolution:
- Adjusted the initial X and Y positions for the projectile based on the player's position. Added boundary checks to ensure the projectile stays within the visible screen area.

6. Managing Shield States for the Boss
What Went Wrong:
- The boss's shield visual did not align with its actual state, causing confusion when players hit the boss while the shield appeared inactive.
Resolution:
- Synchronized the shield's visual state with its logical state using the ShieldImage and ShieldManager classes. Added methods like showShield() and hideShield() for dynamic updates.

7. Maven Build Errors
What Went Wrong:
- Maven builds initially failed due to missing dependencies and incorrect configurations for JavaFX and test libraries.
Resolution:
- Updated the pom.xml file to include all required dependencies (javafx.media, TestFX) and properly configured the maven-compiler-plugin for Java 21. Verified successful builds through repeated tests.

8. Handling Player Inputs
What Went Wrong:
- Player input handling caused delays in movement, particularly when multiple keys were pressed simultaneously.
Resolution:
- Refined the key event handlers to prioritize movement keys (UP, DOWN) and decoupled firing logic (SPACE). Optimized input handling by ensuring only active states triggered actions.

9. Debugging Hitboxes
What Went Wrong:
- Verifying hitboxes for collisions was challenging due to their invisible nature during gameplay.
Resolution:
- Added a debugging feature to render hitboxes as visible rectangles during development. This feature could be toggled using a debug flag, aiding in quick identification of issues.

## Additional Files
- Additional files included in the submission (Design.pdf, Demo.mp4, Javadoc folder).
- Provide a brief description of each file’s purpose:
  Design.pdf: High-level class diagram of the project
  Demo.mp4: A short video showcasing the project and key features
  Javadoc folder: Contains the auto-generated HTML documentation for the project





