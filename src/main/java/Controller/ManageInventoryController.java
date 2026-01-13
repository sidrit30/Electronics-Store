package Controller;

import DAO.ItemDAO;
import Model.Items.Item;
import Model.Users.*;
import View.ManageInventoryView;
import javafx.scene.control.*;

import java.util.Optional;

public class ManageInventoryController {
    private ItemDAO itemDAO;
    private ManageInventoryView inventoryView;
    private Employee selectedEmployee;

    public ManageInventoryView getManageInventoryView() {
        return inventoryView;
    }

    public ManageInventoryController(Employee employee) {
        itemDAO = new ItemDAO();
        inventoryView = new ManageInventoryView();
        selectedEmployee = employee;


        if(employee instanceof Admin) {
            inventoryView.getTable().setItems(itemDAO.getItems());
//            inventoryView.getSortSector().getItems().add("All Sectors");
//            inventoryView.getSortSector().getItems().addAll(itemDAO.getSectorNames());
            inventoryView.getSelectSector().getItems().addAll(itemDAO.getSectorNames());
        }

        if(employee instanceof Manager) {
            inventoryView.getTable().setItems(itemDAO.getItemsBySectors(((Manager)employee).getSectors()));
//            inventoryView.getSortSector().getItems().addAll(((Manager)employee).getSectors());
            inventoryView.getSelectSector().getItems().addAll(((Manager)employee).getSectors());
        }

        if(employee instanceof Cashier) {
            inventoryView.getTable().setItems(itemDAO.getItemsBySector(employee.getSectorName()));
//            inventoryView.getSortSector().getItems().add(employee.getSectorName());
            inventoryView.getSelectSector().getItems().add(employee.getSectorName());
        }

        //setSearchListener();

        if(selectedEmployee.hasPermission(Permission.EDIT_ITEM)) {
            inventoryView.getTable().setEditable(true);
            setEditListeners();
            inventoryView.getSelectSector().setOnAction(event -> loadCategories());
            inventoryView.getAddNewItemButton().setOnAction(e -> onItemAdd());
            inventoryView.getDeleteButton().setOnAction(e -> onItemDelete());
            inventoryView.getSaveButton().setOnAction(e -> {
                boolean flag = itemDAO.UpdateAll();
                if (flag) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Items Updated");
                    alert.show();
                }
            });
        } else {
            inventoryView.getMainVBox().setVisible(false);
        }
    }

    private void loadCategories() {
        String sector = inventoryView.getSelectSector().getSelectionModel().getSelectedItem();
        inventoryView.getSelectItemCategory().getItems().addAll(itemDAO.getItemCategories(sector));
    }


    private void onItemAdd() {
        if(isValid()) {
            String itemName = inventoryView.getAddItemName().getText();
            String itemSector = inventoryView.getSelectSector().getSelectionModel().getSelectedItem();
            String itemCategory = inventoryView.getSelectItemCategory().getSelectionModel().getSelectedItem();
            String itemSupplier = inventoryView.getAddSupplierName().getText();
            Double itemPurchasePrice = Double.valueOf(inventoryView.getAddPurchasePrice().getText());
            Double itemSellingPrice = Double.valueOf(inventoryView.getAddSellPrice().getText());
            int itemQuantity = Integer.valueOf(inventoryView.getAddQuantity().getText());
            String itemDescription = inventoryView.getAddItemDescription().getText();
            itemDAO.createItem(new Item(itemName, itemCategory, itemSellingPrice, itemPurchasePrice, itemQuantity, itemSupplier, itemDescription, itemSector));

            inventoryView.getAddItemName().clear();
            inventoryView.getAddSupplierName().clear();
            inventoryView.getAddPurchasePrice().clear();
            inventoryView.getAddSellPrice().clear();
            inventoryView.getAddQuantity().clear();
            inventoryView.getAddItemDescription().clear();
            inventoryView.getSelectSector().getSelectionModel().clearSelection();
            inventoryView.getSelectItemCategory().getSelectionModel().clearSelection();

        }
    }




    private boolean isValid() {
        if (inventoryView.getAddNewItemButton().getText().isEmpty() ||
                inventoryView.getSelectItemCategory().getValue() == null ||
                inventoryView.getAddSupplierName().getText().isEmpty() ||
                inventoryView.getAddPurchasePrice().getText().isEmpty() ||
                inventoryView.getAddSellPrice().getText().isEmpty() ||
                inventoryView.getAddQuantity().getText().isEmpty()) {
            inventoryView.showErrorAlert("Item details are missing!");
            return false;
        }

        if(inventoryView.getAddItemName().getText().length() < 6) {
            inventoryView.showErrorAlert("Item can't be less than 6 characters!");
            return false;
        }

        if(!itemDAO.validItemName(inventoryView.getAddItemName().getText())) {
            inventoryView.showErrorAlert("Item already exists!");
            return false;
        }

        try {
            Double.parseDouble(inventoryView.getAddPurchasePrice().getText());
            Double.parseDouble(inventoryView.getAddSellPrice().getText());
        } catch (NumberFormatException e) {
            inventoryView.showErrorAlert("Prices must be a positive number.");
            return false;
        }
        if(Double.parseDouble(inventoryView.getAddQuantity().getText())<=0) {
            inventoryView.showErrorAlert("Prices must be a positive number.");
            return false;
        }

        try {
            Integer.parseInt(inventoryView.getAddQuantity().getText());
        } catch (NumberFormatException e) {
            inventoryView.showErrorAlert("Quantity must be a positive integer.");
            return false;
        }

        return true;

    }



    private void onItemDelete() {

        Item toDelete = inventoryView.getTable().getSelectionModel().getSelectedItem();
        Alert alert;
        alert = new Alert(Alert.AlertType.WARNING, "Confirm Deletion", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete item " + toDelete.getItemName() +"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (itemDAO.deleteItem(toDelete)) {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Success!", ButtonType.OK);
                alert.setTitle("Deleted Item");
                alert.setHeaderText("Item Deleted Successfully!");
                alert.show();
            } else {
                alert = new Alert(Alert.AlertType.ERROR, "Error!", ButtonType.OK);
                alert.setTitle("Error");
                alert.setHeaderText("Error while deleting Item!");
                alert.show();
            }
        }
        else {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancelled!", ButtonType.OK);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Item Deletion Cancelled!");
        }
    }

    private void setEditListeners() {
        inventoryView.getPurchasePriceCol().setOnEditCommit(event -> {
            itemDAO.getItemByID(event.getRowValue().getItemID()).setPurchasePrice(event.getNewValue());
            inventoryView.getTable().refresh();
        });

        inventoryView.getSellPriceCol().setOnEditCommit(event -> {
            itemDAO.getItemByID(event.getRowValue().getItemID()).setSellingPrice(event.getNewValue());
            inventoryView.getTable().refresh();

        });

        inventoryView.getQuantityCol().setOnEditCommit(event -> {
            if(event.getNewValue() < event.getOldValue()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Can't decrease item quantity!");
                alert.show();
                return;
            }
            itemDAO.getItemByID(event.getRowValue().getItemID()).setQuantity(event.getNewValue());
            inventoryView.getTable().refresh();

        });

        inventoryView.getSupplierNameCol().setOnEditCommit(event -> {
            itemDAO.getItemByID(event.getRowValue().getItemID()).setSupplier(event.getNewValue());
            inventoryView.getTable().refresh();
        });

        inventoryView.getDescriptionCol().setOnEditCommit(event -> {
            itemDAO.getItemByID(event.getRowValue().getItemID()).setItemDescription(event.getNewValue());
            inventoryView.getTable().refresh();
        });
    }

//    private void setSearchListener() {
//        this.inventoryView.getSearchButton().setOnAction(e -> searchEmployee());
//    }
//
//    private void searchEmployee() {
//
//    }


}
