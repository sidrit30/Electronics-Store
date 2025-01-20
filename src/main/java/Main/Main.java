package Main;

import Controller.LoginController;
import DAO.EmployeeDAO;
import Model.Users.Admin;
import Model.Users.Cashier;
import Model.Users.Employee;
import Model.Users.Manager;
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
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jupiter Electronics");
        primaryStage.show();
    }
}
