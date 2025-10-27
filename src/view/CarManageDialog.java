package view;

import controller.CarManageController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CarManageDialog extends JPanel {
    private CarManageController controller;
    private DefaultTableModel tableModel;
    private JTable table;
    private JDialog parentDialog;

    // Components
    private JTextField txtMaOto, txtTenOto, txtGia, txtLoaiOto, txtSoLuong, txtMoTa, txtMaHang, txtSoLuotBan;
    private JButton btnSua, btnDong;

    public CarManageDialog(JDialog parent) {
        this.parentDialog = parent;
        this.controller = new CarManageController();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tiêu đề
        JLabel lblTitle = new JLabel("SỬA THÔNG TIN Ô TÔ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 123, 255));
        lblTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Panel chính chứa cả form và bảng
        JPanel mainContentPanel = new JPanel(new BorderLayout(10, 10));

        // Panel trái cho form nhập liệu
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(400, 0));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Thông tin ô tô cần sửa"));

        // Form nhập liệu
        JPanel formPanel = createFormPanel();
        leftPanel.add(formPanel, BorderLayout.CENTER);

        // Panel nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnSua = createStyledButton("✏️ Cập Nhật", new Color(255, 193, 7));
        btnDong = createStyledButton("❌ Đóng", new Color(108, 117, 125));

        buttonPanel.add(btnSua);
        buttonPanel.add(btnDong);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainContentPanel.add(leftPanel, BorderLayout.WEST);

        // Panel phải cho bảng dữ liệu
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Danh sách ô tô - Click đúp để chọn"));

        // Bảng dữ liệu
        String[] columns = {"Mã ô tô", "Tên ô tô", "Giá", "Loại ô tô", "Số lượng", "Mô tả", "Mã hãng", "Số lượt bán"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        mainContentPanel.add(rightPanel, BorderLayout.CENTER);
        add(mainContentPanel, BorderLayout.CENTER);

        // Thêm sự kiện
        addEventListeners();

        // Load dữ liệu mẫu cho bảng
        loadSampleData();
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 8));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Mã ô tô
        formPanel.add(new JLabel("Mã ô tô*:"));
        txtMaOto = new JTextField();
        txtMaOto.setEditable(false);
        txtMaOto.setBackground(new Color(240, 240, 240));
        formPanel.add(txtMaOto);

        // Tên ô tô
        formPanel.add(new JLabel("Tên ô tô*:"));
        txtTenOto = new JTextField();
        formPanel.add(txtTenOto);

        // Giá
        formPanel.add(new JLabel("Giá*:"));
        txtGia = new JTextField();
        formPanel.add(txtGia);

        // Loại ô tô
        formPanel.add(new JLabel("Loại ô tô*:"));
        txtLoaiOto = new JTextField();
        formPanel.add(txtLoaiOto);

        // Số lượng
        formPanel.add(new JLabel("Số lượng*:"));
        txtSoLuong = new JTextField();
        formPanel.add(txtSoLuong);

        // Mô tả
        formPanel.add(new JLabel("Mô tả:"));
        txtMoTa = new JTextField();
        formPanel.add(txtMoTa);

        // Mã hãng
        formPanel.add(new JLabel("Mã hãng*:"));
        txtMaHang = new JTextField();
        formPanel.add(txtMaHang);

        // Số lượt bán
        formPanel.add(new JLabel("Số lượt bán:"));
        txtSoLuotBan = new JTextField();
        txtSoLuotBan.setEditable(false);
        txtSoLuotBan.setBackground(new Color(240, 240, 240));
        formPanel.add(txtSoLuotBan);

        return formPanel;
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void addEventListeners() {
        btnSua.addActionListener(e -> suaOto());
        btnDong.addActionListener(e -> parentDialog.dispose());

        // Sự kiện click đúp vào bảng để chọn ô tô cần sửa
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        hienThiThongTinTuTable(selectedRow);
                    }
                }
            }
        });
    }

    private void suaOto() {
        try {
            if (txtMaOto.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ô tô cần sửa từ bảng!");
                return;
            }

            if (validateInput()) {
                // Giả lập cập nhật thành công
                int result = 1; // Giả sử luôn thành công

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin ô tô thành công!");
                    loadSampleData(); // Reload data
                    lamMoiForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin thất bại!");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void hienThiThongTinTuTable(int row) {
        txtMaOto.setText(tableModel.getValueAt(row, 0).toString());
        txtTenOto.setText(tableModel.getValueAt(row, 1).toString());
        txtGia.setText(tableModel.getValueAt(row, 2).toString().replace("₫", "").replace(",", "").trim());
        txtLoaiOto.setText(tableModel.getValueAt(row, 3).toString());
        txtSoLuong.setText(tableModel.getValueAt(row, 4).toString());
        txtMoTa.setText(tableModel.getValueAt(row, 5).toString());
        txtMaHang.setText(tableModel.getValueAt(row, 6).toString());
        txtSoLuotBan.setText(tableModel.getValueAt(row, 7).toString());
    }

    private void lamMoiForm() {
        txtMaOto.setText("");
        txtTenOto.setText("");
        txtGia.setText("");
        txtLoaiOto.setText("");
        txtSoLuong.setText("");
        txtMoTa.setText("");
        txtMaHang.setText("");
        txtSoLuotBan.setText("");
        table.clearSelection();
    }

    private boolean validateInput() {
        if (txtMaOto.getText().trim().isEmpty() ||
                txtTenOto.getText().trim().isEmpty() ||
                txtGia.getText().trim().isEmpty() ||
                txtSoLuong.getText().trim().isEmpty() ||
                txtLoaiOto.getText().trim().isEmpty() ||
                txtMaHang.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ thông tin các trường bắt buộc (*)!");
            return false;
        }

        // Kiểm tra định dạng số
        try {
            Double.parseDouble(txtGia.getText().trim());
            Integer.parseInt(txtSoLuong.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Giá và số lượng phải là số hợp lệ!");
            return false;
        }

        return true;
    }

    private void loadSampleData() {
        // Dữ liệu mẫu cho demo
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{"OTO001", "Toyota Camry", "₫ 850,000,000", "Sedan", "5", "Xe sang trọng", "TOYOTA", "10"});
        tableModel.addRow(new Object[]{"OTO002", "Honda Civic", "₫ 600,000,000", "Sedan", "3", "Xe thể thao", "HONDA", "8"});
        tableModel.addRow(new Object[]{"OTO003", "Ford Ranger", "₫ 750,000,000", "Bán tải", "7", "Xe đa dụng", "FORD", "5"});
        tableModel.addRow(new Object[]{"OTO004", "Hyundai SantaFe", "₫ 980,000,000", "SUV", "12", "SUV 7 chỗ, đầy đủ tiện nghi", "HYUNDAI", "15"});
        tableModel.addRow(new Object[]{"OTO005", "Mazda CX-5", "₫ 820,000,000", "SUV", "10", "SUV 5 chỗ, thiết kế trẻ trung", "MAZDA", "12"});
    }
}