package com.example.makeiteasy;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;


public class ViewController {

    @FXML
    private Pane summaryPane;

    @FXML
    private Pane profilePane;

    @FXML
    private Pane resultPane;

    @FXML
    private Pane foodsPane;



    @FXML
    protected void summary() {setPanes(summaryPane);};
    @FXML
    protected void result() {setPanes(resultPane);}
    @FXML
    protected void profile() {setPanes(profilePane);}
    @FXML
    protected void foods() {setPanes(foodsPane);}
    @FXML
    protected void exit() {
        System.exit(0);
    }

    private void setPanes(Pane actualPane) {
        summaryPane.setDisable(true);
        summaryPane.setVisible(false);

        profilePane.setDisable(true);
        profilePane.setVisible(false);

        resultPane.setDisable(true);
        resultPane.setVisible(false);

        foodsPane.setDisable(true);
        foodsPane.setVisible(false);

        actualPane.setDisable(false);
        actualPane.setVisible(true);

    }
}