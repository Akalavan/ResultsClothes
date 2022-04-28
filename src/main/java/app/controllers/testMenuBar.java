package app.controllers;

import app.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class testMenuBar implements Controller {

    @FXML
    public MenuBar menuBar;
    @FXML
    public Menu menu;
    @FXML
    public MenuItem item;

    @FXML
    private void initialize() {
        item.setOnAction(actionEvent -> {
            MainApp.getMainController().initView("/view/ViewAllPerson.fxml", "Рабочие", new AllPersonController(), 500, 500);
        });

    }

    @Override
    public void setMain(Stage Main) {

    }
}
