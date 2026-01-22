package MethodTests;

import Model.Items.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemGetSellingPriceTest {

    @Test
    void sellingPriceIsReturnedCorrectly() {
        Item item = new Item(
                "Monitor",
                "Acc",
                150,
                100,
                5,
                "Supplier",
                "Description",
                "Sector"
        );

        double price = item.getSellingPrice();

        assertEquals(150, price);
    }

    @Test
    void sellingPriceAtZeroBoundary() {
        Item item = new Item(
                "Cable",
                "Acc",
                0,
                0,
                10,
                "Supplier",
                "Description",
                "Sector"
        );

        assertEquals(0, item.getSellingPrice());
    }
}
