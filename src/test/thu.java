//package test;
//
//import database.JDBC_Util;
//import dao.UsersDAO;
//import view.LoginFrame;
//import javax.swing.*;
//
//public class thu {
//    public static void main(String[] args) {
//        System.out.println("=== BẮT ĐẦU CHẠY ỨNG DỤNG ===");
//        System.out.println("🔍 Kiểm tra kết nối database...");
//
//        // Test kết nối database
//        try {
//            JDBC_Util.getConnection();
//            System.out.println("✓ Kết nối database THÀNH CÔNG");
//
//            // Test in thông tin database
//            System.out.println("📊 Thông tin database:");
//            JDBC_Util.printInfo(JDBC_Util.getConnection());
//
//            // Kiểm tra bảng nhanvien
//            UsersDAO usersDAO = new UsersDAO();
//            if (!usersDAO.checkNhanVienTable()) {
//                System.out.println("⚠️ CẢNH BÁO: Bảng nhanvien trống hoặc không tồn tại");
//                System.out.println("💡 Hãy kiểm tra xem bảng nhanvien đã có dữ liệu chưa");
//            }
//
//        } catch (Exception e) {
//            System.out.println("✗ Lỗi kết nối database: " + e.getMessage());
//            JOptionPane.showMessageDialog(null,
//                    "Không thể kết nối đến database!\n" +
//                            "Vui lòng kiểm tra:\n" +
//                            "- MySQL đã chạy chưa?\n" +
//                            "- Database 'qlchoto' đã tồn tại chưa?\n" +
//                            "- Tên đăng nhập và mật khẩu đúng chưa?",
//                    "Lỗi Database",
//                    JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Set look and feel
//        try {
//            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
//            System.out.println("✓ Thiết lập giao diện thành công");
//        } catch (Exception e) {
//            System.out.println("✗ Lỗi thiết lập giao diện: " + e.getMessage());
//        }
//
//        // Chạy ứng dụng
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    new LoginFrame();
//                    System.out.println("✅ Giao diện đăng nhập đã sẵn sàng");
//                    System.out.println("🎮 Sử dụng tài khoản từ bảng NHANVIEN để đăng nhập");
//                } catch (Exception e) {
//                    System.out.println("✗ Lỗi khởi tạo giao diện: " + e.getMessage());
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//}