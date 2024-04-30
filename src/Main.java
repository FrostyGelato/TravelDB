import controller.DBController;
import ui.EntitiesWindow;

public class Main {
    public static void main(String[] args) {
       DBController dbController = new DBController();
        dbController.start();

        // Uncomment to launch main window directly without logging in
     //   EntitiesWindow entities = new EntitiesWindow();
      //  entities.showFrame();
    }
}