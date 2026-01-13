package Controller;

import DAO.EmployeeDAO;
import Model.Users.Employee;
import View.ProfileView;
import javafx.scene.control.Alert;

public class ProfileController {
    Employee employee;
    ProfileView view;

    public ProfileView getView() {
        return view;
    }

    ProfileController(Employee employee) {
        this.employee = employee;
        this.view = new ProfileView(employee);

        view.getFirstNameField().setEditable(false);
        view.getLastNameField().setEditable(false);
        view.getSalaryField().setEditable(false);

        view.getButton().setOnAction(e -> {
            if(new EmployeeDAO().validUsername(view.getUsernameField().getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Invalid username");
                alert.show();
                return;
            }
            if(view.getUsernameField().getText().length() < 4 || view.getUsernameField().getText().length() > 20) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Username must be between 4 and 20 characters");
                alert.show();
                return;
            }
            if(view.getPasswordField().getText().length() < 4 || view.getPasswordField().getText().length() > 20) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Password must be between 4 and 20 characters");
                alert.show();
                return;
            }

            employee.setEmail(view.getEmailField().getText());
            employee.setUsername(view.getUsernameField().getText());
            employee.setPassword(view.getPasswordField().getText());
            employee.setAddress(view.getAddressField().getText());
            employee.setPhone(view.getPhoneField().getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Changed Saved!");
            alert.show();
        });
    }
}
