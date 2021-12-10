package com.example.makeiteasy;

import com.example.makeiteasy.database.DB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class ViewController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    Label welcomeText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            welcomeText.setText("'Aim for Progress Not Perfection'\nWelcome " + DB.user.firstName + "!");
    }

    @FXML
    private void summary() {
        loadPane("Summary");
    }

    @FXML
    private void result() {
        loadPane("Result");
    }

    @FXML
    private void profile() {
        loadPane("Profile");
    }

    @FXML
    private void foods() {
        loadPane("Food");
    }

    @FXML
    private void exit() {
           Stage stage = (Stage) borderPane.getScene().getWindow();
           stage.close();
    }

    private void loadPane(String fileName) {
        Parent root = null;
        try {
            root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource(fileName + ".fxml"))));
        } catch (IOException e) {
            System.out.println("" + e);
        }
        borderPane.setCenter(root);

    }


}