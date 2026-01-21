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
        LocalDate dateFrom = manageBillView.getDateFrom().getValue();
        LocalDate dateTo = manageBillView.getDateTo().getValue();

        filterSector();

        ObservableList<Bill> billsInTable =
                FXCollections.observableArrayList(manageBillView.getTable().getItems());

        manageBillView.getTable().getItems().clear();

        for (Bill bill : billsInTable) {
            if (bill.getBillTime().getYear() >= dateFrom.getYear()
                    && bill.getBillTime().getMonthValue() >= dateFrom.getMonthValue()
                    && bill.getBillTime().getDayOfMonth() >= dateFrom.getDayOfMonth()
                    && bill.getBillTime().getYear() <= dateTo.getYear()
                    && bill.getBillTime().getMonthValue() <= dateTo.getMonthValue()
                    && bill.getBillTime().getDayOfMonth() <= dateTo.getDayOfMonth()) {

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
}

