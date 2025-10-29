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
import java.util.List;

public class StaffPanel extends JPanel {
    private StaffController controller;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;
    private JComboBox<String> searchTypeComboBox;
    private JButton btnEdit;
    private JButton btnReload;
    private JButton btnSearch;
    private JButton btnClearSearch;

    // Constructor với controller
    public StaffPanel(StaffController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("StaffController cannot be null");
        }
    }
    public StaffPanel() {
        this.controller = new StaffController();
        this.initComponents();
        loadDataFromDatabase();

    }


    public void initComponents() {
        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        add(createSectionTitle(), BorderLayout.NORTH);
        add(createToolbarPanel(), BorderLayout.PAGE_START);
        add(createTablePanel(), BorderLayout.CENTER);
    }

    // ============================ TITLE SECTION ============================
    private JLabel createSectionTitle() {
        JLabel titleLabel = new JLabel("Quản Lý Nhân Viên");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        return titleLabel;
    }

    // ============================ TOOLBAR SECTION ============================
    private JPanel createToolbarPanel() {
        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        toolbarPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Tạo các nút chức năng
        JButton btnAdd = createToolbarButton("➕ Thêm Nhân Viên");
        btnEdit = createToolbarButton("📝 Sửa");
        JButton btnDelete = createToolbarButton("🗑️ Xóa");
        btnReload = createToolbarButton("🔄 Tải lại");

        // Tạo ô tìm kiếm và combobox loại tìm kiếm
        JLabel searchLabel = new JLabel("🔍 Tìm kiếm:");

        // Combobox chọn loại tìm kiếm
        String[] searchTypes = {"Tất cả", "Mã nhân viên", "Tên nhân viên", "Số điện thoại"};
        searchTypeComboBox = new JComboBox<>(searchTypes);
        searchTypeComboBox.setPreferredSize(new Dimension(120, 30));
        searchTypeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Ô nhập từ khóa tìm kiếm
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));

        // Nút tìm kiếm
        btnSearch = createToolbarButton("🔎 Tìm");
        btnClearSearch = createToolbarButton("❌ Xóa tìm kiếm");

        // Thêm components vào toolbar
        toolbarPanel.add(btnAdd);
        toolbarPanel.add(btnEdit);
        toolbarPanel.add(btnDelete);
        toolbarPanel.add(btnReload);
        toolbarPanel.add(Box.createHorizontalStrut(20)); // Khoảng cách
        toolbarPanel.add(searchLabel);
        toolbarPanel.add(searchTypeComboBox);
        toolbarPanel.add(searchField);
        toolbarPanel.add(btnSearch);
        toolbarPanel.add(btnClearSearch);

        // Thêm sự kiện cho các nút
        setupEventListeners();

        return toolbarPanel;
    }

    private void setupEventListeners() {
        // Thêm sự kiện cho nút Sửa
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEditDialog();
            }
        });

        // Thêm sự kiện cho nút Tải lại
        btnReload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });

        // Thêm sự kiện cho nút Tìm kiếm
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        // Thêm sự kiện cho nút Xóa tìm kiếm
        btnClearSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSearch();
            }
        });

        // Thêm sự kiện Enter cho ô tìm kiếm
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
    }

    private JButton createToolbarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // ============================ SEARCH METHODS ============================
    private void performSearch() {
        String keyword = searchField.getText().trim();
        String searchType = (String) searchTypeComboBox.getSelectedItem();

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập từ khóa tìm kiếm",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
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
                showSearchResultMessage(searchResults.size(), keyword, searchType);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy kết quả nào",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tìm kiếm: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void displaySearchResults(List<StaffModel> staffList) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        for (StaffModel staff : staffList) {
            Object[] rowData = {
                    staff.getMaNV(),
                    staff.getTenNV(),
                    formatCurrency(staff.getLuongNV()),
                    staff.getSdtNV(),
                    getChucVuText(staff.getChucVu()),
                    staff.getTenDangNhap(),
                    "✏️ Sửa"
            };
            tableModel.addRow(rowData);
        }
    }

    private void showSearchResultMessage(int resultCount, String keyword, String searchType) {
        String message = String.format("Tìm thấy %d kết quả cho '%s' trong %s",
                resultCount, keyword, searchType.toLowerCase());
        System.out.println(message);
    }

    private void clearSearch() {
        searchField.setText("");
        searchTypeComboBox.setSelectedIndex(0);
        refreshData();
    }

    // ============================ TABLE SECTION ============================
    private JScrollPane createTablePanel() {
        // Tạo model cho bảng
        String[] columns = {"Mã NV", "Tên NV", "Lương", "Số điện thoại", "Chức vụ", "Tên đăng nhập", "Thao tác"};
        tableModel = createTableModel(columns);

        // Tạo bảng
        table = createTable(tableModel);

        // Tạo scroll pane cho bảng
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        return scrollPane;
    }

    private DefaultTableModel createTableModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Chỉ cho phép chỉnh sửa cột "Thao tác"
                return column == 6;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Xác định kiểu dữ liệu cho từng cột
                if (columnIndex == 0) return Integer.class; // Mã NV là số
                return String.class;
            }
        };
    }

    // Phương thức tải dữ liệu từ database
    private void loadDataFromDatabase() {
        try {
            System.out.println("🔄 Đang tải dữ liệu nhân viên từ database...");
            List<StaffModel> staffList = controller.getStaffList();

            tableModel.setRowCount(0); // Xóa dữ liệu cũ

            if (staffList != null && !staffList.isEmpty()) {
                for (StaffModel staff : staffList) {
                    Object[] rowData = {
                            staff.getMaNV(),
                            staff.getTenNV(),
                            formatCurrency(staff.getLuongNV()),
                            staff.getSdtNV(),
                            getChucVuText(staff.getChucVu()),
                            staff.getTenDangNhap(),
                            "✏️ Sửa"
                    };
                    tableModel.addRow(rowData);
                }
                System.out.println("✅ Đã tải " + staffList.size() + " nhân viên từ database");
            } else {
                System.out.println("ℹ️ Không có nhân viên nào trong database");
                // Không hiển thị thông báo popup để tránh làm phiền người dùng
            }

        } catch (Exception e) {
            System.out.println("❌ Lỗi khi tải dữ liệu từ database: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu từ database: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);

        // Thiết lập thuộc tính cho bảng
        table.setRowHeight(35);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setForeground(Color.BLACK);

        // Thiết lập độ rộng cột
        table.getColumnModel().getColumn(0).setPreferredWidth(80);  // Mã NV
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Tên NV
        table.getColumnModel().getColumn(2).setPreferredWidth(120); // Lương
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Số điện thoại
        table.getColumnModel().getColumn(4).setPreferredWidth(80);  // Chức vụ
        table.getColumnModel().getColumn(5).setPreferredWidth(100); // Tên đăng nhập
        table.getColumnModel().getColumn(6).setPreferredWidth(70);  // Thao tác

        // Căn giữa nội dung một số cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Mã NV
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Chức vụ
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); // Thao tác

        // Thêm sự kiện cho cột "Thao tác"
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());

                if (row >= 0 && col == 6) { // Cột "Thao tác"
                    openEditDialogForRow(row);
                }
            }
        });

        return table;
    }

    // ============================ EDIT DIALOG METHODS ============================
    private void openEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn nhân viên cần sửa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        openEditDialogForRow(selectedRow);
    }

    private void openEditDialogForRow(int row) {
        // Lấy mã nhân viên từ hàng được chọn
        int maNV = (int) tableModel.getValueAt(row, 0);

        // Tạo JDialog
        JDialog editDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                "Sửa Thông Tin Nhân Viên",
                true);
        editDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        editDialog.setSize(600, 500);
        editDialog.setLocationRelativeTo(this);

        // Sử dụng StaffEditDialog
        try {
            StaffEditDialog staffEditDialog = new StaffEditDialog(editDialog, controller, maNV);
            editDialog.setContentPane(staffEditDialog);

            // Hiển thị dialog
            editDialog.setVisible(true);

            // Sau khi dialog đóng, refresh dữ liệu
            refreshData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi mở form sửa: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // ============================ PUBLIC METHODS ============================
    public void refreshData() {
        loadDataFromDatabase();
    }

    public JTable getTable() {
        return table;
    }

    public JButton getBtnSua() {
        return btnEdit;
    }

    // ============================ UTILITY METHODS ============================

    // Phương thức định dạng tiền tệ
    private String formatCurrency(long amount) {
        return String.format("₫ %,d", amount);
    }

    // Phương thức chuyển đổi chức vụ từ số sang text - ĐÃ SỬA
    private String getChucVuText(int chucVu) {
        return chucVu == 1 ? "Quản trị viên" : "Nhân viên"; // 1 = Quản lý, 0 = Nhân viên
    }

    // Phương thức kiểm tra trạng thái
    public boolean isReady() {
        return controller != null && controller.isReady();
    }
}