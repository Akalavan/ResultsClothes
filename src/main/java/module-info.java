module App {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires poi.ooxml;
    requires poi;
    requires java.desktop;
    exports app;
    opens app.controllers;
    exports app.controllers;
}

