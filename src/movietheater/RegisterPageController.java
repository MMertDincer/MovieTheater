package movietheater;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterPageController {

    Stage stage;
    Parent root;

    public TextField usernameField;
    public Button registerButton;
    public Label loginLabel;
    public PasswordField passwordField;

    DatabaseConnection connection = new DatabaseConnection();

    public void registerButtonClicked(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(connection.IsUserRegisterSuccessful(username, password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully registered!");
            alert.showAndWait();

            //TODO: Go to login page or user page
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong.");
            alert.showAndWait();
        }
    }

    public void loginLabelClicked(MouseEvent mouseEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene scene = new Scene(root);

        stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Movie Theater - Login");
        stage.show();
    }
}
