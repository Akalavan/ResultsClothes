package app.controllers;

import app.ConnectDataBase;
import app.objects.PersonalProtective;
import app.objects.print.Clothes;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditClothingController implements Controller {

    @FXML
    public TextField fieldNameClothing;
    @FXML
    public TextField fieldUnit;
    @FXML
    public Button buttonSave;

    private PersonalProtective personalProtective;


    @FXML
    private void initialize() {
        personalProtective = AllClothingController.getPersonalProtective();

        fieldNameClothing.setText(personalProtective.getName());
        fieldUnit.setText(personalProtective.getUnit());

        buttonSave.setOnAction(actionEvent -> {
            personalProtective.setName(fieldNameClothing.getText());
            personalProtective.setUnit(fieldUnit.getText());
            ConnectDataBase.queryInsert("UPDATE PUBLIC.PERSONAL_PROTECTIVE t SET t.NAME = '" + personalProtective.getName() + "', t.UNIT = '" + personalProtective.getUnit() + "' WHERE t.ID_PERSONAL_PROTECTIVE = " + personalProtective.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Успешно изменено");
            alert.showAndWait();
        });
    }

    @Override
    public void setMain(Stage Main) {

    }


}
