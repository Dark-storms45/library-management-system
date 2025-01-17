package services;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.SQLException;

import Utility.Display;
import Utility.db_Utilities;



public class transanction_services {

   
private  static Scanner sc=new Scanner(System.in);
    public static String getCurrentDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    private static int transactionCounter = 0;

    public static synchronized String generateTransactionNumber() {
        transactionCounter++;
        return "TXNPF" + String.format("%06d", transactionCounter+"ol");
        
    }
      

    public static void processTransaction() throws SQLException {

   while (true) { 
       
  System.out.println("enter the book isbn>>");
      
        String isbn = sc.nextLine();
        try {
            if (reservation_services.isBookInStock(isbn) == false) {
                System.out.println("Quantity out of stock");
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            break;
        }
        System.out.println("enter the member id>>");
        String memberId = sc.nextLine();
        String issue_date = getCurrentDateTime();
        System.out.println("enter the due date yy/mm/dd>>");
        String due_date = sc.nextLine();
        String transactionId = generateTransactionNumber();
        System.out.println("Enter find amount>>");
        System.out.println("Enter the Quantity of book booked");
        int Quantity=sc.nextInt();
        float fine_amount=sc.nextFloat();
        String fineAmountStr = String.valueOf(fine_amount);
        HashMap<String, String> record = new HashMap<String, String>();
        record.put("TransactionId", transactionId);
        record.put("MemberId",memberId );
        record.put("IssueDate", issue_date);
        record.put("DueDate", due_date);
        record.put("fineAmount", fineAmountStr);
        db_Utilities.add_record(record, "transaction");
        reservation_services.update_book_quantity(isbn,Quantity);
        break;
    }

        
   }

   public static void transaction_menu(){
  String arr[]={
     "proccess Transaction",
     "view Transactions",
     "back"

  };

Display.Display_menu(arr);
System.out.println("Enter your choice");

int choice=sc.nextInt();

switch(choice){
case 1 -> {
    try {
        processTransaction();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
case 2->{
    try {
        Display.Display_tables(db_Utilities.fetchAllRecords("transactions", ""));
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
case 3 ->{
    return;
}
}

   }
}




