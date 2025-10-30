
package view;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

import model.UsersModel;

public class MainApplicationFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private UsersModel currentUser;

    public MainApplicationFrame(UsersModel user) throws SQLException {
        this.currentUser = user;
        this.init();
    }

    private void init() throws SQLException {
        this.setTitle("Car Manage Software");
        this.setSize(1050, 620);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        // Main panel với BorderLayout
        JPanel root = new JPanel(new BorderLayout());
        setContentPane(root);

        // ============================ HEADER SECTION ============================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setPreferredSize(new Dimension(0, 64));

        JLabel appTitle = new JLabel("Car Manage Software");
        appTitle.setBorder(new EmptyBorder(0, 16, 0, 0));
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(appTitle, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        userPanel.setOpaque(false);

        // Hiển thị thông tin user đăng nhập
        String roleText = currentUser.isAdmin() ? "Quản trị viên" : "Nhân viên";
        JLabel userLabel = new JLabel(currentUser.getTenNV() + " (" + roleText + ")");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Nút đăng xuất
        JButton logoutButton = new JButton("Đăng xuất");
        logoutButton.setFocusPainted(false);
        logoutButton.setBackground(new Color(135, 206, 235));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn đăng xuất?",
                    "Xác nhận đăng xuất",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                this.dispose();
                new LoginFrame().setVisible(true);
            }
        });

        userPanel.add(userLabel);
        userPanel.add(logoutButton);
        headerPanel.add(userPanel, BorderLayout.EAST);

        root.add(headerPanel, BorderLayout.NORTH);

        // ============================ SIDEBAR SECTION ============================
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(12, 12, 12, 12));
        sidebar.setBackground(new Color(250, 250, 250));
        sidebar.setPreferredSize(new Dimension(220, 0));

        ButtonGroup menuGroup = new ButtonGroup();

        // Tạo các nút menu riêng biệt
        JToggleButton btnThongKe = new JToggleButton("Thống kê");
        JToggleButton btnSanPham = new JToggleButton("Sản phẩm");
        JToggleButton btnKhachHang = new JToggleButton("Khách hàng");
        JToggleButton btnNhanVien = new JToggleButton("Nhân viên");
        JToggleButton btnGiaoDich = new JToggleButton("Giao dịch");

        // Thiết lập thuộc tính cho các nút
        JToggleButton[] buttons = {btnThongKe, btnSanPham, btnKhachHang, btnNhanVien, btnGiaoDich};
        for (JToggleButton button : buttons) {
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setFocusPainted(false);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            menuGroup.add(button);
            sidebar.add(button);
            sidebar.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        // Ẩn nút quản lý nhân viên nếu không phải admin
        if (!currentUser.isAdmin()) {
            btnNhanVien.setVisible(false);
        }

        // Chọn thống kê làm mặc định
        btnThongKe.setSelected(true);

        root.add(sidebar, BorderLayout.WEST);

        // ============================ CONTENT SECTION ============================
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        // Tạo các panel nội dung
        JPanel panelThongKe = createThongKePanel();
        JPanel panelSanPham = new ProductPanel();
        JPanel panelKhachHang = new CustomerPanel();
        JPanel panelNhanVien = new StaffPanel();
        JPanel panelGiaoDich = createGiaoDichPanel();

        // Thêm các panel vào content
        contentPanel.add(panelThongKe, "Thống kê");
        contentPanel.add(panelSanPham, "Sản phẩm");
        contentPanel.add(panelKhachHang, "Khách hàng");
        contentPanel.add(panelNhanVien, "Nhân viên");
        contentPanel.add(panelGiaoDich, "Giao dịch");

        root.add(contentPanel, BorderLayout.CENTER);

        // ============================ ADD ACTION LISTENERS ============================
        btnThongKe.addActionListener(e -> cardLayout.show(contentPanel, "Thống kê"));
        btnSanPham.addActionListener(e -> cardLayout.show(contentPanel, "Sản phẩm"));
        btnKhachHang.addActionListener(e -> cardLayout.show(contentPanel, "Khách hàng"));
        btnNhanVien.addActionListener(e -> cardLayout.show(contentPanel, "Nhân viên"));
        btnGiaoDich.addActionListener(e -> cardLayout.show(contentPanel, "Giao dịch"));
    }

    // ============================ PANEL CREATION METHODS ============================

    private JPanel createThongKePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Thống Kê & Báo Cáo");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Nội dung thống kê
        JPanel content = new JPanel(new GridLayout(2, 2, 10, 10));
        content.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Các card thống kê
        content.add(createStatCard("Tổng Sản Phẩm", "150", new Color(70, 130, 180)));
        content.add(createStatCard("Tổng Khách Hàng", "45", new Color(60, 179, 113)));
        content.add(createStatCard("Tổng Nhân Viên", "8", new Color(255, 165, 0)));
        content.add(createStatCard("Doanh Thu Tháng", "125.000.000đ", new Color(186, 85, 211)));

        panel.add(content, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createKhachHangPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Quản Lý Khách Hàng");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Placeholder content
        JLabel placeholder = new JLabel("Nội dung quản lý khách hàng sẽ được thêm vào đây", JLabel.CENTER);
        placeholder.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        placeholder.setForeground(Color.GRAY);
        panel.add(placeholder, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createNhanVienPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Quản Lý Nhân Viên");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        if (currentUser.isAdmin()) {
            // Placeholder content cho admin
            JLabel placeholder = new JLabel("Quản lý nhân viên - Chỉ dành cho quản trị viên", JLabel.CENTER);
            placeholder.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            placeholder.setForeground(Color.GRAY);
            panel.add(placeholder, BorderLayout.CENTER);
        } else {
            JLabel accessDenied = new JLabel("Bạn không có quyền truy cập tính năng này", JLabel.CENTER);
            accessDenied.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            accessDenied.setForeground(Color.RED);
            panel.add(accessDenied, BorderLayout.CENTER);
        }

        return panel;
    }

    private JPanel createGiaoDichPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Quản Lý Giao Dịch");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Placeholder content
        JLabel placeholder = new JLabel("Nội dung quản lý giao dịch sẽ được thêm vào đây", JLabel.CENTER);
        placeholder.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        placeholder.setForeground(Color.GRAY);
        panel.add(placeholder, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(16, 16, 16, 16)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    // Getter cho currentUser
    public UsersModel getCurrentUser() {
        return currentUser;
    }
}