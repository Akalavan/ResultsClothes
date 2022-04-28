package app;

import app.controllers.AllPersonController;
import app.controllers.MainController;
import app.controllers.PersonOverviewController;
import app.objects.Position;
import app.objects.Worker;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainApp extends Application {

    private static ObservableList<Worker> workerData = FXCollections.observableArrayList();
    private static Stage primaryStage;
    private static MainController mainController;
    private static ConnectDataBase CDB;
    private BorderPane rootLayout;

    public MainApp() {
        Statement stmt;
        ResultSet result, result1;

        try {
            CDB = new ConnectDataBase();
            stmt = CDB.getStmt();

            result = stmt.executeQuery("select w.TABLE, w.FIRST_NAME, w.SECOND_NAME, w.SURNAME, p.ID_POSITION, p.NAME from WORKER w, POSITION p " +
                    "where w.ID_POSITION = p.ID_POSITION");
            int i = 0;
            while (result.next()) {

                Position position = null;
                position = new Position(result.getInt("ID_POSITION"), result.getString("NAME"));
                System.out.println(position);
                workerData.add(new Worker(
                        i,
                        result.getString("TABLE"),
                        result.getString("SECOND_NAME"),
                        result.getString("FIRST_NAME"),
                        result.getString("SURNAME"),
                        "data",
                        position));
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Перечень одежды");

        InputStream stream = getClass().getResourceAsStream("/coveralls.png");
        Image image = new Image(stream);
        this.primaryStage.getIcons().add(image);

        initRootLayout();
        showPersonOverview();
    }

    private void showPersonOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/PersonOverview.fxml"));
            AnchorPane personalOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(personalOverview);

            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRootLayout() {
        mainController = new MainController();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Worker> getWorkerData() {
        return workerData;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static MainController getMainController() {
        return mainController;
    }

    public static ConnectDataBase getCDB() {
        return CDB;
    }
}
