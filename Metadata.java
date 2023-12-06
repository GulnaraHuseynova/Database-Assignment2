import java.sql.*;

public class Metadata {
    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://your-database-url:3306/your-database-name";
    private static final String USER = "your-username";
    private static final String PASSWORD = "your-password";

    public static void main(String[] args) {
        try {
            // Step 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Open a connection
            System.out.println("Connecting to database...");
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {

                // Step 3: Access metadata
                DatabaseMetaData metaData = connection.getMetaData();

                // Display table names and structures
                displayTableNamesAndStructures(metaData);

                // Display details on columns of tables
                displayColumnDetails(metaData);

                // Display information on primary and foreign keys
                displayKeysInformation(metaData);
            }

            // Step 4: Close the connection
            System.out.println("Connection closed.");

        } catch (ClassNotFoundException | SQLException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    // Method to display table names and structures
    private static void displayTableNamesAndStructures(DatabaseMetaData metaData) throws SQLException {
        System.out.println("Table Names and Structures:");
        ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            System.out.println("Table: " + tableName);

            // Display column names and types
            ResultSet columns = metaData.getColumns(null, null, tableName, "%");
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String columnType = columns.getString("TYPE_NAME");
                System.out.println("   Column: " + columnName + ", Type: " + columnType);
            }
            columns.close();

            System.out.println("-----------");
        }
        tables.close();
    }

    // Method to display details on columns of tables
    private static void displayColumnDetails(DatabaseMetaData metaData) throws SQLException {
        System.out.println("Column Details:");
        ResultSet columns = metaData.getColumns(null, null, null, "%");
        while (columns.next()) {
            String tableName = columns.getString("TABLE_NAME");
            String columnName = columns.getString("COLUMN_NAME");
            String dataType = columns.getString("TYPE_NAME");
            int columnSize = columns.getInt("COLUMN_SIZE");
            boolean isNullable = columns.getBoolean("IS_NULLABLE");

            System.out.println("Table: " + tableName +
                    ", Column: " + columnName +
                    ", Type: " + dataType +
                    ", Size: " + columnSize +
                    ", Nullable: " + (isNullable ? "Yes" : "No"));
        }
        columns.close();
    }

    // Method to display information on primary and foreign keys
    private static void displayKeysInformation(DatabaseMetaData metaData) throws SQLException {
        System.out.println("Keys Information:");
        ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, null);
        while (primaryKeys.next()) {
            String tableName = primaryKeys.getString("TABLE_NAME");
            String primaryKeyColumn = primaryKeys.getString("COLUMN_NAME");
            System.out.println("Primary Key: " + primaryKeyColumn + " in Table: " + tableName);
        }
        primaryKeys.close();

        ResultSet foreignKeys = metaData.getImportedKeys(null, null, null);
        while (foreignKeys.next()) {
            String tableName = foreignKeys.getString("FKTABLE_NAME");
            String columnName = foreignKeys.getString("FKCOLUMN_NAME");
            String referencedTable = foreignKeys.getString("PKTABLE_NAME");
            String referencedColumnName = foreignKeys.getString("PKCOLUMN_NAME");

            System.out.println("Foreign Key: " + columnName + " in Table: " + tableName +
                    " references " + referencedColumnName + " in Table: " + referencedTable);
        }
        foreignKeys.close();
    }
}
