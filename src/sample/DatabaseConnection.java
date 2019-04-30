package sample;

import com.mysql.cj.MysqlConnection;
import com.mysql.cj.jdbc.JdbcPreparedStatement;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;


public class DatabaseConnection {

    public static Connection connection;

    public DatabaseConnection() {
        MysqlDataSource dataSource = new MysqlDataSource();
        try {
            dataSource.setDatabaseName("movie_theater");
            dataSource.setUser("root");
            dataSource.setPassword("");

            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public boolean IsUser(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.absolute(0);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.err.print(ex);
        }

        return false;
    }

    public boolean IsAdmin(String username, String password) {
        String query = "SELECT * FROM employee WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.absolute(0);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.err.print(ex);
        }

        return false;
    }
}
