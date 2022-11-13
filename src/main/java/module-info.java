module pl.polsl {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires qrgen;

    opens pl.polsl to javafx.fxml;
    opens pl.polsl.jktab.model to javafx.base;
    exports pl.polsl;
}
