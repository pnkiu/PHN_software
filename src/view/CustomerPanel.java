package view;

import controller.CustomerController;
import model.CustomerModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CustomerPanel extends JPanel {
    private CustomerController controller;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;
    private JComboBox<String> searchTypeComboBox;
    private JButton btnEdit;
    private JButton btnReload;
    private JButton btnSearch;
    private JButton btnClearSearch;

    public CustomerPanel() throws SQLException {
        this.controller = new CustomerController();
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
        JLabel titleLabel = new JLabel("Quản Lý Khách Hàng");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        return titleLabel;
    }

    // ============================ TOOLBAR SECTION ============================
    private JPanel createToolbarPanel() {
        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        toolbarPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Tạo các nút chức năng
        JButton btnAdd = createToolbarButton("➕ Thêm Khách Hàng");
        btnEdit = createToolbarButton("📝 Sửa");
        JButton btnDelete = createToolbarButton("🗑️ Xóa");
        btnReload = createToolbarButton("🔄 Tải lại");

        // Tạo ô tìm kiếm và combobox loại tìm kiếm
        JLabel searchLabel = new JLabel("🔍 Tìm kiếm:");

        // Combobox chọn loại tìm kiếm
        String[] searchTypes = {"Tất cả", "Mã KH", "Tên KH", "Số điện thoại", "Địa chỉ"};
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
        setupEventListeners(btnAdd, btnDelete);

        return toolbarPanel;
    }

    private void setupEventListeners(JButton btnAdd, JButton btnDelete) {
        // Sự kiện nút Thêm
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddDialog();
            }
        });

        // Sự kiện nút Sửa
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEditDialog();
            }
        });

        // Sự kiện nút Xóa
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedCustomer();
            }
        });

        // Sự kiện nút Tải lại
        btnReload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });

        // Sự kiện nút Tìm kiếm
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        // Sự kiện nút Xóa tìm kiếm
        btnClearSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSearch();
            }
        });

        // Sự kiện Enter cho ô tìm kiếm
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

    // ============================ DIALOG METHODS ============================
    private void openAddDialog() {
        CustomerDialog.showAddDialog(this, controller, new CustomerDialog.OnCustomerSavedListener() {
            @Override
            public void onCustomerSaved() {
                refreshData();
                JOptionPane.showMessageDialog(CustomerPanel.this,
                        "Thêm khách hàng thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void openEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn khách hàng cần sửa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        openEditDialogForRow(selectedRow);
    }

    private void openEditDialogForRow(int row) {
        // Lấy mã khách hàng từ hàng được chọn
        int maKH = (int) tableModel.getValueAt(row, 0);
        CustomerModel customer = controller.getCustomerByMaKH(maKH);

        if (customer != null) {
            CustomerDialog.showEditDialog(this, controller, customer, new CustomerDialog.OnCustomerSavedListener() {
                @Override
                public void onCustomerSaved() {
                    refreshData();
                    JOptionPane.showMessageDialog(CustomerPanel.this,
                            "Cập nhật khách hàng thành công!",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
        } else {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy thông tin khách hàng",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn khách hàng cần xóa",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maKH = (int) tableModel.getValueAt(selectedRow, 0);
        String tenKH = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa khách hàng: " + tenKH + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = controller.deleteCustomer(String.valueOf(maKH));
                if (success) {
                    JOptionPane.showMessageDialog(this,
                            "Xóa khách hàng thành công!",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    refreshData();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Có lỗi xảy ra khi xóa khách hàng",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa khách hàng: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
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
            List<CustomerModel> searchResults = null;

            switch (searchType) {
                case "Tất cả":
                    searchResults = controller.searchAllFields(keyword);
                    break;
                case "Mã KH":
                    searchResults = controller.searchByMaKH(keyword);
                    break;
                case "Tên KH":
                    searchResults = controller.searchByTenKH(keyword);
                    break;
                case "Số điện thoại":
                    searchResults = controller.searchBySDT(keyword);
                    break;
                case "Địa chỉ":
                    searchResults = controller.searchByDiaChi(keyword);
                    break;
            }

            if (searchResults != null && !searchResults.isEmpty()) {
                displaySearchResults(searchResults);
                showSearchResultMessage(searchResults.size(), keyword, searchType);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy kết quả nào cho từ khóa: '" + keyword + "'",
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

    private void displaySearchResults(List<CustomerModel> customerList) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        for (CustomerModel customer : customerList) {
            Object[] rowData = {
                    customer.getMaKH(),
                    customer.getTenKH(),
                    customer.getDckH(),
                    customer.getSdtKH(),
                    formatCurrency(customer.getTongChiTieu()),
                    customer.getSoLanMua()
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
        // Tạo model cho bảng - ĐÃ BỎ CỘT THAO TÁC
        String[] columns = {"Mã KH", "Tên Khách Hàng", "Địa Chỉ", "Số Điện Thoại", "Tổng Chi Tiêu", "Số Lần Mua"};
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
                // Không cho phép chỉnh sửa trực tiếp trên bảng
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Xác định kiểu dữ liệu cho từng cột
                switch (columnIndex) {
                    case 0: return Integer.class; // Mã KH
                    case 4: return String.class;  // Tổng Chi Tiêu (đã format)
                    case 5: return Integer.class; // Số Lần Mua
                    default: return String.class;
                }
            }
        };
    }

    // Phương thức tải dữ liệu từ database
    private void loadDataFromDatabase() {
        try {
            System.out.println("🔄 Đang tải dữ liệu khách hàng từ database...");
            List<CustomerModel> customerList = controller.getCustomerList();

            tableModel.setRowCount(0); // Xóa dữ liệu cũ

            if (customerList != null && !customerList.isEmpty()) {
                for (CustomerModel customer : customerList) {
                    Object[] rowData = {
                            customer.getMaKH(),
                            customer.getTenKH(),
                            customer.getDckH(),
                            customer.getSdtKH(),
                            formatCurrency(customer.getTongChiTieu()),
                            customer.getSoLanMua()
                    };
                    tableModel.addRow(rowData);
                }
                System.out.println("✅ Đã tải " + customerList.size() + " khách hàng từ database");
            } else {
                System.out.println("ℹ️ Không có khách hàng nào trong database");
                // Thêm hàng thông báo nếu không có dữ liệu
                tableModel.addRow(new Object[]{"", "Không có dữ liệu", "", "", "", ""});
            }

        } catch (Exception e) {
            System.out.println("❌ Lỗi khi tải dữ liệu từ database: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu từ database: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

            // Thêm hàng thông báo lỗi
            tableModel.addRow(new Object[]{"", "Lỗi khi tải dữ liệu", "", "", "", ""});
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
        table.setSelectionBackground(new Color(220, 240, 255));
        table.setSelectionForeground(Color.BLACK);

        // Thiết lập độ rộng cột
        table.getColumnModel().getColumn(0).setPreferredWidth(80);  // Mã KH
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Tên KH
        table.getColumnModel().getColumn(2).setPreferredWidth(250); // Địa chỉ
        table.getColumnModel().getColumn(3).setPreferredWidth(120); // Số điện thoại
        table.getColumnModel().getColumn(4).setPreferredWidth(120); // Tổng Chi Tiêu
        table.getColumnModel().getColumn(5).setPreferredWidth(100); // Số Lần Mua

        // Căn giữa nội dung một số cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Mã KH
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Tổng Chi Tiêu
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Số Lần Mua

        // Căn trái cho cột tên và địa chỉ
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        table.getColumnModel().getColumn(1).setCellRenderer(leftRenderer); // Tên KH
        table.getColumnModel().getColumn(2).setCellRenderer(leftRenderer); // Địa chỉ
        table.getColumnModel().getColumn(3).setCellRenderer(leftRenderer); // Số điện thoại

        // Double click để sửa trên bất kỳ cột nào
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = table.rowAtPoint(evt.getPoint());
                    if (row >= 0) {
                        openEditDialogForRow(row);
                    }
                }
            }
        });

        return table;
    }

    // ============================ UTILITY METHODS ============================
    private String formatCurrency(long amount) {
        return String.format("%,d VND", amount);
    }

    // ============================ PUBLIC METHODS ============================
    public void refreshData() {
        loadDataFromDatabase();
    }

    public JTable getTable() {
        return table;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public boolean isReady() {
        return controller != null && controller.isReady();
    }

    // Phương thức lấy controller (nếu cần)
    public CustomerController getController() {
        return controller;
    }

    // Phương thức hiển thị thông báo
    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}