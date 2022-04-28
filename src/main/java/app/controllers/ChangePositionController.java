package app.controllers;

import app.ConnectDataBase;
import app.MainApp;
import app.objects.NormalDocument;
import app.objects.Position;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePositionController implements Controller{

    @FXML
    public TableView<Position> tablePosition;
    @FXML
    public TableColumn<Position, String> columnNamePosition;
    @FXML
    public TextField fieldPosition;
    @FXML
    public TextField fieldSearchPosition;
    @FXML
    public Button buttonChange;
    private static Position pos;

    private Position posLocal;

    private static String callerClass = "";

    @FXML
    private void initialize() {

        columnNamePosition.setCellValueFactory(cellDate -> cellDate.getValue().nameProperty());

        ObservableList<Position> positions = FXCollections.observableArrayList();

        try {
            ResultSet resultSet = ConnectDataBase.query("select * from POSITION;");

            while (resultSet.next()) {
                positions.add(new Position(
                        resultSet.getInt("ID_POSITION"),
                        resultSet.getString("NAME")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        tablePosition.setItems(positions);


        FilteredList<Position> filteredData = new FilteredList<>(positions, p -> true);

        fieldSearchPosition.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate(position -> {


                if (newValue == null || newValue.isEmpty()) return true;

                String loserCase = newValue.toLowerCase();

                if (position.getName().toLowerCase().contains(loserCase))
                    return true;
                return false;
            });
        });

        SortedList<Position> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tablePosition.comparatorProperty());
        tablePosition.setItems(sortedData);


        tablePosition.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                posLocal = tablePosition.getSelectionModel().getSelectedItem();

                if (posLocal == null) return;

                fieldPosition.setText(posLocal.getName());

                System.out.println(tablePosition.getSelectionModel().getSelectedItem().getName());
            }
        });

        buttonChange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (posLocal == null) return;
                pos = posLocal;
                System.out.println(callerClass);
                if (callerClass.equals("ChangePersonController")) {
                    int idW = ChangePersonController.getWorker().getId();
                    ConnectDataBase.queryInsert("UPDATE WORKER t SET t.ID_POSITION = " + pos.getId() + " WHERE t.ID_WORKER = " + idW);
                    ChangePersonController.getWorker().setPosition(pos);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Успешно изменено");
                    alert.showAndWait();
                } else if (callerClass.equals("AddPersonController")) {
                    AddPersonController.getWorker().setPosition(posLocal);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Успешно изменено");
                    alert.showAndWait();
                }

                //записать обновление в должности и обновить все записи в чеклисте на новую должность у данного работника.

            }
        });

    }

    @Override
    public void setMain(Stage Main) {
        
    }

    public static Position getPos() {
        return pos;
    }

    public static void setCallerClass(String callerClass) {
        ChangePositionController.callerClass = callerClass;
    }
}
