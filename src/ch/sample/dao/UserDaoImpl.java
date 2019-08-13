package ch.sample.dao;

import ch.sample.model.UserModel;
import ch.sample.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoImpl implements UserDao{
    @Override
    public UserModel findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(UserModel.class, id);
    }

    @Override
    public void save(UserModel user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(UserModel user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(UserModel user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }
    @Override
    public List<UserModel> findAll() {
        return (List<UserModel>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From UserModel").list();
    }
}
