module com.example.lab_5 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.lab_5 to javafx.fxml;
    exports com.example.lab_5;
}