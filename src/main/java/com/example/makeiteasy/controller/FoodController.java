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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.*;
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

    @FXML
    private TextField inputSearchFood;

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
    private void searchFood(ActionEvent event) {
        DB.searchFoodByName(inputSearchFood.getText());
    }

    @FXML
    private void resetSearchFood(ActionEvent event) {
        DB.resetSearchFood();
        inputSearchFood.clear();
    }

    @FXML
    private void clearDatabaseButton(ActionEvent event) {
        DB.clearFoodTable();
    }

    @FXML
    private void loadDatabaseButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null)  loadDatabase(selectedFile);
    }

    private void loadDatabase(File file) {
        DB.clearFoodTable();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("Something wrong with the reading file");
            System.out.println("" + e);
        }
        assert fileReader != null;
        BufferedReader reader = new BufferedReader(fileReader);
        try {
            String line;
            String name;
            int calories;
            int protein;
            int carbs;
            int fat;
            String[] array = new String[5];
            Food newFood;
            while((line = reader.readLine()) != null) {
                array = line.split("-");
                name = array[0];
                calories = Integer.parseInt(array[1]);
                protein = Integer.parseInt(array[2]);
                carbs = Integer.parseInt(array[3]);
                fat = Integer.parseInt(array[4]);
                newFood = new Food(name, calories, protein, carbs, fat);
                DB.addFood(newFood);
            }
        } catch (IOException e) {
            System.out.println("Something wrong with the data");
            System.out.println("" + e);
        }

    }

    @FXML
    private void clearInputButton(ActionEvent event) {
        for (TextField input : inputs) {
            input.clear();
        }
        DB.getAllFoodsWithMeta();
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
