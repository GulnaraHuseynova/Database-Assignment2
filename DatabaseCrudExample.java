import java.postgresql.*;


public class DatabaseCrudExample {
    private static final String JDBC_URL ="";
    private static final String USER ="Gulnara Huseynova";
    private static final String PASSWORD ="";

    public static void main(String args[]){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to database...");
            try(Connection connection = DriverManager.getConnection(''){
                System.out.println("Inserting new book...");
                int newAuthorId = 1;
                retriveAllBooks(connection);
                insertBook(connection, "New BookTitle", newAuthorId, 50, 19.99);
                

            }
            System.out.println("Connection is closed...");
        } catch (ClassNotFoundException  SQLException e){
            e.printStackTrace();
        }
    }


        private static void retriveAllBooks(Conncetion connection) throws SQLException{
            String retriveQuery = "Select b.BoolId, b.Title, b.StockQuality, b.Price,  a.AuthorName, o.OrderId"+
            "FROM BOOKS b"+
            "JOIN Authors a ON b.AuthorId = a.AuthorId" +
            "LEFT JOIN Orders o ON b.BookId = o.BookId";
            try(PreparedStatement preparedStatement = connection.preparedStatement(retriveQuery);
                ResultSet resultSet = preparedStatement.executeQuery()){
                    while(resultSet.next()){
                        int bookId = resultSet.getInt("BookId");
                        String bookTitle = resultSet.getString("Title");
                        int stockQuality = resultSet.getInt("StockQuality");
                        double price = resultSet.getDouble("Price");
                        String authorName = resultSet.getString("AuthorName");
                        int orderId = resultSet.getInt("OrderId");


                        System.out.println("Book ID: "+bookId);
                        System.out.println("Title: "+ bookTitle);
                        System.out.println("Stock Quality: "+stockQuality);
                        System.out.println("Price: "+price);
                        System.out.println("Author Name: "+authorName);
                        System.out.println("Order Id: "+ (orderId)==0 ? "Not ordered" : orderId);

                        System.out.println("------------------------------------------");

                    }
            }
        }

    private static void insertBook(Conncetion connection, String title, int authorId, int StockQuality, double price)
        throws SQLException{

        String insertQuery = "INSERT INTO Books (Title, AuthorId, StockQuality, Price) values (?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.preparedStatement(insertQuery)){
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, authorId);
            preparedStatement.setInt(3, StockQuality);
            preparedStatement.setDouble(4, price);

            int rowsAffected  = preparedStatement.executeUpdate();
            System.out.println(rowsAffected+" rows inserted.");
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
