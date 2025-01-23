package Controller;

import DAO.ItemDAO;
import Model.Items.Item;
import Model.Users.Employee;
import Model.Users.Manager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ManagerController {
    public ManagerController(Employee emp) {
//        System.out.println(((Manager)emp).getSectors());
        ObservableList<Item> lowStock = FXCollections.observableArrayList();
        ItemDAO dao = new ItemDAO();
        for(Item i: dao.getItemsBySectors(((Manager)emp).getSectors())) {
            if(i.getQuantity() <= 5)
                lowStock.add(i);
        }

        if(!lowStock.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Low Stock", ButtonType.CLOSE);
            alert.setTitle("Low Stock");
            StringBuilder items = new StringBuilder();
            for(Item i: lowStock) {
                items.append(i.getItemName()).append(", ");
            }
            items.setLength(items.length() - 2);
            alert.setHeaderText("Low Stock for these items: " + items + "!");
            alert.showAndWait();
        }
    }
}
