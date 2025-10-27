package test;

import view.MainApplicationFrame;
import view.ProductPanel;
import javax.swing.*;
import java.awt.*;

public class MainTest {
    public static void main(String[] args) {
        // Thiết lập Look and Feel cho ứng dụng
        //        // Tạo ProductPanel và thêm vào frame
        MainApplicationFrame viewFrm = new MainApplicationFrame();
        viewFrm.setVisible(true);
        ProductPanel productPanel = new ProductPanel();
//        mainPanel.add(productPanel, BorderLayout.CENTER);
//        // Hiển thị frame
//        mainFrame.setVisible(true);
    }
}

//    private static void createAndShowGUI() {
//        // Tạo frame chính
//        JFrame mainFrame = new JFrame("Hệ Thống Quản Lý Ô Tô - Demo");
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainFrame.setSize(1200, 700);
//        mainFrame.setLocationRelativeTo(null);
//
//        // Tạo panel chính với BorderLayout
//        JPanel mainPanel = new JPanel(new BorderLayout());
//        mainFrame.setContentPane(mainPanel);
//
//        // Tạo header
//        JLabel headerLabel = new JLabel("HỆ THỐNG QUẢN LÝ Ô TÔ - DEMO TEST", SwingConstants.CENTER);
//        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
//        headerLabel.setForeground(new Color(0, 100, 200));
//        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
//        headerLabel.setBackground(new Color(240, 245, 255));
//        headerLabel.setOpaque(true);
//        mainPanel.add(headerLabel, BorderLayout.NORTH);
//
//        // Tạo ProductPanel và thêm vào frame
//        ProductPanel productPanel = new ProductPanel();
//        mainPanel.add(productPanel, BorderLayout.CENTER);
//
//        // Tạo footer
//        JLabel footerLabel = new JLabel("© 2024 Hệ Thống Quản Lý Ô Tô - Demo Version", SwingConstants.CENTER);
//        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
//        footerLabel.setForeground(Color.GRAY);
//        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        mainPanel.add(footerLabel, BorderLayout.SOUTH);
//
//        // Tạo ProductPanel và thêm vào frame
//        ProductPanel productPanel = new ProductPanel();
//        mainPanel.add(productPanel, BorderLayout.CENTER);
//        // Hiển thị frame
//        mainFrame.setVisible(true);
//
//        System.out.println("Ứng dụng demo đã khởi động thành công!");
//        System.out.println("Nhấn nút 'Sửa' để mở cửa sổ chỉnh sửa thông tin ô tô.");
//    }
//}