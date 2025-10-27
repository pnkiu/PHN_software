package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class CarManageView extends JFrame {

    private JTable tableOtoBanChay;
    private DefaultTableModel tableModel;


    public CarManageView() {
        this.init();
    }

    public void init() {
        Font font = new Font("Arial", Font.BOLD, 40);
        Font fonts = new Font("Arial", Font.BOLD, 17);
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

        JLabel appTitle = new JLabel("Car Manage Software");
        appTitle.setBorder(new EmptyBorder(0, 16, 0, 0));
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(appTitle, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        userPanel.setOpaque(false);
        JLabel userLabel = new JLabel("Quản trị viên");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userPanel.add(userLabel);
        headerPanel.add(userPanel, BorderLayout.EAST);

        JButton btnDangXuat = new JButton("Đăng xuất");
        btnDangXuat.setFocusPainted(false);
        btnDangXuat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnDangXuat.setBackground(new Color(220, 53, 69)); // đỏ nhạt
        btnDangXuat.setForeground(Color.WHITE);
        btnDangXuat.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        btnDangXuat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        userPanel.add(btnDangXuat);

        root.add(headerPanel, BorderLayout.NORTH);

        // ============================ SIDEBAR SECTION ============================
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(12, 12, 12, 12));
        sidebar.setBackground(new Color(250, 250, 250));
        sidebar.setPreferredSize(new Dimension(220, 0));

        ButtonGroup menuGroup = new ButtonGroup();

        JLabel jLabel_title = new JLabel("ADMIN", SwingConstants.CENTER);
        jLabel_title.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel_title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        jLabel_title.setForeground(new Color(50, 50, 50));
        sidebar.add(jLabel_title);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40))); // khoảng cách dưới tiêu đề

        Dimension buttonSize = new Dimension(180, 40);
        Font buttonFont = new Font("Segoe UI", Font.PLAIN, 14);

        JButton btnThongKe = new JButton("Thống kê");
        btnThongKe.setMaximumSize(buttonSize);
        btnThongKe.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnThongKe.setFocusPainted(false);
        btnThongKe.setFont(buttonFont);
        menuGroup.add(btnThongKe);
        sidebar.add(btnThongKe);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnSanPham = new JButton("Sản phẩm");
        btnSanPham.setMaximumSize(buttonSize);
        btnSanPham.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSanPham.setFocusPainted(false);
        btnSanPham.setFont(buttonFont);
        menuGroup.add(btnSanPham);
        sidebar.add(btnSanPham);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnKhachHang = new JButton("Khách hàng");
        btnKhachHang.setMaximumSize(buttonSize);
        btnKhachHang.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKhachHang.setFocusPainted(false);
        btnKhachHang.setFont(buttonFont);
        menuGroup.add(btnKhachHang);
        sidebar.add(btnKhachHang);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnNhanVien = new JButton("Nhân viên");
        btnNhanVien.setMaximumSize(buttonSize);
        btnNhanVien.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNhanVien.setFocusPainted(false);
        btnNhanVien.setFont(buttonFont);
        menuGroup.add(btnNhanVien);
        sidebar.add(btnNhanVien);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnGiaoDich = new JButton("Giao dịch");
        btnGiaoDich.setMaximumSize(buttonSize);
        btnGiaoDich.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGiaoDich.setFocusPainted(false);
        btnGiaoDich.setFont(buttonFont);
        menuGroup.add(btnGiaoDich);
        sidebar.add(btnGiaoDich);


        //bên phải
        JPanel jPanel_right = new JPanel(new BorderLayout(10, 10));
        jPanel_right.setBackground(Color.WHITE);
        jPanel_right.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //tạo viền trống

        JLabel jLabel_header = new JLabel("HỆ THỐNG QUẢN LÝ CỬA HÀNG BÁN XE OTO", SwingConstants.CENTER);
        jLabel_header.setForeground(new Color(65, 105, 225));
        jLabel_header.setFont(font.deriveFont(Font.BOLD, 32f));
        jPanel_right.add(jLabel_header, BorderLayout.NORTH);


        JPanel panel_center_wrapper = new JPanel(new BorderLayout(10, 10));
        panel_center_wrapper.setBackground(Color.WHITE);
        // lọc
        JPanel panel_filter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel_filter.setBackground(Color.WHITE);

        //khúc này AI chỉ
        panel_filter.add(new JLabel("Lọc theo:"));
        String[] filterOptions = {"Năm", "Tháng", "Ngày"};
        JComboBox<String> comboLocTheo = new JComboBox<>(filterOptions);
        panel_filter.add(comboLocTheo);

        panel_filter.add(new JLabel("TG Bắt Đầu:"));
        // lọc date
        JSpinner jSpinner_start= createDatePicker();
        panel_filter.add(jSpinner_start);

        panel_filter.add(new JLabel("TG Kết Thúc:"));
        JSpinner jSpinner_end = createDatePicker();
        panel_filter.add(jSpinner_end);

        // ngày mặc định
        Calendar cal = Calendar.getInstance();
        Date homNay = cal.getTime();
        jSpinner_end.setValue(homNay);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date homQua = cal.getTime();
        jSpinner_start.setValue(homQua);

        JButton buttonLoc = new JButton("Lọc");
        buttonLoc.setBackground(new Color(15, 110, 180));
        buttonLoc.setForeground(Color.WHITE);
        panel_filter.add(buttonLoc);

        panel_center_wrapper.add(panel_filter, BorderLayout.NORTH);

        //xe bán chạy
        String[] columnNames = {"Tên OTO", "Số lượt bán", "Giá"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableOtoBanChay = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tableOtoBanChay);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "DANH SÁCH OTO BÁN CHẠY NHẤT",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.BLACK
        ));

        panel_center_wrapper.add(scrollPane, BorderLayout.CENTER);
        jPanel_right.add(panel_center_wrapper, BorderLayout.CENTER);

        //tiều đề
        JPanel panel_bottom = new JPanel(new GridLayout(1, 2, 20, 0));
        panel_bottom.setBackground(Color.WHITE);
        panel_bottom.setPreferredSize(new Dimension(0, 150));

        // khách hàng tiềm năng
        JPanel panel_khachHang = createSummaryBox("KHÁCH HÀNG MUA NHIỀU NHẤT", "Nhân đẹp trai vãi lồn", Color.RED);

        // doanh thu
        JPanel panel_doanhThu = createSummaryBox("TỔNG DOANH THU CỬA HÀNG", "NHÂN ĐẸP TRAI VÃI LỒN", Color.GREEN);

        panel_bottom.add(panel_khachHang);
        panel_bottom.add(panel_doanhThu);

        jPanel_right.add(panel_bottom, BorderLayout.SOUTH);
        this.add(sidebar, BorderLayout.WEST);
        this.add(jPanel_right, BorderLayout.CENTER);

        this.setVisible(true);
    }

    // khúc này AI chỉ (chổ nút lọc ngày có để thời gian)
    private JSpinner createDatePicker() {
        SpinnerDateModel model = new SpinnerDateModel(
                new Date(),
                null,
                null,
                Calendar.DAY_OF_MONTH
        );
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);
        return spinner;
    }

    private JPanel createSummaryBox(String title, String value, Color bgColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel labelTitle = new JLabel(title, SwingConstants.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitle.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));

        JLabel labelValue = new JLabel(value, SwingConstants.CENTER);
        labelValue.setFont(new Font("Arial", Font.BOLD, 30));
        labelValue.setForeground(new Color(0, 50, 120));

        panel.add(labelTitle, BorderLayout.NORTH);
        panel.add(labelValue, BorderLayout.CENTER);
        return panel;
    }
}