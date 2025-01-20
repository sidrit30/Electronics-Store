package View;


import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class HomePage extends BorderPane {
    private Label title;

    public HomePage(String employeename) {
        title = new Label("Hello "+employeename);
        this.setCenter(title);
    }
}
