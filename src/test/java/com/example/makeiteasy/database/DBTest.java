package com.example.makeiteasy.database;

import com.example.makeiteasy.database.pojo.Food;
import com.example.makeiteasy.database.pojo.Meal;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DBTest {

    @Test
    void addFood() {
        DB.clearFoodTable();
        String name_1 = "testFood_1";
        int calories_1 = 100;
        int protein_1 = 20;
        int carbs_1 = 30;
        int fat_1= 40;

        String name_2 = "testFood_2";
        int calories_2 = 500;
        int protein_2 = 50;
        int carbs_2 = 15;
        int fat_2= 10;

        Food food_1 = new Food(name_1, calories_1, protein_1, carbs_1, fat_1);
        Food food_2 = new Food(name_2, calories_2, protein_2, carbs_2, fat_2);
        DB.addFood(food_1);
        DB.addFood(food_2);
        ArrayList<Food> foods = DB.getAllFoods();

        assertNotNull(foods);
        assertEquals(foods.size(), 2);
    }

    @Test
    void updateFood() {
        DB.clearFoodTable();
        String name_1 = "testFood_1";
        int calories_1 = 100;
        int protein_1 = 20;
        int carbs_1 = 30;
        int fat_1= 40;

        Food toBeUpdatedFood = new Food(name_1, calories_1, protein_1, carbs_1, fat_1);
        DB.addFood(toBeUpdatedFood);

        String updatedFoodName = "updatedFoodName";
        int updatedCalories = 50000;
        int updatedProtein = 1000;
        int updatedCarbs = 1500;
        int updatedFat= 2000;

        toBeUpdatedFood.setName(updatedFoodName);
        toBeUpdatedFood.setCalories(updatedCalories);
        toBeUpdatedFood.setProtein(updatedProtein);
        toBeUpdatedFood.setCarbohydrate(updatedCarbs);
        toBeUpdatedFood.setFat(updatedFat);

        assertEquals(DB.getFoodByID(toBeUpdatedFood.getId()).getName(), "testFood_1");

        DB.updateFood(toBeUpdatedFood);

        assertNotEquals(DB.getFoodByID(toBeUpdatedFood.getId()).getName(), "testFood_1");
        assertEquals(DB.getFoodByID(toBeUpdatedFood.getId()).getName(), "updatedFoodName");

        assertNotEquals(DB.getFoodByID(toBeUpdatedFood.getId()).getCalories(), 100);
        assertEquals(DB.getFoodByID(toBeUpdatedFood.getId()).getCalories(), 50000);

        assertNotEquals(DB.getFoodByID(toBeUpdatedFood.getId()).getProtein(), 20);
        assertEquals(DB.getFoodByID(toBeUpdatedFood.getId()).getProtein(), 1000);

        assertNotEquals(DB.getFoodByID(toBeUpdatedFood.getId()).getCarbohydrate(), 30);
        assertEquals(DB.getFoodByID(toBeUpdatedFood.getId()).getCarbohydrate(), 1500);

        assertNotEquals(DB.getFoodByID(toBeUpdatedFood.getId()).getFat(), 40);
        assertEquals(DB.getFoodByID(toBeUpdatedFood.getId()).getFat(), 2000);
    }

    @Test
    void addMealAndDeleteMeal() {
        DB.clearFoodTable();
        String name_1 = "testFood_1";
        int calories_1 = 100;
        int protein_1 = 20;
        int carbs_1 = 30;
        int fat_1= 40;

        String name_2 = "testFood_2";
        int calories_2 = 500;
        int protein_2 = 50;
        int carbs_2 = 15;
        int fat_2= 10;

        Food food_1 = new Food(name_1, calories_1, protein_1, carbs_1, fat_1);
        Food food_2 = new Food(name_2, calories_2, protein_2, carbs_2, fat_2);
        DB.addFood(food_1);
        DB.addFood(food_2);

        LocalDate currentDay = LocalDate.now();

        Meal meal_1 = new Meal(food_1.getId(), Date.valueOf(currentDay), 1, 200);
        Meal meal_2 = new Meal(food_2.getId(), Date.valueOf(currentDay), 1, 200);
        DB.addMeal(meal_1);
        DB.addMeal(meal_2);

        ArrayList<Meal> meals = DB.getAllMeals();

        assertEquals(meals.size(), 2);
        assertEquals(meals.get(0).getFoodId(), food_1.getId());
        assertEquals(meals.get(1).getFoodId(), food_2.getId());

        DB.deleteMeal(meal_1);
        DB.deleteMeal(meal_2);

        assertEquals(DB.getAllMeals().size(), 0);
    }

    @Test
    void updateMeal() {
    }

    @Test
    void addUser() {
    }
}