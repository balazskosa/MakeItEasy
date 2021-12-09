package com.example.makeiteasy.controller;

import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.Result;
import com.example.makeiteasy.database.pojo.Meal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ResultController implements Initializable {

    @FXML
    private BarChart<String, Number> dailyCalorieChart;
    @FXML
    private CategoryAxis calorieIntakeX;
    @FXML
    private NumberAxis calorieIntakeY;

    private LocalDate currentDay = LocalDate.now();
    private final ArrayList<Meal> meals = DB.getAllMeals();
    private ArrayList<Result> results = new ArrayList<>();
    @FXML
    private Label dayLabel;

    @FXML
    private void prevDay() {
        this.currentDay = this.currentDay.minusDays(1);
        setData();
    }

    @FXML
    private void resetDay() {
        this.currentDay = LocalDate.now();
        setData();
    }

    @FXML
    private void nextDay() {
        this.currentDay = this.currentDay.plusDays(1);
        setData();
    }

    public void setDayLabel() {
        dayLabel.setText(currentDay.toString());
    }

    public void setData() {
        setDayLabel();
        setResults();
        setDailyCalorieChart();
    }

    public void setDailyCalorieChart() {
        XYChart.Series dataSeries1 = new XYChart.Series();

        for (Result result : results) {
            dataSeries1.getData().add(new XYChart.Data(result.getDate().toString(), result.getCalories()));
            //System.out.println(result);
        }

        dailyCalorieChart.getData().clear();
        dailyCalorieChart.getData().add(dataSeries1);
    }

    public void setResults() {
        int index = 0;
        results.clear();
        for (int i = -3; i <= 3; i++) {
            results.add(new Result(currentDay.plusDays(i)));
            index++;
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dailyCalorieChart.setLegendVisible(false);
        setData();
    }
}
