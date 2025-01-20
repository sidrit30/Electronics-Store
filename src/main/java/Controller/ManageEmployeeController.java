package Controller;

import DAO.EmployeeDAO;
import Model.Users.*;
import View.ManageEmployeeTableView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.EnumSet;

public class ManageEmployeeController {
    private EmployeeDAO employeeDAO;
    private ManageEmployeeTableView employeeTableView;

    public ManageEmployeeTableView getManageEmployeeTableView() {
        return employeeTableView;
    }

    public ManageEmployeeController(Employee employee) {
        employeeDAO = new EmployeeDAO();
        employeeTableView = new ManageEmployeeTableView();
        employeeTableView.getTable().setItems(employeeDAO.getExcluded(employee));

        employeeTableView.getAddNewEmployeeButton().setOnAction(e -> onEmployeeAdd());
//        employeeTableView.getDeleteButton().setOnAction(e -> onEmployeeDelete());
//        employeeTableView.getSaveButton().setOnAction(e -> {
//            employeeDAO.UpdateAll();
//        });
    }

    private void onEmployeeAdd() {
        if(isValid()) {
            String employeeName = employeeTableView.getAddFirstName().getText();
            employeeName = employeeName.substring(0, 1).toUpperCase() + employeeName.substring(1);

            String employeeSurname = employeeTableView.getAddLastName().getText();
            employeeSurname = employeeSurname.substring(0, 1).toUpperCase() + employeeSurname.substring(1);

            String employeeEmail = employeeTableView.getAddEmail().getText();
            String employeeUsername = employeeTableView.getAddUsername().getText();
            String employeePassword = employeeTableView.getAddPassword().getText();
            String employeeAddress = employeeTableView.getAddAddress().getText();
            String employeePhone = employeeTableView.getAddPhoneNumber().getText();
            double employeeSalary = Double.parseDouble(employeeTableView.getAddSalary().getText());
            Role role = employeeTableView.getRoleComboBox().getSelectionModel().getSelectedItem();

            ObservableList<Permission> permissionsList
                    = employeeTableView.getPermissionList().getSelectionModel().getSelectedItems();
            EnumSet<Permission> permissions;

            //if permissions are left default
            try {
                permissions = EnumSet.copyOf(permissionsList);
            } catch (IllegalArgumentException e) {
                permissions = EnumSet.noneOf(Permission.class);
            }


            Employee newEmp = null;
            switch (role) {
                case CASHIER:
                    newEmp = new Cashier(employeeSurname, employeeName,
                            employeeUsername, employeePassword, employeeSalary);
                    addEmployeeData(newEmp, employeeEmail, employeeAddress, employeePhone, permissions);
                    break;
                case MANAGER:
                    newEmp = new Manager(employeeSurname, employeeName,
                            employeeUsername, employeePassword, employeeSalary);
                    addEmployeeData(newEmp, employeeEmail, employeeAddress, employeePhone, permissions);
                    break;
                case ADMIN:
                    newEmp = new Admin(employeeSurname, employeeName,
                            employeeUsername, employeePassword, employeeSalary);
                    addEmployeeData(newEmp, employeeEmail, employeeAddress, employeePhone, permissions);
                    break;
            }
            if (newEmp != null) {
                employeeDAO.createEmployee(newEmp);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Added Employee");
                alert.setHeaderText("New Employee Added Successfully!");
                alert.show();

                employeeTableView.getAddFirstName().clear();
                employeeTableView.getAddLastName().clear();
                employeeTableView.getAddEmail().clear();
                employeeTableView.getAddUsername().clear();
                employeeTableView.getAddPassword().clear();
                employeeTableView.getAddAddress().clear();
                employeeTableView.getAddPhoneNumber().clear();
                employeeTableView.getAddSalary().clear();
                employeeTableView.getRoleComboBox().getSelectionModel().clearSelection();
                employeeTableView.getPermissionList().getSelectionModel().clearSelection();
            }
        }


    }

    private boolean isValid() {
        if (employeeTableView.getAddFirstName().getText().isEmpty() ||
                employeeTableView.getAddLastName().getText().isEmpty() ||
                employeeTableView.getAddUsername().getText().isEmpty() ||
                employeeTableView.getAddPassword().getText().isEmpty() ||
                getManageEmployeeTableView().getRoleComboBox().getValue() == null) { // Check for username and password
            employeeTableView.showAlert("All fields must be filled.");
            return false;
        }
        if(!employeeDAO.validUsername(employeeTableView.getAddUsername().getText())) {
            employeeTableView.showAlert("Username is already taken.");
            return false;
        }
        try {
            Double.parseDouble(employeeTableView.getAddSalary().getText());
        } catch (NumberFormatException e) {
            employeeTableView.showAlert("Salary must be a number.");
            return false;
        }
        if(Double.parseDouble(employeeTableView.getAddSalary().getText())<=0) {
            employeeTableView.showAlert("Salary must be a number.");
            return false;
        }

        return true;

    }

    private void addEmployeeData(Employee newEmp, String employeeEmail, String employeeAddress, String employeePhone, EnumSet<Permission> permissions) {
        if(employeeEmail != null && !employeeEmail.isEmpty()) {
            newEmp.setEmail(employeeEmail);
        }
        if(employeeAddress != null && !employeeAddress.isEmpty()) {
            newEmp.setAddress(employeeAddress);
        }
        if(employeePhone != null && !employeePhone.isEmpty()) {
            newEmp.setPhone(employeePhone);
        }
        if(!permissions.isEmpty()) {
            newEmp.setPermissions(permissions);
        }
    }

    private void onEmployeeDelete() {
        Employee toDelete = employeeTableView.getTable().getSelectionModel().getSelectedItem();
        System.out.println(toDelete);
        Alert alert;
        if(employeeDAO.deleteEmployee(toDelete)) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleted Employee");
            alert.setHeaderText("Employee Deleted Successfully!");
        }
        else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error while deleting Employee!");
        }
    }
}
