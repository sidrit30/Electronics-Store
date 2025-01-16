package Model.Users;

import Model.Items.Sector;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.EnumSet;

public class Manager extends Employee {
    @Serial
    private static final long serialVersionUID = 123L;
    private ListProperty<Sector> sectors;

    public Manager(String lastName, String firstName, String username, String password, double salary) {
        super(lastName, firstName, username, password, salary);
        this.sectors = new SimpleListProperty<>();
        this.setPermissions(EnumSet.of(Permission.VIEW_INVENTORY, Permission.MANAGE_INVENTORY,
                Permission.VIEW_ALL_SECTORS, Permission.MANAGE_SECTORS));
    }


//    public ObservableList<String> getSectors() {
//        return sectors.get();
//    }

//    public void setSectors(ObservableList<String> sectors) {
//        this.sectors = sectors;
//    }

//    public void addSector(String sector) {
//        this.sectors.add(sector);
//    }

    public void removeSector(String sector) {
        this.sectors.remove(sector);
    }

    public boolean sectorExists(String sector) {
        return this.sectors.contains(sector);
    }

    @Override
    protected void writeObject(ObjectOutputStream out) throws IOException {
        super.writeObject(out);

    }
}
