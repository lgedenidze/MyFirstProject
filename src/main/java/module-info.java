module com.example.myfirstproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.myfirstproject to javafx.fxml;
    exports com.example.myfirstproject;
}