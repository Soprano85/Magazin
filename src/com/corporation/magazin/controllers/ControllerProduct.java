package com.corporation.magazin.controllers;

import com.corporation.magazin.Main;
import com.corporation.magazin.connection.Connector;
import com.corporation.magazin.models.ModelProduct;
import com.sun.javafx.sg.prism.NGShape;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerProduct implements Initializable {
    Connector connector = new Connector();
    @FXML
    private TableView<ModelProduct> tableview;
    @FXML
    private TableColumn<ModelProduct, Integer> idcolum;
    @FXML
    private TableColumn<ModelProduct, String> namecolum;
    @FXML
    private TableColumn<ModelProduct, String> descriptioncolumn;
    @FXML
    private TableColumn<ModelProduct, Double> pricacolumn;
    @FXML
    private TableColumn<ModelProduct, Double> stockcolumn;
    @FXML
    private TableColumn<ModelProduct, Integer> categorycolumn;
    @FXML
    private TextField nameT;
    @FXML
    private TextField descriptionT;
    @FXML
    private TextField priceT;
    @FXML
    private TextField countT;
    @FXML
    private TextField searchT;
    @FXML
    private Slider priceprice;
    @FXML
    private ComboBox<String> podcategoryComboBox;
    @FXML
    private ComboBox<String> comboSelect;

    public ControllerProduct() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
       // sort();
    }

    public int getIdByName(String name) throws SQLException {
        ResultSet resultSet = connector.executeQuery("select id from podcategory where name='" + name + "'");
        while (resultSet.next()) {
            return resultSet.getInt("id");
        }
       // System.out.println(resultSet);
        return 0;
    }

    public void add(ActionEvent actionEvent) {
        try {
            connector.executeUpdate("insert into product(name,description,price,count,id_podcategory) " +
                    "values('" +
                    nameT.getText() + "','" +
                    descriptionT.getText() + "','" +
                    Double.parseDouble(priceT.getText()) + "','" +
                    Double.parseDouble(countT.getText()) + "','" +
                    getIdByName(podcategoryComboBox.getValue()) + "')");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception");
            alert.setContentText(e.getMessage());
            alert.show();
        }
        update();
    }

    public void delete(ActionEvent actionEvent) {
        if (tableview.getSelectionModel().getSelectedItem() != null) {
            try {
                connector.executeUpdate("delete from product where id=" +
                        tableview.getSelectionModel().getSelectedItem().getId());
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

    public void corect(ActionEvent actionEvent) {
        if (tableview.getSelectionModel().getSelectedItem() != null) {
            try {
                connector.executeUpdate(
                        "update product " +
                                "  set name='" + nameT.getText() + "'," +
                                "  description='" + descriptionT.getText() + "'," +
                                "  price='" + Double.parseDouble(priceT.getText()) + "'," +
                                "  count=" + Double.parseDouble(countT.getText()) + "," +
                                "  id_podcategory=" + getIdByName(podcategoryComboBox.getValue()) +
                                " where id=" + tableview.getSelectionModel().getSelectedItem().getId());
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

    public void update() {
        try {
            ArrayList<String> stringArrayList = new ArrayList<String>();
            ResultSet resultSetCombo = connector.executeQuery("select * from podcategory");
            while (resultSetCombo.next()) {
                stringArrayList.add(resultSetCombo.getString("name"));
            }
            ObservableList<String> observableListCombo = FXCollections.observableArrayList(stringArrayList);
            podcategoryComboBox.setItems(observableListCombo);

            ArrayList<ModelProduct> arrayList = new ArrayList<>();
            ResultSet resultSet = connector.executeQuery("select * from product");
            while (resultSet.next()) {
                arrayList.add(new ModelProduct(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("count"),
                        resultSet.getInt("id_podcategory")));
            }
            ObservableList<ModelProduct> observableList = FXCollections.<ModelProduct>observableArrayList(arrayList);
            tableview.setItems(observableList);
            idcolum.setCellValueFactory(new PropertyValueFactory<ModelProduct, Integer>("id"));
            namecolum.setCellValueFactory(new PropertyValueFactory<ModelProduct, String>("name"));
            descriptioncolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, String>("description"));
            pricacolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, Double>("price"));
            stockcolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, Double>("count"));
            categorycolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, Integer>("id_podcategory"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void search(ActionEvent actionEvent) {
        //tableView
        ArrayList<ModelProduct> arrayList = new ArrayList<ModelProduct>();
        ResultSet resultSet = null;
        try {
            resultSet = connector.executeQuery("select * from product where " +
                    "id             like  '%" + searchT.getText() + "%' or " +
                    "name           like  '%" + searchT.getText() + "%' or " +
                    "description       like  '%" + searchT.getText() + "%' or " +
                    "price           like  '%" + searchT.getText() + "%' or " +
                    "count     like  '%" + searchT.getText() + "%' or " +
                    "id_podcategory like  '%" + searchT.getText() + "%'");

            while (resultSet.next()) {
                arrayList.add(new ModelProduct(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("count"),
                        resultSet.getInt("id_podcategory")));
            }
            ObservableList<ModelProduct> observableList = (FXCollections.<ModelProduct>observableArrayList(arrayList));
            tableview.setItems(observableList);
            //Columns
            tableview.setItems(observableList);
            namecolum.setCellValueFactory(new PropertyValueFactory<ModelProduct, String>("name"));
            idcolum.setCellValueFactory(new PropertyValueFactory<ModelProduct, Integer>("id"));
            descriptioncolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, String>("description"));
            pricacolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, Double>("price"));
            stockcolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, Double>("count"));
            categorycolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, Integer>("id_podcategory"));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void filter(ActionEvent actionEvent) {
        ArrayList<ModelProduct> arrayList = new ArrayList<ModelProduct>();
        ResultSet resultSet = null;
        try {
            resultSet = connector.executeQuery("select * from product where " + "price < " + priceprice.getValue());
            while (resultSet.next()) {
                arrayList.add(new ModelProduct(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("count"),
                        resultSet.getInt("id_podcategory")));
            }
            ObservableList<ModelProduct> observableList = (FXCollections.<ModelProduct>observableArrayList(arrayList));
            tableview.setItems(observableList);
            //Columns
            tableview.setItems(observableList);
            namecolum.setCellValueFactory(new PropertyValueFactory<ModelProduct, String>("name"));
            idcolum.setCellValueFactory(new PropertyValueFactory<ModelProduct, Integer>("id"));
            descriptioncolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, String>("description"));
            pricacolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, Double>("price"));
            stockcolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, Double>("count"));
            categorycolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, Integer>("id_podcategory"));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


    
    public void sort() {
        try {
            ArrayList<String> stringArrayList = new ArrayList<>();
            ResultSet resultSetCombo = connector.executeQuery("select * from podcategory");
            while (resultSetCombo.next()) {
                stringArrayList.add(resultSetCombo.getString("name"));
            }
            ObservableList<String> observableListCombo = FXCollections.observableArrayList(stringArrayList);
            comboSelect.setItems(observableListCombo);

            ArrayList<ModelProduct> arrayList = new ArrayList<>();
            ResultSet resultSet = connector.executeQuery("select * from product where id_podcategory ='"+ getIdByName(comboSelect.getValue()) + "'");
            while (resultSet.next()) {
                arrayList.add(new ModelProduct(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("count"),
                        resultSet.getInt("id_podcategory")));
            }
            ObservableList<ModelProduct> observableList = FXCollections.<ModelProduct>observableArrayList(arrayList);
            tableview.setItems(observableList);
            idcolum.setCellValueFactory(new PropertyValueFactory<ModelProduct, Integer>("id"));
            namecolum.setCellValueFactory(new PropertyValueFactory<ModelProduct, String>("name"));
            descriptioncolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, String>("description"));
            pricacolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, Double>("price"));
            stockcolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, Double>("count"));
            categorycolumn.setCellValueFactory(new PropertyValueFactory<ModelProduct, Integer>("id_podcategory"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void select(ActionEvent actionEvent) {
        sort();
    }

    public void selectAll(ActionEvent actionEvent) {
        update();
    }
}
