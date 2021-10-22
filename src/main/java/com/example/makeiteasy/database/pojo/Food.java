package com.example.makeiteasy.database.pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Food {

    private final StringProperty name;
    private final IntegerProperty protein;
    private final IntegerProperty carbohydrate;
    private final IntegerProperty fat;

    public Food(String name, int protein, int carbohydrate, int fat) {
        this.name = new SimpleStringProperty(name);
        this.protein = new SimpleIntegerProperty(protein);
        this.carbohydrate = new SimpleIntegerProperty(carbohydrate);
        this.fat = new SimpleIntegerProperty(carbohydrate);
    }


    public int getProtein() {
        return protein.get();
    }

    public IntegerProperty proteinProperty() {
        return protein;
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

    public IntegerProperty carbohydrateProperty() {
        return carbohydrate;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate.set(carbohydrate);
    }

    public int getFat() {
        return fat.get();
    }

    public IntegerProperty fatProperty() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat.set(fat);
    }


}
