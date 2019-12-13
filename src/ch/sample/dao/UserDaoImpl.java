package ch.sample.dao;

import ch.sample.model.UserModel;
import ch.sample.utils.AlertMessage;
import ch.sample.utils.HibernateSessionFactoryUtil;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.hql.internal.ast.QuerySyntaxException;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public UserModel findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        UserModel user = session.get(UserModel.class, id);
        return user;
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
    public List<UserModel> findAll() throws QuerySyntaxException {
        List<UserModel> users = new ArrayList<>();
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        if(session != null) {
            try {
                users = session.createQuery("From UserModel").list();
            } catch (IllegalArgumentException ex) {
                AlertMessage.showErrorMessage("Ошибка", "Ошибка запроса к базе данных.", ex.toString());
            } finally {
                if(session != null)
                    session.close();
            }
        }
        return users;
    }
}
