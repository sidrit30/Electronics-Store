package Main;

import Controller.LoginController;
import DAO.EmployeeDAO;
import Model.Users.Admin;
import Model.Users.Employee;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        LoginController loginController = new LoginController();
        Scene scene = new Scene(loginController.getLoginPage(), 800, 600);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Electronics Store");
        primaryStage.show();
    }
}
