package app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

public class AppCheckList extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Перечень одежды");
        primaryStage.setWidth(600);
        primaryStage.setHeight(700);

        InputStream stream = getClass().getResourceAsStream("/coveralls.png");
        Image image = new Image(stream);
        primaryStage.getIcons().add(image);

        Button button = new Button("Enter");
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Enter");
                alert.showAndWait();
            }
        });

        Scene primaryScene = new Scene(button);
        primaryStage.setScene(primaryScene);

        primaryStage.show();
    }
}
