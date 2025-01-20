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
//        Admin admin = new Admin("Zela", "Sidrit", "sidrit", "sidrit", 100000);
//        Manager employee1 = new Manager("Smith", "John", "johnsmith", "pass123", 50000.00);
//        Manager employee2 = new Manager("Doe", "Jane", "janedoe", "secure456", 55000.00);
//        Cashier employee3 = new Cashier("Brown", "Michael", "michaelb", "mypassword789", 60000.00);
//        Cashier employee4 = new Cashier("Taylor", "Emily", "emilyt", "emilysecure321", 52000.00);
//        Cashier employee5 = new Cashier("Wilson", "Chris", "chrisw", "topsecret654", 58000.00);
//        EmployeeDAO dao = new EmployeeDAO();
//        dao.createEmployee(admin);
//        dao.createEmployee(employee1);
//        dao.createEmployee(employee2);
//        dao.createEmployee(employee3);
//        dao.createEmployee(employee4);
//        dao.createEmployee(employee5);
//        dao.UpdateAll();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        LoginController loginController = new LoginController();
        Scene scene = new Scene(loginController.getLoginPage(), 800, 600);
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Electronics Store");
        primaryStage.show();
    }
}
