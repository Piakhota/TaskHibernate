package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        List<User> users = new ArrayList<>();
        users.add(new User("Tony", "Stark", (byte) 52));
        users.add(new User("Steve", "Rogers", (byte) 104));
        users.add(new User("Baby", "Groot", (byte) 8));
        users.add(new User("Bruce", "Banner", (byte) 52));

        userService.dropUsersTable();
        userService.createUsersTable();
        for (User user : users) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.printf("User с именем %s добавлен в базу данных \n", user.getName());
        }
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
