package View;

import Model.Users.Cashier;
import Model.Users.Employee;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PerformanceView extends VBox {
    private final ComboBox<Employee> cashierComboBox = new ComboBox<>();
    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;
    private final Button searchButton = new Button("Search");
    private final Label totalBillsLabel;
    private final Label totalCostLabel;
    private final Label totalRevenueLabel;
    private final Label totalProfitLabel;
    private final TextField totalBillsTextField = new TextField();
    private final TextField totalCostTextField = new TextField();
    private final TextField totalRevenueTextField = new TextField();
    private final TextField totalProfitTextField = new TextField();

    public PerformanceView() {

        // Initialize UI components
        cashierComboBox.setPromptText("Select Cashier");

        startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Start Date");

        endDatePicker = new DatePicker();
        endDatePicker.setPromptText("End Date");

        totalBillsLabel = new Label("Total Bills: ");
        totalCostLabel = new Label("Total Cost: ");
        totalRevenueLabel = new Label("Total Revenue: ");
        totalProfitLabel = new Label("Total Profit: ");

        cashierComboBox.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #884135; -fx-font-size: 14px;");
        startDatePicker.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #884135; -fx-font-size: 14px;");
        endDatePicker.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #884135; -fx-font-size: 14px;");

        HBox hBox = new HBox(10, cashierComboBox, startDatePicker, endDatePicker, searchButton);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(totalBillsLabel, 0, 0);
        gridPane.add(totalBillsTextField, 1, 0);
        gridPane.add(totalCostLabel, 0, 1);
        gridPane.add(totalCostTextField, 1, 1);
        gridPane.add(totalRevenueLabel, 0, 2);
        gridPane.add(totalRevenueTextField, 1, 2);
        gridPane.add(totalProfitLabel, 0, 3);
        gridPane.add(totalProfitTextField, 1, 3);

        // Layout configuration
        setSpacing(10);
        setPadding(new Insets(10));
        getChildren().addAll(hBox, gridPane);

    }

    public ComboBox<Employee> getCashierComboBox() {
        return cashierComboBox;
    }

    public DatePicker getStartDatePicker() {
        return startDatePicker;
    }

    public DatePicker getEndDatePicker() {
        return endDatePicker;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public Label getTotalBillsLabel() {
        return totalBillsLabel;
    }

    public Label getTotalCostLabel() {
        return totalCostLabel;
    }

    public Label getTotalRevenueLabel() {
        return totalRevenueLabel;
    }

    public Label getTotalProfitLabel() {
        return totalProfitLabel;
    }

    public TextField getTotalBillsTextField() {
        return totalBillsTextField;
    }

    public TextField getTotalCostTextField() {
        return totalCostTextField;
    }

    public TextField getTotalRevenueTextField() {
        return totalRevenueTextField;
    }

    public TextField getTotalProfitTextField() {
        return totalProfitTextField;
    }
}
