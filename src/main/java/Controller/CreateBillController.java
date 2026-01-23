package Controller;

import DAO.BillDAO;
import DAO.ItemDAO;
import Model.Bill;
import Model.Exceptions.InsufficientStockException;
import Model.Items.Item;
import Model.Users.Employee;
import View.CreateBillView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;

public class CreateBillController {

    private static final String TITLE_ERROR = "Error";
    private static final String TITLE_SUCCESS = "Success";

    private static final String MSG_NO_ITEM_SELECTED = "No item selected!";
    private static final String MSG_INVALID_QUANTITY = "Please enter a valid quantity!";
    private static final String MSG_QUANTITY_GT_ZERO = "Quantity must be greater than 0!";
    private static final String MSG_BILL_SAVED = "Bill saved and printed successfully";

    private CreateBillView view;
    private BillDAO billDAO;
    private Employee employee;
    private ItemDAO itemDAO;
    private Bill bill;
    private ObservableList<Item> originalItems;

    public CreateBillView getCreateBillView() {
        return view;
    }

    public Bill getBill() {
        return bill;
    }

    public ItemDAO getItemDAO() {
        return itemDAO;
    }

    public CreateBillController(Employee employee) {
        this.view = new CreateBillView();
        this.billDAO = new BillDAO();
        this.bill = new Bill(employee, employee.getSectorName());
        this.itemDAO = new ItemDAO();
        this.employee = employee;

        billDAO.getBills();
        setSearchListeners();

        ObservableList<Item> items =
                FXCollections.observableArrayList(
                        itemDAO.getItemsBySector(employee.getSectorName())
                );

        originalItems = FXCollections.observableArrayList(
                itemDAO.getItemsBySector(employee.getSectorName())
        );

        view.getItemTable().setItems(originalItems);

        if (items != null) {
            view.getItemTable().setItems(items);
        }

        view.getAddItemButton().setOnAction(event -> addItemToBill());
        view.getSavePrintButton().setOnAction(event -> saveBillToFile());
        view.getRemoveItemButton().setOnAction(event -> removeItem());
    }

    private void setSearchListeners() {
        view.getSearchButton().setOnAction(e -> filterItems());
        view.getSearchBar().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                filterItems();
            }
        });
    }

    public void filterItems() {
        String query = view.getSearchBar().getText().toLowerCase().trim();

        ObservableList<Item> filtered = FXCollections.observableArrayList();

        for (Item item : originalItems) {
            if (item.getItemName().toLowerCase().contains(query)) {
                filtered.add(item);
            }
        }

        view.getItemTable().setItems(filtered);
    }
    public void addItemToBill() {
        Item selectedItem = view.getItemTable().getSelectionModel().getSelectedItem();

        try {
            if (selectedItem == null) {
                showAlert(TITLE_ERROR, MSG_NO_ITEM_SELECTED);
                return;
            }

            int quantity = Integer.parseInt(view.getQuantityField().getText());

            if (quantity <= 0) {
                showAlert(TITLE_ERROR, MSG_QUANTITY_GT_ZERO);
                return;
            }

            bill.addItem(selectedItem, quantity);
            view.getItemTable().refresh();

            view.getBillTable().getItems()
                    .add(new CreateBillView.BillItem(selectedItem, quantity));

            view.getQuantityField().clear();
            view.getBillsTextArea().setText(bill.printBill());

        } catch (InsufficientStockException e) {
            showAlert(TITLE_ERROR, e.getMessage());
        } catch (NumberFormatException e) {
            showAlert(TITLE_ERROR, MSG_INVALID_QUANTITY);
        }
    }

    //rewritten for testing
    //TESTED
    public static void addItemToBill(Item item, Bill bill, String quantityString) {
        try {
            int quantity = Integer.parseInt(quantityString);
            if (quantity <= 0) {
                System.out.println(MSG_QUANTITY_GT_ZERO);
                return;
            }

            bill.addItem(item, quantity);

        } catch (InsufficientStockException e) {
            System.out.println("There is insufficient stock!");
        } catch (NumberFormatException e) {
            System.out.println(MSG_INVALID_QUANTITY);
        }
    }

    public void saveBillToFile() {
        if (view.getBillTable().getItems().isEmpty()) {
            showAlert(TITLE_ERROR, MSG_NO_ITEM_SELECTED);
            return;
        }

        showAlert(TITLE_SUCCESS, MSG_BILL_SAVED);

        bill.saveBillToFile();
        billDAO.createBill(bill);
        itemDAO.UpdateAll();

        bill = new Bill(employee, employee.getSectorName());
        view.getBillTable().getItems().clear();
    }

    public void removeItem() {
        CreateBillView.BillItem billItem =
                view.getBillTable().getSelectionModel().getSelectedItem();

        if (billItem != null) {
            bill.removeItem(billItem.getItem());
            view.getBillTable().getItems().remove(billItem);
            view.getBillsTextArea().setText(bill.printBill());
            view.getItemTable().refresh();
        } else {
            showAlert(TITLE_ERROR, MSG_NO_ITEM_SELECTED);
        }
    }

    //rewritten for testing
    //TESTED
    public void removeItem(CreateBillView.BillItem billItem) {
        if (billItem != null) {
            bill.removeItem(billItem.getItem());
        } else {
            System.out.println(MSG_NO_ITEM_SELECTED);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}

