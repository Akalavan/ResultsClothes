package app.controllers;

import app.ConnectDataBase;
import app.MainApp;
import app.objects.NormalDocument;
import app.objects.PersonalProtective;
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

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllNormDocController implements Controller {

    @FXML
    public TableView<NormalDocument> tableNormDoc;
    @FXML
    public TableColumn<NormalDocument, String> columnNameDoc;

    @FXML
    public TextField textSearch;

    @FXML
    public Button buttonAdd;
    @FXML
    public Button buttonEdit;

    private static ObservableList<NormalDocument> normalDocuments = FXCollections.observableArrayList();

    private static NormalDocument normalDocument;

    @FXML
    private void initialize() {
        columnNameDoc.setCellValueFactory(cellDate -> cellDate.getValue().nameProperty());

        normalDocuments = FXCollections.observableArrayList();

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

        tableNormDoc.setItems(normalDocuments);

        FilteredList<NormalDocument> filteredData = new FilteredList<>(normalDocuments, p -> true);

        textSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate(personalProtective -> {

                if (newValue == null || newValue.isEmpty()) return true;

                String loserCase = newValue.toLowerCase();

                if (personalProtective.getName().toLowerCase().contains(loserCase))
                    return true;
                return false;
            });
        });

        SortedList<NormalDocument> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableNormDoc.comparatorProperty());
        tableNormDoc.setItems(sortedData);

        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MainApp.getMainController().initView("/view/addNormalDoc.fxml", "Добавление", new AddNormalDocController(), 453,109);
            }
        });

        buttonEdit.setOnAction(actionEvent -> {
            normalDocument = tableNormDoc.getSelectionModel().getSelectedItem();
            if (normalDocument != null)
                MainApp.getMainController().initView("/view/editNormDoc.fxml", "Изменение", new EditNormDocController(), 728, 106);
        });

    }

    @Override
    public void setMain(Stage Main) {

    }

    public static ObservableList<NormalDocument> getNormalDocuments() {
        return normalDocuments;
    }

    public static NormalDocument getNormalDocument() {
        return normalDocument;
    }
}
