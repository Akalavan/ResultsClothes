package app.controllers;


import app.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    private Stage editPerson;

    private Stage mainStage;

    private Parent fxmlEditPerson;

    private ChangePersonController changePersonController;

    FXMLLoader mainLoader = new FXMLLoader();

    public MainController() {
        mainStage = MainApp.getPrimaryStage();
        init();
    }

    private void init() {
        try {

            mainLoader.setLocation(getClass().getResource("/view/changePerson.fxml"));
            fxmlEditPerson = mainLoader.load();
            changePersonController = mainLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

        editPerson = reg(editPerson, fxmlEditPerson, "ChangePerson", 566, 500);
    }

    public void initView(String path, String nameView, Controller controller, int width, int height) {
        Parent fxml;
        Stage stageView = null;
        try {
            mainLoader = new FXMLLoader();
            mainLoader.setLocation(getClass().getResource(path));
            fxml = mainLoader.load();
            controller = mainLoader.getController();

            stageView = reg(stageView, fxml, nameView, width, height);
            showDialog(stageView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDialog(Stage stageView) {
        //controller.setMain(mainStage);
        stageView.showAndWait();
    }



    public void showDialog(ActionEvent event) {
        Object source = event.getSource();

        if (!(source instanceof Button)) return;

        Button btnClick = (Button) source;

        switch (btnClick.getId()) {
            case "change":
                changePersonController.setMain(mainStage);
                editPerson.showAndWait();

        }
    }

    private Stage reg(Stage stage, Parent parent, String title, int width, int height) {
        if (stage == null) {
            stage = new Stage();
            stage.setTitle(title);
            stage.setMinWidth(width);
            stage.setMinHeight(height);
            // stage.setResizable(false);
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainStage);
        }
        return stage;
    }

    public ChangePersonController getChangePersonController() {
        return changePersonController;
    }

    public Stage getEditPerson() {
        return editPerson;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public Parent getFxmlEditPerson() {
        return fxmlEditPerson;
    }

    public FXMLLoader getMainLoader() {
        return mainLoader;
    }
}
