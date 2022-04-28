package app.controllers;

import app.ConnectDataBase;
import app.MainApp;
import app.objects.PersonalProtective;
import app.objects.Position;
import app.objects.print.PrintChecklist;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllPositionController implements Controller{
    @FXML
    public Button buttonSearch;
    @FXML
    public Button buttonChange;
    @FXML
    public Button buttonAdd;
    @FXML
    public TableView<Position> TablePosition;
    @FXML
    public TableColumn<Position, String> columnName;
    @FXML
    public TextField textSearch;

    private PrintChecklist checklist;

    private static int id;
    private static Position pos;
    private Position posSearch;
    private static ObservableList<Position> positions;


    @FXML
    private void initialize() {
        columnName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Position, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Position, String> cellDate) {
                return cellDate.getValue().nameProperty();
            }
        });

        positions = FXCollections.observableArrayList();

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

        buttonSearch.setVisible(false);

        TablePosition.setItems(positions);


        FilteredList<Position> filteredData = new FilteredList<>(positions, p -> true);

        textSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate(position -> {


                if (newValue == null || newValue.isEmpty()) return true;

                String loserCase = newValue.toLowerCase();

                if (position.getName().toLowerCase().contains(loserCase))
                    return true;
                return false;
            });
        });

        SortedList<Position> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(TablePosition.comparatorProperty());
        TablePosition.setItems(sortedData);


    }


    public void action(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) return;

        Button btnClick = (Button) source;

        switch (btnClick.getId()) {
            case "buttonChange":
                pos = TablePosition.getSelectionModel().getSelectedItem();
                if (pos != null)
                    MainApp.getMainController().initView("/view/changePosition.fxml", "Одежда", new DetailsPositionController(), 500, 500);
                break;
            case "buttonSearch":
                System.out.println(textSearch.getText());
                TablePosition.getItems().stream()
                        .filter(position -> position.getName().equals(textSearch.getText()))
                        .findAny()
                        .ifPresent(position -> {
                            TablePosition.getSelectionModel().select(position);
                            TablePosition.scrollTo(position);
                        });
                break;
            case "buttonAdd":
                MainApp.getMainController().initView("/view/addPosition.fxml", "Добавить", new AddPositionController(), 500, 500);

        }

    }

    @Override
    public void setMain(Stage Main) {

    }

    public void setChecklist(PrintChecklist checklist) {
        this.checklist = checklist;
    }

    public static int getId() {
        return id;
    }

    public static Position getPos() {
        return pos;
    }

    public static ObservableList<Position> getPositions() {
        return positions;
    }
}
