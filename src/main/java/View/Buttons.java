package View;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Buttons {
    private final Button[] buttons = {
            new Button("Create Bill"),
            new Button("View Bills"),
            new Button("Manage Inventory"),
            new Button("Manage Staff"),
            new Button("View Statistics"),
            new Button("Home")
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
        buttons[5].setId("home");
        buttons[5].setPrefWidth(150);

        ImageView homeIcon = new ImageView(new Image("file:src/main/resources/images/home_icon.png"));
        homeIcon.setFitHeight(16);
        homeIcon.setFitWidth(16);

        buttons[5].setGraphic(homeIcon);
        buttons[5].setPrefWidth(150);

    }

    public ArrayList<Button> getButtons() {
        return new ArrayList<>(List.of(buttons));
    }

    public Button getHomeButton() {
        return buttons[5];
    }

}
