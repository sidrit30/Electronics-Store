package temp;

import Model.Users.Employee;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Temp extends BorderPane {

    private Stage primaryStage;
     // Shared root layout
    private MenuBar menuBar;       // Shared MenuBar
    private Employee employee;


    public Temp(Employee employee) {
        this.employee = employee;

        // Create MenuBar and add items
        menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: #d2cfda; -fx-text-fill: #000000;");

        // Menu Items for Statistics, Profile, and Log Out

        Menu statsMenu = createStyledMenu("Statistics");
        Menu profileMenu = createStyledMenu("User Profile");
        Menu logoutMenu = createStyledMenu("Log Out");

        // MenuItems
        MenuItem statsItem = createStyledMenuItem("Statistics");
        MenuItem profileItem = createStyledMenuItem("User Profile");
        MenuItem logoutItem = createStyledMenuItem("Log Out");

        // Add MenuItems to Menus
        statsMenu.getItems().add(statsItem);
        profileMenu.getItems().add(profileItem);
        logoutMenu.getItems().add(logoutItem);

        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(statsMenu, profileMenu, logoutMenu);

        // Shared root layout

        this.setTop(menuBar);
        VBox sidebarHome = new VBox();

        VBox centerContent = new VBox();
        centerContent.setSpacing(20);
        centerContent.setAlignment(Pos.TOP_CENTER);
        centerContent.setPadding(new Insets(80, 0, 0, 0));

        Label titleLabel = new Label("Welcome to Jupiter");
        titleLabel.setFont(new Font("Verdana", 28));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #404436;");

        // Placeholder for Logo
        ImageView logoView = new ImageView();
        try {
            Image logo = new Image("file:src/main/resources/images/jupiterLogo.png");
            logoView.setImage(logo);
            logoView.setFitWidth(180);
            logoView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Logo image not found.");
        }

        centerContent.getChildren().addAll(logoView, titleLabel);

        // Update the root layout with Home Page content
        this.setLeft(sidebarHome);
        this.setCenter(centerContent);

    }
    // Method to create a styled menu
    private Menu createStyledMenu(String text) {
        Menu menu = new Menu(text);
        menu.setStyle("-fx-text-fill: #404436; -fx-font-size: 14px;");
        return menu;
    }

    // Method to create a styled MenuItem
    private MenuItem createStyledMenuItem(String text) {
        MenuItem menuItem = new MenuItem(text);
        menuItem.setStyle("-fx-background-color: transparent; -fx-text-fill: #404436; -fx-font-size: 14px;");
        return menuItem;
    }

    // Method to switch back to the Home Page
    private void setHomePage(String role) {
        String[] buttonLabels;

        switch (role.toLowerCase()) {
            case "manager":
                buttonLabels = new String[]{
                        "Employee Management", "Stock Management", "Create Bill", "View Bill", "Item", "Performance Sector", "Performance All"
                };
                break;
            case "cashier":
                buttonLabels = new String[]{
                        "Create Bill", "View Bill", "View Item"
                };
                break;
            case "admin":
                buttonLabels = new String[]{
                        "Employee Management", "Stock Management", "Create Bill", "View Bill", "Item", "Performance Sector", "Performance All"
                };
                break;
            default:
                buttonLabels = new String[]{"Create Bill", "View Bill"}; // Default fallback
        }


    }

    // Method to open the User Profile page
//    private void openUserProfilePage() {
//        VBox sidebarProfile = createSidebar(new String[]{"Employee Management", "Stock Management", "Create Bill", "View Bill"});
//
//        VBox centerContentProfile = new VBox();
//        centerContentProfile.setSpacing(20);
//        centerContentProfile.setAlignment(Pos.TOP_CENTER);
//        centerContentProfile.setPadding(new Insets(80, 0, 0, 0));
//
//        Label nameLabel = new Label("Name:");
//        TextField nameField = new TextField("John Doe");
//        Label surnameLabel = new Label("Surname:");
//        TextField surnameField = new TextField("Doe");
//        Label usernameLabel = new Label("Username:");
//        TextField usernameField = new TextField("johndoe");
//        Label passwordLabel = new Label("Password:");
//        PasswordField passwordField = new PasswordField();
//        passwordField.setText("password123"); // Placeholder password
//        Label idLabel = new Label("ID:");
//        TextField idField = new TextField("12345");
//        idField.setEditable(false); // Make ID field uneditable
//        Label emailLabel = new Label("Email:");
//        TextField emailField = new TextField("john.doe@example.com");
//        Label phoneLabel = new Label("Phone:");
//        TextField phoneField = new TextField("+123456789");
//        Label addressLabel = new Label("Home Address:");
//        TextField addressField = new TextField("123 Jupiter Lane, Solar City");
//
//        centerContentProfile.getChildren().addAll(
//                nameLabel, nameField,
//                surnameLabel, surnameField,
//                usernameLabel, usernameField,
//                passwordLabel, passwordField,
//                idLabel, idField,
//                emailLabel, emailField,
//                phoneLabel, phoneField,
//                addressLabel, addressField
//        );
//
//        // Update the root layout with User Profile content
//        rootLayout.setLeft(sidebarProfile);
//        rootLayout.setCenter(centerContentProfile);
//    }

    // Helper method to create a styled sidebar with buttons
    private VBox createSidebar(String[] buttonLabels) {
        VBox sidebar = new VBox();
        sidebar.setPadding(new Insets(10));
        sidebar.setSpacing(15);
        sidebar.setStyle("-fx-background-color: #d39c7e;");
        sidebar.setPrefWidth(200);

        for (String label : buttonLabels) {
            Button button = new Button(label);
            button.setStyle("-fx-font-size: 16px; -fx-background-color: #6c757d; -fx-text-fill: white;");
            button.setPrefWidth(Double.MAX_VALUE);
            button.setPrefHeight(40);
            sidebar.getChildren().add(button);
        }

        return sidebar;
    }
}
