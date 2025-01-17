package services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Utility.Display;
import Utility.Utilities;
import Utility.db_Utilities;
public class librant_services {


public void delete_librant() throws SQLException {
    HashMap<String, List<String>> record = db_Utilities.fetchAllRecords("librant", "");
    if (record.isEmpty()) {
        System.out.println("NO RECORD WAS FOUND IN THIS TABLE");
    } else {
        Display.Display_tables(record);
        System.out.println("");
        System.out.println("Enter the ID of the librant you want to delete");
        Scanner sc = new Scanner(System.in);
        String librantId = sc.nextLine();
        sc.close();
        db_Utilities.delete_record("librant", librantId);
    }
}

public void update_librantInfo() throws SQLException {
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
        String librantId = sc.nextLine();
        String columnName[] = {"librantID", "LibrantName", "LibrantEmail", "LibrantAddress", "LibrantContact", "LibrantSexe", "librantType","password"};
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
        db_Utilities.update_record(newRecord, "librant", librantId);
    }
}

public void search_librant() throws SQLException {
    System.out.println("Enter the ID/Name/Email of the member you want to search>>");
    Scanner sc = new Scanner(System.in);
    String data = sc.nextLine();
    sc.close();
    Display.Display_tables(db_Utilities.fetchAllRecords("librant", data));
}

public static void  librant_Servicemenu() throws SQLException {
    String[] librant_menu = {
        "register a librant",
        "update librant info",
        "delete librant",
        " search librant",
        "view librants",
        "Logout"
    };
    Display.Display_menu(librant_menu);
    Scanner input = new Scanner(System.in);
    System.out.println("Enter your choice:");
    int choice = input.nextInt();
    input.close();
    switch (choice) {
        case 1 ->
            Utility.login_utils.register();
        case 2 ->
            new librant_services().update_librantInfo();

        case 3 ->{
            new librant_services().delete_librant();
        }
            case 4 ->{
                new librant_services().search_librant();

            }
        case 5 ->{
            Display.Display_tables(db_Utilities.fetchAllRecords("librants", ""));
        }

        case 6 -> {
          
            return;
        }
        default ->
            System.out.println("Invalid choice");
    }

}
}
