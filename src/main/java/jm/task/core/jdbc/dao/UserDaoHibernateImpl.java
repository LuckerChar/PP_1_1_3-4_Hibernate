package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    //            try (
//    Statement statement = connection.createStatement()) {
//        statement.execute("CREATE TABLE IF NOT EXISTS users (" +
//                "id bigint NOT NULL AUTO_INCREMENT," +
//                "name varchar(255) DEFAULT NULL," +
//                "lastName varchar(255) DEFAULT NULL ," +
//                "age tinyint DEFAULT  NULL," +
//                "PRIMARY KEY (id))");
//    } catch (
//    SQLException e) {
//        e.printStackTrace();
//    }
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionfactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE users (id INTEGER NOT NULL AUTO_INCREMENT, name VARCHAR(45), lastname VARCHAR(45), age BIGINT(3), PRIMARY KEY (id))").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionfactory().openSession()) {
            session.beginTransaction();
            String sql = "DROP TABLE if exists User";
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица users удалена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = Util.getSessionfactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("INSERT INTO users (name, lastname, age) VALUES ('"+name+"', '"+lastName+"', '"+age+"')").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionfactory().openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE User WHERE id = :userid").setParameter("userid", id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionfactory().openSession()) {
            users = session.createQuery("from User").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        dropUsersTable();
        createUsersTable();
    }
}
