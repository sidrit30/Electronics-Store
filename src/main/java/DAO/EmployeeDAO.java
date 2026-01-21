package DAO;

import Model.Exceptions.InvalidPasswordException;
import Model.Exceptions.InvalidUsernameException;
import Model.Users.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;

import static Main.Launcher.PATH;

public class EmployeeDAO {
    private static final File EMPLOYEES_FILE = new File(PATH + File.separator + "data" + File.separator +  "staff.dat");
    private static final ObservableList<Employee> employees = FXCollections.observableArrayList();

    public ObservableList<Employee> getEmployees() {
        if (employees.isEmpty()) {
            loadEmployees();
        }
        return employees;
    }

    private void loadEmployees() {
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(EMPLOYEES_FILE))) {
            while (true) {
                Employee employee = (Employee) input.readObject();
                employees.add(employee);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading employees");
            System.out.println(e.getMessage());
        }
    }

    public boolean createEmployee(Employee employee){
        employees.add(employee);
        return UpdateAll();
    }

    public Employee getEmployeebyID(String id){
        for(Employee employee : getEmployees()) {
            if(employee.getId().equals(id)) {
                return employee;
            }
        }
        return null;
    }

    public boolean validUsername(String username) {
        for (Employee emp : getEmployees()) {
            if (emp.getUsername().equals(username))
                return false;
        }
        return true;
    }

    public boolean deleteEmployee(Employee employee) {
        getEmployees().remove(employee);
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(EMPLOYEES_FILE))) {
            for(Employee emp : employees) {
                output.writeObject(emp);
                System.out.println(emp.toString());
            }
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
        Employee employee = null;
        for(Employee e : getEmployees()) {
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
