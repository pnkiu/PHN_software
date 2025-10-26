package test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class test1 {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/qlchoto";
        String username = "root";
        String password = "Ngocho";
 
        try {
            // Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully!");
 
            // Establish connection
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database!");
 
            // Close connection
            connection.close();
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver class not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred!");
            e.printStackTrace();
        }
    }
}
