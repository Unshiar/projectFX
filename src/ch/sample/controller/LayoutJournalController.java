package ch.sample.controller;

import ch.sample.Main;
import ch.sample.model.Report;
import ch.sample.services.ReportService;
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
import javafx.util.StringConverter;

import java.time.LocalDate;

public class LayoutJournalController {

    private ObservableList<Report> reportsList = FXCollections.observableArrayList();//список записей в журнале
                                                                                  // учета расхода картриджей
    @FXML
    private RadioButton radioButtonAll;//кнопка - весь журнал

    @FXML
    private RadioButton radioButtonLast10;//кнопка - последние 10 записей

    @FXML
    private RadioButton radioButtonLastN;//кнопка - последние N записей

    @FXML
    private TextField textFieldLastN;//поле - количество последних записей

    @FXML
    private RadioButton radioButtonWeek;//кнопка - за неделю

    @FXML
    private RadioButton radioButtonMonth;//кнопка - за месяц

    @FXML
    private RadioButton radioButtonPeriod;//кнопка - за период

    @FXML
    private DatePicker datePickerStartDate;//поле - начальная дата

    @FXML
    private DatePicker datePickerEndDate;//поле - конечная дата

    @FXML
    private TableView<Report> tableReports;//Таблица - журнал учета расхода картриджей

    @FXML
    private TableColumn<Report, String> columnNum;//колонка - "Номер"

    @FXML
    private TableColumn<Report, LocalDate> columnDate;//колонка - "Дата"

    @FXML
    private TableColumn<Report, String> columnUserName;//колонка - "ФИО"

    @FXML
    private TableColumn<Report, String> columnCartridgeOut;//колонка "Извлеч."

    @FXML
    private TableColumn<Report, String> columnCartridgeIn;//колонка "Установл."

    @FXML
    private TableColumn<Report, String> columnPrinterNum;//колонка "инв.№"

    @FXML
    private TableColumn<Report, String> columnDefectName;//колонка "Причина"

    @FXML
    private void initialize() {
        //начальные значения "За период" (по умолчанию текущий день)
        datePickerStartDate.setValue(LocalDate.now());
        datePickerEndDate.setValue(LocalDate.now());
        //Заполняем таблицу учета расхода картриджейф
        tableReports.setItems(reportsList);
        //Сопоставим колонке - "Номер", свойсто "id" из модели Report
        columnNum.setCellValueFactory(new PropertyValueFactory<>("id"));
        //Сопоставим колонке - "Дата", свойсто "date" из модели Report
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        //Сопоставим колонке - "ФИО", свойсто "userName" из модели Report
        columnUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        //Сопоставим колонке - "Извлеч.", свойсто "cartridgeOut" из модели Report
        columnCartridgeOut.setCellValueFactory(new PropertyValueFactory<>("cartridgeOut"));
        //Сопоставим колонке - "Установл.", свойсто "cartridgeIn" из модели Report
        columnCartridgeIn.setCellValueFactory(new PropertyValueFactory<>("cartridgeIn"));
        //Сопоставим колонке - "инв.№", свойсто "printerNum" из модели Report
        columnPrinterNum.setCellValueFactory(new PropertyValueFactory<>("printerNum"));
        //Сопоставим колонке - "Причина", свойсто "defectName" из модели Report
        columnDefectName.setCellValueFactory(new PropertyValueFactory<>("defectName"));
    }

    public LayoutJournalController() {

    }

    @FXML
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

        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(reportController.isResultPresents()) {
                    ReportService reportService = new ReportService();
                    reportService.saveReport(report);
                }
            }
        });

        stage.showAndWait();
    }

    //жмем кнопку "Обновить данные"
    @FXML
    public void handleRefreshData() {
        ReportService reportService = new ReportService();
        reportsList = FXCollections.observableArrayList(reportService.findAllReports());
        tableReports.setItems(reportsList);
    }
}
