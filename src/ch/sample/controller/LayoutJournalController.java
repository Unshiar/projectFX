package ch.sample.controller;

import ch.sample.Main;
import ch.sample.model.Report;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LayoutJournalController {
    @FXML
    private void initialize() {

    }

    public LayoutJournalController() {
    }

    public void handleCreateNote() {
        Stage stage = new Stage();
        stage.setTitle("Добавление новой записи");
        stage.initModality(Modality.APPLICATION_MODAL);//модальное окно
        stage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        Report report = new Report();//новая запись в журнале
        ReportController reportController = new ReportController(report);
        loader.setController(reportController);
        String viewLocation = "view/ReportView.fxml";
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
        stage.showAndWait();
    }
}
