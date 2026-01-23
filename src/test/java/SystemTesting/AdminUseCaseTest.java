package SystemTesting;


import Main.Launcher;
import Model.Users.Employee;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Set;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;


class AdminUseCaseTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new Launcher().start(stage);
    }

    /**
     * USE CASE 1: Admin Complete Workflow
     * Flow: Login as Admin → Add Employee → Edit Employee → Delete Employee → Logout
     */
    @Test
    @DisplayName("UC1: Admin - Complete Employee Management Workflow")
    void adminCompleteEmployeeManagementWorkflow() throws TimeoutException {
        // ===== STEP 1: LOGIN AS ADMIN =====
        loginAsUser("admin", "admin");

        // Verify successful login - should see welcome screen or home page
        sleep(500);
        assertTrue(lookup("Welcome to Jupiter").tryQuery().isPresent(),
                "Should see welcome message after login");

        // ===== STEP 2: NAVIGATE TO EMPLOYEE MANAGEMENT =====
        Button employeeManagementBtn = lookup("#view_sector").queryButton();
        assertNotNull(employeeManagementBtn, "Should find Employee Management button");
        clickOn(employeeManagementBtn);
        sleep(500);

        // Verify employee management view is displayed
        TableView<Employee> employeeTable = lookup(".table-view").queryTableView();
        assertNotNull(employeeTable, "Employee table should be displayed");

        // ===== STEP 3: ADD NEW EMPLOYEE =====
        // Find and fill the employee form fields
        TextField firstNameField = findTextFieldByPrompt("First Name");
        TextField lastNameField = findTextFieldByPrompt("Last Name");
        TextField emailField = findTextFieldByPrompt("Email");
        TextField addressField = findTextFieldByPrompt("Address");
        TextField usernameField = findTextFieldByPrompt("Username");
        PasswordField passwordField = findPasswordFieldByPrompt("Password");
        TextField salaryField = findTextFieldByPrompt("Salary");
        TextField phoneField = findTextFieldByPrompt("Phone Number");

        assertNotNull(firstNameField, "First name field should exist");
        assertNotNull(lastNameField, "Last name field should exist");
        assertNotNull(usernameField, "Username field should exist");
        assertNotNull(passwordField, "Password field should exist");

        // Fill in employee details
        clickOn(firstNameField).write("TestFirstName");
        clickOn(lastNameField).write("TestLastName");
        clickOn(emailField).write("test@example.com");
        clickOn(addressField).write("123 Test Street");
        clickOn(usernameField).write("testuser123");
        clickOn(passwordField).write("testpass123");
        clickOn(salaryField).write("50000");
        clickOn(phoneField).write("1234567890");

        // Select role (Cashier)
        ComboBox roleComboBox = lookup(".combo-box").queryAs(ComboBox.class);
        assertNotNull(roleComboBox, "Role combo box should exist");
        clickOn(roleComboBox);
        sleep(200);
        type(javafx.scene.input.KeyCode.DOWN); // Select Cashier
        type(javafx.scene.input.KeyCode.ENTER);

        // Select sector
        ListView sectorList = lookup(".list-view").queryAs(ListView.class);
        if (sectorList != null && sectorList.isVisible()) {
            clickOn(sectorList);
            sleep(200);
            type(javafx.scene.input.KeyCode.DOWN);
            type(javafx.scene.input.KeyCode.SPACE); // Select sector
        }

        // Click Add Employee button
        Button addEmployeeBtn = findButtonByText("Add New Employee", "Add Employee");
        assertNotNull(addEmployeeBtn, "Add Employee button should exist");
        clickOn(addEmployeeBtn);
        sleep(1000);

        // Verify success alert appears
        DialogPane dialogPane = lookup(".dialog-pane").queryAs(DialogPane.class);
        if (dialogPane != null) {
            assertTrue(dialogPane.getContentText().toLowerCase().contains("success") ||
                            dialogPane.getHeaderText().toLowerCase().contains("success"),
                    "Success message should appear");
            clickOn("OK"); // Close the alert
        }

        // Verify employee appears in table
        sleep(500);
        int tableSize = employeeTable.getItems().size();
        assertTrue(tableSize > 0, "Employee table should have at least one employee");

        // ===== STEP 4: SEARCH FOR THE NEW EMPLOYEE =====
        TextField searchField = findTextFieldByPrompt("Search");
        assertNotNull(searchField, "Search field should exist");
        clickOn(searchField).write("TestFirstName");

        Button searchBtn = findButtonByText("Search");
        clickOn(searchBtn);
        sleep(500);

        // Verify search results
        assertTrue(employeeTable.getItems().size() > 0,
                "Search should return results");


        // ===== STEP 5: DELETE EMPLOYEE =====
        // Select employee
        clickOn((Node) lookup(".table-row-cell").nth(0).query());
        type(javafx.scene.input.KeyCode.DOWN);

        // Click delete button
        Button deleteBtn = findButtonByText("Delete");
        assertNotNull(deleteBtn, "Delete button should exist");
        clickOn(deleteBtn);
        sleep(500);

        // Confirm deletion
        DialogPane confirmDialog = lookup(".dialog-pane").queryAs(DialogPane.class);
        if (confirmDialog != null) {
            clickOn("OK");
            sleep(1000);
        }

        DialogPane confirmDialog2 = lookup(".dialog-pane").queryAs(DialogPane.class);
        if (confirmDialog != null) {
            clickOn("OK");
            sleep(1000);
        }

        // ===== STEP 6: NAVIGATE HOME =====
        Button homeBtn = findButtonByText("Home");
        if (homeBtn != null && homeBtn.isVisible()) {
            clickOn(homeBtn);
            sleep(500);
        }

        // ===== STEP 7: LOGOUT =====
        logout();

        // Verify we're back at login screen
        sleep(500);
        TextField usernameLoginField = lookup(node ->
                node instanceof TextField && !(node instanceof PasswordField))
                .queryAs(TextField.class);
        assertNotNull(usernameLoginField, "Should be back at login screen");
    }



    // ==================== HELPER METHODS ====================

    private void loginAsUser(String username, String password) {
        TextField usernameField = lookup(node ->
                node instanceof TextField && !(node instanceof PasswordField))
                .queryAs(TextField.class);

        PasswordField passwordField = lookup(node ->
                node instanceof PasswordField)
                .queryAs(PasswordField.class);

        assertNotNull(usernameField, "Username field should exist");
        assertNotNull(passwordField, "Password field should exist");

        clickOn(usernameField).write(username);
        clickOn(passwordField).write(password);

        Button loginBtn = findButtonByText("Login");
        assertNotNull(loginBtn, "Login button should exist");
        clickOn(loginBtn);
    }

    private void logout() {
        MenuBar menuBar = lookup(".menu-bar").queryAs(MenuBar.class);
        if (menuBar != null) {
            clickOn("Log Out");
            clickOn(
                    (Node) lookup(".menu-item .label")
                            .match(node -> ((Label) node).getText().equals("Log Out"))
                            .query()
            );

            sleep(500);
        }
    }

    private Button findButtonByText(String... possibleTexts) {
        Set<Node> buttons = lookup(node -> node instanceof Button).queryAll();
        return buttons.stream()
                .map(n -> (Button) n)
                .filter(b -> b.getText() != null &&
                        java.util.Arrays.stream(possibleTexts)
                                .anyMatch(text -> b.getText().toLowerCase()
                                        .contains(text.toLowerCase())))
                .findFirst()
                .orElse(null);
    }

    private TextField findTextFieldByPrompt(String promptText) {
        Set<Node> textFields = lookup(node -> node instanceof TextField).queryAll();
        return textFields.stream()
                .map(n -> (TextField) n)
                .filter(tf -> tf.getPromptText() != null &&
                        tf.getPromptText().toLowerCase()
                                .contains(promptText.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    private PasswordField findPasswordFieldByPrompt(String promptText) {
        Set<Node> passwordFields = lookup(node -> node instanceof PasswordField).queryAll();
        return passwordFields.stream()
                .map(n -> (PasswordField) n)
                .filter(pf -> pf.getPromptText() != null &&
                        pf.getPromptText().toLowerCase()
                                .contains(promptText.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    private TextField findTextFieldByValue(String value) {
        Set<Node> textFields = lookup(node -> node instanceof TextField).queryAll();
        return textFields.stream()
                .map(n -> (TextField) n)
                .filter(tf -> tf.getText() != null &&
                        tf.getText().toLowerCase().contains(value.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public FxRobot sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}