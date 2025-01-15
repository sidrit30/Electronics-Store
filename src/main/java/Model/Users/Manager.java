package Model.Users;

import java.io.Serial;
import java.util.ArrayList;

public class Manager extends Employee {
    @Serial
    private static final long serialVersionUID = 123L;
    private ArrayList<String> sectors;

    public Manager(String lastName, String firstName, String username, String password, double salary) {
        super(lastName, firstName, username, password, salary);
        this.sectors = new ArrayList<>();
        this.setRole(Role.MANAGER);
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
