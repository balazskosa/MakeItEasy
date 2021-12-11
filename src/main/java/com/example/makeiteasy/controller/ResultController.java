package com.example.makeiteasy.controller;

import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.Result;
import com.example.makeiteasy.database.pojo.Meal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
/**
 * Implementing all methods associated with result page
 */
public class ResultController implements Initializable {

    @FXML
    private BarChart<String, Number> dailyCalorieChart;

    @FXML
    private StackedBarChart<String, Number> dailyNutritionChart;

    @FXML
    private NumberAxis nutrientY;

    private LocalDate currentDay = LocalDate.now();
    private final ArrayList<Meal> meals = DB.getAllMeals();
    private ArrayList<Result> results = new ArrayList<>();
    @FXML
    private Label dayLabel;

    /**
     * Set the previous day
     */
    @FXML
    private void prevDay() {
        this.currentDay = this.currentDay.minusDays(1);
        setData();
    }
    /**
     * Reset date
     */
    @FXML
    private void resetDay() {
        this.currentDay = LocalDate.now();
        setData();
    }
    /**
     * Set the next day
     */
    @FXML
    private void nextDay() {
        this.currentDay = this.currentDay.plusDays(1);
        setData();
    }

    /**
     * Displaying the day
     */
    public void setDayLabel() {
        dayLabel.setText(currentDay.toString());
    }

    /**
     * Set Day, Charts and calculating nutriment values to days
     */
    public void setData() {
        setDayLabel();
        setResults();
        setDailyCalorieChart();
    }

    /**
     * Set Daily Calorie Intake chart and Daily Nutriment Intake chart
     */
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

        int protein;
        int carbs;
        int fat;
        int max = 0;
        int sum;
        for (Result result : results) {
            date = outputFormat.format(Date.valueOf(result.getDate()));
            dataSeries1.getData().add(new XYChart.Data(date, result.getCalories()));
            dataSeries2.getData().add(new XYChart.Data(date, DB.user().dailyCaloriesForMaintain));

            protein = result.getProtein();
            carbs = result.getCarbs();
            fat = result.getFat();
            sum = protein + carbs + fat;
            if (sum > max) max = sum;

            dataSeries3.getData().add(new XYChart.Data(date, protein));
            dataSeries4.getData().add(new XYChart.Data(date, carbs));
            dataSeries5.getData().add(new XYChart.Data(date, fat));
        }

        dailyCalorieChart.getData().clear();
        dailyCalorieChart.getData().add(dataSeries1);
        dailyCalorieChart.getData().add(dataSeries2);
        int upperBound = (max + 99) / 100 * 100;
        nutrientY.setUpperBound(upperBound);
        nutrientY.setTickUnit((double) upperBound / 5);
        dailyNutritionChart.getData().clear();
        dailyNutritionChart.getData().addAll(dataSeries3, dataSeries4, dataSeries5);


    }

    /**
     * calculating nutriment values to days (only 7 days)
     */
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
