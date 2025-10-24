package view;

// AutoManageApp.java
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutoManageApp extends JFrame {
    public static void main(String[] args) {
        // Đảm bảo UI chạy trên Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Hiển thị hộp thoại đăng nhập trước
            DangNhapDialog login = new DangNhapDialog();
            boolean ok = login.hienThiHopThoai();
            if (ok) {
                MainFrame frame = new MainFrame(login.getTenNguoiDung());
                frame.setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}

/* ------------------------
   Hộp thoại đăng nhập
   ------------------------ */
class DangNhapDialog extends JDialog {
    private boolean thanhCong = false;
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JCheckBox cbHienMatKhau;

    public DangNhapDialog() {
        setModal(true);
        setTitle("Đăng Nhập - AutoManage Pro");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        khoiTaoUI();
    }

    private void khoiTaoUI() {
        // Panel chính với background gradient
        JPanel panelChinh = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color mau1 = new Color(74, 144, 226); // Xanh dương nhạt
                Color mau2 = new Color(58, 123, 213); // Xanh dương đậm
                GradientPaint gradient = new GradientPaint(0, 0, mau1, getWidth(), getHeight(), mau2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelChinh.setBorder(new EmptyBorder(40, 40, 40, 40));
        add(panelChinh);

        // Header với logo và tiêu đề
        JPanel panelHeader = new JPanel(new BorderLayout(10, 10));
        panelHeader.setOpaque(false);
        
        JLabel lblLogo = new JLabel("🚗", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblLogo.setForeground(Color.WHITE);
        
        JLabel lblTieuDe = new JLabel("AutoManage Pro", SwingConstants.CENTER);
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTieuDe.setForeground(Color.WHITE);
        
        JLabel lblPhuDe = new JLabel("Hệ thống quản lý cửa hàng ô tô", SwingConstants.CENTER);
        lblPhuDe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPhuDe.setForeground(new Color(240, 240, 240));
        
        panelHeader.add(lblLogo, BorderLayout.NORTH);
        panelHeader.add(lblTieuDe, BorderLayout.CENTER);
        panelHeader.add(lblPhuDe, BorderLayout.SOUTH);
        panelChinh.add(panelHeader, BorderLayout.NORTH);

        // Panel form đăng nhập
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setOpaque(false);
        panelForm.setBorder(new EmptyBorder(40, 0, 20, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Tên đăng nhập
        JLabel lblTenDangNhap = new JLabel("Tên đăng nhập:");
        lblTenDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTenDangNhap.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_START;
        panelForm.add(lblTenDangNhap, gbc);

        txtTenDangNhap = new JTextField();
        txtTenDangNhap.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTenDangNhap.setPreferredSize(new Dimension(100, 42));
        txtTenDangNhap.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(12, 12, 12, 12)
        ));
        gbc.gridx = 0; gbc.gridy = 1; 
        panelForm.add(txtTenDangNhap, gbc);

        // Mật khẩu
        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMatKhau.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2; 
        panelForm.add(lblMatKhau, gbc);

        // Panel cho mật khẩu và nút hiện/ẩn
        JPanel panelMatKhau = new JPanel(new BorderLayout(5, 0));
        panelMatKhau.setOpaque(false);
        
        txtMatKhau = new JPasswordField();
        txtMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMatKhau.setPreferredSize(new Dimension(100, 42));
        txtMatKhau.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(12, 12, 12, 12)
        ));
        panelMatKhau.add(txtMatKhau, BorderLayout.CENTER);

        // Nút hiện/ẩn mật khẩu
        JButton btnToggleMatKhau = new JButton("👁️");
        btnToggleMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnToggleMatKhau.setPreferredSize(new Dimension(50, 42));
        btnToggleMatKhau.setBackground(new Color(240, 240, 240));
        btnToggleMatKhau.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        btnToggleMatKhau.setFocusPainted(false);
        btnToggleMatKhau.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelMatKhau.add(btnToggleMatKhau, BorderLayout.EAST);
        gbc.gridx = 0; gbc.gridy = 3; 
        panelForm.add(panelMatKhau, gbc);

        // Checkbox hiện mật khẩu
        JPanel panelCheckbox = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelCheckbox.setOpaque(false);
        
        cbHienMatKhau = new JCheckBox("Hiện mật khẩu");
        cbHienMatKhau.setOpaque(false);
        cbHienMatKhau.setForeground(Color.WHITE);
        cbHienMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelCheckbox.add(cbHienMatKhau);
        
        gbc.gridx = 0; gbc.gridy = 4; 
        panelForm.add(panelCheckbox, gbc);

        panelChinh.add(panelForm, BorderLayout.CENTER);

        // Panel nút bấm
        JPanel panelNut = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelNut.setOpaque(false);

        JButton btnHuy = new JButton("Hủy");
        btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnHuy.setBackground(new Color(108, 117, 125));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setPreferredSize(new Dimension(120, 42));
        btnHuy.setBorder(new EmptyBorder(10, 25, 10, 25));
        btnHuy.setFocusPainted(false);
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnDangNhap = new JButton("Đăng Nhập");
        btnDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDangNhap.setBackground(new Color(40, 167, 69));
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setPreferredSize(new Dimension(120, 42));
        btnDangNhap.setBorder(new EmptyBorder(10, 25, 10, 25));
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelNut.add(btnHuy);
        panelNut.add(btnDangNhap);
        panelChinh.add(panelNut, BorderLayout.SOUTH);

        // Sự kiện nút hiện/ẩn mật khẩu
        btnToggleMatKhau.addActionListener(e -> {
            if (txtMatKhau.getEchoChar() == '\u2022') { // Đang ẩn
                txtMatKhau.setEchoChar((char) 0); // Hiện
                btnToggleMatKhau.setText("🔒");
                cbHienMatKhau.setSelected(true);
            } else {
                txtMatKhau.setEchoChar('\u2022'); // Ẩn
                btnToggleMatKhau.setText("👁️");
                cbHienMatKhau.setSelected(false);
            }
            txtMatKhau.requestFocus();
        });

        // Sự kiện checkbox hiện mật khẩu
        cbHienMatKhau.addActionListener(e -> {
            if (cbHienMatKhau.isSelected()) {
                txtMatKhau.setEchoChar((char) 0); // Hiện
                btnToggleMatKhau.setText("🔒");
            } else {
                txtMatKhau.setEchoChar('\u2022'); // Ẩn
                btnToggleMatKhau.setText("👁️");
            }
        });

        // Sự kiện nút đăng nhập
        btnDangNhap.addActionListener(e -> {
            String tenDangNhap = txtTenDangNhap.getText().trim();
            String matKhau = new String(txtMatKhau.getPassword());
            
            // Kiểm tra demo đơn giản
            if (tenDangNhap.equals("admin") && matKhau.equals("admin")) {
                thanhCong = true;
                dispose();
            } else if (tenDangNhap.length() > 0 && matKhau.length() > 0) {
                // Chấp nhận bất kỳ user nào không trống cho demo
                thanhCong = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Tên đăng nhập hoặc mật khẩu không hợp lệ!\nVui lòng thử lại.",
                    "Đăng nhập thất bại", 
                    JOptionPane.ERROR_MESSAGE);
                txtTenDangNhap.requestFocus();
            }
        });

        // Sự kiện nút hủy
        btnHuy.addActionListener(e -> {
            thanhCong = false;
            dispose();
        });

        // Nhấn Enter để đăng nhập
        getRootPane().setDefaultButton(btnDangNhap);
        
        // Hiển thị ký tự mật khẩu rõ ràng
        txtMatKhau.setEchoChar('\u2022'); // Ký tự bullet
        
        // Focus vào ô tên đăng nhập khi mở
        SwingUtilities.invokeLater(() -> txtTenDangNhap.requestFocus());
    }

    public boolean hienThiHopThoai() {
        setVisible(true);
        return thanhCong;
    }

    public String getTenNguoiDung() {
        String ten = txtTenDangNhap.getText().trim();
        return ten.isEmpty() ? "Khách" : ten;
    }
}

// Database Connection Class
class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/qloto";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
    }
}

// OTO Model Class
class OTO {
    private String maOTO;
    private String tenOTO;
    private int gia;
    private String loaiOTO;
    private int soLuong;
    private String moTa;
    private String maHang;
    
    public OTO(String maOTO, String tenOTO, int gia, String loaiOTO, int soLuong, String moTa, String maHang) {
        this.maOTO = maOTO;
        this.tenOTO = tenOTO;
        this.gia = gia;
        this.loaiOTO = loaiOTO;
        this.soLuong = soLuong;
        this.moTa = moTa;
        this.maHang = maHang;
    }
    
    // Getters and Setters
    public String getMaOTO() { return maOTO; }
    public void setMaOTO(String maOTO) { this.maOTO = maOTO; }
    
    public String getTenOTO() { return tenOTO; }
    public void setTenOTO(String tenOTO) { this.tenOTO = tenOTO; }
    
    public int getGia() { return gia; }
    public void setGia(int gia) { this.gia = gia; }
    
    public String getLoaiOTO() { return loaiOTO; }
    public void setLoaiOTO(String loaiOTO) { this.loaiOTO = loaiOTO; }
    
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    
    public String getMaHang() { return maHang; }
    public void setMaHang(String maHang) { this.maHang = maHang; }
}

// OTO DAO Class
class OTODAO {
    public List<OTO> getAllOTO() {
        List<OTO> danhSachOTO = new ArrayList<>();
        String sql = "SELECT * FROM oto";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                OTO oto = new OTO(
                    rs.getString("maOTO"),
                    rs.getString("tenOTO"),
                    rs.getInt("gia"),
                    rs.getString("loaiOTO"),
                    rs.getInt("soLuong"),
                    rs.getString("moTa"),
                    rs.getString("maHang")
                );
                danhSachOTO.add(oto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ database: " + e.getMessage(), 
                "Lỗi Database", JOptionPane.ERROR_MESSAGE);
        }
        
        return danhSachOTO;
    }
    
    public boolean updateOTO(OTO oto) {
        String sql = "UPDATE oto SET tenOTO=?, gia=?, loaiOTO=?, soLuong=?, moTa=?, maHang=? WHERE maOTO=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, oto.getTenOTO());
            stmt.setInt(2, oto.getGia());
            stmt.setString(3, oto.getLoaiOTO());
            stmt.setInt(4, oto.getSoLuong());
            stmt.setString(5, oto.getMoTa());
            stmt.setString(6, oto.getMaHang());
            stmt.setString(7, oto.getMaOTO());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật ô tô: " + e.getMessage(),
                "Lỗi Database", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public OTO getOTOByMa(String maOTO) {
        String sql = "SELECT * FROM oto WHERE maOTO = ?";
        OTO oto = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, maOTO);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                oto = new OTO(
                    rs.getString("maOTO"),
                    rs.getString("tenOTO"),
                    rs.getInt("gia"),
                    rs.getString("loaiOTO"),
                    rs.getInt("soLuong"),
                    rs.getString("moTa"),
                    rs.getString("maHang")
                );
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm ô tô: " + e.getMessage(),
                "Lỗi Database", JOptionPane.ERROR_MESSAGE);
        }
        
        return oto;
    }
    
    // Thêm phương thức kiểm tra kết nối
    public boolean testConnection() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private String tenNguoiDung;
    private JLabel lblNguoiDung;

    public MainFrame(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
        setTitle("AutoManage Pro - Quản lý cửa hàng ôtô");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 760);
        setLocationRelativeTo(null);
        khoiTaoUI();
    }

    private void khoiTaoUI() {
        JPanel root = new JPanel(new BorderLayout());
        setContentPane(root);

        // Thanh trên cùng
        JPanel thanhTren = new JPanel(new BorderLayout());
        thanhTren.setBorder(BorderFactory.createMatteBorder(0,0,1,0, new Color(220,220,220)));
        thanhTren.setBackground(Color.WHITE);
        thanhTren.setPreferredSize(new Dimension(0,64));
        root.add(thanhTren, BorderLayout.NORTH);

        JLabel tieuDe = new JLabel("AutoManage Pro");
        tieuDe.setBorder(new EmptyBorder(0,16,0,0));
        tieuDe.setFont(new Font("Segoe UI", Font.BOLD, 18));
        thanhTren.add(tieuDe, BorderLayout.WEST);

        JPanel benPhai = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        benPhai.setOpaque(false);
        lblNguoiDung = new JLabel(tenNguoiDung + " ⌄");
        lblNguoiDung.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JButton btnDangXuat = new JButton("Đăng xuất");
        btnDangXuat.addActionListener(e -> thucHienDangXuat());
        benPhai.add(lblNguoiDung);
        benPhai.add(btnDangXuat);
        thanhTren.add(benPhai, BorderLayout.EAST);

        // Sidebar trái
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(12,12,12,12));
        sidebar.setBackground(new Color(250,250,250));
        sidebar.setPreferredSize(new Dimension(220,0));
        root.add(sidebar, BorderLayout.WEST);

        String[] danhMuc = {"Bảng điều khiển", "Sản phẩm", "Khách hàng", "Nhân viên", "Giao dịch", "Thống kê", "Cài đặt"};
        ButtonGroup group = new ButtonGroup();
        for (String ten : danhMuc) {
            JToggleButton btn = new JToggleButton(ten);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0,8)));
            group.add(btn);
            btn.addActionListener(e -> hienThiThe(ten));
            if (ten.equals("Bảng điều khiển")) btn.setSelected(true);
        }

        // Khu vực nội dung với CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBorder(new EmptyBorder(16,16,16,16));
        root.add(contentPanel, BorderLayout.CENTER);

        // Thêm các thẻ
        contentPanel.add(xayDungPanelBangDieuKhien(), "Bảng điều khiển");
        contentPanel.add(new PanelSanPham(), "Sản phẩm");
        contentPanel.add(new PanelKhachHang(), "Khách hàng");
        contentPanel.add(new PanelNhanVien(), "Nhân viên");
        contentPanel.add(new PanelGiaoDich(), "Giao dịch");
        contentPanel.add(new PanelThongKe(), "Thống kê");
        contentPanel.add(xayDungPanelCaiDat(), "Cài đặt");
    }

    private void hienThiThe(String ten) {
        cardLayout.show(contentPanel, ten);
    }

    private void thucHienDangXuat() {
        int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất?",
                "Đăng xuất", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (xacNhan == JOptionPane.YES_OPTION) {
            dispose();
            // Mở lại đăng nhập
            DangNhapDialog login = new DangNhapDialog();
            boolean ok = login.hienThiHopThoai();
            if (ok) {
                MainFrame frame = new MainFrame(login.getTenNguoiDung());
                frame.setVisible(true);
            } else {
                System.exit(0);
            }
        }
    }

    private JPanel xayDungPanelBangDieuKhien() {
        JPanel p = new JPanel(new BorderLayout(12,12));
        JLabel tieuDe = new JLabel("Bảng Điều Khiển");
        tieuDe.setFont(new Font("Segoe UI", Font.BOLD, 20));
        p.add(tieuDe, BorderLayout.NORTH);

        JPanel luoi = new JPanel(new GridLayout(2, 3, 12,12));
        luoi.add(taoThe("Tổng doanh thu", "₫ 1,234,567"));
        luoi.add(taoThe("Khách hàng mới", "12"));
        luoi.add(taoThe("Nhân viên hoạt động", "5"));
        luoi.add(taoThe("Sản phẩm trong kho", "128"));
        luoi.add(taoThe("Giao dịch chờ xử lý", "3"));
        luoi.add(taoThe("Lợi nhuận gộp", "₫ 345,678"));
        p.add(luoi, BorderLayout.CENTER);
        return p;
    }

    private JPanel taoThe(String tieuDe, String giaTri) {
        JPanel the = new JPanel(new BorderLayout());
        the.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                new EmptyBorder(12,12,12,12)
        ));
        the.setBackground(Color.WHITE);
        JLabel lblTieuDe = new JLabel(tieuDe);
        lblTieuDe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JLabel lblGiaTri = new JLabel(giaTri);
        lblGiaTri.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblGiaTri.setForeground(new Color(0, 123, 255));
        the.add(lblTieuDe, BorderLayout.NORTH);
        the.add(lblGiaTri, BorderLayout.CENTER);
        return the;
    }

    private JPanel xayDungPanelCaiDat() {
        JPanel p = new JPanel(new BorderLayout());
        JLabel tieuDe = new JLabel("Cài Đặt");
        tieuDe.setFont(new Font("Segoe UI", Font.BOLD, 20));
        p.add(tieuDe, BorderLayout.NORTH);
        JPanel trungTam = new JPanel();
        trungTam.add(new JLabel("Khu vực cài đặt - cấu hình tùy chọn ứng dụng tại đây."));
        p.add(trungTam, BorderLayout.CENTER);
        return p;
    }
}

/* ------------------------
   Panel sản phẩm
   ------------------------ */
class PanelSanPham extends JPanel {
    private DefaultTableModel model;
    private JTable table;
    private OTODAO otoDAO;

    public PanelSanPham() {
        otoDAO = new OTODAO();
        setLayout(new BorderLayout(8,8));
        JLabel tieuDe = new JLabel("Quản Lý Sản Phẩm");
        tieuDe.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(tieuDe, BorderLayout.NORTH);

        // Kiểm tra kết nối database
        if (!otoDAO.testConnection()) {
            JOptionPane.showMessageDialog(this, 
                "Không thể kết nối đến database!\nVui lòng kiểm tra:\n- MySQL server đang chạy\n- Database 'qloto' tồn tại\n- Username/password đúng",
                "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
        }

        // Thanh công cụ
        JPanel thanhCongCu = new JPanel(new FlowLayout(FlowLayout.LEFT,8,8));
        JButton btnThem = new JButton("➕ Thêm Sản Phẩm");
        JButton btnNhap = new JButton("📥 Nhập");
        JButton btnXuat = new JButton("📤 Xuất");
        JButton btnTaiLai = new JButton("🔄 Tải lại");
        JTextField txtTimKiem = new JTextField(20);
        thanhCongCu.add(btnThem);
        thanhCongCu.add(btnNhap);
        thanhCongCu.add(btnXuat);
        thanhCongCu.add(btnTaiLai);
        thanhCongCu.add(new JLabel("🔍 Tìm kiếm:"));
        thanhCongCu.add(txtTimKiem);
        add(thanhCongCu, BorderLayout.PAGE_START);

        String[] cot = {"Mã OTO", "Tên OTO", "Giá", "Loại OTO", "Số lượng", "Mô tả", "Mã hãng", "Thao tác"};
        model = new DefaultTableModel(cot, 0) {
            @Override 
            public boolean isCellEditable(int row, int column) { 
                return false; 
            }
        };
        table = new JTable(model);
        table.setRowHeight(36);
        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        // Tải dữ liệu từ database
        taiDuLieuOTO();

        // Sự kiện
        btnThem.addActionListener(e -> moFormSanPham(null));
        btnTaiLai.addActionListener(e -> taiDuLieuOTO());
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int hang = table.getSelectedRow();
                    if (hang >= 0) {
                        String id = model.getValueAt(hang,0).toString();
                        moFormSuaSanPham(id);
                    }
                }
            }
        });
    }

    private void taiDuLieuOTO() {
        model.setRowCount(0); // Xóa dữ liệu cũ
        List<OTO> danhSachOTO = otoDAO.getAllOTO();
        
        if (danhSachOTO.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Không có dữ liệu ô tô trong database.\nVui lòng thêm dữ liệu vào bảng oto.",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        
        for (OTO oto : danhSachOTO) {
            Object[] rowData = {
                oto.getMaOTO(),
                oto.getTenOTO(),
                "₫ " + String.format("%,d", oto.getGia()),
                oto.getLoaiOTO(),
                oto.getSoLuong(),
                oto.getMoTa(),
                oto.getMaHang(),
                "✏️ Sửa"
            };
            model.addRow(rowData);
        }
    }

    private void moFormSanPham(String idSanPham) {
        JOptionPane.showMessageDialog(this, "Mở form thêm sản phẩm mới");
    }

    private void moFormSuaSanPham(String maOTO) {
        OTO oto = otoDAO.getOTOByMa(maOTO);
        if (oto != null) {
            new SuaOTOForm(oto, this).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy ô tô với mã: " + maOTO);
        }
    }

    public void capNhatDuLieu() {
        taiDuLieuOTO();
    }
}

/* ------------------------
   Form sửa thông tin ô tô
   ------------------------ */
class SuaOTOForm extends JDialog {
    private OTO oto;
    private PanelSanPham panelSanPham;
    private OTODAO otoDAO;
    
    private JTextField txtMaOTO;
    private JTextField txtTenOTO;
    private JTextField txtGia;
    private JTextField txtLoaiOTO;
    private JSpinner spnSoLuong;
    private JTextArea txtMoTa;
    private JTextField txtMaHang;
    
    public SuaOTOForm(OTO oto, PanelSanPham panelSanPham) {
        this.oto = oto;
        this.panelSanPham = panelSanPham;
        this.otoDAO = new OTODAO();
        
        setTitle("Sửa Thông Tin Ô Tô");
        setModal(true);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        khoiTaoUI();
        hienThiThongTinOTO();
    }
    
    private void khoiTaoUI() {
        JPanel panelChinh = new JPanel(new BorderLayout(10, 10));
        panelChinh.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(panelChinh);
        
        // Tiêu đề
        JLabel lblTieuDe = new JLabel("SỬA THÔNG TIN Ô TÔ", SwingConstants.CENTER);
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTieuDe.setForeground(new Color(0, 123, 255));
        panelChinh.add(lblTieuDe, BorderLayout.NORTH);
        
        // Panel form
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(new EmptyBorder(10, 0, 10, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Mã OTO (readonly)
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Mã OTO:"), gbc);
        txtMaOTO = new JTextField();
        txtMaOTO.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 0;
        panelForm.add(txtMaOTO, gbc);
        
        // Tên OTO
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Tên OTO:"), gbc);
        txtTenOTO = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1;
        panelForm.add(txtTenOTO, gbc);
        
        // Giá
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Giá:"), gbc);
        txtGia = new JTextField();
        gbc.gridx = 1; gbc.gridy = 2;
        panelForm.add(txtGia, gbc);
        
        // Loại OTO
        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Loại OTO:"), gbc);
        txtLoaiOTO = new JTextField();
        gbc.gridx = 1; gbc.gridy = 3;
        panelForm.add(txtLoaiOTO, gbc);
        
        // Số lượng
        gbc.gridx = 0; gbc.gridy = 4;
        panelForm.add(new JLabel("Số lượng:"), gbc);
        spnSoLuong = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        gbc.gridx = 1; gbc.gridy = 4;
        panelForm.add(spnSoLuong, gbc);
        
        // Mã hãng
        gbc.gridx = 0; gbc.gridy = 5;
        panelForm.add(new JLabel("Mã hãng:"), gbc);
        txtMaHang = new JTextField();
        gbc.gridx = 1; gbc.gridy = 5;
        panelForm.add(txtMaHang, gbc);
        
        // Mô tả
        gbc.gridx = 0; gbc.gridy = 6;
        panelForm.add(new JLabel("Mô tả:"), gbc);
        txtMoTa = new JTextArea(3, 20);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        gbc.gridx = 1; gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        panelForm.add(scrollMoTa, gbc);
        
        panelChinh.add(panelForm, BorderLayout.CENTER);
        
        // Panel nút
        JPanel panelNut = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnHuy = new JButton("Hủy");
        JButton btnLuu = new JButton("Lưu Thay Đổi");
        
        btnLuu.setBackground(new Color(40, 167, 69));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setFocusPainted(false);
        
        btnHuy.setBackground(new Color(108, 117, 125));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setFocusPainted(false);
        
        panelNut.add(btnHuy);
        panelNut.add(btnLuu);
        panelChinh.add(panelNut, BorderLayout.SOUTH);
        
        // Sự kiện
        btnHuy.addActionListener(e -> dispose());
        btnLuu.addActionListener(e -> luuThongTinOTO());
        
        // Enter để lưu
        getRootPane().setDefaultButton(btnLuu);
    }
    
    private void hienThiThongTinOTO() {
        txtMaOTO.setText(oto.getMaOTO());
        txtTenOTO.setText(oto.getTenOTO());
        txtGia.setText(String.valueOf(oto.getGia()));
        txtLoaiOTO.setText(oto.getLoaiOTO());
        spnSoLuong.setValue(oto.getSoLuong());
        txtMoTa.setText(oto.getMoTa());
        txtMaHang.setText(oto.getMaHang());
    }
    
    private void luuThongTinOTO() {
        try {
            // Cập nhật thông tin từ form vào đối tượng OTO
            oto.setTenOTO(txtTenOTO.getText().trim());
            oto.setGia(Integer.parseInt(txtGia.getText().trim()));
            oto.setLoaiOTO(txtLoaiOTO.getText().trim());
            oto.setSoLuong((Integer) spnSoLuong.getValue());
            oto.setMoTa(txtMoTa.getText().trim());
            oto.setMaHang(txtMaHang.getText().trim());
            
            // Kiểm tra dữ liệu
            if (oto.getTenOTO().isEmpty() || oto.getMaHang().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bắt buộc!");
                return;
            }
            
            // Cập nhật vào database
            boolean thanhCong = otoDAO.updateOTO(oto);
            if (thanhCong) {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin ô tô thành công!");
                panelSanPham.capNhatDuLieu();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá phải là số nguyên!");
            txtGia.requestFocus();
        }
    }
}

/* ------------------------
   Panel khách hàng
   ------------------------ */
class PanelKhachHang extends JPanel {
    private DefaultTableModel model;

    public PanelKhachHang() {
        setLayout(new BorderLayout(8,8));
        JLabel tieuDe = new JLabel("Quản Lý Khách Hàng");
        tieuDe.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(tieuDe, BorderLayout.NORTH);

        JPanel thanhCongCu = new JPanel(new FlowLayout(FlowLayout.LEFT,8,8));
        JButton btnThem = new JButton("➕ Thêm Khách Hàng");
        JTextField txtTimKiem = new JTextField(20);
        thanhCongCu.add(btnThem);
        thanhCongCu.add(new JLabel("🔍 Tìm kiếm:"));
        thanhCongCu.add(txtTimKiem);
        add(thanhCongCu, BorderLayout.PAGE_START);

        String[] cot = {"ID", "Họ tên", "SĐT", "Email", "Địa chỉ", "Mua gần nhất", "Thao tác"};
        model = new DefaultTableModel(cot,0) {
            @Override 
            public boolean isCellEditable(int row, int column) { 
                return false; 
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(30);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnThem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mở form thêm khách hàng"));

        // Dữ liệu mẫu
        model.addRow(new Object[]{"C001","Nguyễn Văn A","0909123456","a@example.com","Hà Nội","2025-10-01"});
        model.addRow(new Object[]{"C002","Trần Thị B","0912345678","b@example.com","Hồ Chí Minh","2025-09-20"});
    }
}

/* ------------------------
   Panel nhân viên
   ------------------------ */
class PanelNhanVien extends JPanel {
    private DefaultTableModel model;

    public PanelNhanVien() {
        setLayout(new BorderLayout(8,8));
        JLabel tieuDe = new JLabel("Quản Lý Nhân Viên");
        tieuDe.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(tieuDe, BorderLayout.NORTH);

        JPanel thanhCongCu = new JPanel(new FlowLayout(FlowLayout.LEFT,8,8));
        JButton btnThem = new JButton("➕ Thêm Nhân Viên");
        thanhCongCu.add(btnThem);
        add(thanhCongCu, BorderLayout.PAGE_START);

        String[] cot = {"ID","Họ tên","Vai trò","Tên đăng nhập","SĐT","Trạng thái","Thao tác"};
        model = new DefaultTableModel(cot,0) {
            @Override 
            public boolean isCellEditable(int row, int column) { 
                return false; 
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(30);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnThem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mở form thêm nhân viên"));

        model.addRow(new Object[]{"S001","Lê Văn C","Quản trị","admin","0909000111","Đang làm"});
        model.addRow(new Object[]{"S002","Phạm Thị D","Bán hàng","pthd","0909000222","Đang làm"});
    }
}

/* ------------------------
   Panel giao dịch
   ------------------------ */
class PanelGiaoDich extends JPanel {
    private DefaultTableModel model;

    public PanelGiaoDich() {
        setLayout(new BorderLayout(8,8));
        JLabel tieuDe = new JLabel("Quản Lý Giao Dịch");
        tieuDe.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(tieuDe, BorderLayout.NORTH);

        JPanel thanhCongCu = new JPanel(new FlowLayout(FlowLayout.LEFT,8,8));
        JButton btnMoi = new JButton("💰 Giao Dịch Mới");
        JButton btnXuat = new JButton("📊 Xuất CSV");
        thanhCongCu.add(btnMoi);
        thanhCongCu.add(btnXuat);
        add(thanhCongCu, BorderLayout.PAGE_START);

        String[] cot = {"ID","Ngày","Khách hàng","Sản phẩm","Tổng tiền","Trạng thái","Thao tác"};
        model = new DefaultTableModel(cot,0) {
            @Override 
            public boolean isCellEditable(int row, int column) { 
                return false; 
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(28);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnMoi.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mở giao dịch nhanh (POS)"));

        // mẫu
        model.addRow(new Object[]{"T1001","2025-10-15","Nguyễn Văn A","Toyota Camry x1","₫ 850,000,000","Hoàn thành"});
        model.addRow(new Object[]{"T1002","2025-10-18","Trần Thị B","Honda Civic x1","₫ 600,000,000","Đang chờ"});
    }
}

/* ------------------------
   Panel thống kê
   ------------------------ */
class PanelThongKe extends JPanel {
    public PanelThongKe() {
        setLayout(new BorderLayout(8,8));
        JLabel tieuDe = new JLabel("Thống Kê & Báo Cáo");
        tieuDe.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(tieuDe, BorderLayout.NORTH);

        JPanel trungTam = new JPanel(new GridLayout(2,1,8,8));
        JPanel tren = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        tren.add(new JLabel("📅 Khoảng thời gian:"));
        tren.add(new JTextField("2025-10-01 đến 2025-10-20", 24));
        JButton btnApDung = new JButton("Áp dụng");
        tren.add(btnApDung);
        trungTam.add(tren);

        JPanel thongKe = new JPanel(new GridLayout(1,3,12,12));
        thongKe.add(taoTheThongKe("Doanh thu (30 ngày)", "₫ 3,000,000,000"));
        thongKe.add(taoTheThongKe("Mẫu xe bán chạy", "Toyota Camry"));
        thongKe.add(taoTheThongKe("Giá trị tồn kho", "₫ 12,000,000,000"));
        trungTam.add(thongKe);

        add(trungTam, BorderLayout.CENTER);
    }

    private JPanel taoTheThongKe(String tieuDe, String giaTri) {
        JPanel the = new JPanel(new BorderLayout());
        the.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                new EmptyBorder(10,10,10,10)
        ));
        the.setBackground(Color.WHITE);
        the.add(new JLabel(tieuDe), BorderLayout.NORTH);
        JLabel lblGiaTri = new JLabel(giaTri);
        lblGiaTri.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblGiaTri.setForeground(new Color(40, 167, 69));
        the.add(lblGiaTri, BorderLayout.CENTER);
        return the;
    }
}