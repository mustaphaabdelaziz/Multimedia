module com.example.imageprocessing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.jfoenix;


    opens com.example.imageprocessing to javafx.fxml;
    exports com.example.imageprocessing;
}