package temp;

import Model.Users.Admin;

public class TempController {
//     editButton.setOnAction(e -> {
//        Admin selectedAdmin = table.getSelectionModel().getSelectedItem();
//        if (selectedAdmin != null) {
//            populateFormFields(selectedAdmin);
//            // Change the action of the Add New Employee button to save changes
//            addNewEmployeeButton.setText("Save Changes");
//            addNewEmployeeButton.setOnAction(event -> {
//                selectedAdmin.setEmail(addEmail.getText());
//                selectedAdmin.setAddress(addAddress.getText());
//                selectedAdmin.setRole(roleComboBox.getValue());
//                selectedAdmin.setSalary(Double.parseDouble(addSalary.getText()));
//                selectedAdmin.setPhoneNumber(addPhoneNumber.getText());
//                selectedAdmin.setUsername(addUsername.getText()); // Set username
//                selectedAdmin.setPassword(addPassword.getText()); // Set password
//                clearFormFields();
//                addNewEmployeeButton.setText("Add New Employee");
//                addNewEmployeeButton.setOnAction(e2 -> {
//                    if (isValidInput()) { // Validate input before adding
//                        String employeeID = generateEmployeeID();
//                        String fName = addFirstName.getText();
//                        String lName = addLastName.getText();
//                        String email = addEmail.getText();
//                        String address = addAddress.getText();
//                        String role = roleComboBox.getValue();
//                        double salary = Double.parseDouble(addSalary.getText());
//                        String phoneNumber = addPhoneNumber.getText();
//                        String username = addUsername.getText(); // Get username
//                        String password = addPassword.getText(); // Get password
//                        data.add(new Admin(employeeID, fName, lName, email, address, role, salary, phoneNumber, username, password));
//                        clearFormFields();
//                    }
//                });
//                table.refresh();
//            });
//        }
//    });
//
//    addNewEmployeeButton.setOnAction(e -> {
//        if (isValidInput()) { // Validate input before adding
//            String employeeID = generateEmployeeID(); // Replace with your method to generate ID
//            String fName = addFirstName.getText();
//            String lName = addLastName.getText();
//            String email = addEmail.getText();
//            String address = addAddress.getText();
//            String role = roleComboBox.getValue();
//            double salary = Double.parseDouble(addSalary.getText());
//            String phoneNumber = addPhoneNumber.getText();
//            String username = addUsername.getText(); // Get username
//            String password = addPassword.getText(); // Get password
//
//            // Ensure the fields are passed in correct order
//            data.add(new Admin(employeeID, fName, lName, email, address, role, salary, phoneNumber, username, password));
//            clearFormFields();
//        }
//    });

//    private boolean isValidInput() {
//        try {
//            Double.parseDouble(addSalary.getText());
//        } catch (NumberFormatException e) {
//            showAlert("Salary must be a number.");
//            return false;
//        }
//        if (!addEmail.getText().contains("@")) {
//            showAlert("Invalid email format.");
//            return false;
//        }
//        if (addFirstName.getText().isEmpty() || addLastName.getText().isEmpty() || addEmail.getText().isEmpty() ||
//                addAddress.getText().isEmpty() || roleComboBox.getValue() == null || addPhoneNumber.getText().isEmpty() ||
//                addUsername.getText().isEmpty() || addPassword.getText().isEmpty()) { // Check for username and password
//            showAlert("All fields must be filled.");
//            return false;
//        }
//        return true;
//    }
}
