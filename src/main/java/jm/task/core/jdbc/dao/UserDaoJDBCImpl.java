package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String INSERT_NEW = "INSERT INTO users VALUES(default,?,?,?)";
    private static final String CREAT_NEW_TABLE = "CREATE TABLE IF NOT EXISTS users (\n" +
                                                    "id int PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                                                    "name VARCHAR(45) NOT NULL,\n" +
                                                    "lastNAME VARCHAR(45) NOT NULL,\n" +
                                                    "age int NOT NULL\n" + ");";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().execute(CREAT_NEW_TABLE);
        } catch (SQLException e) {
        }

    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().execute("DROP TABLE users");
        } catch (SQLException e) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
            System.out.println("User с именем - " + name + " добавлен в базу данных");

        } catch (SQLException e) {

        }

    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users where id = ?")) {

            preparedStatement.setInt(1, (int) id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT  * FROM users")) {

            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                                        resultSet.getString("lastName"),
                                        resultSet.getByte("age"));

                user.setId(resultSet.getLong("id"));
                users.add(user);
            }

        } catch (SQLException e) {
        }

        return users;
    }


    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().execute("DROP table users");
            connection.createStatement().execute(CREAT_NEW_TABLE);
        } catch (SQLException e) {
        }

    }
}
