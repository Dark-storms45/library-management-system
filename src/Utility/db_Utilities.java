package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
 import Utility.Utilities;
import java.util.*;


public class db_Utilities {
private static final int sec=1500;
    private static final String url = "jdbc:sqlite:src/database/database.sql";

    public static Connection dbConnection() throws SQLException {

        Connection connection = DriverManager.getConnection(url);
        if (connection != null) {
            System.out.println("Connection to SQLite has been established.");

        }
        return connection;
    }

    public static void close_Connection() throws SQLException {

        Connection connection = dbConnection();

        try {
            if (connection != null) {

                connection.close();

            }
        } catch (SQLException e) {
            System.out.println("An error occured while closing the database" + e.getMessage());
        }
    }

    /**
     * function to creat the  tables  in the database if it does not exist yet
     *
     * @throws SQLException
     */
    public static void creatTables() throws SQLException {
        HashMap<String, String> Tables = new HashMap<>();

        Tables.put("Members", "CREATE TABLE IF NOT EXISTS Members (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MemberName TEXT NOT NULL, " +
                "MemberEmail TEXT NOT NULL, " +
                "MemberContact TEXT NOT NULL, " +
                "MembershipType TEXT NOT NULL, " +
                "MembershipFee TEXT NOT NULL, " +
                "MembershipExpiry DATETIME NOT NULL );");

        Tables.put("Librant", "CREATE TABLE IF NOT EXISTS Librant (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "LibrantName TEXT NOT NULL, " +
                "LibrantEmail TEXT NOT NULL, " +
                "Password TEXT NOT NULL, " +
                "Role TEXT NOT NULL, " +
                "LibrantContact TEXT NOT NULL, " +
                "LibrantAddress TEXT NOT NULL, " +
                "LibrantSalary REAL, " +
                "LibrantSex TEXT NOT NULL);");

        Tables.put("Books", "CREATE TABLE IF NOT EXISTS Books (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "ISBN TEXT , " +
                "Title TEXT NOT NULL, " +
                "Author TEXT NOT NULL, " +
                "Publisher TEXT NOT NULL, " +
                "PublicationYear TEXT NOT NULL, " +
                "Genre TEXT NOT NULL, " +
                "Quantity INTEGER NOT NULL);");

        Tables.put("Transaction", "CREATE TABLE IF NOT EXISTS Transactions (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "MemberId INTEGER NOT NULL, " +
                "BookId TEXT NOT NULL, " +
                "IssueDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "DueDate DATETIME NOT NULL, " +
                "FineAmount REAL NOT NULL, " +
                "FOREIGN KEY(MemberId) REFERENCES Members(Id), " +
                "FOREIGN KEY(BookId) REFERENCES Books(Id));");

        Tables.put("Reservation", "CREATE TABLE IF NOT EXISTS reservation (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MemberId INTEGER NOT NULL, " +
                "BookId TEXT NOT NULL, " +
                "ReservationDate DateTime NOT NULL, " +
                "FOREIGN KEY(MemberId) REFERENCES Members(Id), " +
                "FOREIGN KEY(BookId) REFERENCES Books(Id));");

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
     * @param record type Hashmap : key-column where the field will be added and data added -value
     * @param table  type String : table name in which the data will be added
     * @throws SQLException
     */
    public static void add_record(HashMap<String, Object> record, String table) throws SQLException {
        dbConnection();
        if (dbConnection() != null) {
            String columns = String.join(",", record.keySet());
            String placeholders = String.join(",", Collections.nCopies(record.size(), "?"));
            String query = "INSERT INTO " + table + " (" + columns + ") VALUES (" + placeholders + ")";

            try (PreparedStatement pstmt = dbConnection().prepareStatement(query)) {
                int index = 1;
                for (String key : record.keySet()) {
                    Object value = record.get(key);

                    // Dynamically set the correct type
                    if (value instanceof String) {
                        pstmt.setString(index++, (String) value);
                    } else if (value instanceof Integer) {
                        pstmt.setInt(index++, (Integer) value);
                    } else if (value instanceof Float) {
                        pstmt.setFloat(index++, (Float) value);
                    } else if (value instanceof Double) {
                        pstmt.setDouble(index++, (Double) value);
                    } else {
                        pstmt.setObject(index++, value); // Fallback for other types
                    }
                }
                pstmt.executeUpdate();
                System.out.println("Adding records...... ");
                Utilities.sleep(sec);
                System.out.println("Record added successfully");
            } catch (SQLException e) {
                System.out.println("An error occurred while adding record: " + e.getMessage());
                throw e;
            } finally {
                close_Connection();
            }
        }
    }

    /**
     * @param fields  type String : the  data to fetch from the database
     * @param table   type String: The table name from which the data wld be fetched
     * @param filters type String: used to filter the field(s) fetched
     * @return type: Hashmap  returns the column name as key and the fetch data as value
     * @throws SQLException
     */
    public static HashMap<String, String> fetch_record(String fields, String table, Object filters) throws SQLException {
        HashMap<String, String> result = new HashMap<>();

        try (Connection connection = dbConnection()) {
            String query = "SELECT  *   FROM " + table;
            if (!filters.equals("") && filters instanceof String) {
                query += " WHERE  " +fields+"= '"+filters+"'";
            }
            else if (!filters.equals("") && filters instanceof Integer) {
                query += " WHERE  " +fields+"= "+filters;
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
            } finally {
                close_Connection();
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while establishing the connection: " + e.getMessage());
            throw e;


        }
        return result;
    }


    /**
     * Function to update record in the database
     * @param record HashMap<String, Object>: Represents column-value pairs to be updated
     * @param table String: Table name where the update will happen
     * @param filters int: Filter condition, typically the ID of the record
     * @throws SQLException
     */
    public static void update_record(HashMap<String, Object> record, String table, int filters) throws SQLException {

        if (record == null || record.isEmpty()) {
            throw new IllegalArgumentException("Record map cannot be null or empty. Please provide valid data.");
        }

        try (Connection connection = dbConnection()) {
            if (connection != null) {
                // Build the query
                StringBuilder queryBuilder = new StringBuilder("UPDATE ").append(table).append(" SET ");

                for (Map.Entry<String, Object> entry : record.entrySet()) {
                    String column = entry.getKey();
                    Object value = entry.getValue();

                    // Determine the value type and build proper query syntax
                    if (value instanceof String) {
                        queryBuilder.append(column).append(" = '").append(value).append("', ");
                    } else {
                        queryBuilder.append(column).append(" = ").append(value).append(", ");
                    }
                }

                // Remove the trailing comma and space
                queryBuilder.setLength(queryBuilder.length() - 2);

                // Add the WHERE clause
                queryBuilder.append(" WHERE Id = ").append(filters);

                String query = queryBuilder.toString();

                // Execute the query
                try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                    pstmt.executeUpdate();
                    System.out.println("Updating record...... ");
                    Utilities.sleep(sec);
                    System.out.println("Record updated successfully.");
                } catch (SQLException e) {
                    System.out.println("An error occurred while updating the record: " + e.getMessage());
                    throw e;
                }
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while connecting to the database: " + e.getMessage());
            throw e;
        }
    }

    /**
     * function to  delete data  from a table in the database
     * @param table table from which the data wld be deleted
     * @param filters field to be deleted
     * @throws SQLException
     * @return  returns  null
     */
    public static void  delete_record(String table, Object filters) throws SQLException {
       
        try (Connection connection =dbConnection()) {
            if (connection != null) {
                String query = "DELETE FROM " + table + " WHERE "+"Id =" + filters;
                
                try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                    pstmt.executeUpdate();
                    System.out.println("deleting record......");
                    Utilities.sleep(sec);
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
public static HashMap<String, List<String>> fetchAllRecords(String table,Object filter,String field) throws SQLException {
    HashMap<String, List<String>> result = new HashMap<>();

    try (Connection connection = dbConnection()) {
        if (true) {
            String query = "SELECT * FROM " + table;
            if (!filter.equals("")) {
                if (filter instanceof String) {
                    query += " WHERE  " + field + "= '" + filter + "'";
                } else {
                    query += " WHERE  " + field + "=" + filter;
                }
            } else {


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
                    System.out.println("Fetching records...... ");
                    Utilities.sleep(sec);
                } catch (SQLException e) {
                    System.out.println("An error occurred while fetching records: " + e.getMessage());
                    throw e;
                }

            }
        }
    }

         catch(SQLException e){
            System.out.println("An error occurred while establishing the connection: " + e.getMessage());
            throw e;
        } finally{
            close_Connection();
        }
        return result;
    }


    }
