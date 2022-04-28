package app.controllers;

import app.ConnectDataBase;
import app.objects.PersonalProtective;
import app.objects.TableIndividualClothing;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddClothingPositionController implements Controller {

    @FXML
    public TextField fieldUnit;
    @FXML
    public TextField fieldCount;
    @FXML
    public TextField fieldNameClothing;
    @FXML
    public TextField fieldSearchClothing;
    @FXML
    public TableView<PersonalProtective> tableSearchClothing;
    @FXML
    public TableColumn<PersonalProtective, String> columnName;
    @FXML
    public TableColumn<PersonalProtective, String> columnUnit;
    @FXML
    public Button buttonAdd;

    private static PersonalProtective pp;

    @FXML
    private void initialize() {

        columnName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PersonalProtective, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PersonalProtective, String> cellDate) {
                return cellDate.getValue().nameProperty();
            }
        });

        columnUnit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PersonalProtective, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PersonalProtective, String> cellDate) {
                return cellDate.getValue().unitProperty();
            }
        });

        ObservableList<PersonalProtective> personalProtectives = FXCollections.observableArrayList();

        try {
            ResultSet resultSet = ConnectDataBase.query("select * from PERSONAL_PROTECTIVE;");

            while (resultSet.next()) {

                personalProtectives.add(new PersonalProtective(
                        resultSet.getInt("ID_PERSONAL_PROTECTIVE"),
                        resultSet.getString("NAME"),
                        resultSet.getString("UNIT")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tableSearchClothing.setItems(personalProtectives );

        FilteredList<PersonalProtective> filteredData = new FilteredList<>(personalProtectives, p -> true);

        fieldSearchClothing.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate(personalProtective -> {


            if (newValue == null || newValue.isEmpty()) return true;

            String loserCase = newValue.toLowerCase();

            if (personalProtective.getName().toLowerCase().contains(loserCase))
                return true;
            return false;
            });
        });

        SortedList<PersonalProtective> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableSearchClothing.comparatorProperty());
        tableSearchClothing.setItems(sortedData);


        tableSearchClothing.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pp = tableSearchClothing.getSelectionModel().getSelectedItem();

                if (pp == null) return;

                fieldNameClothing.setText(pp.getName());
                fieldUnit.setText(pp.getUnit());

                System.out.println(tableSearchClothing.getSelectionModel().getSelectedItem().getName());
            }
        });

        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!(fieldCount.getText().strip() == null || fieldCount.getText().strip().isEmpty())) {
                    DetailsPositionController.getIndividualClothingData().add(new TableIndividualClothing(
                            pp.getName(),
                            "",
                            pp.getUnit(),
                            fieldCount.getText()
                    ));
                    DetailsPositionController.setAdd(true);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Успешно добавленно");
                    alert.showAndWait();
                }
            }

        });

    }

    public static PersonalProtective getPp() {
        return pp;
    }

    @Override
    public void setMain(Stage Main) {

    }
}
