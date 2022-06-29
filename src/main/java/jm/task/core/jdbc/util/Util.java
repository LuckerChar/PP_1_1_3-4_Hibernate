package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static final Connection CONNECTION = createConnection();

    private Util() {}

    public static Connection getConnection () {
        return CONNECTION;
    }

    public static void closeConnection() {
        try {
            if (CONNECTION != null) {
                CONNECTION.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection createConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}