package Model.Items;

import Model.Exceptions.ExistingItemException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = 143902L;
    private final String categoryName;
    ArrayList<Item> items;

    public Category(String categoryName) {
        this.categoryName = categoryName;
        items = new ArrayList<>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(String itemName, double salePrice, double purchasePrice, int quantity, String supplier, String itemDescription) throws ExistingItemException {
        if (searchItems(itemName) == null) {
            items.add(new Item(itemName, categoryName, salePrice, purchasePrice, quantity, supplier, itemDescription));
        }
        else {
            throw new ExistingItemException("Item already exists");
        }
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public ObservableList<Item> getObservableItems() {
        ObservableList<Item> observableItems = FXCollections.observableArrayList();
        observableItems.addAll(items);
        return observableItems;
    }

    public ObservableList<Item> searchItems(String name) {
        ObservableList<Item> observableItems = FXCollections.observableArrayList();
        for (Item item : items) {
            if(item.getItemName().toLowerCase().contains(name.toLowerCase())) {
                observableItems.add(item);
            }
        }
        return observableItems;
    }

    public String getCategoryName() {
        return categoryName;
    }

}
