package ch.sample.controller;

import ch.sample.Main;
import ch.sample.model.Cartridge;
import ch.sample.model.Defect;
import ch.sample.model.UserModel;
import ch.sample.services.CartridgeService;
import ch.sample.services.DefectService;
import ch.sample.services.UserService;
import ch.sample.utils.AlertMessage;
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
    private TableView<UserModel> tableViewUsers;//таблица с пользователями

    @FXML
    private TableColumn<UserModel, String> tableColumnUserName;//колонка таблицы - имя пользователя

    @FXML
    private Button buttonAddCartridge;//кнопка добавить картридж

    @FXML
    private Button buttonRemoveCartridge;//кнопка удалить картридж

    @FXML
    private Button buttonEditCartridge;//кнопка изменить картридж

    @FXML
    TableView<Cartridge> tableViewCartridges;//таблица с картриджами

    @FXML
    TableColumn<Cartridge, String> columnCartridgeNumber;//колонка таблицы - номер картриджа

    @FXML
    private Button buttonAddDefect;//кнопка добавить неисправность

    @FXML
    private Button buttonRemoveDefect;//кнопка удалить неисправность

    @FXML
    private Button buttonEditDefect;//кнопка изменить неисправность

    @FXML
    TableView<Defect> tableViewDefects;//таблица с дефектами

    @FXML
    TableColumn<Defect, String> columnDefectName;//колонка таблицы - название дефекта

    private ObservableList<UserModel> usersList = FXCollections.observableArrayList();//список пользователей
    private ObservableList<Cartridge> cartridgesList = FXCollections.observableArrayList();//список картриджей
    private ObservableList<Defect> defectsList = FXCollections.observableArrayList();//список дефектов

    public LayoutEditController() {

    }

    @FXML
    private void initialize() {
        //Сопоставляем колонке "Сотруднки", свойство "userName" из модели UserModel
        tableColumnUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        //заполняем таблицу с Сотрудниками(пользователями)
        tableViewUsers.setItems(usersList);
        //Сопоставляем колонке "Картриджи", свойство "number" из модели Cartridge
        columnCartridgeNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        //заполняем таблицу с Картриджами
        tableViewCartridges.setItems(cartridgesList);
        //Сопоставляем колонке "Несправности", свойство "name" из модели Defect
        columnDefectName.setCellValueFactory(new PropertyValueFactory<>("name"));
        //Заполням таблицу с Неисправностями
        tableViewDefects.setItems(defectsList);

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
    public void handleAddUser() {
        Stage stage = new Stage();
        stage.setTitle("Добавление нового пользователя");
        stage.initModality(Modality.APPLICATION_MODAL);//модальное окно

        FXMLLoader loader = new FXMLLoader();
        UserController userController = new UserController();
        loader.setController(userController);
        String viewLocation = "view/UserView.fxml";
        loader.setLocation(Main.class.getResource(viewLocation));
        Parent parent;
        try {
            parent = loader.load();
        } catch (Exception ex) {
            System.err.println("Can't load " + viewLocation);
            ex.printStackTrace();
            return;
        }
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
        int index = tableViewUsers.getSelectionModel().getSelectedIndex();
        if(index < 0) {
            AlertMessage.showErrorMessage("Ошибка", "Не выбран пользователь для удаления.", "");
            return;
        }

        UserService userService = new UserService();
        userService.deleteUser(tableViewUsers.getSelectionModel().getSelectedItem());
        tableViewUsers.getItems().remove(index);
    }

    //Редатируем выбранного пользователя
    @FXML
    public void handleEditUser() {
        Stage stage = new Stage();
        stage.setTitle("Редактирование пользователя");
        stage.initModality(Modality.APPLICATION_MODAL);//модальное окно

        int index = tableViewUsers.getSelectionModel().getSelectedIndex();
        if(index < 0) {
            AlertMessage.showErrorMessage("Ошибка", "Не выбран пользователь для редактирования.", "");
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        UserModel selectedUser = tableViewUsers.getItems().get(tableViewUsers.getSelectionModel().getSelectedIndex());
        UserController userController = new UserController(selectedUser);
        loader.setController(userController);
        String viewLocation = "view/UserView.fxml";
        loader.setLocation(Main.class.getResource(viewLocation));
        Parent parent;
        try {
            parent = loader.load();
        } catch (Exception ex) {
            System.err.println("Can't load " + viewLocation);
            ex.printStackTrace();
            return;
        }
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

    //добавляем новый картридж в базу данных и список отображения
    @FXML
    public void handleAddCartridge() {
        Stage stage = new Stage();
        stage.setTitle("Добавление нового картриджа");
        stage.initModality(Modality.APPLICATION_MODAL);//модальное окно

        FXMLLoader loader = new FXMLLoader();
        CartridgeController cartridgeController = new CartridgeController();
        loader.setController(cartridgeController);
        String viewLocation = "view/CartridgeView.fxml";
        loader.setLocation(Main.class.getResource(viewLocation));
        Parent parent;
        try {
            parent = loader.load();
        } catch (Exception ex) {
            System.err.println("Can't load " + viewLocation);
            ex.printStackTrace();
            return;
        }
        stage.setScene(new Scene(parent));
        stage.setResizable(false);

        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(cartridgeController.isResultPresents()) {
                    CartridgeService cartridgeService = new CartridgeService();
                    cartridgeService.saveCartridge(cartridgeController.getCartridge());
                    cartridgesList.add(cartridgeController.getCartridge());
                }
            }
        });

        stage.showAndWait();
    }

    //Удаляем картридж из базы данных и обновляем в таблице UI
    @FXML
    public void handleRemoveCartridge() {
        int index = tableViewCartridges.getSelectionModel().getSelectedIndex();
        if(index < 0) {
            AlertMessage.showErrorMessage("Ошибка", "Не выбран картридж для удаления.", "");
            return;
        }

        CartridgeService cartridgeService = new CartridgeService();
        cartridgeService.deleteCartridge(tableViewCartridges.getSelectionModel().getSelectedItem());
        tableViewCartridges.getItems().remove(index);
    }

    //Редактируем выбраный картридж
    @FXML
    public void handleEditCartridge() {
        Stage stage = new Stage();
        stage.setTitle("Редактирование картриджа");
        stage.initModality(Modality.APPLICATION_MODAL);//модальное окно

        int index = tableViewCartridges.getSelectionModel().getSelectedIndex();
        if(index < 0) {
            AlertMessage.showErrorMessage("Ошибка", "Не выбран картридж для редактирования.", "");
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        Cartridge cartridge = tableViewCartridges.getSelectionModel().getSelectedItem();
        CartridgeController cartridgeController = new CartridgeController(cartridge);
        loader.setController(cartridgeController);
        String viewLocation = "view/CartridgeView.fxml";
        loader.setLocation(Main.class.getResource(viewLocation));
        Parent parent;
        try {
            parent = loader.load();
        } catch (Exception ex) {
            System.err.println("Can't load " + viewLocation);
            ex.printStackTrace();
            return;
        }
        stage.setScene(new Scene(parent));
        stage.setResizable(false);

        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(cartridgeController.isResultPresents()) {
                    tableViewCartridges.refresh();
                    CartridgeService cartridgeService = new CartridgeService();
                    cartridgeService.updateCartridge(cartridgeController.getCartridge());
                }
            }
        });

        stage.showAndWait();
    }

    //получаем все картриджи из базы данных и отображаем в таблице UI
    @FXML
    public void handleGetAllCartridges() {
        CartridgeService cartridgeService = new CartridgeService();
        cartridgesList = FXCollections.observableArrayList(cartridgeService.findAllCartridges());
        tableViewCartridges.setItems(cartridgesList);
    }

    //добавляем новый дефект в базу данных и список отображения
    @FXML
    public void handleAddDefect() {
        Stage stage = new Stage();
        stage.setTitle("Добавление нового дефекта");
        stage.initModality(Modality.APPLICATION_MODAL);//модальное окно

        FXMLLoader loader = new FXMLLoader();
        DefectController defectController = new DefectController();
        loader.setController(defectController);
        String viewLocation = "view/DefectView.fxml";
        loader.setLocation(Main.class.getResource(viewLocation));
        Parent parent;
        try {
            parent = loader.load();
        } catch (Exception ex) {
            System.err.println("Can't load " + viewLocation);
            ex.printStackTrace();
            return;
        }
        stage.setScene(new Scene(parent));
        stage.setResizable(false);

        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(defectController.isResultPresents()) {
                    DefectService defectService = new DefectService();
                    defectService.saveDefect(defectController.getDefect());
                    defectsList.add(defectController.getDefect());
                }
            }
        });

        stage.showAndWait();
    }

    //Удаляем дефект из базы данных и обновляем в таблице UI
    @FXML
    public void handleRemoveDefect() {
        int index = tableViewDefects.getSelectionModel().getSelectedIndex();
        if(index < 0) {
            AlertMessage.showErrorMessage("Ошибка", "Не выбран дефект для удаления.", "");
            return;
        }

        DefectService defectService = new DefectService();
        defectService.deleteDefect(tableViewDefects.getSelectionModel().getSelectedItem());
        tableViewDefects.getItems().remove(index);
    }

    //Редактируем выбранный дефект
    @FXML
    public void handleEditDefect() {
        Stage stage = new Stage();
        stage.setTitle("Редактирование дефекта");
        stage.initModality(Modality.APPLICATION_MODAL);//модальное окно

        int index = tableViewDefects.getSelectionModel().getSelectedIndex();
        if(index < 0) {
            AlertMessage.showErrorMessage("Ошибка", "Не выбран дефект для редактирования.", "");
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        Defect defect = tableViewDefects.getSelectionModel().getSelectedItem();
        DefectController defectController = new DefectController(defect);
        loader.setController(defectController);
        String viewLocation = "view/DefectView.fxml";
        loader.setLocation(Main.class.getResource(viewLocation));
        Parent parent;
        try {
            parent = loader.load();
        } catch (Exception ex) {
            System.err.println("Can't load " + viewLocation);
            ex.printStackTrace();
            return;
        }
        stage.setScene(new Scene(parent));
        stage.setResizable(false);

        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(defectController.isResultPresents()) {
                    tableViewDefects.refresh();
                    DefectService defectService = new DefectService();
                    defectService.updateDefect(defectController.getDefect());
                }
            }
        });

        stage.showAndWait();
    }

    //получаем все картриджи из базы данных и отображаем в таблице UI
    @FXML
    public void handleGetAllDefects() {
        DefectService defectService = new DefectService();
        defectsList = FXCollections.observableArrayList(defectService.findAllDefects());
        tableViewDefects.setItems(defectsList);
    }
}
