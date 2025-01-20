package View;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class HomePage extends BorderPane {

    public HomePage() {
        MenuBar menuBar = new MenuBar();

        Menu homeMenu = new Menu("Home Page");
        MenuItem goToHome = new MenuItem("Go to Home");
        homeMenu.getItems().add(goToHome);

        Menu statisticsMenu = new Menu("Statistics");
        MenuItem viewStatistics = new MenuItem("View Statistics");
        statisticsMenu.getItems().add(viewStatistics);

        Menu userProfileMenu = new Menu("User Profile");
        MenuItem viewProfile = new MenuItem("View Profile");
        userProfileMenu.getItems().add(viewProfile);

        Menu logOutMenu = new Menu("Log Out");
        MenuItem logOut = new MenuItem("Log Out");
        logOutMenu.getItems().add(logOut);

        menuBar.getMenus().addAll(homeMenu, statisticsMenu, userProfileMenu, logOutMenu);

        this.setTop(menuBar);

    }

}
