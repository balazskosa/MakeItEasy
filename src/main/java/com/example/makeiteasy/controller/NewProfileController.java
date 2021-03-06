package com.example.makeiteasy.controller;

import com.example.makeiteasy.database.DB;
import com.example.makeiteasy.database.pojo.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * This Controller controls the New profile page.
 */
public class NewProfileController implements Initializable {

    /**
     * Stage extends Windows. This class-variable is needed for
     * opening and closing app-related windows.
     */
    public Stage stage;

    //Top buttons
    /**
     * This array contains the Buttons on the top of New Profile Page.
     */
    @FXML
    public Button[] topButtons;
    @FXML
    private Button nameButton;
    @FXML
    private Button weightButton;
    @FXML
    private Button genderButton;
    @FXML
    private Button birthButton;
    @FXML
    private Button heightButton;
    @FXML
    private Button activityButton;

    //Profile creation pages
    @FXML
    private Pane[] pages;
    @FXML
    private Pane namePage;
    @FXML
    private Pane weightPage;
    @FXML
    private Pane genderPage;
    @FXML
    private Pane birthPage;
    @FXML
    private Pane heightPage;
    @FXML
    private Pane activityPage;

    /**
     * This currentPage int value stores the actual page of the New Profile Screen.
     * This variable helps for hiding and showing elements on New Profile Screen.
     */
    public int currentPage;

    //Gender buttons
    @FXML
    private Button maleButton;
    @FXML
    private Button femaleButton;

    //Navigation buttons
    @FXML
    private Button nextArrow;
    @FXML
    private Button backArrow;
    @FXML
    private Button calculateButton;

    //Alert messages for data type checking
    @FXML
    private Text emptyFirstNameMsg;
    @FXML
    private Text emptyLastNameMsg;
    @FXML
    private Text positiveWeightMsg;
    @FXML
    private Text chooseOneGenderMsg;
    @FXML
    private Text positiveHeightMsg;
    @FXML
    private Text chooseOneActivityMsg;

    //Fields for get profile data
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField weight;
    private String gender;
    @FXML
    private DatePicker birthDate;
    @FXML
    private TextField height;
    @FXML
    private ToggleGroup activityRadioGroup;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pages = new Pane[]{namePage, weightPage, genderPage, birthPage, heightPage, activityPage};
        topButtons = new Button[]{nameButton, weightButton, genderButton, birthButton, heightButton, activityButton};
        currentPage = 0;
        birthDate.setValue(LocalDate.parse("2000-01-01"));
    }

    /**
     * This method called when the next arrow clicked on the New Profile Screen.
     * It hides and shows elements related to currentPage variable.
     */
    @FXML
    public EventHandler<ActionEvent> onNextArrowAction() {
        if (checkData(currentPage)) {
            ++currentPage;
            topButtons[currentPage].setStyle("-fx-background-color: #1b5359;" +
                    "-fx-text-fill: white");
            topButtons[currentPage - 1].setStyle("-fx-background-color: transparent;" +
                    "-fx-text-fill: #a3a3a3;");

            pages[currentPage].toFront();

            if (currentPage == pages.length - 1) {
                nextArrow.setVisible(false);
                calculateButton.setVisible(true);
            }


            pages[currentPage - 1].setVisible(false);
            pages[currentPage].setVisible(true);
            backArrow.setVisible(true);
        }
        return null;
    }

    /**
     * This method called when the back arrow clicked on the New Profile Screen.
     * It hides and shows elements related to currentPage variable.
     */
    @FXML
    public EventHandler<ActionEvent> onBackArrowAction() {
        --currentPage;
        topButtons[currentPage].setStyle("-fx-background-color: #1b5359;" +
                "-fx-text-fill: white;");
        topButtons[currentPage + 1].setStyle("-fx-background-color: transparent;" +
                "-fx-text-fill: #a3a3a3;");

        pages[currentPage].toFront();

        if (currentPage == 0)
            backArrow.setVisible(false);

        pages[currentPage + 1].setVisible(false);
        pages[currentPage].setVisible(true);
        nextArrow.setVisible(true);
        calculateButton.setVisible(false);
        return null;
    }

    @FXML
    private void setMaleGender() {
        gender = "Male";
        maleButton.setStyle("-fx-background-color: #74C2BA;");
        femaleButton.setStyle("-fx-background-color: #1b5359;");
    }

    @FXML
    private void setFemaleGender() {
        gender = "Female";
        femaleButton.setStyle("-fx-background-color: #74C2BA;");
        maleButton.setStyle("-fx-background-color: #1b5359;");
    }

    private boolean checkData(int currentPage) {
        boolean isCorrect = true;
        switch (currentPage) {
            case 0:
                if (firstName.getText().isBlank()) {
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
                if (!isNumeric(weight.getText()) || Integer.parseInt(weight.getText().trim()) < 1) {
                    positiveWeightMsg.setVisible(true);
                    isCorrect = false;
                } else {
                    positiveWeightMsg.setVisible(false);
                }
                break;
            case 2:
                if (gender == null) {
                    chooseOneGenderMsg.setVisible(true);
                    isCorrect = false;
                } else {
                    chooseOneGenderMsg.setVisible(false);
                }
                break;
            case 4:
                if (!isNumeric(height.getText()) || Integer.parseInt(height.getText().trim()) < 1) {
                    positiveHeightMsg.setVisible(true);
                    isCorrect = false;
                } else {
                    positiveHeightMsg.setVisible(false);
                }
                break;
            case 5:
                if (activityRadioGroup.getSelectedToggle() == null) {
                    chooseOneActivityMsg.setVisible(true);
                    isCorrect = false;
                } else {
                    chooseOneActivityMsg.setVisible(false);
                }
                break;
            default:

        }
        return isCorrect;
    }

    /**
     * Checks the String value from the method parameter to see if it
     * can be converted to numeric value.
     * @param string the String value to be checked
     * @return false if string cannot be converted, true if can be converted
     */
    public boolean isNumeric(String string) {
        if (string == null || string.equals(""))
            return false;

        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    private void calculateAndCreate() {
        if (checkData(currentPage)) {
            //int age = LocalDate.now().getYear() - birthDate.getValue().getYear();
            RadioButton selectedActivity = (RadioButton) activityRadioGroup.getSelectedToggle();

            User user = new User(
                    firstName.getText().trim(),
                    lastName.getText().trim(),
                    Integer.parseInt(weight.getText().trim()),
                    gender,
                    birthDate.getValue(),
                    Integer.parseInt(height.getText().trim()),
                    getActivityMultiplier(selectedActivity)
            );

            DB.addUser(user);
            //ProfileController.setUserLabels(user);
//            try {
//                replaceSceneContent();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            stage.close();
        }
    }

    /**
     * This method converts a chosen "radio-button" to doubles. Every "radio-button"
     * describes an activity level. This activity levels can be turned to doubles.
     * This conversion is based on researches.
     * @param selectedButton is the "radio-button" to convert based on it's text.
     * @return the activity multiplier double
     */
    public double getActivityMultiplier(RadioButton selectedButton) {
        String buttonText = selectedButton.getText().split(":")[0];
        return switch (buttonText) {
            case "Sedentary" -> 1.2;
            case "Lightly active" -> 1.375;
            case "Moderately active" -> 1.465;
            case "Active" -> 1.55;
            case "Very active" -> 1.725;
            case "Extra active" -> 1.9;
            default -> 1;
        };
    }

//    private void replaceSceneContent() throws IOException {
//        URL url = new File("src/main/resources/com/example/makeiteasy/View.fxml").toURI().toURL();
//        Parent page = FXMLLoader.load(url);
//        stage.getScene().setRoot(page);
//        stage.sizeToScene();
//        stage.centerOnScreen();
//    }

    /**
     * This method allows the app to set the main Stage for this class.
     * @param stage is the app's main stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
