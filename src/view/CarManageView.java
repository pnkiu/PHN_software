package view;

import controller.CarManageController;
import model.CarManageModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class CarManageView extends JFrame {
    private JButton jButton_add;
    private JButton jButton_edit;
    private JButton jButton_delete;
    private JTextField jTextField_search;
    private JButton jButton_search;

    private JDialog addDialog;

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

        //bên trái
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

        String[] columnNames = {"Mã Ô tô", "Tên Ô tô", "Loại", "Giá", "Số lượng", "Mã Hãng", "Số lượt bán"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        carTable = new JTable(tableModel);
        carTable.setFont(new Font("Arial", Font.PLAIN, 14));
        carTable.setRowHeight(25);
        carTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(carTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Thêm padding
        jPanel_right.add(scrollPane, BorderLayout.CENTER);
        this.add(jPanel_menu, BorderLayout.WEST);
        this.add(jPanel_right, BorderLayout.CENTER);
    }
    public void addAddCarListener(ActionListener listener) {
        jButton_add.addActionListener(listener);
    }
    public void displayCarList(List<CarManageModel> carList) {
        tableModel.setRowCount(0);

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

    // Hàm showAddCarDialog viết theo kiểu "newbie" (dùng null layout)
    public void showAddCarDialog(CarManageController controller) {
        addDialog = new JDialog(this, "Thêm sản phẩm Ô tô", true);
        addDialog.setSize(400, 550); // Set kích thước cứng
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(new BorderLayout());

        // Newbie hay dùng null layout (absolute positioning)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null); // Tắt layout manager

        // 1. Mã Ô tô
        JLabel l1 = new JLabel("Mã Ô tô:");
        l1.setBounds(20, 20, 100, 30); // Đặt vị trí (x, y, width, height)
        formPanel.add(l1);

        JTextField txtMaOto = new JTextField();
        txtMaOto.setBounds(130, 20, 230, 30); // Đặt vị trí
        formPanel.add(txtMaOto);

        // 2. Tên Ô tô
        JLabel l2 = new JLabel("Tên Ô tô:");
        l2.setBounds(20, 60, 100, 30);
        formPanel.add(l2);

        JTextField txtTenOto = new JTextField();
        txtTenOto.setBounds(130, 60, 230, 30);
        formPanel.add(txtTenOto);

        // 3. Loại Ô tô
        JLabel l3 = new JLabel("Loại Ô tô:");
        l3.setBounds(20, 100, 100, 30);
        formPanel.add(l3);

        JTextField txtLoaiOto = new JTextField();
        txtLoaiOto.setBounds(130, 100, 230, 30);
        formPanel.add(txtLoaiOto);

        // 4. Giá
        JLabel l4 = new JLabel("Giá:");
        l4.setBounds(20, 140, 100, 30);
        formPanel.add(l4);

        JTextField txtGia = new JTextField();
        txtGia.setBounds(130, 140, 230, 30);
        formPanel.add(txtGia);

        // 5. Số lượng
        JLabel l5 = new JLabel("Số lượng:");
        l5.setBounds(20, 180, 100, 30);
        formPanel.add(l5);

        JTextField txtSoLuong = new JTextField();
        txtSoLuong.setBounds(130, 180, 230, 30);
        formPanel.add(txtSoLuong);

        // 6. Mã Hãng
        JLabel l6 = new JLabel("Mã Hãng:");
        l6.setBounds(20, 220, 100, 30);
        formPanel.add(l6);

        JTextField txtMaHang = new JTextField();
        txtMaHang.setBounds(130, 220, 230, 30);
        formPanel.add(txtMaHang);

        // 7. Mô tả (Dùng JTextArea nhưng không có JScrollPane)
        JLabel l7 = new JLabel("Mô tả:");
        l7.setBounds(20, 260, 100, 30);
        formPanel.add(l7);

        JTextArea txtMoTa = new JTextArea(); // Không set hàng cột
        txtMoTa.setBounds(130, 260, 230, 150); // Đặt kích thước cứng
        txtMoTa.setLineWrap(true); // Vẫn set line wrap
        txtMoTa.setWrapStyleWord(true);
        // Không đặt trong JScrollPane, nếu gõ nhiều chữ sẽ bị mất
        formPanel.add(txtMoTa);

        // Panel cho nút bấm (giữ nguyên vì FlowLayout cũng đơn giản)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // Gắn sự kiện
        btnCancel.addActionListener(e -> addDialog.dispose());

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic gọi Controller vẫn giữ nguyên
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

        // Thêm các panel vào dialog
        addDialog.add(formPanel, BorderLayout.CENTER); // formPanel (dùng null layout) ở giữa
        addDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Hiển thị dialog
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