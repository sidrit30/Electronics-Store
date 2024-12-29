module com.cen.electronicsstore {
    requires javafx.controls;
    requires javafx.fxml;


    exports java;
    opens java to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
}