package app.controllers;

import app.ConnectDataBase;
import app.objects.Position;
import app.objects.Service;
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

public class ChangeServicesController implements Controller {

    @FXML
    public TextField fieldServices;
    @FXML
    public TextField fieldSearchServices;

    @FXML
    public TableView<Service> tableServices;
    @FXML
    public TableColumn<Service, String> columnNameServices;

    @FXML
    public Button buttonChange;

    private static String callerClass = "";

    Service service;

    @FXML
    private void initialize() {
        columnNameServices.setCellValueFactory(cellDate -> cellDate.getValue().nameProperty());

        ObservableList<Service> services = FXCollections.observableArrayList();

        try {
            ResultSet resultSet = ConnectDataBase.query("SELECT t.* FROM SERVICE t;");

            while (resultSet.next()) {
                services.add(new Service(
                        resultSet.getInt("ID_SERVICE"),
                        resultSet.getString("NAME")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableServices.setItems(services);


        FilteredList<Service> filteredData = new FilteredList<>(services, p -> true);

        fieldSearchServices.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate(position -> {


                if (newValue == null || newValue.isEmpty()) return true;

                String loserCase = newValue.toLowerCase();

                if (position.getName().toLowerCase().contains(loserCase))
                    return true;
                return false;
            });
        });

        SortedList<Service> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableServices.comparatorProperty());
        tableServices.setItems(sortedData);

        tableServices.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                service = tableServices.getSelectionModel().getSelectedItem();

                if (service == null) return;

                fieldServices.setText(service.getName());

                System.out.println(tableServices.getSelectionModel().getSelectedItem().getName());
            }
        });

        buttonChange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (service == null) return;
                if (callerClass.equals("ChangePersonController")) {

                } else if (callerClass.equals("AddPersonController")) {
                    System.out.println(service.getName() + " cahnge");
                    AddPersonController.getWorker().setService(service);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Успешно изменено");
                    alert.showAndWait();
                }

            }
        });
    }

    @Override
    public void setMain(Stage Main) {

    }

    public static void setCallerClass(String callerClass) {
        ChangeServicesController.callerClass = callerClass;
    }
}
