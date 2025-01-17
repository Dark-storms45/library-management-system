package services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import services.transanction_services;
import Utility.Display;
import Utility.Utilities;
import Utility.db_Utilities;

public class reservation_services {
public  static final Scanner input = new Scanner(System.in);
    public static boolean isBookInStock(int id) throws SQLException {
        HashMap<String, String> bookRecord = db_Utilities.fetch_record("id", "books", id);

        int currentQuantity ;
        if (!bookRecord.isEmpty()) {
            currentQuantity = Integer.parseInt(bookRecord.get("Quantity"));

            return currentQuantity > 0;
        }

        return false;
    }



public static  void add_reservation() throws SQLException {
    HashMap<String, Object> reservation = new HashMap<>();

    Display.Display_tables(db_Utilities.fetchAllRecords("Books", "",""));
    System.out.println("Enter the member ID>>");
    int memberId = input.nextInt();
    reservation.put("MemberID",String.valueOf( memberId));
    System.out.println("Enter the book Id>>");
    int id = input.nextInt();
    reservation.put("BookID", String.valueOf(id));
    System.out.println("Enter the reservation date YY/MM/DD>>");
    String date = input.next();
    reservation.put("ReservationDate", date);
    reservation.put("Reserved_Date",String.valueOf( transanction_services.getCurrentDateTime()));
    try {
        if (isBookInStock(id)) {
            db_Utilities.add_record(reservation, "Reservation");
            update_book_quantity(id, -1); // Decrease book quantity by 1
        } else {
            System.out.println("Book out of stock");
        }
    } catch (SQLException e) {
        System.out.println("An error has occurred: " + e.getMessage());
    }

}

public static void update_reservation() throws SQLException {
    HashMap<String, List<String>> record = db_Utilities.fetchAllRecords("reservation", "","");
    if (record.isEmpty()) {
        System.out.println("No data found in this table");
    } else {
        Display.Display_tables(record);
        for (int i = 0; i < 3; i++) {
            System.out.println("");
        }
        System.out.println("Enter the reservation to be modified by specifying the ID");
        Scanner sc = new Scanner(System.in);
        int reservationId = sc.nextInt();
        String columnName[] = {"ReservationID", "BookID", "MemberID", "Date"};
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

        HashMap<String, Object> newRecord = new HashMap<>();
        newRecord.put(column, value);
        db_Utilities.update_record(newRecord, "reservation", reservationId);
    }
}
public static void  delete_reservation() throws SQLException {
    HashMap<String, List<String>> record = db_Utilities.fetchAllRecords("reservation", "","");
    if (record.isEmpty()) {
        System.out.println("NO RECORD WAS FOUND IN THIS TABLE");
    } else {
        Display.Display_tables(record);
        System.out.println("");
        System.out.println("Enter the ID of the reservation you want to delete");
        Scanner sc = new Scanner(System.in);
        String reservationId = sc.nextLine();
        System.out.println("Enter the book Id>>");
        int id = sc.nextInt();
        db_Utilities.delete_record("reservation", reservationId);
        update_book_quantity(id, +1); // Increase book quantity by 1
    }
}

public static void update_book_quantity(int id, int quantityChange) throws SQLException {
    HashMap<String, List<String>> bookRecord = db_Utilities.fetchAllRecords("books", String.valueOf(id),"");
    if (!bookRecord.isEmpty()) {
        int currentQuantity = Integer.parseInt(bookRecord.get("Quantity").getFirst());
        int newQuantity = currentQuantity + quantityChange;
        HashMap<String, Object> updateRecord = new HashMap<>();
        updateRecord.put("Quantity", String.valueOf(newQuantity));
        db_Utilities.update_record(updateRecord, "books", id);
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
    while (true) { 
        
        Display.Display_menu(reservation_menu);
    System.out.println("Enter your choice:");

    choice =input.nextInt();
    
   
    switch (choice) {
        case 1 -> {
            add_reservation();
        }
        case 2 -> {
            Display.Display_tables(db_Utilities.fetchAllRecords("reservation", "",""));
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
}