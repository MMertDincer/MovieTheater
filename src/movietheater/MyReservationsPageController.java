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
    public Label titleLabel;
    public Label reservTypeLabel;
    public Label pnrCodeLabel;
    public Label dateAndTimeLabel;
    public Label auditoriumLabel;
    public Button backToUserPageButton;
    public Label rowNumberLabel;

    Stage stage;
    Parent root;

    DatabaseConnection connection = new DatabaseConnection();

    public void PopulateMyReservationsTable() throws SQLException {
        ResultSet resultSet = connection.GetReservationsById(UserSession.getInstance().getUserID());
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
            ResultSet resultSet = connection.GetReservationsById(UserSession.getInstance().getUserID());
            var clickedItem = myReservationsListView.getSelectionModel().getSelectedItem();
            String movieTitle = clickedItem.toString();

            try {
                while(resultSet.next()) {
                    titleLabel.setText("Title: " + movieTitle);
                    dateAndTimeLabel.setText("Date and Time: " + resultSet.getTimestamp("screening_start").toString());
                    auditoriumLabel.setText("Auditorium: " + resultSet.getString("name"));
                    reservTypeLabel.setText("Reservation Type: " + resultSet.getString("reservation_type"));
                    pnrCodeLabel.setText("PNR Code: " + resultSet.getString("pnr_code"));
                    rowNumberLabel.setText("Row: " + resultSet.getInt("row") + "\nSeat No: " + resultSet.getInt("number"));
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
