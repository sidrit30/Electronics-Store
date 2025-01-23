package Controller;

import DAO.BillDAO;
import DAO.EmployeeDAO;
import Model.Bill;
import Model.Users.Admin;
import Model.Users.Cashier;
import Model.Users.Employee;
import Model.Users.Manager;
import View.PerformanceView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class PerformanceController {
    private EmployeeDAO employeeDAO;
    private BillDAO billDAO;
    private Employee employee;
    private PerformanceView performanceView;

    public PerformanceView getPerformanceView() {
        return performanceView;
    }



    public PerformanceController(Employee employee) {
        this.employee = employee;
        employeeDAO = new EmployeeDAO();
        billDAO = new BillDAO();
        performanceView = new PerformanceView();

        ObservableList<Employee> employees = FXCollections.observableArrayList();

        if(this.employee instanceof Admin) {
            for(Employee e : employeeDAO.getEmployees()) {
                if(e instanceof Cashier)
                    employees.add(e);
            }
        } else {
            for(Employee e : employeeDAO.getEmployees()) {
                if((e instanceof Cashier) && (this.employee).getSectorName().contains(e.getSectorName()))
                    employees.add(e);
            }
        }

        performanceView.getCashierComboBox().getItems().addAll(employees);

        performanceView.getSearchButton().setOnAction(e -> {
            Employee emp = performanceView.getCashierComboBox().getSelectionModel().getSelectedItem();
            if(emp == null) {
                return;
            }
            LocalDate dateFrom = performanceView.getStartDatePicker().getValue();
            LocalDate dateTo = performanceView.getEndDatePicker().getValue();
            ObservableList<Bill> filteredBills = FXCollections.observableArrayList();
            //reused form manageBillController
            for (Bill bill : billDAO.getBillsByEmployee(emp)) {
                if(bill.getBillTime().getYear() >= dateFrom.getYear())
                    if(bill.getBillTime().getMonthValue() >= dateFrom.getMonthValue())
                        if(bill.getBillTime().getDayOfMonth() >= dateFrom.getDayOfMonth())
                            if(bill.getBillTime().getYear() <= dateTo.getYear())
                                if(bill.getBillTime().getMonthValue() <= dateTo.getMonthValue())
                                    if(bill.getBillTime().getDayOfMonth() <= dateTo.getDayOfMonth())
                                        filteredBills.add(bill);
            }

            performanceView.getTotalBillsTextField().setText(String.valueOf(filteredBills.size()));
            double totalCost = 0;
            double totalRevenue = 0;
            for(Bill bill : filteredBills) {
                totalCost += bill.getCost();
                totalRevenue += bill.getRevenue();
            }

            performanceView.getTotalCostTextField().setText(String.valueOf(totalCost));
            performanceView.getTotalRevenueTextField().setText(String.valueOf(totalRevenue));
            performanceView.getTotalProfitTextField().setText(String.valueOf(totalRevenue - totalCost));

        });
    }


}
