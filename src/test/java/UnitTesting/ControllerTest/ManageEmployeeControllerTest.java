package UnitTesting.ControllerTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import Controller.ManageEmployeeController;
import DAO.EmployeeDAO;
import Model.Users.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManageEmployeeControllerTest {

    @Mock
    private EmployeeDAO mockDao;

    @Test
    @DisplayName("Should return true for a perfectly valid employee")
    void testIsValid_Success() {
        // Arrange
        when(mockDao.validUsername("validUser")).thenReturn(true);

        // Act
        boolean result = ManageEmployeeController.isValid(
                "John", "Doe", "validUser", "pass123", Role.CASHIER, 2500.0, mockDao
        );

        // Assert
        assertTrue(result);
    }

    @ParameterizedTest(name = "Field check - {0} {1} {2} {3} {4} -> valid: false")
    @DisplayName("Should return false if any required field is blank or null")
    @CsvSource({
            "'', 'Doe', 'user', 'pass', 'CASHIER'", // First name blank
            "'John', '', 'user', 'pass', 'CASHIER'", // Last name blank
            "'John', 'Doe', '', 'pass', 'CASHIER'",   // Username blank
            "'John', 'Doe', 'user', '', 'CASHIER'",   // Password blank
    })
    void testIsValid_BlankFields(String fn, String ln, String un, String pw, Role role) {
        assertFalse(ManageEmployeeController.isValid(fn, ln, un, pw, role, 1000.0, mockDao));
    }

    @Test
    @DisplayName("Should return false if role is null")
    void testIsValid_NullRole() {
        assertFalse(ManageEmployeeController.isValid("John", "Doe", "user", "pass", null, 1000.0, mockDao));
    }

    @ParameterizedTest(name = "Credential length check - {0} (length {1}) -> valid: false")
    @CsvSource({
            "abc, 3",               // Username too short
            "thisusernameiswaytoolongtobevalid, 33" // Username too long
    })
    void testIsValid_InvalidCredentialLengths(String invalidInput) {
        // Testing username length branch
        assertFalse(ManageEmployeeController.isValid("John", "Doe", invalidInput, "pass123", Role.ADMIN, 1000.0, mockDao));

        // Testing password length branch
        assertFalse(ManageEmployeeController.isValid("John", "Doe", "validUser", invalidInput, Role.ADMIN, 1000.0, mockDao));
    }

    @Test
    @DisplayName("Should return false if username is already taken in database")
    void testIsValid_DuplicateUsername() {
        when(mockDao.validUsername("takenUser")).thenReturn(false);

        boolean result = ManageEmployeeController.isValid("John", "Doe", "takenUser", "pass123", Role.MANAGER, 1000.0, mockDao);

        assertFalse(result);
        verify(mockDao).validUsername("takenUser");
    }

    @ParameterizedTest(name = "Salary check - {0} -> valid: false")
    @CsvSource({
            "0.0",
            "-1.0",
            "-500.50"
    })
    void testIsValid_InvalidSalary(double invalidSalary) {
        // Mock DAO for the username check which comes before salary check
        when(mockDao.validUsername("validUser")).thenReturn(true);

        assertFalse(ManageEmployeeController.isValid("John", "Doe", "validUser", "pass123", Role.CASHIER, invalidSalary, mockDao));
    }
}