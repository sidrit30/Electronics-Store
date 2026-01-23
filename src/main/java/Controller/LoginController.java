package Controller;

import DAO.EmployeeDAO;
import Main.Launcher;
import Model.Exceptions.InvalidPasswordException;
import Model.Exceptions.InvalidUsernameException;
import Model.Users.Employee;
import Model.Users.Manager;
import View.LoginPage;
import javafx.event.Event;
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

    public LoginController() {
        this(new EmployeeDAO(), new LoginPage());
    }

    public LoginController(EmployeeDAO employeeDAO, LoginPage loginPage) {
        this.employeeDAO = employeeDAO;
        this.loginPage = loginPage;
        setupListeners();
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    private void setupListeners() {
        loginPage.getPasswordField().setOnKeyPressed(this::onLoginEnter);
        loginPage.getUsernameField().setOnKeyPressed(this::onLoginEnter);
        loginPage.getUnmaskedPasswordField().setOnKeyPressed(this::onLoginEnter);
        loginPage.getLoginButton().setOnAction(this::onLoginButton);
    }


    public void login() {
        String username = loginPage.getUsernameField().getText();
        String password = loginPage.getPasswordField().getText();

        try {
            Employee emp = employeeDAO.authLogin(username, password);
            LOGGER.info(LOG_LOGIN_SUCCESS);

            // Logic for navigation (This is the hard part to test)
            handleNavigation(emp);

        } catch (InvalidUsernameException | InvalidPasswordException ex) {
            loginPage.getErrorLabel().setVisible(true);
            loginPage.getErrorLabel().setText(ex.getMessage());
        }
    }

    protected void handleNavigation(Employee emp) {
        Stage primaryStage = getStage(emp);
        primaryStage.show();
        if (emp instanceof Manager) {
            new ManagerController(emp);
        }
    }

    private void onLoginButton(Event e) {
        login();
    }

    private void onLoginEnter(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    private Stage getStage(Employee emp) {
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
        return primaryStage;
    }
}

