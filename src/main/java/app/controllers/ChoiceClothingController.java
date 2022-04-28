package app.controllers;

import app.objects.TableIndividualClothing;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ChoiceClothingController implements Controller {

    @FXML
    public TableView<TableIndividualClothing> tableClothes;
    @FXML
    public TableColumn<TableIndividualClothing, String>  columnNameClothes;
    @FXML
    public TextField textSearch;
    @FXML
    public Button search;


    @FXML
    private void initialize() {
        columnNameClothes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableIndividualClothing, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TableIndividualClothing, String> cellDate) {
                return cellDate.getValue().nameClothingProperty();
            }
        });
    }

    @Override
    public void setMain(Stage Main) {

    }
}
