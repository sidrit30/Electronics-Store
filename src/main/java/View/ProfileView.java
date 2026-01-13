package View;

import Model.Users.Employee;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ProfileView extends GridPane {
    private Employee employee;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private TextField usernameField;
    private TextField passwordField;
    private TextField salaryField;
    private TextField phoneField;
    private TextField addressField;

    private Button button = new Button("Save Changes");


    public ProfileView(Employee employee) {
        this.employee = employee;
        this.setPadding(new Insets(50));
        this.setAlignment(Pos.CENTER_LEFT);
        this.setVgap(30);
        this.setHgap(10);

        firstNameField = new TextField();
        firstNameField.setText(employee.getFirstName());
        firstNameField.setMaxWidth(100);
        Label firstNameLabel = new Label("First Name");
        lastNameField = new TextField();
        lastNameField.setText(employee.getLastName());
        lastNameField.setMaxWidth(100);
        Label lastNameLabel = new Label("Last Name");
        emailField = new TextField();
        emailField.setText(employee.getEmail());
        emailField.setMaxWidth(100);
        Label emailLabel = new Label("Email");
        usernameField = new TextField();
        usernameField.setText(employee.getUsername());
        usernameField.setMaxWidth(100);
        Label usernameLabel = new Label("Username");
        passwordField = new TextField();
        passwordField.setText(employee.getPassword());
        passwordField.setMaxWidth(100);
        Label passwordLabel = new Label("Password");
        salaryField = new TextField();
        salaryField.setText(String.valueOf(employee.getSalary()));
        salaryField.setMaxWidth(100);
        Label salaryLabel = new Label("Salary");
        phoneField = new TextField();
        phoneField.setText(employee.getPhone());
        phoneField.setMaxWidth(100);
        Label phoneLabel = new Label("Phone");
        addressField = new TextField();
        addressField.setText(employee.getAddress());
        addressField.setMaxWidth(100);
        Label addressLabel = new Label("Address");

        this.add(firstNameLabel, 0, 0);
        this.add(firstNameField, 1, 0);
        this.add(lastNameLabel, 0, 1);
        this.add(lastNameField, 1, 1);
        this.add(emailLabel, 0, 2);
        this.add(emailField, 1, 2);
        this.add(usernameLabel, 0, 3);
        this.add(usernameField, 1, 3);
        this.add(passwordLabel, 0, 4);
        this.add(passwordField, 1, 4);
        this.add(salaryLabel, 0, 5);
        this.add(salaryField, 1, 5);
        this.add(phoneLabel, 0, 6);
        this.add(phoneField, 1, 6);
        this.add(addressLabel, 0, 7);
        this.add(addressField, 1, 7);
        this.add(button, 1, 8);

    }

    public Employee getEmployee() {
        return employee;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextField getSalaryField() {
        return salaryField;
    }

    public TextField getPhoneField() {
        return phoneField;
    }

    public TextField getAddressField() {
        return addressField;
    }

    public Button getButton() {
        return button;
    }
}
