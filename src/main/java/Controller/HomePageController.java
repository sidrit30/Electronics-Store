package Controller;

import Model.Users.Employee;
import View.Buttons;
import View.HomePage;
import View.WelcomeView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class HomePageController {
    private Employee employee;
    private HomePage homePage;
    private Buttons buttons;

    HomePageController(Employee employee) {
        this.employee = employee;
        this.homePage = new HomePage(employee);
        this.homePage.setCenter(new WelcomeView(employee));

        buttons = new Buttons();
        for(Button button : buttons.getButtons()) {
            if(employee.getPermissions().toString().toLowerCase().contains(button.getId())) {
                homePage.getSidebarHome().getChildren().add(button);
            }
        }

        setEventHandlers();
    }

    private void setEventHandlers() {
        buttons.getButtons().get(3).setOnAction(e -> employeeManagement());
    }

    private void employeeManagement() {
        VBox employeManagement = new ManageEmployeeController(employee).getManageEmployeeTableView();
        homePage.setCenter(employeManagement);
    }


    public HomePage getHomePage() {
        return homePage;
    }
}
