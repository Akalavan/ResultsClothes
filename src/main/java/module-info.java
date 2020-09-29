module App {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires poi.ooxml;
    requires poi;
    exports app;
    opens app.controllers;
    exports app.controllers;
}

