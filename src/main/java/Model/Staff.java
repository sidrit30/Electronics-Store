package Model;

import Model.Users.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

public class Staff implements Serializable {
    private ArrayList<Employee> staff;
    public Staff() {
        staff = new ArrayList<>();
    }

    public Employee[] getStaff() {
        return staff.toArray(new Employee[staff.size()]);
    }

    public void addStaff(Employee emp) {
        staff.add(emp);
    }

    public boolean removeStaff(Employee emp) {
        return staff.remove(emp);
    }

    public Employee getEmployee(int index) {
        return staff.get(index);
    }

    //rewrite to return all
    public ObservableList<Employee> getStaffByLastName(String name) {
        ArrayList<Employee> staff1 = new ArrayList<>();
        for (Employee emp : staff) {
            if(emp.getLastName().equals(name)) {
                staff1.add(emp);
            }
        }
        return FXCollections.observableArrayList(staff1);
    }

    public String validateUsername(String username) {
        for (Employee emp : staff) {
            if(emp.getUsername().equals(username)) {
                return emp.getPassword();
            }
        }
        return null;
    }



}
