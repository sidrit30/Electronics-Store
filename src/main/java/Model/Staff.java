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

    public Employee[] getStaffByFirstName(String name) {
        ArrayList<Employee> staff1 = new ArrayList<>();
        for (Employee emp : staff) {
            if(emp.getFirstName().equals(name)) {
                staff1.add(emp);
            }
        }
        return staff1.toArray(new Employee[staff1.size()]);
    }

    public Employee[] getStaffByLastName(String name) {
        ArrayList<Employee> staff1 = new ArrayList<>();
        for (Employee emp : staff) {
            if(emp.getLastName().equals(name)) {
                staff1.add(emp);
            }
        }
        return staff1.toArray(new Employee[staff1.size()]);
    }

    public Employee[] sortStaffByLastName() {
        ArrayList<Employee> staff1 = (ArrayList<Employee>) staff.clone();
        staff1.sort(new Comparator<>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o2.getLastName().compareTo(o1.getLastName());
            }
        });
        return staff1.toArray(new Employee[staff1.size()]);
    }


    public Employee[] sortStaffBySalary() {
        ArrayList<Employee> staff1 = (ArrayList<Employee>) staff.clone();
        staff1.sort(new Comparator<>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                double diff = e1.getSalary() - e2.getSalary();
                if (diff == 0) {
                    return 0;
                }
                return diff > 0 ? 1 : -1;
            }
        });
        return staff1.toArray(new Employee[staff1.size()]);
    }




}
