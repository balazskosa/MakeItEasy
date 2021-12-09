package com.example.makeiteasy.controller;

import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.pojo.Meal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ResultController implements Initializable {

    @FXML
    private BarChart<LocalDate, Number> dailyCalorieChart;
    @FXML
    private CategoryAxis calorieIntakeX;
    @FXML
    private NumberAxis calorieIntakeY;

    private LocalDate currentDay = LocalDate.now();
    private ArrayList<Meal> meals = DB.getAllMeals();

    @FXML
    private Label dayLabel;

    @FXML
    private void prevDay() {
        this.currentDay = this.currentDay.minusDays(1);
        setDayLabel();
    }

    @FXML
    private void resetDay() {
        this.currentDay = LocalDate.now();
        setDayLabel();
    }

    @FXML
    private void nextDay() {
        this.currentDay = this.currentDay.plusDays(1);
        setDayLabel();
    }

    public void setDayLabel() {
        dayLabel.setText(currentDay.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDayLabel();
    }
}
