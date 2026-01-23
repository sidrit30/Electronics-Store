package IntegrationTesting;

import Main.Launcher;
import Model.Items.Item;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ManageItemIntegrationTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new Launcher().start(stage);
    }

    @Test
    @DisplayName("InventoryDAO - Controller - View")
    void testInventoryIntegration() {
        // ---- LOGIN ----
        loginAsUser("admin", "admin");
        sleep(800);

        // ---- NAVIGATE TO MANAGE INVENTORY ----
        Button viewInventoryBtn = lookup("#view_item").queryButton();
        assertNotNull(viewInventoryBtn, "Manage Inventory button should exist");
        clickOn(viewInventoryBtn);
        sleep(600);

        // ---- FIND INVENTORY TABLE ----
        TableView<Item> inventoryTable = lookup(node ->
                node instanceof TableView<?> table &&
                        table.getColumns().size() >= 8 // inventory table is wide & unique
        ).queryAs(TableView.class);

        assertNotNull(inventoryTable, "Inventory TableView should exist");

        // ---- VERIFY COLUMNS EXIST ----
        assertTrue(
                inventoryTable.getColumns()
                        .stream()
                        .anyMatch(c -> c.getText().equalsIgnoreCase("Item Name")),
                "Item Name column should exist"
        );

        assertTrue(
                inventoryTable.getColumns()
                        .stream()
                        .anyMatch(c -> c.getText().equalsIgnoreCase("Quantity")),
                "Quantity column should exist"
        );

        // ---- VERIFY DATA IS LOADED ----
        assertFalse(
                inventoryTable.getItems().isEmpty(),
                "Inventory table should contain data"
        );

        // ---- SELECT FIRST ROW (SANITY CHECK) ----
        interact(() -> inventoryTable.getSelectionModel().select(0));
        sleep(300);

        Item selectedItem = inventoryTable.getSelectionModel().getSelectedItem();
        assertNotNull(selectedItem, "An inventory item should be selectable");

        // ---- BASIC FIELD VALIDATION ----
        assertNotNull(selectedItem.getItemName(), "Item name should not be null");
        assertTrue(selectedItem.getQuantity() >= 0, "Quantity should be valid");

        // ---- LOGOUT ----
        logout();
        sleep(500);
    }

    // ==================== HELPERS ====================

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
            sleep(400);
        }
    }

    private Button findButtonByText(String... possibleTexts) {
        Set<Node> buttons = lookup(node -> node instanceof Button).queryAll();
        return buttons.stream()
                .map(n -> (Button) n)
                .filter(b -> b.getText() != null &&
                        java.util.Arrays.stream(possibleTexts)
                                .anyMatch(text ->
                                        b.getText().toLowerCase().contains(text.toLowerCase())))
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
