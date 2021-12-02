package com.example.makeiteasy.database.pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Food {

    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty calories;
    private final IntegerProperty protein;
    private final IntegerProperty carbohydrate;
    private final IntegerProperty fat;

    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty proteinProperty() {
        return protein;
    }

    public IntegerProperty carbohydrateProperty() {
        return carbohydrate;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate.set(carbohydrate);
    }

    public IntegerProperty fatProperty() {
        return fat;
    }

    public Food(String name, int calories, int protein, int carbohydrate, int fat) {
        this.calories = new SimpleIntegerProperty(calories);
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty(name);
        this.protein = new SimpleIntegerProperty(protein);
        this.carbohydrate = new SimpleIntegerProperty(carbohydrate);
        this.fat = new SimpleIntegerProperty(fat);
    }

    public Food(int id, String name, int calories, int protein, int carbohydrate, int fat) {
        this.calories = new SimpleIntegerProperty(calories);
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.protein = new SimpleIntegerProperty(protein);
        this.carbohydrate = new SimpleIntegerProperty(carbohydrate);
        this.fat = new SimpleIntegerProperty(fat);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getProtein() {
        return protein.get();
    }

    public void setProtein(int protein) {
        this.protein.set(protein);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getCarbohydrate() {
        return carbohydrate.get();
    }

    public int getFat() {
        return fat.get();
    }

    public void setFat(int fat) {
        this.fat.set(fat);
    }

    public int getCalories() {
        return calories.get();
    }

    public IntegerProperty caloriesProperty() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories.set(calories);
    }


    @Override
    public String toString() {
        return id.getValue() + " - " + name.getValue() + " - " + calories.getValue();
    }
}
