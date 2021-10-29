package com.example.makeiteasy.database.pojo;

import javafx.beans.property.IntegerProperty;

import java.sql.Date;
public class Meal {

    private Integer userId = 0;
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

    public Meal(Integer userId, Integer foodId, Date date, Integer whichMeal, Integer weight) {
        this.userId = userId;
        this.foodId = foodId;
        this.date = date;
        this.whichMeal = whichMeal;
        this.weight = weight;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        return "Meals{" +
                "userId=" + userId +
                ", foodId=" + foodId +
                ", date=" + date +
                ", whichMeal=" + whichMeal +
                ", weight=" + weight +
                '}';
    }
}
