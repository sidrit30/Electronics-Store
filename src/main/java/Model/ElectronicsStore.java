package Model;

import java.util.ArrayList;

public class ElectronicsStore {
    ArrayList<Employee> employees;

    public ElectronicsStore() {
        employees = new ArrayList<>();
    }

    public ElectronicsStore(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee emp) {
        employees.add(emp);
    }

    public void removeEmployee(Employee emp) {
        employees.remove(emp);
    }



}
