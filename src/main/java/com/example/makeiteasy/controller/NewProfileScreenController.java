package com.example.makeiteasy.controller;

import com.example.makeiteasy.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
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

    @FXML private Text emptyFirstNameMsg;
    @FXML private Text emptyLastNameMsg;
    @FXML private Text positiveWeightMsg;
    @FXML private Text chooseOneMsg;
    @FXML private Text positiveHeightMsg;

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
    private int gender = -1;
    @FXML private DatePicker birthDate;
    @FXML private TextField height;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pages = new Pane[]{namePage, weightPage, genderPage, birthPage, heightPage};
        topButtons = new Button[]{nameButton, weightButton, genderButton, birthButton, heightButton};
        currentPage = 0;
        birthDate.setValue(LocalDate.parse("2000-01-01"));
    }

    @FXML
    public void onNextArrowAction() {
        if(checkDataIsCorrect(currentPage)) {
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
        if(checkDataIsCorrect(currentPage)) {
            User user = new User (  firstName.getText().trim(),
                    lastName.getText().trim(),
                    Integer.parseInt(weight.getText().trim()),
                    gender,
                    birthDate.getValue(),
                    Integer.parseInt(height.getText().trim())
            );


            try {
                replaceSceneContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Printing the calorie result in User, it has a calculateCalories method (only prints on console)

        //TO-DO jump to View.fxml
        //TO-DO open NewProfile.fxml when click in View.fxml the profile menu
    }

    private boolean checkDataIsCorrect(int currentPage) {
        boolean isCorrect = true;
        switch(currentPage) {
            case 0:
                if(firstName.getText().isBlank()) {
                    emptyFirstNameMsg.setVisible(true);
                    isCorrect = false;
                } else {
                    emptyFirstNameMsg.setVisible(false);
                }

                if (lastName.getText().isBlank()) {
                    emptyLastNameMsg.setVisible(true);
                    isCorrect = false;
                } else {
                    emptyLastNameMsg.setVisible(false);
                }
                break;
            case 1:
                if(!isNumeric(weight.getText()) || Integer.parseInt(weight.getText().trim()) < 1) {
                    positiveWeightMsg.setVisible(true);
                    isCorrect = false;
                } else {
                    positiveWeightMsg.setVisible(false);
                }
                break;
            case 2:
                if(gender == -1) {
                    chooseOneMsg.setVisible(true);
                    isCorrect = false;
                } else {
                    chooseOneMsg.setVisible(false);
                }
                break;
            case 4:
                if(!isNumeric(height.getText()) || Integer.parseInt(height.getText().trim()) < 1) {
                    positiveHeightMsg.setVisible(true);
                    isCorrect = false;
                } else {
                    positiveHeightMsg.setVisible(false);
                }
                break;
        }
        return isCorrect;
    }

    private boolean isNumeric(String string) {
        int intValue;

        if(string == null || string.equals(""))
            return false;

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void replaceSceneContent() throws IOException {
        URL url = new File("src/main/resources/com/example/makeiteasy/View.fxml").toURI().toURL();
        Parent page = FXMLLoader.load(url);
        stage.getScene().setRoot(page);
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
