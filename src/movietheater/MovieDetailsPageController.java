package movietheater;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDetailsPageController {

    Stage stage;
    Parent root;

    public ImageView moviePosterImageView;
    public Label titleLabel;
    public Button backToUserPageButton;
    public Label detailsLabel;

    public int movieID;

    DatabaseConnection connection = new DatabaseConnection();

    public MovieDetailsPageController() {

    }

    public void PopulateMovieDetailsPage(int movieID) {
        this.movieID = movieID;
        ResultSet resultSet = connection.GetMovieById(movieID);

        try {
            while(resultSet.next()) {
                moviePosterImageView.setImage(new Image(resultSet.getString("movie_img_url"), true));
                titleLabel.setText(resultSet.getString("title"));
                detailsLabel.setText("Director: " + resultSet.getString("director") +
                        "\n\nCast: " + resultSet.getString("cast") +
                        "\n\nSummary: " + resultSet.getString("description") +
                        "\n\nRelease date: " + resultSet.getString("release_date") +
                        "\n\nDuration: " + resultSet.getInt("duration_min") + " Min.");
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void initialize() {

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
