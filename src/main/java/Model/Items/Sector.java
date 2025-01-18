package Model.Items;

import Model.Exceptions.AlreadyExistingException;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;

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

    public void add(Item item) throws AlreadyExistingException {
        for (Item i : items) {
            if (i.getItemName().equals(item.getItemName())) {
                throw new AlreadyExistingException("Item: " + item.getItemName() + " already exists");
            }
        }
        items.add(item);
    }

    public boolean remove(Item item) {
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

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(categories.stream().toList());
    }


}
