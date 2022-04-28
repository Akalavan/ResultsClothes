package app.controllers;

import app.ConnectDataBase;
import app.MainApp;
import app.objects.TableIndividualClothing;
import app.objects.Worker;
import app.objects.print.Clothes;
import app.objects.print.PrintChecklist;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Arrays;

public class ChangePersonController implements Controller {
    @FXML
    public TableView<TableIndividualClothing> tableClothes;
    @FXML
    public TableColumn<TableIndividualClothing, String> columnNameClothes;
    @FXML
    public TableColumn<TableIndividualClothing, String> columnNorma;
    @FXML
    public TableColumn<TableIndividualClothing, String> columnUnt;
    @FXML
    public TableColumn<TableIndividualClothing, String> columnCount;


    @FXML
    public Button buttonChange;
    @FXML
    public Button buttonSave;


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

    ObservableList<TableIndividualClothing> individualClothingData = FXCollections.observableArrayList();

    private Stage main;

    private static Worker worker;

    private PrintChecklist checklist;

    public ChangePersonController() {
    }

    @FXML
    private void initialize() {
        columnNameClothes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableIndividualClothing, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TableIndividualClothing, String> cellData) {
//                columnNameClothes.getTableView().setOnMouseClicked(mouseEvent -> {
//                   // System.out.println(mouseEvent.getPickResult());
//                    if (mouseEvent.getClickCount() == 2) {
//                        MainApp.getMainController().initView("/view/choiceClothing.fxml", "Выбор одежды", new ChoiceClothingController(), 500, 500);
//                    }
//                });
                return cellData.getValue().nameClothingProperty();
            }
        });

        columnNorma.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableIndividualClothing, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TableIndividualClothing, String> cellData) {
                return cellData.getValue().normalDocumentProperty();
            }
        });

        columnUnt.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableIndividualClothing, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TableIndividualClothing, String> cellData) {
                return cellData.getValue().unitProperty();
            }
        });

        columnCount.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableIndividualClothing, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TableIndividualClothing, String> cellData) {
                return cellData.getValue().quantityYearProperty();
            }
        });

        columnCount.setCellFactory(TextFieldTableCell.<TableIndividualClothing>forTableColumn());
//        columnCount.setOnEditCommit(event -> {
//            TablePosition<TableIndividualClothing, String> pos = event.getTablePosition();
//
//            String newCount = event.getNewValue();
//
//            int row = pos.getRow();
//
//
//        });

        buttonChange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangePositionController.setCallerClass("ChangePersonController");
                MainApp.getMainController().initView("/view/changePersonPosition.fxml", "Должности", new ChangePositionController(), 500, 500);
                if (ChangePositionController.getPos() != null) {
                    textPosition.setText(ChangePositionController.getPos().getName());
                    checklist.setPosition(ChangePositionController.getPos());
                    checklist = new PrintChecklist(
                            worker.getTable(),
                            worker.getFirstName(),
                            worker.getSecondName(),
                            worker.getSurname(),
                            worker.getPosition());
                    individualClothingData = FXCollections.observableArrayList();
                    for (Clothes clothes:
                            checklist.getClothesList()) {
                        individualClothingData.add(new TableIndividualClothing(
                                clothes.getNameClothes(),
                                checklist.getDocument().getName(),
                                clothes.getUnit(),
                                clothes.getQuantity_year()
                        ));
                    }

                    System.out.println("buttonChange.setOnAction()");
                    tableClothes.setItems(individualClothingData);
                }
            }
        });

        buttonSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!textSurname.getText().equals(worker.getSecondName())) {
                    System.out.println("textSurname");
                    System.out.println(textSurname.getText() + ".equals(" + worker.getSecondName() + ")");
                    worker.setSecondName(textSurname.getText());
                    System.out.println("UPDATE PUBLIC.WORKER t SET t.SECOND_NAME = '" + textSurname.getText() + "' WHERE t.ID_WORKER = " + worker.getId());
                    ConnectDataBase.query("UPDATE PUBLIC.WORKER t SET t.SECOND_NAME = '" + textSurname.getText() + "' WHERE t.ID_WORKER = " + worker.getId());
                }
                if (!textName.getText().equals(worker.getFirstName())) {
                    System.out.println("textName");
                    System.out.println(textName.getText() + ".equals(" + worker.getFirstName() + ")");
                    worker.setFirstName(textName.getText());
                    System.out.println("UPDATE PUBLIC.WORKER t SET t.FIRST_NAME = '" + textName.getText() + "' WHERE t.ID_WORKER = " + worker.getId());
                    ConnectDataBase.query("UPDATE PUBLIC.WORKER t SET t.FIRST_NAME = '" + textName.getText() + "' WHERE t.ID_WORKER = " + worker.getId());
                }
                if (!textPatronymic.getText().equals(worker.getSurname())) {
                    System.out.println("textPatronymic");
                    System.out.println(textSurname.getText() + ".equals(" + worker.getSurname() + ")");
                    worker.setSurname(textPatronymic.getText());
                    System.out.println("UPDATE PUBLIC.WORKER t SET t.SURNAME = '" + textPatronymic.getText() + "' WHERE t.ID_WORKER = " + worker.getId());
                    ConnectDataBase.query("UPDATE PUBLIC.WORKER t SET t.SURNAME = '" + textPatronymic.getText() + "' WHERE t.ID_WORKER = " + worker.getId());
                }
                if (!textTable.getText().equals(worker.getTable())) {
                    System.out.println("textTable");
                    System.out.println(textSurname.getText() + ".equals(" + worker.getSurname() + ")");
                    worker.setTable(textTable.getText());
                    System.out.println("UPDATE PUBLIC.WORKER t SET t.TABLE = '" + textTable.getText() + "' WHERE t.ID_WORKER = " + worker.getId());
                    ConnectDataBase.query("UPDATE PUBLIC.WORKER t SET t.TABLE = '" + textTable.getText() + "' WHERE t.ID_WORKER = " + worker.getId());
                }
            }
        });


    }

    public void setWorker(Worker worker) {
        individualClothingData = FXCollections.observableArrayList();
        this.worker = worker;


        textSurname.setText(checklist.getSurname());
        textName.setText(checklist.getName());
        textPatronymic.setText(checklist.getPatronymic());
        textTable.setText(checklist.getTableNumber());
        textPosition.setText(checklist.getPosition().getName());

//        textSurname.setText(worker.getSecondName());
//        textName.setText(worker.getFirstName());
//        textPatronymic.setText(worker.getSurname());
//        textTable.setText(worker.getTable());
//        textPosition.setText(worker.getPosition().getName());


//        ObservableList<TableColumn<TableIndividualClothing, ?>> tableColumns =  tableClothes.getColumns();
//
//
//        for (TableColumn<TableIndividualClothing, ?> tb :
//                tableColumns) {
//            System.out.println(tb.getText());
//            if (tb.getText().equals("Пункт типовых отраслевых норм"))
//
//        }
        System.out.println(Arrays.toString(individualClothingData.toArray()));
        System.out.println(checklist.getClothesList().isEmpty());
       // if (!checklist.getClothesList().isEmpty())
            for (Clothes clothes:
                 checklist.getClothesList()) {
                individualClothingData.add(new TableIndividualClothing(
                        clothes.getNameClothes(),
                        checklist.getDocument().getName(),
                        clothes.getUnit(),
                        clothes.getQuantity_year()
                ));
            }


        tableClothes.setItems(individualClothingData);


    }

    public void setMain(Stage main) {
        this.main = main;
    }

    public void setChecklist(PrintChecklist checklist) {
        this.checklist = checklist;
    }

    public static Worker getWorker() {
        return worker;
    }
}

