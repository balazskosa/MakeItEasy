module com.example.makeiteasy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.makeiteasy to javafx.fxml;
    exports com.example.makeiteasy;
}