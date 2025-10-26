package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB {
    
// Database Connection Class
 // Hàm tạo kết nối đến MySQL
    public static Connection getConnection() {
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/qlchoto"; // tên database
        String user = "root"; // user mặc định của XAMPP
        String password = ""; // nếu bạn không đặt mật khẩu trong XAMPP thì để trống

        try {
            // Nạp driver MySQL (JDBC)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Tạo kết nối
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Kết nối MySQL thành công!");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Không tìm thấy Driver MySQL: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Kết nối thất bại: " + e.getMessage());
        }
        return conn;
    }

    // Hàm main để kiểm tra kết nối
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("🎉 Đã kết nối tới cơ sở dữ liệu qlyoto!");
            } else {
                System.out.println("⚠️ Không thể kết nối tới cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra kết nối: " + e.getMessage());
        }
    }
    
}
