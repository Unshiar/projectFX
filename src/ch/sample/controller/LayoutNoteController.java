package ch.sample.controller;

import ch.sample.model.Cartridge;
import ch.sample.model.Defect;
import ch.sample.model.UserModel;
import ch.sample.services.CartridgeService;
import ch.sample.services.DefectService;
import ch.sample.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.util.Comparator;


public class LayoutNoteController {
    private ObservableList<UserModel> usersList = FXCollections.observableArrayList();//список пользователей
    private ObservableList<Cartridge> cartridgesList = FXCollections.observableArrayList();//список картриджей
    private ObservableList<Defect> defectsList = FXCollections.observableArrayList();//список дефектов

    @FXML
    private ComboBox<UserModel> comboBoxUsers;

    @FXML
    private ComboBox<Cartridge> comboBoxCartridgeOut;

    @FXML
    private ComboBox<Cartridge> comboBoxCartridgeIn;

    @FXML
    private ComboBox<Defect> comboBoxDefects;


    public LayoutNoteController() {
        //вытягиваем всех пользователей из базы данных и запоминаем в списке
        UserService userService = new UserService();
        usersList.addAll(userService.findAllUsers());
        //вытягиваем все картриджи из базы данных и запоминаем в списке
        CartridgeService cartridgeService = new CartridgeService();
        cartridgesList.addAll(cartridgeService.findAllCartridges());
        //вытягиваем все дефекты из базы данных и запоминаем в списке
        DefectService defectService = new DefectService();
        defectsList.addAll(defectService.findAllDefects());

        //Пользователи отсортированы по возрастанию
        usersList.sort(new Comparator<UserModel>() {
            @Override
            public int compare(UserModel o1, UserModel o2) {
                return o1.getUserName().compareTo(o2.getUserName());
            }
        });
        //Картриджи отсортированы по возрастанию
        cartridgesList.sort(new Comparator<Cartridge>() {
            @Override
            public int compare(Cartridge o1, Cartridge o2) {
                return o1.getNumber().compareTo(o2.getNumber());
            }
        });
        //Дефекты отсортированы по возрастанию
        defectsList.sort(new Comparator<Defect>() {
            @Override
            public int compare(Defect o1, Defect o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    @FXML
    private void initialize() {
        comboBoxUsers.setItems(usersList);
        comboBoxCartridgeIn.setItems(cartridgesList);
        comboBoxCartridgeOut.setItems(cartridgesList);
        comboBoxDefects.setItems(defectsList);

        comboBoxUsers.setConverter(new StringConverter<UserModel>() {
            @Override
            public String toString(UserModel object) {
                return object.getUserName();
            }

            @Override
            public UserModel fromString(String string) {
                return null;
            }
        });

        comboBoxCartridgeOut.setConverter(new StringConverter<Cartridge>() {
            @Override
            public String toString(Cartridge object) {
                return object.getNumber();
            }

            @Override
            public Cartridge fromString(String string) {
                return null;
            }
        });

        comboBoxCartridgeIn.setConverter(new StringConverter<Cartridge>() {
            @Override
            public String toString(Cartridge object) {
                return object.getNumber();
            }

            @Override
            public Cartridge fromString(String string) {
                return null;
            }
        });

        comboBoxDefects.setConverter(new StringConverter<Defect>() {
            @Override
            public String toString(Defect object) {
                return object.getName();
            }

            @Override
            public Defect fromString(String string) {
                return null;
            }
        });
    }

}
