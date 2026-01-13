package Main;

import Controller.LoginController;
import DAO.EmployeeDAO;
import DAO.ItemDAO;
import Model.Users.Admin;
import Model.Items.Item;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;

public class Launcher extends Application {
    private static final Screen screen = Screen.getPrimary();
    public static final Rectangle2D VISUAL_BOUNDS = screen.getVisualBounds();
    public static final String PATH = "src/main/resources";

    public static void main(String[] args) {
        //checkDirectory();
        launch(args);
    }

    private static void checkDirectory() {
        if(new File(PATH).mkdirs()) {
            loadInitial();
        }
    }

    //load initial items and a single admin
    private static void loadInitial() {
        File test = new File(PATH + File.separator + "data");
        test.mkdirs();
        System.out.println(test);
        new File(PATH + File.separator + "Bills").mkdirs();
        EmployeeDAO dao = new EmployeeDAO();
        dao.createEmployee(new Admin("", "Admin", "admin", "admin", 1));
        Item[] demoItems = {
                new Item("Sony WH-1000XM4", "Headphones", 350.0, 200.0, 50, "Sony", "High-quality noise-canceling headphones", "Audio and Sound"),
                new Item("Bose QuietComfort 35 II", "Headphones", 300.0, 180.0, 45, "Bose", "Comfortable noise-canceling headphones", "Audio and Sound"),
                new Item("Apple AirPods Pro", "Earphones", 250.0, 150.0, 60, "Apple", "Wireless earphones with noise cancellation", "Audio and Sound"),
                new Item("Samsung Galaxy Buds Pro", "Earphones", 200.0, 120.0, 55, "Samsung", "Premium wireless earbuds", "Audio and Sound"),
                new Item("JBL Charge 4", "Speakers", 180.0, 100.0, 40, "JBL", "Portable Bluetooth speaker", "Audio and Sound"),
                new Item("Ultimate Ears Boom 3", "Speakers", 150.0, 90.0, 70, "Ultimate Ears", "Waterproof Bluetooth speaker", "Audio and Sound"),
                new Item("Sony SRS-XB43", "Speakers", 250.0, 140.0, 50, "Sony", "Extra Bass portable speaker", "Audio and Sound"),
                new Item("Bose SoundLink Revolve", "Speakers", 230.0, 130.0, 45, "Bose", "Portable Bluetooth speaker", "Audio and Sound"),
                new Item("Beats Powerbeats Pro", "Earphones", 200.0, 110.0, 65, "Beats", "High-performance wireless earphones", "Audio and Sound"),
                new Item("Sennheiser HD 450BT", "Headphones", 200.0, 120.0, 55, "Sennheiser", "Noise-canceling Bluetooth headphones", "Audio and Sound"),
                new Item("Oculus Quest 2", "VR Headsets", 400.0, 250.0, 30, "Oculus", "All-in-one VR headset", "Wearable and VR"),
                new Item("HTC Vive Pro", "VR Headsets", 800.0, 500.0, 20, "HTC", "Professional-grade VR headset", "Wearable and VR"),
                new Item("Apple Watch Series 6", "Smartwatches", 400.0, 220.0, 70, "Apple", "Advanced health features smartwatch", "Wearable and VR"),
                new Item("Samsung Galaxy Watch 3", "Smartwatches", 350.0, 200.0, 65, "Samsung", "Smartwatch with health monitoring", "Wearable and VR"),
                new Item("Garmin Forerunner 945", "Smartwatches", 500.0, 300.0, 40, "Garmin", "Premium GPS running watch", "Wearable and VR"),
                new Item("Sony PlayStation VR", "VR Headsets", 300.0, 180.0, 50, "Sony", "Virtual reality system for PlayStation", "Wearable and VR"),
                new Item("Fitbit Versa 3", "Smartwatches", 250.0, 150.0, 80, "Fitbit", "Health and fitness smartwatch", "Wearable and VR"),
                new Item("Valve Index", "VR Headsets", 1000.0, 600.0, 10, "Valve", "High-fidelity VR headset", "Wearable and VR"),
                new Item("Huawei Watch GT 2", "Smartwatches", 230.0, 140.0, 60, "Huawei", "Smartwatch with long battery life", "Wearable and VR"),
                new Item("Pico Neo 2", "VR Headsets", 700.0, 450.0, 20, "Pico", "Standalone VR headset", "Wearable and VR"),
                new Item("Canon EOS R5", "Cameras", 3900.0, 2500.0, 15, "Canon", "Professional mirrorless camera", "Cameras and Photography"),
                new Item("Nikon Z6 II", "Cameras", 2000.0, 1200.0, 20, "Nikon", "Full-frame mirrorless camera", "Cameras and Photography"),
                new Item("DJI Mavic Air 2", "Drones", 1000.0, 600.0, 25, "DJI", "Compact drone with high-quality camera", "Cameras and Photography"),
                new Item("GoPro HERO9", "Cameras", 450.0, 280.0, 50, "GoPro", "Action camera with 5K video", "Cameras and Photography"),
                new Item("Sony Alpha 7C", "Cameras", 1800.0, 1100.0, 18, "Sony", "Compact full-frame camera", "Cameras and Photography"),
                new Item("Canon EOS M50 Mark II", "Cameras", 700.0, 450.0, 35, "Canon", "Mirrorless camera with 4K video", "Cameras and Photography"),
                new Item("DJI Phantom 4 Pro", "Drones", 1500.0, 900.0, 10, "DJI", "Professional-grade drone", "Cameras and Photography"),
                new Item("Panasonic Lumix GH5", "Cameras", 1700.0, 1000.0, 22, "Panasonic", "Micro Four Thirds mirrorless camera", "Cameras and Photography"),
                new Item("Autel Evo II", "Drones", 1800.0, 1100.0, 12, "Autel", "8K drone camera", "Cameras and Photography"),
                new Item("Nikon D850", "Cameras", 3000.0, 2000.0, 15, "Nikon", "Professional DSLR camera", "Cameras and Photography")};
        ItemDAO itemDAO = new ItemDAO();
        for(Item item: demoItems)
            itemDAO.createItem(item);
        itemDAO.UpdateAll();


        Item cpu1 = new Item("Intel Core i9", "Computer CPUs", 500.0, 300.0, 50, "Intel", "High-performance CPU", "Computer Components");
        Item cpu2 = new Item("AMD Ryzen 9", "Computer CPUs", 450.0, 280.0, 60, "AMD", "Powerful CPU for gaming", "Computer Components");
        Item cpu3 = new Item("Intel Core i7", "Computer CPUs", 350.0, 220.0, 70, "Intel", "Efficient CPU for multitasking", "Computer Components");

        Item console1 = new Item("PlayStation 5", "Consoles", 499.99, 350.0, 100, "Sony", "Next-gen gaming console", "Gaming");
        Item console2 = new Item("Xbox Series X", "Consoles", 499.99, 340.0, 90, "Microsoft Corporation", "Powerful gaming console", "Gaming" );
        Item console3 = new Item("Nintendo Switch", "Consoles", 299.99, 200.0, 120, "Nintendo", "Portable gaming console", "Gaming");
        Item console4 = new Item("Dualsense", "Controllers", 79.99, 64.99, 50, "Sony", "Dualsense", "Gaming");

        Item appliance1 = new Item("Samsung Refrigerator", "Kitchen Appliances", 1200.0, 800.0, 30, "Samsung", "High-capacity refrigerator", "Home Appliances");
        Item appliance2 = new Item("LG Microwave", "Kitchen Appliances", 200.0, 150.0, 80, "LG", "Efficient microwave oven", "Home Appliances");
        Item appliance3 = new Item("KitchenAid Mixer", "Kitchen Appliances", 350.0, 250.0, 40, "KitchenAid", "Versatile kitchen mixer", "Home Appliances");

        Item smartphone = new Item("Samsung Galaxy S21", "Smartphones", 999.99, 600.0, 70, "Samsung", "High-end smartphone", "Mobile Devices");


        itemDAO.createItem(cpu1);
        itemDAO.createItem(cpu2);
        itemDAO.createItem(cpu3);
        itemDAO.createItem(console1);
        itemDAO.createItem(console2);
        itemDAO.createItem(console3);
        itemDAO.createItem(console4);
        itemDAO.createItem(appliance1);
        itemDAO.createItem(appliance2);
        itemDAO.createItem(appliance3);
        itemDAO.createItem(smartphone);
    }

    @Override
    public void start(Stage primaryStage) {
        LoginController loginController = new LoginController();
        Scene scene = new Scene(loginController.getLoginPage(), 800, 600);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jupiter Electronics");
        primaryStage.getIcons().add(new Image(Launcher.class.getResourceAsStream("/images/appIcon.jpg")));
        primaryStage.show();
    }
}
