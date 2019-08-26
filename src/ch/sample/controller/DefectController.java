package ch.sample.controller;

import ch.sample.model.Defect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DefectController {

    private boolean resultPresents = false;
    private Defect defect;

    @FXML
    TextField textField_CurrentName;

    @FXML
    TextField textField_NewName;

    @FXML
    Button buttonSave;

    @FXML
    Button buttonCancel;

    public DefectController() {
        this.defect = new Defect();
    }

    public DefectController(Defect defect) {
        this.defect = defect;
    }

    public Defect getDefect() {
        return defect;
    }

    public void setDefect(Defect defect) {
        this.defect = defect;
    }

    public boolean isResultPresents() {
        return resultPresents;
    }

    @FXML
    private void initialize() {
        textField_CurrentName.setText(defect.getName());
    }

    @FXML
    private void handleSave() {
        defect.setName(textField_NewName.getText());
        resultPresents = true;
        Stage stage = (Stage)buttonSave.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage)buttonCancel.getScene().getWindow();
        stage.close();
    }
}
