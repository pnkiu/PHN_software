////
////            package test;
////
////    import view.MainApplicationFrame;
////    import javax.swing.*;
////
////public class MainTest {
////    public static void main(String[] args) {
////        // Thiết lập Look and Feel
////        try {
////            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////        // Chạy ứng dụng trong Event Dispatch Thread
////        SwingUtilities.invokeLater(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    MainApplicationFrame frame = new MainApplicationFrame();
////                    frame.setVisible(true);
////                    System.out.println("Ứng dụng đã khởi động thành công!");
////                } catch (Exception e) {
////                    JOptionPane.showMessageDialog(null,
////                            "Lỗi khi khởi động ứng dụng: " + e.getMessage(),
////                            "Lỗi", JOptionPane.ERROR_MESSAGE);
////                    e.printStackTrace();
////                }
////            }
////        });
////    }
////}
//
//package test;
//
//import view.LoginFrame;
//import view.MainApplicationFrame;
//import model.UsersModel;
//import javax.swing.*;
//
//public class MainTest {
//    public static void main(String[] args) {
//        // Thiết lập Look and Feel
//        try {
//            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Chạy ứng dụng trong Event Dispatch Thread
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    new LoginFrame().setVisible(true);
//
//                    // Tạo user test với thông tin từ database của bạn
//
//                    UsersModel testUser = new UsersModel();
////                    testUser.setMaNV("");
////                    testUser.setTenNV("");
////                    testUser.setChucVu(1); // 1 = Admin
////                    testUser.setTenDangNhap("");
//
//
//                    MainApplicationFrame frame = new MainApplicationFrame(testUser);
//                    frame.setVisible(true);
//                    System.out.println("✅ Ứng dụng đã khởi động thành công (Test Mode)!");
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null,
//                            "Lỗi khi khởi động ứng dụng: " + e.getMessage(),
//                            "Lỗi", JOptionPane.ERROR_MESSAGE);
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//}
package test;

import view.LoginFrame;
import view.MainApplicationFrame;
import model.UsersModel;
import javax.swing.*;

public class MainTest {
    public static void main(String[] args) {
        // Thiết lập Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Chạy ứng dụng trong Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Chỉ khởi tạo và hiển thị LoginFrame
                    LoginFrame loginFrame = new LoginFrame();
                    loginFrame.setVisible(true);

                    System.out.println("✅ Ứng dụng đã khởi động - Hiển thị trang Login");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Lỗi khi khởi động ứng dụng: " + e.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
    }
}
