package com.example.makeiteasy;

import java.time.LocalDate;
import java.util.Date;

public class User {

    public String firstName;
    public String lastName;
    public int weight;
    public int gender;
    public int age;
    public int height;
    public int dailyCalories;
    public LocalDate birthday;

    public User(String firstName, String lastName, int weight,
                int gender, LocalDate birthDay, int height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.weight = weight;
        this.gender = gender;
        this.height = height;
        this.birthday = birthDay;

        age = LocalDate.now().getYear() - birthDay.getYear();
        calculateCalories();
    }

    public void calculateCalories() {
        // Miffin-St Jeor Equation (probably the most accurate)
        // Male
        if(gender == 1)
            dailyCalories = (int) (10 * weight + 6.25 * height - 5 * age + 5);
        else
            dailyCalories = (int) (10 * weight + 6.25 * height - 5 * age - 161);

        System.out.println(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", weight=" + weight +
                ", gender=" + gender +
                ", age=" + age +
                ", height=" + height +
                ", dailyCalories=" + dailyCalories +
                '}';
    }
}
