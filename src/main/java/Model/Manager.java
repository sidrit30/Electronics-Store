package Model;

public class Manager extends Cashier {

    private String[] sectors;

    public Manager(String lastName, String firstName, String username, String password, double salary, String sector) {
        super(lastName, firstName, username, password, salary, sector);
        this.sectors = new String[] {};
        this.setRole("Manager");
    }


    public String[] getSectors() {
        return sectors;
    }

    public void setSectors(String[] sectors) {
        this.sectors = sectors;
    }
}
