
package view;

import model.CarManageModel;
import controller.CarManageController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CarManageDialog extends JPanel {
    private CarManageController controller;
    private DefaultTableModel tableModel;
    private JTable table;
    private JDialog parentDialog;

    // Components
    private JTextField txtMaOto, txtTenOto, txtGia, txtLoaiOto, txtSoLuong, txtMoTa, txtMaHang, txtSoLuotBan;
    private JButton btnSua, btnDong;

    public CarManageDialog(JDialog parent) {
        this.parentDialog = parent;
        this.controller = new CarManageController();
        initComponents();
        loadCarDataFromDatabase(); // Thay thế loadSampleData()
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tiêu đề
        JLabel lblTitle = new JLabel("SỬA THÔNG TIN Ô TÔ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 123, 255));
        lblTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

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
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        mainContentPanel.add(rightPanel, BorderLayout.CENTER);
        add(mainContentPanel, BorderLayout.CENTER);

        // Thêm sự kiện
        addEventListeners();
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

    private void addEventListeners() {
        btnSua.addActionListener(e -> suaOto());
        btnDong.addActionListener(e -> parentDialog.dispose());

        // Sự kiện click đúp vào bảng để chọn ô tô cần sửa
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        hienThiThongTinTuTable(selectedRow);
                    }
                }
            }
        });
    }

    // PHƯƠNG THỨC TẢI DỮ LIỆU TỪ DATABASE
    private void loadCarDataFromDatabase() {
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
                        String.valueOf(car.getSoLuotBan())
                };
                tableModel.addRow(rowData);
            }

            System.out.println("Đã tải " + carList.size() + " ô tô từ database");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu từ database: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

            // Hiển thị thông báo lỗi chi tiết hơn
            System.err.println("Chi tiết lỗi:");
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

            if (validateInput()) {
                // Tạo đối tượng CarManageModel từ dữ liệu form
                CarManageModel car = new CarManageModel();
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
            String maOTO = tableModel.getValueAt(row, 0).toString();
            System.out.println("Đang tìm ô tô với mã: " + maOTO);

            CarManageModel car = controller.getCarByMaOTO(maOTO);

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
        table.clearSelection();
    }

    private boolean validateInput() {
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

    // PHƯƠNG THỨC ĐỊNH DẠNG TIỀN TỆ
    private String formatCurrency(double amount) {
        return String.format("₫ %,d", (int) amount);
    }
}