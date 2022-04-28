package app.controllers;

import app.ConnectDataBase;
import app.MainApp;
import app.objects.NormalDocument;
import app.objects.PersonalProtective;
import app.objects.Position;
import app.objects.TableIndividualClothing;
import app.objects.print.Clothes;
import app.objects.print.PrintChecklist;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailsPositionController implements Controller {



    private int id;

    private Position pos;

    @FXML
    public TextField fieldName;
    @FXML
    public TextField fieldNormDoc;

    @FXML
    public TableView<TableIndividualClothing> tableClothing;
    @FXML
    public TableColumn<TableIndividualClothing, String> columnName;
    @FXML
    public TableColumn<TableIndividualClothing, String> columnUnit;
    @FXML
    public TableColumn<TableIndividualClothing, String> columnCount;

    @FXML
    public Button buttonSave;
    @FXML
    public Button buttonAdd;
    @FXML
    public Button buttonChange;
    @FXML
    public Button buttonDelete;


    private PrintChecklist checklist;

    private static ObservableList<TableIndividualClothing> individualClothingData;
    private static NormalDocument normalDocument;
    private static boolean add = false;


    @FXML
    private void initialize() {


        columnName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableIndividualClothing, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TableIndividualClothing, String> cellDate) {
                return cellDate.getValue().nameClothingProperty();
            }
        });

        columnUnit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableIndividualClothing, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TableIndividualClothing, String> cellDate) {
                return cellDate.getValue().unitProperty();
            }
        });

        columnCount.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableIndividualClothing, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TableIndividualClothing, String> cellDate) {
                return cellDate.getValue().quantityYearProperty();
            }
        });

        pos = AllPositionController.getPos();
        fieldName.setText(pos.getName());

        try {

        ResultSet resultSetDoc = ConnectDataBase.query("select nd.ID_NORMAL_DOCUMENT, nd.NAME from NORMAL_DOCUMENT nd, POSITION p where nd.ID_NORMAL_DOCUMENT = p.ID_NORMAL_DOC and p.ID_POSITION = " + pos.getId());

        while (resultSetDoc.next()) {
            normalDocument = new NormalDocument(resultSetDoc.getInt("ID_NORMAL_DOCUMENT"), resultSetDoc.getString("NAME"));
        }

        fieldNormDoc.setText(normalDocument.getName());

        individualClothingData = FXCollections.observableArrayList();


            ResultSet resultSet = ConnectDataBase.query("" +
                    "select pp.ID_PERSONAL_PROTECTIVE, pp.NAME, pp.UNIT, c.QUANTITY_YEAR, nd.NAME as NAMEND " +
                    "from CHECKLIST c, PERSONAL_PROTECTIVE pp, NORMAL_DOCUMENT nd " +
                    "where c.ID_PERSONAL_PROTECTIVE = pp.ID_PERSONAL_PROTECTIVE and nd.ID_NORMAL_DOCUMENT = c.ID_NORMAL_DOCUMENT and c.ID_POSITION = " + pos.getId() + ";");

            while (resultSet.next()) {
                TableIndividualClothing tic = new TableIndividualClothing(
                        resultSet.getString("NAME"),
                        resultSet.getString("NAMEND"),
                        resultSet.getString("UNIT"),
                        resultSet.getString("QUANTITY_YEAR")
                );
                tic.setId(resultSet.getInt("ID_PERSONAL_PROTECTIVE"));
                    individualClothingData.add(tic);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("id = " + pos.getId());
        tableClothing.setItems(individualClothingData);
    }

    public void setChecklist(PrintChecklist checklist) {
        this.checklist = checklist;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setMain(Stage Main) {

    }

    public void action(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) return;

        Button btnClick = (Button) source;

        switch (btnClick.getId()) {
            case "buttonAdd":
               // MainApp.getMainController().initView("/view/changePosition.fxml", "Одежда", new DetailsPositionController(), 500, 500);
                MainApp.getMainController().initView("/view/addClothingPosition.fxml", "Изменение", new AddClothingPositionController(), 500,500);
                System.out.println(add + ": buttonAdd");
                break;
            case "buttonSave":
                System.out.println(add + ": buttonSave");
                if (add) {
                    int idND = 0;
                    try {
                        ResultSet resultSet = ConnectDataBase.query("select ID_NORMAL_DOCUMENT FROM NORMAL_DOCUMENT, CHECKLIST " +
                                "where ID_NORMAL_DOCUMENT = CHECKLIST.ID_NORMAL_DOCUMENT AND CHECKLIST.ID_POSITION = " + pos.getId() + " LIMIT 1;");
                        while (resultSet.next()) {
                            idND = resultSet.getInt("ID_NORMAL_DOCUMENT");
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                    PersonalProtective pp = AddClothingPositionController.getPp();
                    TableIndividualClothing tic = tableClothing.getItems().get(tableClothing.getItems().size() - 1);
                    ConnectDataBase.queryInsert("insert into CHECKLIST (ID_POSITION, ID_PERSONAL_PROTECTIVE, QUANTITY_YEAR, ID_NORMAL_DOCUMENT)" +
                            "VALUES ( " + pos.getId() + ", " + pp.getId() + ", " + tic.getQuantityYear() + ", " + idND + ");");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Успешно сохраненно");
                    alert.showAndWait();
                }
                break;
            case "buttonChange":
                MainApp.getMainController().initView("/view/editPositionNormDoc.fxml", "Изменение", new AddClothingPositionController(), 500,500);
                fieldNormDoc.setText(normalDocument.getName());
                break;
            case "buttonDelete":
                TableIndividualClothing tic = tableClothing.getSelectionModel().getSelectedItem();
                int idN = tableClothing.getSelectionModel().getFocusedIndex();
                System.out.println(tic.getNameClothing());

                if (tic == null) return;

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Удаление записи");
                alert.setHeaderText("Вы уверены, что хотите удалить запись?");
                alert.setContentText("Запись под номером: " + idN + " Название: " +tic.getNameClothing());
                alert.showAndWait();

                System.out.println(alert.getResult());

                if (alert.getResult() == ButtonType.OK) {
                    System.out.println("OK");

                    System.out.println("select c.ID_CHECKLIST from CHECKLIST c where c.ID_POSITION = " + pos.getId() + " and c.ID_PERSONAL_PROTECTIVE = " + tic.getId() + " and c.QUANTITY_YEAR = '" + tic.getQuantityYear() + "';");
                    ResultSet resultSet = ConnectDataBase.query("select c.ID_CHECKLIST from CHECKLIST c where c.ID_POSITION = " + pos.getId() + " and c.ID_PERSONAL_PROTECTIVE = " + tic.getId() + " and c.QUANTITY_YEAR = '" + tic.getQuantityYear() + "';");
                    int idCheck = -1;
                    try {

                        while (resultSet.next()) {

                            idCheck = resultSet.getInt("ID_CHECKLIST");

                        }

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    System.out.println("DELETE FROM PUBLIC.CHECKLIST WHERE ID_CHECKLIST = " + idCheck);
                    ConnectDataBase.query("DELETE FROM PUBLIC.CHECKLIST WHERE ID_CHECKLIST = " + idCheck);
                    individualClothingData.remove(tic);

                } else if (alert.getResult() == ButtonType.CANCEL) {
                    System.out.println("CANCEL");
                }
        }
    }

    public static void setAdd(boolean add) {
        DetailsPositionController.add = add;
    }

    public static ObservableList<TableIndividualClothing> getIndividualClothingData() {
        return individualClothingData;
    }

    public static NormalDocument getNormalDocument() {
        return normalDocument;
    }

}
