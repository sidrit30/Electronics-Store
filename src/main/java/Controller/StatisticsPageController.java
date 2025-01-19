package Controller;
import Model.Bill;
import Model.Users.Admin;
import Model.Users.Cashier;
import Model.Users.Employee;
import Model.Users.Manager;
import View.LoginPage;
import View.StatisticsPage;

import java.util.List;
import java.util.stream.Collectors;

public class StatisticsPageController {
    private StatisticsPage view;
    private List<Bill> allBills;
    private StatisticsPage statisticsPage;

    public StatisticsPageController() {}

    public StatisticsPage getStatisticsPage() {
        return statisticsPage;
    }
    
    public StatisticsPageController(StatisticsPage view, List<Bill> allBills) {
        this.view = view;
        this.allBills = allBills;
    }

    public void loadStatistics(Employee user) {
        view.clearBills();
        if (user instanceof Cashier) {
            loadCashierBills((Cashier) user);
        } else if (user instanceof Manager) {
            loadManagerBills((Manager) user);
        } else if (user instanceof Admin) {
            loadAdminBills();
        }
    }

    private void loadCashierBills(Cashier cashier) {
        List<Bill> cashierBills = allBills.stream()
                .filter(bill -> bill.getCashier().equals(cashier))
                .collect(Collectors.toList());

        view.setTitle("Bills Created by Cashier: " + cashier.getFullName());

        for (Bill bill : cashierBills) {
            view.addBill(bill.printBill());
        }
    }

    private void loadManagerBills(Manager manager) {
        List<Bill> managerBills = allBills.stream()
                .filter(bill -> manager.getSectors().contains(((Cashier) bill.getCashier()).getSectorName()))
                .collect(Collectors.toList());

        view.setTitle("Bills in Manager's Sectors: " + manager.getFullName());

        for (Bill bill : managerBills) {
            view.addBill(bill.printBill());
        }
    }

    private void loadAdminBills() {
        view.setTitle("All Bills");

        for (Bill bill : allBills) {
            view.addBill(bill.printBill());
        }
    }
}