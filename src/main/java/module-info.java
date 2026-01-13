module com.cen.electronicsstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    exports View;
    opens View to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
    exports Model.Users;
    opens Model.Users to javafx.fxml;
    exports Model.Exceptions;
    opens Model.Exceptions to javafx.fxml;
    exports DAO;
    opens DAO to javafx.fxml;
    exports Model.Items;
    opens Model.Items to javafx.fxml;
    exports Main;
    opens Main to javafx.fxml;
}