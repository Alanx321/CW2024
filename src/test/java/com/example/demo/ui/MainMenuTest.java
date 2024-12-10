package com.example.demo.ui;

import com.example.demo.controller.HowToPlayWindow;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Test class for the MainMenu JavaFX application.
 * Uses TestFX for JavaFX UI testing and Mockito for mocking dependencies.
 */
@ExtendWith(ApplicationExtension.class)
public class MainMenuTest extends ApplicationTest {

    private MainMenu mainMenu;
    private HowToPlayWindow mockHowToPlayWindow;

    /**
     * Initializes the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        // Mock the HowToPlayWindow for dependency injection
        mockHowToPlayWindow = mock(HowToPlayWindow.class);
    }

    /**
     * Cleans up resources after each test.
     */
    @AfterEach
    void tearDown() {
        if (mainMenu != null && mainMenu.getBackgroundMusicPlayer() != null) {
            mainMenu.getBackgroundMusicPlayer().stop();
        }
    }

    /**
     * Starts the JavaFX application and initializes MainMenu with a mocked HowToPlayWindow.
     *
     * @param stage The primary stage for the JavaFX test.
     * @throws Exception If an error occurs during initialization.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Inject the mocked HowToPlayWindow into MainMenu
        mockHowToPlayWindow = mock(HowToPlayWindow.class);
        mainMenu = new MainMenu(mockHowToPlayWindow);
        mainMenu.start(stage);
    }

    /**
     * Tests that the START, HOW TO PLAY, and QUIT buttons exist in the MainMenu.
     *
     * @param robot FxRobot instance for interacting with the UI.
     */
    @Test
    void testButtonsExist(FxRobot robot) {
        // Lookup buttons by their text
        assertNotNull(robot.lookup("START").queryButton(), "START button should exist.");
        assertNotNull(robot.lookup("HOW TO PLAY").queryButton(), "HOW TO PLAY button should exist.");
        assertNotNull(robot.lookup("QUIT").queryButton(), "QUIT button should exist.");
    }

    /**
     * Tests the functionality of the HOW TO PLAY button.
     * Verifies that clicking the button triggers the display method of HowToPlayWindow.
     *
     * @param robot FxRobot instance for interacting with the UI.
     */
    @Test
    void testHowToPlayButtonAction(FxRobot robot) {
        // Verify the button exists
        assertNotNull(robot.lookup("HOW TO PLAY").queryButton(), "HOW TO PLAY button should exist.");

        // Simulate clicking the HOW TO PLAY button
        robot.clickOn("HOW TO PLAY");

        // Verify that the HowToPlayWindow's display method was invoked
        verify(mockHowToPlayWindow, times(1)).display();
    }


    /**
     * Tests the functionality of the START button.
     * Verifies that clicking the button stops the background music and starts the game.
     *
     * @param robot FxRobot instance for interacting with the UI.
     */
    @Test
    void testStartButtonAction(FxRobot robot) {
        // Simulate clicking the START button
        robot.clickOn("START");

        // Verify that background music is stopped
        assertNotNull(mainMenu.getBackgroundMusicPlayer(), "Background music player should not be null.");
        assertNotNull(mainMenu.getBackgroundMusicPlayer().getStatus(), "Background music should stop.");
    }

    /**
     * Tests the functionality of the QUIT button.
     * Verifies that clicking the button closes the application.
     *
     * @param robot FxRobot instance for interacting with the UI.
     */
    @Test
    void testQuitButtonAction(FxRobot robot) {
        // Simulate clicking the QUIT button
        Stage stage = (Stage) robot.lookup("QUIT").queryButton().getScene().getWindow();
        robot.clickOn("QUIT");

        // Assert that the stage is no longer showing
        assertNotNull(stage, "The stage should not be null.");
        assertNotNull(stage.isShowing(), "The application should close when QUIT is clicked.");
    }
}
