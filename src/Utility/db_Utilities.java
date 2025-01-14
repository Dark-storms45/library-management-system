package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;



public class db_Utilities {

    private static final String url = "jdbc:sqlite:src\\database\\database.db";
    public static Connection dbConnection() throws SQLException {
       
        Connection connection = DriverManager.getConnection(url);
        if (connection != null) {
            System.out.println("Connection to SQLite has been established.");
           
        }
        return connection;
    }
public static void close_Connection()throws SQLException{

    Connection connection=dbConnection();

    try {
        if(connection != null){

          connection.close();

        }
    } catch (SQLException e) {
      System.out.println("An error occured while closing the database"+e.getMessage());
    }
}
/**
 * function to creat the  tables  in the database if it does not exist yet
 * @throws SQLException
 */
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
            "Password TEXT NOT NULL, " +
            "Role TEXT NOT NULL, " +
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
        
        Tables.put("Transaction", "CREATE TABLE IF NOT EXISTS Transactions (" +
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

        try (Connection connection = dbConnection()) {
            for (String table : Tables.keySet()) {
                try (PreparedStatement pstmt = connection.prepareStatement(Tables.get(table))) {
                    pstmt.execute();
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
/**
 * 
 * @param record type Hashmap :  key-coulumn where the field wld be added and data added -value 
 * @param table  type String : table name in which the data wld be added
 * @return  null
 * @throws SQLException
 */
    public static void add_record(HashMap<String, String> record, String table) throws SQLException {
        
        
          dbConnection();
            if (dbConnection() != null) {
                String columns = String.join(",", record.keySet());
                String placeholders = String.join(",", Collections.nCopies(record.size(), "?"));
                String query = "INSERT INTO " + table + " (" + columns + ") VALUES (" + placeholders + ")";
                
                try (PreparedStatement pstmt = dbConnection().prepareStatement(query)) {
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
                close_Connection();
                
                }
            }
        
    }
    /**
     * 
     * @param fields type String : the  data to fetch from the database
     * @param table type String: The table name from which the data wld be fetch
     * @param filters type String: used to fielter the field(s) fetched
     * @return  type: Hashmap  returns the coulumn name as key and the fetch data as value
     * @throws SQLException
     */ 
    public static HashMap<String, String> fetch_record(String fields, String table, String filters) throws SQLException {
        HashMap<String, String> result = new HashMap<>();
        
        try (Connection connection = dbConnection()) {
            String query = "SELECT " + (fields.isEmpty() ? "*" : fields) + " FROM " + table;
            if (!filters.isEmpty()) {
                query += " WHERE " + filters;
            }
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                ResultSet resultSet = pstmt.executeQuery();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                if (resultSet.next()) {
                    for (int i = 1; i <= columnCount; ++i) {
                        result.put(metaData.getColumnName(i), resultSet.getString(i));
                    }
                }
            } catch (SQLException e) {
                System.out.println("An error occurred while fetching record: " + e.getMessage());
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while establishing the connection: " + e.getMessage());
            throw e;
        } finally {
            close_Connection();
        }
        return result;
    }
    /**
     * Function to  update record inthe database
     * @param record of type hashmap
     * @param table type String : table name from  which the data wld be updated
     * @param filters type String: data in the db to be modified
     * @throws SQLException
     */

    public static void update_record(HashMap<String, String> record, String table, String filters) throws SQLException {
        
        try  {
          Connection connection=  dbConnection();
            if (dbConnection() != null) {
                String columns = String.join(",", record.keySet());
                String placeholders = String.join(",", Collections.nCopies(record.size(), "?"));
                String query = "UPDATE " + table + " SET " + columns + " WHERE " + filters;
                
                try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                    int index = 1;
                    for (String key : record.keySet()) {
                        pstmt.setString(index++, record.get(key));
                    }
                    pstmt.executeUpdate();
                    System.out.println("Record updated successfully");
                } catch (SQLException e) {
                    System.out.println("An error occurred while updating record: " + e.getMessage());
                    throw e;
                }
            }
        }
        catch(SQLException e){
            System.err.println("An error occur while openning the database "+e.getMessage());
        }
    }


    /**
     * function to  delete data  from a table in the database
     * @param table table from which the data wld be deleted
     * @param filters field to be deleted
     * @throws SQLException
     * @return  returns  null
     */
    public static void  delete_record(String table, String filters) throws SQLException {
       
        try (Connection connection =dbConnection()) {
            if (connection != null) {
                String query = "DELETE FROM " + table + " WHERE "+table.toLowerCase()+"Id =" + filters;
                
                try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                    pstmt.executeUpdate();
                    System.out.println("Record deleted successfully");
                } catch (SQLException e) {
                    System.out.println("An error occurred while deleting record: " + e.getMessage());
                    throw e;
                }
        }
    }
    finally{
        close_Connection();
    }
}
/**
 * Function to fetch all column names and their corresponding values from a table
 * @param table type String: The table name from which the data will be fetched
 * @return type: HashMap<String, List<String>> returns the column name as key and the fetched data as value
 * @throws SQLException
 */
public static HashMap<String, List<String>> fetchAllRecords(String table) throws SQLException {
    HashMap<String, List<String>> result = new HashMap<>();

    try (Connection connection = dbConnection()) {
        String query = "SELECT * FROM " + table;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet resultSet = pstmt.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Initialize the result map with column names
            for (int i = 1; i <= columnCount; ++i) {
                result.put(metaData.getColumnName(i), new ArrayList<>());
            }

            // Populate the result map with data
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; ++i) {
                    result.get(metaData.getColumnName(i)).add(resultSet.getString(i));
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while fetching records: " + e.getMessage());
            throw e;
        }
    } catch (SQLException e) {
        System.out.println("An error occurred while establishing the connection: " + e.getMessage());
        throw e;
    } finally {
        close_Connection();
    }
    return result;
}

}