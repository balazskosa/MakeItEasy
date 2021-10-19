package com.example.makeiteasy.controller;

import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.FoodDatabase;
import com.example.makeiteasy.database.pojo.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class FoodController implements Initializable {

    private final ObservableList<Food> data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DB db = new DB();
        FoodDatabase fdb = new FoodDatabase(db.getConn(), db.getCreateStatement(), db.getDmbd());
    }
}
