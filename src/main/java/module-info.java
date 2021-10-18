module com.example.makeiteasy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.makeiteasy to javafx.fxml;
    exports com.example.makeiteasy;
    exports com.example.makeiteasy.controller;
    opens com.example.makeiteasy.controller to javafx.fxml;
}