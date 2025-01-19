package Model.Users;

import Model.UniqueIDGenerator;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.*;
import java.util.EnumSet;

public abstract class Employee implements Serializable {
    private final String id;
    private final String lastName;
    private final String firstName;
    private transient StringProperty address;
    private transient StringProperty phone;
    private transient StringProperty email;
    private transient StringProperty username;
    private transient StringProperty password;
    private transient DoubleProperty salary;
    private EnumSet<Permission> permissions;

    public Employee(String lastName, String firstName, String username, String password, double salary) {
        this.id = UniqueIDGenerator.getUniqueId();
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.salary = new SimpleDoubleProperty(salary);
        this.permissions = EnumSet.noneOf(Permission.class);
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
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public double getSalary() {
        return salary.get();
    }

    public void setSalary(double salary) {
        this.salary.set(salary);
    }

    public EnumSet<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(EnumSet<Permission> permissions) {
        this.permissions = permissions;
    }

    public void addPermission(Permission permission) {
        this.permissions.add(permission);
    }

    public void removePermission(Permission permission) {
        this.permissions.remove(permission);
    }

    public boolean hasPermission(Permission permission) {
        return this.permissions.contains(permission);
    }


    @Override
    public String toString() {
        return getFullName();
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        if(address != null) out.writeUTF(this.address.getValueSafe());
        if(phone!=null) out.writeUTF(this.phone.getValueSafe());
        if(email!=null) out.writeUTF(this.email.getValueSafe());
        out.writeUTF(this.username.getValueSafe());
        out.writeUTF(this.password.getValueSafe());
        out.writeDouble(this.salary.getValue());
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.address = new SimpleStringProperty(in.readUTF());
        this.phone = new SimpleStringProperty(in.readUTF());
        this.email = new SimpleStringProperty(in.readUTF());
        this.username = new SimpleStringProperty(in.readUTF());
        this.password = new SimpleStringProperty(in.readUTF());
        this.salary = new SimpleDoubleProperty(in.readDouble());
    }
}
