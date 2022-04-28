package app.controllers;

import app.ConnectDataBase;
import app.MainApp;
import app.objects.PersonalProtective;
import app.objects.Position;
import app.objects.Worker;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class AllClothingController implements Controller {
    @FXML
    public TableView<PersonalProtective> tableClothing;
    @FXML
    public TableColumn<PersonalProtective, String> tableNameClothing;
    @FXML
    public TableColumn<PersonalProtective, String> tableUnit;


    @FXML
    public TextField textSearch;


    @FXML
    public Button buttonAdd;
    @FXML
    public Button buttonSearch;
    @FXML
    public Button buttonEdit;


    private static PersonalProtective personalProtective;

    static private ObservableList<PersonalProtective> personalProtectives = FXCollections.observableArrayList();


    @FXML
    private void initialize() {

        tableNameClothing.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PersonalProtective, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PersonalProtective, String> cellData) {
                return cellData.getValue().nameProperty();
            }
        });

        tableUnit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PersonalProtective, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PersonalProtective, String> cellData) {
                return cellData.getValue().unitProperty();
            }
        });

        personalProtectives = FXCollections.observableArrayList();

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

        tableClothing.setItems(personalProtectives);


        FilteredList<PersonalProtective> filteredData = new FilteredList<>(personalProtectives, p -> true);

        textSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate(personalProtective -> {

                if (newValue == null || newValue.isEmpty()) return true;

                String loserCase = newValue.toLowerCase();

                if (personalProtective.getName().toLowerCase().contains(loserCase))
                    return true;
                return false;
            });
        });

        SortedList<PersonalProtective> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableClothing.comparatorProperty());
        tableClothing.setItems(sortedData);



        buttonSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tableClothing.getItems().stream()
                        .filter(clothing -> clothing.getName().toLowerCase().equals(textSearch.getText().toLowerCase()))
                        .findAny()
                        .ifPresent(clothing -> {
                            tableClothing.getSelectionModel().select(clothing);
                            tableClothing.scrollTo(clothing);
                        });

            }
        });

        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MainApp.getMainController().initView("/view/addClothing.fxml", "Добавление", new AddClothingController(), 500,109);
            }
        });

        buttonEdit.setOnAction(actionEvent -> {
            personalProtective = tableClothing.getSelectionModel().getSelectedItem();
            if (personalProtective != null)
                MainApp.getMainController().initView("/view/editClothing.fxml", "Изменение", new EditClothingController(), 458, 106);
        });


    }

    @Override
    public void setMain(Stage Main) {

    }

    public static ObservableList<PersonalProtective> getPersonalProtectives() {
        return personalProtectives;
    }

    public static PersonalProtective getPersonalProtective() {
        return personalProtective;
    }
}
