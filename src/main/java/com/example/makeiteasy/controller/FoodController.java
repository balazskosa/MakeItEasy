package com.example.makeiteasy.controller;

import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.pojo.Food;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FoodController implements Initializable {

    //<editor-fold desc="object variables">
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

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputProtein;

    @FXML
    private TextField inputCarbs;

    @FXML
    private TextField inputFat;

    private final List<TextField> inputs = new ArrayList<>();

    //</editor-fold">

    @FXML
    private void addButton(ActionEvent event) {
        String name = inputName.getText();
        int protein = Integer.parseInt(inputProtein.getText());
        int carbs = Integer.parseInt(inputCarbs.getText());
        int fat = Integer.parseInt(inputFat.getText());
        Food food = new Food(name, protein, carbs, fat);
        DB.addFood(food);
        setTable();
    }

    @FXML
    private void clearButton(ActionEvent event) {
        for (TextField input : inputs) {
            input.clear();
        }
    }

    public void setTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<Food, String>("name"));
        proteinCol.setCellValueFactory(new PropertyValueFactory<Food, Integer>("protein"));
        carbCol.setCellValueFactory(new PropertyValueFactory<Food, Integer>("carbohydrate"));
        fatCol.setCellValueFactory(new PropertyValueFactory<Food, Integer>("fat"));

        table.setItems(DB.getFoods());

    }

    public void setInputs() {
        inputs.add(inputName);
        inputs.add(inputProtein);
        inputs.add(inputCarbs);
        inputs.add(inputFat);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInputs();
        setTable();
    }
}
