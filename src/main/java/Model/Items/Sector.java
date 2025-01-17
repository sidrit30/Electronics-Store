package Model.Items;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Sector implements Serializable {
    @Serial
    private static final long serialVersionUID = 111L;
    private final String sectorName;
    private ObservableList<String> categories;
    private ObservableList<Item> items;
    public Sector(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getSectorName() {
        return sectorName;
    }

    public ObservableList<String> getCategories() {
        return categories;
    }

    public ObservableList<Item> getItems() {
        return items;
    }

    public void add(Item item) {
        items.add(item);
    }
    public void remove(Item item) {
        items.remove(item);
    }

    public void addCategory(String category) {
        categories.add(category);
    }
    public void removeCategory(String category) {
        categories.remove(category);
    }


}
