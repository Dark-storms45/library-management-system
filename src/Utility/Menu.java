package Utility;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    private String Menu_name;

    public Menu(String Menu_name) {
        this.Menu_name = Menu_name;
    }

    public void display_menue() {
        Scanner sc = new Scanner(System.in);
        Display.displayBox("Welcome to " + getMenu_name() + "menu");
        System.out.println();

        System.out.println("Enter your choice");
        int user_input = sc.nextInt();

        String[] arr = {
            "Add" + getMenu_name(),
            "Remove" + getMenu_name(),
            "update a" + getMenu_name()+"content",
          
          };

        switch (user_input) {

            case 1 -> {

            }
            case 2 -> {

            }
            case 3 -> {

            }

        }

    }

    public String getMenu_name() {
        return Menu_name;
    }

    public void setMenu_name(String name) {

        this.Menu_name = name;

    }

    public void main_menu() throws SQLException {
        String[] main_menu = {
            "login",
            "Register",
            "Exit"};
        Display.Display_menu(main_menu);
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your choice:");
        int choice = input.nextInt();

        switch (choice) {
            case 1 -> {
                try {
                    login_utils.login();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 2 ->
                login_utils.register();
            case 3 ->
                System.exit(0);
            default ->
                System.out.println("Invalid choice");
        }

    }

    public static void user_menu() {
        String[] user_menu = {
            "Books",
            " Reservation",
            "Transaction",
            "Notification",
            "Logout"
        };
        Display.Display_menu(user_menu);
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your choice:");
        int choice = input.nextInt();

        switch (choice) {
            case 1 ->
                System.out.println("Add Book");
            case 2 ->
                System.out.println("Remove Book");
            case 3 ->
                System.out.println("Process Transaction");
            case 4 ->
                System.exit(0);
            default ->
                System.out.println("Invalid choice");
        }
    }

    public static void Admin_menu() {

        String[] arr = {
            "users",
            "Books",
            "Transaction",
            "Notification",
            "complains",
            "logout"
        };

        Display.Display_menu(arr);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your choice");
        int choice = sc.nextInt();
        while (true) {

            switch (choice) {
                case 1 -> {
                    // Code for Books
                }
                case 2 -> {
                    // Code for Users
                }
                case 3 -> {
                    // Code for Transactions
                }
                case 4 -> {
                    // Code for Notification
                }
                case 5 -> {
                    // Code for Complains
                }
                case 6 -> {
                    // Code for Logout
                }
                default ->
                    System.err.println("Invalid choice, enter a valid choice");
            }
        }

    }

}
