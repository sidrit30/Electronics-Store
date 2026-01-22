package MethodTests;

import Model.Items.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemSetQuantityTest {

    @Test
    void setQuantityToZero() {
        Item item = new Item(
                "Keyboard",
                "Acc",
                20,
                10,
                10,
                "Supplier",
                "Description",
                "Sector"
        );

        item.setQuantity(0);

        assertEquals(0, item.getQuantity());
    }

    @Test
    void setQuantityToPositiveValue() {
        Item item = new Item(
                "Keyboard",
                "Acc",
                20,
                10,
                10,
                "Supplier",
                "Description",
                "Sector"
        );

        item.setQuantity(5);

        assertEquals(5, item.getQuantity());
    }
}
