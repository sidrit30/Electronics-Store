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

public class CashierUseCaseTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new Launcher().start(stage);
    }

    @Test
    @DisplayName("UC2: Cashier - Complete Bill Creation Workflow")
    void cashierCompleteBillCreationWorkflow() {
        // ===== STEP 1: LOGIN AS CASHIER =====
        loginAsUser("sidrit", "sidrit");
        sleep(500);

        // ===== STEP 2: NAVIGATE TO CREATE BILL =====
        Button createBillBtn = findButtonByText("Create Bill");
        assertNotNull(createBillBtn, "Create Bill button should exist");
        clickOn(createBillBtn);
        sleep(500);

        // Verify bill creation view is displayed
        TableView<?> itemTable = lookup("#item-table").query();
        assertNotNull(itemTable, "Item table should be displayed");

        // ===== STEP 3: SEARCH FOR ITEM =====
        TextField searchBar = findTextFieldByPrompt("Search");
        assertNotNull(searchBar, "Search bar should exist");
        clickOn(searchBar).write("oculus");

        Button searchBtn = findButtonByText("Search");
        clickOn(searchBtn);
        sleep(500);

        // ===== STEP 4: SELECT ITEM =====

        // Make sure rows are rendered
        interact(() -> itemTable.scrollTo(0));

        Node firstCell =
                lookup(".table-row-cell")
                        .match(row -> !((TableRow<?>) row).isEmpty())
                        .nth(0)
                        .lookup(".table-cell")
                        .nth(0)
                        .query();

        clickOn(firstCell);
        type(javafx.scene.input.KeyCode.DOWN);

        // ===== STEP 5: ENTER QUANTITY =====
        TextField quantityField = findTextFieldByPrompt("Quantity");
        assertNotNull(quantityField, "Quantity field should exist");
        clickOn(quantityField).write("2");

        // ===== STEP 6: ADD ITEM TO BILL =====
        Button addItemBtn = findButtonByText("Add Item");
        assertNotNull(addItemBtn, "Add Item button should exist");
        clickOn(addItemBtn);
        sleep(500);

        TableView<?> billTable = lookup("#bill-table").queryTableView();

        assertNotNull(billTable, "Bill table should have items");
        assertTrue(billTable.getItems().size() > 0, "Bill should contain at least one item");

        // ===== STEP 7: ADD ANOTHER ITEM =====
        clickOn(searchBar).eraseText(6).write("watch");
        clickOn(searchBtn);
        sleep(500);



        // Make sure rows are rendered
        interact(() -> itemTable.scrollTo(0));

        Node firstCell1 =
                lookup(".table-row-cell")
                        .match(row -> !((TableRow<?>) row).isEmpty())
                        .nth(0)
                        .lookup(".table-cell")
                        .nth(0)
                        .query();

        clickOn(firstCell1);
        type(javafx.scene.input.KeyCode.DOWN);

        clickOn(quantityField).eraseText(1).write("5");
        clickOn(addItemBtn);
        sleep(500);

        // ===== STEP 8: VERIFY BILL PREVIEW =====
        TextArea billTextArea = lookup(".text-area").queryAs(TextArea.class);
        assertNotNull(billTextArea, "Bill preview should exist");
        assertFalse(billTextArea.getText().isEmpty(), "Bill preview should have content");

        // ===== STEP 9: REMOVE AN ITEM =====

        interact(() -> billTable.scrollTo(0));

        interact(() -> billTable.scrollTo(0));

        Node firstCellBill =
                from(billTable)  // <-- scope lookup to billTable
                        .lookup(".table-row-cell")
                        .match(row -> !((TableRow<?>) row).isEmpty())
                        .nth(0)
                        .lookup(".table-cell")
                        .nth(0)
                        .query();

        clickOn(firstCellBill);

        Button removeBtn = findButtonByText("Remove");
        if (removeBtn != null && removeBtn.isVisible()) {
            int itemsBeforeRemove = billTable.getItems().size();
            clickOn(removeBtn);
            sleep(500);
            assertTrue(billTable.getItems().size() < itemsBeforeRemove,
                    "Item should be removed from bill");
        }

        // ===== STEP 10: SAVE AND PRINT BILL =====
        Button savePrintBtn = findButtonByText("Save and Print", "Save");
        assertNotNull(savePrintBtn, "Save button should exist");
        clickOn(savePrintBtn);
        sleep(1000);

        // Verify success message
        DialogPane successDialog = lookup(".dialog-pane").queryAs(DialogPane.class);
        if (successDialog != null) {
            assertTrue(successDialog.getContentText().toLowerCase().contains("success") ||
                            successDialog.getHeaderText().toLowerCase().contains("success"),
                    "Success message should appear");
            clickOn("OK");
        }

        // Verify bill is cleared
        sleep(500);
        assertTrue(billTable.getItems().isEmpty() || billTable.getItems().size() == 0,
                "Bill table should be empty after save");

        // ===== STEP 13: LOGOUT =====
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
