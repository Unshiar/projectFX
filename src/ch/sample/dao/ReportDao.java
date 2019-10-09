package ch.sample.dao;

import ch.sample.model.Report;

import java.time.LocalDate;
import java.util.List;

public interface ReportDao {
    Report findById(int id);
    void save(Report report);
    void delete(Report report);
    void update(Report report);
    List<Report> findLastN(int count);//найти последние N записей
    List<Report> findFromToPeriod(LocalDate startDate, LocalDate endDate);
    List<Report> findAll();
}
