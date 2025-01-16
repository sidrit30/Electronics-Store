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
    private ArrayList<Category> categories;
    public Sector(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getSectorName() {
        return sectorName;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }

    public boolean containsCategory(Category category) {
        return categories.contains(category);
    }

    public ObservableList<Category> getCategoriesObservableList() {
        ObservableList<Category> categoryObservableList = FXCollections.observableArrayList();
        categoryObservableList.addAll(categories);
        return categoryObservableList;
    }

    public ObservableList<Category> searchCategory(String keyword) {
        ObservableList<Category> categoryObservableList = FXCollections.observableArrayList();
        for (Category category : categories) {
            if(category.getCategoryName().toLowerCase().contains(keyword.toLowerCase())) {
                categoryObservableList.add(category);
            }
        }
        return categoryObservableList;
    }
}
