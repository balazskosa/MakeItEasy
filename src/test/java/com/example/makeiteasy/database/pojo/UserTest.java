package com.example.makeiteasy.database.pojo;

import javafx.scene.control.DatePicker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {
    User user;

    @BeforeEach
    void setUp() {
        DatePicker birthDate = new DatePicker();
        birthDate.setValue(LocalDate.parse("2000-01-01"));
        user = new User("Pista", "Kis", 80, "Male", birthDate.getValue(), 180, 1.55);
    }

    @Test
    void calculateCalories() {
        assertNotNull(user);
        assertEquals(user.dailyCaloriesForMaintain, 2828);
        assertEquals(user.dailyCaloriesForMildLoss, 2578);
        assertEquals(user.dailyCaloriesForLoss, 2328);
    }
}