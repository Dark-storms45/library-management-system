package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;

public class db_Utilities {
    public static Connection dbConnection() throws SQLException {
        String url = "jdbc:sqlite:src/database/database.db";
        Connection connection = DriverManager.getConnection(url);
        if (connection != null) {
            System.out.println("Connection to SQLite has been established.");
        }
        return connection;
    }
   public  static  void close_connection()throws SQLException{
    Connection connection = dbConnection();
    if (connection != null) {
        try {
        connection.close();
        System.out.println("Connection closed successfully.");
        } catch (SQLException e) {
        System.out.println("An error occurred while closing the connection: " + e.getMessage());
        throw e;
        }
    }

   }
    public static void creatTables() throws SQLException {
        HashMap<String, String> Tables = new HashMap<>();
        
        Tables.put("Members", "CREATE TABLE IF NOT EXISTS Members (" +
            "MemberId INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "MemberName TEXT NOT NULL, " +
            "MemberEmail TEXT NOT NULL, " +
            "MemberContact TEXT NOT NULL, " +
            "MembershipType TEXT NOT NULL, " +
            "MembershipStatus TEXT NOT NULL, " +
            "MembershipFee TEXT NOT NULL, " +
            "MembershipExpiry DATETIME NOT NULL, " +
            "MembershipPaymentStatus TEXT NOT NULL);");
        
        Tables.put("Librant", "CREATE TABLE IF NOT EXISTS Librant (" +
            "LibrantId INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "LibrantName TEXT NOT NULL, " +
            "LibrantEmail TEXT NOT NULL, " +
            "LibrantContact TEXT NOT NULL, " +
            "LibrantAddress TEXT NOT NULL, " +
            "LibrantSalary REAL, " +
            "LibrantSex TEXT NOT NULL);");
        
        Tables.put("Books", "CREATE TABLE IF NOT EXISTS Books (" +
            "ISBN TEXT PRIMARY KEY NOT NULL, " +
            "Title TEXT NOT NULL, " +
            "Author TEXT NOT NULL, " +
            "Publisher TEXT NOT NULL, " +
            "PublicationYear TEXT NOT NULL, " +
            "Genre TEXT NOT NULL);");
        
        Tables.put("Transaction", "CREATE TABLE IF NOT EXISTS Transaction (" +
            "TransactionId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "MemberId INTEGER NOT NULL, " +
            "BookId TEXT NOT NULL, " +
            "IssueDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "ReturnDate DATETIME NOT NULL, " +
            "DueDate DATETIME NOT NULL, " +
            "FineAmount REAL NOT NULL, " +
            "FOREIGN KEY(MemberId) REFERENCES Members(MemberId), " +
            "FOREIGN KEY(BookId) REFERENCES Books(ISBN));");
        
        Tables.put("Notification", "CREATE TABLE IF NOT EXISTS Notification (" +
            "NotificationId INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "MemberId INTEGER NOT NULL, " +
            "Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, " +
            "Content TEXT NOT NULL, " +
            "FOREIGN KEY(MemberId) REFERENCES Members(MemberId));");
        
        Tables.put("Complains", "CREATE TABLE IF NOT EXISTS Complains (" +
            "ComplainId INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "MemberId INTEGER NOT NULL, " +
            "ComplainDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, " +
            "ComplainContent TEXT NOT NULL, " +
            "FOREIGN KEY(MemberId) REFERENCES Members(MemberId));");
        
        Tables.put("Status", "CREATE TABLE IF NOT EXISTS Status (" +
            "StatusId INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "MemberId INTEGER NOT NULL, " +
            "BookId TEXT NOT NULL, " +
            "Status TEXT NOT NULL, " +
            "FOREIGN KEY(MemberId) REFERENCES Members(MemberId), " +
            "FOREIGN KEY(BookId) REFERENCES Books(ISBN));");

        try (Connection connection = dbConnection();
             Statement stmt = connection.createStatement()) {
            for (String table : Tables.keySet()) {
                try {
                    stmt.execute(Tables.get(table));
                    System.out.println("Table " + table + " created successfully.");
                } catch (SQLException e) {
                    System.out.println("An error occurred while creating table " + table + ": " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while establishing the connection: " + e.getMessage());
            throw e;
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
                finally{
                    close_connection();
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
                ResultSet resultSet = connection.createStatement().executeQuery(query);
                ResultSetMetaData metaData = resultSet.getMetaData();
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
                close_connection();;
            }
        }
        return result;
    }
}