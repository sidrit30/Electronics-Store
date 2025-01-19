package Controller;

import DAO.EmployeeDAO;
import Model.Exceptions.InvalidPasswordException;
import Model.Exceptions.InvalidUsernameException;
import Model.Users.Admin;
import Model.Users.Employee;
import View.LoginPage;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

        loginPage.getLoginButton().setOnAction(e -> onLoginButton(e));
    }

    private void onLoginButton(ActionEvent e) {
        String username = loginPage.getTextField().getText();
        String password = loginPage.getPasswordField().getText();
        Employee emp;

        try {
            emp = employeeDAO.authLogin(username, password);
            System.out.println("Login Successful");
        }
        catch (InvalidUsernameException | InvalidPasswordException e1) {
            loginPage.getErrorLabel().setVisible(true);
            loginPage.getErrorLabel().setText(e1.getMessage());

        }

    }

    private void onLoginEnter(KeyEvent e) {
        if(e.getCode() == KeyCode.ENTER) {
            String username = loginPage.getTextField().getText();
            String password = loginPage.getPasswordField().getText();
            Employee emp;

            try {
                emp = employeeDAO.authLogin(username, password);
                System.out.println("Login Successful");


            }
            catch (InvalidUsernameException | InvalidPasswordException e1) {
                loginPage.getErrorLabel().setVisible(true);
                loginPage.getErrorLabel().setText(e1.getMessage());

            }
        }
    }

}
