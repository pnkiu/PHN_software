package view;

import controller.StaffController;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.StaffModel;

public class StaffView extends JPanel {
    // Các component chính
    private JButton jButton_add;
    private JButton jButton_edit;
    private JButton jButton_delete;
    private JTextField jTextField_search;
    private JButton jButton_search;
    private JComboBox<String> jComboBox_searchType;

    private JTable staffTable;
    private DefaultTableModel tableModel;

    // Controller
    private StaffController controller;

    // public StaffView() {
    //     this.init();
    // }

    public StaffView(StaffController controller) {
    this.controller = controller; // Nhận controller từ bên ngoài
    this.init(); // Gọi hàm init của bạn
}

    public void init() {
        Font font = new Font("Arial", Font.BOLD, 40);

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        JPanel jPanel_right = new JPanel(new BorderLayout());
        jPanel_right.setBackground(Color.WHITE);

        JLabel jLabel_header = new JLabel("QUẢN LÝ NHÂN VIÊN", SwingConstants.CENTER);
        jLabel_header.setForeground(Color.BLACK);
        jLabel_header.setFont(font);
        jLabel_header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel jPanel_action = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        jPanel_action.setBackground(Color.WHITE);

        jButton_add = new JButton("Thêm Nhân Viên");
        jButton_edit = new JButton("Sửa");
        jButton_delete = new JButton("Xóa");
        jTextField_search = new JTextField(20);
        jButton_search = new JButton("Tìm kiếm");

        // ComboBox cho loại tìm kiếm
        String[] searchTypes = {"Tất cả", "Mã NV", "Tên NV", "Số điện thoại", "Chức vụ"};
        jComboBox_searchType = new JComboBox<>(searchTypes);

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

        // Bảng nhân viên
        String[] columnNames = {"Mã NV", "Tên NV", "Số Điện Thoại", "Lương", "Chức Vụ", "Tên Đăng Nhập"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        staffTable = new JTable(tableModel);
        staffTable.setFont(new Font("Arial", Font.PLAIN, 14));
        staffTable.setRowHeight(30);
        staffTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        staffTable.getTableHeader().setBackground(new Color(52, 73, 94));
        staffTable.getTableHeader().setForeground(Color.WHITE);
        staffTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(staffTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel_right.add(scrollPane, BorderLayout.CENTER);

        this.add(jPanel_right, BorderLayout.CENTER);

        // Khởi tạo controller
        // controller = new StaffController();

        // Load dữ liệu ban đầu
        loadStaffData();

        // Thêm event listeners
        setupEventListeners();
    }

    // ============================ EVENT LISTENERS SETUP ============================
    private void setupEventListeners() {
        jButton_add.addActionListener(e -> showAddDialog());
        jButton_edit.addActionListener(e -> showEditDialog());
        jButton_delete.addActionListener(e -> deleteSelectedStaff());
        jButton_search.addActionListener(e -> performSearch());

        // Thêm sự kiện Enter để tìm kiếm
        jTextField_search.addActionListener(e -> performSearch());
    }

    // ============================ SEARCH FUNCTIONALITY (SỬA HOÀN TOÀN) ============================
    public void performSearch() {
        String keyword = getSearchKeyword();
        String searchType = getSearchType();

        System.out.println("=== BẮT ĐẦU TÌM KIẾM ===");
        System.out.println("Loại tìm kiếm: " + searchType);
        System.out.println("Từ khóa: '" + keyword + "'");

        // Nếu từ khóa rỗng, hiển thị tất cả
        if (keyword.isEmpty()) {
            System.out.println("Từ khóa rỗng - hiển thị tất cả");
            loadStaffData();
            showSuccessMessage("Đã hiển thị tất cả nhân viên");
            return;
        }

        try {
            ArrayList<StaffModel> searchResult = new ArrayList<>();

            switch (searchType) {
                case "Mã NV":
                    System.out.println("Gọi searchByMaNV...");
                    searchResult = controller.searchByMaNV(keyword);
                    break;
                case "Tên NV":
                    System.out.println("Gọi searchByTenNV...");
                    searchResult = controller.searchByTenNV(keyword);
                    break;
                case "Số điện thoại":
                    System.out.println("Gọi searchBySDT...");
                    searchResult = controller.searchBySDT(keyword);
                    break;
                case "Chức vụ":
                    System.out.println("Gọi searchByChucVu...");
                    searchResult = searchByChucVu(keyword);
                    break;
                default: // Tất cả
                    System.out.println("Gọi searchAllFields...");
                    searchResult = controller.searchAllFields(keyword);
                    break;
            }

            System.out.println("Số kết quả tìm được: " + searchResult.size());

            // Hiển thị chi tiết kết quả để debug
            for (StaffModel staff : searchResult) {
                System.out.println(" - " + staff.getMaNV() + " - " + staff.getTenNV());
            }

            if (searchResult.isEmpty()) {
                showErrorMessage("Không tìm thấy nhân viên nào phù hợp với từ khóa '" + keyword + "'");
                // Vẫn hiển thị kết quả rỗng để user biết
                hienthidulieu(searchResult);
            } else {
                hienthidulieu(searchResult);
                // showSuccessMessage("Tìm thấy " + searchResult.size() + " nhân viên phù hợp");
            }

        } catch (Exception e) {
            System.out.println("LỖI KHI TÌM KIẾM: " + e.getMessage());
            e.printStackTrace();
            showErrorMessage("Lỗi khi tìm kiếm: " + e.getMessage());

            // Fallback: hiển thị tất cả nếu có lỗi
            loadStaffData();
        }
    }

    // ============================ PHƯƠNG THỨC TÌM KIẾM THEO CHỨC VỤ (SỬA LẠI) ============================
    private ArrayList<StaffModel> searchByChucVu(String keyword) {
        ArrayList<StaffModel> result = new ArrayList<>();
        ArrayList<StaffModel> allStaff = controller.getStaffList();

        String keywordLower = keyword.toLowerCase().trim();
        System.out.println("Tìm kiếm chức vụ với từ khóa: " + keywordLower);

        for (StaffModel staff : allStaff) {
            String chucVuText = (staff.getChucVu() == 0) ? "quản lý" : "nhân viên";
            int chucVuValue = staff.getChucVu();

            System.out.println("Kiểm tra nhân viên: " + staff.getTenNV() + " - Chức vụ: " + chucVuText + "(" + chucVuValue + ")");

            // Tìm kiếm linh hoạt
            boolean match = false;
            if (chucVuText.contains(keywordLower)) {
                match = true;
                System.out.println("  -> Khớp qua tên chức vụ");
            } else if (keywordLower.equals("ql") && chucVuValue == 0) {
                match = true;
                System.out.println("  -> Khớp qua 'ql'");
            } else if (keywordLower.equals("nv") && chucVuValue == 1) {
                match = true;
                System.out.println("  -> Khớp qua 'nv'");
            } else if (keywordLower.equals("0") && chucVuValue == 0) {
                match = true;
                System.out.println("  -> Khớp qua '0'");
            } else if (keywordLower.equals("1") && chucVuValue == 1) {
                match = true;
                System.out.println("  -> Khớp qua '1'");
            } else if (keywordLower.equals("quan ly") && chucVuValue == 0) {
                match = true;
                System.out.println("  -> Khớp qua 'quan ly'");
            } else if (keywordLower.equals("nhan vien") && chucVuValue == 1) {
                match = true;
                System.out.println("  -> Khớp qua 'nhan vien'");
            }

            if (match) {
                result.add(staff);
                System.out.println("  -> THÊM VÀO KẾT QUẢ");
            }
        }

        System.out.println("Tổng kết quả chức vụ: " + result.size());
        return result;
    }

    // ============================ TEST SEARCH (PHƯƠNG THỨC KIỂM TRA) ============================
    private void testSearchMethods() {
        System.out.println("=== KIỂM TRA PHƯƠNG THỨC TÌM KIẾM ===");

        // Test searchAllFields
        System.out.println("1. Testing searchAllFields...");
        ArrayList<StaffModel> result1 = controller.searchAllFields("NV");
        System.out.println("searchAllFields('NV'): " + result1.size() + " kết quả");

        // Test searchByMaNV
        System.out.println("2. Testing searchByMaNV...");
        ArrayList<StaffModel> result2 = controller.searchByMaNV("NV");
        System.out.println("searchByMaNV('NV'): " + result2.size() + " kết quả");

        // Test searchByTenNV
        System.out.println("3. Testing searchByTenNV...");
        ArrayList<StaffModel> result3 = controller.searchByTenNV("Nguyễn");
        System.out.println("searchByTenNV('Nguyễn'): " + result3.size() + " kết quả");

        // Test searchBySDT
        System.out.println("4. Testing searchBySDT...");
        ArrayList<StaffModel> result4 = controller.searchBySDT("0123");
        System.out.println("searchBySDT('0123'): " + result4.size() + " kết quả");

        // Test controller có sẵn sàng không
        System.out.println("5. Testing controller readiness...");
        boolean ready = controller.isReady();
        System.out.println("Controller ready: " + ready);

        // Test get all staff
        System.out.println("6. Testing get all staff...");
        ArrayList<StaffModel> allStaff = controller.getStaffList();
        System.out.println("Tổng số nhân viên: " + allStaff.size());
        for (StaffModel staff : allStaff) {
            System.out.println(" - " + staff.getMaNV() + " | " + staff.getTenNV() + " | " + staff.getSdtNV() + " | " + staff.getChucVu());
        }
    }

    // ============================ ADD STAFF DIALOG ============================
    private void showAddDialog() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog addDialog = new JDialog(parentFrame, "Thêm Nhân Viên Mới", true);
        addDialog.setSize(500, 500);
        addDialog.setLocationRelativeTo(this);
        addDialog.setResizable(false);

        // Tạo panel chứa form thêm nhân viên
        JPanel addPanel = createAddDialogPanel(addDialog);
        addDialog.add(addPanel);
        addDialog.setVisible(true);

        // Refresh data after dialog closes
        loadStaffData();
    }

    private JPanel createAddDialogPanel(JDialog dialog) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

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
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        JLabel lblTenNV = new JLabel("Tên nhân viên *:");
        formPanel.add(lblTenNV, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        JTextField txtTenNV = new JTextField(20);
        txtTenNV.setPreferredSize(new Dimension(250, 30));
        formPanel.add(txtTenNV, gbc);

        // Lương
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        formPanel.add(new JLabel("Lương *:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        JTextField txtLuongNV = new JTextField(20);
        txtLuongNV.setPreferredSize(new Dimension(250, 30));
        formPanel.add(txtLuongNV, gbc);

        // Số điện thoại
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        formPanel.add(new JLabel("Số điện thoại *:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        JTextField txtSdtNV = new JTextField(20);
        txtSdtNV.setPreferredSize(new Dimension(250, 30));
        formPanel.add(txtSdtNV, gbc);

        // Tên đăng nhập
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        formPanel.add(new JLabel("Tên đăng nhập *:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        JTextField txtTenDangNhap = new JTextField(20);
        txtTenDangNhap.setPreferredSize(new Dimension(250, 30));
        formPanel.add(txtTenDangNhap, gbc);

        // Mật khẩu
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.3;
        formPanel.add(new JLabel("Mật khẩu *:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        JTextField txtMatKhau = new JTextField(20);
        txtMatKhau.setPreferredSize(new Dimension(250, 30));
        formPanel.add(txtMatKhau, gbc);

        // Chức vụ
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.3;
        formPanel.add(new JLabel("Chức vụ *:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        String[] chucVuOptions = {"Nhân viên", "Quản lý"};
        JComboBox<String> cbChucVu = new JComboBox<>(chucVuOptions);
        cbChucVu.setPreferredSize(new Dimension(250, 30));
        formPanel.add(cbChucVu, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnSave = new JButton("💾 Lưu");
        JButton btnCancel = new JButton("❌ Hủy");

        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnSave.setPreferredSize(new Dimension(100, 35));
        btnCancel.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // Add components to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Event listeners
        btnCancel.addActionListener(e -> dialog.dispose());

        btnSave.addActionListener(e -> {
            if (validateAddInput(txtTenNV, txtLuongNV, txtSdtNV, txtTenDangNhap, txtMatKhau)) {
                saveNewStaff(txtTenNV, txtLuongNV, txtSdtNV, txtTenDangNhap, txtMatKhau, cbChucVu, dialog);
            }
        });

        return mainPanel;
    }

    private boolean validateAddInput(JTextField txtTenNV, JTextField txtLuongNV, JTextField txtSdtNV,
                                    JTextField txtTenDangNhap, JTextField txtMatKhau) {
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

        return true;
    }

    private void saveNewStaff(JTextField txtTenNV, JTextField txtLuongNV, JTextField txtSdtNV,
                            JTextField txtTenDangNhap, JTextField txtMatKhau,
                            JComboBox<String> cbChucVu, JDialog dialog) {
        try {
            // Create new staff model
            StaffModel newStaff = controller.createNewStaff();
            newStaff.setTenNV(txtTenNV.getText().trim());
            try {
                long luong = Long.parseLong(txtLuongNV.getText().trim()); // Chuyển String sang long
                newStaff.setLuongNV(luong); // Giờ đã là long, gán thành công
                } catch (NumberFormatException e) {
                    showValidationError("Lương phải là một con số hợp lệ!");
                    return; // Dừng lại nếu nhập sai
                    }
            newStaff.setSdtNV(txtSdtNV.getText().trim());
            newStaff.setTenDangNhap(txtTenDangNhap.getText().trim());
            newStaff.setMatKhau(txtMatKhau.getText().trim());
            newStaff.setChucVu(cbChucVu.getSelectedIndex()); // 0 = Nhân viên, 1 = Quản lý

            System.out.println("Thông tin NV trước khi thêm:");
            System.out.println("Mã NV: " + newStaff.getMaNV());
            System.out.println("Tên NV: " + newStaff.getTenNV());
            System.out.println("Lương: " + newStaff.getLuongNV());
            System.out.println("SDT: " + newStaff.getSdtNV());
            System.out.println("Chức vụ: " + newStaff.getChucVu());
            System.out.println("Tên đăng nhập: " + newStaff.getTenDangNhap());
            System.out.println("Mật khẩu: " + newStaff.getMatKhau());

            // Call controller to add staff
            boolean success = controller.insertStaff(newStaff);

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

    // ============================ EDIT STAFF DIALOG ============================
    private void showEditDialog() {
        StaffModel selectedStaff = getSelectedStaff();
        if (selectedStaff == null) {
            showErrorMessage("Vui lòng chọn nhân viên cần sửa!");
            return;
        }

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog editDialog = new JDialog(parentFrame, "Sửa Thông Tin Nhân Viên", true);
        editDialog.setSize(500, 500);
        editDialog.setLocationRelativeTo(this);
        editDialog.setResizable(false);

        // Tạo panel chứa form sửa nhân viên
        JPanel editPanel = createEditDialogPanel(editDialog, selectedStaff.getMaNV());
        editDialog.add(editPanel);
        editDialog.setVisible(true);

        // Refresh data after dialog closes
        loadStaffData();
    }

    private JPanel createEditDialogPanel(JDialog parentDialog, String maNV) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Sửa Thông Tin Nhân Viên");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titlePanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Mã NV
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã NV:"), gbc);
        gbc.gridx = 1;
        JTextField txtMaNV = new JTextField(20);
        txtMaNV.setEditable(false);
        formPanel.add(txtMaNV, gbc);

        // Tên NV
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Tên NV:"), gbc);
        gbc.gridx = 1;
        JTextField txtTenNV = new JTextField(20);
        formPanel.add(txtTenNV, gbc);

        // Lương
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Lương:"), gbc);
        gbc.gridx = 1;
        JTextField txtLuong = new JTextField(20);
        formPanel.add(txtLuong, gbc);

        // Số điện thoại
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        JTextField txtSDT = new JTextField(20);
        formPanel.add(txtSDT, gbc);

        // Chức vụ
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Chức vụ:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> cboChucVu = new JComboBox<>(new String[]{"Nhân viên", "Quản lý"});
        formPanel.add(cboChucVu, gbc);

        // Tên đăng nhập
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        JTextField txtTenDangNhap = new JTextField(20);
        formPanel.add(txtTenDangNhap, gbc);

        // Mật khẩu
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        JTextField txtMatKhau = new JTextField(20);
        formPanel.add(txtMatKhau, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");

        btnSave.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // Load staff data
        loadStaffDataToForm(txtMaNV, txtTenNV, txtLuong, txtSDT, cboChucVu, txtTenDangNhap, txtMatKhau, maNV);

        // Add event listeners
        btnSave.addActionListener(e -> {
            saveStaffData(txtMaNV, txtTenNV, txtLuong, txtSDT, cboChucVu, txtTenDangNhap, txtMatKhau, parentDialog);
        });

        btnCancel.addActionListener(e -> {
            parentDialog.dispose();
        });

        // Add components to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private void loadStaffDataToForm(JTextField txtMaNV, JTextField txtTenNV, JTextField txtLuong,
                                    JTextField txtSDT, JComboBox<String> cboChucVu,
                                    JTextField txtTenDangNhap, JTextField txtMatKhau, String maNV) {
        try {
            StaffModel staff = controller.getStaffById(maNV);
            if (staff != null) {
                txtMaNV.setText(staff.getMaNV());
                txtTenNV.setText(staff.getTenNV());
                // txtLuong.setText(staff.getLuongNV());
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

    private void saveStaffData(JTextField txtMaNV, JTextField txtTenNV, JTextField txtLuong,
                            JTextField txtSDT, JComboBox<String> cboChucVu,
                            JTextField txtTenDangNhap, JTextField txtMatKhau, JDialog parentDialog) {
                                long salaryValue = 0;
        try {
            // Lấy thông tin từ form
            String tenNV = txtTenNV.getText().trim();
            String luong = txtLuong.getText().trim();
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
                salaryValue = Long.parseLong(luong);
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

            // Tạo đối tượng StaffModel
            StaffModel staff = new StaffModel(txtMaNV.getText(), tenNV, salaryValue, sdt, chucVu, tenDangNhap, matKhau);
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

    // ============================ DATA DISPLAY ============================
    public void loadStaffData() {
        ArrayList<StaffModel> staffList = controller.getStaffList();
        hienthidulieu(staffList);
    }

    public void hienthidulieu(ArrayList<StaffModel> staffList) {
        tableModel.setRowCount(0);
        for (StaffModel staff : staffList) {
            String chucVu = staff.getChucVu() == 1 ? "Quản lý" : "Nhân viên";
            Object[] rowData = {
                    staff.getMaNV(),
                    staff.getTenNV(),
                    staff.getSdtNV(),
                    formatCurrency(staff.getLuongNV()),
                    chucVu,
                    staff.getTenDangNhap()
            };
            tableModel.addRow(rowData);
        }
    }

    // private String formatCurrency(String amount) {
    //     try {
    //         long value = Long.parseLong(amount);
    //         return String.format("%,d VNĐ", value);
    //     } catch (NumberFormatException e) {
    //         return amount;
    //     }   
    // }

    private String formatCurrency(long amount) { // ⬅️ Sửa tham số thành long
    try {
        return String.format("%,d VNĐ", amount); // Dùng 'amount' trực tiếp
    } catch (Exception e) {
        return String.valueOf(amount); // Trả về nếu lỗi
    }
}

    // ============================ DELETE FUNCTIONALITY ============================
    public boolean deleteSelectedStaff() {
        StaffModel selectedStaff = getSelectedStaff();
        if (selectedStaff == null) {
            showErrorMessage("Vui lòng chọn nhân viên cần xóa!");
            return false;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhân viên " + selectedStaff.getTenNV() + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.deleteStaff(selectedStaff.getMaNV())) {
                showSuccessMessage("Xóa nhân viên thành công!");
                loadStaffData();
                return true;
            } else {
                showErrorMessage("Xóa nhân viên thất bại!");
                return false;
            }
        }
        return false;
    }

    // ============================ UTILITY METHODS ============================
    public String getSearchKeyword() {
        return jTextField_search.getText().trim();
    }

    public String getSearchType() {
        return jComboBox_searchType.getSelectedItem().toString();
    }

    public StaffModel getSelectedStaff() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }

        String maNV = staffTable.getValueAt(selectedRow, 0).toString();
        return controller.getStaffById(maNV);
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Lỗi nhập liệu",
                JOptionPane.WARNING_MESSAGE);
    }

    // ============================ PUBLIC METHODS FOR EXTERNAL USE ============================
    public void addAddStaffListener(ActionListener listener) {
        jButton_add.addActionListener(listener);
    }

    public void addEditStaffListener(ActionListener listener) {
        jButton_edit.addActionListener(listener);
    }

    public void addDeleteStaffListener(ActionListener listener) {
        jButton_delete.addActionListener(listener);
    }

    public void addSearchListener(ActionListener listener) {
        jButton_search.addActionListener(listener);
    }

    public JTable getStaffTable() {
        return staffTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public StaffController getController() {
        return controller;
    }

    // ============================ TEST METHOD ============================
    public void testSearchFunctionality() {
        System.out.println("=== KIỂM TRA CHỨC NĂNG TÌM KIẾM ===");
        testSearchMethods();
    }
}

