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