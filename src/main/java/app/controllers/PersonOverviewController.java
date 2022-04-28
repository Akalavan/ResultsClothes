package app.controllers;

import app.ConnectDataBase;
import app.MainApp;
import app.objects.PersonalProtective;
import app.objects.Worker;
import app.objects.print.Clothes;
import app.objects.print.PrintChecklist;
import app.unload.Excel.ManagingSpreadsheet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PersonOverviewController implements Controller {

    @FXML
    public TextField textSearch;


    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu itemMenuOpen;
    @FXML
    private Menu itemMenuCreate;

    @FXML
    private MenuItem itemOpenClothing;
    @FXML
    private MenuItem itemOpenPosition;
    @FXML
    private MenuItem itemCreateWorker;
    @FXML
    public MenuItem itemOpenNormDoc;



    private Stage editPerson;

    private Stage mainStage;

    private Parent fxmlEditPerson;

    private ChangePersonController changePersonController;


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
    @FXML
    private Button change;


    private Worker worker = null;

    FXMLLoader mainLoader = new FXMLLoader();

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

        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (worker != null) {
                    MainApp.getMainController().getChangePersonController().setChecklist(new PrintChecklist(
                            worker.getTable(),
                            worker.getFirstName(),
                            worker.getSecondName(),
                            worker.getSurname(),
                            worker.getPosition()
                    ));
                    MainApp.getMainController().getChangePersonController().setWorker(worker);
                    MainApp.getMainController().showDialog(actionEvent);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Выберите Рабочего");
                    alert.showAndWait();
                }
            }
        });


        itemOpenClothing.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MainApp.getMainController().initView("/view/ViewAllClothing.fxml", "Одежда", new AllClothingController(), 500, 500);
            }
        });

        itemOpenPosition.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MainApp.getMainController().initView("/view/changeAllPosition.fxml", "Должности", new AllPositionController(), 500, 500);
            }
        });

        itemOpenNormDoc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MainApp.getMainController().initView("/view/ViewAllNormDoc.fxml", "Нормативные документы", new AllNormDocController(), 500, 500);
            }
        });

        itemCreateWorker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MainApp.getMainController().initView("/view/addPerson.fxml", "Добавить", new AllClothingController(), 660, 218);
            }
        });


        personTable.setItems(MainApp.getWorkerData());


        FilteredList<Worker> filteredData = new FilteredList<>(MainApp.getWorkerData(), p -> true);

        textSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate(workers -> {

                if (newValue == null || newValue.isEmpty()) return true;

                String loserCase = newValue.toLowerCase();


                return workers.getFirstName().toLowerCase().contains(loserCase) ||
                        workers.getSecondName().toLowerCase().contains(loserCase) ||
                        workers.getSurname().toLowerCase().contains(loserCase) ||
                        workers.getTable().toLowerCase().contains(loserCase);

            });
        });

        SortedList<Worker> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(personTable.comparatorProperty());
        personTable.setItems(sortedData);

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

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


    @Override
    public void setMain(Stage Main) {

    }
}
