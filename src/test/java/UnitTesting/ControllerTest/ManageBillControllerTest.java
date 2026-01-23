package UnitTesting.ControllerTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import Controller.ManageBillController;
import DAO.BillDAO;
import DAO.ItemDAO;
import Model.Bill;
import Model.Users.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ManageBillControllerTest {

    @Mock private BillDAO mockBillDAO;
    @Mock private ItemDAO mockItemDAO;
    private ManageBillController controller;

    @BeforeEach
    void setUp() {

        lenient().when(mockBillDAO.getBills()).thenReturn(FXCollections.observableArrayList());

        // Mock the employee for the constructor
        Employee dummy = mock(Cashier.class);

        // Assuming your constructor looks like: ManageBillController(Employee e, BillDAO b, ItemDAO i)
        controller = new ManageBillController(dummy, mockBillDAO, mockItemDAO);
    }

    @Test
    void testLoadData_Admin_ReturnsAllBills() {
        // Arrange
        Admin admin = mock(Admin.class);
        ObservableList<Bill> allBills = FXCollections.observableArrayList(mock(Bill.class), mock(Bill.class));

        when(mockBillDAO.getBills()).thenReturn(allBills);

        List<Bill> result = controller.loadData(admin);

        assertEquals(2, result.size());
        verify(mockBillDAO, atLeastOnce()).getBills();
    }

    @Test
    void testLoadData_Manager_ReturnsSectorBills() {
        // Arrange
        Manager manager = mock(Manager.class);
        ObservableList<String> sectors = FXCollections.observableArrayList("Electronics");
        ObservableList<Bill> sectorBills = FXCollections.observableArrayList(mock(Bill.class));

        when(manager.getSectors()).thenReturn(sectors);
        when(mockBillDAO.getBillsBySectors(sectors)).thenReturn(sectorBills);

        List<Bill> result = controller.loadData(manager);

        assertEquals(1, result.size());
        verify(mockBillDAO).getBillsBySectors(sectors);
    }

    @Test
    void testLoadData_Cashier_ReturnsOwnBills() {
        // Arrange
        Cashier cashier = mock(Cashier.class);
        ObservableList<Bill> cashierBills = FXCollections.observableArrayList(
                mock(Bill.class), mock(Bill.class), mock(Bill.class)
        );

        when(mockBillDAO.getBillsByEmployee(cashier)).thenReturn(cashierBills);

        // Act
        List<Bill> result = controller.loadData(cashier);

        // Assert
        assertEquals(3, result.size());
        verify(mockBillDAO).getBillsByEmployee(cashier);
    }
}