package com.example.demo.utils;

// Importing necessary classes
import javafx.scene.control.Alert;

/**
 * ErrorHandler utility class handles error reporting and displays alerts to the user.
 * It provides a centralized mechanism for managing errors across the application.
 */
public class ErrorHandler {

    /**
     * Displays an error alert with the given details.
     *
     * @param title  The title of the error alert window.
     * @param header The header text displayed in the alert window.
     * @param cause  The exception or error that occurred.
     */
    public static void showError(String title, String header, Throwable cause) {
        // Print the error details to the console for debugging purposes
        System.err.println(header + ": " + cause);
        cause.printStackTrace();

        // Create and configure an error alert to show the error details
        Alert alert = new Alert(Alert.AlertType.ERROR); // Initialize an error-type alert
        alert.setTitle(title); // Set the title of the alert window
        alert.setHeaderText(header); // Set the header text to describe the context of the error
        alert.setContentText("Error: " + cause.getMessage()); // Display the specific error message

        // Show the alert to the user
        alert.show();
    }
}
