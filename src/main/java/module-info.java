module pl.polsl {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens pl.polsl to javafx.fxml;
    exports pl.polsl;
}
