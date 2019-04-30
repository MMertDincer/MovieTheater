package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {

    public TextField usernameField;
    public Button loginButton;
    public Label registerLabel;
    public PasswordField passwordField;

    Stage stage;
    Parent root;

    DatabaseConnection connection = new DatabaseConnection();


    public void loginButtonClicked(MouseEvent mouseEvent) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (connection.IsUser(username, password)) {
            //TODO: Change to user scene
        } else if (connection.IsAdmin(username, password)) {
            root = FXMLLoader.load(getClass().getResource("AdminPage.fxml"));
            Scene scene = new Scene(root);

            stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Movie Theater - Employee Panel");
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to log in!");
            alert.showAndWait();
        }
    }

    public void registerLabelClicked(MouseEvent mouseEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("RegisterPage.fxml"));
        Scene scene = new Scene(root);

        stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Movie Theater - Register");
        stage.show();
    }
}
