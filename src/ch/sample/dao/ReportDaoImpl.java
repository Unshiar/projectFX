package ch.sample.dao;

import ch.sample.model.Report;
import ch.sample.utils.AlertMessage;
import ch.sample.utils.HibernateSessionFactoryUtil;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
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
                AlertMessage.showErrorMessage("Ошибка", "Ошибка запроса к базе данных.", ex.toString());
            } finally {
                if(session != null)
                    session.close();
            }
        }
        return reports;
    }

    //найти последние N записей
    @Override
    public List<Report> findLastN(int count) {
        List<Report> reports = new ArrayList<>();
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        if(session != null) {
            try {
                String jpql = "FROM Report r ORDER BY r.id DESC";
                Query query = session.createQuery(jpql);
                query.setMaxResults(count);
                reports = query.list();
            } catch (IllegalArgumentException ex) {
                AlertMessage.showErrorMessage("Ошибка", "Ошибка запроса к базе данных.", ex.toString());
            } finally {
                if(session != null)
                    session.close();
            }
        }
        return reports;
    }

    //найти записи в выбранном периоде
    @Override
    public List<Report> findFromToPeriod(LocalDate startDate, LocalDate endDate) {
        List<Report> reports = new ArrayList<>();
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        if(session != null) {
            try {
                String jpql = "FROM Report r WHERE r.date BETWEEN :startDate AND :endDate";
                Query query = session.createQuery(jpql);
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
                reports = query.list();
            } catch (IllegalArgumentException ex) {
                AlertMessage.showErrorMessage("Ошибка", "Ошибка запроса к базе данных.", ex.toString());
            } finally {
                if(session != null)
                    session.close();
            }
        }
        return reports;
    }
}
