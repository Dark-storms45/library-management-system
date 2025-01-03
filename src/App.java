import Utility.Display;
import Utility.db_Utilities;
import Utility.login_utils;
import java.sql.SQLException;
import java.util.Scanner;

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

        if (choice==1){
           try {
               login_utils.login();
           } catch (SQLException e) {
               e.printStackTrace();
           }
        }
        else if (choice==2){
            login_utils.register();
        }
        else if (choice==3){
            System.exit(0);
        }
        else{
            System.out.println("Invalid choice");
        }


    }
  
                public static void main(String[] args) throws SQLException {
            
            db_Utilities.dbConnection();
            // db_Utilities.creatTables();
            // Display.displayBox("Welcome to the Library Management System");
                 } 
                
                }

