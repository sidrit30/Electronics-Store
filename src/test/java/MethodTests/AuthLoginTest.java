package MethodTests;

import DAO.EmployeeDAO;
import Model.Exceptions.InvalidPasswordException;
import Model.Exceptions.InvalidUsernameException;
import Model.Users.Cashier;
import Model.Users.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthLoginTest {

    private EmployeeDAO employeeDAO;
    private List<Employee> testEmployees;
    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        employeeDAO = new EmployeeDAO();

        // Create test employees
        employee1 = new Cashier("Doe", "John", "john", "pass123", 50000);
        employee2 = new Cashier("Smith", "Jane", "jane", "pass456", 55000);

        testEmployees = new ArrayList<>();
        testEmployees.add(employee1);
        testEmployees.add(employee2);
    }


    @Test
    @DisplayName("EC-1")
    void testEC_ValidCredentials() {
        Employee result = employeeDAO.authLogin("john", "pass123", testEmployees);
        assertEquals(employee1, result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    @DisplayName("EC-2")
    void testEC_WrongPassword() {
        assertThrows(InvalidPasswordException.class, () -> {
            employeeDAO.authLogin("john", "wrongpassword", testEmployees);
        });
    }

    @Test
    @DisplayName("EC-3")
    void testEC_InvalidUsername() {
        assertThrows(InvalidUsernameException.class, () -> {
            employeeDAO.authLogin("unknown", "pass123", testEmployees);
        });
    }

    @Test
    @DisplayName("EC-4")
    void testEC_EmptyList() {
        assertThrows(InvalidUsernameException.class, () -> {
            employeeDAO.authLogin("john", "pass123", new ArrayList<>());
        });
    }

    @Test
    @DisplayName("EC-5")
    void testEC_SecondEmployee() {
        Employee result = employeeDAO.authLogin("jane", "pass456", testEmployees);
        assertEquals(employee2, result);
        assertEquals("Jane", result.getFirstName());
    }
}