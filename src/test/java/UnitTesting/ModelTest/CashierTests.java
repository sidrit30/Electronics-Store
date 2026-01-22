package UnitTesting.ModelTest;

import Model.Users.Cashier;
import Model.Users.Permission;
import Model.Users.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CashierTests {

    @Test
    void cashier_constructor_sets_role_and_permissions() {
        Cashier c = new Cashier("Doe", "John", "john", "pw", 500);

        assertEquals(Role.CASHIER, c.getRole());
        assertTrue(c.hasPermission(Permission.CREATE_BILL));
        assertTrue(c.hasPermission(Permission.VIEW_BILL));
    }

    @Test
    void cashier_sectorName_get_set() {
        Cashier c = new Cashier("Doe", "John", "john", "pw", 500);

        c.setSectorName("Electronics");
        assertEquals("Electronics", c.getSectorName());
    }
}
