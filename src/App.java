import java.sql.SQLException;
import java.util.Scanner;

import Utility.Display;
import Utility.Utilities;
import Utility.db_Utilities;
import Utility.login_utils;

public class App {

    /* Main menu function */

    public static void main(String[] args) throws SQLException {

        while (true) {
            App app = new App();
            db_Utilities.creatTables();
            Display.displayBox("Welcome to the Library Management System");
            app.main_menu();
            Utilities.clear_Screen();
        }
    }

}
