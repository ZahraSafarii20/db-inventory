module com.example.dbinventory {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires mongo.java.driver;
    requires jdk.management.agent;
    requires fontawesomefx;
    requires java.desktop;

    opens com.example.dbinventory to javafx.fxml;
    exports com.example.dbinventory;
}