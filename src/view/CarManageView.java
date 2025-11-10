package view;

import controller.CarManageController;
import controller.StaffController;
import dao.ProductDAO;
import dao.StaffDAO;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import model.ProductModel;

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
    private JLabel lblKhachHangTheoTien, lblKhachHangTheoSoLan;
    private JLabel lblDoanhThu;
    CarManageController controller = new CarManageController(this, new ProductDAO());

    public CarManageView() {
        this.init();
    }

    public void init() {
        Font font = new Font("Arial", Font.BOLD, 40);
        this.setTitle("Car Management Software");
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


        JPanel thongKePanel = createThongKePanel();
        contentPanel.add(thongKePanel, "Thống kê");

        JPanel ProductPanel = new ProductView();
        contentPanel.add(ProductPanel, "Sản phẩm");

        JPanel CustomerPanel = new CustomerView();
        contentPanel.add(CustomerPanel, "Khách hàng");

        // JPanel StaffPanel = new StaffView();
        // contentPanel.add(StaffPanel, "Nhân viên");
        
        StaffDAO staffDAO = StaffDAO.getInstance();
        StaffController staffController = new StaffController(staffDAO);
        JPanel panelNhanVien = new StaffView(staffController); 
        contentPanel.add(panelNhanVien, "Nhân viên");

        JPanel TransactionPanel = new TransacionView();
        contentPanel.add(TransactionPanel, "Giao dịch");



        root.add(sidebar, BorderLayout.WEST);
        root.add(contentPanel, BorderLayout.CENTER);
        btnThongKe.addActionListener(e -> cardLayout.show(contentPanel, "Thống kê"));
        btnSanPham.addActionListener(e -> cardLayout.show(contentPanel, "Sản phẩm"));
        btnNhanVien.addActionListener(e -> cardLayout.show(contentPanel, "Nhân viên"));
        btnKhachHang.addActionListener(e -> cardLayout.show(contentPanel, "Khách hàng"));
        btnGiaoDich.addActionListener(e -> cardLayout.show(contentPanel, "Giao dịch"));
        
        controller.hienThiXeBanChay();
        this.setVisible(true);
    }

    // thống kê
    private JPanel createThongKePanel() {
        JPanel jPanel_right = new JPanel(new BorderLayout(10, 10));
        jPanel_right.setBackground(Color.WHITE);
        jPanel_right.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //tạo viền trống

        JLabel jLabel_header = new JLabel("HỆ THỐNG QUẢN LÝ CỬA HÀNG BÁN XE", SwingConstants.CENTER);
        jLabel_header.setForeground(Color.BLACK);
        jLabel_header.setFont(new Font("Arial", Font.BOLD, 32));
        jPanel_right.add(jLabel_header, BorderLayout.NORTH);


        JPanel panel_center_wrapper = new JPanel(new BorderLayout(10, 10));
        panel_center_wrapper.setBackground(Color.WHITE);
        // lọc
        JPanel panel_filter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel_filter.setBackground(Color.WHITE);


        JLabel jLabelFiler = new JLabel("LỌC:");
        jLabelFiler.setFont(new Font("Arial", Font.BOLD, 16));
        panel_filter.add(new JLabel("LỌC"));

        JLabel jLabelStar = new JLabel("TỪ:");
        jLabelFiler.setFont(new Font("Arial", Font.BOLD, 16));
        panel_filter.add(jLabelStar);
        // lọc date
        JSpinner jSpinner_start= createDatePicker();
        panel_filter.add(jSpinner_start);

        JLabel jLabelEnd = new JLabel("ĐẾN:");
        jLabelFiler.setFont(new Font("Arial", Font.BOLD, 16));
        panel_filter.add(jLabelEnd);
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
        tableOtoBanChay = new JTable(tableModel);
        tableOtoBanChay.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tableOtoBanChay.setRowHeight(28);
        tableOtoBanChay.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableOtoBanChay.getTableHeader().setBackground(new Color(52, 73, 94));
        tableOtoBanChay.getTableHeader().setForeground(Color.WHITE);
        tableOtoBanChay.setSelectionBackground(new Color(52, 152, 219));
        tableOtoBanChay.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tableOtoBanChay);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "DANH SÁCH OTO BÁN CHẠY NHẤT  ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16),
                Color.BLACK
        ));

        panel_center_wrapper.add(scrollPane, BorderLayout.CENTER);
        jPanel_right.add(panel_center_wrapper, BorderLayout.CENTER);

        //tiều đề
        JPanel panel_bottom = new JPanel(new GridLayout(1, 2, 20, 0));
        panel_bottom.setBackground(Color.WHITE);
        panel_bottom.setPreferredSize(new Dimension(0, 150));

        // khách hàng mua nhiều nhất (theo tiền)
        JPanel panel_khTien = createSummaryBox("KH THÂN THIẾT", "N/A", new Color(220, 220, 220));

        // khách hàng mua nhiều nhất (theo số lần)
        JPanel panel_khSoLan = createSummaryBox("KH MUA NHIỀU", "N/A", new Color(220, 220, 220));


        // doanh thu
        JPanel panel_doanhThu = createSummaryBox("DOANH THU CỬA HÀNG", "N/A", new Color(220, 220, 220));
    

        panel_bottom.add(panel_khTien);
        panel_bottom.add(panel_khSoLan);
        panel_bottom.add(panel_doanhThu);

        jPanel_right.add(panel_bottom, BorderLayout.SOUTH);
        this.add(jPanel_right, BorderLayout.CENTER);

        buttonLoc.addActionListener(e -> {
            Date startDate = (Date) jSpinner_start.getValue();
            Date endDate = (Date) jSpinner_end.getValue();
            controller.thongKeTheoKhoangThoiGian(startDate, endDate);
        });
        return jPanel_right;
    }

    private JSpinner createDatePicker() {
        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
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
        labelValue.setFont(new Font("Arial", Font.BOLD, 15));
        labelValue.setForeground(new Color(0, 50, 120));

        panel.add(labelTitle, BorderLayout.NORTH);
        panel.add(labelValue, BorderLayout.CENTER);

        if (title.equals("KH THÂN THIẾT")) lblKhachHangTheoTien = labelValue;
        else if (title.equals("KH MUA NHIỀU")) lblKhachHangTheoSoLan = labelValue;
        else if (title.equals("DOANH THU CỬA HÀNG")) lblDoanhThu = labelValue;

        return panel;
    }
    public void hienThiXeBanChayNhat(ArrayList<ProductModel> list) {
    String[] columnNames = {"Tên Xe", "Tên Hãng", "Số Lượt Bán"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

    for (ProductModel o : list) {
        Object[] row = { o.getTenOto(), o.getMaHang(), o.getSoLuotBan() };
        model.addRow(row);
    }
    tableOtoBanChay.setModel(model);
}


public void capNhatThongKe(String topKhTheoSoLan, String topKhTheoTien, double tongDoanhThu) {
    
    // 1. Cập nhật tổng doanh thu
    lblDoanhThu.setText(String.format("%,.0f VNĐ", tongDoanhThu));

    // 2. Cập nhật khách hàng mua nhiều nhất
    
    if (lblKhachHangTheoTien != null) {
            lblKhachHangTheoTien.setText("<html>" + topKhTheoTien.replaceAll("\n", "<br>") + "</html>");
        }
    if (lblKhachHangTheoSoLan != null) {
            lblKhachHangTheoSoLan.setText("<html>" + topKhTheoSoLan.replaceAll("\n", "<br>") + "</html>");
        }
}
}
