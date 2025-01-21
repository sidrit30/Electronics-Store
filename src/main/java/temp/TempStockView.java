//package temp;
//
//import Model.Items.Item;
//import Model.Items.Sector;
//import Model.Users.Employee;
//import javafx.application.Application;
//import javafx.beans.property.*;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.collections.transformation.FilteredList;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.*;
//import javafx.stage.Stage;
//
//import java.io.*;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TempStockView extends VBox {
//
//
//    private final ObservableList<Item> data = FXCollections.observableArrayList();
//
//    private ComboBox<Sector> filterSectors = new ComboBox<>();
//    private ComboBox<String> filterCategories = new ComboBox<>();
//    private TextField searchField = new TextField();
//    private ComboBox<Sector> addItemSector = new ComboBox<>();
//    private ComboBox<String> addItemCategory = new ComboBox<>();
//    private TextField addItemName = new TextField();
//    private TextField addSellingPrice = new TextField();
//    private TextField addPurchasePrice = new TextField();
//    private TextField addQuantity = new TextField();
//    private TextField addSupplier = new TextField();
//    private TextField addItemDescription = new TextField();
//
//    private TableView<Item> table = new TableView<>();
//
//    public TempStockView() {
//        // Table setup
//        table.setEditable(false);
//        table.setPrefWidth(800);
//        table.setPrefHeight(400);
//
//        TableColumn<Item, String> itemIDCol = new TableColumn<>("Item ID");
//        itemIDCol.setCellValueFactory(new PropertyValueFactory<>("itemID"));
//        itemIDCol.setStyle("-fx-alignment: CENTER;");
//
//        TableColumn<Item, String> itemNameCol = new TableColumn<>("Item Name");
//        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
//        itemNameCol.setStyle("-fx-alignment: CENTER;");
//
//        TableColumn<Item, String> itemCategoryCol = new TableColumn<>("Category");
//        itemCategoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
//        itemCategoryCol.setStyle("-fx-alignment: CENTER;");
//
//        TableColumn<Item, String> itemSectorCol = new TableColumn<>("Sector");
//        itemSectorCol.setCellValueFactory(new PropertyValueFactory<>("sector"));
//        itemSectorCol.setStyle("-fx-alignment: CENTER;");
//
//        TableColumn<Item, Double> sellingPriceCol = new TableColumn<>("Selling Price");
//        sellingPriceCol.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
//        sellingPriceCol.setStyle("-fx-alignment: CENTER;");
//
//        TableColumn<Item, Double> purchasePriceCol = new TableColumn<>("Purchase Price");
//        purchasePriceCol.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
//        purchasePriceCol.setStyle("-fx-alignment: CENTER;");
//
//        TableColumn<Item, Integer> quantityCol = new TableColumn<>("Quantity");
//        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        quantityCol.setStyle("-fx-alignment: CENTER;");
//
//        TableColumn<Item, String> supplierCol = new TableColumn<>("Supplier");
//        supplierCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));
//        supplierCol.setStyle("-fx-alignment: CENTER;");
//
//        TableColumn<Item, String> itemDescriptionCol = new TableColumn<>("Description");
//        itemDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
//        itemDescriptionCol.setStyle("-fx-alignment: CENTER;");
//
//        table.setItems(data);
//        table.getColumns().addAll(itemIDCol, itemNameCol, itemCategoryCol, itemSectorCol, sellingPriceCol, purchasePriceCol, quantityCol, supplierCol, itemDescriptionCol);
//
//        // Sector and category dropdowns for filtering
//        filterSectors.setItems(sectors);
//        filterSectors.setPromptText("Filter by Sector");
//        filterSectors.setOnAction(e -> filterItems());
//
//        filterCategories.setPromptText("Filter by Category");
//        filterCategories.setOnAction(e -> filterItems());
//
//        searchField.setPromptText("Search by Item Name");
//        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterItems());
//
//        HBox filterBox = new HBox(10);
//        filterBox.setPadding(new Insets(10));
//        filterBox.getChildren().addAll(filterSectors, filterCategories, searchField);
//
//        // Adding form to add or update items
//        addItemSector.setItems(sectors);
//        addItemSector.setPromptText("Select Sector");
//
//        addItemSector.setOnAction(e -> {
//            Sector selectedSector = addItemSector.getValue();
//            if (selectedSector != null) {
//                addItemCategory.setItems(selectedSector.getCategories());
//            }
//        });
//
//        addItemCategory.setPromptText("Select Category");
//        addItemName.setPromptText("Item Name");
//        addSellingPrice.setPromptText("Selling Price");
//        addPurchasePrice.setPromptText("Purchase Price");
//        addQuantity.setPromptText("Quantity");
//        addSupplier.setPromptText("Supplier");
//        addItemDescription.setPromptText("Description");
//
//        GridPane form = new GridPane();
//        form.setPadding(new Insets(10));
//        form.setHgap(10);
//        form.setVgap(10);
//        form.add(new Label("Item Name:"), 0, 0);
//        form.add(addItemName, 1, 0);
//
//        form.add(new Label("Category:"), 0, 1);
//        form.add(addItemCategory, 1, 1);
//
//        form.add(new Label("Sector:"), 0, 2);
//        form.add(addItemSector, 1, 2);
//
//        form.add(new Label("Selling Price:"), 0, 3);
//        form.add(addSellingPrice, 1, 3);
//
//        form.add(new Label("Purchase Price:"), 0, 4);
//        form.add(addPurchasePrice, 1, 4);
//
//        form.add(new Label("Quantity:"), 0, 5);
//        form.add(addQuantity, 1, 5);
//
//        form.add(new Label("Supplier:"), 0, 6);
//        form.add(addSupplier, 1, 6);
//
//        form.add(new Label("Description:"), 0, 7);
//        form.add(addItemDescription, 1, 7);
//
//// Buttons remain as they are
//
//
//        // Button for adding new items
//        Button addNewItemButton = new Button("Add New Item");
//        addNewItemButton.setOnAction(e -> {
//            String itemName = addItemName.getText();
//            String itemCategory = addItemCategory.getValue();
//            String itemSector = addItemSector.getValue().getSectorName();
//            double sellingPrice = Double.parseDouble(addSellingPrice.getText());
//            double purchasePrice = Double.parseDouble(addPurchasePrice.getText());
//            int quantity = Integer.parseInt(addQuantity.getText());
//            String supplier = addSupplier.getText();
//            String itemDescription = addItemDescription.getText();
//
//            Item newItem = new Item(itemName, itemCategory, itemSector, sellingPrice, purchasePrice, quantity, supplier, itemDescription);
//            data.add(newItem);
//            addItemSector.getValue().addItem(newItem);
//            clearFormFields();
//        });
//
//        // Button for editing item information
//        Button editButton = new Button("Edit");
//        editButton.setOnAction(e -> {
//            Item selectedItem = table.getSelectionModel().getSelectedItem();
//            if (selectedItem != null) {
//                populateFormFields(selectedItem);
//                addNewItemButton.setText("Save Changes");
//                addNewItemButton.setOnAction(event -> {
//                    selectedItem.setItemCategory(addItemCategory.getValue());
//                    selectedItem.setSector(addItemSector.getValue().getSectorName());
//                    selectedItem.setSellingPrice(Double.parseDouble(addSellingPrice.getText()));
//                    selectedItem.setPurchasePrice(Double.parseDouble(addPurchasePrice.getText()));
//                    selectedItem.setQuantity(Integer.parseInt(addQuantity.getText()));
//                    selectedItem.setSupplier(addSupplier.getText());
//                    selectedItem.setItemDescription(addItemDescription.getText());
//                    clearFormFields();
//                    addNewItemButton.setText("Add New Item");
//                    table.refresh();
//                });
//            }
//        });
//
//        HBox buttonBox = new HBox();
//        buttonBox.setSpacing(10);
//        buttonBox.setPadding(new Insets(10));
//        buttonBox.getChildren().addAll(addNewItemButton, editButton);
//
//        HBox mainBox = new HBox();
//        mainBox.setSpacing(10);
//        mainBox.setPadding(new Insets(10));
//        mainBox.getChildren().addAll(form, buttonBox);
//
//        // Adding left sidebar with navigation buttons
//        VBox leftSidebar = new VBox();
//        leftSidebar.setSpacing(10);
//        leftSidebar.setPadding(new Insets(10));
//
//        ImageView homeIcon = new ImageView(new Image("C:/Users/jonid/OneDrive/Pictures/home icon.png"));
//        homeIcon.setFitHeight(16);
//        homeIcon.setFitWidth(16);
//        Button homeButton = new Button("Home");
//        homeButton.setGraphic(homeIcon);
//        homeButton.setPrefWidth(150);
//
//        ImageView userIcon = new ImageView(new Image("C:/Users/jonid/OneDrive/Pictures/log out icon.png"));
//        userIcon.setFitHeight(16);
//        userIcon.setFitWidth(16);
//        Button logoutButton = new Button("Logout");
//        logoutButton.setGraphic(userIcon);
//        logoutButton.setPrefWidth(150);
//
//        leftSidebar.getChildren().addAll(homeButton, logoutButton);
//
//        // Creating the main BorderPane layout
//        BorderPane mainBorderPane = new BorderPane();
//        mainBorderPane.setTop(filterBox);
//        mainBorderPane.setLeft(leftSidebar);
//        mainBorderPane.setCenter(table);
//        mainBorderPane.setBottom(mainBox);
//        mainBorderPane.setPadding(new Insets(10));
//
//        Scene scene = new Scene(mainBorderPane, 1200, 600);
//        stage.setScene(scene);
//        stage.setTitle("Item Management System");
//        stage.show();
//    }
//
//    private void filterItems() {
//        Sector selectedSector = filterSectors.getValue();
//        String selectedCategory = filterCategories.getValue();
//        String searchText = searchField.getText().toLowerCase();
//
//        FilteredList<Item> filteredData = new FilteredList<>(data, item -> {
//            boolean matchesSector = (selectedSector == null || item.getSector().equals(selectedSector.getSectorName()));
//            boolean matchesCategory = (selectedCategory == null || item.getItemCategory().equals(selectedCategory));
//            boolean matchesSearch = (searchText.isEmpty() || item.getItemName().toLowerCase().contains(searchText));
//            return matchesSector && matchesCategory && matchesSearch;
//        });
//
//        table.setItems(filteredData);
//    }
//
//    private void populateFormFields(Item item) {
//        addItemSector.setValue(findSectorByName(item.getSector()));
//        addItemCategory.setValue(item.getItemCategory());
//        addItemName.setText(item.getItemName());
//        addSellingPrice.setText(String.valueOf(item.getSellingPrice()));
//        addPurchasePrice.setText(String.valueOf(item.getPurchasePrice()));
//        addQuantity.setText(String.valueOf(item.getQuantity()));
//        addSupplier.setText(item.getSupplier());
//        addItemDescription.setText(item.getItemDescription());
//    }
//
//    private Sector findSectorByName(String sectorName) {
//        for (Sector sector : sectors) {
//            if (sector.getSectorName().equals(sectorName)) {
//                return sector;
//            }
//        }
//        return null;
//    }
//
//    private void clearFormFields() {
//        addItemSector.setValue(null);
//        addItemCategory.setValue(null);
//        addItemName.clear();
//        addSellingPrice.clear();
//        addPurchasePrice.clear();
//        addQuantity.clear();
//        addSupplier.clear();
//        addItemDescription.clear();
//    }
