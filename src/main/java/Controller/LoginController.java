package Controller;

import DAO.EmployeeDAO;
import Model.Exceptions.InvalidPasswordException;
import Model.Exceptions.InvalidUsernameException;
import Model.Users.Admin;
import Model.Users.Employee;
import View.LoginPage;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
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
            Scene homeScene = new Scene(new HomePageController(emp).getHomePage(), 1500, 700);
            Stage primaryStage = (Stage) loginPage.getLoginButton().getScene().getWindow();
            primaryStage.setScene(homeScene);
            primaryStage.setMaximized(true);

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
                //Scene homeScene = new Scene(new HomePageController(emp).getHomePage());
                Scene test = new Scene(new ManageEmployeeController(emp).getManageEmployeeTableView());
                Stage primaryStage = (Stage) loginPage.getLoginButton().getScene().getWindow();
                primaryStage.setScene(test);
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();

                primaryStage.setX(bounds.getMinX());
                primaryStage.setY(bounds.getMinY());
                primaryStage.setWidth(bounds.getWidth());
                primaryStage.setHeight(bounds.getHeight());
                //primaryStage.setMaximized(true);
                //primaryStage.centerOnScreen();
            }
            catch (InvalidUsernameException | InvalidPasswordException e1) {
                loginPage.getErrorLabel().setVisible(true);
                loginPage.getErrorLabel().setText(e1.getMessage());

            }
        }
    }

}
