package services;

import java.sql.SQLException;
import java.util.*;

import Utility.Utilities;
import Utility.Display;
import Utility.db_Utilities;

public class books_services {
    private static final Scanner input = new Scanner(System.in);

    public void add_book() {
        HashMap<String, Object> books = new HashMap<>();
        System.out.println("Enter the book ISBN");
        String isbn = input.nextLine();
        books.put("ISBN", isbn);
        System.out.println("Enter the book title");
        String title = input.nextLine();
        books.put("Title", title);
        System.out.println("Enter the book author");
        String author = input.nextLine();
        books.put("Author", author);
        System.out.println("Enter the publisher");
        String publisher = input.nextLine();
        books.put("Publisher", publisher);
        System.out.println("Enter publication year YY/MM/DD");
        String year = input.nextLine();
        books.put("publicationYear", year);
        System.out.println("Enter the book genre");
        String genre = input.nextLine();
        books.put("Genre", genre);
        System.out.println("Enter the book Quantity");
        String Quantity = input.nextLine();
        books.put("Quantity", genre);

        try {
            db_Utilities.add_record(books, "books");

        } catch (SQLException e) {
            System.out.println("An error has occur" + e.getMessage());
        }


    }

    public void delete_book() throws SQLException {
        HashMap<String, List<String>> record = db_Utilities.fetchAllRecords("books", "","");
        if (record.isEmpty()) {
            System.out.println("NO RECORD WAS FOUND IN THIS TABLE");
        } else {
            Display.Display_tables(record);
            System.out.println("");
            System.out.println("Enter the Isbn of the book u want to delete");

            String isbn = input.nextLine();

            db_Utilities.delete_record("books ", isbn);


        }


    }

    public void update_book() throws SQLException {
        HashMap<String, List<String>> record = new HashMap<>();
        record = db_Utilities.fetchAllRecords("books", "","");
        if (record.isEmpty()) {
            System.out.println("No data found in this table ");
        } else {
            Display.Display_tables(record);
            for (int i = 0; i < 3; i++) {
                System.out.println("");
            }
            System.out.println("Enter the book to be modified by  specifying it's id ");

            int id = input.nextInt();
            String columnName[] = {"isbn", "title", "author", "publisher", "publicationyear", "genre","Quantity"};
            String column;


            while (true) {
                System.out.println("Enter the column name to be modified as shown on the table");
                column = input.nextLine();
                boolean validColumn = false;
                for (String col : columnName) {
                    if (col.equalsIgnoreCase(column.toLowerCase())) {
                        validColumn = true;
                        break;
                    }
                }
                if (validColumn) {
                    break;
                } else {
                    System.err.println("No such column found");
                    Utilities.clear_Screen();
                }
            }

            System.out.println("Enter the new value ");
            String value = input.nextLine();

            HashMap<String, Object> newrecord = new HashMap<>();
            newrecord.put(column, value);
            db_Utilities.update_record(newrecord, "books", id);

        }
    }

    public void search_book() throws SQLException {
        System.out.println(" Enter the  title   of the book you want to search");
        Scanner sc = new Scanner(System.in);
        String data = sc.nextLine();
        Display.Display_tables(db_Utilities.fetchAllRecords("Books",data,"Title"));

    }

    public static void book_menu() throws SQLException {

        String[] book_menu = {
                "Add Book",
                "Remove Book",
                "Update Book",
                "View Books",
                "search book",
                "Back"
        };

        int sec;
        Scanner input = new Scanner(System.in);
        while (true) {
            sec = 3000;
            Display.Display_menu(book_menu);

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
                        new books_services().add_book();
                    }
                    case 2 -> {
                        new books_services().delete_book();
                    }
                    case 3 -> {
                        new books_services().update_book();
                    }
                    case 4 -> {
                        Display.Display_tables(db_Utilities.fetchAllRecords("books", "",""));
                    }
                    case 5 -> {
                        new books_services().search_book();
                    }
                    case 6 -> {
                        return;
                    }

                    default -> {
                        System.out.println("Invalid choice");
                    }
                }


                Utilities.sleep(sec);




        }
    }
}