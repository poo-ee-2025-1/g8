module com.ricardo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires ormlite.jdbc;

    opens edu.grupo8 to javafx.fxml;
    exports edu.grupo8;
}
