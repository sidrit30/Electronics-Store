package View;

import Controller.CreateBillController;
import Model.Items.Item;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class CreateBillView {
    private BorderPane root;
    private TableView<Item> itemTable;
    private TableView<BillItem> billTable;
    private TextField quantityField;
    private TextArea billsTextArea;
    private CreateBillController controller;

    public CreateBillView(CreateBillController controller) {
        this.controller = controller;
        setupUI();
    }

    private void setupUI() {
        root = new BorderPane();

        // Left Section
        VBox leftSection = new VBox();
        leftSection.setPadding(new Insets(10));
        leftSection.setSpacing(8);
        //leftSection.setStyle("-fx-background-color: orange;");

        // Search Bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search...");
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> controller.filterItems(newValue));

        // Table for items
        itemTable = new TableView<>();
        itemTable.setPrefWidth(550);
        TableColumn<Item, String> itemIdColumn = new TableColumn<>("ItemID");
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        itemTable.getColumns().addAll(itemIdColumn, nameColumn, categoryColumn, priceColumn, quantityColumn);
        itemTable.setItems(controller.getItemList());

        // Add Item Button and Quantity TextField
        HBox addItemBox = new HBox();
        addItemBox.setSpacing(8);
        Button addItemButton = new Button("Add Item");
        addItemButton.setOnAction(event -> {
            Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
            int quantity = Integer.parseInt(quantityField.getText());
            controller.addItemToBill(selectedItem, quantity);
        });
        quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        addItemBox.getChildren().addAll(addItemButton, quantityField);

        leftSection.getChildren().addAll(searchBar, itemTable, addItemBox);

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
        billTable = new TableView<>();
        TableColumn<BillItem, String> billItemIdColumn = new TableColumn<>("ItemID");
        billItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        TableColumn<BillItem, String> billNameColumn = new TableColumn<>("Name");
        billNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        TableColumn<BillItem, String> billCategoryColumn = new TableColumn<>("Category");
        billCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        TableColumn<BillItem, Double> billPriceColumn = new TableColumn<>("Price");
        billPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        TableColumn<BillItem, Integer> billQuantityColumn = new TableColumn<>("Quantity");
        billQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        billTable.getColumns().addAll(billItemIdColumn, billNameColumn, billCategoryColumn, billPriceColumn, billQuantityColumn);
        billTable.setItems(controller.getBillItems());

        // Save and Print Bill Button
        Button savePrintButton = new Button("Save and Print Bill");
        savePrintButton.setOnAction(event -> controller.saveAndPrintBill());

        rightSection.getChildren().addAll(billsTextArea, billTable, savePrintButton);

        // Add Sections to Root
        root.setLeft(leftSection);
        root.setCenter(rightSection);
    }

    public void updateItemTable(ObservableList<Item> items) {
        itemTable.setItems(items);
    }

    public void clearQuantityField() {
        quantityField.clear();
    }

    public void refreshTables() {
        itemTable.refresh();
        billTable.refresh();
    }

    public void appendBillText(String text) {
        billsTextArea.appendText(text);
    }

    public BorderPane getRoot() {
        return root;
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
    }
}
