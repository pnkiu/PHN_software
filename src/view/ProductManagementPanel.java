package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductManagementPanel extends JPanel {

    public ProductManagementPanel() {
        initComponents();
    }

    private void initComponents() {
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

        // Tạo ô tìm kiếm
        JLabel searchLabel = new JLabel("🔍 Tìm kiếm:");
        JTextField searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));

        // Thêm components vào toolbar
        toolbarPanel.add(btnAdd);
        toolbarPanel.add(btnEdit);
        toolbarPanel.add(btnDelete);
        toolbarPanel.add(btnReload);
        toolbarPanel.add(searchLabel);
        toolbarPanel.add(searchField);

        return toolbarPanel;
    }

    private JButton createToolbarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // ============================ TABLE SECTION ============================
    private JScrollPane createTablePanel() {
        // Tạo model cho bảng
        String[] columns = {"Mã OTO", "Tên OTO", "Giá", "Loại OTO", "Số lượng", "Mô tả", "Mã hãng", "Thao tác"};
        DefaultTableModel tableModel = createTableModel(columns);

        // Thêm dữ liệu mẫu
        addSampleData(tableModel);

        // Tạo bảng
        JTable table = createTable(tableModel);

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

    private void addSampleData(DefaultTableModel model) {
        // Dữ liệu mẫu cho bảng sản phẩm
        Object[][] sampleData = {
                {"OTO001", "Toyota Camry 2024", "₫ 850,000,000", "Sedan", "15", "Xe sedan hạng D, động cơ 2.5L", "TOYOTA", "✏️ Sửa"},
                {"OTO002", "Honda Civic RS", "₫ 720,000,000", "Sedan", "8", "Xe thể thao, phiên bản RS", "HONDA", "✏️ Sửa"},
                {"OTO003", "Ford Ranger Raptor", "₫ 1,250,000,000", "Bán tải", "5", "Xe bán tải thể thao, động cơ 2.0L", "FORD", "✏️ Sửa"},
                {"OTO004", "Hyundai SantaFe", "₫ 980,000,000", "SUV", "12", "SUV 7 chỗ, đầy đủ tiện nghi", "HYUNDAI", "✏️ Sửa"},
                {"OTO005", "Mazda CX-5", "₫ 820,000,000", "SUV", "10", "SUV 5 chỗ, thiết kế trẻ trung", "MAZDA", "✏️ Sửa"},
                {"OTO006", "VinFast VF e34", "₫ 590,000,000", "Sedan", "20", "Xe điện, công nghệ thông minh", "VINFAST", "✏️ Sửa"},
                {"OTO007", "Mercedes C200", "₫ 1,650,000,000", "Sedan", "3", "Xe sang, động cơ 1.5L turbo", "MERCEDES", "✏️ Sửa"},
                {"OTO008", "BMW X3", "₫ 2,100,000,000", "SUV", "4", "SUV hạng sang, động cơ 2.0L", "BMW", "✏️ Sửa"}
        };

        for (Object[] row : sampleData) {
            model.addRow(row);
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
        // Phương thức để làm mới dữ liệu (có thể override sau)
        System.out.println("Làm mới dữ liệu sản phẩm");
    }

    public JTable getProductTable() {
        Component[] components = getComponents();
        for (Component comp : components) {
            if (comp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) comp;
                JViewport viewport = scrollPane.getViewport();
                return (JTable) viewport.getView();
            }
        }
        return null;
    }
}