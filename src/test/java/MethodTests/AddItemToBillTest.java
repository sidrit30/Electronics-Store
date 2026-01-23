package MethodTests;

import static Controller.CreateBillController.addItemToBill;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import Model.Bill;

import Model.Items.Item;
import Model.Users.Cashier;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AddItemToBillTest {

    private Item testItem;
    private Bill testBill;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        Cashier testEmployee = new Cashier("Doe", "John", "john123", "pass123", 50000);
        testEmployee.setSectorName("Electronics");

        testBill = new Bill(testEmployee, "Electronics");

        testItem = new Item("Test Laptop", "Computers", 1500.0, 1000.0, 100,
                "SupplierA", "Test Description", "Electronics");

        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    //Boundary Value Analysis

    @Test
    @DisplayName("BVA-1")
    void testBVA_QuantityZero() {
        addItemToBill(testItem, testBill, "0");
        String output = outputStream.toString();
        assertTrue(output.contains("Quantity must be greater than 0!"));
    }

    @Test
    @DisplayName("BVA-2")
    void testBVA_QuantityOne() {
        addItemToBill(testItem, testBill, "1");
        assertEquals(1, testBill.getItemList().size());
    }

    @Test
    @DisplayName("BVA-3")
    void testBVA_QuantityTwo() {
        addItemToBill(testItem, testBill, "2");
        assertEquals(1, testBill.getItemList().size());
    }

    @Test
    @DisplayName("BVA-4")
    void testBVA_QuantityNormal() {
        // This will throw InsufficientStockException as stock is only 100
        addItemToBill(testItem, testBill, "9999");
        String output = outputStream.toString();
        assertTrue(output.contains("There is insufficient stock!"));
    }

    @Test
    @DisplayName("BVA-5")
    void testBVA_QuantityNegative() {
        addItemToBill(testItem, testBill, "-1");
        String output = outputStream.toString();
        assertTrue(output.contains("Quantity must be greater than 0!"));
    }

    @Test
    @DisplayName("BVA-6")
    void testBVA_QuantityMaxInt() {
        addItemToBill(testItem, testBill, String.valueOf(Integer.MAX_VALUE));
        String output = outputStream.toString();
        assertTrue(output.contains("There is insufficient stock!"));
    }

    @Test
    @DisplayName("BVA-7")
    void testBVA_EmptyString() {
        addItemToBill(testItem, testBill, "");
        String output = outputStream.toString();
        assertTrue(output.contains("Please enter a valid quantity!"));
    }

    @Test
    @DisplayName("BVA-8")
    void testBVA_NonNumeric() {
        addItemToBill(testItem, testBill, "abc");
        String output = outputStream.toString();
        assertTrue(output.contains("Please enter a valid quantity!"));
    }

    @Test
    @DisplayName("BVA-9")
    void testBVA_Decimal() {
        addItemToBill(testItem, testBill, "5.5");
        String output = outputStream.toString();
        assertTrue(output.contains("Please enter a valid quantity!"));
    }


    //Equivalence Class Testing

    @Test
    @DisplayName("EC-1")
    void testEC_ValidQuantitySufficientStock() {
        addItemToBill(testItem, testBill, "50");
        assertEquals(1, testBill.getItemList().size());
    }

    @Test
    @DisplayName("EC-2")
    void testEC_InvalidFormatNonNumeric() {
        addItemToBill(testItem, testBill, "test");
        String output = outputStream.toString();
        assertTrue(output.contains("Please enter a valid quantity!"));
    }

    @Test
    @DisplayName("EC-3")
    void testEC_InvalidFormatDecimal() {
        addItemToBill(testItem, testBill, "10.75");
        String output = outputStream.toString();
        assertTrue(output.contains("Please enter a valid quantity!"));
    }

    @Test
    @DisplayName("EC-4")
    void testEC_InvalidFormatEmpty() {
        addItemToBill(testItem, testBill, "");
        String output = outputStream.toString();
        assertTrue(output.contains("Please enter a valid quantity!"));
    }

    @Test
    @DisplayName("EC-5")
    void testEC_ZeroQuantity() {
        addItemToBill(testItem, testBill, "0");
        String output = outputStream.toString();
        assertTrue(output.contains("Quantity must be greater than 0!"));
    }

    @Test
    @DisplayName("EC-6")
    void testEC_NegativeQuantity() {
        addItemToBill(testItem, testBill, "-10");
        String output = outputStream.toString();
        assertTrue(output.contains("Quantity must be greater than 0!"));
    }

    @Test
    @DisplayName("EC-7")
    void testEC_ExceedingStock() {
        addItemToBill(testItem, testBill, "150");
        String output = outputStream.toString();
        assertTrue(output.contains("There is insufficient stock!"));
    }
}


