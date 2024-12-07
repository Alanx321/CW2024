package com.example.demo.controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class responsible for displaying the "How to Play" window.
 */
public class HowToPlayWindow {

    /**
     * Displays the "How to Play" window.
     * Delegates the layout and styling to helper methods to follow the Single Responsibility Principle.
     */
    public void display() {
        // Create a new stage for the "How to Play" window
        Stage howToPlayStage = new Stage();
        
        // Block interaction with the main menu while this window is open
        howToPlayStage.initModality(Modality.APPLICATION_MODAL);
        howToPlayStage.setTitle("How to Play");

        // Get the layout of the "How to Play" window
        Scene scene = new Scene(createContent(howToPlayStage), 800, 450);

        // Set the scene for the stage and display the window
        howToPlayStage.setScene(scene);
        howToPlayStage.show();
    }

    /**
     * Creates and organizes the content for the "How to Play" window.
     * 
     * @param howToPlayStage the stage to be closed by the close button.
     * @return a styled root layout for the scene.
     */
    private StackPane createContent(Stage howToPlayStage) {
        // Create title text
        Text title = createTitle();

        // Create key stroke summary text
        Text summary = createSummary();

        // Create close button
        Button closeButton = createCloseButton(howToPlayStage);

        // Layout for the window using a BorderPane
        BorderPane layout = new BorderPane();
        layout.setTop(title); // Add the title to the top
        layout.setCenter(summary); // Add the summary text to the center
        layout.setBottom(closeButton); // Add the close button to the bottom

        // Center-align the title and close button in their respective positions
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(closeButton, Pos.CENTER);

        // Create a root layout with a background style
        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #0f2027, #203a43, #2c5364);"); // Gradient background

        // Return the styled root layout
        return root;
    }

    /**
     * Creates the title text for the "How to Play" window.
     * 
     * @return a styled Text object for the title.
     */
    private Text createTitle() {
        // Title text for the window
        Text title = new Text("Key Stroke Summary");
        title.setFont(new Font("Arial", 30)); // Set font and size for the title
        title.setStyle("-fx-fill: white;"); // Set the text color to white
        return title;
    }

    /**
     * Creates the summary text explaining key strokes.
     * 
     * @return a styled Text object containing the key stroke summary.
     */
    private Text createSummary() {
        // Key stroke summary text with instructions for controls
        Text summary = new Text(
            "P: Go to Pause Menu\n\n" +
        
            "UP Arrow: Move the plane up\n\n" +
            
            "DOWN Arrow: Move the plane down\n\n" +
            
            "SPACE: Fire a projectile\n\n" +
            
            "Release UP/DOWN: Stop the plane movement\n\n" +
            
            "To Restart: The button is in the Pause Menu\n\n" +
            
            "3 Levels:\n" + 
            "Level One  : 10 kills\n"+
            "Level Two  : 15 kills\n"+
            "Level Boss : Kill the Boss\n"
            
        );
        summary.setFont(new Font("Arial", 20)); // Set font and size for the summary
        summary.setStyle("-fx-fill: white;"); // Set the text color to white
        return summary;
    }

    /**
     * Creates a close button for the "How to Play" window.
     * 
     * @param howToPlayStage the stage to be closed when the button is clicked.
     * @return a styled Button object for closing the window.
     */
    private Button createCloseButton(Stage howToPlayStage) {
        // Close button to exit the "How to Play" window
        Button closeButton = new Button("CLOSE");
        closeButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 15px;"); // Style the button
        closeButton.setOnAction(e -> howToPlayStage.close()); // Close the window when the button is clicked
        return closeButton;
    }
}
