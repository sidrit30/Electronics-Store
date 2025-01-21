package Main;

import Controller.LoginController;
import DAO.EmployeeDAO;
import DAO.SectorDAO;
import Model.Items.Item;
import Model.Items.Sector;
import Model.Users.Admin;
import Model.Users.Cashier;
import Model.Users.Employee;
import Model.Users.Manager;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    private static final Screen screen = Screen.getPrimary();
    public static final Rectangle2D VISUAL_BOUNDS = screen.getVisualBounds();

    public static void main(String[] args) {
//        Item cpu1 = new Item("Intel Core i9", "Computer CPUs", 500.0, 300.0, 50, "Intel", "High-performance CPU");
//        Item cpu2 = new Item("AMD Ryzen 9", "Computer CPUs", 450.0, 280.0, 60, "AMD", "Powerful CPU for gaming");
//        Item cpu3 = new Item("Intel Core i7", "Computer CPUs", 350.0, 220.0, 70, "Intel", "Efficient CPU for multitasking");
//
//        Item console1 = new Item("PlayStation 5", "Consoles", 499.99, 350.0, 100, "Sony", "Next-gen gaming console");
//        Item console2 = new Item("Xbox Series X", "Consoles", 499.99, 340.0, 90, "Microsoft Corporation", "Powerful gaming console");
//        Item console3 = new Item("Nintendo Switch", "Consoles", 299.99, 200.0, 120, "Nintendo", "Portable gaming console");
//
//        Item appliance1 = new Item("Samsung Refrigerator", "Kitchen Appliances", 1200.0, 800.0, 30, "Samsung", "High-capacity refrigerator");
//        Item appliance2 = new Item("LG Microwave", "Kitchen Appliances", 200.0, 150.0, 80, "LG", "Efficient microwave oven");
//        Item appliance3 = new Item("KitchenAid Mixer", "Kitchen Appliances", 350.0, 250.0, 40, "KitchenAid", "Versatile kitchen mixer");
//
//        Item smartphone = new Item("Samsung Galaxy S21", "Smartphones", 999.99, 600.0, 70, "Samsung", "High-end smartphone");
//
//        Sector cpuSector = new Sector("Computer Components");
//        cpuSector.addCategory("Computer CPUs");
//        cpuSector.addItem(cpu1);
//        cpuSector.addItem(cpu2);
//        cpuSector.addItem(cpu3);
//
//        Sector consoleSector = new Sector("Gaming");
//        consoleSector.addCategory("Consoles");
//        consoleSector.addItem(console1);
//        consoleSector.addItem(console2);
//        consoleSector.addItem(console3);
//
//        Sector applianceSector = new Sector("Home Appliances");
//        applianceSector.addCategory("Kitchen Appliances");
//        applianceSector.addItem(appliance1);
//        applianceSector.addItem(appliance2);
//        applianceSector.addItem(appliance3);
//
//        Sector smartphoneSector = new Sector("Mobile Devices");
//        smartphoneSector.addCategory("Smartphones");
//        smartphoneSector.addItem(smartphone);
//
//        SectorDAO sectorDAO = new SectorDAO();
//        sectorDAO.createSector(cpuSector);
//        sectorDAO.createSector(consoleSector);
//        sectorDAO.createSector(applianceSector);
//        sectorDAO.createSector(smartphoneSector);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        LoginController loginController = new LoginController();
        Scene scene = new Scene(loginController.getLoginPage(), 800, 600);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jupiter Electronics");
        primaryStage.show();
    }
}
