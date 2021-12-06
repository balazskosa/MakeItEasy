package com.example.makeiteasy.database.pojo;

import com.example.makeiteasy.database.DB;
import javafx.beans.property.IntegerProperty;

import java.sql.Date;
public class Meal {

    private final Integer foodId;
    private final Date date;
    private final Integer whichMeal;
    private final Integer weight;


    public Meal(Integer foodId, Date date, Integer whichMeal, Integer weight) {
        this.foodId = foodId;
        this.date = date;
        this.whichMeal = whichMeal;
        this.weight = weight;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public Date getDate() {
        return date;
    }

    public Integer getWhichMeal() {
        return whichMeal;
    }

    public Integer getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return DB.getFoodNameByID(foodId) + " | " + weight;
    }
}
