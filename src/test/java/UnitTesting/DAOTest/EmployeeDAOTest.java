// Orgest Baçova

package UnitTesting.DAOTest;

import DAO.EmployeeDAO;
import Model.Exceptions.InvalidPasswordException;
import Model.Exceptions.InvalidUsernameException;
import Model.Users.Employee;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeDAOTest {

    private EmployeeDAO employeeDAO;
    private Employee mockEmployee;

    @BeforeEach
    void setUp() {
        employeeDAO = new EmployeeDAO();

        mockEmployee = Mockito.mock(Employee.class);
        when(mockEmployee.getId()).thenReturn("1");
        when(mockEmployee.getUsername()).thenReturn("john");
        when(mockEmployee.getPassword()).thenReturn("1234");
        when(mockEmployee.getFullName()).thenReturn("John Doe");

        // Clear static list safely
        employeeDAO.getEmployees().clear();
        employeeDAO.getEmployees().add(mockEmployee);
    }

    // ---------- getEmployeebyID ----------

    @Test
    void testGetEmployeeById_Valid() {
        Employee result = employeeDAO.getEmployeebyID("1");
        assertNotNull(result);
        assertEquals("john", result.getUsername());
    }

    @Test
    void testGetEmployeeById_Invalid() {
        Employee result = employeeDAO.getEmployeebyID("99");
        assertNull(result);
    }

    // ---------- validUsername ----------

    @Test
    void testValidUsername_AlreadyExists() {
        assertFalse(employeeDAO.validUsername("john"));
    }

    @Test
    void testValidUsername_Available() {
        assertTrue(employeeDAO.validUsername("newuser"));
    }

    // ---------- authLogin ----------

    @Test
    void testAuthLogin_ValidCredentials() {
        Employee result = employeeDAO.authLogin("john", "1234");
        assertNotNull(result);
        assertEquals("john", result.getUsername());
    }

    @Test
    void testAuthLogin_InvalidUsername() {
        assertThrows(
                InvalidUsernameException.class,
                () -> employeeDAO.authLogin("unknown", "1234")
        );
    }

    @Test
    void testAuthLogin_InvalidPassword() {
        assertThrows(
                InvalidPasswordException.class,
                () -> employeeDAO.authLogin("john", "wrong")
        );
    }
}
