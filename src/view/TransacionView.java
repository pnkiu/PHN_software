package view;

import controller.TransactionController;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.TransactionDAO;
import model.CustomerModel;
import model.StaffModel;
import model.TransactionModel;
import model.ProductModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransacionView extends JPanel {
    // ... (Các biến của bạn đã đúng) ...
    private JButton jButton_add;
    private JButton jButton_edit;
    private JButton jButton_delete;
    private JButton jButton_details;
    private JTextField jTextField_search;
    private JButton jButton_search;
    private JDialog addDialog;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JComboBox<CustomerModel> cbKhachHang;
    private JComboBox<StaffModel> cbNhanVien;
    private JTextField txtMaOTO;
    private JButton btnChonOto;
    private JSpinner spinnerSoLuong;
    private JTextField txtTongTien;
    private ProductModel sanPhamDaChon;
    private JDialog addCustomerDialog;
    private double giaBan;


    public TransacionView() {
        this.init();
    }

    public void init() {
        // ... (Code JPanel của bạn đã đúng) ...
        Font font = new Font("Arial", Font.BOLD, 40);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JPanel jPanel_right = new JPanel(new BorderLayout());
        jPanel_right.setBackground(Color.WHITE);
        JLabel jLabel_header = new JLabel("QUẢN LÝ GIAO DỊCH", SwingConstants.CENTER);
        jLabel_header.setForeground(Color.BLACK);
        jLabel_header.setFont(font);
        jLabel_header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        JPanel jPanel_action = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        jPanel_action.setBackground(Color.WHITE);
        jButton_add = new JButton("Thêm giao dịch");
        jButton_edit = new JButton("Sửa");
        jButton_delete = new JButton("Xóa");
        jButton_details = new JButton("Chi Tiết");
        jTextField_search = new JTextField(20);
        jButton_search = new JButton("Tìm kiếm");
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        for (JButton btn : new JButton[]{jButton_add, jButton_edit, jButton_delete, jButton_details, jButton_search}) {
            btn.setFont(buttonFont);
            btn.setBackground(new Color(65, 105, 225));
            btn.setForeground(Color.WHITE);
        }
        jPanel_action.add(jButton_add);
        jPanel_action.add(jButton_edit);
        jPanel_action.add(jButton_delete);
        jPanel_action.add(jButton_details);
        jPanel_action.add(jTextField_search);
        jPanel_action.add(jButton_search);
        JPanel jPanel_north_wrapper = new JPanel(new BorderLayout());
        jPanel_north_wrapper.setBackground(Color.WHITE);
        jPanel_north_wrapper.add(jLabel_header, BorderLayout.NORTH);
        jPanel_north_wrapper.add(jPanel_action, BorderLayout.CENTER);
        jPanel_right.add(jPanel_north_wrapper, BorderLayout.NORTH);

        // SỬA: Thêm cột "Số Lượng" (ở vị trí 4)
        String[] columnNames = {"Mã Giao Dịch", "Mã Khách Hàng", "Mã Nhân Viên", "Mã Ô Tô", "Số Lượng", "Tổng Tiền", "Ngày Giao Dịch"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        transactionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel_right.add(scrollPane, BorderLayout.CENTER);
        this.add(jPanel_right, BorderLayout.CENTER);
        TransactionController controller = new TransactionController(this, new TransactionDAO());
    }

    // ... (Các hàm add...Listener của bạn đã đúng) ...
    public void addAddgdListener(ActionListener listener) {
        jButton_add.addActionListener(listener);
    }
    public void addDeletegdListener(ActionListener listener) {
        jButton_delete.addActionListener(listener);
    }
    public void addEditgdListener(ActionListener listener) {
        jButton_edit.addActionListener(listener);
    }
    public void addViewDetailsListener(ActionListener listener) {
        jButton_details.addActionListener(listener);
    }


    public void hienthidulieu(List<TransactionModel> txList) {
        tableModel.setRowCount(0);
        for (TransactionModel tx : txList) {
            Object[] rowData = {
                    tx.getMaGD(),
                    tx.getMaKH(),
                    tx.getMaNV(),
                    tx.getMaOTO(),
                    tx.getSoLuong(), // SỬA: Thêm "Số Lượng"
                    tx.getTongtien(), // SỬA: Dùng T hoa
                    tx.getNgayGD()
            };
            tableModel.addRow(rowData);
        }
    }

    public void formThemsp(TransactionController controller,
                           List<CustomerModel> dsKhachHang,
                           List<StaffModel> dsNhanVien) {
        // ... (Hàm formThemsp của bạn đã đúng, vì bạn đã lấy soLuong từ spinner) ...
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        addDialog = new JDialog(parentFrame, "Thêm Giao Dịch", true);
        addDialog.setSize(450, 450);
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(new BorderLayout());
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        JLabel l1 = new JLabel("Mã Giao Dịch:");
        l1.setBounds(20, 20, 100, 30);
        formPanel.add(l1);
        JTextField txtMaGD = new JTextField();
        txtMaGD.setText("Mã giao dịch");
        txtMaGD.setEditable(false);
        txtMaGD.setBackground(Color.LIGHT_GRAY);
        txtMaGD.setBounds(130, 20, 280, 30);
        formPanel.add(txtMaGD);
        JLabel l2 = new JLabel("Khách Hàng:");
        l2.setBounds(20, 60, 100, 30);
        formPanel.add(l2);
        cbKhachHang = new JComboBox<>(dsKhachHang.toArray(new CustomerModel[0]));
        cbKhachHang.setBounds(130, 60, 170, 30);
        formPanel.add(cbKhachHang);
        JButton btnThemKH = new JButton("Mới...");
        btnThemKH.setBounds(310, 60, 100, 30);
        formPanel.add(btnThemKH);
        JLabel l3 = new JLabel("Nhân Viên:");
        l3.setBounds(20, 100, 100, 30);
        formPanel.add(l3);
        cbNhanVien = new JComboBox<>(dsNhanVien.toArray(new StaffModel[0]));
        cbNhanVien.setBounds(130, 100, 280, 30);
        formPanel.add(cbNhanVien);
        JLabel l4 = new JLabel("Ô tô:");
        l4.setBounds(20, 140, 100, 30);
        formPanel.add(l4);
        txtMaOTO = new JTextField();
        txtMaOTO.setBounds(130, 140, 170, 30);
        txtMaOTO.setEditable(false);
        formPanel.add(txtMaOTO);
        btnChonOto = new JButton("Chọn...");
        btnChonOto.setBounds(310, 140, 100, 30);
        formPanel.add(btnChonOto);
        JLabel l5 = new JLabel("Số Lượng:");
        l5.setBounds(20, 180, 100, 30);
        formPanel.add(l5);
        spinnerSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        spinnerSoLuong.setBounds(130, 180, 280, 30);
        formPanel.add(spinnerSoLuong);
        JLabel l6 = new JLabel("Tổng Tiền:");
        l6.setBounds(20, 220, 100, 30);
        formPanel.add(l6);
        txtTongTien = new JTextField("0");
        txtTongTien.setBounds(130, 220, 280, 30);
        txtTongTien.setEditable(false);
        formPanel.add(txtTongTien);
        JLabel l7 = new JLabel("Ngày Giao Dịch:");
        l7.setBounds(20, 260, 100, 30);
        formPanel.add(l7);
        JTextField txtNgayGD = new JTextField();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String thoiGianHienTai = now.format(formatter);
        txtNgayGD.setText(thoiGianHienTai);
        txtNgayGD.setEditable(false);
        txtNgayGD.setBackground(Color.LIGHT_GRAY);
        txtNgayGD.setBounds(130, 260, 280, 30);
        formPanel.add(txtNgayGD);
        btnChonOto.addActionListener(e -> controller.moCuaSoChonSanPham());
        spinnerSoLuong.addChangeListener(e -> capNhatTongTien());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        btnThemKH.addActionListener(e -> controller.moCuaSoThemKhachHang());
        btnCancel.addActionListener(e -> addDialog.dispose());
        btnSave.addActionListener(e -> {
            try {
                CustomerModel kh = (CustomerModel) cbKhachHang.getSelectedItem();
                StaffModel nv = (StaffModel) cbNhanVien.getSelectedItem();
                if (kh == null || nv == null || sanPhamDaChon == null) {
                    showErrorMessage("Vui lòng chọn đầy đủ Khách Hàng, Nhân Viên và Sản Phẩm!");
                    return;
                }
                String maGD = txtMaGD.getText();
                String maKH = kh.getMaKH();
                String maNV = nv.getMaNV();
                String maOTO = sanPhamDaChon.getMaOto();
                double tongTien = Double.parseDouble(txtTongTien.getText());
                String ngayGD = txtNgayGD.getText();
                int soLuong = (int) spinnerSoLuong.getValue(); // <-- Dòng này đã đúng

                // Gọi hàm 'them' với 7 tham số
                controller.them(maGD, maKH, maNV, maOTO, tongTien, ngayGD, soLuong);
            } catch (NumberFormatException ex) {
                showErrorMessage("Tổng tiền không hợp lệ!");
            } catch (Exception ex) {
                showErrorMessage("Lỗi khi lấy dữ liệu: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);
        addDialog.setVisible(true);
    }

    // ... (Hàm formThemKhachHang của bạn đã đúng) ...
    public void formThemKhachHang(TransactionController controller) {
        //...
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this.addDialog);
        addCustomerDialog = new JDialog(parentFrame, "Thêm Khách Hàng Mới", true);
        addCustomerDialog.setSize(400, 250);
        addCustomerDialog.setLocationRelativeTo(parentFrame);
        addCustomerDialog.setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(null);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel l1 = new JLabel("Tên Khách Hàng:");
        l1.setBounds(20, 20, 100, 30);
        formPanel.add(l1);
        JTextField txtTenKH = new JTextField();
        txtTenKH.setBounds(130, 20, 230, 30);
        formPanel.add(txtTenKH);
        JLabel l2 = new JLabel("Số Điện Thoại:");
        l2.setBounds(20, 60, 100, 30);
        formPanel.add(l2);
        JTextField txtSdtKH = new JTextField();
        txtSdtKH.setBounds(130, 60, 230, 30);
        formPanel.add(txtSdtKH);
        JLabel l3 = new JLabel("Địa Chỉ:");
        l3.setBounds(20, 100, 100, 30);
        formPanel.add(l3);
        JTextField txtDiaChi = new JTextField();
        txtDiaChi.setBounds(130, 100, 230, 30);
        formPanel.add(txtDiaChi);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        btnCancel.addActionListener(e -> addCustomerDialog.dispose());
        btnSave.addActionListener(e -> {
            controller.themKhachHangMoi(
                    txtTenKH.getText(),
                    txtSdtKH.getText(),
                    txtDiaChi.getText()
            );
        });
        addCustomerDialog.add(formPanel, BorderLayout.CENTER);
        addCustomerDialog.add(buttonPanel, BorderLayout.SOUTH);
        addCustomerDialog.setVisible(true);
    }

    public void formSuaGiaoDich(TransactionController controller,
                                TransactionModel tx,
                                CustomerModel khachHangDaChon,
                                StaffModel nhanVienDaChon,
                                ProductModel sanPhamDaChon,
                                List<CustomerModel> dsKhachHang,
                                List<StaffModel> dsNhanVien) {

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog editDialog = new JDialog(parentFrame, "Sửa Giao Dịch", true);
        editDialog.setSize(450, 530); // SỬA: Tăng chiều cao thêm
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);

        // ... (Mã GD, Khách Hàng, Địa chỉ, Nhân Viên, Ô tô - đã đúng) ...
        JLabel l1 = new JLabel("Mã Giao Dịch:");
        l1.setBounds(20, 20, 100, 30);
        formPanel.add(l1);
        JTextField txtMaGD = new JTextField();
        txtMaGD.setBounds(130, 20, 280, 30);
        txtMaGD.setText(tx.getMaGD());
        txtMaGD.setEditable(false);
        txtMaGD.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtMaGD);
        JLabel l2 = new JLabel("Khách Hàng:");
        l2.setBounds(20, 60, 100, 30);
        formPanel.add(l2);
        JComboBox<CustomerModel> cbKH = new JComboBox<>(dsKhachHang.toArray(new CustomerModel[0]));
        for (CustomerModel kh : dsKhachHang) {
            if (kh.getMaKH().equals(khachHangDaChon.getMaKH())) {
                cbKH.setSelectedItem(kh);
                break;
            }
        }
        cbKH.setBounds(130, 60, 280, 30);
        formPanel.add(cbKH);
        JLabel l_diaChi = new JLabel("Địa chỉ:");
        l_diaChi.setBounds(20, 100, 100, 30);
        formPanel.add(l_diaChi);
        final JTextField txtDiaChi = new JTextField();
        txtDiaChi.setBounds(130, 100, 280, 30);
        txtDiaChi.setText(khachHangDaChon.getDckH());
        formPanel.add(txtDiaChi);
        cbKH.addActionListener(e -> {
            CustomerModel selectedCustomer = (CustomerModel) cbKH.getSelectedItem();
            if (selectedCustomer != null) {
                txtDiaChi.setText(selectedCustomer.getDckH());
            }
        });
        JLabel l3 = new JLabel("Nhân Viên:");
        l3.setBounds(20, 140, 100, 30);
        formPanel.add(l3);
        JComboBox<StaffModel> cbNV = new JComboBox<>(dsNhanVien.toArray(new StaffModel[0]));
        for (StaffModel nv : dsNhanVien) {
            if (nv.getMaNV().equals(nhanVienDaChon.getMaNV())) {
                cbNV.setSelectedItem(nv);
                break;
            }
        }
        cbNV.setBounds(130, 140, 280, 30);
        formPanel.add(cbNV);
        JLabel l4 = new JLabel("Ô tô:");
        l4.setBounds(20, 180, 100, 30);
        formPanel.add(l4);
        JTextField txtOto = new JTextField();
        txtOto.setBounds(130, 180, 280, 30);
        txtOto.setText(sanPhamDaChon != null ? sanPhamDaChon.getTenOto() : "Không tìm thấy");
        txtOto.setEditable(false);
        txtOto.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtOto);

        // SỬA: Thêm "Số Lượng" (chỉ-đọc)
        JLabel l5 = new JLabel("Số Lượng:");
        l5.setBounds(20, 220, 100, 30); // y = 180 -> 220
        formPanel.add(l5);
        JTextField txtSoLuong = new JTextField();
        txtSoLuong.setBounds(130, 220, 280, 30);
        txtSoLuong.setText(String.valueOf(tx.getSoLuong())); // Lấy số lượng cũ
        txtSoLuong.setEditable(false);
        txtSoLuong.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtSoLuong);

        // 6. Tổng Tiền (dịch chuyển y)
        JLabel l6 = new JLabel("Tổng Tiền:");
        l6.setBounds(20, 260, 100, 30); // y = 220 -> 260
        formPanel.add(l6);
        JTextField txtTien = new JTextField();
        txtTien.setBounds(130, 260, 280, 30); // y = 220 -> 260
        txtTien.setText(String.valueOf(tx.getTongtien())); // SỬA: T hoa
        txtTien.setEditable(false);
        txtTien.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtTien);

        // 7. Ngày Giao Dịch (dịch chuyển y)
        JLabel l7 = new JLabel("Ngày Giao Dịch:");
        l7.setBounds(20, 300, 100, 30); // y = 260 -> 300
        formPanel.add(l7);
        JTextField txtNgayGD = new JTextField();
        txtNgayGD.setBounds(130, 300, 280, 30); // y = 260 -> 300
        txtNgayGD.setText(tx.getNgayGD());
        formPanel.add(txtNgayGD);

        // ----- Nút bấm -----
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        btnCancel.addActionListener(e -> editDialog.dispose());

        btnSave.addActionListener(e -> {
            try {
                // ... (lấy maGD) ...
                String maGD = txtMaGD.getText();
                String ngayGDMoi = txtNgayGD.getText();
                double tongTien = tx.getTongtien(); // SỬA: T hoa
                String maOto = tx.getMaOTO();
                int soLuong = tx.getSoLuong(); // SỬA: Lấy số lượng cũ

                CustomerModel khMoi = (CustomerModel) cbKH.getSelectedItem();
                StaffModel nvMoi = (StaffModel) cbNV.getSelectedItem();

                String diaChiMoi = txtDiaChi.getText();
                khMoi.setDckH(diaChiMoi);

                // SỬA: Gọi hàm tạo 7 tham số
                TransactionModel txMoi = new TransactionModel(maGD, khMoi.getMaKH(), nvMoi.getMaNV(), maOto, tongTien, ngayGDMoi, soLuong);

                controller.suaGiaoDich(txMoi, khMoi);
                editDialog.dispose();

            } catch (Exception ex) {
                showErrorMessage("Lỗi khi lưu: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        editDialog.add(formPanel, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);
        editDialog.setVisible(true);
    }

    public void showDetailDialog(TransactionModel tx, CustomerModel kh, StaffModel nv, ProductModel sp) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog detailDialog = new JDialog(parentFrame, "Chi Tiết Giao Dịch", true);
        detailDialog.setSize(450, 520); // SỬA: Tăng chiều cao
        detailDialog.setLocationRelativeTo(this);
        detailDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String tenKH = (kh != null) ? kh.getTenKH() : "Không tìm thấy (" + tx.getMaKH() + ")";
        String sdtKH = (kh != null) ? kh.getSdtKH() : "Chưa có sdt";
        String diaChi = (kh != null) ? kh.getDckH() : "chưa thêm địa chỉ";
        String tenNV = (nv != null) ? nv.getTenNV() : "Không tìm thấy (" + tx.getMaNV() + ")";
        String tenOto = (sp != null) ? sp.getTenOto() : "Không tìm thấy (" + tx.getMaOTO() + ")";

        // SỬA: Thêm "Số Lượng"
        String[] labels = {
                "Mã Giao Dịch:",
                "Khách Hàng:",
                "Địa chỉ:",
                "Số Điện Thoại:",
                "Nhân Viên:",
                "Sản Phẩm:",
                "Số Lượng:", // <-- THÊM MỚI
                "Tổng Tiền:",
                "Ngày Giao Dịch:"
        };
        String[] values = {
                tx.getMaGD(),
                tenKH,
                diaChi,
                sdtKH,
                tenNV,
                tenOto,
                String.valueOf(tx.getSoLuong()), // <-- THÊM MỚI
                String.valueOf(tx.getTongtien()), // SỬA: T hoa
                tx.getNgayGD()
        };

        int y_pos = 20;
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setBounds(20, y_pos, 120, 30);
            formPanel.add(label);

            JTextField textField = new JTextField(values[i]);
            textField.setBounds(150, y_pos, 250, 30);
            textField.setEditable(false);
            textField.setBackground(Color.LIGHT_GRAY);
            formPanel.add(textField);

            y_pos += 40;
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnClose = new JButton("Đóng");
        buttonPanel.add(btnClose);
        btnClose.addActionListener(e -> detailDialog.dispose());

        detailDialog.add(formPanel, BorderLayout.CENTER);
        detailDialog.add(buttonPanel, BorderLayout.SOUTH);
        detailDialog.setVisible(true);
    }

    // ... (Các hàm closeAddCustomer, refreshCustomerComboBox, setSanPhamDaChon, capNhatTongTien đã đúng) ...
    public void closeAddCustomerDialog() {
        if (addCustomerDialog != null) {
            addCustomerDialog.dispose();
        }
    }
    public void refreshCustomerComboBox(List<CustomerModel> dsKhachHang, CustomerModel khachHangMoi) {
        if (cbKhachHang == null) return;
        cbKhachHang.removeAllItems();
        for (CustomerModel kh : dsKhachHang) {
            cbKhachHang.addItem(kh);
        }
        cbKhachHang.setSelectedItem(khachHangMoi);
    }
    public void setSanPhamDaChon(ProductModel sanPham) {
        this.sanPhamDaChon = sanPham;
        this.giaBan = sanPham.getGia();
        txtMaOTO.setText(sanPham.getMaOto());
        spinnerSoLuong.setValue(1);
        capNhatTongTien();
    }
    private void capNhatTongTien() {
        if (sanPhamDaChon == null) return;
        try {
            int soLuong = (int) spinnerSoLuong.getValue();
            double tongTien = this.giaBan * soLuong;
            txtTongTien.setText(String.valueOf(tongTien));
        } catch (Exception e) {
            txtTongTien.setText("0");
        }
    }

    // SỬA LỖI NGHIÊM TRỌNG Ở ĐÂY
    public TransactionModel getSelectedTransaction() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một giao dịch để thực hiện!", "Chưa chọn hàng", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        try {
            String maGD = transactionTable.getValueAt(selectedRow, 0).toString();
            String maKH = transactionTable.getValueAt(selectedRow, 1).toString();
            String maNV = transactionTable.getValueAt(selectedRow, 2).toString();
            String maOTO = transactionTable.getValueAt(selectedRow, 3).toString();

            // SỬA: Đọc đúng cột (dựa trên 'columnNames' trong hàm 'init')
            int soLuong = Integer.parseInt(transactionTable.getValueAt(selectedRow, 4).toString());
            double tongTien = Double.parseDouble(transactionTable.getValueAt(selectedRow, 5).toString());
            String ngayGD = transactionTable.getValueAt(selectedRow, 6).toString();

            // SỬA: Gọi đúng constructor 7 tham số
            return new TransactionModel(maGD, maKH, maNV, maOTO, tongTien, ngayGD, soLuong);

        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Không thể lấy dữ liệu từ hàng đã chọn.");
            return null;
        }
    }

    // ... (Các hàm showSuccessMessage, showErrorMessage, closeAddDialog đã đúng) ...
    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    public void closeAddDialog() {
        if (addDialog != null) addDialog.dispose();
    }
}