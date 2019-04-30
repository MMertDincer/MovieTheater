package sample;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class AdminPageController {

    public TableView userTableView;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button registerButton;
    public Button deleteButton;

    DatabaseConnection connection = new DatabaseConnection();

    public void registerButtonClicked(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(connection.IsUserRegisterSuccessful(username, password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully registered!");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong.");
            alert.showAndWait();
        }
    }

    public void deleteButtonClicked(MouseEvent mouseEvent) {
        //TODO: Delete user by selected row on the table
    }
}
