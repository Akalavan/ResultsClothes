package app.controllers;

import app.MainApp;
import app.objects.Worker;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AllPersonController implements Controller {

    @FXML
    public TableView<Worker> tablePerson;
    @FXML
    public TableColumn<Worker, String> columnTable;
    @FXML
    public TableColumn<Worker, String> columnName;
    @FXML
    public TableColumn<Worker, String> columnSurname;
    @FXML
    public TableColumn<Worker, String> columnPatronymic;
    @FXML
    public TextField textSearch;
    @FXML
    public Button buttonSearch;
    @FXML
    public Button buttonAdd;

    @FXML
    private void initialize() {

        columnTable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Worker, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Worker, String> cellData) {
                return cellData.getValue().tableProperty();
            }
        });

        columnName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Worker, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Worker, String> cellData) {
                return cellData.getValue().firstNameProperty();
            }
        });

        columnSurname.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Worker, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Worker, String> cellData) {
                return cellData.getValue().surnameProperty();
            }
        });

        columnPatronymic.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Worker, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Worker, String> cellData) {
                return cellData.getValue().secondNameProperty();
            }
        });


        tablePerson.setItems(MainApp.getWorkerData());

    }

    @Override
    public void setMain(Stage Main) {

    }
}