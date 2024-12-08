module com.example.demo {
    // Required JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media; // Remove if not used
    requires javafx.base;
    requires transitive javafx.graphics;

    // Desktop support (optional, depending on your app needs)
    requires transitive java.desktop;

    // Opens packages for reflection (required by JavaFX)
    opens com.example.demo to javafx.fxml;

    // Exported packages
    exports com.example.demo.controller;
    exports com.example.demo;
}
