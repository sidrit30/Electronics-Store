package UnitTesting.ModelTest;

import Model.Items.Item;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class ItemTests {

    private Item makeItem(int qty, double sale, double purchase) {
        return new Item(
                "Mouse",
                "Accessories",
                sale,
                purchase,
                qty,
                "SupplierX",
                "Wireless mouse",
                "Electronics"
        );
    }

    @Test
    void constructor_shouldSetFieldsCorrectly() {
        Item item = makeItem(10, 20.0, 12.0);

        assertNotNull(item.getItemID());
        assertEquals("Mouse", item.getItemName());
        assertEquals("Accessories", item.getItemCategory());
        assertEquals("Electronics", item.getSectorName());

        assertEquals(20.0, item.getSellingPrice(), 0.0001);
        assertEquals(12.0, item.getPurchasePrice(), 0.0001);
        assertEquals(10, item.getQuantity());

        assertEquals("SupplierX", item.getSupplier());
        assertEquals("Wireless mouse", item.getItemDescription());
    }

    @Test
    void purchaseDate_shouldMatchExpectedFormat() {
        Item item = makeItem(10, 20.0, 12.0);

        String date = item.getPurchaseDate();
        // Expected format: yyyy/MM/dd HH:mm:ss
        assertTrue(Pattern.matches("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}", date));
    }

    @Test
    void setters_shouldUpdateValues() {
        Item item = makeItem(10, 20.0, 12.0);

        item.setSellingPrice(25.5);
        item.setPurchasePrice(13.5);
        item.setQuantity(7);
        item.setSupplier("SupplierY");
        item.setItemDescription("New description");

        assertEquals(25.5, item.getSellingPrice(), 0.0001);
        assertEquals(13.5, item.getPurchasePrice(), 0.0001);
        assertEquals(7, item.getQuantity());
        assertEquals("SupplierY", item.getSupplier());
        assertEquals("New description", item.getItemDescription());
    }

    @Test
    void serialization_roundTrip_shouldPreserveSerializableState() throws Exception {
        Item original = makeItem(10, 20.0, 12.0);

        byte[] bytes;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(original);
            bytes = bos.toByteArray();
        }

        Item restored;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            restored = (Item) in.readObject();
        }

        assertNotNull(restored);

        // Basic fields (final fields)
        assertEquals(original.getItemID(), restored.getItemID());
        assertEquals(original.getItemName(), restored.getItemName());
        assertEquals(original.getItemCategory(), restored.getItemCategory());
        assertEquals(original.getSectorName(), restored.getSectorName());

        // Transient properties restored through readObject()
        assertEquals(original.getSellingPrice(), restored.getSellingPrice(), 0.0001);
        assertEquals(original.getPurchasePrice(), restored.getPurchasePrice(), 0.0001);
        assertEquals(original.getQuantity(), restored.getQuantity());
        assertEquals(original.getSupplier(), restored.getSupplier());
        assertEquals(original.getItemDescription(), restored.getItemDescription());
    }
}
