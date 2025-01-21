package Controller;

import DAO.BillDAO;
import Model.Bill;
import Model.Exceptions.InsufficientStockException;
import Model.Items.Item;
import View.CreateBillView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class CreateBillController {
    private CreateBillView view;
    private Bill currentBill;
    private ObservableList<Item> itemList;
    private ObservableList<CreateBillView.BillItem> billItems;

    public CreateBillController(BillDAO billDAO) {
        itemList = FXCollections.observableArrayList();
        billItems = FXCollections.observableArrayList();

        view = new CreateBillView(this);
        view.updateItemTable(itemList); // Update the item table with the sample items

        // Create a dummy cashier for the bill
        currentBill = new Bill(new Model.Users.Cashier("PoJamDepressed", "Kaniher", "Buta", "password123", 3000.00));
    }

    public ObservableList<Item> getItemList() {
        return itemList;
    }

    public ObservableList<CreateBillView.BillItem> getBillItems() {
        return billItems;
    }

    public void filterItems(String query) {
        ObservableList<Item> filteredItems = FXCollections.observableArrayList();
        for (Item item : itemList) {
            if (item.getItemName().toLowerCase().contains(query.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        view.updateItemTable(filteredItems);
    }

    public void addItemToBill(Item selectedItem, int quantity) {
        if (selectedItem != null) {
            try {
                currentBill.addItem(selectedItem, quantity);
                CreateBillView.BillItem billItem = new CreateBillView.BillItem(selectedItem, quantity);
                billItems.add(billItem);
                view.clearQuantityField();
                view.refreshTables();
            } catch (InsufficientStockException e) {
                showAlert("Error", e.getMessage());
            }
        } else {
            showAlert("Error", "No item selected or quantity not specified");
        }
    }

    public void saveAndPrintBill() {
        String billText = currentBill.printBill();
        view.appendBillText(billText + "\n\n---------------------------------\n\n");
        currentBill.saveBillToFile();
        showAlert("Success", "Bill saved and printed successfully");

        clearTables();
        startNewBill();
    }

    private void clearTables() {
        billItems.clear();
        view.refreshTables();
    }

    private void startNewBill() {
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public CreateBillView getView() {
        return view;
    }
}
