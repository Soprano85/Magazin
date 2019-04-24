package com.corporation.magazin.controllers;

import com.corporation.magazin.connection.Connector;
import com.corporation.magazin.models.ModelBuy;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerProdaja implements Initializable {
    Connector connector = new Connector();
    @FXML
    private TableView<ModelBuy> tableview;
    @FXML
    private TableColumn<ModelBuy, Integer> idC;
    @FXML
    private TableColumn<ModelBuy, Integer> id_tovarC;
    @FXML
    private TableColumn<ModelBuy, String> nameC;
    @FXML
    private TableColumn<ModelBuy, Double> countC;
    @FXML
    private TableColumn<ModelBuy, String> clientC;
    @FXML
    private TableColumn<ModelBuy, String> dateC;
    @FXML
    private TableColumn<ModelBuy, Integer> pricefullC;
    @FXML
    private ComboBox<String> comboboxtovar;
    @FXML
    private TextField client;
    @FXML
    private DatePicker date;
    @FXML
    private TextField countT;
    @FXML
    private Label itogoT;

    public ControllerProdaja() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }

    @FXML
    void summaAction(ActionEvent event) {
        try {
            int id = getIdByName(comboboxtovar.getValue());
            double kol = Double.parseDouble(countT.getText());
            double itogo = getItogoByIdTovarKol(id, kol);
            itogoT.setText(itogo + "");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception");
            alert.setContentText(e.getMessage());
        }
    }

    // метод для получения id товара по его названию (из БД)////////////////////////////////////////////////////////////
    int getIdByName(String name) throws SQLException {
        ResultSet resultSet = connector.executeQuery("select id from product where name='" + name + "'");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id"));
            return resultSet.getInt("id");
        }
        return 0;
    }

    double getItogoByIdTovarKol(int id_tovar, double count) throws SQLException {
        ResultSet resultSet = connector.executeQuery("select price from product where id='" + id_tovar + "'");
        while (resultSet.next()) {
            return resultSet.getDouble("price") * count;
        }
        System.out.println(resultSet);
        return 0;
    }

    public void addAction(ActionEvent actionEvent) {
        try {

            connector.executeUpdate("insert into prodaja(id_tovar,name,count,client,date,itogo) " +
                    "values('" +
                    getIdByName(comboboxtovar.getValue()) + "','" +
                    comboboxtovar.getValue() + "','" +
                    Double.parseDouble(countT.getText()) + "','" +
                    client.getText() + "','" +
                    date.getEditor().getText() + "','" +
                    Double.parseDouble(itogoT.getText()) + "')");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception");
            alert.setContentText(e.getMessage());
            alert.show();
        }
        update();
    }

    public void updateAction(ActionEvent actionEvent) {

        if (tableview.getSelectionModel().getSelectedItem() != null) {
            try {
                connector.executeUpdate("update prodaja " +
                        "  set id_tovar='" + getIdByName(comboboxtovar.getValue()) + "'," +
                        "  name='" + nameC.getText() + "'," +
                        "  count='" + Double.parseDouble(countC.getText()) + "'," +
                        "  client='" + client.getText() + "'," +
                        "  date= '" + date.getEditor().getText() + "'," +
                        "  itogo= '" + Double.parseDouble(itogoT.getText()) +
                        "  where id=" + tableview.getSelectionModel().getSelectedItem().getId());
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

    public void deleteAction(ActionEvent actionEvent) {
        if (tableview.getSelectionModel().getSelectedItem() != null) {
            try {
                connector.executeUpdate("delete from prodaja where id=" +
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


    public void update() {
           try {
               ArrayList<String> stringArrayList = new ArrayList<String>();
               ResultSet resultSetCombo = connector.executeQuery("select * from product");
               while (resultSetCombo.next()) {
                   stringArrayList.add(resultSetCombo.getString("name"));
               }
               ObservableList<String> observableListCombo = FXCollections.observableArrayList(stringArrayList);
               comboboxtovar.setItems(observableListCombo);

               ArrayList<ModelBuy> arrayList = new ArrayList<>();
               ResultSet resultSet = connector.executeQuery("select * from prodaja");
               while (resultSet.next()) {
                   arrayList.add(new ModelBuy(
                           resultSet.getInt("id"),
                           resultSet.getInt("id_tovar"),
                           resultSet.getString("name"),
                           resultSet.getDouble("count"),
                           resultSet.getString("client"),
                           resultSet.getString("date"),
                           resultSet.getDouble("itogo")));
               }
               ObservableList<ModelBuy> observableList = FXCollections.<ModelBuy>observableArrayList(arrayList);
               tableview.setItems(observableList);
               idC.setCellValueFactory(new PropertyValueFactory<ModelBuy, Integer>("id"));
               id_tovarC.setCellValueFactory(new PropertyValueFactory<ModelBuy, Integer>("id_tovar"));
               nameC.setCellValueFactory(new PropertyValueFactory<ModelBuy, String>("name"));
               countC.setCellValueFactory(new PropertyValueFactory<ModelBuy, Double>("count"));
               clientC.setCellValueFactory(new PropertyValueFactory<ModelBuy, String>("client"));
               dateC.setCellValueFactory(new PropertyValueFactory<ModelBuy, String>("data"));
               pricefullC.setCellValueFactory(new PropertyValueFactory<ModelBuy, Integer>("itogo"));
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
}

