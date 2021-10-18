package com.example.makeiteasy.controller;

import com.example.makeiteasy.database.DB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class FoodController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DB db = new DB();
    }
}
