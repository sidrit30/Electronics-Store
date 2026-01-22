package UnitTesting.ModelTest;

import Model.Users.Permission;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermissionTests {

    @Test
    void permission_enum_contains_expected_values() {
        assertNotNull(Permission.valueOf("CREATE_BILL"));
        assertNotNull(Permission.valueOf("VIEW_BILL"));
        assertNotNull(Permission.valueOf("VIEW_ITEM"));
        assertNotNull(Permission.valueOf("EDIT_ITEM"));
        assertNotNull(Permission.valueOf("VIEW_SECTOR"));
        assertNotNull(Permission.valueOf("EDIT_SECTOR"));
        assertNotNull(Permission.valueOf("PERFORMANCE_SECTOR"));
        assertNotNull(Permission.valueOf("PERFORMANCE_ALL"));
    }
}
