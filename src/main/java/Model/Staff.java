package Model;

import java.util.ArrayList;
import java.util.Comparator;

public class Staff {
    private ArrayList<Employee> staff;
    public Staff() {
        staff = new ArrayList<>();
    }

    public Staff(ArrayList<Employee> staff) {
        this.staff = staff;
    }

    public ArrayList<Employee> getStaff() {
        return staff;
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

    public ArrayList<Employee> getStaffByFirstName(String name) {
        ArrayList<Employee> staff1 = new ArrayList<>();
        for (Employee emp : staff) {
            if(emp.getFirstName().equals(name)) {
                staff1.add(emp);
            }
        }
        return staff1;
    }

    public ArrayList<Employee> getStaffByLastName(String name) {
        ArrayList<Employee> staff1 = new ArrayList<>();
        for (Employee emp : staff) {
            if(emp.getLastName().equals(name)) {
                staff1.add(emp);
            }
        }
        return staff1;
    }

//    needs to be implemented
//    public ArrayList<Employee> sortStaffById() {
//
//    }
//    public ArrayList<Employee> sortStaffByLastName() {
//
//    }


    public ArrayList<Employee> sortStaffBySalary() {
        staff.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                double diff = e1.getSalary() - e2.getSalary();
                if(diff == 0) {
                    return 0;
                }
                return diff > 0 ? 1 : -1;
            }
        });
        return staff;
    }




}
