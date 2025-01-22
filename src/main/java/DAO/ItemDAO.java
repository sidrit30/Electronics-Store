package DAO;

import Model.Items.Item;
import Model.Users.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class ItemDAO {
    private static final File ITEM_FILE = new File("src/main/resources/data/items.dat");
    private static final ObservableList<Item> items = FXCollections.observableArrayList();

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
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(ITEM_FILE))) {
            while (true) {
                items.add((Item) input.readObject());
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean createItem(Item item) {
        try (FileOutputStream outputStream = new FileOutputStream(ITEM_FILE, true)) {
            ObjectOutputStream writer;

            if(ITEM_FILE.length() > 0) {
                writer = new HeaderlessObjectOutputStream(outputStream);
            } else {
                writer = new ObjectOutputStream(outputStream);
            }

            writer.writeObject(item);
            items.add(item);
            return true;
        } catch (IOException e) {
            return false;
        }
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
            }
            return true;
        } catch (IOException e) {
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
            if(s.getSectorName().equals(sectorName))
                if(!(categories).contains(s.getItemCategory()))
                    categories.add(s.getItemCategory());
        }
        return categories;
    }

}
