package sample;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import java.time.LocalDate;

import com.mysql.cj.protocol.Resultset;
import org.mindrot.jbcrypt.BCrypt;

import javax.xml.transform.Result;


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
                    return true;
                }
            }

        } catch (SQLException ex) {
            System.err.println(ex);
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
            System.err.println(ex);
        }

        return false;
    }

    public boolean IsUserRegisterSuccessful(String username, String password) {
        String query = "INSERT INTO user(username, password) VALUES(?, ?)";

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
            System.err.println(ex);
        }

        return false;
    }

    public ResultSet GetUserTable() {
        String query = "SELECT * FROM user";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return null;
    }

    public ResultSet GetMovieTable() {
        String query = "SELECT * FROM movie";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return null;
    }

    public ResultSet GetAuditoriumTable() {
        String query = "SELECT * FROM auditorium";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return null;
    }

    public ResultSet GetScreeningTable() {
        String query = "SELECT * FROM screening";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return null;
    }

    public ResultSet GetReservationTable() {
        String query = "SELECT * FROM reservation";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return null;
    }

    public boolean IsAddMovieSuccessful(String title, String director, String cast, String desc, String url, Date date, String duration) {
        String query = "INSERT INTO movie(title, director, cast, description, " +
                "movie_img_url, release_date, duration_min) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            if (title.isEmpty()) {
                preparedStatement.setNull(1, Types.VARCHAR);
            } else {
                preparedStatement.setString(1, title);
            }

            preparedStatement.setString(2, director);
            preparedStatement.setString(3, cast);
            preparedStatement.setString(4, desc);
            preparedStatement.setString(5, url);
            preparedStatement.setDate(6, date);
            preparedStatement.setString(7, duration);

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return false;
    }

    public boolean IsAddAuditoriumSuccessful(String name, String seatsNumber) {
        String query = "INSERT INTO auditorium(name, seats_no) VALUES(?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            if (name.isEmpty()) {
                preparedStatement.setNull(1, Types.VARCHAR);
            } else {
                preparedStatement.setString(1, name);
            }

            if (seatsNumber.isEmpty()) {
                preparedStatement.setNull(2, Types.VARCHAR);
            } else {
                preparedStatement.setString(2, seatsNumber);
            }

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return false;
    }

    public boolean IsAddScreeningSuccessful(int movieID, int auditoriumID, Timestamp timestamp) {
        String query = "INSERT INTO screening(movie_id, auditorium_id, screening_start)" +
                "VALUES(?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, movieID);
            preparedStatement.setInt(2, auditoriumID);
            preparedStatement.setTimestamp(3, timestamp);

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return false;
    }

    public boolean IsAddReservationSuccessful(int screeningID, int reservTypeID, int userID, String pnrCode) {
        String query = "INSERT INTO reservation(screening_id, reservation_type_id, user_id, pnr_code)" +
                "VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, screeningID);
            preparedStatement.setInt(2, reservTypeID);
            preparedStatement.setInt(3, userID);
            preparedStatement.setString(4, pnrCode);

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return false;
    }

    public int GetUserID(String username, String password) {
        String query = "SELECT id, password FROM user WHERE username = ?";
        String hashed_pw = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.absolute(0);

            while(resultSet.next()) {
                hashed_pw = resultSet.getString("password");

                if (BCrypt.checkpw(password, hashed_pw)) {
                    return resultSet.getInt("id");
                }
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return 0;
    }
}
