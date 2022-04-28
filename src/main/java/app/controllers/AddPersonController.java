package app.controllers;

import app.ConnectDataBase;
import app.MainApp;
import app.objects.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPersonController implements Controller {

    @FXML
    public Button buttonSave;
    @FXML
    public Button buttonChangePosition;
    @FXML
    public Button buttonChangeService;


    @FXML
    public TextField textName;
    @FXML
    public TextField textService;
    @FXML
    public TextField textSurname;
    @FXML
    public TextField textPosition;
    @FXML
    public TextField textPatronymic;
    @FXML
    public TextField textTable;


    private static Worker worker = new Worker();

    @FXML
    private void initialize() {
        buttonChangePosition.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangePositionController.setCallerClass("AddPersonController");
                MainApp.getMainController().initView("/view/changePersonPosition.fxml", "Должности", new ChangePositionController(), 500, 500);
                textPosition.setText(worker.getPosition().getName());
            }
        });

        buttonChangeService.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeServicesController.setCallerClass("AddPersonController");
                MainApp.getMainController().initView("/view/changePersonServices.fxml", "Службы", new ChangeServicesController(), 500, 500);
                textService.setText(worker.getService().getName());
            }
        });

        buttonSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if ( textName.getText().strip().equals("") ) message(Alert.AlertType.WARNING,"Заполните Имя");
                else if ( textSurname.getText().strip().equals("") ) message(Alert.AlertType.WARNING,"Заполните Фамилию");
                else if ( textPosition.getText().strip().equals("") ) message(Alert.AlertType.WARNING, "Выбирите Должность");
                else if ( textService.getText().strip().equals("") ) message(Alert.AlertType.WARNING, "Выбирите Службу");
                else if ( textTable.getText().strip().equals("") ) message(Alert.AlertType.WARNING, "Заполните табель");
                else {
                    worker.setFirstName(textName.getText().strip());
                    worker.setSecondName(textSurname.getText().strip());
                    worker.setSurname(textPatronymic.getText().strip());
                    worker.setTable(textTable.getText().strip());
                    MainApp.getWorkerData().add(worker);
                    // дописать добавление рабочего
                    ConnectDataBase.queryInsert("INSERT INTO WORKER " +
                            "(\"TABLE\", ID_SERVICE, SECOND_NAME, FIRST_NAME, SURNAME, DATE_ACCEPTANCE, ID_POSITION) VALUES " +
                            "('" + worker.getTable() + "', " + worker.getService().getId() + ", '" + worker.getFirstName() + "', '" + worker.getSecondName() + "', '" + worker.getSurname() + "', '" + worker.getDateAcceptance().toString() + "', " + worker.getPosition().getId() + ");");
                    message(Alert.AlertType.INFORMATION, "Успешно добавлен");
                    worker = new Worker();
                    textName.setText("");
                    textSurname.setText("");
                    textPosition.setText("");
                    textPatronymic.setText("");
                    textService.setText("");
                    textTable.setText("");
                }
            }
        });

    }

    void message(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }

    @Override
    public void setMain(Stage Main) {

    }

    public static Worker getWorker() {
        return worker;
    }
}
