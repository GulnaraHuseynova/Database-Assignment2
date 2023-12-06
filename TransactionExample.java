import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class TransactionExample {

    private static final String JDBC_URL = "jdbc:postgresql://your-database-url";
    private static final String USER = "Gulnara Huseynova";
    private static final String PASSWORD = "your-password";

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");

            System.out.println("Opening a connection...");

            try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
                connection.setAutoCommit(false);

                try {
                    int customerId = 1;
                    int bookId = 1;
                    int quantity = 1;

                    if (isStockAvailable(connection, bookId, quantity)) {
                        insertOrder(connection, customerId, bookId, quantity);
                        updateBookStock(connection, bookId, quantity);
                        connection.commit();
                    } else {
                        System.out.println("Insufficient stock. Order not placed.");
                    }
                } catch (SQLException e) {
                    connection.rollback();
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isStockAvailable(Connection connection, int bookId, int quantity) throws SQLException {
        String checkStockQuery = "SELECT StockQuality FROM Books WHERE BookId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkStockQuery)) {
            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int currentStock = resultSet.getInt("StockQuality");
                    return currentStock >= quantity;
                }
            }
        }
        return false;
    }

    private static void insertOrder(Connection connection, int customerId, int bookId, int quantity) throws SQLException {
        String insertOrderQuery = "INSERT INTO Orders (CustomerId, OrderDate) VALUES (?, NOW())";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, customerId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);
                        System.out.println("Order ID: " + orderId);
                    }
                }
            }
        }
    }

    private static void updateBookStock(Connection connection, int bookId, int quantity) throws SQLException {
        String updateStockQuery = "UPDATE Books SET StockQuality = StockQuality - ? WHERE BookId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateStockQuery)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, bookId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " rows updated in Books table.");
        }
    }
}
