package movietheater;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyReservationsPageController {

    public ListView myReservationsListView;
    public Button backToUserPageButton;
    public Label reservDetailLabel;

    Stage stage;
    Parent root;

    DatabaseConnection connection = new DatabaseConnection();

    public void PopulateMyReservationsTable() throws SQLException {
        System.out.println(UserSession.getInstance().getUsername() +
                UserSession.getInstance().getUserID());

        ResultSet resultSet = connection.GetReservationsByUserId(UserSession.getInstance().getUserID());
        ObservableList<String> data = FXCollections.observableArrayList();
        myReservationsListView.setItems(data);

        while (resultSet.next()) {
            String title = resultSet.getString("title");
            data.add(title);
        }
    }

    public void initialize() throws SQLException {
        PopulateMyReservationsTable();

        myReservationsListView.setOnMouseClicked(mouseEvent -> {
            ResultSet resultSet = connection.GetReservationsByUserId(UserSession.getInstance().getUserID());
            var clickedItem = myReservationsListView.getSelectionModel().getSelectedItem();
            String selectedMovieTitle = clickedItem.toString();

            try {
                while(resultSet.next()) {
                    if (selectedMovieTitle.equals(resultSet.getString("title"))) {
                        reservDetailLabel.setText(
                                "Title: " + selectedMovieTitle +
                                "\nDate and Time: " + resultSet.getTimestamp("screening_start") +
                                "\nAuditorium: " + resultSet.getString("name") +
                                "\nReservation Type: " + resultSet.getString("reservation_type") +
                                "\n" +
                                "Seat Row: " + resultSet.getInt("row") +
                                "\nSeat Number: " + resultSet.getInt("number"));
                    }
                }
            } catch (SQLException ex) {
                System.err.println(ex);
            }

        });
    }

    public void backToUserPageButtonClicked(MouseEvent mouseEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("UserPage.fxml"));
        Scene scene = new Scene(root);

        stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Movie Theater - Welcome, " + UserSession.getInstance().getUsername());
        stage.show();
    }
}
