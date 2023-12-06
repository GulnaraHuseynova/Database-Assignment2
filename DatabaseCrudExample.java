import java.sql.*;

public class DatabaseCrudExample {
    private static final String JDBC_URL = "jdbc:postgresql://your-database-url";
    private static final String USER = "your-username";
    private static final String PASSWORD = "your-password";

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");

            System.out.println("Connecting to the database...");
            try (Connection connection = DriverManager.getConnection('')) {
                System.out.println("Inserting a new book...");
                int newAuthorId = 1;
                retrieveAllBooks(connection);
                insertBook(connection, "New BookTitle", newAuthorId, 50, 19.99);
            }
            System.out.println("Connection is closed...");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void retrieveAllBooks(Connection connection) {
        String retrieveQuery = "SELECT b.BookId, b.Title, b.StockQuality, b.Price, a.AuthorName, o.OrderId " +
                "FROM Books b " +
                "JOIN Authors a ON b.AuthorId = a.AuthorId " +
                "LEFT JOIN Orders o ON b.BookId = o.BookId";
        try (PreparedStatement preparedStatement = connection.prepareStatement(retrieveQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int bookId = resultSet.getInt("BookId");
                String bookTitle = resultSet.getString("Title");
                int stockQuality = resultSet.getInt("StockQuality");
                double price = resultSet.getDouble("Price");
                String authorName = resultSet.getString("AuthorName");
                int orderId = resultSet.getInt("OrderId");

                System.out.println("Book ID: " + bookId);
                System.out.println("Title: " + bookTitle);
                System.out.println("Stock Quality: " + stockQuality);
                System.out.println("Price: " + price);
                System.out.println("Author Name: " + authorName);
                System.out.println("Order Id: " + (orderId == 0 ? "Not ordered" : orderId));

                System.out.println("------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertBook(Connection connection, String title, int authorId, int stockQuality, double price) {
        String insertQuery = "INSERT INTO Books (Title, AuthorId, StockQuality, Price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, authorId);
            preparedStatement.setInt(3, stockQuality);
            preparedStatement.setDouble(4, price);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " rows inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateBook(Connection connection, int bookId, String title, int stockQuality, double price)
    throws SQLException{
        String updateQuery = "UPDATE Books SET Title = ?, StockQuality = ?, Price = ?, Where BookId - ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)){
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, stockQuality);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, bookId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected+" rows updated");
        }
    }

    private static void deleteBook(Connection connection, int BookId)
    throws SQLException{
        String deleteQuery ="DELETE FROM BOOKS WHERE BookId =?";
        try(PreparedStatement preparedStatement  = connection.prepareStatement(deleteQuery)){
            preparedStatement.setInt(1, BookId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " rows deleted");
        }

    }

    
    }
