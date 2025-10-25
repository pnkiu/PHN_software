package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class ThongKeView extends JFrame {

    private JTable tableOtoBanChay;
    private DefaultTableModel tableModel;


    public ThongKeView() {
        this.init();
    }

    public void init() {
        this.setTitle("Car Manage Software");
        this.setSize(1050, 620); // Kích thước giữ nguyên
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        Font font = new Font("Arial", Font.BOLD, 40);
        Font fonts = new Font("Arial", Font.BOLD, 17);

        //bên trái
        JPanel jPanel_menu = new JPanel();
        jPanel_menu.setLayout(new BoxLayout(jPanel_menu, BoxLayout.Y_AXIS));
        jPanel_menu.setPreferredSize(new Dimension(240, 0));
        jPanel_menu.setBackground(new Color(190, 190, 190, 190));
        jPanel_menu.setBorder(BorderFactory.createEmptyBorder(35, 10, 15, 10));

        // Tiêu đề
        JLabel jLabel_title = new JLabel("ADMIN", SwingConstants.CENTER);
        jLabel_title.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel_title.setForeground(Color.BLACK);
        jLabel_title.setFont(font);
        jPanel_menu.add(jLabel_title);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 50))); //k/cách tiêu đề

        //các list role
        JButton jButton_TK = new JButton("Thống kê");
        jButton_TK.setFont(fonts);
        jButton_TK.setBackground(new Color(70, 130, 180));
        jButton_TK.setForeground(Color.white);
        jButton_TK.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_TK.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_TK);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton jButton_QLSP = new JButton("QUẢN LÝ SẢN PHẨM");
        jButton_QLSP.setFont(fonts);
        jButton_QLSP.setBackground(new Color(70, 130, 180));
        jButton_QLSP.setForeground(Color.white);
        jButton_QLSP.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_QLSP.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_QLSP);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton jButton_QLGD = new JButton("QUẢN LÝ GIAO DỊCH");
        jButton_QLGD.setFont(fonts);
        jButton_QLGD.setBackground(new Color(70, 130, 180));
        jButton_QLGD.setForeground(Color.white);
        jButton_QLGD.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_QLGD.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_QLGD);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton jButton_QLKH = new JButton("QUẢN LÝ KHÁCH HÀNG");
        jButton_QLKH.setFont(fonts);
        jButton_QLKH.setBackground(new Color(70, 130, 180));
        jButton_QLKH.setForeground(Color.white);
        jButton_QLKH.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_QLKH.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_QLKH);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton jButton_QLNV = new JButton("QUẢN LÝ NHÂN VIÊN");
        jButton_QLNV.setFont(fonts);
        jButton_QLNV.setBackground(new Color(70, 130, 180));
        jButton_QLNV.setForeground(Color.white);
        jButton_QLNV.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_QLNV.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_QLNV);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton jButton_out = new JButton("Đăng xuất");
        jButton_out.setFont(fonts);
        jButton_out.setBackground(new Color(70, 130, 180));
        jButton_out.setForeground(Color.white);
        jButton_out.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_out.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_out);


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
        this.add(jPanel_menu, BorderLayout.WEST);
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