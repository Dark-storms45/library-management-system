package services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Utility.Display;
import Utility.Utilities;
import Utility.db_Utilities;

public class books_services {

   
    
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
        books.put("publicationYear", year);
        System.out.println("Enter the book genre");
        String  genre=sc.nextLine();
        books.put("Genre", genre);
        sc.close();
        try {
            db_Utilities.add_record(books, "books");
            
        } catch (SQLException e) {
            System.out.println("An error has occur"+e.getMessage());
        }
        

    }

    public void delete_book() throws SQLException {
    HashMap<String ,List<String>> record = db_Utilities.fetchAllRecords("books","");
    if(record.isEmpty()){
    System.out.println("NO RECORD WAS FOUND IN THIS TABLE");
    }
    else{  
        Display.Display_tables(record);
        System.out.println("");
      System.out.println("Enter the Isbn of the book u want to delete");
    Scanner sc= new Scanner(System.in);
      String  isbn= sc.nextLine();
      sc.close();
      db_Utilities.delete_record("books ", isbn);



    }


    }

    public void update_book()throws SQLException {
        HashMap<String ,List<String>>record=new HashMap<>();
        record= db_Utilities.fetchAllRecords("books","");
        if (record.isEmpty()){
              System.out.println("No data found in this table ");
        }
  else{
    Display.Display_tables(record);
    for (int i = 0; i < 3; i++) {
        System.out.println("");
    }
    System.out.println("Enter the book to be modified by  specifing the isbn ");
    Scanner sc= new Scanner(System.in);
    String  isbn= sc.nextLine();
    String columnName []={"isbn","title","author","publisher","publicationyear","genre"};
    String column;
    


    while (true) {
        System.out.println("Enter the column name to be modified as shown on the table");
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
  
         System.out.println("Enter the new value ");
         String value =sc.nextLine();

         HashMap<String,String>newrecord=new HashMap<>();
         newrecord.put(column, value);
         db_Utilities.update_record(newrecord, "books", isbn);

} 
    }

  public void search_book() throws SQLException{
    System.out.println(" Enter the isbn/ title  /author of the book you want to search");
    Scanner sc= new Scanner(System.in);
      String data=sc.nextLine();
      sc.close();
          Display.Display_tables(db_Utilities.fetchAllRecords("books", data));


  }

  public static void book_menu() {
    String[] book_menu = {
        "Add Book",
        "Remove Book",
        "Update Book",
        "View Books",
        "Back"
    };
    Display.Display_menu(book_menu);
    Scanner input = new Scanner(System.in);
    System.out.println("Enter your choice:");
    int choice = input.nextInt();
    switch (choice) {
        case 1 -> {
        
        }
        case 2 -> {
            System.out.println("Remove Book");
        }
        case 3 -> {
            System.out.println("Update Book");
        }
        case 4 -> {
            System.out.println("View Books");
        }
        case 5 -> {
            System.out.println("Back");
        }
        default -> {
            System.out.println("Invalid choice");
        }
    }
}

}
