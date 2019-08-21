package ch.sample.utils;

import ch.sample.model.UserModel;
import javafx.scene.control.Alert;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();

                //configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(UserModel.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (HibernateException ex) {
                System.out.println("Исключение!" + ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка подключения.");
                alert.setContentText(ex.toString());
                alert.showAndWait();
            }
        }
        return sessionFactory;
    }

    public static void CloseSessionFactory() {
        if(sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
