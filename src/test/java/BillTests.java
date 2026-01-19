package Model;

import Model.Exceptions.InsufficientStockException;
import Model.Items.Item;
import Model.Users.Cashier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BillTests {

    private Cashier makeCashier() {
        return new Cashier("Doe", "John", "john.doe", "pass123", 500.0);
    }

    private Item makeItemWithStock(int stock) {
        return new Item(
                "Mouse",
                "Accessories",
                20.0,     // selling price
                10.0,     // purchase price
                stock,    // quantity in stock
                "SupplierX",
                "Wireless mouse",
                "Electronics"
        );
    }

    @Test
    void addItem_quantityLessThanStock_shouldSucceedAndDecreaseStock() throws Exception {
        Bill bill = new Bill(makeCashier(), "Electronics");
        Item item = makeItemWithStock(10);

        bill.addItem(item, 9);

        assertEquals(1, bill.getItemList().size());
        assertTrue(bill.getItemList().contains(item));
        assertEquals(1, item.getQuantity(), "Stock should decrease by quantity added");
    }

    @Test
    void addItem_quantityEqualToStock_boundary_shouldSucceedAndStockBecomesZero() throws Exception {
        Bill bill = new Bill(makeCashier(), "Electronics");
        Item item = makeItemWithStock(10);

        bill.addItem(item, 10);

        assertEquals(1, bill.getItemList().size());
        assertEquals(0, item.getQuantity(), "Stock should become 0 at boundary");
    }

    @Test
    void addItem_quantityGreaterThanStock_boundary_shouldThrowAndNotChangeBillOrStock() {
        Bill bill = new Bill(makeCashier(), "Electronics");
        Item item = makeItemWithStock(10);

        assertThrows(InsufficientStockException.class, () -> bill.addItem(item, 11));

        assertEquals(0, bill.getItemList().size(), "Item should NOT be added if exception occurs");
        assertEquals(10, item.getQuantity(), "Stock should remain unchanged if exception occurs");
    }

    @Test
    void addItem_quantityZero_observeCurrentBehavior() throws Exception {
        Bill bill = new Bill(makeCashier(), "Electronics");
        Item item = makeItemWithStock(10);

        bill.addItem(item, 0);

        // Current code allows it. This is useful to mention as an observation in your doc.
        assertEquals(1, bill.getItemList().size());
        assertEquals(10, item.getQuantity(), "Stock should not change if quantity is 0");
    }

    @Test
    void removeItem_itemPresent_shouldRemoveAndRestoreStock() throws Exception {
        Bill bill = new Bill(makeCashier(), "Electronics");
        Item item = makeItemWithStock(10);

        bill.addItem(item, 4); // stock becomes 6
        assertEquals(6, item.getQuantity());

        bill.removeItem(item);

        assertEquals(0, bill.getItemList().size(), "Item should be removed from bill");
        assertEquals(10, item.getQuantity(), "Stock should be restored after removing item");
    }

    @Test
    void removeItem_itemNotPresent_shouldThrowIndexOutOfBounds_dueToCurrentImplementation() {
        Bill bill = new Bill(makeCashier(), "Electronics");
        Item itemNotInBill = makeItemWithStock(10);

        // Current implementation uses indexOf() then quantities.get(index) without checking index == -1
        assertThrows(IndexOutOfBoundsException.class, () -> bill.removeItem(itemNotInBill));
    }
}

