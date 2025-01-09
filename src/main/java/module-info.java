module com.cen.electronicsstore {
    requires javafx.controls;
    requires javafx.fxml;


    exports View;
    opens View to javafx.fxml;
    exports Logic;
    opens Logic to javafx.fxml;
    exports Logic.Users;
    opens Logic.Users to javafx.fxml;

}