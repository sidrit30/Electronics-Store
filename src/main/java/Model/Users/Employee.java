package Model.Users;

import java.io.Serializable;

public abstract class Employee implements Serializable {
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
    private Role role;

    public Employee(String lastName, String firstName, String username, String password, double salary) {
        //start from id 0001
        this.id = String.format("%04d", ++nrEmployees);
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.password = password;
        this.salary = salary;
    }

    protected Employee() {
        this.id = "000";
        this.firstName = "Admin";
        this.lastName = "Admin";
    }


    public static int getNrEmployees() {
        return nrEmployees;
    }
    public static void setNrEmployees(int nrEmployees) {
        Employee.nrEmployees = nrEmployees;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

//    @Override
//    public String toString() {
//        return "Employee{" +
//                "lastName='" + lastName + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", address='" + address + '\'' +
//                ", phone='" + phone + '\'' +
//                ", email='" + email + '\'' +
//                ", username='" + username + '\'' +
//                ", salary=" + salary +
//                ", role='" + role + '\'' +
//                '}';
//    }
}
