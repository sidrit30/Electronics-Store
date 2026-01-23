package Model.Users;

import java.io.Serial;
import java.util.EnumSet;

public class Admin extends Employee{
    @Serial
    private static final long serialVersionUID = 1L;
    public Admin(String lastName, String firstName, String username, String password, double salary) {
        super(lastName, firstName, username, password, salary);
        this.setRole(Role.ADMIN);
        this.setPermissions(EnumSet.allOf(Permission.class));
    }

    public String getSectorName() {
        return "All";
    }

}