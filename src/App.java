import java.sql.SQLException;

import Utility.Display;
import Utility.Menu;
import Utility.Utilities;
import Utility.db_Utilities;

public class App {

    /* Main  function */

    public static void main(String[] args) throws SQLException {

        while (true) {
            App app = new App();
            db_Utilities.creatTables();
            Display.displayBox("Welcome to the Library Management System");
        Menu.main_menu();
            Utilities.clear_Screen();
        }
    }

}
