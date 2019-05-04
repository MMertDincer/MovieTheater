package movietheater;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserPageController {

    public ListView inTheatersListView;
    public Button myReservationsButton;
    public Button logoutButton;

    Stage stage;
    Parent root;

    Map<ImageView, Integer> imageTitleDict = new HashMap<ImageView, Integer>();
    DatabaseConnection connection = new DatabaseConnection();

    String movieTitle;

    public void PopulateInTheatersPane() throws SQLException {
        ResultSet resultSet = connection.GetScreeningMoviePosterAndTitle();
        ObservableList<ImageView> data = FXCollections.observableArrayList();
        inTheatersListView.setItems(data);

        while(resultSet.next()) {
            Image image = new Image(resultSet.getString(1), true);
            movieTitle = resultSet.getString(2);

            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(370);
            imageView.setPreserveRatio(true);

            imageTitleDict.put(imageView, connection.GetMovieIDFromTitle(movieTitle));
            data.add(imageView);
        }
    }

    public void initialize() throws SQLException {
        PopulateInTheatersPane();

        inTheatersListView.setOnMouseClicked(mouseEvent -> {
            var clickedItem = inTheatersListView.getSelectionModel().getSelectedItem();
            var movieID = imageTitleDict.get(clickedItem);

            MovieDetailsPageController detailsPageController = null;
            FXMLLoader loader = null;
            try {
                loader = new FXMLLoader(getClass().getResource("MovieDetailsPage.fxml"));
                root = loader.load();

                detailsPageController = loader.getController();
                detailsPageController.PopulateMovieDetailsPage(movieID);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene scene = new Scene(root);

            stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Movie Theater - " + connection.GetMovieTitleFromID(movieID));
            stage.show();
        });
    }

    public void myReservationsButtonClicked(MouseEvent mouseEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MyReservationsPage.fxml"));
        Scene scene = new Scene(root);

        stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Movie Theater - My Reservations");
        stage.show();
    }

    public void logoutButtonClicked(MouseEvent mouseEvent) throws IOException {
        UserSession.getInstance().cleanUserSession();

        root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene scene = new Scene(root);

        stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Movie Theater - Login");
        stage.show();
    }
}
