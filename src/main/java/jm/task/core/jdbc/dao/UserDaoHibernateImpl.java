package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final static UserDaoHibernateImpl INSTANCE = new UserDaoHibernateImpl();

    public UserDaoHibernateImpl() {

    }

    public static UserDaoHibernateImpl getInstance() {
        return INSTANCE;
    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE users " +
                "(id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(45) NULL, " +
                "last_name VARCHAR(45) NULL, " +
                "age TINYINT(1) NULL, " +
                "PRIMARY KEY (id), " +
                "UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE) " +
                "ENGINE = InnoDB " +
                "DEFAULT CHARACTER SET = utf8")
                .executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users")
                .executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();
//        session.delete(session.get(User.class, id));
        session.createQuery("DELETE User WHERE id=:id").setParameter("id", id).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        criteria.from(User.class);
        List<User> data = session.createQuery(criteria).getResultList();
        session.getTransaction().commit();
        return data;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.createQuery("DELETE User").executeUpdate();
        session.getTransaction().commit();
    }
}
