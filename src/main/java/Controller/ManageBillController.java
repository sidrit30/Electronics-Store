package Controller;

import DAO.BillDAO;
import DAO.EmployeeDAO;
import DAO.SectorDAO;
import Model.Bill;
import Model.Users.*;
import View.ManageBillView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ManageBillController {
    private Employee employee;
    private BillDAO billDAO;
    private SectorDAO sectorDAO;
    private EmployeeDAO employeeDAO;
    private ManageBillView manageBillView;

    public ManageBillController(Employee employee) {
        this.employee = employee;
        this.billDAO = new BillDAO();
        this.sectorDAO = new SectorDAO();
        this.employeeDAO = new EmployeeDAO();
        this.manageBillView = new ManageBillView(employee);

        ObservableList<String> sectorNames = FXCollections.observableArrayList();

        switch (employee.getRole()) {
            case CASHIER:
                if(employee.hasPermission(Permission.VIEW_SECTOR)) {
                    //a bit too convoluted but I swear it makes sense
                    manageBillView.getTable().setItems(billDAO.getBillsBySector(((Cashier) employee).getSectorName()));
                    break;
                }
                manageBillView.getTable().setItems(billDAO.getBillsByEmployee(employee));
                break;
            case MANAGER:
                manageBillView.getSectorFilter().getItems().addAll(((Manager) employee).getSectors());
                if(employee.hasPermission(Permission.VIEW_SECTOR)) {
                    manageBillView.getTable().setItems(billDAO.getBillsBySectors(((Manager) employee).getSectors()));
                    break;
                }
                break;
            case ADMIN:
                manageBillView.getSectorFilter().getItems().add("All Sectors");
                manageBillView.getSectorFilter().getItems().addAll(sectorDAO.getSectorNames());
                manageBillView.getTable().setItems(billDAO.getBills());
                break;
        }

        manageBillView.getViewDetailsButton().setOnAction(e -> viewBillDetails());
        manageBillView.getSectorFilter().setOnAction(e -> filterSector());
        manageBillView.getSearchButton().setOnAction(e -> searchDate());
    }

    private void searchDate() {
        LocalDate dateFrom = manageBillView.getDateFrom().getValue();
        LocalDate dateTo = manageBillView.getDateTo().getValue();
        for (Bill bill : manageBillView.getTable().getItems()) {
            if(!(bill.getBillTime().getDayOfMonth() >= dateFrom.getDayOfMonth() && bill.getBillTime().getMonthValue() >=
                    dateFrom.getMonthValue() && bill.getBillTime().getYear() >= dateFrom.getYear())) {
                if (!(bill.getBillTime().getDayOfMonth() <= dateFrom.getDayOfMonth() && bill.getBillTime().getMonthValue() <=
                        dateTo.getMonthValue() && bill.getBillTime().getYear() <= dateTo.getYear()))
                    manageBillView.getTable().getItems().remove(bill);
            }
        }
    }

    private void filterSector() {
        String sectorFilter = manageBillView.getSectorFilter().getSelectionModel().getSelectedItem();
        manageBillView.getTable().getSelectionModel().clearSelection();
        if(sectorFilter.equals("All Sectors")) {
            manageBillView.getTable().getSelectionModel().clearSelection();
            manageBillView.getTable().setItems(billDAO.getBills());
            return;
        }
        manageBillView.getTable().setItems(billDAO.getBillsBySector(sectorFilter));
        manageBillView.getSectorFilter().getSelectionModel().clearSelection();
    }

    private void viewBillDetails() {
        Stage popUpWindow = new Stage();
        String billDetails = manageBillView.getTable().getSelectionModel().getSelectedItem().printBill();
        Button ok = new Button("OK");
        VBox vBox = new VBox(10, new TextArea(billDetails), ok);
        ok.setOnAction(e -> popUpWindow.close());

    }
}
