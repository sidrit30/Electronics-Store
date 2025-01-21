package View;

import Model.Users.Employee;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HomePage extends BorderPane {
    // Shared root layout
    private MenuBar menuBar;       // Shared MenuBar
    private Employee employee;

    private MenuItem homeMenuItem;
    private MenuItem statsItem;
    private MenuItem profileItem;
    private MenuItem aboutItem;
    private MenuItem logoutItem;
    private VBox sidebarHome = new VBox();



    public HomePage(Employee employee) {
        this.employee = employee;

        // Create MenuBar and add items
        menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");

        // Menu Items for Statistics, Profile, and Log Out
        Menu profileMenu = createStyledMenu("User Profile");
        Menu aboutMenu = createStyledMenu("About");
        Menu logoutMenu = createStyledMenu("Log Out");

        // MenuItems
        profileItem = createStyledMenuItem("User Profile");
        aboutItem = createStyledMenuItem("About");
        logoutItem = createStyledMenuItem("Log Out");


        // Add MenuItems to Menus
        profileMenu.getItems().add(profileItem);
        aboutMenu.getItems().add(aboutItem);
        logoutMenu.getItems().add(logoutItem);

        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(profileMenu, aboutMenu, logoutMenu);

        this.setTop(menuBar);

        this.setStyle("-fx-background-color: #fcf2d8;");

        sidebarHome.setPadding(new Insets(40));
        sidebarHome.setSpacing(25);
        sidebarHome.setStyle("-fx-background-color: #90614d;");
        sidebarHome.setPrefWidth(220);

        Label titleLabel = new Label("Hello, " + employee.getFullName() + "!");
        titleLabel.setFont(new Font("Verdana", 28));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #404436;");

        this.setLeft(sidebarHome);
    }

    // Method to create a styled menu
    private Menu createStyledMenu(String text) {
        Menu menu = new Menu(text);
        menu.setStyle("-fx-text-fill: #D2BD96; -fx-font-size: 14px;");
        return menu;
    }

    // Method to create a styled MenuItem
    private MenuItem createStyledMenuItem(String text) {
        MenuItem menuItem = new MenuItem(text);
        menuItem.setStyle("-fx-background-color: transparent; -fx-text-fill: #404436; -fx-font-size: 14px;");
        return menuItem;
    }

    public MenuItem getHomeMenuItem() {
        return homeMenuItem;
    }

    public MenuItem getStatsItem() {
        return statsItem;
    }

    public MenuItem getProfileItem() {
        return profileItem;
    }

    public MenuItem getAboutItem() {
        return aboutItem;
    }

    public MenuItem getLogoutItem() {
        return logoutItem;
    }

    public VBox getSidebarHome() {
        return sidebarHome;
    }
}
