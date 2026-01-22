package UnitTesting.ModelTest;

import Model.Exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionTests {

    @Test
    void alreadyExistingException_shouldStoreMessage() {
        AlreadyExistingException ex = new AlreadyExistingException("already exists");
        assertEquals("already exists", ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void invalidUsernameException_shouldStoreMessage() {
        InvalidUsernameException ex = new InvalidUsernameException("bad username");
        assertEquals("bad username", ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void invalidPasswordException_shouldStoreMessage() {
        InvalidPasswordException ex = new InvalidPasswordException("bad password");
        assertEquals("bad password", ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void insufficientStockException_shouldStoreMessage_andBeException() {
        InsufficientStockException ex = new InsufficientStockException("not enough stock");
        assertEquals("not enough stock", ex.getMessage());
        assertTrue(ex instanceof Exception);
    }
}
