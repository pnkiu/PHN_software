package view;

import controller.StaffController;
import model.StaffModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffEditDialog extends JPanel {
    private JDialog parentDialog;
    private StaffController controller;
    private String maNV; // SỬA TỪ int SANG String

    private JTextField txtMaNV, txtTenNV, txtLuong, txtSDT, txtTenDangNhap, txtMatKhau;
    private JComboBox<String> cboChucVu;
    private JButton btnSave, btnCancel;

    // SỬA CONSTRUCTOR: tham số maNV từ int sang String
    public StaffEditDialog(JDialog parentDialog, StaffController controller, String maNV) {
        this.parentDialog = parentDialog;
        this.controller = controller;
        this.maNV = maNV;
        initComponents();
        loadStaffData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Sửa Thông Tin Nhân Viên");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Mã NV
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã NV:"), gbc);
        gbc.gridx = 1;
        txtMaNV = new JTextField(20);
        txtMaNV.setEditable(false);
        formPanel.add(txtMaNV, gbc);

        // Tên NV
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Tên NV:"), gbc);
        gbc.gridx = 1;
        txtTenNV = new JTextField(20);
        formPanel.add(txtTenNV, gbc);

        // Lương
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Lương:"), gbc);
        gbc.gridx = 1;
        txtLuong = new JTextField(20);
        formPanel.add(txtLuong, gbc);

        // Số điện thoại
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        txtSDT = new JTextField(20);
        formPanel.add(txtSDT, gbc);

        // Chức vụ
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Chức vụ:"), gbc);
        gbc.gridx = 1;
        cboChucVu = new JComboBox<>(new String[]{"Nhân viên", "Quản trị viên"});
        formPanel.add(cboChucVu, gbc);

        // Tên đăng nhập
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        txtTenDangNhap = new JTextField(20);
        formPanel.add(txtTenDangNhap, gbc);

        // Mật khẩu
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        txtMatKhau = new JTextField(20);
        formPanel.add(txtMatKhau, gbc);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        btnSave = new JButton("💾 Lưu");
        btnCancel = new JButton("❌ Hủy");

        // Style buttons
        btnSave.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // Add event listeners
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStaffData();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentDialog.dispose();
            }
        });

        return buttonPanel;
    }

    private void loadStaffData() {
        try {
            // SỬA: gọi controller với tham số String
            StaffModel staff = controller.getStaffById(maNV);
            if (staff != null) {
                // SỬA: không cần String.valueOf() vì maNV đã là String
                txtMaNV.setText(staff.getMaNV());
                txtTenNV.setText(staff.getTenNV());
                txtLuong.setText(String.valueOf(staff.getLuongNV()));
                txtSDT.setText(staff.getSdtNV());
                cboChucVu.setSelectedIndex(staff.getChucVu());
                txtTenDangNhap.setText(staff.getTenDangNhap());
                txtMatKhau.setText(staff.getMatKhau());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải thông tin nhân viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveStaffData() {
        try {
            // Lấy thông tin từ form
            String tenNV = txtTenNV.getText().trim();
            String luong = txtLuong.getText().trim(); // GIỮ NGUYÊN KIỂU STRING
            String sdt = txtSDT.getText().trim();
            int chucVu = cboChucVu.getSelectedIndex();
            String tenDangNhap = txtTenDangNhap.getText().trim();
            String matKhau = txtMatKhau.getText().trim();

            // Validation
            if (tenNV.isEmpty() || sdt.isEmpty() || tenDangNhap.isEmpty() || matKhau.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate lương
            try {
                long salaryValue = Long.parseLong(luong);
                if (salaryValue <= 0) {
                    JOptionPane.showMessageDialog(this, "Lương phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Lương phải là số hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate số điện thoại
            if (!sdt.matches("\\d{10,11}")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải có 10-11 chữ số", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo đối tượng StaffModel - SỬA Ở ĐÂY
            StaffModel staff = new StaffModel(maNV, tenNV, luong, sdt, chucVu, tenDangNhap, matKhau);

            // Gọi controller để update
            boolean success = controller.updateStaff(staff);

            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                parentDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}