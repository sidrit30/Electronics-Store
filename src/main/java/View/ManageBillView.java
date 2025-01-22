package View;

import Model.Bill;
import Model.Users.Employee;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ManageBillView extends VBox {
    private TableView<Bill> table = new TableView<>();
    private TableColumn<Bill, String> billIdCol;
    private TableColumn<Bill, String> billDateCol;
    private TableColumn<Bill, String> cashierCol;
    private TableColumn<Bill, String> totalRevenueCol;
    private TableColumn<Bill, String> costCol;

    private ObservableList<Bill> data;
    private ComboBox<String> sectorFilter = new ComboBox<>();
    private DatePicker dateFrom = new DatePicker();
    private DatePicker dateTo = new DatePicker();
    private HBox filterBox;
    private TextField searchField = new TextField();

    private Button viewDetailsButton = new Button("View Bill Details");
    private Button searchCashierButton = new Button("Search by Cashier");
    private Button searchButton = new Button("Search by Date");


    private Employee employee;

    public ManageBillView(Employee employee) {

        // TableView setup
        table.setPlaceholder(new Label("No content in table"));
        table.setEditable(false);

        // Columns
        billIdCol = new TableColumn<>("Bill ID");
        billIdCol.setCellValueFactory(new PropertyValueFactory<>("billID"));
        billIdCol.setStyle("-fx-alignment: CENTER;");

        billDateCol = new TableColumn<>("Bill Date");
        billDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        billDateCol.setStyle("-fx-alignment: CENTER;");

        cashierCol = new TableColumn<>("Cashier");
        cashierCol.setCellValueFactory(new PropertyValueFactory<>("cashier"));
        cashierCol.setStyle("-fx-alignment: CENTER;");

        totalRevenueCol = new TableColumn<>("Total Revenue");
        totalRevenueCol.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        setStyle("-fx-alignment: CENTER;");

        costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        setStyle("-fx-alignment: CENTER;");

        table.getColumns().add(billIdCol);
        table.getColumns().add(billDateCol);
        table.getColumns().add(cashierCol);
        table.getColumns().add(totalRevenueCol);
        table.getColumns().add(costCol);

        sectorFilter.setPromptText("Select sector");

        dateFrom.setPromptText("From:");
        dateTo.setPromptText("To:");

        // Layout
        filterBox = new HBox(10, sectorFilter, searchField, searchCashierButton ,new Label("From:"), dateFrom, new Label("To:"), dateTo, searchButton);
        filterBox.setPadding(new Insets(10));

        HBox buttonBox = new HBox(10, viewDetailsButton);
        buttonBox.setPadding(new Insets(10));

        VBox topBox = new VBox(10, filterBox, buttonBox);

        this.getChildren().addAll(topBox, table);
    }

    public TableView<Bill> getTable() {
        return table;
    }

    public TableColumn<Bill, String> getBillIdCol() {
        return billIdCol;
    }

    public TableColumn<Bill, String> getBillDateCol() {
        return billDateCol;
    }

    public TableColumn<Bill, String> getCashierCol() {
        return cashierCol;
    }

    public TableColumn<Bill, String> getTotalRevenueCol() {
        return totalRevenueCol;
    }

    public TableColumn<Bill, String> getCostCol() {
        return costCol;
    }

    public ObservableList<Bill> getData() {
        return data;
    }

    public ComboBox<String> getSectorFilter() {
        return sectorFilter;
    }

    public DatePicker getDateFrom() {
        return dateFrom;
    }

    public DatePicker getDateTo() {
        return dateTo;
    }

    public HBox getFilterBox() {
        return filterBox;
    }

    public Button getViewDetailsButton() {
        return viewDetailsButton;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public Button getSearchCashierButton() {
        return searchCashierButton;
    }

    public TextField getSearchField() {
        return searchField;
    }
}
