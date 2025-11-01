package view;

import controller.StaffController;
import model.StaffModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffAddDialog extends JPanel {
    private JDialog dialog;
    private StaffController controller;

    // Form fields
    private JTextField txtTenNV;
    private JTextField txtLuongNV;
    private JTextField txtSdtNV;
    private JTextField txtMatKhau;
    private JTextField txtTenDangNhap;
    private JComboBox<String> cbChucVu;
    private JButton btnSave;
    private JButton btnCancel;

    public StaffAddDialog(JDialog dialog, StaffController controller) {
        this.dialog = dialog;
        this.controller = controller;
        initComponents();
        setupLayout();
        setupEventListeners();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Thêm Nhân Viên Mới");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Tên nhân viên
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Tên nhân viên *:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtTenNV = new JTextField(20);
        formPanel.add(txtTenNV, gbc);

        // Lương
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Lương *:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtLuongNV = new JTextField(20);
        formPanel.add(txtLuongNV, gbc);

        // Số điện thoại
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Số điện thoại *:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtSdtNV = new JTextField(20);
        formPanel.add(txtSdtNV, gbc);

        // Tên đăng nhập
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Tên đăng nhập *:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtTenDangNhap = new JTextField(20);
        formPanel.add(txtTenDangNhap, gbc);

        // Mật khẩu
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Mật khẩu *:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtMatKhau = new JTextField(20); // ĐỔI TỪ JPasswordField SANG JTextField
        formPanel.add(txtMatKhau, gbc);

        // Chức vụ
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Chức vụ *:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        String[] chucVuOptions = {"Nhân viên", "Quản trị viên"};
        cbChucVu = new JComboBox<>(chucVuOptions);
        formPanel.add(cbChucVu, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnSave = new JButton("💾 Lưu");
        btnCancel = new JButton("❌ Hủy");

        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // Add components to main panel
        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupLayout() {
        // Set preferred sizes for better appearance
        Dimension fieldSize = new Dimension(250, 30);
        txtTenNV.setPreferredSize(fieldSize);
        txtLuongNV.setPreferredSize(fieldSize);
        txtSdtNV.setPreferredSize(fieldSize);
        txtTenDangNhap.setPreferredSize(fieldSize);
        txtMatKhau.setPreferredSize(fieldSize);
        cbChucVu.setPreferredSize(fieldSize);

        Dimension buttonSize = new Dimension(100, 35);
        btnSave.setPreferredSize(buttonSize);
        btnCancel.setPreferredSize(buttonSize);
    }

    private void setupEventListeners() {
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStaff();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
    }

    private void saveStaff() {
        try {
            // Validate input
            if (!validateInput()) {
                return;
            }

            // Create new staff model - SỬA Ở ĐÂY
            StaffModel newStaff = controller.createNewStaff();
            newStaff.setTenNV(txtTenNV.getText().trim());
            newStaff.setLuongNV(txtLuongNV.getText().trim()); // GIỮ NGUYÊN KIỂU STRING
            newStaff.setSdtNV(txtSdtNV.getText().trim());
            newStaff.setTenDangNhap(txtTenDangNhap.getText().trim());
            newStaff.setMatKhau(txtMatKhau.getText().trim()); // ĐỔI TỪ getPassword() SANG getText()
            newStaff.setChucVu(cbChucVu.getSelectedIndex()); // 0 = Nhân viên, 1 = Quản trị viên

            System.out.println("Thông tin NV trước khi thêm:");
            System.out.println("Mã NV: " + newStaff.getMaNV());
            System.out.println("Tên NV: " + newStaff.getTenNV());
            System.out.println("Lương: " + newStaff.getLuongNV());
            System.out.println("SDT: " + newStaff.getSdtNV());
            System.out.println("Chức vụ: " + newStaff.getChucVu());
            System.out.println("Tên đăng nhập: " + newStaff.getTenDangNhap());
            System.out.println("Mật khẩu: " + newStaff.getMatKhau());

            // Call controller to add staff
            boolean success = controller.addStaff(newStaff);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Thêm nhân viên thành công!\nMã nhân viên: " + newStaff.getMaNV(),
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Thêm nhân viên thất bại! Kiểm tra lại thông tin.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi thêm nhân viên: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean validateInput() {
        // Check required fields
        if (txtTenNV.getText().trim().isEmpty()) {
            showValidationError("Vui lòng nhập tên nhân viên!");
            txtTenNV.requestFocus();
            return false;
        }

        if (txtLuongNV.getText().trim().isEmpty()) {
            showValidationError("Vui lòng nhập lương!");
            txtLuongNV.requestFocus();
            return false;
        }

        if (txtSdtNV.getText().trim().isEmpty()) {
            showValidationError("Vui lòng nhập số điện thoại!");
            txtSdtNV.requestFocus();
            return false;
        }

        if (txtTenDangNhap.getText().trim().isEmpty()) {
            showValidationError("Vui lòng nhập tên đăng nhập!");
            txtTenDangNhap.requestFocus();
            return false;
        }

        if (txtMatKhau.getText().trim().isEmpty()) {
            showValidationError("Vui lòng nhập mật khẩu!");
            txtMatKhau.requestFocus();
            return false;
        }

        // Validate salary is positive number
        try {
            long salary = Long.parseLong(txtLuongNV.getText().trim());
            if (salary <= 0) {
                showValidationError("Lương phải là số dương!");
                txtLuongNV.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            showValidationError("Lương phải là số hợp lệ!");
            txtLuongNV.requestFocus();
            return false;
        }

        // Validate phone number format
        String phone = txtSdtNV.getText().trim();
        if (!phone.matches("\\d{10,11}")) {
            showValidationError("Số điện thoại phải có 10-11 chữ số!");
            txtSdtNV.requestFocus();
            return false;
        }

        // Check if phone already exists
        if (controller.isPhoneExists(phone)) {
            showValidationError("Số điện thoại đã tồn tại trong hệ thống!");
            txtSdtNV.requestFocus();
            return false;
        }

        // Validate username
        String username = txtTenDangNhap.getText().trim();
        if (username.length() < 3) {
            showValidationError("Tên đăng nhập phải có ít nhất 3 ký tự!");
            txtTenDangNhap.requestFocus();
            return false;
        }

        // Check if username already exists
        if (controller.isUsernameExists(username)) {
            showValidationError("Tên đăng nhập đã tồn tại trong hệ thống!");
            txtTenDangNhap.requestFocus();
            return false;
        }

        // BỎ RÀNG BUỘC MẬT KHẨU - chỉ kiểm tra không trống
        if (txtMatKhau.getText().trim().isEmpty()) {
            showValidationError("Mật khẩu không được để trống!");
            txtMatKhau.requestFocus();
            return false;
        }

        return true;
    }

    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Lỗi nhập liệu",
                JOptionPane.WARNING_MESSAGE);
    }
}