package movietheater;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.security.SecureRandom;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Random;

public class AdminPageController {

    // Users tab
    public TableView userTableView;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button refreshUserTable;
    public Button registerUserButton;
    public Button deleteButton;

    // Movies tab
    public TableView movieTableView;
    public TextField titleField;
    public TextField directorField;
    public TextField castField;
    public TextField descField;
    public TextField posterUrlField;
    public DatePicker releaseDateField;
    public TextField durationField;
    public Button addMovieButton;
    public AnchorPane refreshMovieTable;
    public Button refreshMovieTableButton;

    // Auditoriums tab
    public TableView auditoriumTableView;
    public Button refreshAuditoriumTableButton;
    public TextField auditNameField;
    public TextField auditSeatNoField;
    public Button addAuditoriumButton;

    // Screenings tab
    public TableView screeningTableView;
    public Button refreshScreeningTableButton;
    public TextField movieIDField;
    public TextField auditoriumIDField;
    public Button addScreeningButton;
    public TextField timestampField;

    // Reservations tab
    public TableView reservationTableView;
    public Button refreshReservationTableButton;
    public TextField screeningIDField;
    public TextField reservationTypeIDField;
    public TextField userIDField;
    public TextField pnrCodeField;
    public Button addReservationButton;


    DatabaseConnection connection = new DatabaseConnection();
    ObservableList<ObservableList> data;

    // For generating the PNR Code
    final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    Random rng = new SecureRandom();

    public AdminPageController() {

    }

    public void PopulateUsersTable() {
        data = FXCollections.observableArrayList();
        ResultSet usersResultSet = connection.GetUserTable();

        // Clear the table first for the refresh
        userTableView.getItems().clear();
        userTableView.getColumns().clear();

        try {
            for (int i = 0; i < usersResultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(usersResultSet.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                userTableView.getColumns().addAll(col);
                //System.out.println("Column [" + i + "] ");
            }

            while(usersResultSet.next()){
                ObservableList<String> row = FXCollections.observableArrayList();

                for(int i = 1 ; i <= usersResultSet.getMetaData().getColumnCount(); i++)
                    row.add(usersResultSet.getString(i));

                //System.out.println("Row [1] added "+row );
                data.add(row);
            }

            userTableView.setItems(data);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    } // end PopulateUsersTable()

    public void PopulateMoviesTable() {
        data = FXCollections.observableArrayList();
        ResultSet moviesResultSet = connection.GetMovieTable();

        // Clear the table first for the refresh
        movieTableView.getItems().clear();
        movieTableView.getColumns().clear();


        try {
            for (int i = 0; i < moviesResultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(moviesResultSet.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                movieTableView.getColumns().addAll(col);
                //System.out.println("Column [" + i + "] ");
            }

            while(moviesResultSet.next()){
                ObservableList<String> row = FXCollections.observableArrayList();

                for(int i = 1 ; i <= moviesResultSet.getMetaData().getColumnCount(); i++)
                    row.add(moviesResultSet.getString(i));

                //System.out.println("Row [1] added "+row );
                data.add(row);
            }

            movieTableView.setItems(data);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    } // end PopulateMoviesTable()

    public void PopulateAuditoriumTable() {
        data = FXCollections.observableArrayList();
        ResultSet auditoriumResultSet = connection.GetAuditoriumTable();

        // Clear the table first for the refresh
        auditoriumTableView.getItems().clear();
        auditoriumTableView.getColumns().clear();


        try {
            for (int i = 0; i < auditoriumResultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(auditoriumResultSet.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                auditoriumTableView.getColumns().addAll(col);
                //System.out.println("Column [" + i + "] ");
            }

            while(auditoriumResultSet.next()){
                ObservableList<String> row = FXCollections.observableArrayList();

                for(int i = 1 ; i <= auditoriumResultSet.getMetaData().getColumnCount(); i++)
                    row.add(auditoriumResultSet.getString(i));

                //System.out.println("Row [1] added "+row );
                data.add(row);
            }

            auditoriumTableView.setItems(data);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    } // end PopulateAuditoriumTable()

    public void PopulateScreeningTable() {
        data = FXCollections.observableArrayList();
        ResultSet screeningResultSet = connection.GetScreeningTable();

        // Clear the table first for the refresh
        screeningTableView.getItems().clear();
        screeningTableView.getColumns().clear();


        try {
            for (int i = 0; i < screeningResultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(screeningResultSet.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                screeningTableView.getColumns().addAll(col);
                //System.out.println("Column [" + i + "] ");
            }

            while(screeningResultSet.next()){
                ObservableList<String> row = FXCollections.observableArrayList();

                for(int i = 1 ; i <= screeningResultSet.getMetaData().getColumnCount(); i++)
                    row.add(screeningResultSet.getString(i));

                //System.out.println("Row [1] added "+row );
                data.add(row);
            }

            screeningTableView.setItems(data);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void PopulateReservationTable() {
        data = FXCollections.observableArrayList();
        ResultSet reservationResultSet = connection.GetReservationTable();

        // Clear the table first for the refresh
        reservationTableView.getItems().clear();
        reservationTableView.getColumns().clear();


        try {
            for (int i = 0; i < reservationResultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(reservationResultSet.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                reservationTableView.getColumns().addAll(col);
                //System.out.println("Column [" + i + "] ");
            }

            while(reservationResultSet.next()){
                ObservableList<String> row = FXCollections.observableArrayList();

                for(int i = 1 ; i <= reservationResultSet.getMetaData().getColumnCount(); i++)
                    row.add(reservationResultSet.getString(i));

                //System.out.println("Row [1] added "+row );
                data.add(row);
            }

            reservationTableView.setItems(data);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @FXML
    public void initialize() {
        PopulateUsersTable();
        PopulateMoviesTable();
        PopulateAuditoriumTable();
        PopulateScreeningTable();
        PopulateReservationTable();
    }

    public void registerUserButtonClicked(MouseEvent mouseEvent) {
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

    public void deleteUserButtonClicked(MouseEvent mouseEvent) throws NoSuchFieldException {
        String x = userTableView.getSelectionModel().getSelectedItem().toString();
        int userIDToBeDeleted = Integer.parseInt(x.substring(1, x.indexOf(",")));

        if (connection.IsDeleteUserSuccessful(userIDToBeDeleted)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully deleted user!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong.");
            alert.showAndWait();
        }
    }

    public void addMovieButtonClicked(MouseEvent mouseEvent) {
        String title = titleField.getText();
        String director = directorField.getText();
        String cast = castField.getText();
        String description = descField.getText();
        String posterUrl = posterUrlField.getText();
        Date releaseDate = Date.valueOf(releaseDateField.getValue());
        String duration = durationField.getText();

        if (connection.IsAddMovieSuccessful(title, director, cast, description, posterUrl, releaseDate, duration)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully added!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong.");
            alert.showAndWait();
        }
    }

    public void refreshUserTableButtonClicked(MouseEvent mouseEvent) {
        PopulateUsersTable();
    }

    public void refreshMovieTableButtonClicked(MouseEvent mouseEvent) {
        PopulateMoviesTable();
    }

    public void refreshAuditoriumTableButtonClicked(MouseEvent mouseEvent) {
        PopulateAuditoriumTable();
    }

    public void refreshScreeningTableButtonClicked(MouseEvent mouseEvent) {
        PopulateScreeningTable();
    }

    public void refreshReservationTableButtonClicked(MouseEvent mouseEvent) {
        PopulateReservationTable();
    }

    public void addAuditoriumButtonClicked(MouseEvent mouseEvent) {
        String name = auditNameField.getText();
        String seatsNumber = auditSeatNoField.getText();

        if (connection.IsAddAuditoriumSuccessful(name, seatsNumber)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully added!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong.");
            alert.showAndWait();
        }
    }

    public void addScreeningButtonClicked(MouseEvent mouseEvent) {
        int movieID = Integer.parseInt(movieIDField.getText());
        int auditoriumID = Integer.parseInt(auditoriumIDField.getText());
        Timestamp timestamp = Timestamp.valueOf(timestampField.getText());

        if (connection.IsAddScreeningSuccessful(movieID, auditoriumID, timestamp)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully added!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong.");
            alert.showAndWait();
        }
    }

    public char GetRandomChar() {
        return alphabet.charAt(rng.nextInt(alphabet.length()));
    }

    public String GeneratePNRCode(int length, int spacing, char spacerChar) {
        StringBuilder sb = new StringBuilder();
        int spacer = 0;

        while(length > 0){
            if(spacer == spacing){
                sb.append(spacerChar);
                spacer = 0;
            }

            length--;
            spacer++;
            sb.append(GetRandomChar());
        }
        return sb.toString();
    }

    public void addReservationButtonClicked(MouseEvent mouseEvent) {
        int screeningID = Integer.parseInt(screeningIDField.getText());
        int reservTypeID = Integer.parseInt(reservationTypeIDField.getText());
        int userID = Integer.parseInt(userIDField.getText());
        String pnrCode = GeneratePNRCode(8, 0, 'T');
        pnrCodeField.setText(pnrCode);
        System.out.println(pnrCode.length());

        if (connection.IsAddReservationSuccessful(screeningID, reservTypeID, userID, pnrCode)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully added!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong.");
            alert.showAndWait();
        }
    }
}
