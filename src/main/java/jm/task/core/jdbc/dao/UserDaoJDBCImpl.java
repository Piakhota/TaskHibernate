package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final UserDaoJDBCImpl INSTANCE = new UserDaoJDBCImpl();
    private static final String CREATE_SQL = "CREATE TABLE users " +
            "(id INT NOT NULL AUTO_INCREMENT, " +
            "name VARCHAR(45) NULL, " +
            "last_name VARCHAR(45) NULL, " +
            "age TINYINT(1) NULL, " +
            "PRIMARY KEY (id), " +
            "UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE) " +
            "ENGINE = InnoDB " +
            "DEFAULT CHARACTER SET = utf8;";
    private static final String DROP_SQL = "DROP TABLE IF EXISTS users;";
    private static final String SAVE_SQL = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?);";
    private static final String REMOVE_SQL = "DELETE FROM users WHERE id = ?;";
    private static final String GET_ALL_SQL = "SELECT name, last_name, age FROM users;";
    private static final String CLEAN_SQL = "TRUNCATE TABLE users;";

    public static UserDaoJDBCImpl getInstance() {
        return INSTANCE;
    }

    private UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_SQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(new User(resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age")));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_SQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
