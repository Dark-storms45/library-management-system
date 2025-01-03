package Utility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;

public class db_Utilities {
    /** funtion to create connection with  the database **/

    public static void dbConnection(){


        Connection connection = null;
        try {
            // Load the SQLite JDBC driver (you don't need this line if you are using a modern JDBC driver)
           Class.forName("org.sqlite.JDBC");

            // Create a connection to the database
            String url = "jdbc:sqlite:src\\database\\database.db";
            connection = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println( " An error has occured "+e.getMessage());
        }
        catch (ClassNotFoundException e) {
            System.out.println("An error has occured "+e.getMessage());
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("An error occurred while closing the connection: " + e.getMessage());
            }
        }
         
    }
    
    public static void db_CloseConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection to SQLite has been closed.");
            } catch (SQLException e) {
                System.out.println("An error occurred while closing the connection: " + e.getMessage());
            }
        }
    }
       /** funtion to create the difrrent database tables if the are not already created */
    public static void creatTables() throws SQLException {
      HashMap<String,String>Tables=new HashMap<>();
      Tables.put("Members", "CREATE TABLE IF NOT EXISTS Members (" +
      "MemberId INTEGER PRIMARY KEY AUTOINCREMENT, " +
      "MemberName TEXT NOT NULL, " +
      "MemberEmail TEXT NOT NULL, " +
      "MemberContact INTEGER NOT NULL, " +
      "MembershipType TEXT NOT NULL, " +
      "MenbershipStatus TEXT NOT NULL, " +
      "MembershipFee TEXT NOT NULL, " +
      "MEMBERSHIPEXPIRY DATETIME NOT NULL, " +
      "MembershipPaymentstatus TEXT NOT NULL);");


      Tables.put("librant","CREATE TABLE IF NOT EXISTS librant("+
        "LibrantId INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "LibrantName TEXT NOT NULL, " +
        "LibrantEmail TEXT NOT NULL, " +
        "LibrantContact text NOT NULL, " +
        "LibrantAddress TEXT NOT NULL, " +
        "LibrantSalary REAL,"+
        "librantSexe TEXT NOT NULL,"+
        "LibrantType TEXT NOT NULL,"+
        "LibrantPassword TEXT NOT NULL);");

        Tables.put("Books","CREATE TABLE IF NOT EXISTS Books("+
        "ISBN TEXT PRIMARY KEY NOT NULL,"+
        "Title TEXT NOT NULL,"+
        "Author TEXT NOT NULL,"+
        "publisher TEXT NOT NULL,"+
        "PublicationYear TEXT NOT NULL,"+
        "Genre TEXT NOT NULL)");
        
        Tables.put("Transaction","CREATE TABLE IF NOT EXISTS Transaction("+
        "TansactionId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
        "MemberId INTERGER NOT NULL,"+
        "BookId TEXT NOT NULL,"+
        "IssueDate DEFAULT CURRENT_TIMESTAMP,"+
        "ReturnDate DATETIME NOT NULL,"+
        "DueDate DATETIME NOT NULL,"+
        "FineAmount REAL NOT NULL"+
        "FOREING KEY(MemberId) REFERENCES Members(MemberId),"+
        "FOREING KEY(BookId) REFERENCES Books(ISBN))");

        Tables.put("Reservation","CREATE TABLE IF NOT EXISTS Reservation("+
        "ReservationId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
        "MemberId INTEGER NOT NULL,"+
        "BookId TEXT NOT NULL"+
        "REservationDate DEFAULT CURRENT_TIMESTAMP,"+
        "Status Text NOT NULL"+
        "FOREING KEY(MemberId) REFERENCES Members(MemberId),"+
        "FOREING KEY(BookId) REFERENCES BOOKS(ISBN))");

        Tables.put("Notification ","CREATE TABLE IF NOT EXISTS Notification("+
        "NotificationId INTEGER PRIMAR KEY NOT NULL,"+
        "MemberId INTEGER NOT NULL,"+
        "Date CURRENT_TIMESTAMP NOT NULL,"+
        "Content TEXT NOT NULL,"+
        "FOREING KEY(MemberId) REFERENCES Members(MemberId))");

        Tables.put("complains", "CREATE TABLE IF NOT EXISTS Complains("+ 
        "ComplainId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
        "MemberId INTEGER NOT NULL,"+
        "ComplainDate CURRENT_TIMESTAMP NOT NULL,"+
        "ComplainContent TEXT NOT NULL,"+
        "FOREING KEY(MemberId) REFERENCES Members(MemberId))");


         Connection connection ;
        connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\database.db");
        if(connection!=null){
            for(String table:Tables.keySet()){
                try{
                    connection.createStatement().execute(Tables.get(table));
                    System.out.println("Table "+table+" created successfully");
                }catch(SQLException e){
                    System.out.println("An error occured while creating table "+table+" "+e.getMessage());
                }
                finally {
                    try {
                    
                            connection.close();
                    
                } catch (SQLException e) {
                    System.out.println("An error occurred while closing the connection: " + e.getMessage());
                            }
            }
            }

    }
}
public static void add_record(HashMap<String, String> record, String table) throws SQLException {
    String url = "jdbc:sqlite:src\\database\\database.db";
    try (Connection connection = DriverManager.getConnection(url)) {
        if (connection != null) {
            String columns = String.join(",", record.keySet());
            String placeholders = String.join(",", Collections.nCopies(record.size(), "?"));
            String query = "INSERT INTO " + table + " (" + columns + ") VALUES (" + placeholders + ")";
            
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                int index = 1;
                for (String key : record.keySet()) {
                    pstmt.setString(index++, record.get(key));
                }
                pstmt.executeUpdate();
                System.out.println("Record added successfully");
            } catch (SQLException e) {
                System.out.println("An error occurred while adding record: " + e.getMessage());
                throw e;
            }
        }
    }
}


public static HashMap<String, String> fetch_record(String table, String filters, String fields) throws SQLException {
Connection connection = null;
HashMap<String, String> result = new HashMap<>();
connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\database.db");
if (connection != null) {
    try {
        String query = "SELECT " + fields + " FROM " + table;
        if (!filters.isEmpty()) {
            query += " WHERE " + filters;
        }
        var resultSet = connection.createStatement().executeQuery(query);
        var metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                result.put(metaData.getColumnName(i), resultSet.getString(i));
            }
        }
        System.out.println("Record fetched successfully");
    } catch (SQLException e) {
        System.out.println("An error occurred while fetching record " + e.getMessage());
    } finally {
        connection.close();
    }
}
return result;
}
}
   




















