package SystemTesting;

import Main.Launcher;
import Model.Users.Employee;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Set;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;


public class EditProfileUseCaseTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        new Launcher().start(stage);
    }

    @Test
    @DisplayName("UC4: User Profile Management Workflow")
    void userProfileManagementWorkflow() {
// ===== STEP 1: LOGIN =====
        loginAsUser("sidrit", "sidrit");
        sleep(500);

        // ===== STEP 2: NAVIGATE TO PROFILE =====
        MenuBar menuBar = lookup(".menu-bar").queryAs(MenuBar.class);
        assertNotNull(menuBar, "Menu bar should exist");

        // Open User Profile menu
        clickOn("User Profile");
        clickOn( (Node)
                lookup(".menu-item .label")
                        .match(label -> ((Label) label).getText().equals("User Profile"))
                        .query()
        );
        sleep(500);

        verifyThat("#pass_field", Node::isVisible);


        // ===== STEP 3: VERIFY PROFILE VIEW =====
        TextField emailField = lookup("#email_field").queryAs(TextField.class);
        TextField passwordField = lookup("#pass_field").queryAs(TextField.class);
        TextField addressField = lookup("#address_field").queryAs(TextField.class);
        TextField phoneField = lookup("#phone_field").queryAs(TextField.class);


        // ===== STEP 4: EDIT PROFILE INFORMATION =====
        clickOn(emailField).eraseText(emailField.getText().length()).write("newemail@example.com");
        clickOn(passwordField).eraseText(passwordField.getText().length()).write("newpassword123");

        clickOn(addressField).eraseText(addressField.getText().length()).write("123 Test St");
        clickOn(phoneField).eraseText(phoneField.getText().length()).write("9876543210");

        // ===== STEP 5: SAVE CHANGES =====
        Button saveBtn = lookup(".button")
                .match(b -> ((Button) b).getText().toLowerCase().contains("save"))
                .queryAs(Button.class);
        assertNotNull(saveBtn, "Save button should exist");
        clickOn(saveBtn);
        sleep(1000);

        DialogPane successDialog = lookup(".dialog-pane").queryAs(DialogPane.class);
        if (successDialog != null) {
            clickOn("OK");
        }

        // ===== STEP 6: LOGOUT =====
        logout();
        sleep(500);
        loginAsUser("sidrit", "newpassword123");
        sleep(500);
        logout();
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
