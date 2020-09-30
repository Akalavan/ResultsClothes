package app.controllers;

import app.ConnectDataBase;
import app.MainApp;
import app.objects.Worker;
import app.objects.print.Clothes;
import app.objects.print.PrintChecklist;
import app.unload.Excel.ManagingSpreadsheet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

    @FXML
    private Button print;
    private Worker worker = null;
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
                worker = newValue;
            }
        });

        print.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (worker != null) {
                    ManagingSpreadsheet managingSpreadsheet = new ManagingSpreadsheet(new PrintChecklist(
                            worker.getTable(),
                            worker.getFirstName(),
                            worker.getSecondName(),
                            worker.getSurname(),
                            worker.getPosition()
                    ));
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Выберите Рабочего");
                    alert.showAndWait();
                }
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
            positionLabel.setText(worker.getPosition().getName());
        } else {
            tableLabel.setText("");
            nameLabel.setText("");
            lastNameLabel.setText("");
            surnameLabel.setText("");
            positionLabel.setText("");
        }

    }


}
