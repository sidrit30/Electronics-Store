package Controller;

import DAO.EmployeeDAO;
import Main.Launcher;
import Model.Exceptions.InvalidPasswordException;
import Model.Exceptions.InvalidUsernameException;
import Model.Users.Employee;
import Model.Users.Manager;
import View.LoginPage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.logging.Logger;

import static Main.Launcher.VISUAL_BOUNDS;

public class LoginController {

    private static final Logger LOGGER =
            Logger.getLogger(LoginController.class.getName());

    private static final String LOG_LOGIN_SUCCESS = "Login Successful";

    private EmployeeDAO employeeDAO;
    private LoginPage loginPage;

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public LoginController() {
        loginPage = new LoginPage();
        employeeDAO = new EmployeeDAO();

        loginPage.getPasswordField().setOnKeyPressed(this::onLoginEnter);
        loginPage.getUsernameField().setOnKeyPressed(this::onLoginEnter);
        loginPage.getUnmaskedPasswordField().setOnKeyPressed(this::onLoginEnter);
        loginPage.getLoginButton().setOnAction(e -> onLoginButton());
    }

    private void onLoginButton() {
        login();
    }

    private void onLoginEnter(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    private void login() {
        String username = loginPage.getUsernameField().getText();
        String password = loginPage.getPasswordField().getText();
        Employee emp;

        try {
            emp = employeeDAO.authLogin(username, password);
            LOGGER.info(LOG_LOGIN_SUCCESS);

            Scene homeScene = new Scene(new HomePageController(emp).getHomePage());
            Stage oldStage = (Stage) loginPage.getScene().getWindow();
            oldStage.close();

            Stage primaryStage = new Stage();
            primaryStage.setScene(homeScene);
            primaryStage.setTitle("Jupiter Electronics");
            primaryStage.setX(VISUAL_BOUNDS.getMinX());
            primaryStage.setY(VISUAL_BOUNDS.getMinY());
            primaryStage.setWidth(VISUAL_BOUNDS.getWidth());
            primaryStage.setHeight(VISUAL_BOUNDS.getHeight());
            primaryStage.getIcons().add(
                    new Image(Launcher.class.getResourceAsStream("/images/appIcon.jpg"))
            );
            primaryStage.show();

            if (emp instanceof Manager) {
                new ManagerController(emp);
            }

        } catch (InvalidUsernameException | InvalidPasswordException ex) {
            loginPage.getErrorLabel().setVisible(true);
            loginPage.getErrorLabel().setText(ex.getMessage());
        }
    }
}

