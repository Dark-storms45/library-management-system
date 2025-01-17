package Utility;

import java.sql.SQLException;
import java.util.Scanner;

import  services.books_services;
import services.member_services;
import services.reservation_services;
import services.transanction_services;



public class Menu {


    
 

    public static void main_menu() throws SQLException {
        String[] main_menu = {
            "login",
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
                System.exit(0);
            default ->
                System.out.println("Invalid choice");
        }

    }

    public static void user_menu() throws SQLException {
        String[] user_menu = {
            "Books",
            "members",
            "Transaction",
            " Reservation",
            "Logout"
        };
        Display.Display_menu(user_menu);
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your choice:");
        int choice = input.nextInt();

        switch (choice) {
            case 1 ->
                books_services.book_menu();
            case 2 ->
             member_services.member_menu();
             
            case 3 ->{
                transanction_services.transaction_menu();
            }
                case 4 ->{
                    reservation_services.reservation_menu();

                }
              
            case 5 ->
                System.exit(0);
            default ->
                System.out.println("Invalid choice");
        }
    }

    public static void Admin_menu() throws SQLException {

        String[] arr = {
            "users",
            "Books",
            "Transaction",
            "Staff",
            "reservation",
            "logout"
        };

        Display.Display_menu(arr);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your choice");
        int choice = sc.nextInt();
        while (true) {

            switch (choice) {
                case 1 -> {
                }
                case 2 -> {
                    books_services.book_menu();
                }
                case 3 -> {
                    transanction_services.transaction_menu();
                }
                case 4 -> {
                   /// notification_menu();
                }
                case 5 -> {
                    reservation_services.reservation_menu();
                }
                case 6 -> {
                    System.exit(0);
                }
                default ->
                    System.err.println("Invalid choice, enter a valid choice");
            }
        }

    }

    

    


}
