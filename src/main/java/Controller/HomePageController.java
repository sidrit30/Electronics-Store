package Controller;

import Model.Users.Employee;
import View.HomePage;

public class HomePageController {
    Employee employee;
    HomePage homePage;

    HomePageController(Employee employee) {
        this.employee = employee;
        this.homePage = new HomePage();
    }
}
