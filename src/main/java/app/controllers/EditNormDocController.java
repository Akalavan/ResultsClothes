package app.controllers;

import app.ConnectDataBase;
import app.objects.NormalDocument;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditNormDocController implements Controller {

    @FXML
    public TextField fieldNameDoc;

    @FXML
    public Button buttonSave;

    private NormalDocument normalDocument;

    @FXML
    private void initialize() {
        normalDocument = AllNormDocController.getNormalDocument();

        fieldNameDoc.setText(normalDocument.getName());

        buttonSave.setOnAction(actionEvent -> {
            normalDocument.setName(fieldNameDoc.getText());
            ConnectDataBase.queryInsert("UPDATE NORMAL_DOCUMENT t SET t.NAME = '" + normalDocument.getName() + "' WHERE t.ID_NORMAL_DOCUMENT = " + normalDocument.getId());
        });
    }

    @Override
    public void setMain(Stage Main) {

    }
}
