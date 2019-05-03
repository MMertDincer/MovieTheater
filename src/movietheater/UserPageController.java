package movietheater;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserPageController {

    public ListView inTheatersListView;

    Map<ImageView, Integer> imageTitleDict = new HashMap<ImageView, Integer>();
    DatabaseConnection connection = new DatabaseConnection();

    public void PopulateInTheatersPane() throws SQLException {
        ResultSet resultSet = connection.GetMoviePosterAndTitle();
        ObservableList<ImageView> data = FXCollections.observableArrayList();
        inTheatersListView.setItems(data);

        while(resultSet.next()) {
            Image image = new Image(resultSet.getString(1), true);
            String title = resultSet.getString(2);

            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(370);
            imageView.setPreserveRatio(true);

            imageTitleDict.put(imageView, connection.GetMovieIDFromTitle(title));
            data.add(imageView);
        }
    }

    public void initialize() throws SQLException {
        PopulateInTheatersPane();

        inTheatersListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                var clickedItem = inTheatersListView.getSelectionModel().getSelectedItem();
                System.out.println("Clicked on movie with the ID of " + imageTitleDict.get(clickedItem));
            }
        });
    }
}
