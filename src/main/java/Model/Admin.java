package Model;

public class Admin extends Manager{
    public Admin(String lastName, String firstName, String username, String password, double salary, String sector) {
        super(lastName, firstName, username, password, salary, sector);
        this.setRole("Admin");
    }
}
