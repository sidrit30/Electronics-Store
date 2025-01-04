package Model;

public abstract class Employee {
    private static int nrEmployees;
    private final String id;
    private final String lastName;
    private final String firstName;
    private String address;
    private String phone;
    private String email;
    private String username;
    private String password;
    private double salary;
    private String role;

    public Employee(String lastName, String firstName, String username, String password, double salary) {
        //start from id 001
        this.id = String.format("%03d", ++nrEmployees);
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.password = password;
        this.salary = salary;
    }


    public static int getNrEmployees() {
        return nrEmployees;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", salary=" + salary +
                ", role='" + role + '\'' +
                '}';
    }
}
