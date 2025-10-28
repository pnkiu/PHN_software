package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class CarManageView extends JFrame {
    private JButton btnThongKe;
    private JButton btnSanPham;
    private JButton btnKhachHang;
    private JButton btnNhanVien;
    private JButton btnGiaoDich;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JTable tableOtoBanChay;
    private DefaultTableModel tableModel;

    public CarManageView() {
        this.init();
    }

    public void init() {
        Font font = new Font("Arial", Font.BOLD, 40);
        this.setTitle("Car Manage Software");
        this.setSize(1050, 620);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        JPanel root = new JPanel(new BorderLayout());
        setContentPane(root);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setPreferredSize(new Dimension(0, 64));

        JLabel appTitle = new JLabel("PHẦN MỀM QUẢN LÝ ÔTÔ");
        appTitle.setBorder(new EmptyBorder(0, 16, 0, 0));
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(appTitle, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        userPanel.setOpaque(false);
        JLabel userLabel = new JLabel("Quản trị viên");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userPanel.add(userLabel);

        JButton btnDangXuat = new JButton("ĐĂNG XUẤT");
        btnDangXuat.setFocusPainted(false);
        btnDangXuat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnDangXuat.setBackground(new Color(220, 53, 69));
        btnDangXuat.setForeground(Color.WHITE);
        btnDangXuat.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        btnDangXuat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        userPanel.add(btnDangXuat);

        headerPanel.add(userPanel, BorderLayout.EAST);
        root.add(headerPanel, BorderLayout.NORTH);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(12, 12, 12, 12));
        sidebar.setBackground(new Color(250, 250, 250));
        sidebar.setPreferredSize(new Dimension(220, 0));

        JLabel jLabel_title = new JLabel("ADMIN", SwingConstants.CENTER);
        jLabel_title.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel_title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        jLabel_title.setForeground(new Color(50, 50, 50));
        sidebar.add(jLabel_title);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        Dimension buttonSize = new Dimension(180, 40);

        btnThongKe = new JButton("THỐNG KÊ");
        btnSanPham = new JButton("SẢN PHẨM");
        btnKhachHang = new JButton("KHÁCH HÀNG");
        btnNhanVien = new JButton("NHÂN VIÊN");
        btnGiaoDich = new JButton("GIAO DỊCH");

        JButton[] buttons = {btnThongKe, btnSanPham, btnKhachHang, btnNhanVien, btnGiaoDich};
        for (JButton button : buttons) {
            button.setMaximumSize(buttonSize);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setFocusPainted(false);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            sidebar.add(button);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // thực hiện chuyển cửa số
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        JPanel ProductPanel = new ProductView();
        contentPanel.add(ProductPanel, "Sản phẩm");



        root.add(sidebar, BorderLayout.WEST);
        root.add(contentPanel, BorderLayout.CENTER);

        btnSanPham.addActionListener(e -> cardLayout.show(contentPanel, "Sản phẩm"));

        this.setVisible(true);
    }

    // thống kê
    private JPanel createThongKePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel jLabel_header = new JLabel("THỐNG KÊ BÁN HÀNG", SwingConstants.CENTER);
        jLabel_header.setForeground(Color.BLACK);
        jLabel_header.setFont(new Font("Arial", Font.BOLD, 32));
        panel.add(jLabel_header, BorderLayout.NORTH);

        JPanel panel_filter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel_filter.setBackground(Color.WHITE);

        JLabel jLabelFilter = new JLabel("LỌC:");
        jLabelFilter.setFont(new Font("Arial", Font.BOLD, 16));
        panel_filter.add(jLabelFilter);

        String[] filterOptions = {"Năm", "Tháng", "Ngày"};
        JComboBox<String> comboLocTheo = new JComboBox<>(filterOptions);
        panel_filter.add(comboLocTheo);

        JLabel jLabelStart = new JLabel("TỪ:");
        jLabelStart.setFont(new Font("Arial", Font.BOLD, 16));
        panel_filter.add(jLabelStart);
        JSpinner jSpinner_start = createDatePicker();
        panel_filter.add(jSpinner_start);

        JLabel jLabelEnd = new JLabel("ĐẾN:");
        jLabelEnd.setFont(new Font("Arial", Font.BOLD, 16));
        panel_filter.add(jLabelEnd);
        JSpinner jSpinner_end = createDatePicker();
        panel_filter.add(jSpinner_end);

        JButton buttonLoc = new JButton("Lọc");
        buttonLoc.setBackground(new Color(15, 110, 180));
        buttonLoc.setForeground(Color.WHITE);
        panel_filter.add(buttonLoc);

        panel.add(panel_filter, BorderLayout.NORTH);

        String[] columnNames = {"Tên xe", "Tên hãng", "Số lượt bán"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableOtoBanChay = new JTable(tableModel);
        tableOtoBanChay.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tableOtoBanChay.setFont(new Font("Arial", Font.PLAIN, 14));
        tableOtoBanChay.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tableOtoBanChay);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "DANH SÁCH XE BÁN CHẠY NHẤT",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                Color.BLACK
        ));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JSpinner createDatePicker() {
        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);
        return spinner;
    }
}
