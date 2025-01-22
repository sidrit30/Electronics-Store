//package Controller;
//
//import DAO.ItemDAO;
//import Model.Users.*;
//import View.ManageInventoryView;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.input.KeyCode;
//import javafx.scene.layout.GridPane;
//import javafx.stage.Stage;
//
//import java.util.EnumSet;
//import java.util.Optional;
//
//public class ManageInventoryController {
//    private ItemDAO itemDAO;
//    private ManageInventoryView inventoryView;
//    private Employee selectedEmployee;
//
//    public ManageInventoryView getManageInventoryView() {
//        return inventoryView;
//    }
//
//    public ManageInventoryController(Employee employee) {
//        itemDAO = new ItemDAO();
//        inventoryView = new ManageInventoryView();
//        selectedEmployee = employee;
//
//
//        if(employee instanceof Admin) {
//            inventoryView.getTable().setItems(itemDAO.getItems());
//            inventoryView.g
//        }
//
//        if(employee instanceof Manager) {
//            inventoryView.getTable().setItems(itemDAO.getItemsBySectors(((Manager)employee).getSectors()));
//        }
//
//        if(employee instanceof Cashier) {
//            inventoryView.getTable().setItems(itemDAO.getItemsBySector(employee.getSectorName()));
//        }
//
//
//
//        setSearchListener();
//
//        if(selectedEmployee.hasPermission(Permission.EDIT_ITEM)) {
//            inventoryView.getTable().setEditable(true);
//            setEditListeners();
//            inventoryView.getAddNewItemButton().setOnAction(e -> onItemAdd());
//            inventoryView.getDeleteButton().setOnAction(e -> onItemDelete());
//            inventoryView.getSaveButton().setOnAction(e -> {
//                boolean flag = itemDAO.UpdateAll();
//                if (flag) {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Success");
//                    alert.setHeaderText("Employees Updated");
//                    alert.show();
//                }
//            });
//        } else {
//            inventoryView.getMainVBox().setVisible(false);
//        }
//    }
//
//    private void onItemDelete() {
//    }
//
//    private void onItemAdd() {
//    }
//
//
//
//    private void onEmployeeAdd() {
//        if(isValid()) {
//            String employeeName = inventoryView.getAddFirstName().getText();
//            employeeName = employeeName.substring(0, 1).toUpperCase() + employeeName.substring(1);
//
//            String employeeSurname = inventoryView.getAddLastName().getText();
//            employeeSurname = employeeSurname.substring(0, 1).toUpperCase() + employeeSurname.substring(1);
//
//            String employeeEmail = inventoryView.getAddEmail().getText();
//            String employeeUsername = inventoryView.getAddUsername().getText();
//            String employeePassword = inventoryView.getAddPassword().getText();
//            String employeeAddress = inventoryView.getAddAddress().getText();
//            String employeePhone = inventoryView.getAddPhoneNumber().getText();
//            double employeeSalary = Double.parseDouble(inventoryView.getAddSalary().getText());
//            Role role = inventoryView.getRoleComboBox().getSelectionModel().getSelectedItem();
//
//            ObservableList<Permission> permissionsList
//                    = inventoryView.getPermissionList().getSelectionModel().getSelectedItems();
//            EnumSet<Permission> permissions;
//
//            //if permissions are left default
//            try {
//                permissions = EnumSet.copyOf(permissionsList);
//            } catch (IllegalArgumentException e) {
//                permissions = EnumSet.noneOf(Permission.class);
//            }
//
//
//            Employee newEmp = null;
//
//            switch (role) {
//                case CASHIER:
//                    newEmp = new Cashier(employeeSurname, employeeName,
//                            employeeUsername, employeePassword, employeeSalary);
//                    ((Cashier) newEmp).setSectorName(inventoryView.getSectorList().getSelectionModel().getSelectedItem());
//                    addEmployeeData(newEmp, employeeEmail, employeeAddress, employeePhone, permissions);
//                    break;
//                case MANAGER:
//                    newEmp = new Manager(employeeSurname, employeeName,
//                            employeeUsername, employeePassword, employeeSalary);
//                    ((Manager) newEmp).setSectors(inventoryView.getSectorList().getSelectionModel().getSelectedItems());
//                    addEmployeeData(newEmp, employeeEmail, employeeAddress, employeePhone, permissions);
//                    break;
//                case ADMIN:
//                    newEmp = new Admin(employeeSurname, employeeName,
//                            employeeUsername, employeePassword, employeeSalary);
//                    addEmployeeData(newEmp, employeeEmail, employeeAddress, employeePhone, permissions);
//                    break;
//            }
//            if (newEmp != null) {
//                employeeDAO.createEmployee(newEmp);
//                inventoryView.getTable().refresh();
//                System.out.println(newEmp);
//                System.out.println(newEmp.getSectorName());
//                System.out.println(newEmp.getPermissions());
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Added Employee");
//                alert.setHeaderText("New Employee Added Successfully!");
//                alert.show();
//
//                inventoryView.getAddFirstName().clear();
//                inventoryView.getAddLastName().clear();
//                inventoryView.getAddEmail().clear();
//                inventoryView.getAddUsername().clear();
//                inventoryView.getAddPassword().clear();
//                inventoryView.getAddAddress().clear();
//                inventoryView.getAddPhoneNumber().clear();
//                inventoryView.getAddSalary().clear();
//                inventoryView.getRoleComboBox().getSelectionModel().clearSelection();
//                inventoryView.getPermissionList().getSelectionModel().clearSelection();
//            }
//        }
//
//
//    }
//
//    private boolean isValid() {
//        if (inventoryView.getAddNewItemButton().getText().isEmpty() ||
//                inventoryView.getSelectItemCategory().getValue() == null ||
//                inventoryView.getAddSupplierName().getText().isEmpty() ||
//                inventoryView.getAddPurchasePrice().getText().isEmpty() ||
//                inventoryView.getAddSellPrice().getText().isEmpty() ||
//                inventoryView.getAddQuantity().getText().isEmpty()) {
//            inventoryView.showErrorAlert("Item details are missing!");
//            return false;
//        }
//
//        if(inventoryView.getAddItemName().getText().length() < 6) {
//            inventoryView.showErrorAlert("Item can't be less than 6 characters!");
//            return false;
//        }
//        if(!(inventoryView.getAddUsername().getText())) {
//            inventoryView.showErrorAlert("Username is already taken.");
//            return false;
//        }
//
//        //check password
//        if(inventoryView.getAddPassword().getText().length() < 4 || inventoryView.getAddPassword().getText().length() > 20) {
//            inventoryView.showErrorAlert("Password must be between 4 and 20 characters.");
//            return false;
//        }
//
//        //check salary
//        try {
//            Double.parseDouble(inventoryView.getAddSalary().getText());
//        } catch (NumberFormatException e) {
//            inventoryView.showErrorAlert("Salary must be a positive number.");
//            return false;
//        }
//        if(Double.parseDouble(inventoryView.getAddSalary().getText())<=0) {
//            inventoryView.showErrorAlert("Salary must be a positive number.");
//            return false;
//        }
//
//        return true;
//
//    }
//
//    private void addEmployeeData(Employee newEmp, String employeeEmail, String employeeAddress, String employeePhone, EnumSet<Permission> permissions) {
//        if(employeeEmail != null && !employeeEmail.isEmpty()) {
//            newEmp.setEmail(employeeEmail);
//        }
//        if(employeeAddress != null && !employeeAddress.isEmpty()) {
//            newEmp.setAddress(employeeAddress);
//        }
//        if(employeePhone != null && !employeePhone.isEmpty()) {
//            newEmp.setPhone(employeePhone);
//        }
//        if(permissions != null && !permissions.isEmpty()) {
//            newEmp.setPermissions(permissions);
//        }
//    }
//
//    private void onEmployeeDelete() {
//
//        Employee toDelete = inventoryView.getTable().getSelectionModel().getSelectedItem();
//        Alert alert;
//
//        if(toDelete.equals(selectedEmployee)) {
//            alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Delete Employee");
//            alert.setHeaderText("You Cannot Delete Yourself.");
//            alert.show();
//        }
//        else {
//            alert = new Alert(Alert.AlertType.WARNING, "Confirm Deletion", ButtonType.OK, ButtonType.CANCEL);
//            alert.setTitle("Confirm Deletion");
//            alert.setHeaderText("Are you sure you want to delete employee " + toDelete +"?");
//
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.isPresent() && result.get() == ButtonType.OK) {
//                if (employeeDAO.deleteEmployee(toDelete)) {
//                    alert = new Alert(Alert.AlertType.CONFIRMATION);
//                    alert.setTitle("Deleted Employee");
//                    alert.setHeaderText("Employee Deleted Successfully!");
//                    alert.show();
//                } else {
//                    alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error");
//                    alert.setHeaderText("Error while deleting Employee!");
//                    alert.show();
//                }
//            }
//            else {
//                alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Confirmation");
//                alert.setHeaderText("Employee Deletion Cancelled!");
//            }
//
//        }
//    }
//
//    private void setEditListeners() {
//
//        this.inventoryView.getUsernameCol().setOnEditCommit(e -> {
//            String username = e.getNewValue();
//            if(username == null || username.length() < 4 || username.length() > 20) {
//                this.inventoryView.showErrorAlert("Username must be between 4 and 20 characters.");
//                return;
//            }
//            if (!employeeDAO.validUsername(username)) {
//                this.inventoryView.showErrorAlert(username+" is not an available username.");
//                return;
//            }
//
//            employeeDAO.getEmployees().get(e.getTablePosition().getRow()).setUsername(username);
//        });
//        this.inventoryView.getPasswordCol().setOnEditCommit(e -> {
//            String password = e.getNewValue();
//            if(password == null || password.length() < 4 || password.length() > 20) {
//                inventoryView.showErrorAlert("Password must be between 4 and 20 characters.");
//                return;
//            }
//            employeeDAO.getEmployees().get(e.getTablePosition().getRow()).setPassword(password);
//        });
//        this.inventoryView.getEmailCol().setOnEditCommit(e -> {
//            employeeDAO.getEmployees().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
//        });
//        this.inventoryView.getAddressCol().setOnEditCommit(e -> {
//            employeeDAO.getEmployees().get(e.getTablePosition().getRow()).setAddress(e.getNewValue());
//        });
//        this.inventoryView.getPhoneNumberCol().setOnEditCommit(e -> {
//            employeeDAO.getEmployees().get(e.getTablePosition().getRow()).setPhone(e.getNewValue());
//        });
//        this.inventoryView.getSalaryCol().setOnEditCommit(e -> {
//            employeeDAO.getEmployees().get(e.getTablePosition().getRow()).setSalary(e.getNewValue());
//        });
//    }
//
//    private void setSearchListener() {
//        this.inventoryView.getSearchButton().setOnAction(e -> searchEmployee());
//        this.inventoryView.getSearchField().setOnKeyReleased(e -> {
//            if(e.getCode() == KeyCode.ENTER) {
//                searchEmployee();
//            }
//        });
//    }
//
//    private void searchEmployee() {
//        String searchString = inventoryView.getSearchField().getText();
//        ObservableList<Employee> filteredEmployees = FXCollections.observableArrayList();
//
//        String criteria = this.inventoryView.getSearchBy().getSelectionModel().getSelectedItem();
//
//        if(criteria.equals("Full Name")) {
//            for (Employee employee : employeeDAO.getEmployees()) {
//                if (employee.getFullName().toLowerCase().contains(searchString.toLowerCase())) {
//                    filteredEmployees.add(employee);
//                }
//            }
//        } else {
//            for (Employee employee : employeeDAO.getEmployees()) {
//                if(employee.getSectorName() == null || employee.getSectorName().isEmpty())
//                    continue;
//                if(employee instanceof Admin)
//                    continue;
//                if(employee instanceof Manager) {
//                    for(String sector : ((Manager)employee).getSectors()) {
//                        if(sector.toLowerCase().contains(searchString.toLowerCase())) {
//                            filteredEmployees.add(employee);
//                        }
//                    }
//                }
//                if(employee instanceof Cashier) {
//                    if(((Cashier)employee).getSectorName().toLowerCase().contains(searchString.toLowerCase())) {
//                        filteredEmployees.add(employee);
//                    }
//                }
//            }
//        }
//
//        this.inventoryView.getTable().getSelectionModel().clearSelection();
//        this.inventoryView.getTable().setItems(filteredEmployees);
//        this.inventoryView.getSearchField().clear();
//
//    }
//
//    private void editPermissions() {
//        Employee emp = inventoryView.getTable().getSelectionModel().getSelectedItem();
//        if(emp == null) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Permissions");
//            alert.setHeaderText("Select An Employee First!");
//            alert.show();
//            return;
//        }
//        if(emp instanceof Admin) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Permissions");
//            alert.setHeaderText("You Cannot Edit Admin Permissions!");
//            alert.show();
//            return;
//        }
//
//        Stage popup = new Stage();
//        ListView<Permission> permissionsListView = new ListView<>();
//        permissionsListView.getItems().setAll(EnumSet.allOf(Permission.class));
//        if(emp instanceof Manager) {
//            permissionsListView.getItems().removeFirst();
//        }
//        permissionsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        permissionsListView.setMaxHeight(120);
//        Label perm = new Label("Select Permission");
//        Button submit = new Button("Submit");
//        Button cancel = new Button("Cancel");
//        GridPane grid = new GridPane();
//        grid.add(perm, 0, 0);
//        grid.add(permissionsListView,0, 1);
//        grid.add(submit, 1, 2);
//        grid.add(cancel, 0, 2);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(20, 10, 10, 10));
//        popup.setTitle("Permissions");
//        popup.setResizable(false);
//        popup.setScene(new Scene(grid));
//        popup.show();
//
//        submit.setOnAction(e -> {
//            ObservableList<Permission> permissionsList
//                    = permissionsListView.getSelectionModel().getSelectedItems();
//            EnumSet<Permission> permissions;
//
//            //if permissions are left default
//            try {
//                permissions = EnumSet.copyOf(permissionsList);
//                emp.setPermissions(permissions);
//            } catch (IllegalArgumentException ignored) {
//            }
//            popup.close();
//        });
//
//        cancel.setOnAction(e -> {
//            popup.close();
//        });
//    }
//}
