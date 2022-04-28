package app.controllers;

import app.ConnectDataBase;
import app.objects.NormalDocument;
import app.objects.PersonalProtective;
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
import javafx.scene.input.RotateEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddPositionController implements Controller {
    
    @FXML
    public TextField fieldNewPosition;
    @FXML
    public TableView<NormalDocument> tableNormalDoc;
    @FXML
    public TableColumn<NormalDocument, String> columnNameDoc;
    @FXML
    public TextField fieldSearchND;
    @FXML
    public TextField fieldNormalDoc;
    @FXML
    public Button buttonAdd;

    private static NormalDocument nd;

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

        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (nd == null)  {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Выбирите нормативный документ");
                    alert.showAndWait();
                    return;
                }

                if (fieldNewPosition.getText().strip().equals(""))  {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Не введено название должности");
                    alert.showAndWait();
                    return;
                }

                int id = -1;
                try {
                    ResultSet resultSet = ConnectDataBase.query("select MAX(ID_POSITION) FROM POSITION;");

                    while (resultSet.next()) {
                        id = resultSet.getInt(1);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if (id == -1) return;
                Position p = new Position(id, fieldNewPosition.getText());
                System.out.println("insert into POSITION (NAME, ID_NORMAL_DOC)" +
                        " VALUES ('" + p.getName() + "', " + nd.getId() + ");");
                ConnectDataBase.queryInsert("insert into POSITION (NAME, ID_NORMAL_DOC)" +
                        "VALUES ( '" + p.getName() + "', " + nd.getId() + ");");

                fieldNewPosition.setText("");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Успешно добавленно");
                alert.showAndWait();

                AllPositionController.getPositions().add(p);
            }
        });

    }

    @Override
    public void setMain(Stage Main) {

    }
}
