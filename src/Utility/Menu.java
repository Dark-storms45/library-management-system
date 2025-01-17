package Utility;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import  services.books_services;
import services.member_services;
import services.reservation_services;
import services.transanction_services;
import  services.librant_services;


public class Menu {

    private static final Scanner  input = new Scanner(System.in);


    public static void main_menu() throws SQLException {
        String[] main_menu = {
                "login",
                "Exit"};

        while (true) {
            Display.Display_menu(main_menu);
            System.out.println("Enter your choice:");

            int choice = -1; // Default invalid value
            while (true) {
                try {
                    // Validate input as an integer
                    if (input.hasNextInt()) {
                        choice = input.nextInt();
                        input.nextLine(); // Clear the buffer
                        break; // Exit the loop once valid input is received
                    } else {
                        System.out.println("Invalid input. Please enter a number:");
                        input.nextLine(); // Consume invalid input
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number:");
                    input.nextLine(); // Clear current invalid input
                }
            }
                switch (choice) {
                    case 1 -> {
                        try {

                            login_utils.login();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    case 2 -> System.exit(0);
                    default -> System.out.println("Invalid choice");
                }


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
        while (true) {
            Display.Display_menu(user_menu);
            System.out.println("Enter your choice:");

            int choice = -1; // Default invalid value
            while (true) {
                try {
                    // Validate input as an integer
                    if (input.hasNextInt()) {
                        choice = input.nextInt();
                        input.nextLine(); // Clear the buffer
                        break; // Exit the loop once valid input is received
                    } else {
                        System.out.println("Invalid input. Please enter a number:");
                        input.nextLine(); // Consume invalid input
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number:");
                    input.nextLine(); // Clear current invalid input
                }


                }
                switch (choice) {
                case 1 -> {
                    books_services.book_menu();

                }
                case 2 -> member_services.member_menu();

                case 3 -> {
                    transanction_services.transaction_menu();
                }
                case 4 -> {
                    reservation_services.reservation_menu();

                }

                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
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
        while (true) {
            Display.Display_menu(arr);

            System.out.println("Enter your choice:");

            int choice = -1; // Default invalid value
            while (true) {
                try {
                    // Validate input as an integer
                    if (input.hasNextInt()) {
                        choice = input.nextInt();
                        input.nextLine(); // Clear the buffer
                        break; // Exit the loop once valid input is received
                    } else {
                        System.out.println("Invalid input. Please enter a number:");
                        input.nextLine(); // Consume invalid input
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number:");
                    input.nextLine(); // Clear current invalid input
                }

            }

                switch (choice) {
                    case 1 -> {
                        member_services.member_menu();
                    }
                    case 2 -> {

                        books_services.book_menu();


                    }
                    case 3 -> {
                        transanction_services.transaction_menu();
                    }
                    case 4 -> {
                        librant_services.librant_Servicemenu();
                    }

                    case 5 -> {
                        reservation_services.reservation_menu();
                    }
                    case 6 -> {
                        System.exit(0);

                    }
                    default -> System.err.println("Invalid choice, enter a valid choice");



            }
            }


        }

    }








