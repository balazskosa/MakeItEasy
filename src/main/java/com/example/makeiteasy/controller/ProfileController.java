package com.example.makeiteasy.controller;

import com.example.makeiteasy.MakeItEasy;
import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.pojo.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ProfileController implements Initializable {

    @FXML Label firstName;
    @FXML Label lastName;
    @FXML Label weight;
    @FXML Label gender;
    @FXML Label age;
    @FXML Label height;
    @FXML Label activityMultiplier;
    @FXML Label dailyCaloriesForMaintain;
    @FXML Label dailyCaloriesForMildLoss;
    @FXML Label dailyCaloriesForLoss;
    @FXML Label birthday;

    public NewProfileController newProfileController;
    FXMLLoader fxmlLoader;
    Stage stage = new Stage();
    Scene scene = null;

    public void openNewProfileDialog() {
        fxmlLoader = new FXMLLoader(MakeItEasy.class.getResource("NewProfile.fxml"));
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        newProfileController = fxmlLoader.getController();
        newProfileController.setStage(stage);
        stage.show();
    }

    public void refreshUserData() {
        setUserLabels(DB.user());
    }

    public void setUserLabels(User user) {
        firstName.setText(user.firstName);
        lastName.setText(user.lastName);
        weight.setText(String.valueOf(user.weight));
        gender.setText(user.gender);
        age.setText(String.valueOf(user.age));
        height.setText(String.valueOf(user.height));
        activityMultiplier.setText(String.valueOf(user.activityMultiplier));
        dailyCaloriesForMaintain.setText(String.valueOf(user.dailyCaloriesForMaintain));
        dailyCaloriesForMildLoss.setText(String.valueOf(user.dailyCaloriesForMildLoss));
        dailyCaloriesForLoss.setText(String.valueOf(user.dailyCaloriesForLoss));
        birthday.setText(user.birthday.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage.setResizable(false);
        setUserLabels(DB.user());
    }
}
