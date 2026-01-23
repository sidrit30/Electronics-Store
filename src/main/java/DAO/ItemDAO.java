package DAO;

import Model.Items.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.logging.Logger;

import static Main.Launcher.PATH;

public class ItemDAO {
    private static final File ITEM_FILE = new File(PATH + File.separator + "data" + File.separator + "items.dat");
    private static final ObservableList<Item> items = FXCollections.observableArrayList();
    private final Logger logger = Logger.getLogger(getClass().getName());


    public ObservableList<Item> getItems() {
        if (items.isEmpty()) {
            loadItems();
        }
        return items;
    }

    public ObservableList<Item> getItemsBySector(String sector) {
        ObservableList<Item> filteredItems = FXCollections.observableArrayList();
        for (Item item : getItems()) {
            if(item.getSectorName().equals(sector)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public boolean validItemName(String name) {
        for (Item item : getItems()) {
            if (item.getItemName().equals(name))
                return false;
        }
        return true;
    }

    public ObservableList<Item> getItemsBySectors(ObservableList<String> sectors) {
        ObservableList<Item> filteredItems = FXCollections.observableArrayList();
        if(sectors.isEmpty())
            return filteredItems;
        for (Item item : getItems()) {
            if(sectors.contains(item.getSectorName()))
                filteredItems.add(item);
        }
        return filteredItems;
    }

    public void loadItems() {
        items.clear();

        if (!ITEM_FILE.exists()) return;

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(ITEM_FILE))) {
            while (true) {
                Item item = (Item) input.readObject();
                items.add(item);
            }
        } catch (EOFException e) {
            // End of file reached normally
            logger.info("Finished loading items.");
        } catch (IOException | ClassNotFoundException e) {
            logger.severe("Error loading items!");
            logger.severe(e.toString());
        }
    }


    public boolean createItem(Item item) {
        items.add(item);
        return UpdateAll();
    }

    public boolean deleteItem(Item item) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(ITEM_FILE))) {
            for(Item i : items) {
                if(!i.equals(item)) {
                    output.writeObject(i);
                }
            }
            items.remove(item);
            return true;
        } catch (IOException e) {
            return false;
        }
    }



    public boolean UpdateAll() {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(ITEM_FILE))) {
            for (Item i : items) {
                output.writeObject(i);
                logger.info("Updated items!");
            }
            return true;
        } catch (IOException e) {
            logger.severe("Error updating items!");
            return false;
        }
    }

    public ObservableList<String> getSectorNames() {
        ObservableList<String> names = FXCollections.observableArrayList();
        for(Item s : getItems()) {
            if(!(names.contains(s.getSectorName())))
                names.add(s.getSectorName());
        }
        return names;
    }

    public ObservableList<String> getItemCategories(String sectorName) {
        ObservableList<String> categories = FXCollections.observableArrayList();
        for(Item s : getItems()) {
            if(s.getSectorName().equals(sectorName) && !(categories).contains(s.getItemCategory()))
                    categories.add(s.getItemCategory());
        }
        return categories;
    }

    public Item getItemByID(String id) {
        for(Item item : getItems()) {
            if(item.getItemID().equals(id))
                return item;
        }
        return null;
    }
}
