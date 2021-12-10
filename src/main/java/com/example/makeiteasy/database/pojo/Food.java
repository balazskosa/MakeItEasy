package com.example.makeiteasy.database.pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Food Class: Simple Class to represent all values to create a food object
 */
public class Food {

    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty calories;
    private final IntegerProperty protein;
    private final IntegerProperty carbohydrate;
    private final IntegerProperty fat;

    /**
     * Returns the id of the object.
     * @return the id of object
     */
    public IntegerProperty idProperty() {
        return id;
    }

    /**
     * Returns the amount of protein pro 100 grams.
     * @return the amount of protein
     */
    public IntegerProperty proteinProperty() {
        return protein;
    }

    /**
     * Returns the amount of carbohydrate pro 100 grams.
     * @return the amount of carbohydrate
     */
    public IntegerProperty carbohydrateProperty() {
        return carbohydrate;
    }

    /**
     * Set the amount of carbohydrate of the object
     * @param carbohydrate the amount of carbohydrate
     */
    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate.set(carbohydrate);
    }

    /**
     * Returns the amount of fat pro 100 grams.
     * @return the amount of fat
     */
    public IntegerProperty fatProperty() {
        return fat;
    }

    /**
     *
     * @param name the name of the object
     * @param calories the calories of the obbject
     * @param protein the amount of protein
     * @param carbohydrate the amount of carbs
     * @param fat the amount of fat
     */
    public Food(String name, int calories, int protein, int carbohydrate, int fat) {
        this.calories = new SimpleIntegerProperty(calories);
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty(name);
        this.protein = new SimpleIntegerProperty(protein);
        this.carbohydrate = new SimpleIntegerProperty(carbohydrate);
        this.fat = new SimpleIntegerProperty(fat);
    }

    /**
     *
     * @param id the id of the object
     * @param name the name of the object
     * @param calories the calories of the obbject
     * @param protein the amount of protein
     * @param carbohydrate he amount of carbs
     * @param fat the amount of fat
     */
    public Food(int id, String name, int calories, int protein, int carbohydrate, int fat) {
        this.calories = new SimpleIntegerProperty(calories);
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.protein = new SimpleIntegerProperty(protein);
        this.carbohydrate = new SimpleIntegerProperty(carbohydrate);
        this.fat = new SimpleIntegerProperty(fat);
    }

    /**
     * Return the if of the object
     * @return the unique id of the object
     */
    public int getId() {
        return id.get();
    }

    /**
     *  Set id of the object
     * @param id the unique id of the object
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * The amount of protein pro 100 grams.
     * @return the amount of protein
     */
    public int getProtein() {
        return protein.get();
    }

    /**
     * Set the amount of the protein pr 100 grams.
     * @param protein the amount of protein
     */
    public void setProtein(int protein) {
        this.protein.set(protein);
    }

    /**
     * Return the name of the object.
     * @return the name of the object
     */
    public String getName() {
        return name.get();
    }

    /**
     * Return the name of the object
     * @return the name of the object
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Set the name of the object
     * @param name the name of the object
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Return the amount of carbohydrate pro 100 grams.
     * @return the amount of carbohydrate
     */
    public int getCarbohydrate() {
        return carbohydrate.get();
    }

    /**
     * Return the amount of fat pro 100 grams.
     * @return the amount of fat
     */
    public int getFat() {
        return fat.get();
    }

    /**
     * Set the amount of fat pro 100 grams.
     * @param fat the amount of fat
     */
    public void setFat(int fat) {
        this.fat.set(fat);
    }

    /**
     * Return the calories of the object pro 100 grams.
     * @return the calories of the object
     */
    public int getCalories() {
        return calories.get();
    }

    /**
     *Return the calories of the object pro 100 grams.
     * @return the calories of the object
     */
    public IntegerProperty caloriesProperty() {
        return calories;
    }

    /**
     * Set the calories of the object pro 100 grams.
     * @param calories the calories of the object
     */
    public void setCalories(int calories) {
        this.calories.set(calories);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return name.getValue() + " - " + calories.getValue();
    }
}
