package com.example.makeiteasy.controller;

import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.FoodDatabase;
import com.example.makeiteasy.database.pojo.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.util.ResourceBundle;

public class FoodController implements Initializable {

    @FXML
    private TableView<Food> table;

    @FXML
    private TableColumn<Food, Integer> carbCol;

    @FXML
    private TableColumn<Food, String> nameCol;

    @FXML
    private TableColumn<Food, Integer> proteinCol;

    @FXML
    private TableColumn<Food, Integer> fatCol;



    private final ObservableList<Food> data = FXCollections.observableArrayList();

    DB db = new DB();


    public void setTable() {

        nameCol.setCellValueFactory(new PropertyValueFactory<Food,String>("name"));
        proteinCol.setCellValueFactory(new PropertyValueFactory<Food,Integer>("protein"));
        carbCol.setCellValueFactory(new PropertyValueFactory<Food,Integer>("carbohydrate"));
        fatCol.setCellValueFactory(new PropertyValueFactory<Food,Integer>("fat"));

        table.setItems(data);

    }

    public void createData() {
        FoodDatabase fdb = new FoodDatabase(db.getConn(), db.getCreateStatement(), db.getDmbd());
        data.addAll(fdb.getAllFoods());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createData();
        setTable();
    }
}
