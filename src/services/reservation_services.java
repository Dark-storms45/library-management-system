package services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Utility.Display;
import Utility.Utilities;
import Utility.db_Utilities;

public class reservation_services {

    public static boolean isBookInStock(String isbn) throws SQLException {
        HashMap<String, List<String>> bookRecord = db_Utilities.fetchAllRecords("books", isbn);
        if (!bookRecord.isEmpty()) {
            int currentQuantity = Integer.parseInt(bookRecord.get("Quantity").get(0));
            return currentQuantity > 0;
        }
        return false;
    }



public static  void add_reservation() {
    HashMap<String, String> reservation = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    System.out.println("Enter the reservation ID>>");
    String reservationId = sc.nextLine();
    reservation.put("ReservationID", reservationId);
    System.out.println("Enter the book ISBN>>");
    String isbn = sc.nextLine();
    reservation.put("BookISBN", isbn);
    System.out.println("Enter the member ID>>");
    String memberId = sc.nextLine();
    reservation.put("MemberID", memberId);
    System.out.println("Enter the reservation date YY/MM/DD>>");
    String date = sc.nextLine();
    reservation.put("Date", date);
    try {
        if (isBookInStock(isbn)) {
            db_Utilities.add_record(reservation, "reservations");
            update_book_quantity(isbn, -1); // Decrease book quantity by 1
        } else {
            System.out.println("Book out of stock");
        }
    } catch (SQLException e) {
        System.out.println("An error has occurred: " + e.getMessage());
    }
    sc.close();
    try {
        db_Utilities.add_record(reservation, "reservations");
        update_book_quantity(isbn, -1); // Decrease book quantity by 1
    } catch (SQLException e) {
        System.out.println("An error has occurred: " + e.getMessage());
    }
}

public static void update_reservation() throws SQLException {
    HashMap<String, List<String>> record = db_Utilities.fetchAllRecords("reservations", "");
    if (record.isEmpty()) {
        System.out.println("No data found in this table");
    } else {
        Display.Display_tables(record);
        for (int i = 0; i < 3; i++) {
            System.out.println("");
        }
        System.out.println("Enter the reservation to be modified by specifying the ID");
        Scanner sc = new Scanner(System.in);
        String reservationId = sc.nextLine();
        String columnName[] = {"ReservationID", "BookISBN", "MemberID", "Date"};
        String column;

        while (true) {
            System.out.println("Enter the column name to be modified as shown on the table>>");
            column = sc.nextLine();
            boolean validColumn = false;
            for (String col : columnName) {
                if (col.equalsIgnoreCase(column)) {
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
        System.out.println("Enter the new value>>");
        String value = sc.nextLine();

        HashMap<String, String> newRecord = new HashMap<>();
        newRecord.put(column, value);
        db_Utilities.update_record(newRecord, "reservations", reservationId);
    }
}
public static void  delete_reservation() throws SQLException {
    HashMap<String, List<String>> record = db_Utilities.fetchAllRecords("reservations", "");
    if (record.isEmpty()) {
        System.out.println("NO RECORD WAS FOUND IN THIS TABLE");
    } else {
        Display.Display_tables(record);
        System.out.println("");
        System.out.println("Enter the ID of the reservation you want to delete");
        Scanner sc = new Scanner(System.in);
        String reservationId = sc.nextLine();
        System.out.println("Enter the book ISBN>>");
        String isbn = sc.nextLine();
        sc.close();
        db_Utilities.delete_record("reservations", reservationId);
        update_book_quantity(isbn, +1); // Increase book quantity by 1
    }
}

public static void update_book_quantity(String isbn, int quantityChange) throws SQLException {
    HashMap<String, List<String>> bookRecord = db_Utilities.fetchAllRecords("books", isbn);
    if (!bookRecord.isEmpty()) {
        int currentQuantity = Integer.parseInt(bookRecord.get("Quantity").get(0));
        int newQuantity = currentQuantity + quantityChange;
        HashMap<String, String> updateRecord = new HashMap<>();
        updateRecord.put("Quantity", String.valueOf(newQuantity));
        db_Utilities.update_record(updateRecord, "books", isbn);
    }
}



 public static void  reservation_menu()throws  SQLException{
    String[] reservation_menu = {
        "process Reservation",
        "View Reservation",
        "update Reservation",
        "delete Reservation",
        "Back"
    };
    int choice;
    System.out.println("Enter your choice:");
    Scanner input = new Scanner(System.in);
    choice =input.nextInt();
    input.close();
    Display.Display_menu(reservation_menu);
    switch (choice) {
        case 1 -> {
            add_reservation();
        }
        case 2 -> {
            Display.Display_tables(db_Utilities.fetchAllRecords("reservation", ""));
        }
        case 3 -> {
            update_reservation();
            
        }
        case 4 -> {
            delete_reservation();
        }
        case 5 -> {
            return;
        }
        default -> {
            System.out.println("Invalid choice");
        }
    }

 }
}
