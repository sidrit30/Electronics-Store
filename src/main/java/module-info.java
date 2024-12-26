module com.cen215.electronicsstore {
    requires javafx.controls;
    requires javafx.fxml;


    opens Staff to javafx.fxml;
    exports Staff;
    exports java;
    opens java to javafx.fxml;
}