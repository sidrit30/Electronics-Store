package View;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class Buttons {
    private final Button[] buttons = {
            new Button("Create Bill"),
            new Button("View Bills"),
            new Button("Inventory Management"),
            new Button("Employee Management"),
            new Button("View Statistics"),
    };

    public Buttons() {
        buttons[0].setId("create_bill");
        buttons[0].setPrefWidth(150);
        buttons[1].setId("view_bill");
        buttons[1].setPrefWidth(150);
        buttons[2].setId("view_item");
        buttons[2].setPrefWidth(150);
        buttons[3].setId("view_sector");
        buttons[3].setPrefWidth(150);
        buttons[4].setId("performance_sector");
        buttons[4].setPrefWidth(150);
    }

    public ArrayList<Button> getButtons() {
        return new ArrayList<>(List.of(buttons));
    }

}
