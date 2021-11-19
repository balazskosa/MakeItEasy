package com.example.makeiteasy.controller;

import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.pojo.Food;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

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
    private TableColumn<Food, Integer> caloriesCol;

    @FXML
    private TableColumn<Food, Integer> proteinCol;

    @FXML
    private TableColumn<Food, Integer> fatCol;

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputCalories;

    @FXML
    private TextField inputProtein;

    @FXML
    private TextField inputCarbs;

    @FXML
    private TextField inputFat;

    private final List<TextField> inputs = new ArrayList<>(

    );

    //</editor-fold">

    @FXML
    private void addButton(ActionEvent event) {
        String name = inputName.getText();
        int calories = Integer.parseInt(inputCalories.getText());
        int protein = Integer.parseInt(inputProtein.getText());
        int carbs = Integer.parseInt(inputCarbs.getText());
        int fat = Integer.parseInt(inputFat.getText());
        Food food = new Food(name, calories, protein, carbs, fat);
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
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        caloriesCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        proteinCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        carbCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        fatCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        nameCol.setCellValueFactory(new PropertyValueFactory<Food, String>("name"));
        caloriesCol.setCellValueFactory(new PropertyValueFactory<Food, Integer>("calories"));
        proteinCol.setCellValueFactory(new PropertyValueFactory<Food, Integer>("protein"));
        carbCol.setCellValueFactory(new PropertyValueFactory<Food, Integer>("carbohydrate"));
        fatCol.setCellValueFactory(new PropertyValueFactory<Food, Integer>("fat"));

        nameCol.setOnEditCommit(
                t -> {
                    Food actualFood = (Food) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actualFood.setName(t.getNewValue());
                    DB.updateFood(actualFood);
                }
        );

        caloriesCol.setOnEditCommit(
                t -> {
                    Food actualFood = (Food) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actualFood.setCalories(t.getNewValue());
                    DB.updateFood(actualFood);
                }
        );

       proteinCol.setOnEditCommit(
                t -> {
                    Food actualFood = (Food) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actualFood.setProtein(t.getNewValue());
                    DB.updateFood(actualFood);
                }
        );

        carbCol.setOnEditCommit(
                t -> {
                    Food actualFood = (Food) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actualFood.setCarbohydrate(t.getNewValue());
                    DB.updateFood(actualFood);
                }
        );

        fatCol.setOnEditCommit(
                t -> {
                    Food actualFood = (Food) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actualFood.setFat(t.getNewValue());
                    DB.updateFood(actualFood);
                }
        );

        table.setItems(DB.getFoods());

    }

    public void setInputs() {
        inputs.add(inputName);
        inputs.add(inputCalories);
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
