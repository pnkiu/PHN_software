package view;

import controller.CustomerController;
import model.CustomerModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDialog extends JDialog {
    private CustomerController controller;
    private CustomerModel customer;
    private boolean success = false;

    private JTextField txtMaKH;
    private JTextField txtTenKH;
    private JTextField txtDiaChi;
    private JTextField txtSDT;
    private JTextField txtTongChiTieu;
    private JTextField txtSoLanMua;

    private JButton btnSave;
    private JButton btnCancel;

    public CustomerDialog(Frame owner, CustomerController controller, CustomerModel customer, boolean isEditMode) {
        super(owner, true);
        this.controller = controller;
        this.customer = customer;

        setTitle(isEditMode ? "Sửa Thông Tin Khách Hàng" : "Thêm Khách Hàng Mới");
        initComponents(isEditMode);
        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
        setSize(400, 400);
    }

    private void initComponents(boolean isEditMode) {
        setLayout(new BorderLayout(10, 10));

        // Main panel với padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tiêu đề
        JLabel titleLabel = new JLabel(isEditMode ? "SỬA THÔNG TIN KHÁCH HÀNG" : "THÊM KHÁCH HÀNG MỚI");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel);

        // Panel chứa các trường nhập liệu
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.weightx = 0.3;

        // Mã KH
        gbc.gridy = 0;
        JLabel lblMaKH = new JLabel("Mã KH:");
        lblMaKH.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblMaKH, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtMaKH = new JTextField(20);
        txtMaKH.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        if (isEditMode && customer != null) {
            txtMaKH.setText(customer.getMaKH());
            txtMaKH.setEditable(false);
            txtMaKH.setBackground(new Color(240, 240, 240));
        } else {
            txtMaKH.setText(controller.getNextMaKH());
            txtMaKH.setEditable(false);
            txtMaKH.setBackground(new Color(240, 240, 240));
        }
        formPanel.add(txtMaKH, gbc);

        // Tên KH
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel lblTenKH = new JLabel("Tên KH:");
        lblTenKH.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblTenKH, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtTenKH = new JTextField(20);
        txtTenKH.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        if (isEditMode && customer != null) {
            txtTenKH.setText(customer.getTenKH());
        }
        formPanel.add(txtTenKH, gbc);

        // Địa chỉ
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0.3;
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblDiaChi, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtDiaChi = new JTextField(20);
        txtDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        if (isEditMode && customer != null) {
            txtDiaChi.setText(customer.getDckH());
        }
        formPanel.add(txtDiaChi, gbc);

        // Số điện thoại
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 0.3;
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblSDT, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtSDT = new JTextField(20);
        txtSDT.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        if (isEditMode && customer != null) {
            txtSDT.setText(customer.getSdtKH());
        }
        formPanel.add(txtSDT, gbc);

        // Tổng chi tiêu
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.weightx = 0.3;
        JLabel lblTongChiTieu = new JLabel("Tổng chi tiêu:");
        lblTongChiTieu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblTongChiTieu, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtTongChiTieu = new JTextField(20);
        txtTongChiTieu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtTongChiTieu.setText("0");
        if (isEditMode && customer != null) {
            txtTongChiTieu.setText(String.valueOf(customer.getTongChiTieu()));
        }
        txtTongChiTieu.setEditable(false);
        txtTongChiTieu.setBackground(new Color(240, 240, 240));
        formPanel.add(txtTongChiTieu, gbc);

        // Số lần mua
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.weightx = 0.3;
        JLabel lblSoLanMua = new JLabel("Số lần mua:");
        lblSoLanMua.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblSoLanMua, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtSoLanMua = new JTextField(20);
        txtSoLanMua.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSoLanMua.setText("0");
        if (isEditMode && customer != null) {
            txtSoLanMua.setText(String.valueOf(customer.getSoLanMua()));
        }
        txtSoLanMua.setEditable(false);
        txtSoLanMua.setBackground(new Color(240, 240, 240));
        formPanel.add(txtSoLanMua, gbc);

        mainPanel.add(formPanel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        btnSave = new JButton("LƯU");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSave.setBackground(new Color(70, 130, 180));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setPreferredSize(new Dimension(100, 30));

        btnCancel = new JButton("HỦY");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancel.setBackground(new Color(220, 80, 60));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        btnCancel.setPreferredSize(new Dimension(100, 30));

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        mainPanel.add(buttonPanel);

        add(mainPanel, BorderLayout.CENTER);

        // Event listeners
        setupEventListeners(isEditMode);
    }

    private void setupEventListeners(boolean isEditMode) {
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCustomer(isEditMode);
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                success = false;
                dispose();
            }
        });

        // Enter key listener for form submission
        txtTenKH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCustomer(isEditMode);
            }
        });

        txtDiaChi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCustomer(isEditMode);
            }
        });

        txtSDT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCustomer(isEditMode);
            }
        });
    }

    private void saveCustomer(boolean isEditMode) {
        // Validate data
        if (txtTenKH.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập tên khách hàng",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            txtTenKH.requestFocus();
            return;
        }

        if (txtSDT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập số điện thoại",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            txtSDT.requestFocus();
            return;
        }

        // Validate số điện thoại
        String phoneNumber = txtSDT.getText().trim();
        if (!phoneNumber.matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this,
                    "Số điện thoại phải có 10-11 chữ số",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            txtSDT.requestFocus();
            return;
        }

        try {
            CustomerModel customerToSave;
            if (isEditMode && customer != null) {
                customerToSave = customer;
            } else {
                customerToSave = new CustomerModel();
            }

            customerToSave.setMaKH(txtMaKH.getText().trim());
            customerToSave.setTenKH(txtTenKH.getText().trim());
            customerToSave.setDckH(txtDiaChi.getText().trim());
            customerToSave.setSdtKH(txtSDT.getText().trim());
            customerToSave.setTongChiTieu(Long.parseLong(txtTongChiTieu.getText().trim()));
            customerToSave.setSoLanMua(Integer.parseInt(txtSoLanMua.getText().trim()));

            boolean result;
            if (isEditMode) {
                result = controller.updateCustomer(customerToSave);
            } else {
                result = controller.addCustomer(customerToSave);
            }

            if (result) {
                success = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi lưu khách hàng",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi lưu khách hàng: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public boolean isSuccess() {
        return success;
    }
}