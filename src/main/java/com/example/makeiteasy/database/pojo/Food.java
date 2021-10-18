package com.example.makeiteasy.database.pojo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Food {

    private final SimpleStringProperty name;
    private final SimpleIntegerProperty protein;
    private final SimpleIntegerProperty carbohydrate;
    private final SimpleIntegerProperty fat;


    public Food(String name, int protein, int carbohydrate, int fat) {
        this.name = new SimpleStringProperty(name);
        this.protein = new SimpleIntegerProperty(protein);
        this.carbohydrate = new SimpleIntegerProperty(carbohydrate);
        this.fat = new SimpleIntegerProperty(fat);
    }


    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getProtein() {
        return protein.get();
    }

    public void setProtein(int protein) {
        this.protein.set(protein);
    }

    public int getCarbohydrate() {
        return carbohydrate.get();
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate.set(carbohydrate);
    }

    public int getFat() {
        return fat.get();
    }

    public void setFat(int fat) {
        this.fat.set(fat);
    }
}
