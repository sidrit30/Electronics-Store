package Model.Users;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.EnumSet;

public class Cashier extends Employee {
    @Serial
    private static final long serialVersionUID = 12L;
    private String sectorName;

    public Cashier(String lastName, String firstName, String username, String password, double salary) {
        super(lastName, firstName, username, password, salary);
        this.setRole(Role.CASHIER);
        this.setPermissions(EnumSet.of(Permission.CREATE_BILL, Permission.VIEW_BILL));
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }


}
