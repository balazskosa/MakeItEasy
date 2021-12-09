package com.example.makeiteasy.controller;

import com.example.makeiteasy.MakeItEasy;
import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.pojo.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class ProfileController implements Initializable {
    public NewProfileController newProfileController;
    FXMLLoader fxmlLoader;
    Stage stage = new Stage();
    Scene scene = null;
    public void openNewProfileDialog()  {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage.setResizable(false);
    }
}
