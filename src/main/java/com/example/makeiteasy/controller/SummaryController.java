package com.example.makeiteasy.controller;


import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.pojo.Food;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class SummaryController implements Initializable {

    @FXML
    ListView<String> mealList;

    @FXML
    ListView<Food> foodList;

    @FXML
    PieChart nutrimentChart;


    public void setMealList() {
        mealList.getItems().addAll("Breakfast", "Lunch", "Dinner", "Snacks");

        mealList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String currentMeal = mealList.getSelectionModel().getSelectedItem();
                System.out.println(currentMeal);
            }
        });
    }

    public void setFoodList() {
        foodList.setItems(DB.getFoods());

        foodList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Food>() {
            @Override
            public void changed(ObservableValue<? extends Food> observableValue, Food food, Food t1) {
                int foodId = foodList.getSelectionModel().getSelectedItem().getId();
                System.out.println(foodId);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMealList();
        setFoodList();
        setNutrimentChart();
    }
}
