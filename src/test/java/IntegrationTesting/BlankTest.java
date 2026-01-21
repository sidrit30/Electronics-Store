package IntegrationTesting;
import DAO.EmployeeDAO;
import Model.Exceptions.InvalidPasswordException;
import Model.Exceptions.InvalidUsernameException;
import Model.Users.Employee;
import View.LoginPage;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    private LoginController loginController;
    private EmployeeDAO mockEmployeeDAO;
    private LoginPage mockLoginPage;

    // Mock UI Components
    private TextField usernameField;
    private PasswordField passwordField;
    private Label errorLabel;

    @BeforeEach
    void setUp() {
        // 1. Initialize Mocks
        mockEmployeeDAO = mock(EmployeeDAO.class);
        mockLoginPage = mock(LoginPage.class);

        usernameField = new TextField();
        passwordField = new PasswordField();
        errorLabel = new Label();
        errorLabel.setVisible(false);

        // 2. Link mocks to the UI components
        when(mockLoginPage.getUsernameField()).thenReturn(usernameField);
        when(mockLoginPage.getPasswordField()).thenReturn(passwordField);
        when(mockLoginPage.getErrorLabel()).thenReturn(errorLabel);

        // 3. Instantiate controller with mocks
        // Note: You may need to wrap this in a JavaFX thread runner
        // if the constructor logic triggers UI toolkit checks.
        loginController = new LoginController(mockEmployeeDAO, mockLoginPage);
    }

    @Test
    void testLoginFailure_InvalidUsername() throws InvalidUsernameException, InvalidPasswordException {
        // Arrange
        usernameField.setText("wrongUser");
        passwordField.setText("password123");

        when(mockEmployeeDAO.authLogin("wrongUser", "password123"))
                .thenThrow(new InvalidUsernameException("User not found"));

        // Act
        // We manually trigger the logic or use reflection to call the private login()
        // For this example, we assume we've exposed login() or are testing via the button
        invokePrivateMethod(loginController, "login");

        // Assert
        assertTrue(errorLabel.isVisible(), "Error label should be visible on failed login");
        assertEquals("User not found", errorLabel.getText());
    }

    @Test
    void testLoginFailure_InvalidPassword() throws InvalidUsernameException, InvalidPasswordException {
        // Arrange
        usernameField.setText("admin");
        passwordField.setText("wrongPass");

        when(mockEmployeeDAO.authLogin("admin", "wrongPass"))
                .thenThrow(new InvalidPasswordException("Incorrect Password"));

        // Act
        invokePrivateMethod(loginController, "login");

        // Assert
        assertTrue(errorLabel.isVisible());
        assertEquals("Incorrect Password", errorLabel.getText());
    }

    @Test
    void testLoginSuccess() throws InvalidUsernameException, InvalidPasswordException {
        // Arrange
        usernameField.setText("admin");
        passwordField.setText("correctPass");
        Employee mockEmp = mock(Employee.class);

        when(mockEmployeeDAO.authLogin("admin", "correctPass")).thenReturn(mockEmp);

        // Act
        // Note: This test might fail if it tries to open a new Stage
        // without a full JavaFX environment set up.
        assertDoesNotThrow(() -> invokePrivateMethod(loginController, "login"));

        // Verify the DAO was actually called
        verify(mockEmployeeDAO).authLogin("admin", "correctPass");
    }

    // Helper to access private login() method for unit testing
    private void invokePrivateMethod(Object obj, String methodName) {
        try {
            java.lang.reflect.Method method = obj.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            method.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}