module com.cen215.electronicsstore {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cen215.electronicsstore to javafx.fxml;
    exports com.cen215.electronicsstore;
}