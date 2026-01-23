package SystemTesting;




import Main.Launcher;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerUseCaseTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new Launcher().start(stage);
    }
    @Test
    @DisplayName("UC3: Manager - Complete Inventory and Performance Workflow")
    void managerCompleteInventoryWorkflow() {
        // ===== STEP 1: LOGIN AS MANAGER =====
        loginAsUser("moel1", "moel1");
        sleep(1000);

        // ===== STEP 2: CHECK FOR LOW STOCK ALERT =====
        // Manager should see low stock alert on login if applicable
        DialogPane alertDialog = lookup(".dialog-pane").queryAs(DialogPane.class);
        if (alertDialog != null && alertDialog.getHeaderText() != null &&
                alertDialog.getHeaderText().toLowerCase().contains("low stock")) {
            // Low stock alert appeared
            assertTrue(alertDialog.isVisible(), "Low stock alert should be visible");
            clickOn("Close");
            sleep(500);
        }

        // ===== STEP 3: NAVIGATE TO INVENTORY MANAGEMENT =====
        Button inventoryBtn = lookup("#view_item").queryButton();
        assertNotNull(inventoryBtn, "Inventory Management button should exist");
        clickOn(inventoryBtn);
        sleep(500);

        // Verify inventory table is displayed
        TableView inventoryTable = lookup(".table-view").queryAs(TableView.class);
        assertNotNull(inventoryTable, "Inventory table should be displayed");

        // ===== STEP 4: SELECT SECTOR =====
        ComboBox sectorComboBox = lookup(".combo-box").queryAs(ComboBox.class);
        if (sectorComboBox != null && sectorComboBox.isVisible()) {
            clickOn(sectorComboBox);
            sleep(200);
            type(javafx.scene.input.KeyCode.DOWN);
            type(javafx.scene.input.KeyCode.ENTER);
            sleep(500);
        }

        // ===== STEP 5: ADD NEW ITEM =====
        TextField itemNameField = findTextFieldByPrompt("Item Name");
        TextField supplierField = findTextFieldByPrompt("Supplier");
        TextField purchasePriceField = findTextFieldByPrompt("Purchase Price");
        TextField sellPriceField = findTextFieldByPrompt("Selling Price");
        TextField quantityField = findTextFieldByPrompt("Quantity");

        if (itemNameField != null && itemNameField.isVisible()) {
            clickOn(itemNameField).write("Test Product Item");
            clickOn(supplierField).write("Test Supplier");
            clickOn(purchasePriceField).write("100");
            clickOn(sellPriceField).write("150");
            clickOn(quantityField).write("50");

            // Select category
            ComboBox categoryComboBox = lookup(".combo-box").queryAll()
                    .stream()
                    .map(node -> (ComboBox) node)
                    .filter(cb -> cb.getPromptText() != null &&
                            cb.getPromptText().contains("Category"))
                    .findFirst()
                    .orElse(null);

            if (categoryComboBox != null) {
                clickOn(categoryComboBox);
                sleep(200);
                type(javafx.scene.input.KeyCode.DOWN);
                type(javafx.scene.input.KeyCode.ENTER);
            }

            Button addItemBtn = findButtonByText("Add New Item", "Add Item");
            if (addItemBtn != null) {
                clickOn(addItemBtn);
                sleep(1000);
            }
        }



        // ===== STEP 6: NAVIGATE TO PERFORMANCE VIEW =====
        Button homeBtn = findButtonByText("Home");
        if (homeBtn != null && homeBtn.isVisible()) {
            clickOn(homeBtn);
            sleep(500);
        }

        Button performanceBtn = findButtonByText("Performance");
        if (performanceBtn != null) {
            clickOn(performanceBtn);
            sleep(500);

            // Verify performance view is displayed
            // Look for performance-related elements (charts, statistics, etc.)
            VBox performanceView = lookup(".vbox").queryAs(VBox.class);
            assertNotNull(performanceView, "Performance view should be displayed");
        }

        // ===== STEP 7: LOGOUT =====
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
