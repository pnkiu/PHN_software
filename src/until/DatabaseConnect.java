package until; // Bạn có thể đặt tên package là 'util' hoặc 'config'

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/qlchoto";
    private static final String USER = "root";
    private static final String PASS = "123456";
     //Lấy một kết nối đến CSDL
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể kết nối CSDL!", e);
        }
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
            }
        } catch (SQLException e) {
            System.err.println("Kiểm tra thất bại: " + e.getMessage());
        }
    }
}