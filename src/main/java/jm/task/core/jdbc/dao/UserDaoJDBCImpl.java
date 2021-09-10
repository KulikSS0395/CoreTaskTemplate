package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = null;

        try {
            connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS users (\n" +
                                    "id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                                    "name VARCHAR(45) NOT NULL,\n" +
                                    "lastNAME VARCHAR(45) NOT NULL,\n" +
                                    "age TINYINT NOT NULL\n" + ");");

            try {
                statement.close();
            } catch (SQLException e) {
                connection.rollback();
                System.err.println("Failed close statement when create table");
            }

            connection.commit();

        } catch (SQLException e) {
            System.err.println("Create table failed");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                System.err.println("Failed close connection");
            }
        }

    }

    public void dropUsersTable() {
        Connection connection = null;

        try {
            connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS users");

            try {
                statement.close();
            } catch (SQLException e) {
                connection.rollback();
                System.err.println("Failed close statement when drop table");
            }
            connection.commit();

        } catch (SQLException e) {
            System.err.println("Drop table failed");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                System.err.println("Failed close connection");
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;

        try {
            connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, lastNAME, age) VALUES(?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();

            try {
                preparedStatement.close();
            } catch (SQLException e) {
                connection.rollback();
                System.err.println("Failed close preparedStatement when save user");
            }
            connection.commit();

            System.out.println("User с именем - " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            System.err.println("Save user failed");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                System.err.println("Failed close connection");
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = null;

        try {
            connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users where id = ?");
            preparedStatement.setInt(1, (int) id);
            preparedStatement.executeUpdate();

            try {
                preparedStatement.close();
            } catch (SQLException e) {
                connection.rollback();
                System.err.println("Failed close preparedStatement when remove user");
            }
            connection.commit();

        } catch (SQLException e) {
            System.err.println("Remove user failed");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                System.err.println("Failed close connection");
            }
        }
    }

    public List<User> getAllUsers() {
        Connection connection = null;
        List<User> users = new ArrayList<>();

        try {
            connection = Util.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  * FROM users");
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));

                user.setId(resultSet.getLong("id"));
                users.add(user);
            }

            try {
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                connection.rollback();
                System.err.println("Failed close statement when get user");
            }
            connection.commit();

        } catch (SQLException e) {
            System.err.println("Get allUsers failed");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                System.err.println("Failed close connection");
            }
        }

        return users;
    }


    public void cleanUsersTable() {
        Connection connection = null;

        try {
            connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("TRUNCATE TABLE users");

            try {
                statement.close();
            } catch (SQLException e) {
                connection.rollback();
                System.err.println("Failed close statement when create table");
            }
            connection.commit();

        } catch (SQLException e) {
            System.err.println("Clean table failed");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                System.err.println("Failed close connection");
            }
        }

    }
}
