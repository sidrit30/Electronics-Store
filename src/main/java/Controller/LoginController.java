package Controller;

import DAO.EmployeeDAO;
import Model.Exceptions.InvalidPasswordException;
import Model.Exceptions.InvalidUsernameException;
import Model.Users.Employee;
import Model.Users.Manager;
import View.LoginPage;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import static Main.Main.VISUAL_BOUNDS;

public class LoginController {
    private EmployeeDAO employeeDAO;
    private LoginPage loginPage;

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public LoginController() {
        loginPage = new LoginPage();
        employeeDAO = new EmployeeDAO();
        loginPage.getPasswordField().setOnKeyPressed(e->onLoginEnter(e));
        loginPage.getUsernameField().setOnKeyPressed(e->onLoginEnter(e));
        loginPage.getUnmaskedPasswordField().setOnKeyPressed(e->onLoginEnter(e));
        loginPage.getLoginButton().setOnAction(e -> onLoginButton(e));
    }

    private void onLoginButton(ActionEvent e) {
        login();

    }

    private void onLoginEnter(KeyEvent e) {
        if(e.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    private void login() {
        String username = loginPage.getUsernameField().getText();
        String password = loginPage.getPasswordField().getText();
        Employee emp;

        try {
            emp = employeeDAO.authLogin(username, password);
            System.out.println("Login Successful");
            Scene homeScene = new Scene(new HomePageController(emp).getHomePage());
            //Scene test = new Scene(new ManageEmployeeController(emp).getManageEmployeeTableView());
            Stage oldStage = (Stage) loginPage.getScene().getWindow();
            oldStage.close();
            Stage primaryStage = new Stage();
            primaryStage.setScene(homeScene);
            primaryStage.setTitle("Jupiter Electronics");
            primaryStage.setX(VISUAL_BOUNDS.getMinX());
            primaryStage.setY(VISUAL_BOUNDS.getMinY());
            primaryStage.setWidth(VISUAL_BOUNDS.getWidth());
            primaryStage.setHeight(VISUAL_BOUNDS.getHeight());
            primaryStage.show();

            if(emp instanceof Manager) {
                new ManagerController(emp);
            }
        }
        catch (InvalidUsernameException | InvalidPasswordException e1) {
            loginPage.getErrorLabel().setVisible(true);
            loginPage.getErrorLabel().setText(e1.getMessage());

        }
    }


}
