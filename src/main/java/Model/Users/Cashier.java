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
    private StringProperty sectorName;
    public Cashier(String lastName, String firstName, String username, String password, double salary, String sector) {
        super(lastName, firstName, username, password, salary);
        this.sectorName = new SimpleStringProperty(sector);
        this.setPermissions(EnumSet.of(Permission.CREATE_BILL, Permission.VIEW_BILL));
    }

    public String getSectorName() {
        return sectorName.get();
    }

    public void setSectorName(String sectorName) {
        this.sectorName.set(sectorName);
    }

    @Override
    protected void writeObject(ObjectOutputStream out) throws IOException {
        super.writeObject(out);
        out.writeUTF(sectorName.getValueSafe());
    }

    @Override
    protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readObject(in);
        this.sectorName = new SimpleStringProperty(in.readUTF());
    }
}
