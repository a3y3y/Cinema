package by.itacademy.util.log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractConnection {
    private static String url = "jdbc:mysql://127.0.0.1:3306/cinema";
    private static String username = "root";
    private static String password = "root";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (connection == null) {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException(e);
        }
        return connection;
    }

    public static void close() throws SQLException {
        if(connection != null){
                connection.close();
        }
        connection = null;
    }
}
