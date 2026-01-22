package MethodTests;

import Model.Bill;
import Model.Items.Item;
import Model.Users.Admin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BillPrintBillTest {

    @Test
    void zeroItems() {
        Admin cashier = new Admin("Test", "User", "test", "pw", 1000);
        Bill bill = new Bill(cashier, "Sector");

        String receipt = bill.printBill();

        assertTrue(receipt.contains("Bill Details"));
        assertTrue(receipt.contains("Total"));
    }
    @Test
    void oneItem() throws Exception {
        Admin cashier = new Admin("Test", "User", "test", "pw", 1000);
        Bill bill = new Bill(cashier, "Sector");

        Item item = new Item("Mouse", "Acc", 10, 5, 10, "Supp", "Desc", "Sector");
        bill.addItem(item, 1);

        String receipt = bill.printBill();

        assertTrue(receipt.contains("Mouse"));
        assertTrue(receipt.contains("Quantity"));
    }
}
