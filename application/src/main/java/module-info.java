module application {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql.rowset;
    requires javafx.web;

    opens application to javafx.fxml;
    exports application;

    opens entities to javafx.fxml;
    exports entities;
}
