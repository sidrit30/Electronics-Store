package Controller;

import Model.Users.Employee;
import View.HomePage;

public class HomePageController {
    Employee employee;
    HomePage homePage;

    public HomePage getHomePage() {
        return homePage;
    }

    HomePageController(Employee employee) {
        this.employee = employee;
        this.homePage = new HomePage(employee.getFullName());
    }
}
