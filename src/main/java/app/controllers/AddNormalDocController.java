package app.controllers;

import app.ConnectDataBase;
import app.objects.NormalDocument;
import app.objects.PersonalProtective;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddNormalDocController implements Controller {

    @FXML
    public TextField fieldNameNew;
    @FXML
    public Button buttonAdd;

    @FXML
    private void initialize() {

        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (fieldNameNew.getText().strip().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Не введено наименование нормативного документа");
                    alert.showAndWait();
                    return;
                }

                int id = -1;
                try {
                    ResultSet resultSet = ConnectDataBase.query("select MAX(ID_NORMAL_DOCUMENT) FROM NORMAL_DOCUMENT;");

                    while (resultSet.next()) {
                        id = resultSet.getInt(1);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if (id == -1) return;
                NormalDocument nd = new NormalDocument(id, fieldNameNew.getText());

                System.out.println("INSERT INTO PUBLIC.NORMAL_DOCUMENT (NAME) VALUES ('" + nd.getName() + "')");
                ConnectDataBase.queryInsert("INSERT INTO PUBLIC.NORMAL_DOCUMENT (NAME) VALUES ('" + nd.getName() + "')");

                fieldNameNew.setText("");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Успешно добавленно");
                alert.showAndWait();

                AllNormDocController.getNormalDocuments().add(nd);


            }
        });

    }

    @Override
    public void setMain(Stage Main) {

    }
}
