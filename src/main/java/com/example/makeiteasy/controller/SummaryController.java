package com.example.makeiteasy.controller;

import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.Result;
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
    TextField amount;

    @FXML
    TextField changeAmount;

    @FXML
    Label dayLabel;

    @FXML
    private Label maxCal;

    @FXML
    private Label consumedCal;


    private Integer foodId = null;
    private int whichMeal;
    private Meal selectedMeal;


    LocalDate currentDay = LocalDate.now();

    Map<String, Integer> mealTime = new HashMap<>();

    @FXML
    public void changeIntakeFood() {
        int amount;
        try {
            amount = Integer.parseInt(changeAmount.getText());
        } catch (Exception e) {
            this.changeAmount.setStyle("-fx-background-color: #d98f80;");
            return;
        }
        if (selectedMeal != null) {
            DB.updateMeal(selectedMeal, amount);
            changeAmount.clear();

        }
        setCaloriesValues();
        this.changeAmount.setStyle("-fx-background-color: white;");

    }

    public void clearTextFields() {
        amount.clear();
        amount.setStyle("-fx-background-color: white;");
        changeAmount.clear();
        changeAmount.setStyle("-fx-background-color: white;");
    }

    @FXML
    public void delIntakeFood() {
        if (selectedMeal != null) {
            DB.deleteMeal(selectedMeal);
            setCaloriesValues();
        }

    }

    @FXML
    public void prevDay() {
        this.currentDay = this.currentDay.minusDays(1);
        setDayLabel();
        DB.searchMealsByWhichMeal(whichMeal, currentDay);
        setCaloriesValues();
        clearTextFields();

    }

    @FXML
    public void nextDay() {
        this.currentDay = this.currentDay.plusDays(1);
        DB.searchMealsByWhichMeal(whichMeal, currentDay);
        setDayLabel();
        setCaloriesValues();
        clearTextFields();
    }


    @FXML
    public void resetDay() {
        this.currentDay = LocalDate.now();
        DB.searchMealsByWhichMeal(whichMeal, currentDay);
        setDayLabel();
        setCaloriesValues();
        clearTextFields();

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
        int amount;
//        if(selectedFood == null) return;
        try {
            amount = Integer.parseInt(this.amount.getText());
        } catch (Exception e) {
            this.amount.setStyle("-fx-background-color: #d98f80;");
            System.out.println("Wrong number");
            return;
        }

        Meal meal = new Meal(foodId, Date.valueOf(currentDay), whichMeal, amount);
        DB.addMeal(meal);
        this.amount.clear();
        setCaloriesValues();
        this.amount.setStyle("-fx-background-color: white;");
    }

    public void setMealList() {
        mealList.getItems().addAll("Breakfast", "Lunch", "Dinner", "Snacks");

        //set part of the day
        int partOfTheDay = 1;
        whichMeal = partOfTheDay;
        mealList.getSelectionModel().select(partOfTheDay - 1);
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
                Food tmp = foodList.getSelectionModel().getSelectedItem();
                if(tmp != null) {
                    foodId = tmp.getId();
                } else {
                   foodId = null;
                }
            }
        });

    }

    public void setFoodIntakeList() {
        foodIntakeList.setItems(DB.getMeals());

        foodIntakeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal>() {
            @Override
            public void changed(ObservableValue<? extends Meal> observableValue, Meal meal, Meal t1) {
                Meal tmp = foodIntakeList.getSelectionModel().getSelectedItem();
                if (tmp != null) {
                    selectedMeal = tmp;
                } else {
                    selectedMeal = null;
                }
            }
        });
    }

    public void setNutrimentChart(Result result) {
        PieChart.Data proteinData = new PieChart.Data("Protein", result.getProtein());
        PieChart.Data carbsData = new PieChart.Data("Carbs", result.getCarbs());
        PieChart.Data fatData = new PieChart.Data("Fat", result.getFat());
        nutrimentChart.getData().clear();
        nutrimentChart.getData().add(proteinData);
        nutrimentChart.getData().add(carbsData);
        nutrimentChart.getData().add(fatData);
        if (result.getCalories() == 0) {
            nutrimentChart.setLabelsVisible(false);
        } else {
            nutrimentChart.setLabelsVisible(true);
        }
    }

    public void setCaloriesValues() {
        Result result = new Result(currentDay);
        consumedCal.setText(String.valueOf(result.getCalories()));
        setNutrimentChart(result);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maxCal.setText("/" + DB.user().dailyCaloriesForMaintain);
        nutrimentChart.setLegendVisible(false);
        setCaloriesValues();
        setDayLabel();
        setFoodIntakeList();
        setWhichMeal();
        setMealList();
        setFoodList();

    }
}
