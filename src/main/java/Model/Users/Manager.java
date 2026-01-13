package Model.Users;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serial;
import java.util.ArrayList;
import java.util.EnumSet;

public class Manager extends Employee {
    @Serial
    private static final long serialVersionUID = 123L;
    private ArrayList<String> sectors;

    public Manager(String lastName, String firstName, String username, String password, double salary) {
        super(lastName, firstName, username, password, salary);
        this.sectors = new ArrayList<>();
        this.setRole(Role.MANAGER);
        this.setPermissions(EnumSet.of(Permission.EDIT_ITEM, Permission.VIEW_ITEM, Permission.PERFORMANCE_SECTOR, Permission.VIEW_SECTOR));
    }


    public ObservableList<String> getSectors() {
        return FXCollections.observableList(sectors);
    }

    public void setSectors(ObservableList<String> sectors) {
        this.sectors = new ArrayList<>(sectors);
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

    public String getSectorName() {
        return getSectors().toString();
    }


}
