package com.example.makeiteasy.controller;

import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.Result;
import com.example.makeiteasy.database.pojo.Meal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ResultController implements Initializable {

    @FXML
    private BarChart<String, Number> dailyCalorieChart;

    @FXML
    private StackedBarChart<String, Number> dailyNutritionChart;

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
        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries1.setName("consumed Calories");
        dataSeries2.setName("maximum Calories");

        XYChart.Series dataSeries3 = new XYChart.Series();
        XYChart.Series dataSeries4 = new XYChart.Series();
        XYChart.Series dataSeries5 = new XYChart.Series();
        dataSeries3.setName("Protein");
        dataSeries4.setName("Carbs");
        dataSeries5.setName("fat");

        SimpleDateFormat outputFormat = new SimpleDateFormat("MM.dd");
        String date;

        for (Result result : results) {
            date = outputFormat.format(Date.valueOf(result.getDate()));
            dataSeries1.getData().add(new XYChart.Data(date, result.getCalories()));
            dataSeries2.getData().add(new XYChart.Data(date, DB.user().dailyCaloriesForMaintain));

            dataSeries3.getData().add(new XYChart.Data(date, result.getProtein()));
            dataSeries4.getData().add(new XYChart.Data(date, result.getCarbs()));
            dataSeries5.getData().add(new XYChart.Data(date, result.getFat()));
        }

        dailyCalorieChart.getData().clear();
        dailyCalorieChart.getData().add(dataSeries1);
        dailyCalorieChart.getData().add(dataSeries2);

        dailyNutritionChart.getData().clear();
        dailyNutritionChart.getData().addAll(dataSeries3, dataSeries4, dataSeries5);
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
        dailyCalorieChart.setLegendVisible(true);
        setData();
    }
}
