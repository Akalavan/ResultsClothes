package app.controllers;

import app.MainApp;
import app.objects.Worker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class PersonOverviewController {

    @FXML
    private TableView<Worker> personTable;
    @FXML
    private TableColumn<Worker, String> tableColumn;
    @FXML
    private TableColumn<Worker, String> nameColumn;
    @FXML
    private TableColumn<Worker, String> lastNameColumn;

    @FXML
    private Label tableLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label positionLabel;

    private MainApp mainApp;

    public PersonOverviewController() {
    }

    @FXML
    private void initialize() {
        tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Worker, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Worker, String> cellData) {
                return cellData.getValue().tableProperty();
            }
        });
        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Worker, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Worker, String> cellData) {
                return cellData.getValue().firstNameProperty();
            }
        });
        lastNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Worker, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Worker, String> cellData) {
                return cellData.getValue().secondNameProperty();
            }
        });

        showPersonDetails(null);

        personTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Worker>() {
            @Override
            public void changed(ObservableValue<? extends Worker> observable, Worker oldValue, Worker newValue) {
                PersonOverviewController.this.showPersonDetails(newValue);
            }
        });
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        personTable.setItems(mainApp.getWorkerData());
    }
    
    private void showPersonDetails(Worker worker) {
        if (worker != null) {
            tableLabel.setText(worker.getTable());
            nameLabel.setText(worker.getFirstName());
            lastNameLabel.setText(worker.getSecondName());
            surnameLabel.setText(worker.getSurname());
            positionLabel.setText(worker.getPosition());
        } else {
            tableLabel.setText("");
            nameLabel.setText("");
            lastNameLabel.setText("");
            surnameLabel.setText("");
            positionLabel.setText("");
        }

    }
}
