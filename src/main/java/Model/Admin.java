package Model;

public class Admin extends Employee{
    public Admin(String lastName, String firstName, String username, String password, double salary) {
        super(lastName, firstName, username, password, salary);
        this.setRole("Admin");
    }
}