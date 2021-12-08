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
import java.util.stream.Collectors;

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
    TextField amount;

    @FXML
    TextField changeAmount;

    @FXML
    Label dayLabel;

    private int foodId;
    private int whichMeal;
    private Meal selectedMeal;

    private final LocalDate localDate = LocalDate.now();

    LocalDate currentDay = LocalDate.now();

    Map<String, Integer> mealTime = new HashMap<>();

    @FXML
    public void  changeIntakeFood() {

    }

    @FXML
    public void delIntakeFood() {
        if(selectedMeal != null) {
            DB.deleteMealById(selectedMeal);
        }
    }

    @FXML
    public void prevDay() {
        this.currentDay = this.currentDay.minusDays(1);
        setDayLabel();
        DB.searchMealsByWhichMeal(whichMeal, currentDay);

    }

    @FXML
    public void nextDay() {
        this.currentDay = this.currentDay.plusDays(1);
        DB.searchMealsByWhichMeal(whichMeal, currentDay);
        setDayLabel();
    }


    @FXML
    public void resetDay() {
        this.currentDay = LocalDate.now();
        DB.searchMealsByWhichMeal(whichMeal, currentDay);
        setDayLabel();

    }

    public void setWhichMeal() {
        mealTime.put("Breakfast", 1);
        mealTime.put("Lunch", 2);
        mealTime.put("Dinner", 3);
        mealTime.put("Snacks", 4);
    }

    public void setDayLabel() {
        dayLabel.setText(currentDay.toString());
    }

    public void addMeal() {
        int amount = Integer.parseInt(this.amount.getText());
        Meal meal = new Meal(foodId, Date.valueOf(localDate), whichMeal, amount);
        DB.addMeal(meal);
        this.amount.clear();
    }

    public void setMealList() {
        mealList.getItems().addAll("Breakfast", "Lunch", "Dinner", "Snacks");

        //set part of the day
        int partOfTheDay = 1;
        whichMeal = partOfTheDay;
        mealList.getSelectionModel().select(partOfTheDay-1);
        DB.searchMealsByWhichMeal(partOfTheDay, currentDay);

        mealList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String currentMeal = mealList.getSelectionModel().getSelectedItem();
                whichMeal = mealTime.get(currentMeal);
                DB.searchMealsByWhichMeal(whichMeal, currentDay);
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

    public void setFoodIntakeList() {
        foodIntakeList.setItems(DB.getMeals());

        foodIntakeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal>() {
            @Override
            public void changed(ObservableValue<? extends Meal> observableValue, Meal meal, Meal t1) {
                Meal tmp = foodIntakeList.getSelectionModel().getSelectedItem();
                if(tmp!= null) {
                    selectedMeal = tmp;
                } else {
                    selectedMeal = null;
                }
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
        setDayLabel();
        setFoodIntakeList();
        setWhichMeal();
        setMealList();
        setFoodList();
        setNutrimentChart();
    }
}
