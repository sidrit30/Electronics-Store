package Model.Items;

import Model.Exceptions.AlreadyExistingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sector implements Serializable {
    @Serial
    private static final long serialVersionUID = 111L;
    private final String sectorName;
    private ArrayList<String> categories;
    private ArrayList<Item> items;
    public Sector(String sectorName) {
        this.sectorName = sectorName;
        this.categories = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    public String getSectorName() {
        return sectorName;
    }

    public ObservableList<String> getCategories() {
        ObservableList<String> categories = FXCollections.observableArrayList();
        for(Item item : items) {
            if(categories.isEmpty()) {
                categories.add(item.getItemCategory());
            }
            if(!categories.contains(item.getItemCategory())) {
                categories.add(item.getItemCategory());
            }
        }
        return categories;
    }

    public ObservableList<Item> getItems() {
        ObservableList<Item> items = FXCollections.observableArrayList();
        items.addAll(this.items);
        return items;
    }

    public void addItem(Item item) throws AlreadyExistingException {
        for (Item i : items) {
            if (i.getItemName().equals(item.getItemName())) {
                throw new AlreadyExistingException("Item: " + item.getItemName() + " already exists");
            }
        }
        items.add(item);
    }

    public boolean removeItem(Item item) {
        try {
            items.remove(item);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public void addCategory(String category) throws AlreadyExistingException {
        if (categories.contains(category)) {
            throw new AlreadyExistingException("Category: " + category + " already exists");
        }
        categories.add(category);

    }

    public void removeCategory(String category) {
        categories.remove(category);
    }
}
