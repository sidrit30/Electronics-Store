package MethodTests;

import Model.Users.Admin;
import Model.Users.Cashier;
import Model.Users.Employee;
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static Controller.ManageEmployeeController.onEmployeeDelete;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OnEmployeeDeleteTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    // Mock-like Employee objects (Replace with your actual Employee constructor)
    private final Employee currentUser = new Admin("Pinci", "Ilo", "qwerty", "qwerty", 10);
    private final Employee otherUser = new Cashier("berti", "berti", "berti32", "abc123", 10);

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    private String getOutput() {
        return outContent.toString().trim();
    }

    // STATEMENT & BRANCH COVERAGE

    @Test
    @DisplayName("Branch 1: Attempt to delete self")
    void testDeleteSelf() {
        onEmployeeDelete(currentUser, currentUser, false, false, false);
        assertTrue(getOutput().contains("Can't delete self!"));
    }

    @Test
    @DisplayName("Branch 2 & 3: Successful Deletion")
    void testDeleteSuccess() {
        onEmployeeDelete(otherUser, currentUser, true, true, true);
        assertTrue(getOutput().contains("User deleted successfully!"));
    }

    @Test
    @DisplayName("Branch 3 (False): DAO Failure")
    void testDeleteDaoFailure() {
        onEmployeeDelete(otherUser, currentUser, true, true, false);
        assertTrue(getOutput().contains("Error deleting user!"));
    }

    // CONDITION & MC/DC COVERAGE

    @Test
    @DisplayName("MC/DC: isAlerted is False (Short-circuit/Cancelled)")
    void testDeleteCancelledAlertFalse() {
        onEmployeeDelete(otherUser, currentUser, false, true, true);
        assertTrue(getOutput().contains("Deletion cancelled!"));
    }

    @Test
    @DisplayName("MC/DC: isOkPressed is False (User clicked Cancel)")
    void testDeleteCancelledOkFalse() {
        onEmployeeDelete(otherUser, currentUser, true, false, true);
        assertTrue(getOutput().contains("Deletion cancelled!"));
    }
}