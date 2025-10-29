

package view;

import controller.CarManageController;
import model.CarManageModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductPanel extends JPanel {
    private CarManageController controller;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;
    private JComboBox<String> searchTypeComboBox;

    public ProductPanel() {
        this.controller = new CarManageController();
        initComponents();
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
        JLabel titleLabel = new JLabel("Quản Lý Sản Phẩm");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        return titleLabel;
    }

    // ============================ TOOLBAR SECTION ============================
    private JPanel createToolbarPanel() {
        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        toolbarPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Tạo các nút chức năng
        JButton btnAdd = createToolbarButton("➕ Thêm Sản Phẩm");
        JButton btnEdit = createToolbarButton("📝 Sửa");
        JButton btnDelete = createToolbarButton("🗑️ Xóa");
        JButton btnReload = createToolbarButton("🔄 Tải lại");

        // Tạo ô tìm kiếm và combobox loại tìm kiếm
        JLabel searchLabel = new JLabel("🔍 Tìm kiếm:");

        // Combobox chọn loại tìm kiếm
        String[] searchTypes = {"Tất cả", "Mã ô tô", "Tên ô tô", "Loại ô tô"};
        searchTypeComboBox = new JComboBox<>(searchTypes);
        searchTypeComboBox.setPreferredSize(new Dimension(120, 30));
        searchTypeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));

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
        toolbarPanel.add(Box.createHorizontalStrut(20)); // Khoảng cách
        toolbarPanel.add(searchLabel);
        toolbarPanel.add(searchTypeComboBox);
        toolbarPanel.add(searchField);
        toolbarPanel.add(btnSearch);
        toolbarPanel.add(btnClearSearch);

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

        return toolbarPanel;
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
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<CarManageModel> searchResults = null;

            switch (searchType) {
                case "Tất cả":
                    searchResults = controller.searchAllFields(keyword);
                    break;
                case "Mã ô tô":
                    searchResults = controller.searchByMaOto(keyword);
                    break;
                case "Tên ô tô":
                    searchResults = controller.searchByTenOto(keyword);
                    break;
                case "Loại ô tô":
                    searchResults = controller.searchByLoaiOto(keyword);
                    break;
            }

            if (searchResults != null) {
                displaySearchResults(searchResults);
                showSearchResultMessage(searchResults.size(), keyword, searchType);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void displaySearchResults(List<CarManageModel> carList) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        for (CarManageModel car : carList) {
            Object[] rowData = {
                    car.getMaOto(),
                    car.getTenOto(),
                    formatCurrency(car.getGia()),
                    car.getLoaiOto(),
                    String.valueOf(car.getSoLuong()),
                    car.getMoTa(),
                    car.getMaHang(),
                    "✏️ Sửa"
            };
            tableModel.addRow(rowData);
        }
    }

    private void showSearchResultMessage(int resultCount, String keyword, String searchType) {
        String message;
        if (resultCount == 0) {
            message = String.format("Không tìm thấy kết quả nào cho '%s' trong %s", keyword, searchType.toLowerCase());
            JOptionPane.showMessageDialog(this, message, "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
        } else {
            message = String.format("Tìm thấy %d kết quả cho '%s' trong %s", resultCount, keyword, searchType.toLowerCase());
            // Có thể hiển thị thông báo hoặc không, tùy theo thiết kế
            System.out.println(message);
        }
    }

    private void clearSearch() {
        searchField.setText("");
        searchTypeComboBox.setSelectedIndex(0);
        refreshData();
        JOptionPane.showMessageDialog(this, "Đã xóa tìm kiếm và hiển thị tất cả dữ liệu", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    // ============================ TABLE SECTION ============================
    private JScrollPane createTablePanel() {
        // Tạo model cho bảng
        String[] columns = {"Mã OTO", "Tên OTO", "Giá", "Loại OTO", "Số lượng", "Mô tả", "Mã hãng", "Thao tác"};
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
                return String.class;
            }
        };
    }

    // Phương thức tải dữ liệu từ database
    private void loadDataFromDatabase() {
        try {
            List<CarManageModel> carList = controller.getCarList();
            tableModel.setRowCount(0); // Xóa dữ liệu cũ

            for (CarManageModel car : carList) {
                Object[] rowData = {
                        car.getMaOto(),
                        car.getTenOto(),
                        formatCurrency(car.getGia()),
                        car.getLoaiOto(),
                        String.valueOf(car.getSoLuong()),
                        car.getMoTa(),
                        car.getMaHang(),
                        "✏️ Sửa"
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        table.getColumnModel().getColumn(0).setPreferredWidth(80);  // Mã OTO
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Tên OTO
        table.getColumnModel().getColumn(2).setPreferredWidth(120); // Giá
        table.getColumnModel().getColumn(3).setPreferredWidth(80);  // Loại OTO
        table.getColumnModel().getColumn(4).setPreferredWidth(70);  // Số lượng
        table.getColumnModel().getColumn(5).setPreferredWidth(200); // Mô tả
        table.getColumnModel().getColumn(6).setPreferredWidth(80);  // Mã hãng
        table.getColumnModel().getColumn(7).setPreferredWidth(70);  // Thao tác

        // Căn giữa nội dung một số cột
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Mã OTO
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Số lượng
        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer); // Thao tác

        return table;
    }

    // ============================ EDIT DIALOG METHOD ============================
    private void openEditDialog() {
        // Tạo JDialog thay vì JFrame
        JDialog editDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa Thông Tin Ô Tô", true);
        editDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        editDialog.setSize(1000, 600);
        editDialog.setLocationRelativeTo(this);

        // Sử dụng CarManageDialog thay vì CarManageView
        CarManageDialog carManageDialog = new CarManageDialog(editDialog);
        editDialog.setContentPane(carManageDialog);

        // Hiển thị dialog
        editDialog.setVisible(true);

        // Sau khi dialog đóng, refresh dữ liệu
        refreshData();
    }

    // ============================ PUBLIC METHODS ============================
    public void refreshData() {
        try {
            loadDataFromDatabase();
            System.out.println("Làm mới dữ liệu sản phẩm thành công!");
        } catch (Exception e) {
            System.out.println("Lỗi khi làm mới dữ liệu: " + e.getMessage());
        }
    }

    public JTable getProductTable() {
        return table;
    }

    // Phương thức định dạng tiền tệ
    private String formatCurrency(double amount) {
        return String.format("₫ %,d", (int) amount);
    }
}