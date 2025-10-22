package view;

import controller.CarManageController;
import model.CarManageModel; // Thêm import này

import javax.swing.*;
import javax.swing.table.DefaultTableModel; // Thêm import này
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List; // Thêm import này
import java.awt.event.ActionEvent;

public class CarManageView extends JFrame {

    // (Các biến cũ giữ nguyên)
    private JButton jButton_add;
    private JButton jButton_edit;
    private JButton jButton_delete;
    private JTextField jTextField_search; // Bạn gõ nhầm tên biến, tôi sửa lại
    private JButton jButton_search;

    private JDialog addDialog;

    // === BIẾN MỚI CHO BẢNG ===
    private JTable carTable;
    private DefaultTableModel tableModel;

    public CarManageView() {
        this.init();
    }

    public void init() {
        this.setTitle("Car Manage Software");
        this.setSize(1050, 620);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        // (Code menu bên trái giữ nguyên... )
        Font font = new Font("Arial", Font.BOLD, 40);
        Font fonts = new Font("Arial", Font.BOLD, 17);

        JPanel jPanel_menu = new JPanel();
        jPanel_menu.setLayout(new BoxLayout(jPanel_menu, BoxLayout.Y_AXIS));
        jPanel_menu.setPreferredSize(new Dimension(240, 0));
        jPanel_menu.setBackground(new Color(190, 190, 190, 190));
        jPanel_menu.setBorder(BorderFactory.createEmptyBorder(35, 10, 15, 10));

        JLabel jLabel_title = new JLabel("ADMIN", SwingConstants.CENTER);
        jLabel_title.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel_title.setForeground(Color.BLACK);
        jLabel_title.setFont(font);
        jPanel_menu.add(jLabel_title);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 50)));

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
        jButton_QLGD.setBackground(new Color(70,130,180));
        jButton_QLGD.setForeground(Color.white);
        jButton_QLGD.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_QLGD.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_QLGD);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton jButton_QLKH = new JButton("QUẢN LÝ KHÁCH HÀNG");
        jButton_QLKH.setFont(fonts);
        jButton_QLKH.setBackground(new Color(70,130,180));
        jButton_QLKH.setForeground(Color.white);
        jButton_QLKH.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_QLKH.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_QLKH);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton jButton_QLNV = new JButton("QUẢN LÝ NHÂN VIÊN");
        jButton_QLNV.setFont(fonts);
        jButton_QLNV.setBackground(new Color(70,130,180));
        jButton_QLNV.setForeground(Color.white);
        jButton_QLNV.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_QLNV.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_QLNV);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton jButton_out = new JButton("Đăng xuất");
        jButton_out.setFont(fonts);
        jButton_out.setBackground(new Color(70,130,180));
        jButton_QLNV.setForeground(Color.white);
        jButton_out.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_out.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_out);

        //bên phải
        JPanel jPanel_right = new JPanel(new BorderLayout());
        jPanel_right.setBackground(Color.WHITE);

        // (Code panel header và action giữ nguyên)
        JLabel jLabel_header = new JLabel("QUẢN LÝ SẢN PHẨM", SwingConstants.CENTER);
        jLabel_header.setForeground(new Color(65, 105, 225));
        jLabel_header.setFont(font);
        jLabel_header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel jPanel_action = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        jPanel_action.setBackground(Color.WHITE);

        jButton_add = new JButton("Thêm sản phẩm");
        jButton_edit = new JButton("Sửa");
        jButton_delete = new JButton("Xóa");
        jTextField_search = new JTextField(20);
        jButton_search = new JButton("Tìm kiếm");

        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        for (JButton btn : new JButton[]{jButton_add, jButton_edit, jButton_delete, jButton_search}) {
            btn.setFont(buttonFont);
            btn.setBackground(new Color(65, 105, 225));
            btn.setForeground(Color.WHITE);
        }

        jPanel_action.add(jButton_add);
        jPanel_action.add(jButton_edit);
        jPanel_action.add(jButton_delete);
        jPanel_action.add(jTextField_search);
        jPanel_action.add(jButton_search);

        JPanel jPanel_north_wrapper = new JPanel(new BorderLayout());
        jPanel_north_wrapper.setBackground(Color.WHITE);
        jPanel_north_wrapper.add(jLabel_header, BorderLayout.NORTH);
        jPanel_north_wrapper.add(jPanel_action, BorderLayout.CENTER);

        jPanel_right.add(jPanel_north_wrapper, BorderLayout.NORTH);

        // === BẮT ĐẦU THÊM JTABLE VÀO ĐÂY ===

        // 1. Định nghĩa cột
        String[] columnNames = {"Mã Ô tô", "Tên Ô tô", "Loại", "Giá", "Số lượng", "Mã Hãng", "Số lượt bán"};

        // 2. Tạo TableModel và JTable
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Không cho phép sửa trực tiếp trên bảng
                return false;
            }
        };
        carTable = new JTable(tableModel);
        carTable.setFont(new Font("Arial", Font.PLAIN, 14));
        carTable.setRowHeight(25);
        carTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // 3. Tạo JScrollPane để chứa JTable
        JScrollPane scrollPane = new JScrollPane(carTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Thêm padding

        // 4. Thêm JScrollPane (chứa bảng) vào giữa panel bên phải
        jPanel_right.add(scrollPane, BorderLayout.CENTER);

        // === KẾT THÚC THÊM JTABLE ===

        this.add(jPanel_menu, BorderLayout.WEST);
        this.add(jPanel_right, BorderLayout.CENTER);
    }

    // (Hàm addAddCarListener giữ nguyên)
    public void addAddCarListener(ActionListener listener) {
        jButton_add.addActionListener(listener);
    }

    // === HÀM MỚI: ĐỂ CONTROLLER CẬP NHẬT BẢNG ===
    /**
     * Hiển thị danh sách xe lên JTable
     * @param carList Danh sách xe lấy từ Controller
     */
    public void displayCarList(List<CarManageModel> carList) {
        // Xóa hết dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Duyệt qua danh sách và thêm từng hàng vào tableModel
        for (CarManageModel car : carList) {
            Object[] rowData = {
                    car.getMaOto(),
                    car.getTenOto(),
                    car.getLoaiOto(),
                    car.getGia(),
                    car.getSoLuong(),
                    car.getMaHang(),
                    car.getSoLuotBan()
            };
            tableModel.addRow(rowData);
        }
    }

    // (Các hàm showAddCarDialog, closeAddDialog, showSuccessMessage, showErrorMessage giữ nguyên)
    public void showAddCarDialog(CarManageController controller) {
        addDialog = new JDialog(this, "Thêm sản phẩm Ô tô", true);
        addDialog.setSize(400, 500);
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tạo các trường nhập liệu
        JTextField txtMaOto = new JTextField();
        JTextField txtTenOto = new JTextField();
        JTextField txtLoaiOto = new JTextField();
        JTextField txtGia = new JTextField();
        JTextField txtSoLuong = new JTextField();
        JTextField txtMaHang = new JTextField();
        JTextField txtMoTa = new JTextField();

        // Thêm vào form
        formPanel.add(new JLabel("Mã Ô tô:"));
        formPanel.add(txtMaOto);
        formPanel.add(new JLabel("Tên Ô tô:"));
        formPanel.add(txtTenOto);
        formPanel.add(new JLabel("Loại Ô tô:"));
        formPanel.add(txtLoaiOto);
        formPanel.add(new JLabel("Giá:"));
        formPanel.add(txtGia);
        formPanel.add(new JLabel("Số lượng:"));
        formPanel.add(txtSoLuong);
        formPanel.add(new JLabel("Mã Hãng:"));
        formPanel.add(txtMaHang);
        formPanel.add(new JLabel("Mô tả:"));
        formPanel.add(txtMoTa);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // Nút Hủy (logic đơn giản, có thể giữ lại)
        btnCancel.addActionListener(e -> addDialog.dispose());

        // Nút Lưu
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chỉ lấy dữ liệu và GỌI CONTROLLER
                // Không tự xử lý
                controller.saveNewCar(
                        txtMaOto.getText(),
                        txtTenOto.getText(),
                        txtLoaiOto.getText(),
                        txtGia.getText(),
                        txtSoLuong.getText(),
                        txtMaHang.getText(),
                        txtMoTa.getText()
                );
            }
        });

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);
        addDialog.setVisible(true);
    }

    public void closeAddDialog() {
        if (addDialog != null) {
            addDialog.dispose();
        }
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}