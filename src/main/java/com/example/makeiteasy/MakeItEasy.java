package com.example.makeiteasy;

import com.example.makeiteasy.controller.NewProfileController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MakeItEasy extends Application {

    public NewProfileController newProfileController;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader;

        // write 1 instead of 0 to work without the new profile fxml
        // later we will get the user id from database

        if(getCurrentUserID() == 0)
            fxmlLoader = new FXMLLoader(MakeItEasy.class.getResource("NewProfile.fxml"));
        else
            fxmlLoader = new FXMLLoader(MakeItEasy.class.getResource("View.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MakeItEasy");
        stage.setResizable(false);
        stage.setScene(scene);

        newProfileController = fxmlLoader.getController();
        newProfileController.setStage(stage);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private int getCurrentUserID() {
        int userID = 0;
        // Get user information (e.g. id) from database...
        return userID;
    }
}