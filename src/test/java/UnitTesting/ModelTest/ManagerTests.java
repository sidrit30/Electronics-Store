package UnitTesting.ModelTest;

import Model.Users.Manager;
import Model.Users.Permission;
import Model.Users.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTests {

    @Test
    void manager_constructor_sets_role_and_permissions() {
        Manager m = new Manager("Doe", "Max", "mgr", "pw", 900);

        assertEquals(Role.MANAGER, m.getRole());

        assertTrue(m.hasPermission(Permission.EDIT_ITEM));
        assertTrue(m.hasPermission(Permission.VIEW_ITEM));
        assertTrue(m.hasPermission(Permission.PERFORMANCE_SECTOR));
        assertTrue(m.hasPermission(Permission.VIEW_SECTOR));
    }

    @Test
    void manager_sectors_add_remove_exists_work() {
        Manager m = new Manager("Doe", "Max", "mgr", "pw", 900);

        assertFalse(m.sectorExists("Electronics"));

        m.addSector("Electronics");
        assertTrue(m.sectorExists("Electronics"));

        m.removeSector("Electronics");
        assertFalse(m.sectorExists("Electronics"));
    }

    @Test
    void manager_setSectors_replaces_internal_list() {
        Manager m = new Manager("Doe", "Max", "mgr", "pw", 900);

        ObservableList<String> list = FXCollections.observableArrayList("A", "B");
        m.setSectors(list);

        assertTrue(m.sectorExists("A"));
        assertTrue(m.sectorExists("B"));

        // getSectorName() returns getSectors().toString()
        assertEquals("[A, B]", m.getSectorName());
    }
}
