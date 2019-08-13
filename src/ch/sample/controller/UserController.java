package ch.sample.controller;

import ch.sample.model.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserController {

    @FXML
    private TextField textField_CurrentUserName;

    @FXML
    private TextField textField_NewUserName;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonSave;

    private UserModel user;
    private boolean resultPresents = false;

    @FXML
    private void initialize() {
        textField_CurrentUserName.setText(user.getUserName());
    }

    @FXML
    //Сохраняем изменения
    private void handleSave() {
        user.setUserName(textField_NewUserName.getText());
        resultPresents = true;
        Stage stage = (Stage)buttonSave.getScene().getWindow();
        stage.close();
    }

    @FXML
    //Закрываем окно без изменений
    private void handleCancel() {
        Stage stage = (Stage)buttonCancel.getScene().getWindow();
        stage.close();
    }

    public UserController() {
        user = new UserModel();
    }

    public UserController(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public boolean isResultPresents() {
        return resultPresents;
    }
}
