package app.controllers;

import app.ConnectDataBase;
import app.objects.NormalDocument;
import app.objects.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EditPositionNormDocController implements Controller {


    @FXML
    public TableView<NormalDocument> tableNormalDoc;
    @FXML
    public TableColumn<NormalDocument, String> columnNameDoc;

    @FXML
    public TextField fieldSearchND;
    @FXML
    public TextField fieldNormalDoc;

    @FXML
    public Button buttonChange;

    private static NormalDocument nd;

    private static Position p;

    @FXML
    private void initialize() {
        columnNameDoc.setCellValueFactory(cellDate -> cellDate.getValue().nameProperty());

        ObservableList<NormalDocument> normalDocuments = FXCollections.observableArrayList();

        try {
            ResultSet resultSet = ConnectDataBase.query("select * from NORMAL_DOCUMENT;");

            while (resultSet.next()) {

                normalDocuments.add(new NormalDocument(
                        resultSet.getInt("ID_NORMAL_DOCUMENT"),
                        resultSet.getString("NAME")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tableNormalDoc.setItems(normalDocuments);

        FilteredList<NormalDocument> filteredData = new FilteredList<>(normalDocuments, p -> true);

        fieldSearchND.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate(normalDocument -> {


                if (newValue == null || newValue.isEmpty()) return true;

                String loserCase = newValue.toLowerCase();

                if (normalDocument.getName().toLowerCase().contains(loserCase))
                    return true;
                return false;
            });
        });

        SortedList<NormalDocument> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableNormalDoc.comparatorProperty());
        tableNormalDoc.setItems(sortedData);


        tableNormalDoc.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                nd = tableNormalDoc.getSelectionModel().getSelectedItem();

                if (nd == null) return;

                fieldNormalDoc.setText(nd.getName());

                System.out.println(tableNormalDoc.getSelectionModel().getSelectedItem().getName());
            }
        });

        tableNormalDoc.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                nd = tableNormalDoc.getSelectionModel().getSelectedItem();

                if (nd == null) return;

                fieldNormalDoc.setText(nd.getName());

                System.out.println(tableNormalDoc.getSelectionModel().getSelectedItem().getName());
            }
        });

        buttonChange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (nd == null)  {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Выбирите нормативный документ");
                    alert.showAndWait();
                    return;
                }

                int id = AllPositionController.getPos().getId();;

                if (id == -1) return;

                System.out.println("UPDATE PUBLIC.POSITION t SET t.ID_NORMAL_DOC = " + nd.getId() + " WHERE t.ID_POSITION = " + id);
                System.out.println("update CHECKLIST c set c.ID_NORMAL_DOCUMENT = " + nd.getId() + " where c.ID_POSITION = " + id);

                ConnectDataBase.queryInsert("UPDATE PUBLIC.POSITION t SET t.ID_NORMAL_DOC = " + nd.getId() + " WHERE t.ID_POSITION = " + id);
                ConnectDataBase.queryInsert("update CHECKLIST c set c.ID_NORMAL_DOCUMENT = " + nd.getId() + " where c.ID_POSITION = " + id);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Успешно изменено");
                alert.showAndWait();

                DetailsPositionController.getNormalDocument().setName(nd.getName());
                DetailsPositionController.getNormalDocument().setId(nd.getId());
            }
        });
    }

    @Override
    public void setMain(Stage Main) {

    }

    public static void setP(Position p) {
        EditPositionNormDocController.p = p;
    }
}
