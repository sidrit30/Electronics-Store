package Model.Users;

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
        this.setPermissions(EnumSet.of(Permission.VIEW_INVENTORY, Permission.MANAGE_INVENTORY,
                Permission.VIEW_SECTOR, Permission.MANAGE_SECTOR));
    }


    public ArrayList<String> getSectors() {
        return sectors;
    }

    public void setSectors(ArrayList<String> sectors) {
        this.sectors = sectors;
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
