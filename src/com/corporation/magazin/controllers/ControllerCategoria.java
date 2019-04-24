package com.corporation.magazin.controllers;

import com.corporation.magazin.connection.Connector;
import com.corporation.magazin.models.ModelCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerCategoria implements Initializable {

    Connector connector = new Connector();

    @FXML
    private TableView<ModelCategory> CategoriaTable;

    @FXML
    private TableColumn<ModelCategory, Integer> idColumn;

    @FXML
    private TableColumn<ModelCategory, String> nameColumn;

    @FXML
    private TextField nameT;

    public ControllerCategoria() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }

    @FXML
    public void add(ActionEvent event) {
        try {
            connector.executeUpdate("insert into category(name)" + "values('" + nameT.getText() + "')");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception");
            alert.setContentText(e.getMessage());
            alert.show();
        }
        update();
    }

    @FXML
    public void corect(ActionEvent actionEvent) {
        if (CategoriaTable.getSelectionModel().getSelectedItem() != null) {
            System.out.println(CategoriaTable.getSelectionModel().getSelectedItem().getId());
            try {
                connector.executeUpdate(
                        "update category " +
                                "  set name='" + nameT.getText() + "'" +
                                " where id=" + CategoriaTable.getSelectionModel().getSelectedItem().getId());
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
        if (CategoriaTable.getSelectionModel().getSelectedItem() != null) {
                   try {
                       connector.executeUpdate("delete from category where id=" +
                               CategoriaTable.getSelectionModel().getSelectedItem().getId());
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

    public void back(ActionEvent actionEvent) {
    }

    //обнавление таблицы
    public void update() {
        try {
            //tableView
            ArrayList<ModelCategory> arrayList = new ArrayList<ModelCategory>();
            ResultSet resultSet = connector.executeQuery("select * from category");
            while (resultSet.next()) {
                arrayList.add(new ModelCategory(
                        resultSet.getInt("id"),
                        resultSet.getString("name")));
            }
            System.out.println(arrayList);
            //Columns
            ObservableList<ModelCategory> observableList = (FXCollections.<ModelCategory>observableArrayList(arrayList));
            CategoriaTable.setItems(observableList);
            nameColumn.setCellValueFactory(new PropertyValueFactory<ModelCategory, String>("name"));
            idColumn.setCellValueFactory(new PropertyValueFactory<ModelCategory, Integer>("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
