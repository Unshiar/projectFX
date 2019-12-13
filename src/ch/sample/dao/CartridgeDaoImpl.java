package ch.sample.dao;

import ch.sample.model.Cartridge;
import ch.sample.utils.AlertMessage;
import ch.sample.utils.HibernateSessionFactoryUtil;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CartridgeDaoImpl implements CartridgeDao {
    @Override
    public Cartridge findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Cartridge cartridge = session.get(Cartridge.class, id);
        return cartridge;
    }

    @Override
    public void save(Cartridge cartridge) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(cartridge);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Cartridge cartridge) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(cartridge);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Cartridge cartridge) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(cartridge);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Cartridge> findAll() {
        List<Cartridge> cartridges = new ArrayList<>();
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        if(session != null) {
            try {
                cartridges = session.createQuery("From Cartridge").list();
            } catch (IllegalArgumentException ex) {
                AlertMessage.showErrorMessage("Ошибка", "Ошибка запроса к базе данных.", ex.toString());
            } finally {
                if(session != null)
                    session.close();
            }
        }
        return cartridges;
    }
}
