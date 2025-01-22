package Controller;

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
            employee.setEmail(view.getEmailField().getText());
            employee.setUsername(view.getUsernameField().getText());
            employee.setPassword(view.getPasswordField().getText());
            employee.setAddress(view.getAddressField().getText());
            employee.setPhone(view.getPhoneField().getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Changed Saved!");
            alert.showAndWait();
        });
    }
}
