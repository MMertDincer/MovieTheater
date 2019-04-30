package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginPageController {

    public TextField usernameField;
    public Button loginButton;
    public Label registerLabel;
    public PasswordField passwordField;

    DatabaseConnection connection = new DatabaseConnection();


    public void loginButtonClicked(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (connection.IsUser(username, password)) {
            //TODO: Change to user scene
            System.out.println("This guy is a fucking user!");
        } else if (connection.IsAdmin(username, password)) {
            //TODO: Change to admin scene
            System.out.println("This guy is a fucking admin!");
        }
    }
}
