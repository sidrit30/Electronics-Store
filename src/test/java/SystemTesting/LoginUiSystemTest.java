package SystemTesting;

import Main.Launcher;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginUiSystemTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        // Launch your real JavaFX app
        new Launcher().start(stage);
    }

    @Test
    void invalidLogin_shouldShowSomeErrorMessage() {
        TextField username = lookup(node -> node instanceof TextField && !(node instanceof PasswordField))
                .queryAs(TextField.class);
        assertNotNull(username, "Could not find a TextField for username");

        PasswordField password = lookup(node -> node instanceof PasswordField).queryAs(PasswordField.class);
        assertNotNull(password, "Could not find a PasswordField");

        clickOn(username).write("wrg");
        clickOn(password).write("wrgPass");

        // 4) Click a button that contains the word "Login"
        Set<Node> buttons = lookup(node -> node instanceof Button).queryAll();
        Button loginBtn = buttons.stream()
                .map(n -> (Button) n)
                .filter(b -> b.getText() != null && b.getText().toLowerCase().contains("login"))
                .findFirst()
                .orElse(null);

        assertNotNull(loginBtn, "Could not find a Login button (text containing 'Login')");
        clickOn(loginBtn);


        Set<Node> labels = lookup(Label.class::isInstance).queryAll();
        boolean anyVisibleErrorLabel = labels.stream()
                .map(n -> (Label) n)
                .anyMatch(l -> l.isVisible() && l.getText() != null && !l.getText().trim().isEmpty()
                        && l.getText().toLowerCase().contains("invalid"));

        assertTrue(anyVisibleErrorLabel,
                "Expected an error label containing 'invalid' to be visible after invalid login");
    }
}

