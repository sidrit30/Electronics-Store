package Controller;

import DAO.EmployeeDAO;
import Model.Users.Employee;
import View.ManageEmployeeTableView;

public class ManageEmployeeController {
    private EmployeeDAO employeeDAO;
    private ManageEmployeeTableView employeeTableView;

    public ManageEmployeeTableView getManageEmployeeTableView() {
        return employeeTableView;
    }

    public ManageEmployeeController(Employee employee) {
        employeeDAO = new EmployeeDAO();
        employeeTableView = new ManageEmployeeTableView();
        employeeTableView.getTable().setItems(employeeDAO.getExcluded(employee));
    }

}
