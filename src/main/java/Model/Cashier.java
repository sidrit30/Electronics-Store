package Model;

import java.io.Serializable;

public class Cashier extends Employee {
    private String sector;
    public Cashier(String lastName, String firstName, String username, String password, double salary, String sector) {
        super(lastName, firstName, username, password, salary);
        this.sector = sector;
        this.setRole("Cashier");
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
