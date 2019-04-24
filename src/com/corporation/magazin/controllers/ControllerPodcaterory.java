package com.corporation.magazin.controllers;

import com.corporation.magazin.connection.Connector;
import com.corporation.magazin.models.ModelPodcategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerPodcaterory implements Initializable {
    Connector connector = new Connector();
    @FXML
    private TableView<ModelPodcategory> PodcategoriaTable;
    @FXML
    private TableColumn<ModelPodcategory, Integer> idColumn;
    @FXML
    private TableColumn<ModelPodcategory, String> nameColumn;
    @FXML
    private TableColumn<ModelPodcategory, Integer> podcatColumn;
    @FXML
    private ComboBox<String> idcombo;
    @FXML
    private TextField nameT;

    public ControllerPodcaterory() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();

    }

    int getIdByName(String name) throws SQLException {
        ResultSet resultSet = connector.executeQuery("select id from category where name='" + name + "'");
        while (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return 0;
    }

    public void control() throws SQLException {
        String a = idcombo.getValue();
        System.out.println(a);
        int f = getIdByName(idcombo.getValue());
        System.out.println(f);
    }

    public void add(ActionEvent actionEvent) {
        try {
            connector.executeUpdate("insert into podcategory(name,id_category) " +
                    "values('" + nameT.getText() + "','" + getIdByName(idcombo.getValue()) + "')");
            control();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception");
            alert.setContentText(e.getMessage());
            alert.show();
        }
        update();
    }

    public void corect(ActionEvent actionEvent) {
        if (PodcategoriaTable.getSelectionModel().getSelectedItem() != null) {
            try {
                connector.executeUpdate("update podcategory " +
                        "  set name='" + nameT.getText() + "'," +
                        "  id_category=" + getIdByName(idcombo.getValue()) +
                        " where id=" + PodcategoriaTable.getSelectionModel().getSelectedItem().getId());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Exception");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("INFORMATION");
            alert.setContentText("Выберите строку которую хотите изменить");
            alert.show();
        }
        update();
    }

    public void delete(ActionEvent actionEvent) {
        if (PodcategoriaTable.getSelectionModel().getSelectedItem() != null) {
            try {
                connector.executeUpdate("delete from podcategory where id=" +
                        PodcategoriaTable.getSelectionModel().getSelectedItem().getId());
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("SQLException");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("INFORMATION");
            alert.setContentText("Выберите строку которую хотите удалить");
            alert.show();
        }
        update();

    }

    void update() {
        try {
            //Combobox
            ArrayList<String> stringArrayList = new ArrayList<String>();
            ResultSet resultSetCombo = connector.executeQuery("select * from category");
            while (resultSetCombo.next()) {
                stringArrayList.add(resultSetCombo.getString("name"));
            }
            ObservableList<String> observableListCombo = FXCollections.observableArrayList(stringArrayList);
            idcombo.setItems(observableListCombo);

            //tableView
            ArrayList<ModelPodcategory> arrayList = new ArrayList<ModelPodcategory>();
            ResultSet resultSet = connector.executeQuery("select * from podcategory");
            while (resultSet.next()) {
                arrayList.add(new ModelPodcategory(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("id_category")));
            }

            //Columns
            ObservableList<ModelPodcategory> observableList = (FXCollections.<ModelPodcategory>observableArrayList(arrayList));
            PodcategoriaTable.setItems(observableList);
            nameColumn.setCellValueFactory(new PropertyValueFactory<ModelPodcategory, String>("name"));
            idColumn.setCellValueFactory(new PropertyValueFactory<ModelPodcategory, Integer>("id"));
            podcatColumn.setCellValueFactory(new PropertyValueFactory<ModelPodcategory, Integer>("id_category"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
