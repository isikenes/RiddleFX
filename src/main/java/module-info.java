module com.ei.riddlefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.ei.riddlefx to javafx.fxml;
    exports com.ei.riddlefx;
}