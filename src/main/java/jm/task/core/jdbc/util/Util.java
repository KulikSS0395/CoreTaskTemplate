package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;

    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry serviceRegistry;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static Configuration getConfiguration() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        properties.setProperty("hibernate.connection.url", URL);
        properties.setProperty("hibernate.connection.username", USERNAME);
        properties.setProperty("hibernate.connection.password", PASSWORD);
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.current_session_context_class", "thread");
        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(User.class);
        return configuration;
    }

    public static SessionFactory getSessionFactory() {

        try {
            Configuration cfg = getConfiguration();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
            sessionFactory = cfg.buildSessionFactory(serviceRegistry);

        } catch (Exception e) {
            System.out.println("SessionFactory failed");
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
        return sessionFactory;
    }
}
