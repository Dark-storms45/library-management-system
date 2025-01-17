package services;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.SQLException;

import Utility.Display;
import Utility.Utilities;
import Utility.db_Utilities;



public class transanction_services {


    private static Scanner sc = new Scanner(System.in);

    public static String getCurrentDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    private static int transactionCounter = 0;

    public static synchronized String generateTransactionNumber() {
        transactionCounter++;
        return "TXNPF" + String.format("%06d", transactionCounter );

    }

    public static void processTransaction() throws SQLException {
        int id = 0;
        while (true) {
            System.out.println("Enter the book id >>");
            id = sc.nextInt();
            try {
                if (!reservation_services.isBookInStock(id)) {
                    System.out.println("Quantity out of stock");
                    break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                break;
            }
            System.out.println("Enter the member id >>");
            int memberId = sc.nextInt();
            sc.nextLine(); // Clear buffer
            System.out.println("Enter the due date (yy/mm/dd) >>");
            String due_date = sc.nextLine();
            String transactionId = generateTransactionNumber();
            System.out.println("Enter fine amount >>");
            float fine_amount = 1200.05f; // Placeholder for your actual input
            System.out.println("Enter the quantity of books >>");
            int quantity = sc.nextInt();
            String fineAmountStr = String.valueOf(fine_amount * quantity);

            HashMap<String, Object> record = new HashMap<>();
            record.put("BookId", id);
            record.put("Id", transactionId);
            record.put("Quantity", quantity);
            record.put("MemberId", memberId);
            record.put("IssueDate", getCurrentDateTime());
            record.put("DueDate", due_date);
            record.put("fineAmount", fineAmountStr); // Keep as string since it involves calculations

            db_Utilities.add_record(record, "transactions");
            reservation_services.update_book_quantity(id, quantity);
            break;
        }
    }
    public static void return_book() throws SQLException {
        while (true) {
            try {
                System.out.println("Enter the transaction id >>");
                String id = sc.next(); // Read the transaction ID

                // Fetch the record by transaction ID
                HashMap<String, String> records = db_Utilities.fetch_record("Id", "transactions", id);

                // Validate if records exist
                if (records == null || records.isEmpty()) {
                    System.out.println("No matching transaction found for ID: " + id);
                    continue; // Retry
                }

                // Retrieve and parse book ID and quantity
                String bookIdStr = records.get("BookId");
                String quantityStr = records.get("Quantity");

                if (bookIdStr == null || quantityStr == null) {
                    System.out.println("Transaction data is incomplete. Please verify the database records.");
                    continue; // Retry
                }

                int bookId = Integer.parseInt(bookIdStr); // Parse book ID
                int quantity = Integer.parseInt(quantityStr); // Parse quantity

                // Update book stock
                reservation_services.update_book_quantity(bookId, quantity);
                System.out.println("updating......");
                Utilities.sleep(1100);
                System.out.println("Book return processed successfully.");
                break; // Exit after successful processing

            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid data format. Please check transaction details.");
            } catch (SQLException e) {
                System.out.println("A database error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void transaction_menu() {
        String arr[] = {
                "process Transaction",
                "view transaction",
                "return book",
                "back"

        };
        while (true) {
            Display.Display_menu(arr);
            System.out.println("Enter your choice");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    try {
                        processTransaction();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    try {
                        Display.Display_tables(db_Utilities.fetchAllRecords("transactions", "",""));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 3 -> {
                    try {
                        return_book();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 4 -> {
                    return;
                }
            }

        }
    }

}


