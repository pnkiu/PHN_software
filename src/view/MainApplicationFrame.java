package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// ============================ MAIN FRAME ============================
public class MainApplicationFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainApplicationFrame() {
        setTitle("Hệ Thống Quản Lý Ô Tô");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 760);
        setLocationRelativeTo(null);
        initComponents();
    }

    // ============================ INIT COMPONENTS ============================
    private void initComponents() {
        // Main panel với BorderLayout
        JPanel root = new JPanel(new BorderLayout());
        setContentPane(root);

        root.add(createHeaderPanel(), BorderLayout.NORTH);
        root.add(createSidebarPanel(), BorderLayout.WEST);
        root.add(createContentPanel(), BorderLayout.CENTER);
    }

    // ============================ HEADER SECTION ============================
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0, new Color(220,220,220)));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setPreferredSize(new Dimension(0,64));

        // Tiêu đề ứng dụng
        JLabel appTitle = new JLabel("Hệ Thống Quản Lý Ô Tô");
        appTitle.setBorder(new EmptyBorder(0,16,0,0));
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(appTitle, BorderLayout.WEST);

        // Thông tin người dùng
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        userPanel.setOpaque(false);
        JLabel userLabel = new JLabel("Quản trị viên");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userPanel.add(userLabel);
        headerPanel.add(userPanel, BorderLayout.EAST);

        return headerPanel;
    }

    // ============================ SIDEBAR SECTION ============================
    private JPanel createSidebarPanel() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(12,12,12,12));
        sidebar.setBackground(new Color(250,250,250));
        sidebar.setPreferredSize(new Dimension(220,0));

        String[] menuItems = {"Thống kê", "Sản phẩm", "Khách hàng", "Nhân viên", "Giao dịch"};
        ButtonGroup menuGroup = new ButtonGroup();

        for (String item : menuItems) {
            JToggleButton menuButton = createMenuButton(item);
            sidebar.add(menuButton);
            sidebar.add(Box.createRigidArea(new Dimension(0,8)));
            menuGroup.add(menuButton);

            // Chọn thống kê làm mặc định
            if (item.equals("Thống kê")) {
                menuButton.setSelected(true);
            }
        }

        return sidebar;
    }

    private JToggleButton createMenuButton(String text) {
        JToggleButton button = new JToggleButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.addActionListener(e -> showCard(text));
        return button;
    }

    // ============================ CONTENT SECTION ============================
    private JPanel createContentPanel() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBorder(new EmptyBorder(16,16,16,16));

        // Thêm các panel vào content
        contentPanel.add(new ProductPanel(), "Sản phẩm");

        contentPanel.add(createStatisticsPanel(), "Thống kê");
        contentPanel.add(createCustomersPanel(), "Khách hàng");
        contentPanel.add(createEmployeesPanel(), "Nhân viên");
        contentPanel.add(createTransactionsPanel(), "Giao dịch");

        return contentPanel;
    }

    private void showCard(String cardName) {
        cardLayout.show(contentPanel, cardName);
    }

    // ============================ STATISTICS PANEL ============================
    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new BorderLayout(8,8));

        // Tiêu đề
        panel.add(createSectionTitle("Thống Kê & Báo Cáo"), BorderLayout.NORTH);

        // Nội dung chính
        JPanel mainContent = new JPanel(new GridLayout(2, 1, 8, 8));
        mainContent.add(createTimeRangePanel());
        mainContent.add(createStatsGridPanel());

        panel.add(mainContent, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTimeRangePanel() {
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        timePanel.add(new JLabel("📅 Khoảng thời gian:"));
        timePanel.add(new JTextField("2025-10-01 đến 2025-10-20", 24));
        timePanel.add(new JButton("Áp dụng"));
        return timePanel;
    }

    private JPanel createStatsGridPanel() {
        JPanel statsGrid = new JPanel(new GridLayout(1, 3, 12, 12));
        statsGrid.add(createStatCard("Doanh thu (30 ngày)", "₫ 3,000,000,000"));
        statsGrid.add(createStatCard("Mẫu xe bán chạy", "Toyota Camry"));
        statsGrid.add(createStatCard("Giá trị tồn kho", "₫ 12,000,000,000"));
        return statsGrid;
    }

    // ============================ CUSTOMERS PANEL ============================
    private JPanel createCustomersPanel() {
        JPanel panel = new JPanel(new BorderLayout(8,8));

        panel.add(createSectionTitle("Quản Lý Khách Hàng"), BorderLayout.NORTH);
        panel.add(createCustomersToolbar(), BorderLayout.PAGE_START);
        panel.add(createCustomersTable(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCustomersToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        toolbar.add(new JButton("➕ Thêm Khách Hàng"));
        toolbar.add(new JLabel("🔍 Tìm kiếm:"));
        toolbar.add(new JTextField(20));
        return toolbar;
    }

    private JScrollPane createCustomersTable() {
        String[] columns = {"ID", "Họ tên", "SĐT", "Email", "Địa chỉ", "Mua gần nhất", "Thao tác"};
        DefaultTableModel model = createNonEditableTableModel(columns);

        Object[][] sampleData = {
                {"C001", "Nguyễn Văn A", "0909123456", "a@example.com", "Hà Nội", "2025-10-01", "✏️ Sửa"},
                {"C002", "Trần Thị B", "0912345678", "b@example.com", "Hồ Chí Minh", "2025-09-20", "✏️ Sửa"},
                {"C003", "Lê Văn C", "0909111222", "c@example.com", "Đà Nẵng", "2025-10-15", "✏️ Sửa"},
                {"C004", "Phạm Thị D", "0912333444", "d@example.com", "Hải Phòng", "2025-10-10", "✏️ Sửa"}
        };

        for (Object[] row : sampleData) {
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setRowHeight(30);
        return new JScrollPane(table);
    }

    // ============================ EMPLOYEES PANEL ============================
    private JPanel createEmployeesPanel() {
        JPanel panel = new JPanel(new BorderLayout(8,8));

        panel.add(createSectionTitle("Quản Lý Nhân Viên"), BorderLayout.NORTH);
        panel.add(createEmployeesToolbar(), BorderLayout.PAGE_START);
        panel.add(createEmployeesTable(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createEmployeesToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        toolbar.add(new JButton("➕ Thêm Nhân Viên"));
        toolbar.add(new JLabel("🔍 Tìm kiếm:"));
        toolbar.add(new JTextField(20));
        return toolbar;
    }

    private JScrollPane createEmployeesTable() {
        String[] columns = {"ID", "Họ tên", "Vai trò", "Tên đăng nhập", "SĐT", "Trạng thái", "Thao tác"};
        DefaultTableModel model = createNonEditableTableModel(columns);

        Object[][] sampleData = {
                {"NV001", "Nguyễn Văn Nam", "Quản lý", "namnv", "0909000111", "Đang làm", "✏️ Sửa"},
                {"NV002", "Trần Thị Hương", "Nhân viên bán hàng", "huongtt", "0909000222", "Đang làm", "✏️ Sửa"},
                {"NV003", "Lê Văn Tú", "Kế toán", "tulv", "0909000333", "Đang làm", "✏️ Sửa"},
                {"NV004", "Phạm Thị Lan", "Nhân viên kho", "lanpt", "0909000444", "Đang làm", "✏️ Sửa"},
                {"NV005", "Hoàng Văn Minh", "Kỹ thuật", "minhhv", "0909000555", "Nghỉ phép", "✏️ Sửa"}
        };

        for (Object[] row : sampleData) {
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setRowHeight(30);
        return new JScrollPane(table);
    }

    // ============================ TRANSACTIONS PANEL ============================
    private JPanel createTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout(8,8));

        panel.add(createSectionTitle("Quản Lý Giao Dịch"), BorderLayout.NORTH);
        panel.add(createTransactionsToolbar(), BorderLayout.PAGE_START);
        panel.add(createTransactionsTable(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTransactionsToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        toolbar.add(new JButton("💰 Giao Dịch Mới"));
        toolbar.add(new JButton("📊 Xuất CSV"));
        toolbar.add(new JLabel("🔍 Tìm kiếm:"));
        toolbar.add(new JTextField(20));
        return toolbar;
    }

    private JScrollPane createTransactionsTable() {
        String[] columns = {"ID", "Ngày", "Khách hàng", "Sản phẩm", "Tổng tiền", "Trạng thái", "Thao tác"};
        DefaultTableModel model = createNonEditableTableModel(columns);

        Object[][] sampleData = {
                {"GD001", "2025-10-15", "Nguyễn Văn A", "Toyota Camry 2024", "₫ 850,000,000", "Hoàn thành", "📋 Chi tiết"},
                {"GD002", "2025-10-16", "Trần Thị B", "Honda Civic RS", "₫ 720,000,000", "Hoàn thành", "📋 Chi tiết"},
                {"GD003", "2025-10-18", "Lê Văn C", "Ford Ranger Raptor", "₫ 1,250,000,000", "Đang xử lý", "📋 Chi tiết"},
                {"GD004", "2025-10-20", "Phạm Thị D", "Hyundai SantaFe", "₫ 980,000,000", "Chờ thanh toán", "📋 Chi tiết"},
                {"GD005", "2025-10-22", "Hoàng Văn E", "Mazda CX-5", "₫ 820,000,000", "Hoàn thành", "📋 Chi tiết"}
        };

        for (Object[] row : sampleData) {
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setRowHeight(28);
        return new JScrollPane(table);
    }

    // ============================ UTILITY METHODS ============================
    private JLabel createSectionTitle(String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        return titleLabel;
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                new EmptyBorder(10,10,10,10)
        ));
        card.setBackground(Color.WHITE);

        card.add(new JLabel(title), BorderLayout.NORTH);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valueLabel.setForeground(new Color(40, 167, 69));
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private DefaultTableModel createNonEditableTableModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}