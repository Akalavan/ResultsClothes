package app.controllers;

import app.ConnectDataBase;
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

public class AddClothingController implements Controller{

    @FXML
    public TextField fieldNameNew;
    @FXML
    public TextField fieldUnit;
    @FXML
    public Button buttonAdd;

    @FXML
    private void initialize() {
        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (fieldNameNew.getText().strip().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Не введено наименование одежды");
                    alert.showAndWait();
                    return;
                }

                int id = -1;
                try {
                    ResultSet resultSet = ConnectDataBase.query("select MAX(ID_PERSONAL_PROTECTIVE) FROM PERSONAL_PROTECTIVE;");

                    while (resultSet.next()) {
                        id = resultSet.getInt(1);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if (id == -1) return;
                PersonalProtective pp = new PersonalProtective(id, fieldNameNew.getText(), fieldUnit.getText());

                System.out.println("INSERT INTO PUBLIC.PERSONAL_PROTECTIVE (NAME, UNIT) VALUES ('" + pp.getName() + "', '" + pp.getUnit() + "')");
                ConnectDataBase.queryInsert("INSERT INTO PUBLIC.PERSONAL_PROTECTIVE (NAME, UNIT) VALUES ('" + pp.getName() + "', '" + pp.getUnit() + "')");

                fieldNameNew.setText("");
                fieldUnit.setText("");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Успешно добавленно");
                alert.showAndWait();

                AllClothingController.getPersonalProtectives().add(pp);


            }
        });
    }

    @Override
    public void setMain(Stage Main) {

    }
}
