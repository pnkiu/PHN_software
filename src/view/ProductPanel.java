//
//
//package view;
//
//import controller.CarManageController;
//import model.ProductModel;
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.List;
//
//public class ProductPanel extends JPanel {
//    private CarManageController controller;
//    private DefaultTableModel tableModel;
//    private JTable table;
//    private JTextField searchField;
//    private JComboBox<String> searchTypeComboBox;
//
//    public ProductPanel() {
//        this.controller = new CarManageController();
//        initComponents();
//        loadDataFromDatabase();
//    }
//
//    public void initComponents() {
//        setLayout(new BorderLayout(8, 8));
//        setBorder(new EmptyBorder(10, 10, 10, 10));
//
//        add(createSectionTitle(), BorderLayout.NORTH);
//        add(createToolbarPanel(), BorderLayout.PAGE_START);
//        add(createTablePanel(), BorderLayout.CENTER);
//    }
//
//    // ============================ TITLE SECTION ============================
//    private JLabel createSectionTitle() {
//        JLabel titleLabel = new JLabel("Quản Lý Sản Phẩm");
//        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
//        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
//        return titleLabel;
//    }
//
//    // ============================ TOOLBAR SECTION ============================
//    private JPanel createToolbarPanel() {
//        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
//        toolbarPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
//
//        // Tạo các nút chức năng
//        JButton btnAdd = createToolbarButton("➕ Thêm Sản Phẩm");
//        JButton btnEdit = createToolbarButton("📝 Sửa");
//        JButton btnDelete = createToolbarButton("🗑️ Xóa");
//        JButton btnReload = createToolbarButton("🔄 Tải lại");
//
//        // Tạo ô tìm kiếm và combobox loại tìm kiếm
//        JLabel searchLabel = new JLabel("🔍 Tìm kiếm:");
//
//        // Combobox chọn loại tìm kiếm
//        String[] searchTypes = {"Tất cả", "Mã ô tô", "Tên ô tô", "Loại ô tô"};
//        searchTypeComboBox = new JComboBox<>(searchTypes);
//        searchTypeComboBox.setPreferredSize(new Dimension(120, 30));
//        searchTypeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
//
//        // Ô nhập từ khóa tìm kiếm
//        searchField = new JTextField(20);
//        searchField.setPreferredSize(new Dimension(200, 30));
//
//        // Nút tìm kiếm
//        JButton btnSearch = createToolbarButton("🔎 Tìm");
//        JButton btnClearSearch = createToolbarButton("❌ Xóa tìm kiếm");
//
//        // Thêm components vào toolbar
//        toolbarPanel.add(btnAdd);
//        toolbarPanel.add(btnEdit);
//        toolbarPanel.add(btnDelete);
//        toolbarPanel.add(btnReload);
//        toolbarPanel.add(Box.createHorizontalStrut(20)); // Khoảng cách
//        toolbarPanel.add(searchLabel);
//        toolbarPanel.add(searchTypeComboBox);
//        toolbarPanel.add(searchField);
//        toolbarPanel.add(btnSearch);
//        toolbarPanel.add(btnClearSearch);
//
//        // Thêm sự kiện cho nút Sửa
//        btnEdit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                openEditDialog();
//            }
//        });
//
//        // Thêm sự kiện cho nút Tải lại
//        btnReload.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                refreshData();
//            }
//        });
//
//        // Thêm sự kiện cho nút Tìm kiếm
//        btnSearch.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                performSearch();
//            }
//        });
//
//        // Thêm sự kiện cho nút Xóa tìm kiếm
//        btnClearSearch.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                clearSearch();
//            }
//        });
//
//        // Thêm sự kiện Enter cho ô tìm kiếm
//        searchField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                performSearch();
//            }
//        });
//
//        return toolbarPanel;
//    }
//
//    private JButton createToolbarButton(String text) {
//        JButton button = new JButton(text);
//        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
//        button.setFocusPainted(false);
//        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        return button;
//    }
//
//    // ============================ SEARCH METHODS ============================
//    private void performSearch() {
//        String keyword = searchField.getText().trim();
//        String searchType = (String) searchTypeComboBox.getSelectedItem();
//
//        if (keyword.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm", "Thông báo", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        try {
//            List<ProductModel> searchResults = null;
//
//            switch (searchType) {
//                case "Tất cả":
//                    searchResults = controller.searchAllFields(keyword);
//                    break;
//                case "Mã ô tô":
//                    searchResults = controller.searchByMaOto(keyword);
//                    break;
//                case "Tên ô tô":
//                    searchResults = controller.searchByTenOto(keyword);
//                    break;
//                case "Loại ô tô":
//                    searchResults = controller.searchByLoaiOto(keyword);
//                    break;
//            }
//
//            if (searchResults != null) {
//                displaySearchResults(searchResults);
//                showSearchResultMessage(searchResults.size(), keyword, searchType);
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//            e.printStackTrace();
//        }
//    }
//
//    private void displaySearchResults(List<ProductModel> carList) {
//        tableModel.setRowCount(0); // Xóa dữ liệu cũ
//
//        for (ProductModel car : carList) {
//            Object[] rowData = {
//                    car.getMaOto(),
//                    car.getTenOto(),
//                    formatCurrency(car.getGia()),
//                    car.getLoaiOto(),
//                    String.valueOf(car.getSoLuong()),
//                    car.getMoTa(),
//                    car.getMaHang(),
//                    "✏️ Sửa"
//            };
//            tableModel.addRow(rowData);
//        }
//    }
//
//    private void showSearchResultMessage(int resultCount, String keyword, String searchType) {
//        String message;
//        if (resultCount == 0) {
//            message = String.format("Không tìm thấy kết quả nào cho '%s' trong %s", keyword, searchType.toLowerCase());
//            JOptionPane.showMessageDialog(this, message, "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
//        } else {
//            message = String.format("Tìm thấy %d kết quả cho '%s' trong %s", resultCount, keyword, searchType.toLowerCase());
//            // Có thể hiển thị thông báo hoặc không, tùy theo thiết kế
//            System.out.println(message);
//        }
//    }
//
//    private void clearSearch() {
//        searchField.setText("");
//        searchTypeComboBox.setSelectedIndex(0);
//        refreshData();
//        JOptionPane.showMessageDialog(this, "Đã xóa tìm kiếm và hiển thị tất cả dữ liệu", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    // ============================ TABLE SECTION ============================
//    private JScrollPane createTablePanel() {
//        // Tạo model cho bảng
//        String[] columns = {"Mã OTO", "Tên OTO", "Giá", "Loại OTO", "Số lượng", "Mô tả", "Mã hãng", "Thao tác"};
//        tableModel = createTableModel(columns);
//
//        // Tạo bảng
//        table = createTable(tableModel);
//
//        // Tạo scroll pane cho bảng
//        JScrollPane scrollPane = new JScrollPane(table);
//        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
//
//        return scrollPane;
//    }
//
//    private DefaultTableModel createTableModel(String[] columns) {
//        return new DefaultTableModel(columns, 0) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                // Không cho phép chỉnh sửa trực tiếp trên bảng
//                return false;
//            }
//
//            @Override
//            public Class<?> getColumnClass(int columnIndex) {
//                // Xác định kiểu dữ liệu cho từng cột
//                return String.class;
//            }
//        };
//    }
//
//    // Phương thức tải dữ liệu từ database
//    private void loadDataFromDatabase() {
//        try {
//            List<ProductModel> carList = controller.getCarList();
//            tableModel.setRowCount(0); // Xóa dữ liệu cũ
//
//            for (ProductModel car : carList) {
//                Object[] rowData = {
//                        car.getMaOto(),
//                        car.getTenOto(),
//                        formatCurrency(car.getGia()),
//                        car.getLoaiOto(),
//                        String.valueOf(car.getSoLuong()),
//                        car.getMoTa(),
//                        car.getMaHang(),
//                        "✏️ Sửa"
//                };
//                tableModel.addRow(rowData);
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//            e.printStackTrace();
//        }
//    }
//
//    private JTable createTable(DefaultTableModel model) {
//        JTable table = new JTable(model);
//
//        // Thiết lập thuộc tính cho bảng
//        table.setRowHeight(35);
//        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
//        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
//        table.getTableHeader().setBackground(new Color(240, 240, 240));
//        table.getTableHeader().setForeground(Color.BLACK);
//
//        // Thiết lập độ rộng cột
//        table.getColumnModel().getColumn(0).setPreferredWidth(80);  // Mã OTO
//        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Tên OTO
//        table.getColumnModel().getColumn(2).setPreferredWidth(120); // Giá
//        table.getColumnModel().getColumn(3).setPreferredWidth(80);  // Loại OTO
//        table.getColumnModel().getColumn(4).setPreferredWidth(70);  // Số lượng
//        table.getColumnModel().getColumn(5).setPreferredWidth(200); // Mô tả
//        table.getColumnModel().getColumn(6).setPreferredWidth(80);  // Mã hãng
//        table.getColumnModel().getColumn(7).setPreferredWidth(70);  // Thao tác
//
//        // Căn giữa nội dung một số cột
//        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
//        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Mã OTO
//        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Số lượng
//        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer); // Thao tác
//
//        return table;
//    }
//
//    // ============================ EDIT DIALOG METHOD ============================
//    private void openEditDialog() {
//        // Tạo JDialog thay vì JFrame
//        JDialog editDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa Thông Tin Ô Tô", true);
//        editDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//        editDialog.setSize(1000, 600);
//        editDialog.setLocationRelativeTo(this);
//
//        // Sử dụng CarManageDialog thay vì CarManageView
//        CarManageDialog carManageDialog = new CarManageDialog(editDialog);
//        editDialog.setContentPane(carManageDialog);
//
//        // Hiển thị dialog
//        editDialog.setVisible(true);
//
//        // Sau khi dialog đóng, refresh dữ liệu
//        refreshData();
//    }
//
//    // ============================ PUBLIC METHODS ============================
//    public void refreshData() {
//        try {
//            loadDataFromDatabase();
//            System.out.println("Làm mới dữ liệu sản phẩm thành công!");
//        } catch (Exception e) {
//            System.out.println("Lỗi khi làm mới dữ liệu: " + e.getMessage());
//        }
//    }
//
//    public JTable getProductTable() {
//        return table;
//    }
//
//    // Phương thức định dạng tiền tệ
//    private String formatCurrency(double amount) {
//        return String.format("₫ %,d", (int) amount);
//    }
//}

package view;

import controller.CarManageController;
import model.ProductModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ProductPanel extends JPanel {
    private final CarManageController CarManageController;
    private CarManageController controller;

    private DefaultTableModel tableModel;
    private DefaultTableModel dialogTableModel;
    private JTable table;
    private JTable dialogTable;
    private JTextField searchField;
    private JComboBox<String> searchTypeComboBox;

    // Components for dialog
    private JTextField txtMaOto, txtTenOto, txtGia, txtLoaiOto, txtSoLuong, txtMoTa, txtMaHang, txtSoLuotBan;
    private JButton btnSua, btnDong;

    // Add dialog
    private JDialog addDialog;

    public ProductPanel() {
        this.controller = new CarManageController();
        this.CarManageController = new CarManageController();
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
                openCarManageDialog();
            }
        });

        // Thêm sự kiện cho nút Thêm
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formThemsp(CarManageController);
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

    // ============================ ADD PRODUCT DIALOG ============================
    public void formThemsp(CarManageController controller) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        addDialog = new JDialog(parentFrame, "Thêm sản phẩm Ô tô", true);
        addDialog.setSize(400, 550);
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);

        JLabel l1 = new JLabel("Mã Ô tô:");
        l1.setBounds(20, 20, 100, 30);
        formPanel.add(l1);

        JTextField txtMaOto = new JTextField();
        txtMaOto.setBounds(130, 20, 230, 30);
        formPanel.add(txtMaOto);

        JLabel l2 = new JLabel("Tên Ô tô:");
        l2.setBounds(20, 60, 100, 30);
        formPanel.add(l2);

        JTextField txtTenOto = new JTextField();
        txtTenOto.setBounds(130, 60, 230, 30);
        formPanel.add(txtTenOto);

        JLabel l3 = new JLabel("Loại Ô tô:");
        l3.setBounds(20, 100, 100, 30);
        formPanel.add(l3);

        JTextField txtLoaiOto = new JTextField();
        txtLoaiOto.setBounds(130, 100, 230, 30);
        formPanel.add(txtLoaiOto);

        JLabel l4 = new JLabel("Giá:");
        l4.setBounds(20, 140, 100, 30);
        formPanel.add(l4);

        JTextField txtGia = new JTextField();
        txtGia.setBounds(130, 140, 230, 30);
        formPanel.add(txtGia);

        JLabel l5 = new JLabel("Số lượng:");
        l5.setBounds(20, 180, 100, 30);
        formPanel.add(l5);

        JTextField txtSoLuong = new JTextField();
        txtSoLuong.setBounds(130, 180, 230, 30);
        formPanel.add(txtSoLuong);

        JLabel l6 = new JLabel("Mã Hãng:");
        l6.setBounds(20, 220, 100, 30);
        formPanel.add(l6);

        JTextField txtMaHang = new JTextField();
        txtMaHang.setBounds(130, 220, 230, 30);
        formPanel.add(txtMaHang);

        JLabel l7 = new JLabel("Mô tả:");
        l7.setBounds(20, 260, 100, 30);
        formPanel.add(l7);

        JTextArea txtMoTa = new JTextArea();
        txtMoTa.setBounds(130, 260, 230, 150);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        formPanel.add(txtMoTa);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        btnCancel.addActionListener(e -> closeAddDialog());

        btnSave.addActionListener(e -> {
            boolean addCar = controller.addCar(
                    txtMaOto.getText(),
                    txtTenOto.getText(),
                    txtLoaiOto.getText(),
                    txtGia.getText(),
                    txtSoLuong.getText(),
                    txtMaHang.getText(),
                    txtMoTa.getText()
            );
            // Refresh data after adding
            refreshData();
            closeAddDialog();
        });

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);
        addDialog.setVisible(true);
    }

    public void closeAddDialog() {
        if (addDialog != null) addDialog.dispose();
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public ProductModel getSelectedOto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return null; // chưa chọn hàng nào
        }
        String maOto = table.getValueAt(selectedRow, 0).toString();
        String tenOto = table.getValueAt(selectedRow, 1).toString();
        double gia = Double.parseDouble(table.getValueAt(selectedRow, 2).toString().replace("₫", "").replace(",", "").trim());
        String loaiOto = table.getValueAt(selectedRow, 3).toString();
        int soLuong = Integer.parseInt(table.getValueAt(selectedRow, 4).toString());
        String moTa = table.getValueAt(selectedRow, 5).toString();
        String maHang = table.getValueAt(selectedRow, 6).toString();
        int soLuotBan = Integer.parseInt(table.getValueAt(selectedRow, 7).toString());

        return new ProductModel(gia, loaiOto, maOto, moTa, soLuong, tenOto, soLuotBan, maHang);
    }

    // ============================ CAR MANAGE DIALOG ============================
    private void openCarManageDialog() {
        try {
            // Tạo JDialog
            JDialog dialog = new JDialog();
            dialog.setTitle("Quản Lý Ô Tô");
            dialog.setModal(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(1200, 700);
            dialog.setLocationRelativeTo(this);

            // Tạo panel cho dialog (tích hợp code từ CarManageDialog)
            JPanel dialogPanel = createCarManagePanel(dialog);
            dialog.setContentPane(dialogPanel);

            // Hiển thị dialog
            dialog.setVisible(true);

            // Sau khi dialog đóng, refresh dữ liệu
            refreshData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi mở dialog: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // ============================ CAR MANAGE DIALOG COMPONENTS ============================
    private JPanel createCarManagePanel(JDialog parentDialog) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tiêu đề
        JLabel lblTitle = new JLabel("SỬA THÔNG TIN Ô TÔ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 123, 255));
        lblTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(lblTitle, BorderLayout.NORTH);

        // Panel chính chứa cả form và bảng
        JPanel mainContentPanel = new JPanel(new BorderLayout(10, 10));

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
        btnDong = createStyledButton("❌ Đóng", new Color(108, 117, 125));

        buttonPanel.add(btnSua);
        buttonPanel.add(btnDong);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainContentPanel.add(leftPanel, BorderLayout.WEST);

        // Panel phải cho bảng dữ liệu
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Danh sách ô tô - Click đúp để chọn"));

        // Bảng dữ liệu
        String[] columns = {"Mã ô tô", "Tên ô tô", "Giá", "Loại ô tô", "Số lượng", "Mô tả", "Mã hãng", "Số lượt bán"};
        dialogTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dialogTable = new JTable(dialogTableModel);
        dialogTable.setRowHeight(30);
        dialogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(dialogTable);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        mainContentPanel.add(rightPanel, BorderLayout.CENTER);
        panel.add(mainContentPanel, BorderLayout.CENTER);

        // Thêm sự kiện
        addDialogEventListeners(parentDialog);
        loadCarDataFromDatabase();

        return panel;
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

    private void addDialogEventListeners(JDialog parentDialog) {
        btnSua.addActionListener(e -> suaOto());
        btnDong.addActionListener(e -> parentDialog.dispose());

        // Sự kiện click đúp vào bảng để chọn ô tô cần sửa
        dialogTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = dialogTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        hienThiThongTinTuTable(selectedRow);
                    }
                }
            }
        });
    }

    // PHƯƠNG THỨC TẢI DỮ LIỆU TỪ DATABASE CHO DIALOG
    private void loadCarDataFromDatabase() {
        try {
            List<ProductModel> carList = controller.getCarList();
            dialogTableModel.setRowCount(0); // Xóa dữ liệu cũ

            for (ProductModel car : carList) {
                Object[] rowData = {
                        car.getMaOto(),
                        car.getTenOto(),
                        formatCurrency(car.getGia()),
                        car.getLoaiOto(),
                        String.valueOf(car.getSoLuong()),
                        car.getMoTa(),
                        car.getMaHang(),
                        String.valueOf(car.getSoLuotBan())
                };
                dialogTableModel.addRow(rowData);
            }

            System.out.println("Đã tải " + carList.size() + " ô tô từ database");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu từ database: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // PHƯƠNG THỨC SỬA Ô TÔ
    private void suaOto() {
        try {
            if (txtMaOto.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ô tô cần sửa từ bảng!");
                return;
            }

            if (validateDialogInput()) {
                // Tạo đối tượng ProductModel từ dữ liệu form
                ProductModel car = new ProductModel();
                car.setMaOto(txtMaOto.getText().trim());
                car.setTenOto(txtTenOto.getText().trim());
                car.setGia(Double.parseDouble(txtGia.getText().trim()));
                car.setLoaiOto(txtLoaiOto.getText().trim());
                car.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));
                car.setMoTa(txtMoTa.getText().trim());
                car.setMaHang(txtMaHang.getText().trim());
                // soLuotBan không sửa được

                System.out.println("Dữ liệu sẽ cập nhật:");
                System.out.println("Mã: " + car.getMaOto());
                System.out.println("Tên: " + car.getTenOto());
                System.out.println("Giá: " + car.getGia());
                System.out.println("Loại: " + car.getLoaiOto());
                System.out.println("Số lượng: " + car.getSoLuong());
                System.out.println("Mô tả: " + car.getMoTa());
                System.out.println("Mã hãng: " + car.getMaHang());

                // Gọi controller để cập nhật
                boolean success = controller.updateCar(car);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin ô tô thành công!");
                    loadCarDataFromDatabase(); // Reload data từ database
                    lamMoiForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin thất bại!");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void hienThiThongTinTuTable(int row) {
        try {
            String maOTO = dialogTableModel.getValueAt(row, 0).toString();
            System.out.println("Đang tìm ô tô với mã: " + maOTO);

            ProductModel car = controller.getCarByMaOTO(maOTO);

            if (car != null) {
                txtMaOto.setText(car.getMaOto());
                txtTenOto.setText(car.getTenOto());
                txtGia.setText(String.valueOf((int)car.getGia())); // Bỏ định dạng tiền tệ
                txtLoaiOto.setText(car.getLoaiOto());
                txtSoLuong.setText(String.valueOf(car.getSoLuong()));
                txtMoTa.setText(car.getMoTa());
                txtMaHang.setText(car.getMaHang());
                txtSoLuotBan.setText(String.valueOf(car.getSoLuotBan()));

                System.out.println("Đã tải thông tin ô tô: " + car.getTenOto());
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin ô tô với mã: " + maOTO);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải thông tin ô tô: " + e.getMessage());
            e.printStackTrace();
        }
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
        dialogTable.clearSelection();
    }

    private boolean validateDialogInput() {
        if (txtMaOto.getText().trim().isEmpty() ||
                txtTenOto.getText().trim().isEmpty() ||
                txtGia.getText().trim().isEmpty() ||
                txtSoLuong.getText().trim().isEmpty() ||
                txtLoaiOto.getText().trim().isEmpty() ||
                txtMaHang.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ thông tin các trường bắt buộc (*)!");
            return false;
        }

        // Kiểm tra định dạng số
        try {
            Double.parseDouble(txtGia.getText().trim());
            Integer.parseInt(txtSoLuong.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Giá và số lượng phải là số hợp lệ!");
            return false;
        }

        return true;
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
            List<ProductModel> searchResults = null;

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

    private void displaySearchResults(List<ProductModel> carList) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        for (ProductModel car : carList) {
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

        // Thêm sự kiện double-click cho bảng
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    openCarManageDialog();
                }
            }
        });

        // Tạo scroll pane cho bảng
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        return scrollPane;
    }

    private DefaultTableModel createTableModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };
    }

    // Phương thức tải dữ liệu từ database cho main table
    private void loadDataFromDatabase() {
        try {
            List<ProductModel> carList = controller.getCarList();
            tableModel.setRowCount(0); // Xóa dữ liệu cũ

            for (ProductModel car : carList) {
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