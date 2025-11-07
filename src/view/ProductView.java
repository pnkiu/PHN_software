package view;

import controller.ProductController;
import dao.ProductDAO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.ProductModel;

public class ProductView extends JPanel {
    // ... (Các biến ban đầu của bạn) ...
    private JButton jButton_add;
    private JButton jButton_edit;
    private JButton jButton_delete;
    private JTextField jTextField_search;
    private JButton jButton_search;

    private JTable carTable;
    private DefaultTableModel tableModel;

    // Biến cho dialog "Thêm"
    private JDialog addDialog;

    // Biến cho dialog "Sửa"
    private JDialog editDialog;
    private JTable dialogTable; // Bảng phụ trong dialog sửa
    private DefaultTableModel dialogTableModel;
    private JButton btnSua;
    private JButton btnDong;
    private JTextField txtMaOtoEdit, txtTenOtoEdit, txtLoaiOtoEdit, txtGiaEdit, txtSoLuongEdit, txtMaHangEdit;
    private JTextArea txtMoTaEdit;

    // Controller
    private ProductController controller;


    public ProductView() {
        this.init();
    }

    public void init() {
        Font font = new Font("Arial", Font.BOLD, 40);
        // ... (Code set layout và header của bạn) ...

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        JPanel jPanel_right = new JPanel(new BorderLayout());
        jPanel_right.setBackground(Color.WHITE);

        JLabel jLabel_header = new JLabel("QUẢN LÝ SẢN PHẨM", SwingConstants.CENTER);
        jLabel_header.setForeground(Color.BLACK);
        jLabel_header.setFont(font);
        jLabel_header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel jPanel_action = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        jPanel_action.setBackground(Color.WHITE);

        jButton_add = new JButton("Thêm sản phẩm");
        jButton_edit = new JButton("Sửa");
        jButton_delete = new JButton("Xóa");
        jTextField_search = new JTextField(20);
        jButton_search = new JButton("Tìm kiếm");

        // ... (Code style button của bạn) ...
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

        // ... (Code JTable chính của bạn) ...
        String[] columnNames = {"Mã Ô Tô", "Tên Ô Tô", "Giá", "Loại Ô Tô", "Số Lượng", "Mô Tả", "Mã Hãng", "Số Lượt Bán"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        carTable = new JTable(tableModel);
        carTable.setFont(new Font("Arial", Font.PLAIN, 14));
        carTable.setRowHeight(25);
        carTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        carTable.getTableHeader().setBackground(new Color(52, 73, 94));
        carTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(carTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel_right.add(scrollPane, BorderLayout.CENTER);

        this.add(jPanel_right, BorderLayout.CENTER);

        // Khởi tạo controller (khi JPanel được load)
        // Controller sẽ tự động thêm listener vào các nút
        controller = new ProductController(this, new ProductDAO());
    }

    // Các hàm add listener
    public void addAddCarListener(ActionListener listener) {
        jButton_add.addActionListener(listener);
    }
    public void addDeleteCarListener(ActionListener listener) {
        jButton_delete.addActionListener(listener);
    }
    public void addEditCarListener(ActionListener listener) { // <-- Thêm hàm add listener cho nút Sửa
        jButton_edit.addActionListener(listener);
    }
    public void addSearchCarListener(ActionListener listener) {
        jButton_search.addActionListener(listener);
        jTextField_search.addActionListener(listener); // Bắt cả sự kiện nhấn Enter
    }

    // Hiển thị dữ liệu lên bảng chính
    public void hienthidulieu(List<ProductModel> carList) {
        tableModel.setRowCount(0);
        for (ProductModel car : carList) {
            Object[] rowData = {
                    car.getMaOto(),
                    car.getTenOto(),
                    car.getGia(),
                    car.getLoaiOto(),
                    car.getSoLuong(),
                    car.getMoTa(),
                    car.getMaHang(),
                    car.getSoLuotBan()
            };
            tableModel.addRow(rowData);
        }
    }

    // Form "Thêm sản phẩm" (Code gốc của bạn)
    public void formThemsp(ProductController controller) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        addDialog = new JDialog(parentFrame, "Thêm sản phẩm Ô tô", true);
        addDialog.setSize(400, 550);
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // ... (Code các label và textfield của form thêm) ...
        JLabel l1 = new JLabel("Mã Ô tô:"); l1.setBounds(20, 20, 100, 30); formPanel.add(l1);
        JTextField txtMaOto = new JTextField(); txtMaOto.setBounds(130, 20, 230, 30); formPanel.add(txtMaOto);
        JLabel l2 = new JLabel("Tên Ô tô:"); l2.setBounds(20, 60, 100, 30); formPanel.add(l2);
        JTextField txtTenOto = new JTextField(); txtTenOto.setBounds(130, 60, 230, 30); formPanel.add(txtTenOto);
        JLabel l3 = new JLabel("Loại Ô tô:"); l3.setBounds(20, 100, 100, 30); formPanel.add(l3);
        JTextField txtLoaiOto = new JTextField(); txtLoaiOto.setBounds(130, 100, 230, 30); formPanel.add(txtLoaiOto);
        JLabel l4 = new JLabel("Giá:"); l4.setBounds(20, 140, 100, 30); formPanel.add(l4);
        JTextField txtGia = new JTextField(); txtGia.setBounds(130, 140, 230, 30); formPanel.add(txtGia);
        JLabel l5 = new JLabel("Số lượng:"); l5.setBounds(20, 180, 100, 30); formPanel.add(l5);
        JTextField txtSoLuong = new JTextField(); txtSoLuong.setBounds(130, 180, 230, 30); formPanel.add(txtSoLuong);
        JLabel l6 = new JLabel("Mã Hãng:"); l6.setBounds(20, 220, 100, 30); formPanel.add(l6);
        JTextField txtMaHang = new JTextField(); txtMaHang.setBounds(130, 220, 230, 30); formPanel.add(txtMaHang);
        JLabel l7 = new JLabel("Mô tả:"); l7.setBounds(20, 260, 100, 30); formPanel.add(l7);
        JTextArea txtMoTa = new JTextArea();
        txtMoTa.setLineWrap(true); txtMoTa.setWrapStyleWord(true);
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa); scrollMoTa.setBounds(130, 260, 230, 150); formPanel.add(scrollMoTa);

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

    // Form "Sửa sản phẩm" (Hoàn thiện)
    public void formSuasp(ProductController controller) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        editDialog = new JDialog(parentFrame, "Sửa thông tin Ô tô", true);
        editDialog.setSize(900, 600); // Tăng kích thước để chứa cả form và bảng
        editDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tiêu đề
        JLabel lblTitle = new JLabel("SỬA THÔNG TIN Ô TÔ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 123, 255));
        lblTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(lblTitle, BorderLayout.NORTH);

        // Panel chính chứa cả form và bảng
        JPanel mainContentPanel = new JPanel(new BorderLayout(10, 10));

        // Panel trái cho form nhập liệu
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(350, 0)); // Kích thước form
        leftPanel.setBorder(BorderFactory.createTitledBorder("Thông tin ô tô cần sửa"));

        // Form nhập liệu
        JPanel formPanel = createFormPanel(); // Dùng hàm phụ trợ
        leftPanel.add(formPanel, BorderLayout.CENTER);

        // Panel nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnSua = createStyledButton("Cập Nhật", new Color(40, 167, 69));
        btnDong = createStyledButton("Đóng", new Color(108, 117, 125));

        buttonPanel.add(btnSua);
        buttonPanel.add(btnDong);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainContentPanel.add(leftPanel, BorderLayout.WEST);

        // Panel phải cho bảng dữ liệu
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Danh sách ô tô - Click đúp để chọn"));

        // Bảng dữ liệu
        String[] columns = {"Mã ô tô", "Tên ô tô", "Giá", "Loại ô tô", "Số lượng", "Mô tả", "Mã hãng"};
        dialogTableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        dialogTable = new JTable(dialogTableModel);
        dialogTable.setRowHeight(25);
        dialogTable.setFont(new Font("Arial", Font.PLAIN, 12));
        dialogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(dialogTable);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        mainContentPanel.add(rightPanel, BorderLayout.CENTER);
        panel.add(mainContentPanel, BorderLayout.CENTER);

        editDialog.add(panel);

        // Thêm sự kiện
        addDialogEventListeners(controller);
        loadCarDataToDialogTable(); // Tải dữ liệu vào bảng phụ

        editDialog.setVisible(true);
    }

    // (Hàm phụ trợ) Tạo form cho dialog sửa
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Mã Ô tô
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Mã Ô tô:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtMaOtoEdit = new JTextField();
        txtMaOtoEdit.setEditable(false); // Không cho sửa Mã
        txtMaOtoEdit.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtMaOtoEdit, gbc);

        // Tên Ô tô
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Tên Ô tô:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtTenOtoEdit = new JTextField();
        formPanel.add(txtTenOtoEdit, gbc);

        // Loại Ô tô
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Loại Ô tô:"), gbc);
        gbc.gridx = 1;
        txtLoaiOtoEdit = new JTextField();
        formPanel.add(txtLoaiOtoEdit, gbc);

        // Giá
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Giá:"), gbc);
        gbc.gridx = 1;
        txtGiaEdit = new JTextField();
        formPanel.add(txtGiaEdit, gbc);

        // Số lượng
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 1;
        txtSoLuongEdit = new JTextField();
        formPanel.add(txtSoLuongEdit, gbc);

        // Mã Hãng
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Mã Hãng:"), gbc);
        gbc.gridx = 1;
        txtMaHangEdit = new JTextField();
        formPanel.add(txtMaHangEdit, gbc);

        // Mô tả
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
        txtMoTaEdit = new JTextArea(5, 20);
        txtMoTaEdit.setLineWrap(true); txtMoTaEdit.setWrapStyleWord(true);
        JScrollPane moTaScroll = new JScrollPane(txtMoTaEdit);
        formPanel.add(moTaScroll, gbc);

        return formPanel;
    }

    private JButton createStyledButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void loadCarDataToDialogTable() {
        dialogTableModel.setRowCount(0); // Xóa dữ liệu cũ
        List<ProductModel> carList = ProductDAO.getInstance().selectAll();
        for (ProductModel car : carList) {
            Object[] rowData = {
                    car.getMaOto(),
                    car.getTenOto(),
                    car.getGia(),
                    car.getLoaiOto(),
                    car.getSoLuong(),
                    car.getMoTa(),
                    car.getMaHang()
            };
            dialogTableModel.addRow(rowData);
        }
    }

    // (Hàm phụ trợ) Thêm sự kiện cho các nút và bảng trong dialog sửa
    private void addDialogEventListeners(ProductController controller) {
        // Nút Đóng
        btnDong.addActionListener(e -> editDialog.dispose());

        // Nút Sửa (Cập nhật)
        btnSua.addActionListener(e -> controller.sua(
                txtMaOtoEdit.getText(),
                txtTenOtoEdit.getText(),
                txtLoaiOtoEdit.getText(),
                txtGiaEdit.getText(),
                txtSoLuongEdit.getText(),
                txtMaHangEdit.getText(),
                txtMoTaEdit.getText()
        ));

        // Sự kiện click đúp chuột vào bảng phụ
        dialogTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = dialogTable.getSelectedRow();
                    if (row != -1) {
                        txtMaOtoEdit.setText(dialogTableModel.getValueAt(row, 0).toString());
                        txtTenOtoEdit.setText(dialogTableModel.getValueAt(row, 1).toString());
                        txtGiaEdit.setText(dialogTableModel.getValueAt(row, 2).toString());
                        txtLoaiOtoEdit.setText(dialogTableModel.getValueAt(row, 3).toString());
                        txtSoLuongEdit.setText(dialogTableModel.getValueAt(row, 4).toString());
                        txtMoTaEdit.setText(dialogTableModel.getValueAt(row, 5).toString());
                        txtMaHangEdit.setText(dialogTableModel.getValueAt(row, 6).toString());
                    }
                }
            }
        });
    }

    // Đóng dialog "Thêm"
    public void closeAddDialog() {
        if (addDialog != null) addDialog.dispose();
    }

    // Đóng dialog "Sửa"
    public void closeEditDialog() {
        if (editDialog != null) editDialog.dispose();
    }

    // Hiển thị thông báo
    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    // Hiển thị lỗi
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    // Lấy xe đang được chọn trên bảng chính
    public ProductModel getSelectedOto() {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow == -1) {
            return null; // chưa chọn hàng nào
        }
        String maOto = carTable.getValueAt(selectedRow, 0).toString();
        String tenOto = carTable.getValueAt(selectedRow, 1).toString();
        double gia = Double.parseDouble(carTable.getValueAt(selectedRow, 2).toString());
        String loaiOto = carTable.getValueAt(selectedRow, 3).toString();
        int soLuong = Integer.parseInt(carTable.getValueAt(selectedRow, 4).toString());
        String moTa = carTable.getValueAt(selectedRow, 5).toString();
        String maHang = carTable.getValueAt(selectedRow, 6).toString();
        int soLuotBan = Integer.parseInt(carTable.getValueAt(selectedRow, 7).toString());

        return new ProductModel(gia, loaiOto, maOto, moTa, soLuong, tenOto, soLuotBan, maHang);
    }
    public String getSearchKeyword() {
        return jTextField_search.getText();
    }
}
