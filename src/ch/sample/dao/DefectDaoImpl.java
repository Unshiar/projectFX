package ch.sample.dao;

import ch.sample.model.Defect;
import ch.sample.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DefectDaoImpl implements DefectDao {
    @Override
    public Defect findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Defect defect = session.get(Defect.class, id);
        return defect;
    }

    @Override
    public void save(Defect defect) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(defect);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Defect defect) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(defect);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Defect defect) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(defect);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Defect> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Defect> defectList = session.createQuery("From Defects").list();
        return defectList;
    }
}
