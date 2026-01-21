package Controller;

import DAO.EmployeeDAO;
import DAO.ItemDAO;
import Model.Users.*;
import View.ManageEmployeeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.EnumSet;
import java.util.Optional;
import java.util.logging.Logger;


public class ManageEmployeeController {

    private static final int MIN_CREDENTIAL_LENGTH = 4;
    private static final int MAX_CREDENTIAL_LENGTH = 20;
    private static final String TITLE_PERMISSIONS = "Permissions";
    private static final String TITLE_SECTOR = "Sector";

    private static final Logger LOGGER =
            Logger.getLogger(ManageEmployeeController.class.getName());

    private EmployeeDAO employeeDAO;
    private ManageEmployeeTableView employeeTableView;
    private Employee selectedEmployee;

    public ManageEmployeeTableView getManageEmployeeTableView() {
        return employeeTableView;
    }

    public ManageEmployeeController(Employee employee) {
        employeeDAO = new EmployeeDAO();
        employeeTableView = new ManageEmployeeTableView();
        selectedEmployee = employee;


        employeeTableView.getTable().setItems(employeeDAO.getEmployees());
        setSearchListener();

        if(selectedEmployee.hasPermission(Permission.EDIT_SECTOR)) {
            employeeTableView.getTable().setEditable(true);
            setEditListeners();
            employeeTableView.getAddNewEmployeeButton().setOnAction(e -> onEmployeeAdd());
            employeeTableView.getDeleteButton().setOnAction(e -> onEmployeeDelete());
            employeeTableView.getSaveButton().setOnAction(e -> {
                boolean flag = employeeDAO.UpdateAll();
                if (flag) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Employees Updated");
                    alert.show();
                }
            });
            employeeTableView.getEditPermissionsButton().setOnAction(e -> editPermissions());
            employeeTableView.getRoleComboBox().setOnAction(e -> addSector());
            employeeTableView.getEditSectorButton().setOnAction(e -> editSector());
        } else {
            employeeTableView.getMainVBox().setVisible(false);
        }
    }

    private void editSector() {
        Employee emp = employeeTableView.getTable().getSelectionModel().getSelectedItem();
        if(emp == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(TITLE_SECTOR);
            alert.setHeaderText("Select An Employee First!");
            alert.show();
            return;
        }

        if(emp instanceof Admin) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(TITLE_SECTOR);
            alert.setHeaderText("You Cannot Edit Admin Sectors!");
            alert.show();
            return;
        }

        Stage popup = new Stage();
        ListView<String> sectorListView = new ListView<>();
        sectorListView.getItems().setAll(new ItemDAO().getSectorNames());

        if(emp instanceof Cashier)
            sectorListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        if(emp instanceof Manager)
            sectorListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        sectorListView.setMaxHeight(120);

        Label sector = new Label("Select Sector/s: ");

        Button submit = new Button("Submit");
        Button cancel = new Button("Cancel");
        GridPane grid = new GridPane();
        grid.add(sector, 0, 0);
        grid.add(sectorListView,0, 1);
        grid.add(submit, 1, 2);
        grid.add(cancel, 0, 2);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));
        popup.setTitle(TITLE_SECTOR);
        popup.setResizable(false);
        popup.setScene(new Scene(grid));
        popup.show();

        submit.setOnAction(e -> {
            ObservableList<String> sectorList;
            String sectorName = sectorListView.getSelectionModel().getSelectedItem();
            if(emp instanceof Manager) {
                sectorList = sectorListView.getSelectionModel().getSelectedItems();
                if(sectorList == null || sectorList.isEmpty())
                    popup.close();
                ((Manager) emp).setSectors(sectorList);
            }
            if(emp instanceof Cashier) {
                if(sectorName == null || sectorName.isEmpty())
                    popup.close();
                ((Cashier) emp).setSectorName(sectorName);
            }
            popup.close();
        });

        cancel.setOnAction(e -> popup.close());

    }

    private void addSector() {
        Role role = employeeTableView.getRoleComboBox().getSelectionModel().getSelectedItem();
        //get addBox1
        HBox hBox = (HBox) employeeTableView.getMainVBox().getChildren().get(0);
        if(Role.MANAGER.equals(role)) {
            if(hBox.getChildren().size() < 6)
                hBox.getChildren().add(employeeTableView.getSectorBox());

            employeeTableView.getSectorList().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
        if(Role.CASHIER.equals(role)) {
            LOGGER.info(() -> "HBox children count: " + hBox.getChildren().size());
            if(hBox.getChildren().size() < 6)
                hBox.getChildren().add(employeeTableView.getSectorBox());
            employeeTableView.getSectorList().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        }
        if(Role.ADMIN.equals(role) && hBox.getChildren().size() >= 6) {
            hBox.getChildren().remove(hBox.getChildren().size() - 1);
        }
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
                    ((Cashier) newEmp).setSectorName(employeeTableView.getSectorList().getSelectionModel().getSelectedItem());
                    addEmployeeData(newEmp, employeeEmail, employeeAddress, employeePhone, permissions);
                    break;
                case MANAGER:
                    newEmp = new Manager(employeeSurname, employeeName,
                            employeeUsername, employeePassword, employeeSalary);
                    ((Manager) newEmp).setSectors(employeeTableView.getSectorList().getSelectionModel().getSelectedItems());
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
                employeeTableView.getTable().refresh();
                LOGGER.info(newEmp.toString());
                LOGGER.info(String.valueOf(newEmp.getSectorName()));
                LOGGER.info(String.valueOf(newEmp.getPermissions()));
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
            employeeTableView.showErrorAlert("Employee full name, role, username and password are required.");
            return false;
        }

        //check username
        if(employeeTableView.getAddUsername().getText().length() < MIN_CREDENTIAL_LENGTH || employeeTableView.getAddUsername().getText().length() > MAX_CREDENTIAL_LENGTH) {
            employeeTableView.showErrorAlert("Username must be between 4 and 20 characters.");
            return false;
        }
        if(!employeeDAO.validUsername(employeeTableView.getAddUsername().getText())) {
            employeeTableView.showErrorAlert("Username is already taken.");
            return false;
        }

        //check password
        if(employeeTableView.getAddPassword().getText().length() < MIN_CREDENTIAL_LENGTH || employeeTableView.getAddPassword().getText().length() > MIN_CREDENTIAL_LENGTH) {
            employeeTableView.showErrorAlert("Password must be between 4 and 20 characters.");
            return false;
        }

        //check salary
        try {
            Double.parseDouble(employeeTableView.getAddSalary().getText());
        } catch (NumberFormatException e) {
            employeeTableView.showErrorAlert("Salary must be a positive number.");
            return false;
        }
        if(Double.parseDouble(employeeTableView.getAddSalary().getText())<=0) {
            employeeTableView.showErrorAlert("Salary must be a positive number.");
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
        if(permissions != null && !permissions.isEmpty()) {
            newEmp.setPermissions(permissions);
        }
    }

    private void onEmployeeDelete() {

        Employee toDelete = employeeTableView.getTable().getSelectionModel().getSelectedItem();
        Alert alert;

        if(toDelete.equals(selectedEmployee)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Employee");
            alert.setHeaderText("You Cannot Delete Yourself.");
            alert.show();
        }
        else {
            alert = new Alert(Alert.AlertType.WARNING, "Confirm Deletion", ButtonType.OK, ButtonType.CANCEL);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete employee " + toDelete +"?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (employeeDAO.deleteEmployee(toDelete)) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION, "Success!", ButtonType.OK);
                    alert.setTitle("Deleted Employee");
                    alert.setHeaderText("Employee Deleted Successfully!");
                    alert.show();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error while deleting Employee!");
                    alert.show();
                }
            }
            else {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancelled", ButtonType.OK);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Employee Deletion Cancelled!");
            }

        }
    }

    private void setEditListeners() {
        this.employeeTableView.getUsernameCol().setOnEditCommit(e -> {
            String username = e.getNewValue();
            if(username == null || username.length() < 4 || username.length() > 20) {
                this.employeeTableView.showErrorAlert("Username must be between 4 and 20 characters.");
                return;
            }
            if (!employeeDAO.validUsername(username)) {
                this.employeeTableView.showErrorAlert(username+" is not an available username.");
                return;
            }

            employeeDAO.getEmployeebyID(e.getRowValue().getId()).setUsername(username);
            e.getTableView().refresh();
        });
        this.employeeTableView.getPasswordCol().setOnEditCommit(e -> {
            String password = e.getNewValue();
            if(password == null || password.length() < 4 || password.length() > 20) {
                employeeTableView.showErrorAlert("Password must be between 4 and 20 characters.");
                return;
            }
            employeeDAO.getEmployeebyID(e.getRowValue().getId()).setPassword(password);
            e.getTableView().refresh();
        });
        this.employeeTableView.getEmailCol().setOnEditCommit(e -> {
            employeeDAO.getEmployeebyID(e.getRowValue().getId()).setEmail(e.getNewValue());
            e.getTableView().refresh();
        });
        this.employeeTableView.getAddressCol().setOnEditCommit(e -> {
            employeeDAO.getEmployeebyID(e.getRowValue().getId()).setAddress(e.getNewValue());
            e.getTableView().refresh();
        });
        this.employeeTableView.getPhoneNumberCol().setOnEditCommit(e -> {
            employeeDAO.getEmployeebyID(e.getRowValue().getId()).setPhone(e.getNewValue());
            e.getTableView().refresh();
        });
        this.employeeTableView.getSalaryCol().setOnEditCommit(e -> {
            employeeDAO.getEmployeebyID(e.getRowValue().getId()).setSalary(e.getNewValue());
            e.getTableView().refresh();
        });
    }

    private void setSearchListener() {
        this.employeeTableView.getSearchButton().setOnAction(e -> searchEmployee());
        this.employeeTableView.getSearchField().setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                searchEmployee();
            }
        });
    }

    private void searchEmployee() {
        String searchString = employeeTableView.getSearchField().getText();
        ObservableList<Employee> filteredEmployees = FXCollections.observableArrayList();

        String criteria = this.employeeTableView.getSearchBy().getSelectionModel().getSelectedItem();

        if(criteria.equals("Full Name")) {
            for (Employee employee : employeeDAO.getEmployees()) {
                if (employee.getFullName().toLowerCase().contains(searchString.toLowerCase())) {
                    filteredEmployees.add(employee);
                }
            }
        } else {
            for (Employee employee : employeeDAO.getEmployees()) {

                if (!(employee instanceof Admin)
                        && employee.getSectorName() != null
                        && !employee.getSectorName().isEmpty()) {

                    if (employee instanceof Manager) {
                        for (String sector : ((Manager) employee).getSectors()) {
                            if (sector.toLowerCase().contains(searchString.toLowerCase())) {
                                filteredEmployees.add(employee);
                            }
                        }
                    } else if (employee instanceof Cashier && ((Cashier) employee).getSectorName()
                            .toLowerCase()
                            .contains(searchString.toLowerCase())) {
                            filteredEmployees.add(employee);
                    }
                }
            }

        }

        this.employeeTableView.getTable().getSelectionModel().clearSelection();
        this.employeeTableView.getTable().setItems(filteredEmployees);
        this.employeeTableView.getSearchField().clear();

    }

    private void editPermissions() {
        Employee emp = employeeTableView.getTable().getSelectionModel().getSelectedItem();
        if(emp == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(TITLE_PERMISSIONS);
            alert.setHeaderText("Select An Employee First!");
            alert.show();
            return;
        }
        if(emp instanceof Admin) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(TITLE_PERMISSIONS);
            alert.setHeaderText("You Cannot Edit Admin Permissions!");
            alert.show();
            return;
        }

        Stage popup = new Stage();
        ListView<Permission> permissionsListView = new ListView<>();
        permissionsListView.getItems().setAll(EnumSet.allOf(Permission.class));
        if(emp instanceof Manager) {
            permissionsListView.getItems().clear();
        }
        permissionsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        permissionsListView.setMaxHeight(120);
        Label perm = new Label("Select Permission");
        Button submit = new Button("Submit");
        Button cancel = new Button("Cancel");
        GridPane grid = new GridPane();
        grid.add(perm, 0, 0);
        grid.add(permissionsListView,0, 1);
        grid.add(submit, 1, 2);
        grid.add(cancel, 0, 2);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));
        popup.setTitle(TITLE_PERMISSIONS);
        popup.setResizable(false);
        popup.setScene(new Scene(grid));
        popup.show();

        submit.setOnAction(e -> {
            ObservableList<Permission> permissionsList
                    = permissionsListView.getSelectionModel().getSelectedItems();
            EnumSet<Permission> permissions;

            //if permissions are left default
            try {
                permissions = EnumSet.copyOf(permissionsList);
                emp.setPermissions(permissions);
            } catch (IllegalArgumentException ignored) {
                // No permissions selected – leave unchanged intentionally
            }
            popup.close();
        });

        cancel.setOnAction(e -> popup.close());
    }
}
