package services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Utility.Display;
import Utility.Utilities;
import Utility.db_Utilities;

public class member_services {

    private static final Scanner input = new Scanner(System.in);


    public void add_member() {

        HashMap<String, Object> member = new HashMap<>();

        System.out.println("Enter the member name>>");
        String name = input.nextLine();
        member.put("MemberName", name);
        System.out.println("Enter the member email>>");
        String email = input.nextLine();
        member.put("MemberEmail", email);
        System.out.println("Enter the member address>>");
        String address = input.nextLine();
        member.put("MemberAddress", address);
        System.out.println("Enter the member contact>>");
        String contact = input.nextLine();
        member.put("MemberContact", contact);
        System.out.println("Enter the membership type>>");
        String type = input.nextLine();
        member.put("MembershipType", type);
        System.out.println("Enter the membership fee>>");
        String fee = input.nextLine();
        member.put("MembershipFee", fee);
        System.out.println("Enter the membership Expiry data YY/MM/DD>>");
        String date = input.nextLine();
        member.put("MembershipExpiry", date);

        try {
            db_Utilities.add_record(member, "members");
        } catch (SQLException e) {
            System.out.println("An error has occurred: " + e.getMessage());

        }
    }

    public void delete_member() throws SQLException {
        HashMap<String, List<String>> record = db_Utilities.fetchAllRecords("members", "","");
        if (record.isEmpty()) {
            System.out.println("NO RECORD WAS FOUND IN THIS TABLE");
        } else {
            Display.Display_tables(record);
            System.out.println("");
            System.out.println("Enter the ID of the member you want to delete");

            String memberId = input.nextLine();

            db_Utilities.delete_record("members", memberId);
        }
    }

    public void update_member() throws SQLException {
        HashMap<String, List<String>> record = db_Utilities.fetchAllRecords("members", "","");
        if (record.isEmpty()) {
            System.out.println("No data found in this table");
        } else {
            Display.Display_tables(record);
            for (int i = 0; i < 3; i++) {
                System.out.println("");
            }
            System.out.println("Enter the member to be modified by specifying the ID>>");

            int memberId = input.nextInt();
            String columnName[] = {"MemberID", "Name", "Email", "Address", "Contact", "Gender"};
            String column;

            while (true) {
                System.out.println("Enter the column name to be modified as shown on the table>>");
                column = input.nextLine();
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
            String value = input.nextLine();

            HashMap<String, Object> newRecord = new HashMap<>();
            newRecord.put(column, value);
            db_Utilities.update_record(newRecord, "members", memberId);
        }
    }

    public void search_member() throws SQLException {
        System.out.println("Enter the Name of the member you want to search>>");

        String data = input.nextLine();
        Display.Display_tables(db_Utilities.fetchAllRecords("members", data,"MemberName"));
    }


    public static void member_menu() throws SQLException {

        String[] member_menu = {
                "Add Member",
                "Remove Member",
                "Update Member information",
                "search a member",
                "View Members list",
                "Back"
        };

        int choice;
        Display.Display_menu(member_menu);
        while (true) {
            System.out.println("Enter your choice:");
            choice = -1;
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

            if (choice == 1) {
                new member_services().add_member();
            } else if (choice == 2) {
                new member_services().delete_member();
            } else if (choice == 3) {
                new member_services().update_member();
            } else if (choice == 4) {
                new member_services().search_member();
            } else if (choice == 5) {
                Display.Display_tables(db_Utilities.fetchAllRecords("members", "",""));
            } else if (choice == 6) {
                return;
            } else {
                System.out.println("Invalid choice");
            }
        }

    }
}