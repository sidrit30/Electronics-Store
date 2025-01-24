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
    private final CreateBillView view;
    private BillDAO billDAO;
    private Employee employee;
    private ItemDAO itemDAO;
    private Bill bill;


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

       setSearchListeners();
       //get all the items from the sector of the current employee
//        System.out.println(employee.getSectorName());
       ObservableList<Item> items = FXCollections.observableArrayList(itemDAO.getItemsBySector(employee.getSectorName()));
       if(items != null)
           view.getItemTable().setItems(items);
       view.getAddItemButton().setOnAction(event -> addItemToBill());
       view.getSavePrintButton().setOnAction(event -> saveBillToFile());
       view.getRemoveItemButton().setOnAction(e -> removeItem());

    }

    private void setSearchListeners() {
        view.getSearchButton().setOnAction(e -> filterItems());
        view.getSearchBar().setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                filterItems();
            }
        });
    }

    public void filterItems() {
        String query = view.getSearchBar().getText();
        ObservableList<Item> filteredItems = FXCollections.observableArrayList();
        for (Item item : view.getItems()) {
            if (item.getItemName().toLowerCase().contains(query.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        view.getItemTable().getItems().clear();
        view.getItemTable().getItems().addAll(filteredItems);
    }

    public void addItemToBill() {
        Item selectedItem = view.getItemTable().getSelectionModel().getSelectedItem();
        try {
            if (selectedItem == null) {
                showAlert("Error", "No item selected!");
            }
            System.out.println(selectedItem.getItemCategory());
            int quantity = Integer.parseInt(view.getQuantityField().getText());
            if(quantity <= 0) {
                showAlert("Error", "Quantity must be greater than 0!");
            }

            bill.addItem(selectedItem, quantity);
            view.getItemTable().refresh();

            view.getBillTable().getItems().add(new CreateBillView.BillItem(selectedItem, quantity));
            view.getQuantityField().clear();
            view.getBillsTextArea().setText(bill.printBill());
            } catch (InsufficientStockException e) {
                showAlert("Error", e.getMessage());
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter a valid quantity!");
            }

        }


    public void saveBillToFile() {
        if(view.getBillTable().getItems().isEmpty()) {
            showAlert("Error", "No item selected!");
            return;
        }
        showAlert("Success", "Bill saved and printed successfully");
        billDAO.createBill(bill);
        itemDAO.UpdateAll();
        bill.saveBillToFile();
        bill = new Bill(employee, employee.getSectorName());
        view.getBillTable().getItems().clear();
    }

    public void removeItem() {
        CreateBillView.BillItem billItem = view.getBillTable().getSelectionModel().getSelectedItem();
        if(billItem != null) {
            bill.removeItem(billItem.getItem());
            view.getBillTable().getItems().remove(billItem);
            view.getBillsTextArea().setText(bill.printBill());
            view.getItemTable().refresh();

        }
        else
            showAlert("Error", "No item selected!");
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }


}
