package UnitTesting.ModelTest;

import Model.Users.Admin;
import Model.Users.Permission;
import Model.Users.Role;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class AdminTests {

    @Test
    void admin_constructor_sets_role_and_all_permissions() {
        Admin a = new Admin("Doe", "Jane", "admin", "pw", 1000);

        assertEquals(Role.ADMIN, a.getRole());
        assertEquals(EnumSet.allOf(Permission.class), a.getPermissions());
    }

    @Test
    void admin_sectorName_is_all() {
        Admin a = new Admin("Doe", "Jane", "admin", "pw", 1000);
        assertEquals("All", a.getSectorName());
    }
}
