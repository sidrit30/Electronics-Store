package Main;

import Controller.LoginController;

import DAO.EmployeeDAO;
import DAO.ItemDAO;
import Model.Items.Item;

import Model.Users.*;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    private static final Screen screen = Screen.getPrimary();
    public static final Rectangle2D VISUAL_BOUNDS = screen.getVisualBounds();

    public static void main(String[] args) {
//        Admin admin = new Admin("Zela", "Sidrit", "sidrit", "sidrit", 3200);
//        EmployeeDAO employeeDAO = new EmployeeDAO();
//        employeeDAO.createEmployee(admin);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        LoginController loginController = new LoginController();
        Scene scene = new Scene(loginController.getLoginPage(), 800, 600);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jupiter Electronics");
        primaryStage.show();
    }
}
