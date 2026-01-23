package IntegrationTesting;

import Main.Launcher;
import Model.Users.Employee;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ManageEmployeeIntegrationTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new Launcher().start(stage);
    }

    @Test
    @DisplayName("EmployeeDAO - Controller - View (Data Load Only)")
    void testEmployeeDataIsLoadedAndDisplayed() {

        // ---- LOGIN ----
        loginAsUser("admin", "admin");
        sleep(800);

        // ---- NAVIGATE TO EMPLOYEE MANAGEMENT ----
        Button employeeManagementBtn = lookup("#view_sector").queryButton();
        assertNotNull(employeeManagementBtn, "Employee Management button should exist");
        clickOn(employeeManagementBtn);
        sleep(500);

        // ---- FIND EMPLOYEE TABLE ----
        TableView<Employee> employeeTable =
                lookup(node -> node instanceof TableView<?>)
                        .queryAs(TableView.class);

        assertNotNull(employeeTable, "Employee TableView should exist");

        // ---- VERIFY DATA IS LOADED ----
        assertFalse(
                employeeTable.getItems().isEmpty(),
                "Employee table should contain data"
        );

        // ---- VERIFY FIRST ROW IS RENDERED ----
        interact(() -> employeeTable.getSelectionModel().select(0));
        sleep(300);

        Employee selectedEmployee =
                employeeTable.getSelectionModel().getSelectedItem();

        assertNotNull(
                selectedEmployee,
                "Selecting first row should return an Employee"
        );

        // ---- OPTIONAL STRONG ASSERTION ----
        assertNotNull(
                selectedEmployee.getFirstName(),
                "Employee first name should not be null"
        );

        // ---- LOGOUT ----
        logout();
        sleep(500);
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
            clickOn( (Node)
                    lookup(".menu-item .label")
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
                                .anyMatch(text ->
                                        b.getText().toLowerCase()
                                                .contains(text.toLowerCase())))
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
        return this;
    }
}
