import java.sql.*;

public class DatabaseCrudExample {
    private static final String JDBC_URL ="";
    private static final String USER ="Gulnara Huseynova";
    private static final String PASSWORD ="";

    public static void main(String args[]){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to database...");
            try(Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)){
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
    
    }
