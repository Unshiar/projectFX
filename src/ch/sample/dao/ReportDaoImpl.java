package ch.sample.dao;

import ch.sample.model.Report;
import ch.sample.utils.HibernateSessionFactoryUtil;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ReportDaoImpl implements ReportDao {
    @Override
    public Report findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Report report = session.get(Report.class, id);
        return report;
    }

    @Override
    public void save(Report report) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(report);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Report report) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(report);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Report report) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(report);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Report> findAll() {
        List<Report> reports = new ArrayList<>();
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        if(session != null) {
            try {
                reports = session.createQuery("From Report").list();
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка запроса к базе данных.");
                alert.setContentText(ex.toString());
                alert.showAndWait();
            } finally {
                if(session != null)
                    session.close();
            }
        }
        return reports;
    }
}
