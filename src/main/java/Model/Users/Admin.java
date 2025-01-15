package Model.Users;

import java.io.Serial;

public class Admin extends Employee{
    @Serial
    private static final long serialVersionUID = 1L;
    public Admin(String lastName, String firstName, String username, String password, double salary) {
        super(lastName, firstName, username, password, salary);
        this.setRole(Role.ADMIN);
    }

    //creates a basic admin for testing purposes
    public Admin() {
        this.setUsername("admin");
        this.setPassword("admin");
        this.setRole(Role.ADMIN);
    }
}