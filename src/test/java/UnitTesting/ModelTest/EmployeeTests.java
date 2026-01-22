package UnitTesting.ModelTest;

import Model.Users.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTests {

    @Test
    void employee_commonFields_and_setters_work() {
        Employee e = new Cashier("Doe", "John", "john", "pw", 500);

        assertNotNull(e.getId());
        assertFalse(e.getId().isBlank());

        assertEquals("Doe", e.getLastName());
        assertEquals("John", e.getFirstName());
        assertEquals("John Doe", e.getFullName());

        e.setAddress("Prishtine");
        e.setPhone("049123123");
        e.setEmail("a@b.com");
        e.setUsername("newUser");
        e.setPassword("newPass");
        e.setSalary(999);

        assertEquals("Prishtine", e.getAddress());
        assertEquals("049123123", e.getPhone());
        assertEquals("a@b.com", e.getEmail());
        assertEquals("newUser", e.getUsername());
        assertEquals("newPass", e.getPassword());
        assertEquals(999, e.getSalary(), 0.0001);

        assertEquals("John Doe", e.toString());
    }

    @Test
    void employee_permissions_add_remove_hasPermission_work() {
        Cashier c = new Cashier("Doe", "John", "john", "pw", 500);

        // Cashier constructor sets these permissions in your code
        assertTrue(c.hasPermission(Permission.CREATE_BILL));
        assertTrue(c.hasPermission(Permission.VIEW_BILL));

        c.addPermission(Permission.EDIT_ITEM);
        assertTrue(c.hasPermission(Permission.EDIT_ITEM));

        c.removePermission(Permission.EDIT_ITEM);
        assertFalse(c.hasPermission(Permission.EDIT_ITEM));
    }

    @Test
    void employee_serialization_restores_transient_fields() throws Exception {
        Cashier original = new Cashier("Doe", "John", "john", "pw", 500);

        original.setAddress("Addr");
        original.setPhone("123");
        original.setEmail("x@y.com");
        original.setSalary(777);
        original.setPermissions(EnumSet.of(Permission.CREATE_BILL));

        // serialize
        byte[] bytes;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(original);
            bytes = bos.toByteArray();
        }

        // deserialize
        Cashier restored;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            restored = (Cashier) in.readObject();
        }

        // non-transient final fields
        assertEquals(original.getId(), restored.getId());
        assertEquals(original.getFullName(), restored.getFullName());

        // transient fields restored by readObject in Employee.java
        assertEquals("Addr", restored.getAddress());
        assertEquals("123", restored.getPhone());
        assertEquals("x@y.com", restored.getEmail());
        assertEquals("john", restored.getUsername());
        assertEquals("pw", restored.getPassword());
        assertEquals(777, restored.getSalary(), 0.0001);

        assertTrue(restored.hasPermission(Permission.CREATE_BILL));
        assertFalse(restored.hasPermission(Permission.VIEW_BILL)); // not in EnumSet we saved
    }

    @Test
    void employee_ids_should_be_unique() {
        Employee a = new Cashier("A", "A", "u1", "p", 1);
        Employee b = new Cashier("B", "B", "u2", "p", 1);

        assertNotEquals(a.getId(), b.getId());
    }
}
