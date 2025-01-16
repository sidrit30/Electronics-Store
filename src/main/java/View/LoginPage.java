package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginPage {
    private Stage primaryStage;
    private TextField textField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button loginButton = new Button("Login");

    public TextField getTextField() {
        return textField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public LoginPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        show();
    }

    public void show() {
        Image backgroundImage = new Image("file:src/main/resources/images/background.jpg");

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(bgImage));


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        CornerRadii cornerRadii = new CornerRadii(10);
        BackgroundFill backgroundFill = new BackgroundFill(Color.DARKORANGE, cornerRadii, Insets.EMPTY);
        gridPane.setBackground(new Background(backgroundFill));
        gridPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        Label username = new Label("Username");
        username.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");

        styleTextField(textField);

        Label password = new Label("Password");
        password.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        styleTextField(passwordField);

        styleButton(loginButton, Color.DARKORANGE, Color.BLACK, 10);
        loginButton.disableProperty().bind(textField.textProperty().isEmpty().or(passwordField.textProperty().isEmpty()));

        gridPane.add(username, 0, 0);
        gridPane.add(textField, 1, 0);
        gridPane.add(password, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);

        Label titleLabel = new Label("Welcome to our application");
        titleLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        Label footerLabel = new Label("@ 2025 our Company");
        footerLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");

        StackPane centerPane = new StackPane(gridPane);
        centerPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        centerPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
        centerPane.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(titleLabel, centerPane, footerLabel);
        vBox.setAlignment(Pos.CENTER);

        borderPane.setCenter(vBox);

        HBox.setMargin(titleLabel, new Insets(10));
        HBox.setMargin(footerLabel, new Insets(10));

        Scene scene = new Scene(borderPane, 400, 300);
        assert primaryStage != null;
        primaryStage.setResizable(false);
        primaryStage.setTitle("Log-In");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void styleTextField(TextField textField) {
        textField.setStyle(
                "-fx-background-color: orange;" +
                        "-fx-text-fill: black;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 2;" +
                        "-fx-padding: 5 10 5 10;");
    }

    private void styleButton(Button button, Color backgroundColor, Color borderColor, double borderRadius) {
        button.setStyle("-fx-background-color: " + toRgbString(backgroundColor) + ";" + "-fx-text-fill: black;" + "-fx-background-radius: " + borderRadius + ";" +
                "-fx-border-radius: " + borderRadius + ";" + "-fx-border-color: " + toRgbString(borderColor) + ";" + "-fx-border-width: 2;" + "-fx-padding: 5 15 5 15;");
    }

    private String toRgbString(Color color) {
        return "rgb(" + (int) (color.getRed() * 255) + "," + (int) (color.getGreen() * 255) + "," + (int) (color.getBlue() * 255) + ")";
    }
}