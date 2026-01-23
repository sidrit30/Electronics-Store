package View;

import Model.Users.Employee;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;

import static Main.Launcher.PATH;

public class WelcomeView extends VBox {
    public WelcomeView(Employee employee) {
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(80, 0, 0, 0));

        Label titleLabel = new Label("Hello, " + employee.getFullName());

        titleLabel.setFont(new Font("Verdana", 28));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #404436;");

        Label titleLabel1 = new Label("Welcome to Jupiter");
        titleLabel1.setId("welcome_label");
        titleLabel1.setFont(new Font("Verdana", 28));
        titleLabel1.setStyle("-fx-font-weight: bold; -fx-text-fill: #404436;");

        // Placeholder for Logo
        ImageView logoView = new ImageView(new Image(WelcomeView.class.getResourceAsStream("/images/jupiterLogo.png")));
        logoView.setFitWidth(180);
        logoView.setPreserveRatio(true);

        this.getChildren().addAll(titleLabel, logoView, titleLabel1);
    }
}
