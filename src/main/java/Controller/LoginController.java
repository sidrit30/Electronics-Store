package Controller;

import Model.Users.Admin;
import View.LoginPage;
import javafx.stage.Stage;

public class LoginController {
    private Admin userModel;
    private LoginPage loginPage;

    public LoginController(Stage primaryStage) {
        this.userModel = new Admin("admin", "admin", "admin", "admin", 3200);
        this.loginPage = new LoginPage(primaryStage);
        addEventHandlers();
    }

    private void addEventHandlers() {
        loginPage.getLoginButton().setOnAction(event -> {
            String username = loginPage.getTextField().getText();
            String password = loginPage.getPasswordField().getText();

            if (userModel.getUsername().equals(username) && userModel.getPassword().equals(password)) {
                System.out.println("Login successful");
                // Handle successful login
            } else {
                System.out.println("Login failed");
                // Handle login failure
            }
        });
    }
}
