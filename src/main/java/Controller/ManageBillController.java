package Controller;

import DAO.BillDAO;
import DAO.EmployeeDAO;
import DAO.SectorDAO;
import Model.Bill;
import Model.Users.*;
import View.ManageBillView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    public ManageBillView getManageBillView() {
        return manageBillView;
    }

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
                    manageBillView.getTable().setItems(billDAO.getBillsBySector((employee).getSectorName()));
                }
                else
                    manageBillView.getTable().setItems(billDAO.getBillsByEmployee(employee));
                break;
            case MANAGER:
                if(employee.hasPermission(Permission.VIEW_SECTOR)) {
                    manageBillView.getSectorFilter().getItems().add("All Sectors");
                }
                manageBillView.getSectorFilter().getItems().addAll(((Manager) employee).getSectors());
                manageBillView.getTable().setItems(billDAO.getBillsBySectors(((Manager) employee).getSectors()));
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
        filterSector();
        ObservableList<Bill> billsInTable = FXCollections.observableArrayList(manageBillView.getTable().getItems());
        System.out.println(billsInTable);
        manageBillView.getTable().getItems().clear();
        //this looks atrocious, but I can't find a better way rn
        for (Bill bill : billsInTable) {
            if(bill.getBillTime().getYear() >= dateFrom.getYear())
                if(bill.getBillTime().getMonthValue() >= dateFrom.getMonthValue())
                    if(bill.getBillTime().getDayOfMonth() >= dateFrom.getDayOfMonth())
                        if(bill.getBillTime().getYear() <= dateTo.getYear())
                            if(bill.getBillTime().getMonthValue() <= dateTo.getMonthValue())
                                if(bill.getBillTime().getDayOfMonth() <= dateTo.getDayOfMonth())
                                    manageBillView.getTable().getItems().add(bill);
        }
    }

    private void filterSector() {
        String sectorFilter = manageBillView.getSectorFilter().getSelectionModel().getSelectedItem();
        manageBillView.getTable().getSelectionModel().clearSelection();
        if(sectorFilter == null) {
            return;
        }
        if(sectorFilter.equals("All Sectors")) {
            manageBillView.getTable().setItems(billDAO.getBills());
            manageBillView.getSectorFilter().getSelectionModel().clearSelection();
            return;
        }
        ObservableList<Bill> bills = FXCollections.observableArrayList(billDAO.getBillsBySector(sectorFilter));
        manageBillView.getTable().setItems(bills);
        manageBillView.getSectorFilter().getSelectionModel().clearSelection();
    }

    private void viewBillDetails() {
        if(manageBillView.getTable().getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Select a Bill");
            alert.show();
            return;
        }
        Stage popUpWindow = new Stage();
        String billDetails = manageBillView.getTable().getSelectionModel().getSelectedItem().printBill();
        Button ok = new Button("OK");
        VBox vBox = new VBox(10, new TextArea(billDetails), ok);
        popUpWindow.setTitle("Bill Details");
        popUpWindow.setScene(new Scene(vBox));
        popUpWindow.show();
        ok.setOnAction(e -> popUpWindow.close());

    }
}
