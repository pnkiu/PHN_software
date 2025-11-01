package view;

import controller.StaffController;
import model.StaffModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StaffPanel extends JPanel {
    private StaffController controller;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;
    private JComboBox<String> searchTypeComboBox;

    public StaffPanel() {
        this.controller = new StaffController();
        initComponents();
        loadDataFromDatabase();
    }

    private void initComponents() {
        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        add(createSectionTitle(), BorderLayout.NORTH);
        add(createToolbarPanel(), BorderLayout.PAGE_START);
        add(createTablePanel(), BorderLayout.CENTER);
    }

    private JLabel createSectionTitle() {
        JLabel titleLabel = new JLabel("Quản Lý Nhân Viên");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        return titleLabel;
    }

    private JPanel createToolbarPanel() {
        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        toolbarPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Tạo các nút chức năng
        JButton btnAdd = createToolbarButton("➕ Thêm Nhân Viên");
        JButton btnEdit = createToolbarButton("📝 Sửa");
        JButton btnDelete = createToolbarButton("🗑️ Xóa");
        JButton btnReload = createToolbarButton("🔄 Tải lại");

        // Tạo ô tìm kiếm và combobox loại tìm kiếm
        JLabel searchLabel = new JLabel("🔍 Tìm kiếm:");

        // Combobox chọn loại tìm kiếm
        String[] searchTypes = {"Tất cả", "Mã nhân viên", "Tên nhân viên", "Số điện thoại"};
        searchTypeComboBox = new JComboBox<>(searchTypes);
        searchTypeComboBox.setPreferredSize(new Dimension(120, 30));

        // Ô nhập từ khóa tìm kiếm
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));

        // Nút tìm kiếm
        JButton btnSearch = createToolbarButton("🔎 Tìm");
        JButton btnClearSearch = createToolbarButton("❌ Xóa tìm kiếm");

        // Thêm components vào toolbar
        toolbarPanel.add(btnAdd);
        toolbarPanel.add(btnEdit);
        toolbarPanel.add(btnDelete);
        toolbarPanel.add(btnReload);
        toolbarPanel.add(Box.createHorizontalStrut(20));
        toolbarPanel.add(searchLabel);
        toolbarPanel.add(searchTypeComboBox);
        toolbarPanel.add(searchField);
        toolbarPanel.add(btnSearch);
        toolbarPanel.add(btnClearSearch);

        // Thêm sự kiện
        btnAdd.addActionListener(e -> openAddDialog());
        btnEdit.addActionListener(e -> openEditDialog());
        btnDelete.addActionListener(e -> deleteSelectedStaff());
        btnReload.addActionListener(e -> refreshData());
        btnSearch.addActionListener(e -> performSearch());
        btnClearSearch.addActionListener(e -> clearSearch());
        searchField.addActionListener(e -> performSearch());

        return toolbarPanel;
    }

    private JButton createToolbarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void openAddDialog() {
        // Implementation for add dialog
        try {
            String nextMaNV = controller.getNextStaffId();
            StaffModel newStaff = controller.createNewStaff();

            // Hiển thị form thêm nhân viên
            showStaffForm(newStaff, "Thêm Nhân Viên Mới", true);
        } catch (Exception e) {
            showError("Lỗi khi mở form thêm: " + e.getMessage());
        }
    }

    private void openEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showWarning("Vui lòng chọn nhân viên cần sửa");
            return;
        }

        String maNV = (String) tableModel.getValueAt(selectedRow, 0);
        StaffModel staff = controller.getStaffById(maNV);

        if (staff != null) {
            showStaffForm(staff, "Sửa Thông Tin Nhân Viên", false);
        } else {
            showError("Không tìm thấy thông tin nhân viên");
        }
    }

    private void showStaffForm(StaffModel staff, String title, boolean isNew) {
        // Tạo dialog form đơn giản
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Mã NV
        panel.add(new JLabel("Mã NV:"));
        JTextField txtMaNV = new JTextField(staff.getMaNV());
        txtMaNV.setEditable(false);
        panel.add(txtMaNV);

        // Tên NV
        panel.add(new JLabel("Tên NV:"));
        JTextField txtTenNV = new JTextField(staff.getTenNV());
        panel.add(txtTenNV);

        // Lương
        panel.add(new JLabel("Lương:"));
        JTextField txtLuong = new JTextField(staff.getLuongNV());
        panel.add(txtLuong);

        // Số điện thoại
        panel.add(new JLabel("Số điện thoại:"));
        JTextField txtSDT = new JTextField(staff.getSdtNV());
        panel.add(txtSDT);

        // Chức vụ
        panel.add(new JLabel("Chức vụ:"));
        JComboBox<String> cboChucVu = new JComboBox<>(new String[]{"Nhân viên", "Quản lý"});
        cboChucVu.setSelectedIndex(staff.getChucVu());
        panel.add(cboChucVu);

        // Tên đăng nhập
        panel.add(new JLabel("Tên đăng nhập:"));
        JTextField txtTenDangNhap = new JTextField(staff.getTenDangNhap());
        panel.add(txtTenDangNhap);

        // Mật khẩu
        panel.add(new JLabel("Mật khẩu:"));
        JTextField txtMatKhau = new JTextField(staff.getMatKhau());
        panel.add(txtMatKhau);

        // Nút lưu/hủy
        JButton btnSave = new JButton("💾 Lưu");
        JButton btnCancel = new JButton("❌ Hủy");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            try {
                staff.setTenNV(txtTenNV.getText().trim());
                staff.setLuongNV(txtLuong.getText().trim());
                staff.setSdtNV(txtSDT.getText().trim());
                staff.setChucVu(cboChucVu.getSelectedIndex());
                staff.setTenDangNhap(txtTenDangNhap.getText().trim());
                staff.setMatKhau(txtMatKhau.getText().trim());

                boolean success;
                if (isNew) {
                    success = controller.addStaff(staff);
                } else {
                    success = controller.updateStaff(staff);
                }

                if (success) {
                    showInfo((isNew ? "Thêm" : "Cập nhật") + " nhân viên thành công!");
                    dialog.dispose();
                    refreshData();
                } else {
                    showError((isNew ? "Thêm" : "Cập nhật") + " nhân viên thất bại!");
                }
            } catch (Exception ex) {
                showError("Lỗi: " + ex.getMessage());
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void deleteSelectedStaff() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showWarning("Vui lòng chọn nhân viên cần xóa");
            return;
        }

        String maNV = (String) tableModel.getValueAt(selectedRow, 0);
        String tenNV = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhân viên:\n" +
                        "Mã: " + maNV + " - Tên: " + tenNV + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = controller.deleteStaff(maNV);
                if (success) {
                    showInfo("Xóa nhân viên thành công!");
                    refreshData();
                } else {
                    showError("Xóa nhân viên thất bại!");
                }
            } catch (Exception e) {
                showError("Lỗi khi xóa nhân viên: " + e.getMessage());
            }
        }
    }

    private void performSearch() {
        String keyword = searchField.getText().trim();
        String searchType = (String) searchTypeComboBox.getSelectedItem();

        if (keyword.isEmpty()) {
            showWarning("Vui lòng nhập từ khóa tìm kiếm");
            return;
        }

        try {
            List<StaffModel> searchResults = null;

            switch (searchType) {
                case "Tất cả":
                    searchResults = controller.searchAllFields(keyword);
                    break;
                case "Mã nhân viên":
                    searchResults = controller.searchByMaNV(keyword);
                    break;
                case "Tên nhân viên":
                    searchResults = controller.searchByTenNV(keyword);
                    break;
                case "Số điện thoại":
                    searchResults = controller.searchBySDT(keyword);
                    break;
            }

            if (searchResults != null && !searchResults.isEmpty()) {
                displaySearchResults(searchResults);
                showInfo("Tìm thấy " + searchResults.size() + " kết quả");
            } else {
                showInfo("Không tìm thấy kết quả nào");
            }

        } catch (Exception e) {
            showError("Lỗi khi tìm kiếm: " + e.getMessage());
        }
    }

    private void displaySearchResults(List<StaffModel> staffList) {
        tableModel.setRowCount(0);
        for (StaffModel staff : staffList) {
            Object[] rowData = {
                    staff.getMaNV(),
                    staff.getTenNV(),
                    formatCurrency(staff.getLuongNV()),
                    staff.getSdtNV(),
                    getChucVuText(staff.getChucVu()),
                    staff.getTenDangNhap(),
                    "***" // Ẩn mật khẩu
            };
            tableModel.addRow(rowData);
        }
    }

    private void clearSearch() {
        searchField.setText("");
        searchTypeComboBox.setSelectedIndex(0);
        refreshData();
    }

    private JScrollPane createTablePanel() {
        String[] columns = {"Mã NV", "Tên NV", "Lương", "Số điện thoại", "Chức vụ", "Tên đăng nhập", "Mật khẩu"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(80);

        return new JScrollPane(table);
    }

    private void loadDataFromDatabase() {
        try {
            List<StaffModel> staffList = controller.getStaffList();
            tableModel.setRowCount(0);

            if (staffList != null && !staffList.isEmpty()) {
                for (StaffModel staff : staffList) {
                    Object[] rowData = {
                            staff.getMaNV(),
                            staff.getTenNV(),
                            formatCurrency(staff.getLuongNV()),
                            staff.getSdtNV(),
                            getChucVuText(staff.getChucVu()),
                            staff.getTenDangNhap(),
                            "***" // Ẩn mật khẩu
                    };
                    tableModel.addRow(rowData);
                }
                System.out.println("✅ Đã tải " + staffList.size() + " nhân viên");
            }
        } catch (Exception e) {
            showError("Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    public void refreshData() {
        loadDataFromDatabase();
    }

    // Utility methods
    private String formatCurrency(String amount) {
        try {
            long value = Long.parseLong(amount);
            return String.format("%,d VND", value);
        } catch (NumberFormatException e) {
            return amount;
        }
    }

    private String getChucVuText(int chucVu) {
        return chucVu == 0 ? "Quản lý" : "Nhân viên";
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}