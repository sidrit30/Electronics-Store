package Model.Users;

import java.io.Serial;

public class Cashier extends Employee {
    @Serial
    private static final long serialVersionUID = 12L;
    private String sector;
    public Cashier(String lastName, String firstName, String username, String password, double salary, String sector) {
        super(lastName, firstName, username, password, salary);
        this.sector = sector;
        this.setRole(Role.CASHIER);
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
