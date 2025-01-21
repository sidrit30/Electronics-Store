package Controller;

import Model.Users.Employee;
import View.Buttons;
import View.HomePage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomePageController {
    private Employee employee;
    private HomePage homePage;
    private Buttons buttons;

    HomePageController(Employee employee) {
        this.employee = employee;
        this.homePage = new HomePage(employee);

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
        Scene scene = new Scene(new ManageEmployeeController(employee).getManageEmployeeTableView());
        Stage stage = (Stage) homePage.getScene().getWindow();
        stage.setScene(scene);
    }

    public HomePage getHomePage() {
        return homePage;
    }
}
