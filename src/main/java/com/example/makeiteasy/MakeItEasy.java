package com.example.makeiteasy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MakeItEasy extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MakeItEasy.class.getResource("View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 580);
        stage.setTitle("MakeItEasy");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}