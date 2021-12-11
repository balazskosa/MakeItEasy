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

/**
 * Implementing all methods associated with food page
 */

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

    private final List<TextField> inputs = new ArrayList<>();

    private final Integer MIN_NUTRIMENT_VALUE = 0;
    private final Integer MAX_NUTRIMENT_VALUE = 100;
    //</editor-fold">


    /**
     * Validation of the number
     * @param number
     */
    private void checkNumber(int number) {
        if (number < MIN_NUTRIMENT_VALUE || number > MAX_NUTRIMENT_VALUE) {
            throw new NumberFormatException();
        }
    }

    /**
     * Add food to the DB
     * Validation the input fields with visual feedback
     * @param event
     */
    @FXML
    private void addButton(ActionEvent event) {
        String name = inputName.getText();

        int calories = 0;
        int protein = 0;
        int carbs = 0;
        int fat = 0;
        boolean wrongValue = false;

        try {
            calories = Integer.parseInt(inputCalories.getText());
            if (calories < 0 || calories > MAX_NUTRIMENT_VALUE * 10) throw new NumberFormatException();
            inputCalories.setStyle("-fx-background-color: white;");
        } catch (NumberFormatException e) {
            inputCalories.setStyle("-fx-background-color: #d98f80;");
            wrongValue = true;
        }
        try {
            protein = Integer.parseInt(inputProtein.getText());
            checkNumber(protein);
            inputProtein.setStyle("-fx-background-color: white;");
        } catch (NumberFormatException e) {
            inputProtein.setStyle("-fx-background-color: #d98f80;");
            wrongValue = true;
        }

        try {
            carbs = Integer.parseInt(inputCarbs.getText());
            checkNumber(carbs);
            inputCarbs.setStyle("-fx-background-color: white;");
        } catch (NumberFormatException e) {
            inputCarbs.setStyle("-fx-background-color: #d98f80;");
            wrongValue = true;
        }

        try {
            fat = Integer.parseInt(inputFat.getText());
            checkNumber(fat);
            inputFat.setStyle("-fx-background-color: white;");
        } catch (NumberFormatException e) {
            inputFat.setStyle("-fx-background-color: #d98f80;");
            wrongValue = true;
        }

        if (wrongValue) return;

        Food food = new Food(name, calories, protein, carbs, fat);
        DB.addFood(food);
        setTable();
        clearInputButton();

    }

    /**
     * Searching food by name
     * @param event
     */
    @FXML
    private void searchFood(ActionEvent event) {
        DB.searchFoodByName(inputSearchFood.getText());
    }

    /**
     * Show all food, reset searching food by name
     * @param event
     */
    @FXML
    private void resetSearchFood(ActionEvent event) {
        DB.resetSearchFood();
        inputSearchFood.clear();
    }

    /**
     * Clear food and meal table
     * @param event
     */
    @FXML
    private void clearDatabaseButton(ActionEvent event) {
        DB.clearFoodTable();
    }

    /**
     * Select the right location of file in another window
     * And call loadDatabase method to set up the food table
     * @param event
     */
    @FXML
    private void loadDatabaseButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) loadDatabase(selectedFile);
    }

    /**
     * Load all food based on the specified file
     * @param file File
     */
    private void loadDatabase(File file) {
        DB.clearFoodTable();
        InputStream inputStream = null;
        Reader fileReader = null;
        try {
            inputStream = new FileInputStream(file);
            fileReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (fileReader == null) {
            return;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(fileReader);

            String line;
            String name;
            int calories;
            int protein;
            int carbs;
            int fat;
            String[] array;
            Food newFood;
            while ((line = reader.readLine()) != null) {

                array = line.split("-");
                if (array.length != 5) throw new ArrayIndexOutOfBoundsException();
                name = array[0];
                calories = Integer.parseInt(array[1]);
                protein = Integer.parseInt(array[2]);
                carbs = Integer.parseInt(array[3]);
                fat = Integer.parseInt(array[4]);
                newFood = new Food(name, calories, protein, carbs, fat);
                DB.addFood(newFood);
            }
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Something wrong with the data");
            System.out.println("" + e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Reset all input fields
     */
    @FXML
    private void clearInputButton() {
        for (TextField input : inputs) {
            input.setStyle("-fx-background-color: white;");
            input.clear();
        }
    }

    /**
     * Set food table and made editable cells
     */
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

    /**
     *  Adding input fields to an arraylist for easier handling
     */
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
