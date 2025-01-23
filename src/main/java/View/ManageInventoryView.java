package View;

import Model.Items.Item;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.ArrayList;

public class ManageInventoryView extends VBox {
    //most of the class adapted from ManageEmployeeTableView
    private final TableView<Item> table = new TableView<>();
    private final TableColumn<Item, String> itemIdCol;
    private final TableColumn<Item, String> itemNameCol;
    private final TableColumn<Item, String> itemSectorCol;
    private final TableColumn<Item, String> itemCategoryCol;
    private final TableColumn<Item, String> supplierNameCol;
    private final TableColumn<Item, Double> purchasePriceCol;
    private final TableColumn<Item, Double> sellPriceCol;
    private final TableColumn<Item, Integer> quantityCol;
    private final TableColumn<Item, String> descriptionCol;

    private final TextField addItemName = new TextField();
    private final ComboBox<String> selectSector = new ComboBox<>();
    private final ComboBox<String> selectItemCategory = new ComboBox<>();
    private final TextField addSupplierName = new TextField();
    private final TextField addPurchasePrice = new TextField();
    private final TextField addSellPrice = new TextField();
    private final TextField addQuantity = new TextField();
    private final TextArea addItemDescription = new TextArea();

    //no time
//    private final ComboBox<String> sortSector = new ComboBox<>();
//    private final ChoiceBox<String> searchBy = new ChoiceBox<>();
//    private final Button searchButton = new Button("Search");
    private final Button deleteButton = new Button("Delete");
    private final Button addNewItemButton = new Button("Add New Item");
    private final Button saveButton = new Button("Save");


    private final VBox mainVBox;

    private final TextField searchField = new TextField();


    public ManageInventoryView() {
        //table datafields
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setEditable(false);

        itemIdCol = new TableColumn<>("Item ID");
        itemIdCol.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        itemIdCol.setStyle("-fx-alignment: CENTER;");

        itemNameCol = new TableColumn<>("Item Name");
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemNameCol.setStyle("-fx-alignment: CENTER;");

        itemSectorCol = new TableColumn<>("Sector");
        itemSectorCol.setCellValueFactory(new PropertyValueFactory<>("sectorName"));
        itemSectorCol.setStyle("-fx-alignment: CENTER;");

        itemCategoryCol = new TableColumn<>("Category");
        itemCategoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        itemCategoryCol.setStyle("-fx-alignment: CENTER;");

        supplierNameCol = new TableColumn<>("Supplier Name");
        supplierNameCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        supplierNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        supplierNameCol.setStyle("-fx-alignment: CENTER;");

        purchasePriceCol = new TableColumn<>("Purchase Price");
        purchasePriceCol.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        purchasePriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        purchasePriceCol.setStyle("-fx-alignment: CENTER;");

        sellPriceCol = new TableColumn<>("Selling Price");
        sellPriceCol.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        sellPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        sellPriceCol.setStyle("-fx-alignment: CENTER;");

        quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityCol.setStyle("-fx-alignment: CENTER;");

        descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setStyle("-fx-alignment: CENTER;");


        table.getColumns().add(itemIdCol);
        table.getColumns().add(itemNameCol);
        table.getColumns().add(itemSectorCol);
        table.getColumns().add(itemCategoryCol);
        table.getColumns().add(supplierNameCol);
        table.getColumns().add(purchasePriceCol);
        table.getColumns().add(sellPriceCol);
        table.getColumns().add(quantityCol);
        table.getColumns().add(descriptionCol);


        addItemName.setPromptText("Item Name");
        addSupplierName.setPromptText("Supplier Name");
        addPurchasePrice.setPromptText("Purchase Price");
        addSellPrice.setPromptText("Selling Price");
        addQuantity.setPromptText("Quantity");
        addItemDescription.setPromptText("Description");

        selectSector.setPromptText("Select Sector");
        selectSector.setStyle("-fx-alignment: CENTER;");
        selectSector.setMinWidth(100);

        selectItemCategory.setPromptText("Select Item Category");
        selectItemCategory.setStyle("-fx-alignment: CENTER;");
        selectItemCategory.setMinWidth(100);
        selectItemCategory.setPromptText("Select Category");




        HBox addBox1 = new HBox();
        addBox1.setSpacing(30);
        addBox1.setPadding(new Insets(10));
        addBox1.getChildren().addAll(addItemName, selectSector, selectItemCategory);

        HBox addBox2 = new HBox();
        addBox2.setSpacing(30);
        addBox2.setPadding(new Insets(10));
        addBox2.getChildren().addAll(addSupplierName, addPurchasePrice, addSellPrice, addQuantity, addItemDescription);


        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.getChildren().addAll(addNewItemButton, deleteButton, saveButton);

        mainVBox = new VBox();
        mainVBox.setSpacing(15);
        mainVBox.setPadding(new Insets(10));
        mainVBox.getChildren().addAll(addBox1, addBox2, buttonBox);

//        sortSector.setPromptText("Select Sector");
//        searchField.setPromptText("Search By: ");
//        ArrayList<String> searchCriteria = new ArrayList<>();
//        searchCriteria.add("Item Name");
//        searchCriteria.add("Category");
//        searchBy.getItems().setAll(searchCriteria);
//        searchBy.getSelectionModel().selectFirst();

//        HBox searchBox = new HBox();
//        searchBox.setSpacing(10);
//        searchBox.setPadding(new Insets(10));
//        searchBox.getChildren().addAll(sortSector, searchField, searchBy, searchButton);


        table.setStyle("-fx-background-color: #D2CFDA; -fx-text-fill: #884135;");
        mainVBox.setStyle("-fx-background-color: #D39C7E;");
        //searchBox.setStyle("-fx-background-color: #916449; -fx-text-fill: white;");


        this.getChildren().addAll(table, mainVBox);
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        //this.setStyle("-fx-background-color: #90614d;");

    }

    private void clearFormFields() {
        addItemName.clear();
        selectItemCategory.getSelectionModel().clearSelection();
        addSupplierName.clear();
        addPurchasePrice.clear();
        addSellPrice.clear();
        addQuantity.clear();
        addItemDescription.clear();

    }

    public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(message);
        alert.setTitle("Error");
        alert.showAndWait();
    }

    public TableView<Item> getTable() {
        return table;
    }

    public TableColumn<Item, String> getItemIdCol() {
        return itemIdCol;
    }

    public TableColumn<Item, String> getItemNameCol() {
        return itemNameCol;
    }

    public TableColumn<Item, String> getItemCategoryCol() {
        return itemCategoryCol;
    }


    public TableColumn<Item, String> getSupplierNameCol() {
        return supplierNameCol;
    }

    public TableColumn<Item, Double> getPurchasePriceCol() {
        return purchasePriceCol;
    }

    public TableColumn<Item, Double> getSellPriceCol() {
        return sellPriceCol;
    }

    public TableColumn<Item, Integer> getQuantityCol() {
        return quantityCol;
    }

    public TableColumn<Item, String> getDescriptionCol() {
        return descriptionCol;
    }

    public TextField getAddItemName() {
        return addItemName;
    }

    public ComboBox<String> getSelectItemCategory() {
        return selectItemCategory;
    }

    public TextField getAddSupplierName() {
        return addSupplierName;
    }

    public TextField getAddPurchasePrice() {
        return addPurchasePrice;
    }

    public TextField getAddSellPrice() {
        return addSellPrice;
    }

    public TextField getAddQuantity() {
        return addQuantity;
    }

    public TextArea getAddItemDescription() {
        return addItemDescription;
    }

//    public ChoiceBox<String> getSearchBy() {
//        return searchBy;
//    }
//
//    public Button getSearchButton() {
//        return searchButton;
//    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getAddNewItemButton() {
        return addNewItemButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public VBox getMainVBox() {
        return mainVBox;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public ComboBox<String> getSelectSector() {
        return selectSector;
    }

//    public ComboBox<String> getSortSector() {
//        return sortSector;
//    }
}
