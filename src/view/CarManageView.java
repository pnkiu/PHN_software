package view;

import controller.CarManageController;
import model.CarManageModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CarManageView extends JFrame {
    private CarManageController controller;
    private DefaultTableModel tableModel;
    private JTable table;
    
    // Components
    private JTextField txtMaOto, txtTenOto, txtGia, txtLoaiOto, txtSoLuong, txtMoTa, txtMaHang, txtSoLuotBan;
    private JButton btnSua;

    public CarManageView() {
        this.controller = new CarManageController();
        setTitle("Sửa Thông Tin Ô Tô");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // Main panel với BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        // Tiêu đề
        JLabel lblTitle = new JLabel("SỬA THÔNG TIN Ô TÔ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 123, 255));
        lblTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

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
        buttonPanel.add(btnSua);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);

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

        mainPanel.add(rightPanel, BorderLayout.CENTER);

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
                int result = controller.updateCar(
                    txtMaOto.getText().trim(),
                    txtTenOto.getText().trim(),
                    Double.parseDouble(txtGia.getText().trim()),
                    txtLoaiOto.getText().trim(),
                    Integer.parseInt(txtSoLuong.getText().trim()),
                    txtMoTa.getText().trim(),
                    txtMaHang.getText().trim(),
                    txtSoLuotBan.getText().isEmpty() ? 0 : Integer.parseInt(txtSoLuotBan.getText().trim())
                );
                
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
        if (!controller.validateCarData(
            txtMaOto.getText().trim(),
            txtTenOto.getText().trim(),
            txtGia.getText().trim(),
            txtSoLuong.getText().trim(),
            txtLoaiOto.getText().trim(),
            txtMaHang.getText().trim())) {
            
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập đầy đủ thông tin các trường bắt buộc (*) và đúng định dạng!");
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
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new CarManageView().setVisible(true);
        });
    }
}