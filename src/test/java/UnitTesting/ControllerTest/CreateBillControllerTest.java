package UnitTesting.ControllerTest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import Controller.CreateBillController;
import Model.Bill;
import Model.Items.Item;
import Model.Users.Employee;
import View.CreateBillView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

@ExtendWith(MockitoExtension.class)
class CreateBillControllerTest extends ApplicationTest {

    private CreateBillController controller;
    @Mock private Bill mockBill;
    @Mock private Item mockItem;
    @Mock private CreateBillView.BillItem mockBillItem;
    @Mock private Employee mockEmployee;

    @Override
    public void start(Stage stage) {
        // TestFX calls this to initialize the JavaFX Toolkit
        // We mock the employee sector to prevent NPE during controller init
        when(mockEmployee.getSectorName()).thenReturn("Electronics");
        controller = new CreateBillController(mockEmployee);
    }

    @Test
    void testRemoveItem_ValidItem() throws Exception {
        injectMockBill(mockBill);
        when(mockBillItem.getItem()).thenReturn(mockItem);

        controller.removeItem(mockBillItem);

        verify(mockBill).removeItem(mockItem);
    }

    @Test
    void testRemoveItem_NullItem() throws Exception {
        injectMockBill(mockBill);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        controller.removeItem(null);

        verify(mockBill, never()).removeItem(any());
        assertEquals("No item selected!", outContent.toString().trim());

        System.setOut(System.out);
    }

    private void injectMockBill(Bill bill) throws Exception {
        Field field = CreateBillController.class.getDeclaredField("bill");
        field.setAccessible(true);
        field.set(controller, bill);
    }
}