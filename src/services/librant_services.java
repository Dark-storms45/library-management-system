package services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Utility.Display;
import Utility.Utilities;
import Utility.db_Utilities;
public class librant_services {
private static final    Scanner input = new Scanner(System.in);

    public void delete_librant() throws SQLException {
        HashMap<String, List<String>> record = db_Utilities.fetchAllRecords("librant", "","");
        if (record.isEmpty()) {
            System.out.println("NO RECORD WAS FOUND IN THIS TABLE");
        } else {
            Display.Display_tables(record);
            System.out.println("");
            System.out.println("Enter the ID of the librant you want to delete");

            String librantId = input.nextLine();

            db_Utilities.delete_record("librant", librantId);
        }
    }

    public void update_librantInfo() throws SQLException {
        HashMap<String, List<String>> record = db_Utilities.fetchAllRecords("members", "","");
        if (record.isEmpty()) {
            System.out.println("No data found in this table");
        } else {
            Display.Display_tables(record);
            for (int i = 0; i < 3; i++) {
                System.out.println("");
            }
            System.out.println("Enter the member to be modified by specifying the ID>>");

            int librantId = input.nextInt();
            String columnName[] = {"librantID", "LibrantName", "LibrantEmail", "LibrantAddress", "LibrantContact", "LibrantSexe", "librantType", "password"};
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
            db_Utilities.update_record(newRecord, "librant", librantId);
        }
    }

    public void search_librant() throws SQLException {
        System.out.println("Enter the Name of the librant you want to search>>");

        String data = input.nextLine();

        Display.Display_tables(db_Utilities.fetchAllRecords("librant", data,"LibrantName"));
    }

    public static void librant_Servicemenu() throws SQLException {
        String[] librant_menu = {
                "register a librant",
                "update librant info",
                "delete librant",
                " search librant",
                "view librants",
                "Logout"
        };
        int choice;
        while (true) {

            Display.Display_menu(librant_menu);

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
            switch (choice) {
                case 1 -> Utility.login_utils.register();
                case 2 -> new librant_services().update_librantInfo();

                case 3 -> {
                    new librant_services().delete_librant();
                }
                case 4 -> {
                    new librant_services().search_librant();
                }
                case 5 -> {
                    Display.Display_tables(db_Utilities.fetchAllRecords("librant", "",""));
                }

                case 6 -> {
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
