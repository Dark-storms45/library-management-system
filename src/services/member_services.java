package services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Utility.Display;
import Utility.Utilities;
import Utility.db_Utilities;

public class member_services {


    
  
public  void add_member() {
    HashMap<String, String> member = new HashMap<>();
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the member name>>");
    String name = sc.nextLine();
    member.put("MemberName", name);
    System.out.println("Enter the member email>>");
    String email = sc.nextLine();
    member.put("MemberEmail", email);
    System.out.println("Enter the member address>>");
    String address = sc.nextLine();
    member.put("MemberAddress", address);
    System.out.println("Enter the member contact>>");
    String contact = sc.nextLine();
    member.put("MemberContact", contact);
    System.out.println("Enter the membership type>>");
    String type=sc.nextLine();
    member.put("MembershipType", type);
    System.out.println("Enter the membership fee>>");
    String fee = sc.nextLine();
    member.put("MembershipFee", fee);
    System.out.println("Enter the membership Expiry data YY/MM/DD>>");
    String date=sc.nextLine();
    member.put("MembershipExpiry", date);
    sc.close();
    try {
        db_Utilities.add_record(member, "members");
    } catch (SQLException e) {
        System.out.println("An error has occurred: " + e.getMessage());
    }
}

public void delete_member() throws SQLException {
    HashMap<String, List<String>> record = db_Utilities.fetchAllRecords("members", "");
    if (record.isEmpty()) {
        System.out.println("NO RECORD WAS FOUND IN THIS TABLE");
    } else {
        Display.Display_tables(record);
        System.out.println("");
        System.out.println("Enter the ID of the member you want to delete");
        Scanner sc = new Scanner(System.in);
        String memberId = sc.nextLine();
        sc.close();
        db_Utilities.delete_record("members", memberId);
    }
}

public void update_member() throws SQLException {
    HashMap<String, List<String>> record = db_Utilities.fetchAllRecords("members", "");
    if (record.isEmpty()) {
        System.out.println("No data found in this table");
    } else {
        Display.Display_tables(record);
        for (int i = 0; i < 3; i++) {
            System.out.println("");
        }
        System.out.println("Enter the member to be modified by specifying the ID>>");
        Scanner sc = new Scanner(System.in);
        String memberId = sc.nextLine();
        String columnName[] = {"MemberID", "Name", "Email", "Address", "Contact", "Gender"};
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
        db_Utilities.update_record(newRecord, "members", memberId);
    }
}

public void search_member() throws SQLException {
    System.out.println("Enter the ID/Name/Email of the member you want to search>>");
    Scanner sc = new Scanner(System.in);
    String data = sc.nextLine();
    sc.close();
    Display.Display_tables(db_Utilities.fetchAllRecords("members", data));
}


public static void  member_menu() throws SQLException{

    String[] member_menu = {
        "Add Member",
        "Remove Member",
        "Update Member information",
        "search a member",
        "View Members list",
        "Back"
    };
    Display.Display_menu(member_menu);
    Scanner input = new Scanner(System.in);
    System.out.println("Enter your choice:");
    int choice = input.nextInt();
    input.close();
    switch (choice) {
        case 1 -> {
        new member_services().add_member(); 
        }
        case 2 -> {
            new member_services().delete_member();
            
        }
        case 3 -> {
            new member_services().update_member();
        }
        case 4 ->{
            new member_services().search_member();
        }
        case 5 -> {
            Display.Display_tables(db_Utilities.fetchAllRecords("members", ""));
        }
        case 6-> {
            return;
        }
        default -> {
            System.out.println("Invalid choice");
        }
    }
 }
}
