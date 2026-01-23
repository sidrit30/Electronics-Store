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

public class ManageBillIntegrationTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new Launcher().start(stage);
    }

    @Test
    @DisplayName("BillDAO - Controller - View")
    void testBillIntegration() {
        // ---- LOGIN ----
        loginAsUser("admin", "admin");
        sleep(800);

        Button viewBillBtn = lookup("#view_bill").queryButton();
        assertNotNull(viewBillBtn, "Should find Employee Management button");
        clickOn(viewBillBtn);
        sleep(500);

        // ---- FIND BILL TABLE ----
        TableView<?> billTable = lookup(node ->
                node instanceof TableView<?> &&
                        ((TableView<?>) node).getColumns().size() >= 5 // unique to bill table
        ).queryAs(TableView.class);

        assertNotNull(billTable, "Bill TableView should exist");

        // ---- VERIFY DATA LOADED ----
        assertFalse(
                billTable.getItems().isEmpty(),
                "Bill table should contain data"
        );

        // ---- SELECT FIRST ROW ----
        interact(() -> billTable.getSelectionModel().select(0));
        sleep(300);

        Object selectedBill = billTable.getSelectionModel().getSelectedItem();
        assertNotNull(selectedBill, "A bill should be selected");

        String billText = selectedBill.toString(); // fallback
        if (selectedBill instanceof Model.Bill bill) {
            billText = bill.printBill();
        }

        // ---- CLICK VIEW DETAILS ----
        Button viewDetailsBtn = findButtonByText("View Bill Details");
        assertNotNull(viewDetailsBtn, "View Bill Details button should exist");

        clickOn(viewDetailsBtn);
        sleep(500);

        // ---- VERIFY POPUP WINDOW ----
        Stage popupStage = listTargetWindows()
                .stream()
                .filter(w -> w instanceof Stage)
                .map(w -> (Stage) w)
                .filter(s -> "Bill Details".equals(s.getTitle()))
                .findFirst()
                .orElse(null);

        assertNotNull(popupStage, "Bill Details popup should appear");

        // ---- VERIFY BILL CONTENT ----
        TextArea billDetailsArea = lookup(node ->
                node instanceof TextArea
        ).queryAs(TextArea.class);

        assertNotNull(billDetailsArea, "Bill details TextArea should exist");

        assertTrue(
                billDetailsArea.getText().contains(billText.split("\n")[0]),
                "Bill details should match selected bill"
        );

        // ---- CLOSE POPUP ----
        clickOn("OK");
        sleep(300);

        // ---- LOGOUT ----
        logout();
        sleep(500);
    }

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
