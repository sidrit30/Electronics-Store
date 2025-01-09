import View.LoginPage;
import Logic.Staff;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main extends Application {
    public static Staff staff;
    public static void main(String[] args) {
        try(
                ObjectInputStream input = new ObjectInputStream(ClassLoader.getSystemClassLoader().getResourceAsStream("ProgramData/staff.dat"));
        ){
            staff = (Staff) input.readObject();
        }
        catch(IOException e){
            System.out.println("Trouble reading staff.dat");
            System.out.println("Check resources directory and try again");
        } catch (ClassNotFoundException e) {
            System.out.println("Idk know");
        }

        launch();
        try (
                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("src/main/resources/staff.dat"));
        ) {
            output.writeObject(staff);
            System.out.println("Saved staff");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage stage) {
        new LoginPage().show(stage);

    }
}
