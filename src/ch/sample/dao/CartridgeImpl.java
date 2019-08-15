package ch.sample.dao;

import ch.sample.model.Cartridge;
import ch.sample.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CartridgeImpl implements CartridgeDao {
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
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Cartridge> cartridgeList = session.createQuery("From Cartridge").list();
        return cartridgeList;
    }
}
