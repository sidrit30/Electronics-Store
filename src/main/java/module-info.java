module com.cen.electronicsstore {
    requires javafx.controls;
    requires javafx.fxml;


    exports View;
    opens View to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
    exports Model.Users;
    opens Model.Users to javafx.fxml;
    exports main;
    opens main to javafx.fxml;

}