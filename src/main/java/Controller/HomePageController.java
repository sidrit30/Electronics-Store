package Controller;

import Main.Main;
import Model.Users.Admin;
import Model.Users.Cashier;
import Model.Users.Employee;
import Model.Users.Manager;
import View.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePageController {
    private Employee employee;
    private HomePage homePage;
    private Buttons buttons;

    HomePageController(Employee employee) {
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
        homePage.getLogoutItem().setOnAction(event -> logout());

        setEventHandlers();
    }

    private void logout() {
        Stage oldStage = (Stage) homePage.getScene().getWindow();
        oldStage.close();
        Main main = new Main();
        Stage stage = new Stage();
        main.start(stage);

    }

    private void setEventHandlers() {
        buttons.getButtons().get(0).setOnAction(event -> createBill());
        buttons.getButtons().get(1).setOnAction(event -> billManagement());

        buttons.getButtons().get(3).setOnAction(e -> employeeManagement());

    }

    private void createBill() {
        BorderPane createBillPane = new CreateBillController(employee).getCreateBillView();
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
