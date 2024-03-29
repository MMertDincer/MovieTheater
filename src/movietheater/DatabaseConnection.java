package movietheater;

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
            return preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return null;
    }

    public ResultSet GetMovieTable() {
        String query = "SELECT * FROM movie";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE);
            return preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return null;
    }

    public ResultSet GetAuditoriumTable() {
        String query = "SELECT * FROM auditorium";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE);
            return preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return null;
    }

    public ResultSet GetScreeningTable() {
        String query = "SELECT * FROM screening";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE);
            return preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return null;
    }

    public ResultSet GetReservationTable() {
        String query = "SELECT * FROM reservationwithoutid";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE);
            return preparedStatement.executeQuery();
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

    public boolean IsAddReservationSuccessful(int screeningID, int reservTypeID, int userID, String pnrCode, int seatID) {
        String query = "INSERT INTO reservation(screening_id, reservation_type_id, user_id, pnr_code, seat_id)" +
                "VALUES(?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, screeningID);
            preparedStatement.setInt(2, reservTypeID);
            preparedStatement.setInt(3, userID);
            preparedStatement.setString(4, pnrCode);
            preparedStatement.setInt(5, seatID);

            if (IsReserveSeatSuccessful(seatID)) {
                preparedStatement.executeUpdate();
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return false;
    }

    public boolean IsReserveSeatSuccessful(int seatID) {
        String query1 = "SELECT reserved FROM seat WHERE id = ?";
        String query2 = "UPDATE seat SET reserved = TRUE WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            PreparedStatement preparedStatement1 = connection.prepareStatement(query2);

            preparedStatement.setInt(1, seatID);
            preparedStatement1.setInt(1, seatID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getBoolean("reserved")) {
                    return false;
                } else {
                    preparedStatement1.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return false;
    }

    public int GetUserID(String username, String password) {
        String query = "SELECT id, password FROM user WHERE username = ?";
        String hashed_pw;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.absolute(0);

            while (resultSet.next()) {
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

    public boolean IsDeleteUserSuccessful(int userID) {
        String query = "DELETE FROM user WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return false;
    }

    public boolean IsDeleteReservationSuccessful(int reservationID) {
        String query = "DELETE FROM reservation WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reservationID);

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return false;
    }

    public ResultSet GetScreeningMoviePosterAndTitle() {
        String query = "SELECT DISTINCT movie_img_url, title FROM movie INNER JOIN screening ON movie.id = screening.movie_id;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeQuery();

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return null;
    }

    public int GetMovieIDFromTitle(String title) {
        String query = "SELECT id FROM movie WHERE title LIKE ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + title + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return -1;
    }

    public String GetMovieTitleFromID(int movieID) {
        String query = "SELECT title FROM movie WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, movieID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                return resultSet.getString("title");
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return "";
    }

    public ResultSet GetMovieById(int movieID) {
        String query = "SELECT * FROM movie WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, movieID);
            return preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return null;
    }

    public ResultSet GetReservationsByUserId(int userID) {
        String query = "SELECT * FROM reservation\n" +
                "INNER JOIN screening ON screening.id = reservation.screening_id\n" +
                "INNER JOIN movie ON movie.id = screening.movie_id\n" +
                "INNER JOIN auditorium ON auditorium.id = screening.auditorium_id\n" +
                "INNER JOIN reservation_type ON reservation_type.id = reservation.reservation_type_id\n" +
                "INNER JOIN seat ON seat.id = reservation.seat_id\n" +
                "WHERE reservation.user_id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);

            return preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return null;
    }

    public boolean IsDeleteMovieSuccessful(int movieID) {
        String query = "DELETE FROM movie WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, movieID);

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return false;
    }

    public boolean IsDeleteScreeningSuccessful(int screeningID) {
        String query = "DELETE FROM screening WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, screeningID);

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return false;
    }
}
