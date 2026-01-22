package UnitTesting.DAOTest;

import DAO.ItemDAO;
import Model.Items.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.io.File;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class ItemDAOTest {

    private static final String ITEMS_PATH = "src/main/resources/data/items.dat";

    private ItemDAO dao;

    private Item item1; // Computers
    private Item item2; // Phones
    private Item item3; // Computers (different category)

    @BeforeEach
    void setUp() throws Exception {
        dao = new ItemDAO();

        // Ensure data folder exists (ItemDAO uses src/main/resources/data/items.dat)
        File dir = new File("src/main/resources/data");
        if (!dir.exists())
            assertTrue(dir.mkdirs());

        // Prepare deterministic in-memory data (avoid relying on loadItems() behavior)
        item1 = new Item("LaptopA", "Laptops", 999.99, 700.00, 10, "SupplierX", "Gaming laptop", "Computers");
        item2 = new Item("PhoneB", "Smartphones", 499.99, 300.00, 20, "SupplierY", "Android phone", "Phones");
        item3 = new Item("PCPartC", "Components", 199.99, 120.00, 15, "SupplierZ", "GPU part", "Computers");

        setItemsList(FXCollections.observableArrayList(item1, item2, item3));
    }

    @AfterEach
    void tearDown() throws Exception {
        // Clear list to avoid test interference
        setItemsList(FXCollections.observableArrayList());

        // Optional: cleanup file produced by UpdateAll/deleteItem/createItem
        File f = new File(ITEMS_PATH);
        if (f.exists()) {
            // keep or delete; deleting keeps repo clean locally
            // f.delete();
        }
    }

    // --- Reflection helper: control the private static ObservableList<Item> items
    // ---
    private void setItemsList(ObservableList<Item> newList) throws Exception {
        Field itemsField = ItemDAO.class.getDeclaredField("items");
        itemsField.setAccessible(true);

        @SuppressWarnings("unchecked")
        ObservableList<Item> staticItems = (ObservableList<Item>) itemsField.get(null);

        staticItems.clear();
        staticItems.addAll(newList);
    }

    // ======================
    // getItems()
    // ======================

    @Test
    void getItems_returnsPreloadedItems() {
        ObservableList<Item> items = dao.getItems();
        assertEquals(3, items.size());
    }

    // ======================
    // getItemsBySector(String)
    // ======================

    @Test
    void getItemsBySector_filtersCorrectly() {
        ObservableList<Item> computers = dao.getItemsBySector("Computers");
        assertEquals(2, computers.size());
        assertTrue(computers.stream().allMatch(i -> i.getSectorName().equals("Computers")));
    }

    @Test
    void getItemsBySector_unknownSector_returnsEmpty() {
        ObservableList<Item> result = dao.getItemsBySector("DoesNotExist");
        assertTrue(result.isEmpty());
    }

    // ======================
    // validItemName(String)
    // ======================

    @Test
    void validItemName_unique_returnsTrue() {
        assertTrue(dao.validItemName("BrandNewName"));
    }

    @Test
    void validItemName_duplicate_returnsFalse() {
        assertFalse(dao.validItemName("LaptopA"));
    }

    // ======================
    // getItemsBySectors(ObservableList<String>)
    // ======================

    @Test
    void getItemsBySectors_emptyList_returnsEmpty() {
        ObservableList<String> sectors = FXCollections.observableArrayList();
        ObservableList<Item> result = dao.getItemsBySectors(sectors);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getItemsBySectors_multipleSectors_returnsUnion() {
        ObservableList<String> sectors = FXCollections.observableArrayList("Computers", "Phones");
        ObservableList<Item> result = dao.getItemsBySectors(sectors);
        assertEquals(3, result.size());
    }

    // ======================
    // getSectorNames()
    // ======================

    @Test
    void getSectorNames_returnsUniqueSectors() {
        ObservableList<String> names = dao.getSectorNames();
        assertEquals(2, names.size());
        assertTrue(names.contains("Computers"));
        assertTrue(names.contains("Phones"));
    }

    // ======================
    // getItemCategories(String sectorName)
    // ======================

    @Test
    void getItemCategories_returnsUniqueCategoriesForSector() {
        ObservableList<String> categories = dao.getItemCategories("Computers");
        assertEquals(2, categories.size());
        assertTrue(categories.contains("Laptops"));
        assertTrue(categories.contains("Components"));
    }

    @Test
    void getItemCategories_unknownSector_returnsEmpty() {
        ObservableList<String> categories = dao.getItemCategories("Nope");
        assertTrue(categories.isEmpty());
    }

    // ======================
    // getItemByID(String)
    // ======================

    @Test
    void getItemByID_existing_returnsItem() {
        String id = item2.getItemID();
        Item found = dao.getItemByID(id);
        assertNotNull(found);
        assertEquals("PhoneB", found.getItemName());
    }

    @Test
    void getItemByID_nonExisting_returnsNull() {
        assertNull(dao.getItemByID("NON_EXISTING_ID"));
    }

    // ======================
    // UpdateAll() + createItem() + deleteItem()
    // (file-writing behavior)
    // ======================

    @Test
    void UpdateAll_writesFile_returnsTrue() {
        boolean ok = dao.UpdateAll();
        assertTrue(ok);

        File f = new File(ITEMS_PATH);
        assertTrue(f.exists());
        assertTrue(f.length() > 0);
    }

    @Test
    void createItem_addsItem_andUpdates_returnsTrue() {
        Item newItem = new Item("MouseX", "Peripherals", 20.0, 9.0, 30, "Sup", "Desc", "Accessories");

        boolean ok = dao.createItem(newItem);
        assertTrue(ok);

        assertTrue(dao.getItems().contains(newItem));
    }

    @Test
    void deleteItem_removesItem_returnsTrue() {
        boolean ok = dao.deleteItem(item1);
        assertTrue(ok);

        assertFalse(dao.getItems().contains(item1));
    }
}
