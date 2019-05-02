package movietheater;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPageController {

    public ListView inTheatersListView;
    DatabaseConnection connection = new DatabaseConnection();

    public void PopulateInTheatersPane() throws SQLException {
        ResultSet resultSet = connection.GetMoviePosterAndTitle();
        ObservableList<ImageView> data = FXCollections.observableArrayList();
        inTheatersListView.setItems(data);

        // Hide scrollbars


        while(resultSet.next()) {
            Image image = new Image(resultSet.getString(1));
            String title = resultSet.getString(2);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(370);
            imageView.setPreserveRatio(true);
            Tooltip.install(imageView, new Tooltip(title));

            data.add(imageView);
        }
    }

    public void initialize() throws SQLException {
        PopulateInTheatersPane();

        inTheatersListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                var clickedItem = inTheatersListView.getSelectionModel().getSelectedItem();
                System.out.println("Clicked on " + clickedItem);
            }
        });
    }
}
