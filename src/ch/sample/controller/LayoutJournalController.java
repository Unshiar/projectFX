package ch.sample.controller;

import ch.sample.Main;
import ch.sample.model.Report;
import ch.sample.services.ReportService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.io.File;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.function.ToIntFunction;

public class LayoutJournalController {

    private ObservableList<Report> reportsList = FXCollections.observableArrayList();//список записей в журнале
                                                                                     // учета расхода картриджей

    //Как отображаются записи журнала
    private enum JournalType {
        JOURNAL_NONE,
        JOURNAL_ALL,//отображать весь журнал
        JOURNAL_LAST_10,//отображать последние 10 записей
        JOURNAL_LAST_N,//отображать последние N записей
        JOURNAL_WEEK,//отображать записи за последнюю неделю
        JOURNAL_MONTH,//отображать записи за последний месяц
        JOURNAL_PERIOD//отображать записи за выбранный период
    }

    private JournalType journalType;//как отображать записи журнала

    private int reportsCount;//отображаемое кол-во записей журнала
    private LocalDate startDate;//начальная дата с которой нужно отображать записи в журнале
    private LocalDate endDate;//конечная дата по которую нужно отображать записи в журнале

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
    private Button buttonJasper;

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

        textFieldLastN.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty())
                    textFieldLastN.setText("1");
                else if(newValue.matches("[0-9]*")) {
                    reportsCount = Integer.parseInt(newValue);
                }
                else textFieldLastN.setText(oldValue);
            }
        });

        datePickerStartDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                startDate = newValue;
            }
        });

        datePickerEndDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                endDate = newValue;
            }
        });

        //Инициализация начальных значений
        reportsCount = Integer.parseInt(textFieldLastN.getText());
        startDate = datePickerStartDate.getValue();
        endDate = datePickerEndDate.getValue();
    }

    public LayoutJournalController() {
        //Инициализация начальных значений
        journalType = JournalType.JOURNAL_ALL;

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

    //жмем кнопку "Обновить данные", получаем список отчетов
    @FXML
    public void handleRefreshData() {
        ReportService reportService = new ReportService();

        switch (journalType) {
            case JOURNAL_NONE:
                break;
            case JOURNAL_ALL:
                reportsList = FXCollections.observableArrayList(reportService.findAllReports());
                break;
            case JOURNAL_LAST_10:
                reportsList = FXCollections.observableArrayList(reportService.findLastNReports(10));
                break;
            case JOURNAL_LAST_N:
                reportsList = FXCollections.observableArrayList(reportService.findLastNReports(reportsCount));
                break;
            case JOURNAL_WEEK:
                reportsList = FXCollections.observableArrayList(reportService.findFromToPeriod(LocalDate.now().minusDays(7), LocalDate.now()));
                break;
            case JOURNAL_MONTH:
                reportsList = FXCollections.observableArrayList(reportService.findFromToPeriod(LocalDate.now().minusMonths(1), LocalDate.now()));
                break;
            case JOURNAL_PERIOD:
                reportsList = FXCollections.observableArrayList(reportService.findFromToPeriod(startDate, endDate));
                break;
        }

        //сортируем полученные результаты по возрастанию id
        reportsList.sort(Comparator.comparingInt(new ToIntFunction<Report>() {
            @Override
            public int applyAsInt(Report value) {
                return value.getId();
            }
        }));

        tableReports.setItems(reportsList);
    }

    //жмем кнопку Jasper
    @FXML
    public void handleJasper() {
        /*
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./templates"));
        File file = fileChooser.showOpenDialog(buttonJasper.getScene().getWindow());

        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        }
        catch (JRException ex) {
            ex.printStackTrace();
        }

        //reportsList.

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(reportsList);

        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport,
                    null, jrBeanCollectionDataSource);
        }
        catch (JRException ex) {
            ex.printStackTrace();
        }

        JasperViewer jasperViewer = new JasperViewer(jasperPrint);
        JasperViewer.viewReport(jasperPrint, false);

        Exporter exporter = new JRDocxExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

        File exportReportFile = new File("D:\\report.docx");

        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        */
    }

    //Отображать весь журнал
    @FXML
    public void handleAllJournal() {
        journalType = JournalType.JOURNAL_ALL;
    }

    //Отображать последние 10 записей
    @FXML
    public void handleLast10Reports() {
        journalType = JournalType.JOURNAL_LAST_10;
    }

    //Отображать последние N записей
    @FXML
    public void handleLastNReports() {
        journalType = journalType.JOURNAL_LAST_N;
    }

    //Отображать записи за неделю
    @FXML
    public void handleLastWeek() {
        journalType = JournalType.JOURNAL_WEEK;
    }

    //Отображать записи за месяц
    @FXML
    public void handleLastMonth() {
        journalType = JournalType.JOURNAL_MONTH;
    }

    //Отображать записи за выбраный период
    @FXML
    public void handlePeriod() {
        journalType = JournalType.JOURNAL_PERIOD;
    }
}
