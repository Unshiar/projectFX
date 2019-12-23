package ch.sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class LayoutChoiceStatementController {
    @FXML
    private Button buttonToDirector;

    @FXML
    private Button buttonToAccountant;

    private List dataSource;

    public LayoutChoiceStatementController(List dataSource) {
        this.dataSource = dataSource;
    }

    @FXML
    public void handleToDirector() {
        File file = new File("./templates/DirectorReport.jrxml");

        JasperReport jasperReport;
        try {
            jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        }
        catch (JRException ex) {
            ex.printStackTrace();
            return;
        }

        JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(dataSource);

        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport,
                    null, collectionDataSource);
        }
        catch (JRException ex) {
            ex.printStackTrace();
        }

        JasperViewer jasperViewer = new JasperViewer(jasperPrint);
        JasperViewer.viewReport(jasperPrint, false);
    }

    @FXML
    public void handleToAccountant() {
        File file = new File("./templates/AccountantReport.jrxml");

        JasperReport jasperReport;
        try {
            jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        }
        catch (JRException ex) {
            ex.printStackTrace();
            return;
        }

        JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(dataSource);

        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport,
                    null, collectionDataSource);
        }
        catch (JRException ex) {
            ex.printStackTrace();
        }

        JasperViewer jasperViewer = new JasperViewer(jasperPrint);
        JasperViewer.viewReport(jasperPrint, false);
    }
}
