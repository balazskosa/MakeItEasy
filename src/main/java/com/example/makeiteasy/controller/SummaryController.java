package com.example.makeiteasy.controller;


import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.pojo.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class SummaryController implements Initializable {

    @FXML
    ListView<Food> listView1;

    @FXML
    ListView<Food> listView2;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView1.setItems(DB.getFoods());
    }
}
