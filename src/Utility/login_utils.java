package Utility;
import java.sql.SQLException;
import  java.util.HashMap;
import java.util.Scanner;
public class login_utils {

    /**function 
     * return :void
     * parrameter :password
     * use : hash the password
     */

    public static int  hash_password(String password){
        // Hash the password
        int hash = password.hashCode();
       // System.out.println("The hashed password is: " + hash);
       return hash;
    }

    public static boolean validate_password(String password){

        if (password.length() > 8 && password.matches(".*[A-Z].*") &&  password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            
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
     * return :void
     * parrameter :none
     * use : register user and add it to the database
     */
    public static void register() throws SQLException{

      Scanner sc=new Scanner(System.in);
        
        System.out.println("Enter your name:");
        String name=sc.nextLine();


        System.out.println("Enter your contact:");
        String contact=sc.nextLine();


        System.out.println("Enter your role:");     
        String role=sc.nextLine();

        System.out.println("Enter your sexe:");
        String sexe=sc.nextLine();

        System.out.println("Enter your address:");
        String address=sc.nextLine();
         
        
        System.out.println("Enter your email:");
                String email=sc.nextLine();

                while (true) {
                    if (email_validation(email)==true) {
                        break;
                    } else {
                        System.out.println("Invalid email. Please enter a valid email:");
                        email = sc.nextLine();
                    }
                    
                }
        
        System.out.println("Enter your password:");
        String password=sc.nextLine();
        while (true) {
            if (validate_password(password)==true) {
                break;
            } else {
                System.out.println("make sure your password is atlest 8 character long and contains atlease one special character and an uppercase letter:");
                password = sc.nextLine();
            }
    
        }

        System.out.println("Enter your confirm password:");
        String confirm_password=sc.nextLine();
        while (true) {
            if (password.equals(confirm_password)) {
                System.out.println("user created sucessfully");
                break;
            } else {
                System.out.println("Password does not match. Please enter the same password:");
                confirm_password = sc.nextLine();
            }
            
        }
        HashMap<String,String> record=new HashMap<>();
        record.put("name",name);
        record.put("email",email);
        record.put("contact",contact);
        record.put("address",address);
        record.put("sexe",sexe);
        record.put("role",role);
        record.put("password",hash_password(password)+"");

        try {
            db_Utilities.add_record(record, "librant");
        } catch (SQLException e) {
            System.out.println("An error occured while adding record "+e.getMessage());
        }
    
    }

    public  static void login() throws SQLException{
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter your email:");
        String email=sc.nextLine();
        System.out.println("Enter your password:");
        String password=sc.nextLine();
        HashMap<String,String> record=new HashMap<>();
        record.put("email",email);
        record.put("password",password);
        try {
           hash_password(password);
           int hashedPassword = password.hashCode();
           record = db_Utilities.fetch_record("librant", "email='" + email + "' AND password='" + hashedPassword + "'", "*");
           if (record != null && !record.isEmpty()) {
               System.out.println("Login successful!");
               System.out.println("Welcome, " + record.get("name"));
           } else {
               System.out.println("Invalid email or password.");
           }

        } catch (SQLException e) {
            System.out.println("An error occured while fetching record "+e.getMessage());
        }
    }

}
