/**
 * Module declaration for the `com.example.demo` application.
 * Specifies the required JavaFX modules, dependencies, and package access configurations.
 */
module com.example.demo {
    // Required JavaFX modules for UI components, media playback, and base classes
    requires javafx.controls; // JavaFX controls for building the UI (e.g., Button, TextField)
    requires javafx.fxml; // JavaFX FXML support for defining UI in XML files
    requires javafx.media; // JavaFX media support for audio and video playback
    requires javafx.base; // JavaFX base classes for properties and collections
    requires transitive javafx.graphics; // JavaFX graphics module, transitive to pass visibility to dependent modules

    // Desktop support for optional features (e.g., image handling, AWT components)
    requires transitive java.desktop;

    // Opens the `com.example.demo` package to JavaFX for reflection (required by FXMLLoader)
    opens com.example.demo to javafx.fxml;

    // Exports packages for use by other modules or external code
    exports com.example.demo.controller; // Exports the controller package for managing app logic
    exports com.example.demo; // Exports the main package for launching and app-level resources
}
