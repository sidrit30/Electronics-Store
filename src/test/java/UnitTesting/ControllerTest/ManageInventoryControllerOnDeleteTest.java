package UnitTesting.ControllerTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import Controller.ManageInventoryController;
import DAO.ItemDAO;
import Model.Items.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManageInventoryControllerOnDeleteTest {

    @Mock
    private ItemDAO mockItemDao;
    @Mock private Item mockItem;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restore() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Path 1: User confirms and DAO deletes successfully")
    void testOnItemDelete_Success() {
        when(mockItemDao.deleteItem(mockItem)).thenReturn(true);

        ManageInventoryController.onItemDelete(mockItem, true, true, mockItemDao);

        assertEquals("Success", outContent.toString().trim());
    }

    @Test
    @DisplayName("Path 2: User confirms but DAO fails (e.g. database error)")
    void testOnItemDelete_Fail() {
        when(mockItemDao.deleteItem(mockItem)).thenReturn(false);

        ManageInventoryController.onItemDelete(mockItem, true, true, mockItemDao);

        assertEquals("Fail", outContent.toString().trim());
    }

    @Test
    @DisplayName("Path 3: User cancels the deletion alert")
    void testOnItemDelete_Cancelled() {
        ManageInventoryController.onItemDelete(mockItem, true, false, mockItemDao);

        assertEquals("Cancelled", outContent.toString().trim());
        verify(mockItemDao, never()).deleteItem(any());
    }
}
