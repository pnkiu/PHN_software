package view;

import controller.ProductController;
import dao.ProductDAO;
import model.ProductModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class ProductView extends JPanel {
    private JButton btnThongKe;
    private JButton btnSanPham;
    private JButton btnKhachHang;
    private JButton btnNhanVien;
    private JButton btnGiaoDich;
    private JButton jButton_add;
    private JButton jButton_edit;
    private JButton jButton_delete;
    private JTextField jTextField_search;
    private JButton jButton_search;

    private JDialog addDialog;

    private JTable carTable;
    private DefaultTableModel tableModel;

    public ProductView() {
        this.init();
    }

    public void init() {
        Font font = new Font("Arial", Font.BOLD, 40);
        Font fonts = new Font("Arial", Font.BOLD, 17);

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

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
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel_right.add(scrollPane, BorderLayout.CENTER);

        this.add(jPanel_right, BorderLayout.CENTER);

        // Khởi tạo controller (khi JPanel được load)
        ProductController controller = new ProductController(this, new ProductDAO());
        controller.hienThiDB();
    }

    public void addAddCarListener(ActionListener listener) {

        jButton_add.addActionListener(listener);
    }

    public void hienthidulieu(List<ProductModel> carList) {
        tableModel.setRowCount(0);
        for (ProductModel car : carList) {
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

    public void formThemsp(ProductController controller) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        addDialog = new JDialog(parentFrame, "Thêm sản phẩm Ô tô", true);
        addDialog.setSize(400, 550);
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);

        JLabel l1 = new JLabel("Mã Ô tô:");
        l1.setBounds(20, 20, 100, 30);
        formPanel.add(l1);

        JTextField txtMaOto = new JTextField();
        txtMaOto.setBounds(130, 20, 230, 30);
        formPanel.add(txtMaOto);

        JLabel l2 = new JLabel("Tên Ô tô:");
        l2.setBounds(20, 60, 100, 30);
        formPanel.add(l2);

        JTextField txtTenOto = new JTextField();
        txtTenOto.setBounds(130, 60, 230, 30);
        formPanel.add(txtTenOto);

        JLabel l3 = new JLabel("Loại Ô tô:");
        l3.setBounds(20, 100, 100, 30);
        formPanel.add(l3);

        JTextField txtLoaiOto = new JTextField();
        txtLoaiOto.setBounds(130, 100, 230, 30);
        formPanel.add(txtLoaiOto);

        JLabel l4 = new JLabel("Giá:");
        l4.setBounds(20, 140, 100, 30);
        formPanel.add(l4);

        JTextField txtGia = new JTextField();
        txtGia.setBounds(130, 140, 230, 30);
        formPanel.add(txtGia);

        JLabel l5 = new JLabel("Số lượng:");
        l5.setBounds(20, 180, 100, 30);
        formPanel.add(l5);

        JTextField txtSoLuong = new JTextField();
        txtSoLuong.setBounds(130, 180, 230, 30);
        formPanel.add(txtSoLuong);

        JLabel l6 = new JLabel("Mã Hãng:");
        l6.setBounds(20, 220, 100, 30);
        formPanel.add(l6);

        JTextField txtMaHang = new JTextField();
        txtMaHang.setBounds(130, 220, 230, 30);
        formPanel.add(txtMaHang);

        JLabel l7 = new JLabel("Mô tả:");
        l7.setBounds(20, 260, 100, 30);
        formPanel.add(l7);

        JTextArea txtMoTa = new JTextArea();
        txtMoTa.setBounds(130, 260, 230, 150);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        formPanel.add(txtMoTa);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        btnCancel.addActionListener(e -> addDialog.dispose());

        btnSave.addActionListener(e -> controller.them(
                txtMaOto.getText(),
                txtTenOto.getText(),
                txtLoaiOto.getText(),
                txtGia.getText(),
                txtSoLuong.getText(),
                txtMaHang.getText(),
                txtMoTa.getText()
        ));

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);
        addDialog.setVisible(true);
    }

    public void closeAddDialog() {
        if (addDialog != null) addDialog.dispose();
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
