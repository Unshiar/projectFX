package ch.sample.controller;

import ch.sample.model.Cartridge;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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

    }
}
