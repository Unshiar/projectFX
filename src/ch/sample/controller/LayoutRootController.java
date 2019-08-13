package ch.sample.controller;

import ch.sample.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class LayoutRootController {

    private Parent rootLayout;//контейнер окон
    private Parent editLayout;//окно редактирование данных
    private Parent journalLayout;//окно журнала

    public LayoutRootController() throws Exception {
        InitLayouts();
    }

    public  void SetParentRoot(Parent root) {
        this.rootLayout = root;
    }

    //Выход из приложения
    public void ExitApp() {
        System.exit(0);
    }

    //Показать окно журнала
    public void ShowJournalLayout() {
        ((BorderPane) rootLayout).setCenter(journalLayout);
    }

    //Показать окно редактирования
    public void ShowEditLayout() {
        ((BorderPane) rootLayout).setCenter(editLayout);
    }

    private void InitLayouts() throws Exception {
        journalLayout = FXMLLoader.load(Main.class.getResource("resources/LayoutJournal.fxml"));
        editLayout = FXMLLoader.load(Main.class.getResource("resources/LayoutEdit.fxml"));
    }
}
