module com.ricardo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.kordamp.ikonli.javafx;

    opens com.ricardo to javafx.fxml;
    exports com.ricardo;
}
