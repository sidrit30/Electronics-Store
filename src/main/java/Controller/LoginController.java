package Controller;

import Model.Users.Admin;
import View.LoginPage;
import javafx.stage.Stage;

public class LoginController {
    String uname;
    String pass;
    public LoginController(Stage stage) {
        Admin admin = new Admin();
        LoginPage login = new LoginPage(stage);

        login.getLoginButton().setOnAction(e -> {
            System.out.println("Login Button pressed");
            uname = login.getTextField().getText();
            System.out.println(uname);
            pass = login.getPasswordField().getText();
            System.out.println(pass);
            if(admin.getUsername().equals(uname) && admin.getPassword().equals(pass)) {
                System.out.println("Login Successful");
                new AdminController(stage);
            }
            System.out.println("Login Failed");
        });
    }
}
