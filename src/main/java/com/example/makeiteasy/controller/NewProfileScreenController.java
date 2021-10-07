package com.example.makeiteasy.controller;

import com.example.makeiteasy.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewProfileScreenController implements Initializable {

    @FXML private Button[] topButtons;
    @FXML private Button nameButton;
    @FXML private Button weightButton;
    @FXML private Button genderButton;
    @FXML private Button birthButton;
    @FXML private Button heightButton;
    @FXML private Button maleButton;
    @FXML private Button femaleButton;

    @FXML private Button nextArrow;
    @FXML private Button backArrow;

    @FXML private Pane[] pages;
    @FXML private Pane namePage;
    @FXML private Pane weightPage;
    @FXML private Pane genderPage;
    @FXML private Pane birthPage;
    @FXML private Pane heightPage;
    private int currentPage = -1;

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField weight;
    private int gender;
    @FXML private DatePicker birthDate;
    @FXML private TextField height;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pages = new Pane[]{namePage, weightPage, genderPage, birthPage, heightPage};
        topButtons = new Button[]{nameButton, weightButton, genderButton, birthButton, heightButton};
        currentPage = 0;
    }

    @FXML
    public void onNextArrowAction() {
        ++currentPage;
        topButtons[currentPage].setStyle("-fx-background-color: #1b5359;" +
                "-fx-text-fill: white");
        topButtons[currentPage-1].setStyle("-fx-background-color: transparent;" +
                "-fx-text-fill: #a3a3a3;");

        pages[currentPage].toFront();

        if (currentPage == pages.length-1)
            nextArrow.setVisible(false);

        pages[currentPage-1].setVisible(false);
        pages[currentPage].setVisible(true);
        backArrow.setVisible(true);
    }

    @FXML
    private void onBackArrowAction() {
        --currentPage;
        topButtons[currentPage].setStyle("-fx-background-color: #1b5359;" +
                "-fx-text-fill: white;");
        topButtons[currentPage+1].setStyle("-fx-background-color: transparent;" +
                "-fx-text-fill: #a3a3a3;");

        pages[currentPage].toFront();

        if(currentPage == 0)
            backArrow.setVisible(false);

        pages[currentPage+1].setVisible(false);
        pages[currentPage].setVisible(true);
        nextArrow.setVisible(true);
    }

    @FXML
    private void setMaleGender() {
        gender = 1;
        maleButton.setStyle("-fx-background-color: #74C2BA;");
        femaleButton.setStyle("-fx-background-color: #1b5359;");
    }

    @FXML
    private void setFemaleGender() {
        gender = 2;
        femaleButton.setStyle("-fx-background-color: #74C2BA;");
        maleButton.setStyle("-fx-background-color: #1b5359;");
    }

    @FXML
    private void createUser() {
        //Empty fields and string to int field are not checked yet, but with accurate data it works

        User user = new User (  firstName.getText(),
                                lastName.getText(),
                                Integer.parseInt(weight.getText()),
                                gender,
                                birthDate.getValue(),
                                Integer.parseInt(height.getText())
                                );

        //Printing the calorie result in User, it has a calculateCalories method (only prints on console)

        //TO-DO jump to View.fxml
        //TO-DO open NewProfile.fxml when click in View.fxml the profile menu
    }



}
