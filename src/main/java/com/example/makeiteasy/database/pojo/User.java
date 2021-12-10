package com.example.makeiteasy.database.pojo;

import java.time.LocalDate;

public class User {

    public String firstName;
    public String lastName;
    public int weight;
    public String gender;
    public int age;
    public int height;
    public double activityMultiplier;
    public int dailyCaloriesForMaintain;
    public int dailyCaloriesForMildLoss;
    public int dailyCaloriesForLoss;
    public LocalDate birthday;

    public User(String firstName, String lastName, int weight,
                String gender, LocalDate birthDay, int height, double activityMultiplier) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.weight = weight;
        this.gender = gender;
        this.height = height;
        this.birthday = birthDay;
        this.activityMultiplier = activityMultiplier;

        age = LocalDate.now().getYear() - birthDay.getYear();
        calculateCalories();
    }

    private void calculateCalories() {
        // Algorithms from calculator.net/calorie-calculator

        // Miffin-St Jeor Equation (probably the most accurate BMR algorithm without body fat %)
        if(gender.equals("Male"))
            dailyCaloriesForMaintain = (int) ((10 * weight + 6.25 * height - 5 * age + 5) * activityMultiplier);
        else
            dailyCaloriesForMaintain = (int) ((10 * weight + 6.25 * height - 5 * age - 161) * activityMultiplier);

        //approximately -0.25 kg / week
        dailyCaloriesForMildLoss = dailyCaloriesForMaintain - 250;

        //approximately -0.5kg / week
        dailyCaloriesForLoss = dailyCaloriesForMaintain - 500;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", weight=" + weight +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", activityMultiplier=" + activityMultiplier +
                ",\ndailyCaloriesForMaintain=" + dailyCaloriesForMaintain +
                ", dailyCaloriesForMildLoss(approx. -0.25kg/week)=" + dailyCaloriesForMildLoss +
                ", dailyCaloriesForLoss(approx. -0.5kg/week)=" + dailyCaloriesForLoss +
                '}';
    }
}
