package com.example.makeiteasy.database;

import com.example.makeiteasy.database.pojo.Food;
import com.example.makeiteasy.database.pojo.Meal;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class helps to link the food and meal table to process the data
 */
public class Result {
    private LocalDate date;
    private Integer calories = 0;
    private Integer protein = 0;
    private Integer carbs = 0;
    private Integer fat = 0;

    private ArrayList<Meal> meals = DB.getAllMeals();

    /**
     * Collects and determines all nutriment values for a given day
     * @param date Date
     */
    public Result(LocalDate date) {
        this.date = date;
        meals.removeIf(m -> !(m.getDate().toLocalDate().equals(date)));
        for (Meal meal : meals) {
            Food actualFood = DB.getFoodByID(meal.getFoodId());
            this.calories = this.calories + (int) ((double) actualFood.getCalories() * ((double) meal.getWeight() / 100));
            this.protein = this.protein + (int) ((double) actualFood.getProtein() * ((double) meal.getWeight() / 100));
            this.carbs = this.carbs + (int) ((double) actualFood.getCarbohydrate() * ((double) meal.getWeight() / 100));
            this.fat = this.fat + (int) ((double) actualFood.getFat() * ((double) meal.getWeight() / 100));
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getProtein() {
        return protein;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public Integer getCarbs() {
        return carbs;
    }

    public void setCarbs(Integer carbs) {
        this.carbs = carbs;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }


    @Override
    public String toString() {
        return "Result{" +
                "date=" + date +
                ", calories=" + calories +
                ", protein=" + protein +
                ", carbs=" + carbs +
                ", fat=" + fat +
                '}';
    }
}
