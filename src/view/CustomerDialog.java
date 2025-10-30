package view;

import controller.CustomerController;
import model.CustomerModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDialog extends JPanel {
    private CustomerController controller;
    private CustomerModel customer;
    private boolean isEditMode;

    // Components
    private JTextField txtMaKH;
    private JTextField txtTenKH;
    private JTextField txtDiaChi;
    private JTextField txtSDT;
    private JTextField txtTongChiTieu;
    private JTextField txtSoLanMua;
    private JButton btnSave;
    private JButton btnCancel;

    // Listeners
    private OnCustomerSavedListener savedListener;

    public interface OnCustomerSavedListener {
        void onCustomerSaved();
    }

    public CustomerDialog(CustomerController controller, CustomerModel customer) {
        this.controller = controller;
        this.customer = customer;
        this.isEditMode = (customer != null);

        initComponents();
        setupLayout();
        setupEventListeners();

        if (isEditMode) {
            loadCustomerData();
        } else {
            setDefaultValues();
        }
    }

    private void initComponents() {
        // Initialize components
        txtMaKH = new JTextField(15);
        txtMaKH.setEditable(false);
        txtMaKH.setBackground(new Color(240, 240, 240));

        txtTenKH = new JTextField(15);
        txtDiaChi = new JTextField(15);
        txtSDT = new JTextField(15);
        txtTongChiTieu = new JTextField(15);
        txtTongChiTieu.setEditable(false);
        txtTongChiTieu.setBackground(new Color(240, 240, 240));
        txtSoLanMua = new JTextField(15);
        txtSoLanMua.setEditable(false);
        txtSoLanMua.setBackground(new Color(240, 240, 240));

        btnSave = new JButton("💾 Lưu");
        btnCancel = new JButton("❌ Hủy");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Title
        String title = isEditMode ? "SỬA THÔNG TIN KHÁCH HÀNG" : "THÊM KHÁCH HÀNG MỚI";
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Mã KH
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Mã KH:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtMaKH, gbc);

        // Tên KH
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabel("Tên KH *:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtTenKH, gbc);

        // Địa chỉ
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createLabel("Địa chỉ *:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDiaChi, gbc);

        // Số điện thoại
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createLabel("Số điện thoại *:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtSDT, gbc);

        // Tổng chi tiêu
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(createLabel("Tổng chi tiêu:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtTongChiTieu, gbc);

        // Số lần mua
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(createLabel("Số lần mua:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtSoLanMua, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // Add components to main panel
        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setPreferredSize(new Dimension(120, 25));
        return label;
    }

    private void setupEventListeners() {
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCustomer();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeDialog();
            }
        });
    }

    private void loadCustomerData() {
        if (customer != null) {
            txtMaKH.setText(String.valueOf(customer.getMaKH()));
            txtTenKH.setText(customer.getTenKH());
            txtDiaChi.setText(customer.getDckH());
            txtSDT.setText(customer.getSdtKH());
            txtTongChiTieu.setText(String.format("%,d VND", customer.getTongChiTieu()));
            txtSoLanMua.setText(String.valueOf(customer.getSoLanMua()));
        }
    }

    private void setDefaultValues() {
        txtMaKH.setText("Tự động tạo");
        txtTongChiTieu.setText("0 VND");
        txtSoLanMua.setText("0");
    }

    private void saveCustomer() {
        if (!validateInput()) {
            return;
        }

        try {
            boolean success;
            String successMessage;

            if (isEditMode) {
                // Update existing customer
                customer.setTenKH(txtTenKH.getText().trim());
                customer.setDckH(txtDiaChi.getText().trim());
                customer.setSdtKH(txtSDT.getText().trim());
                success = controller.updateCustomer(customer);
                successMessage = "Cập nhật khách hàng thành công!";
            } else {
                // Add new customer
                CustomerModel newCustomer = new CustomerModel();
                newCustomer.setTenKH(txtTenKH.getText().trim());
                newCustomer.setDckH(txtDiaChi.getText().trim());
                newCustomer.setSdtKH(txtSDT.getText().trim());
                newCustomer.setTongChiTieu(0);
                newCustomer.setSoLanMua(0);
                success = controller.addCustomer(newCustomer);
                successMessage = "Thêm khách hàng thành công!";
            }

            if (success) {
                if (savedListener != null) {
                    savedListener.onCustomerSaved();
                }
                closeDialog();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Có lỗi xảy ra khi lưu dữ liệu",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Lỗi dữ liệu",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi hệ thống: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean validateInput() {
        // Validate tên KH
        if (txtTenKH.getText().trim().isEmpty()) {
            showValidationError("Vui lòng nhập tên khách hàng", txtTenKH);
            return false;
        }

        // Validate địa chỉ
        if (txtDiaChi.getText().trim().isEmpty()) {
            showValidationError("Vui lòng nhập địa chỉ", txtDiaChi);
            return false;
        }

        // Validate số điện thoại
        String phone = txtSDT.getText().trim();
        if (phone.isEmpty()) {
            showValidationError("Vui lòng nhập số điện thoại", txtSDT);
            return false;
        }

        // Validate định dạng số điện thoại
        if (!phone.matches("\\d{10,11}")) {
            showValidationError("Số điện thoại phải có 10-11 chữ số", txtSDT);
            return false;
        }

        return true;
    }

    private void showValidationError(String message, JComponent component) {
        JOptionPane.showMessageDialog(this,
                message,
                "Lỗi nhập liệu",
                JOptionPane.ERROR_MESSAGE);
        component.requestFocus();
    }

    private void closeDialog() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    // Public methods
    public void setOnCustomerSavedListener(OnCustomerSavedListener listener) {
        this.savedListener = listener;
    }

    public static void showAddDialog(Component parent, CustomerController controller, OnCustomerSavedListener listener) {
        JDialog dialog = createDialog(parent, "Thêm Khách Hàng Mới");
        CustomerDialog customerDialog = new CustomerDialog(controller, null);
        customerDialog.setOnCustomerSavedListener(listener);
        setupDialog(dialog, customerDialog);
    }

    public static void showEditDialog(Component parent, CustomerController controller, CustomerModel customer, OnCustomerSavedListener listener) {
        JDialog dialog = createDialog(parent, "Sửa Thông Tin Khách Hàng");
        CustomerDialog customerDialog = new CustomerDialog(controller, customer);
        customerDialog.setOnCustomerSavedListener(listener);
        setupDialog(dialog, customerDialog);
    }

    private static JDialog createDialog(Component parent, String title) {
        JDialog dialog = new JDialog(
                SwingUtilities.getWindowAncestor(parent),
                title,
                Dialog.ModalityType.APPLICATION_MODAL
        );
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);
        return dialog;
    }

    private static void setupDialog(JDialog dialog, CustomerDialog customerDialog) {
        dialog.setContentPane(customerDialog);
        dialog.setVisible(true);
    }
}