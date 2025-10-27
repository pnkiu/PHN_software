package view;
import view.ProductPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class thu extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public thu() {
        this.init();
    }

    private void init() {

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
        headerPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0, new Color(220,220,220)));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setPreferredSize(new Dimension(0,64));

        JLabel appTitle = new JLabel("Car Manage Software");
        appTitle.setBorder(new EmptyBorder(0,16,0,0));
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(appTitle, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        userPanel.setOpaque(false);
        JLabel userLabel = new JLabel("Quản trị viên");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userPanel.add(userLabel);
        headerPanel.add(userPanel, BorderLayout.EAST);

        root.add(headerPanel, BorderLayout.NORTH);

        // ============================ SIDEBAR SECTION ============================
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(12,12,12,12));
        sidebar.setBackground(new Color(250,250,250));
        sidebar.setPreferredSize(new Dimension(220,0));

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
            sidebar.add(Box.createRigidArea(new Dimension(0,8)));
        }

        // Chọn thống kê làm mặc định
        btnThongKe.setSelected(true);

        root.add(sidebar, BorderLayout.WEST);

        // ============================ CONTENT SECTION ============================
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBorder(new EmptyBorder(16,16,16,16));

        // Tạo các panel nội dung
        JPanel panelThongKe = new JPanel(new BorderLayout());
        JLabel lblThongKe = new JLabel("Thống Kê & Báo Cáo");
        lblThongKe.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panelThongKe.add(lblThongKe, BorderLayout.NORTH);

        JPanel panelSanPham = new ProductPanel();

        JPanel panelKhachHang = new JPanel(new BorderLayout());
        JLabel lblKhachHang = new JLabel("Quản Lý Khách Hàng");
        lblKhachHang.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panelKhachHang.add(lblKhachHang, BorderLayout.NORTH);

        JPanel panelNhanVien = new JPanel(new BorderLayout());
        JLabel lblNhanVien = new JLabel("Quản Lý Nhân Viên");
        lblNhanVien.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panelNhanVien.add(lblNhanVien, BorderLayout.NORTH);

        JPanel panelGiaoDich = new JPanel(new BorderLayout());
        JLabel lblGiaoDich = new JLabel("Quản Lý Giao Dịch");
        lblGiaoDich.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panelGiaoDich.add(lblGiaoDich, BorderLayout.NORTH);

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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame frame = new MainApplicationFrame();
            frame.setVisible(true);
        });
    }
}