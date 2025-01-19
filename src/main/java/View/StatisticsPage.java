package View;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatisticsPage extends VBox {
    private Label title;
    private VBox billsBox;

    public StatisticsPage() {
        setSpacing(10);
        setPadding(new Insets(10));
        title = new Label();
        billsBox = new VBox();
        billsBox.setSpacing(10);
        getChildren().addAll(title, billsBox);
    }

    public void setTitle(String text) {
        title.setText(text);
    }

    public void addBill(String billDetails) {
        billsBox.getChildren().add(new Label(billDetails));
    }

    public void clearBills() {
        billsBox.getChildren().clear();
    }
}