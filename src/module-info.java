module MovieTheater {
    requires javafx.fxml;
    requires javafx.controls;
    requires mysql.connector.java;
    requires java.sql;
    requires java.naming;
    requires jbcrypt;

    opens movietheater;
}