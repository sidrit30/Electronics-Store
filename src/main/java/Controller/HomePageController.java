package Controller;

import Main.Launcher;
import Model.Users.Cashier;
import Model.Users.Employee;
import View.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePageController {
    private Employee employee;
    private HomePage homePage;
    private Buttons buttons;

    public HomePageController(Employee employee) {
        this.employee = employee;
        this.homePage = new HomePage(employee);
        buttons = new Buttons();
        welcomeView();

        for(Button button : buttons.getButtons()) {
            if(!(employee instanceof Cashier) && button.getId().equals("create_bill")) {
                continue;
            }
            if(employee.getPermissions().toString().toLowerCase().contains(button.getId())) {
                homePage.getSidebarHome().getChildren().add(button);
            }
        }


        homePage.getSidebarHome().getChildren().add(buttons.getHomeButton());
        homePage.getProfileItem().setOnAction(event -> profile());
        homePage.getLogoutItem().setOnAction(event -> logout());

        setEventHandlers();
    }

    private void profile() {
        GridPane gridPane = new ProfileController(employee).getView();
        homePage.setCenter(gridPane);
        buttons.getHomeButton().setVisible(true);
        buttons.getHomeButton().setOnAction(e -> welcomeView());
    }

    private void logout() {
        Stage oldStage = (Stage) homePage.getScene().getWindow();
        oldStage.close();
        Launcher main = new Launcher();
        Stage stage = new Stage();
        main.start(stage);

    }

    private void setEventHandlers() {
        buttons.getButtons().get(0).setOnAction(event -> createBill());
        buttons.getButtons().get(1).setOnAction(event -> billManagement());
        buttons.getButtons().get(2).setOnAction(event -> inventoryManagement());
        buttons.getButtons().get(3).setOnAction(e -> employeeManagement());
        buttons.getButtons().get(4).setOnAction(event -> performance());
    }

    private void performance() {
        VBox vBox = new PerformanceController(employee).getPerformanceView();
        homePage.setCenter(vBox);
        buttons.getHomeButton().setVisible(true);
        buttons.getHomeButton().setOnAction(e -> welcomeView());
    }

    private void inventoryManagement() {
        VBox vBox = new ManageInventoryController(employee).getManageInventoryView();
        homePage.setCenter(vBox);
        buttons.getHomeButton().setVisible(true);
        buttons.getHomeButton().setOnAction(e -> welcomeView());
    }

    private void createBill() {
        CreateBillController controller = new CreateBillController(employee);
        BorderPane createBillPane = controller.getCreateBillView();
        homePage.setCenter(createBillPane);
        buttons.getHomeButton().setVisible(true);
        buttons.getHomeButton().setOnAction(e -> welcomeView());
    }

    private void billManagement() {
        VBox billManagement = new ManageBillController(employee).getManageBillView();
        homePage.setCenter(billManagement);
        buttons.getHomeButton().setVisible(true);
        buttons.getHomeButton().setOnAction(e -> welcomeView());
    }

    private void employeeManagement() {
        VBox employeeManagement = new ManageEmployeeController(employee).getManageEmployeeTableView();
        homePage.setCenter(employeeManagement);
        buttons.getHomeButton().setVisible(true);
        buttons.getHomeButton().setOnAction(e -> welcomeView());

    }

    private void welcomeView() {
        this.homePage.setCenter(new WelcomeView(employee));
        buttons.getHomeButton().setVisible(false);
    }


    public HomePage getHomePage() {
        return homePage;
    }
}

