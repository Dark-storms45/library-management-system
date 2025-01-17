package Utility;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

import Classes.librant;

public class login_utils {
private static final  Scanner input = new Scanner(System.in);
    /**
     * function return :void parameter :password use : hash the password
     */
    public static int hash_password(String password) {
        // Hash the password
        int hash = password.hashCode();
        
        return hash;
    }

    public static boolean validate_password(String password) {

        if (password.length() > 8 && password.matches(".*[A-Z].*") && password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {

            return true;

        } else {
            return false;
        }

    }

    public static boolean email_validation(String email) {
        // Add email validation logic here
        email = email.toLowerCase().trim();
        if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * return :void parameter :none use : register user and add it to the
     * database
     */
    public static void register() throws SQLException {



        System.out.println("Enter your name:");
        String name = input.nextLine();

        System.out.println("Enter your contact:");
        String contact = input.nextLine();

        System.out.println("Enter your role:");
        String role = input.nextLine();

        System.out.println("Enter your sex:");
        String sex = input.nextLine();

        System.out.println("Enter your address:");
        String address = input.nextLine();

        System.out.println("Enter your email:");
        String email = input.nextLine();

        while (true) {
            if (email_validation(email) == true) {
                break;
            } else {
                System.out.println("Invalid email. Please enter a valid email:");
                email = input.nextLine();
            }

        }

        System.out.println("Enter your password:");
        String password = input.nextLine();
        while (true) {
            if (validate_password(password) == true) {
                break;
            } else {
                System.out.println("make sure your password is at least 8 characters long and contains at least one special character and an uppercase letter:");
                password = input.nextLine();
            }

        }

        System.out.println("Enter  the confirm  password:");
        String confirm_password = input.nextLine();
        while (true) {
            if (password.equals(confirm_password)) {
                System.out.println("user created successfully");
                break;
            } else {
                System.out.println("Password does not match. Please enter the same password:");
                confirm_password = input.nextLine();
            }

        }
        HashMap<String, Object> record = new HashMap<>();
        record.put("LibrantName", name);
        record.put("LibrantEmail", email);
        record.put("LibrantContact", contact);
        record.put("LibrantAddress", address);
        record.put("LibrantSex", sex);
        record.put("role", role);
        record.put("password", hash_password(password) + "");

        try {
            db_Utilities.add_record(record, "librant");
        } catch (SQLException e) {
            System.out.println("An error occurred while adding record " + e.getMessage());
        }

    }

    public static void login() throws SQLException {

        System.out.println("Enter your email:");
        String email = input.nextLine();
        System.out.println("Enter your password:");
        String password = input.nextLine();
        HashMap<String, String> record = new HashMap<>();
        record.put("email", email);
        record.put("password", password);

        try {

            String hashedPassword = String.valueOf(hash_password(password)); ;
            record = db_Utilities.fetch_record("LibrantEmail", "Librant" , email);

           // librant libran = new librant(record.get("LibrantName"), record.get("LibrantEmail"), record.get("LibrantAdress"), record.get("LibrantContact") , record.get("LibrantSex"), record.get("LibrantId"), record.get("LibrantType"),String.valueOf( record.get("password")));
            if (String.valueOf(record.get("Password")).equals(hashedPassword)) {
                if (record.get("Role").equals("Admin")) {
                    System.out.println("Welcome, " + record.get("LibrantName"));
                    Display.displayBox("Welcome to the Admin dashboard");
                    Menu.Admin_menu();

                } else {
                    Menu.user_menu();
                }

            } else {
                System.out.println("Invalid email or password.");
                if(record.isEmpty()){
                    System.out.println("no data in here");
                }
            }

        } catch (SQLException e) {
            System.out.println("An error  while fetching record " + e.getMessage());
        }
    }
    

}
