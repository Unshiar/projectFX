package ch.sample.controller;

import ch.sample.model.Cartridge;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CartridgeController {

    private boolean resultPresents = false;
    private Cartridge cartridge;

    @FXML
    private TextField textField_CurrentNumber;

    @FXML
    private TextField textField_NewNumber;

    @FXML
    private TextField textField_CurrentModel;

    @FXML
    private TextField textField_NewModel;

    @FXML
    Button buttonSave;

    @FXML
    Button buttonCancel;

    public CartridgeController() {
        this.cartridge = new Cartridge();
    }

    public CartridgeController(Cartridge cartridge) {
        this.cartridge = cartridge;
    }

    public Cartridge getCartridge() {
        return cartridge;
    }

    public void setCartridge(Cartridge cartridge) {
        this.cartridge = cartridge;
    }

    public boolean isResultPresents() {
        return resultPresents;
    }

    @FXML
    private void initialize() {
        textField_CurrentNumber.setText(cartridge.getNumber());
        textField_CurrentModel.setText(cartridge.getModel());
    }

    @FXML
    private void handleSave() {
        cartridge.setNumber(textField_NewNumber.getText());
        cartridge.setModel(textField_NewModel.getText());
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
