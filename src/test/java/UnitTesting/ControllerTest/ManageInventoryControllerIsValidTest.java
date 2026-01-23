package UnitTesting.ControllerTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import Controller.ManageInventoryController;
import DAO.ItemDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManageInventoryControllerIsValidTest {

    @Mock
    private ItemDAO mockItemDao;

    @Test
    @DisplayName("Should return true for a perfectly valid item")
    void testIsValid_Success() {
        when(mockItemDao.validItemName("Smartphone X")).thenReturn(true);

        boolean result = ManageInventoryController.isValid(
                "Smartphone X", "Electronics", 999.99, 500.0, 10,
                "Tech Corp", "Latest model", "Electronics Sector", mockItemDao
        );

        assertTrue(result);
    }

    @ParameterizedTest(name = "Invalid Case: {0}")
    @CsvSource({
            "Name too short, 'ABC', 'Cat', 10.0, 5.0, 1, 'Sup'",
            "Missing Name, '', 'Cat', 10.0, 5.0, 1, 'Sup'",
            "Zero Sell Price, 'ValidName', 'Cat', 0.0, 5.0, 1, 'Sup'",
            "Negative Purchase Price, 'ValidName', 'Cat', 10.0, -1.0, 1, 'Sup'",
            "Zero Quantity, 'ValidName', 'Cat', 10.0, 5.0, 0, 'Sup'",
            "Blank Supplier, 'ValidName', 'Cat', 10.0, 5.0, 1, ''"
    })
    void testIsValid_FailureCases(String description, String name, String cat,
                                  double sell, double buy, int qty, String sup) {
        // Note: category is passed as string, test logic treats 'Cat' as non-null
        boolean result = ManageInventoryController.isValid(
                name, cat, sell, buy, qty, sup, "Desc", "Sector", mockItemDao
        );
        assertFalse(result, "Failed on: " + description);
    }

    @Test
    @DisplayName("Should return false if item name already exists in DAO")
    void testIsValid_DuplicateName() {
        when(mockItemDao.validItemName("Existing Item")).thenReturn(false);

        boolean result = ManageInventoryController.isValid(
                "Existing Item", "Cat", 100.0, 50.0, 10, "Sup", "Desc", "Sector", mockItemDao
        );

        assertFalse(result);
    }
}

