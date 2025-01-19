package DAO;

import Model.Exceptions.InvalidPasswordException;
import Model.Exceptions.InvalidUsernameException;
import Model.Users.Cashier;
import Model.Users.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private static final File EMPLOYEES_FILE = new File("src/main/resources/data/staff.dat");
    private static final ObservableList<Employee> employees = FXCollections.observableArrayList();

    public ObservableList<Employee> getEmployees() {
        if (employees.isEmpty()) {
            loadEmployees();
        }
        return employees;
    }

    public ObservableList<Cashier> getCashiers(ArrayList<String> sectors) {
        getEmployees();
        ObservableList<Cashier> cashiers = FXCollections.observableArrayList();
        for (Employee employee : employees) {
            if(employee instanceof Cashier){
                if(sectors.contains(((Cashier) employee).getSectorName()))
                    cashiers.add((Cashier) employee);
            }
        }
        return cashiers;
    }

    private void loadEmployees() {
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(EMPLOYEES_FILE))) {
            while (true) {
                Employee employee = (Employee) input.readObject();
                System.out.println(employee.getFullName());
                employees.add(employee);
            }
        } catch (EOFException ignored) {
            System.out.println("yeeeee");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean createEmployee(Employee employee) {
        try (FileOutputStream outputStream = new FileOutputStream(EMPLOYEES_FILE, true)) {
            ObjectOutputStream writer;

            if(EMPLOYEES_FILE.length() > 0) {
                writer = new HeaderlessObjectOutputStream(outputStream);
            } else {
                writer = new ObjectOutputStream(outputStream);
            }

            writer.writeObject(employee);
            employees.add(employee);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean deleteEmployee(Employee employee) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(EMPLOYEES_FILE))) {
            for(Employee emp : employees) {
                if(!emp.equals(employee)) {
                    output.writeObject(emp);
                }
            }
            employees.remove(employee);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean deleteList(List<Employee> list) {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(EMPLOYEES_FILE))) {
            for (Employee e : employees) {
                if(!list.contains(e))
                    output.writeObject(e);
            }
            employees.remove(list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean UpdateAll() {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(EMPLOYEES_FILE))) {
            for (Employee e : employees) {
                output.writeObject(e);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Employee authLogin(String username, String password) {
        loadEmployees();
        Employee employee = null;
        for(Employee e : employees) {
            if(e.getUsername().equals(username)) {
                employee = e;
                break;
            }
        }
        if(employee == null) {
            throw new InvalidUsernameException("Invalid Username");
        }
        if(employee.getPassword().equals(password)) {
            return employee;
        }
        throw new InvalidPasswordException("Incorrect Password");
    }
}
