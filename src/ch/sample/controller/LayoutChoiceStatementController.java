package ch.sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LayoutChoiceStatementController {
    @FXML
    private Button buttonToDirector;

    @FXML
    private Button buttonToAccountant;

    public LayoutChoiceStatementController() {
    }

    @FXML
    public void handleToDirector() {
        buttonToDirector.setText("1");
    }

    @FXML
    public void handleToAccountant() {
        buttonToAccountant.setText("2");
    }
}
