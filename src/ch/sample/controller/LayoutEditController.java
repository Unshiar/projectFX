package ch.sample.controller;

import ch.sample.Main;
import ch.sample.model.UserModel;
import ch.sample.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LayoutEditController {


    @FXML
    private Label labelText;

    @FXML
    private Button buttonAddUser;//кнопка добавить сотрудника

    @FXML
    private Button buttonRemoveUser;//кнопка удалить сотрудника

    @FXML
    private Button buttonEditUser;//кнопка изменить сотрудника

    @FXML
    private Button buttonAddCartridge;//кнопка добавить картридж

    @FXML
    private Button buttonRemoveCartridge;//кнопка удалить картридж

    @FXML
    private Button buttonEditCartridge;//кнопка изменить картридж

    @FXML
    private Button buttonAddDefect;//кнопка добавить неисправность

    @FXML
    private Button buttonRemoveDefect;//кнопка удалить неисправность

    @FXML
    private Button buttonEditDefect;//кнопка изменить неисправность

    @FXML
    private TableView<UserModel> tableViewUsers;//таблица с пользователями

    @FXML
    private TableColumn<UserModel, String> tableColumnUserName;//колонка имя пользователя

    private ObservableList<UserModel> usersList = FXCollections.observableArrayList();

    public LayoutEditController() {

    }

    @FXML
    private void initialize() {
        tableColumnUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        tableViewUsers.setItems(usersList);

        //ToolTips for buttons
        Tooltip tooltipAdd = new Tooltip("Добавить");
        Tooltip tooltipRemove = new Tooltip("Удалить");
        Tooltip tooltipEdit = new Tooltip("Изменить");

        buttonAddUser.setTooltip(tooltipAdd);
        buttonRemoveUser.setTooltip(tooltipRemove);
        buttonEditUser.setTooltip(tooltipEdit);

        buttonAddCartridge.setTooltip(tooltipAdd);
        buttonRemoveCartridge.setTooltip(tooltipRemove);
        buttonEditCartridge.setTooltip(tooltipEdit);

        buttonAddDefect.setTooltip(tooltipAdd);
        buttonRemoveDefect.setTooltip(tooltipRemove);
        buttonEditDefect.setTooltip(tooltipEdit);
        //
    }

    //добавляем нового пользователя в базу и список отображения
    @FXML
    public void handleAddUser() throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Добавление нового пользователя");
        stage.initModality(Modality.APPLICATION_MODAL);//модальное окно

        FXMLLoader loader = new FXMLLoader();
        UserController userController = new UserController();
        loader.setController(userController);
        loader.setLocation(Main.class.getResource("view/UserView.fxml"));
        Parent parent = loader.load();
        stage.setScene(new Scene(parent));
        stage.setResizable(false);

        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(userController.isResultPresents()) {
                    UserService userService = new UserService();
                    userService.saveUser(userController.getUser());
                    usersList.add(userController.getUser());
                }
            }
        });

        stage.showAndWait();
    }

    //Удаляем пользователся из базы и обновляем в таблице UI
    @FXML
    public void handleRemoveUser() {
        UserService userService = new UserService();
        userService.deleteUser(tableViewUsers.getSelectionModel().getSelectedItem());
        tableViewUsers.getItems().remove(tableViewUsers.getSelectionModel().getSelectedIndex());
    }

    //Редатируем выбранного пользователя
    @FXML
    public void handleEditUser() throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Редактирование пользователя");
        stage.initModality(Modality.APPLICATION_MODAL);//модальное окно

        FXMLLoader loader = new FXMLLoader();
        UserModel selectedUser = tableViewUsers.getItems().get(tableViewUsers.getSelectionModel().getSelectedIndex());
        UserController userController = new UserController(selectedUser);
        loader.setController(userController);
        loader.setLocation(Main.class.getResource("view/UserView.fxml"));
        Parent parent = loader.load();
        stage.setScene(new Scene(parent));
        stage.setResizable(false);

        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(userController.isResultPresents()) {
                    tableViewUsers.refresh();
                    UserService userService = new UserService();
                    userService.updateUser(userController.getUser());
                }
            }
        });

        stage.showAndWait();
    }

    //получаем всех пользователей из базы и отображаем в таблице UI
    @FXML
    public void handleGetAllUsers() {
        UserService userService = new UserService();
        usersList = FXCollections.observableArrayList(userService.findAllUsers());
        tableViewUsers.setItems(usersList);
    }
}
