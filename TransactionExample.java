import java.beans.Statement;
import java.postgresql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class TransactionExample {
    private static final String JDBC_URL = "";
    private static final String USER = "Gulnara huseynova";
    private static final String PASSWORD = "";


    public static void main(String args[]){
        try{
            Class.forName("com.postgresql.cd.jdbc.Driver");

            System.out.println("open a conncetion..");

            try(Connection conncetion = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)){
                conncetion.setAutoCommit(false);

                try{
                    int customerId = 1;
                    int bookId = 1;
                    int quantity = 1;
                }
            }
        }

    }

private static boolean isStockAvailabel(Connection connection, int bookId, int quantity)
throws SQLException{
    String checkStockQuery = "SELECT StockQuality FROM Boos WHERE BookId = ?";
    try(PreparedStatement preparedStatement  connection.prepareStatement(checkStockQuery)){
        preparedStatement.setInt(1, bookId);
        try(ResultSet resultSet = preparedStatement.executeQuery()){
            if(resultSet.next()){
                int currentStock = resultSet.getInt("StockQuality");
                return currentStock >= quantity;
            }
        }
    }
    return false;
}


private static void insertOrder(Conncetion conncetion, int customerId, int bookId, int quantity )
throws SQLException{
    String insertOrderQuery = "INSERT INTO ORDERS (CustomerId, OderDate) VALUES (?, NOW())";
    try(PreparedStatement preparedStatement = conncetion.preparedStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)){
        preparedStatement.setInt(1, customerId);

        int rowsAffected = preparedStatement.executeUpdate();

        if(rowsAffected==1){
            try(ResultSet generatedkeys = preparedStatement.getGeneratedKeys()){
                if(generatedkeys.next()){
                    int orderId = generatedKeys.getInt(1);
                    System.out.println("Order id: "+orderId);
                }
            }
        }
    }
}


private static void updateBookStock(Connetion connection, int bookId, int quantity) throws SQLException{
    String updateStockQuery = "UPDATE Books SET StockQuality = StockQuality -? WHERE BookId = ?";
    try(PreparedStatement preparedStatement= connection.preparedStatement(updateStockQuery)){
        preparedStatement.setInt(1, quantity);
        preparedStatement.setInt(2, bookId);

        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println(rowsAffected+" wors updated in books table.");

    }
}

    //---------------------

}
