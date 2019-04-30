package sample;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;


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
        String query = "SELECT password FROM user WHERE username = ?";
        String hashed_pw = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.absolute(0);

            while(resultSet.next()) {
                hashed_pw = resultSet.getString("password");

                if (BCrypt.checkpw(password, hashed_pw)) {
                    System.out.println("true");
                    return true;
                }
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

            if (username.isEmpty()) {
                preparedStatement.setNull(1, Types.VARCHAR);
            } else {
                preparedStatement.setString(1, username);
            }

            if (password.isEmpty()) {
                preparedStatement.setNull(2, Types.VARCHAR);
            } else {
                preparedStatement.setString(2, password);
            }

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

    public boolean IsUserRegisterSuccessful(String username, String password) {
        String query = "INSERT INTO user(username, password) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // If fields are empty, make the values null or it'll send an empty string to database
            if (username.isEmpty()) {
                preparedStatement.setNull(1, Types.VARCHAR);
            } else {
                preparedStatement.setString(1, username);
            }

            if (password.isEmpty()) {
                preparedStatement.setNull(2, Types.CHAR);
            } else {
                preparedStatement.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            }

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.print(ex);
        }

        return false;
    }
}
