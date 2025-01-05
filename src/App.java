import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.text.Utilities;

import Utility.Display;
import Utility.db_Utilities;
import Utility.login_utils;
import Utility.Utilities;

public class App {

     /*Main menu function */
    public void main_menu() throws SQLException {
        String [] main_menu={
        "login",
        "Register",
        "Exit"};
        Display.Display_menu(main_menu);
        Scanner input = new Scanner(System.in); 
        System.out.println("Enter your choice:");
        int choice = input.nextInt();



        switch (choice) {
            case 1:
            try {
                login_utils.login();
            } catch (SQLException e) {
                e.printStackTrace();
            }
                break;
            case 2:
                login_utils.register();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }

    }
  
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

