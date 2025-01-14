package Classes;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Utility.Display;
import Utility.db_Utilities;
public class librant extends users {

    private String librantID;
    private String librantType;
    private String password;

    public librant(String name, String email, String address, String contact, String sexe, String librantID, String
     librantType, String password) {
        super(name, email, address, contact, sexe);
        this.librantID = librantID;
        this.librantType = librantType;
        this.password = password;

    }

    public String getLibrantID() {
        return librantID;
    }

    public void setLibrantID(String librantID) {
        this.librantID = librantID;
    }

    public String getLibrantType() {
        return librantType;
    }

    public String getLIbrantType() {
        return librantType;
    }

    public void setLibrantType(String librantType) {
        this.librantType = librantType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addBook() {

    }

    public void removeBook() {

    }

    public void updateBook() {

    }

    public void registerMember() {

    }

    public void processTransaction() {

    }

    public void add_book()  {
        HashMap<String, String> books = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the book ISBN");
        String isbn = sc.nextLine();
        books.put("ISBN", isbn);
        System.out.println("Enter the book title");
        String title = sc.nextLine();
        books.put("Title", title);
        System.out.println("Enter the book author");
        String author = sc.nextLine();
        books.put("Author", author);
        System.out.println("Enter the publisher");
        String publisher= sc.nextLine();
        books.put("Publisher", publisher);
        System.out.println("Enter publication year YY/MM/DD");
        String  year= sc.nextLine();
        books.put("publicationYear", title);
        System.out.println("Enter the book genre");
        String  genre=sc.nextLine();
        books.put("Genre", genre);
        try {
            db_Utilities.add_record(books, "books");
            
        } catch (SQLException e) {
            System.out.println("An error has occur"+e.getMessage());
        }
        

    }

    public void delete_book() throws SQLException {
       HashMap<String ,List<String>>record=new HashMap<>();
        record= db_Utilities.fetchAllRecords("books");
        Display.Display_tables(record);
        System.out.println("");
      System.out.println("Enter the Isbn of the book u want to delete");
    Scanner sc= new Scanner(System.in);
      String  isbn= sc.nextLine();
      db_Utilities.delete_record("books", isbn);

    }

    public void update_record(int recordId) {

    }

    public void send_Notification() {

    }

    public void view_record() {

    }

}
