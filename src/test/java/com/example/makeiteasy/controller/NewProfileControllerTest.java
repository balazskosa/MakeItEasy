package com.example.makeiteasy.controller;

import com.example.makeiteasy.MakeItEasy;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({ApplicationExtension.class, MockitoExtension.class})
class NewProfileControllerTest {

    NewProfileController controller;
    Button button;
    Scene scene;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MakeItEasy.class.getResource("NewProfile.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

        controller = fxmlLoader.getController();
        controller.setStage(stage);
    }

    @Test
    void initialize() {
        assertNotNull(controller);
        assertNotNull(controller.stage);
    }

    @Test
    void onNextArrowAction(FxRobot robot) {
        int beforeNextArrowActionPageNum = controller.currentPage;
        button = new Button("nextButtonReplacer");
        button.setOnAction(controller.onNextArrowAction());

        robot.clickOn(".button");
        assertEquals(beforeNextArrowActionPageNum, controller.currentPage);


    }

    @Test
    void isNumeric() {
        assertFalse(controller.isNumeric("ThisIsNotNumericString"));
        assertFalse(controller.isNumeric(""));
        assertFalse(controller.isNumeric("      "));
        assertTrue(controller.isNumeric("23"));
    }

    @Test
    void getActivityMultiplier() {
        RadioButton button = new RadioButton();
        button.setText("Sedentary: etc...");
        assertEquals(1.2, controller.getActivityMultiplier(button));

        button.setText("BLA:");
        assertEquals(1, controller.getActivityMultiplier(button));

        button.setText("ThereIsNoSeparator");
        assertEquals(1, controller.getActivityMultiplier(button));

        button.setText("");
        assertEquals(1, controller.getActivityMultiplier(button));
    }

    @Test
    void setStage() {
        assertNotNull(controller.stage);
    }
}