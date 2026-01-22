package UnitTesting.ModelTest;

import Model.Bill;
import Model.Items.Item;
import Model.Users.Cashier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BillItemIntegrationTests {

    private Cashier makeCashier() {
        return new Cashier("Doe", "John", "john.doe", "pass123", 500.0);
    }

    private Item makeItem(String name, double sale, double purchase, int stock) {
        return new Item(
                name,
                "Accessories",
                sale,
                purchase,
                stock,
                "SupplierX",
                "desc",
                "Electronics"
        );
    }

    @Test
    void billAndItems_shouldWorkTogether_addRemove_andRestoreStock() throws Exception {
        Bill bill = new Bill(makeCashier(), "Electronics");
        Item item = makeItem("Mouse", 20.0, 12.0, 10);

        bill.addItem(item, 4); // stock becomes 6
        assertEquals(6, item.getQuantity());
        assertEquals(1, bill.getItemList().size());

        bill.removeItem(item); // stock restored
        assertEquals(10, item.getQuantity());
        assertEquals(0, bill.getItemList().size());
    }

    @Test
    void billCalculations_shouldBeCorrect_forMultipleItems() throws Exception {
        Bill bill = new Bill(makeCashier(), "Electronics");

        Item mouse = makeItem("Mouse", 20.0, 12.0, 50);
        Item keyboard = makeItem("Keyboard", 40.0, 25.0, 50);

        bill.addItem(mouse, 2);     // revenue 40, cost 24
        bill.addItem(keyboard, 3);  // revenue 120, cost 75

        double cost = bill.getCost();       // 24 + 75 = 99
        double revenue = bill.getRevenue(); // 40 + 120 = 160

        assertEquals(99.0, cost, 0.0001);
        assertEquals(160.0, revenue, 0.0001);

        // calculateProfit() uses stored revenue/cost fields, so call getCost/getRevenue first (done above)
        assertEquals(61.0, bill.calculateProfit(), 0.0001);
    }

    @Test
    void printBill_shouldContainKeyInformation() throws Exception {
        Bill bill = new Bill(makeCashier(), "Electronics");
        Item mouse = makeItem("Mouse", 20.0, 12.0, 10);

        bill.addItem(mouse, 2);

        String printed = bill.printBill();

        assertTrue(printed.contains("Bill Details"));
        assertTrue(printed.contains("Cashier:"));
        assertTrue(printed.contains("Item: Mouse"));
        assertTrue(printed.contains("Total:"));
    }
}
