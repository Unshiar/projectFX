package ch.sample.services;

import ch.sample.dao.ReportDaoImpl;
import ch.sample.model.Report;

import java.util.List;

public class ReportService {
    private ReportDaoImpl reportDao = new ReportDaoImpl();

    public ReportService() {
    }

    public Report findReport(int id) {
        return reportDao.findById(id);
    }

    public void saveReport(Report report) {
        reportDao.save(report);
    }

    public void deleteReport(Report report) {
        reportDao.delete(report);
    }

    public void updateReport(Report report) {
        reportDao.update(report);
    }

    public List<Report> findAllReports() {
        return reportDao.findAll();
    }
}
