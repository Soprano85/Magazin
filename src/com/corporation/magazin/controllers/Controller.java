package com.corporation.magazin.controllers;


import com.corporation.magazin.Main;
import com.corporation.magazin.connection.Connector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Controller() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void Categoria(ActionEvent actionEvent) {
        System.out.println("categoria");
        try {
            Stage stage = new Stage();
            stage.setTitle("Добавление категории");
            Pane pane = FXMLLoader.load(getClass().getResource("../views/categoria.fxml"));
            stage.setScene(new Scene(pane, 600, 400));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PodCategoria(ActionEvent actionEvent) {
        System.out.println("podcategoria");
        try {
            Stage stage = new Stage();
            stage.setTitle("Добавление подкатегории");
            Pane pane = FXMLLoader.load(getClass().getResource("../views/podcategory.fxml"));
            stage.setScene(new Scene(pane, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Goods(ActionEvent actionEvent) {
        System.out.println("goods");
        try {
            Stage stage = new Stage();
            stage.setTitle("Добавление подкатегории");
            Pane pane = FXMLLoader.load(getClass().getResource("../views/product.fxml"));
            stage.setScene(new Scene(pane, 700, 550));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buyGoods(ActionEvent actionEvent) {
        System.out.println("buygoods");
        try {
            Stage stage = new Stage();
            stage.setTitle("Добавление подкатегории");
            Pane pane = FXMLLoader.load(getClass().getResource("../views/prodaja.fxml"));
            stage.setScene(new Scene(pane, 760, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
