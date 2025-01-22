package View;

import Controller.CreateBillController;
import Model.Items.Item;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class CreateBillView extends BorderPane {
    private Button searchButton = new Button("Search: ");
    //private BorderPane root;
    private TableView<Item> itemTable = new TableView<>();
    TableColumn<Item, String> itemIdColumn;
    TableColumn<Item, String> nameColumn;
    TableColumn<Item, String> categoryColumn;
    TableColumn<Item, Double> priceColumn;
    TableColumn<Item, Integer> quantityColumn;

    private TableView<BillItem> billTable = new TableView<>();
    TableColumn<BillItem, String> billItemIdColumn;
    TableColumn<BillItem, String> billNameColumn;
    TableColumn<BillItem, String> billCategoryColumn;
    TableColumn<BillItem, Double> billPriceColumn;
    TableColumn<BillItem, Integer> billQuantityColumn;

    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Integer> quantity = new ArrayList<>();

    TextField searchBar = new TextField();
    private TextField quantityField;
    private TextArea billsTextArea;
    private Button addItemButton = new Button("Add Item");
    private Button savePrintButton = new Button("Save and Print Bill");
    private Button removeItemButton = new Button("Remove from Bill");

    public CreateBillView() {
        // Left Section
        VBox leftSection = new VBox();
        leftSection.setPadding(new Insets(10));
        leftSection.setSpacing(8);
        //leftSection.setStyle("-fx-background-color: orange;");

        // Search Bar
        searchBar.setPromptText("Search...");
        HBox searchBox = new HBox(10, searchBar, searchButton);

        // Table for items
        itemTable.setPrefWidth(550);
        itemIdColumn = new TableColumn<>("ItemID");
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        itemTable.getColumns().addAll(itemIdColumn, nameColumn, categoryColumn, priceColumn, quantityColumn);

        // Add Item Button and Quantity TextField
        HBox addItemBox = new HBox();
        addItemBox.setSpacing(8);

//        addItemButton.setOnAction(event -> {
//            Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
//            int quantity = Integer.parseInt(quantityField.getText());
//            controller.addItemToBill(selectedItem, quantity);
//        });
        quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        addItemBox.getChildren().addAll(addItemButton, quantityField, removeItemButton);

        leftSection.getChildren().addAll(searchBox, itemTable, addItemBox);

        // Right Section
        VBox rightSection = new VBox();
        rightSection.setPadding(new Insets(10));
        rightSection.setSpacing(8);
        //rightSection.setStyle("-fx-background-color: orange;");

        // Bills TextArea
        billsTextArea = new TextArea();
        billsTextArea.setPromptText("Bills:");
        billsTextArea.setWrapText(true);
        billsTextArea.setPrefHeight(600);

        // Bill Table

        billItemIdColumn = new TableColumn<>("ItemID");
        billItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        billNameColumn = new TableColumn<>("Name");
        billNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        billCategoryColumn = new TableColumn<>("Category");
        billCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        billPriceColumn = new TableColumn<>("Price");
        billPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        billQuantityColumn = new TableColumn<>("Quantity");
        billQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        billTable.getColumns().addAll(billItemIdColumn, billNameColumn, billCategoryColumn, billPriceColumn, billQuantityColumn);
        rightSection.getChildren().addAll(billsTextArea, billTable, savePrintButton);

        // Add Sections to Root
        this.setLeft(leftSection);
        this.setCenter(rightSection);
    }


    public void refreshTables() {
        itemTable.refresh();
        billTable.refresh();
    }

    public void appendBillText(String text) {
        billsTextArea.appendText(text);
    }

    public TableView<Item> getItemTable() {
        return itemTable;
    }

    public TableColumn<Item, String> getItemIdColumn() {
        return itemIdColumn;
    }

    public TableColumn<Item, String> getNameColumn() {
        return nameColumn;
    }

    public TableColumn<Item, String> getCategoryColumn() {
        return categoryColumn;
    }

    public TableColumn<Item, Double> getPriceColumn() {
        return priceColumn;
    }

    public TableColumn<Item, Integer> getQuantityColumn() {
        return quantityColumn;
    }

    public TableView<BillItem> getBillTable() {
        return billTable;
    }

    public TableColumn<BillItem, String> getBillItemIdColumn() {
        return billItemIdColumn;
    }

    public TableColumn<BillItem, String> getBillNameColumn() {
        return billNameColumn;
    }

    public TableColumn<BillItem, String> getBillCategoryColumn() {
        return billCategoryColumn;
    }

    public TableColumn<BillItem, Double> getBillPriceColumn() {
        return billPriceColumn;
    }

    public TableColumn<BillItem, Integer> getBillQuantityColumn() {
        return billQuantityColumn;
    }

    public TextField getSearchBar() {
        return searchBar;
    }

    public TextField getQuantityField() {
        return quantityField;
    }

    public TextArea getBillsTextArea() {
        return billsTextArea;
    }

    public Button getAddItemButton() {
        return addItemButton;
    }

    public Button getSavePrintButton() {
        return savePrintButton;
    }

    public Button getRemoveItemButton() {
        return removeItemButton;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Integer> getQuantity() {
        return quantity;
    }

    // Inner class to represent items added to the bill with their quantities
    public static class BillItem {
        private Item item;
        private int quantity;

        public BillItem(Item item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        public String getItemID() {
            return item.getItemID();
        }

        public String getItemName() {
            return item.getItemName();
        }

        public String getItemCategory() {
            return item.getItemCategory();
        }

        public double getSellingPrice() {
            return item.getSellingPrice();
        }

        public int getQuantity() {
            return quantity;
        }

        public Item getItem() {
            return item;
        }
    }


}

