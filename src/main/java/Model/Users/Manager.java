package Model.Users;

import Model.Items.Sector;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Manager extends Employee {
    @Serial
    private static final long serialVersionUID = 123L;
    private List<String> sectors;

    public Manager(String lastName, String firstName, String username, String password, double salary) {
        super(lastName, firstName, username, password, salary);
        this.sectors = new ArrayList<>();
        this.setPermissions(EnumSet.of(Permission.VIEW_INVENTORY, Permission.MANAGE_INVENTORY,
                Permission.VIEW_ALL_SECTORS, Permission.MANAGE_SECTORS));
    }


    public ObservableList<String> getSectors() {
        return FXCollections.observableList(sectors);
    }

    public void setSectors(ObservableList<String> sectors) {
        this.sectors = sectors.stream().toList();
    }

    public void addSector(String sector) {
        this.sectors.add(sector);
    }

    public void removeSector(String sector) {
        this.sectors.remove(sector);
    }

    public boolean sectorExists(String sector) {
        return this.sectors.contains(sector);
    }


}
