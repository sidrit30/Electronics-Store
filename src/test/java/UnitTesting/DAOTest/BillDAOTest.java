// Orgest Baçova

package UnitTesting.DAOTest;

import DAO.BillDAO;
import Model.Bill;
import Model.Users.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BillDAOTest {

    private BillDAO billDAO;
    private Employee emp1;
    private Employee emp2;

    @BeforeEach
    void setup() {
        billDAO = new BillDAO();

        emp1 = mock(Employee.class);
        emp2 = mock(Employee.class);

        when(emp1.getId()).thenReturn(String.valueOf(1));
        when(emp2.getId()).thenReturn(String.valueOf(2));

        when(emp1.getSectorName()).thenReturn("SectorA");
        when(emp2.getSectorName()).thenReturn("SectorB");

        billDAO.getBills().clear();
    }

    @Test
    void testGetBillsByEmployee() {
        Bill bill1 = mock(Bill.class);
        Bill bill2 = mock(Bill.class);
        Bill bill3 = mock(Bill.class);

        when(bill1.getCashier()).thenReturn(emp1);
        when(bill2.getCashier()).thenReturn(emp1);
        when(bill3.getCashier()).thenReturn(emp2);

        billDAO.getBills().addAll(bill1, bill2, bill3);

        ObservableList<Bill> result = billDAO.getBillsByEmployee(emp1);

        assertEquals(2, result.size());
        assertTrue(result.contains(bill1));
        assertTrue(result.contains(bill2));
        assertFalse(result.contains(bill3));
    }

    @Test
    void testGetBillsBySector() {
        Bill bill = mock(Bill.class);

        when(bill.getCashier()).thenReturn(emp1);

        billDAO.getBills().add(bill);

        ObservableList<Bill> result = billDAO.getBillsBySector("SectorA");

        assertEquals(1, result.size());
    }

    @Test
    void testGetBillsBySectors() {
        Bill bill1 = mock(Bill.class);
        Bill bill2 = mock(Bill.class);

        when(bill1.getCashier()).thenReturn(emp1);
        when(bill2.getCashier()).thenReturn(emp2);

        billDAO.getBills().addAll(bill1, bill2);

        ObservableList<String> sectors =
                FXCollections.observableArrayList("SectorA");

        ObservableList<Bill> result = billDAO.getBillsBySectors(sectors);

        assertEquals(1, result.size());
        assertTrue(result.contains(bill1));
    }

    @Test
    void testGetBillsByDate() {
        Bill bill1 = mock(Bill.class);
        Bill bill2 = mock(Bill.class);

        when(bill1.getBillTime()).thenReturn(LocalDateTime.of(2024, 1, 10, 10, 0));
        when(bill2.getBillTime()).thenReturn(LocalDateTime.of(2024, 3, 10, 10, 0));

        billDAO.getBills().addAll(bill1, bill2);

        ObservableList<Bill> result =
                billDAO.getBillsByDate(
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 2, 1)
                );

        assertEquals(1, result.size());
        assertTrue(result.contains(bill1));
    }
}