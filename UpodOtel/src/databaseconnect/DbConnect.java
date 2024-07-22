package databaseconnect;

import java.sql.*;

public class DbConnect {


    public static final String DB_URL = "jdbc:mysql://localhost:3306/hotel_management?useUnicode=true&characterEncoding=utf-8";
    public static final String DB_USER = "user";
    public static final String DB_PASSWORD = "password";


    public static Connection connect() {
//        System.out.println("Using System.setProerty to load driver");
//        System.setProperty("jdbc.driver", "com.mysql.jdbc.Driver");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        checkConnection(connection);
        return connection;
    }

    public static void checkConnection(Connection connection) {

        if (connection != null) {
            System.out.println("Connection!");

        } else {
            System.out.println("Connection failed");
        }
    }


    public static ResultSet executeQuery(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    //veri tabanından bir değer döndürüp getirmesini istemediğim için buradaki metot yapısını resultSet kullanmadım...

    public static int executeUpdate(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(query);
    }

    public static void main(String[] args) {
        connect();
    }

}
