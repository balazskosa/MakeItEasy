package com.example.makeiteasy.controller;

import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.pojo.Food;
import com.example.makeiteasy.database.pojo.Meal;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SummaryController implements Initializable {


    @FXML
    ListView<String> mealList;

    @FXML
    ListView<Food> foodList;

    @FXML
    ListView<Meal> foodIntakeList;

    @FXML
    PieChart nutrimentChart;

    @FXML
    Label test;

    @FXML
    TextField amount;

    private int foodId;
    private int whichMeal;
    private final LocalDate localDate = LocalDate.now();

    Map<String, Integer> mealTime = new HashMap<>();


    public void setWhichMeal() {
        mealTime.put("Breakfast", 1);
        mealTime.put("Lunch", 2);
        mealTime.put("Dinner", 3);
        mealTime.put("Snacks", 4);
    }

    public void addMeal() {
        int amount = Integer.parseInt(this.amount.getText());
        Meal meal = new Meal(foodId, Date.valueOf(localDate), whichMeal, amount);
        DB.addMeal(meal);
        System.out.println(meal.getId());
        this.amount.clear();
    }

    public void setMealList() {
        mealList.getItems().addAll("Breakfast", "Lunch", "Dinner", "Snacks");

        mealList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String currentMeal = mealList.getSelectionModel().getSelectedItem();
                whichMeal = mealTime.get(currentMeal);
            }
        });
    }

    public void setFoodList() {
        foodList.setItems(DB.getFoods());

        foodList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Food>() {
            @Override
            public void changed(ObservableValue<? extends Food> observableValue, Food food, Food t1) {
                foodId = foodList.getSelectionModel().getSelectedItem().getId();
            }
        });

    }

    public void setNutrimentChart() {
        int protein = 50;
        int carbs = 50;
        int fat = 50;
        PieChart.Data proteinData = new PieChart.Data("Protein", protein);
        PieChart.Data carbsData = new PieChart.Data("Carbs", carbs);
        PieChart.Data fatData = new PieChart.Data("Fat", fat);
        nutrimentChart.getData().add(proteinData);
        nutrimentChart.getData().add(carbsData);
        nutrimentChart.getData().add(fatData);
        //nutrimentChart.setLabelsVisible(false);
    }

    public void setFoodIntakeList() {
        foodIntakeList.setItems(DB.getMeals());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setWhichMeal();
        setFoodIntakeList();
        setMealList();
        setFoodList();
        setNutrimentChart();
    }
}
