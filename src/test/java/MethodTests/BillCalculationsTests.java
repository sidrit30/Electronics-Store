package MethodTests;
import Model.Bill;
import Model.Items.Item;
import Model.Users.Employee;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillCalculationsTests {

    //Mock helpers with Mockito

    private Item mockItem(double purchase, double selling) {
        Item item = Mockito.mock(Item.class);
        Mockito.when(item.getPurchasePrice()).thenReturn(purchase);
        Mockito.when(item.getSellingPrice()).thenReturn(selling);
        Mockito.when(item.getQuantity()).thenReturn(100);
        return item;
    }

    private Employee mockEmployee() {
        return Mockito.mock(Employee.class);
    }

    // ============================
    // calculateCost() TESTS
    // ============================

    @Test
    public void testCalculateCost_EmptyList() {
        Bill bill = new Bill(mockEmployee(), "Sales");
        assertEquals(0, bill.getCost());
    }

    @Test
    public void testCalculateCost_SingleItem() throws Exception {
        Bill bill = new Bill(mockEmployee(), "Sales");
        Item item = mockItem(10, 15);
        bill.addItem(item, 2); // 10 * 2 = 20
        assertEquals(20, bill.getCost());
    }

    @Test
    public void testCalculateCost_MultipleItems() throws Exception {
        Bill bill = new Bill(mockEmployee(), "Sales");
        bill.addItem(mockItem(10, 15), 2); // 20
        bill.addItem(mockItem(5, 8), 3);   // 15
        assertEquals(35, bill.getCost());
    }

    // ============================
    // calculateRevenue() TESTS
    // ============================

    @Test
    public void testCalculateRevenue_EmptyList() {
        Bill bill = new Bill(mockEmployee(), "Sales");
        assertEquals(0, bill.getRevenue());
    }

    @Test
    public void testCalculateRevenue_SingleItem() throws Exception {
        Bill bill = new Bill(mockEmployee(), "Sales");
        bill.addItem(mockItem(10, 15), 2); // 15 * 2 = 30
        assertEquals(30, bill.getRevenue());
    }

    @Test
    public void testCalculateRevenue_MultipleItems() throws Exception {
        Bill bill = new Bill(mockEmployee(), "Sales");
        bill.addItem(mockItem(10, 15), 2); // 30
        bill.addItem(mockItem(5, 8), 3);   // 24
        assertEquals(54, bill.getRevenue());
    }

    // ============================
    // calculateProfit() TESTS
    // ============================

    @Test
    public void testCalculateProfit_Positive() throws Exception {
        Bill bill = new Bill(mockEmployee(), "Sales");
        bill.addItem(mockItem(10, 20), 2); // cost=20, revenue=40
        bill.getCost();
        bill.getRevenue();
        assertEquals(20, bill.calculateProfit());
    }

    @Test
    public void testCalculateProfit_Zero() throws Exception {
        Bill bill = new Bill(mockEmployee(), "Sales");
        bill.addItem(mockItem(10, 10), 2); // cost=20, revenue=20
        bill.getCost();
        bill.getRevenue();
        assertEquals(0, bill.calculateProfit());
    }

    @Test
    public void testCalculateProfit_Negative() throws Exception {
        Bill bill = new Bill(mockEmployee(), "Sales");
        bill.addItem(mockItem(20, 10), 2); // cost=40, revenue=20
        bill.getCost();
        bill.getRevenue();
        assertEquals(-20, bill.calculateProfit());
    }
}
