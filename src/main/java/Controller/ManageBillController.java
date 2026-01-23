package Controller;

import DAO.BillDAO;
import DAO.ItemDAO;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class ManageBillController {

    private static final Logger LOGGER =
            Logger.getLogger(ManageBillController.class.getName());

    private Employee employee;
    private BillDAO billDAO;
    private ItemDAO itemDAO;
    private ManageBillView manageBillView;
    private ObservableList<Bill> bills;

    public ManageBillView getManageBillView() {
        return manageBillView;
    }

    //testing constructor
    public ManageBillController(Employee employee, BillDAO billDAO, ItemDAO itemDAO) {
        this.employee = employee;
        this.billDAO = billDAO;
        this.itemDAO = itemDAO;
    }

    public ManageBillController(Employee employee) {
        this.employee = employee;
        this.billDAO = new BillDAO();
        this.itemDAO = new ItemDAO();
        this.manageBillView = new ManageBillView(employee);

        loadData();
        manageBillView.getTable().setItems(bills);

        manageBillView.getViewDetailsButton().setOnAction(e -> viewBillDetails());
        manageBillView.getSectorFilter().setOnAction(e -> filterSector());
        manageBillView.getSearchButton().setOnAction(e -> searchDate());
        manageBillView.getSearchCashierButton().setOnAction(e -> searchCashier());
    }

    private void searchCashier() {
        String searchString = manageBillView.getSearchField().getText();
        ObservableList<Bill> filteredBills = FXCollections.observableArrayList();

        for (Bill bill : bills) {
            if (bill.getCashier().getFullName().toLowerCase()
                    .contains(searchString.toLowerCase())) {
                filteredBills.add(bill);
            }
        }

        manageBillView.getTable().setItems(filteredBills);
    }

    private void searchDate() {
        LocalDateTime dateFrom = manageBillView.getDateFrom().getValue().atStartOfDay();
        LocalDateTime dateTo = manageBillView.getDateTo().getValue().atStartOfDay();

        filterSector();

        ObservableList<Bill> billsInTable =
                FXCollections.observableArrayList(manageBillView.getTable().getItems());

        manageBillView.getTable().getItems().clear();

        for (Bill bill : billsInTable) {
            if (bill.getBillTime().isAfter(dateFrom) &&
                bill.getBillTime().isBefore(dateTo)) {
                manageBillView.getTable().getItems().add(bill);
            }
        }
    }

    private void filterSector() {
        String sectorFilter =
                manageBillView.getSectorFilter().getSelectionModel().getSelectedItem();

        LOGGER.info(sectorFilter);

        if ("All Sectors".equals(sectorFilter)) {
            manageBillView.getTable().setItems(billDAO.getBills());
        } else {
            ObservableList<Bill> filteredBills =
                    FXCollections.observableArrayList(
                            billDAO.getBillsBySector(sectorFilter)
                    );
            manageBillView.getTable().setItems(filteredBills);
        }
    }

    private void viewBillDetails() {
        if (manageBillView.getTable().getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Select a Bill");
            alert.show();
            return;
        }

        Stage popUpWindow = new Stage();
        String billDetails =
                manageBillView.getTable().getSelectionModel()
                        .getSelectedItem().printBill();

        Button ok = new Button("OK");
        VBox vBox = new VBox(10, new TextArea(billDetails), ok);

        popUpWindow.setTitle("Bill Details");
        popUpWindow.setScene(new Scene(vBox));
        popUpWindow.show();

        ok.setOnAction(e -> popUpWindow.close());
    }

    private void loadData() {
        if (employee instanceof Admin) {
            bills = FXCollections.observableArrayList(billDAO.getBills());
            manageBillView.getSectorFilter().getItems().add("All Sectors");
            manageBillView.getSectorFilter().getItems().addAll(itemDAO.getSectorNames());
        }

        if (employee instanceof Manager) {
            bills = FXCollections.observableArrayList(
                    billDAO.getBillsBySectors(((Manager) employee).getSectors())
            );
            manageBillView.getSectorFilter().getItems()
                    .addAll(((Manager) employee).getSectors());
        }

        if (employee instanceof Cashier) {
            bills = FXCollections.observableArrayList(
                    billDAO.getBillsByEmployee(employee)
            );
            manageBillView.getSectorFilter().setDisable(true);
        }
    }

    //rewritten for testing
    //TESTED
    public List<Bill> loadData(Employee employee) {
        List<Bill> billTest = null;
        if (employee instanceof Admin) {
            billTest = billDAO.getBills().stream().toList();
        }

        if (employee instanceof Manager) {
            billTest = billDAO.getBillsBySectors(((Manager) employee).getSectors()).stream().toList();
        }

        if (employee instanceof Cashier) {
            billTest = billDAO.getBillsByEmployee(employee).stream().toList();

        }
        return billTest;
    }
}

