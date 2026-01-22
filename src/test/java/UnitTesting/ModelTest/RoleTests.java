package UnitTesting.ModelTest;

import Model.Users.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTests {

    @Test
    void role_enum_contains_expected_values() {
        assertNotNull(Role.valueOf("CASHIER"));
        assertNotNull(Role.valueOf("MANAGER"));
        assertNotNull(Role.valueOf("ADMIN"));

        assertEquals(3, Role.values().length);
    }
}
