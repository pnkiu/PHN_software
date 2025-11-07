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
    private List<TransactionModel> fullTransactionList;

    // THÊM: Các biến kiểu dáng chuẩn
    private Font formLabelFont = new Font("Arial", Font.BOLD, 14);
    private Font formFieldFont = new Font("Arial", Font.PLAIN, 14);
    private Font formButtonFont = new Font("Arial", Font.BOLD, 14);
    private Color buttonColor = new Color(65, 105, 225);
    private Color headerColor = new Color(52, 73, 94);


    public TransacionView() {
        this.init();
    }

    public void init() {
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

        // (Tên nút của bạn)
        jButton_add = new JButton("Thêm");
        jButton_edit = new JButton("Sửa");
        jButton_delete = new JButton("Xóa");
        jButton_details = new JButton("Xem chi tiết");
        jTextField_search = new JTextField(15);
        jButton_search = new JButton("Tìm kiếm");

        // (Style nút của bạn đã giống ProductView)
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        for (JButton btn : new JButton[]{jButton_add, jButton_edit, jButton_delete, jButton_details, jButton_search}) {
            btn.setFont(buttonFont);
            btn.setBackground(buttonColor);
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

        String[] columnNames = {"Mã Giao Dịch", "Tên Khách Hàng", "Tên Ô Tô", "Tổng Tiền", "Ngày Giao Dịch"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        transactionTable = new JTable(tableModel);

        // SỬA: Thêm style cho JTable (giống ProductView)
        transactionTable.setFont(new Font("Arial", Font.PLAIN, 14));
        transactionTable.setRowHeight(25);
        transactionTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        transactionTable.getTableHeader().setBackground(headerColor);
        transactionTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel_right.add(scrollPane, BorderLayout.CENTER);
        this.add(jPanel_right, BorderLayout.CENTER);
        TransactionController controller = new TransactionController(this, new TransactionDAO());
    }

    //listener (Giữ nguyên tên hàm mới của bạn)
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
    public void addSearchListener(ActionListener listener) {
        jButton_search.addActionListener(listener);
    }
    public String getSearchText() {
        return jTextField_search.getText();
    }


    public void hienthidulieu(List<TransactionModel> txList) {
        this.fullTransactionList = txList;
        tableModel.setRowCount(0);
        for (TransactionModel tx : txList) {
            Object[] rowData = {
                    tx.getMaGD(),
                    tx.getTenKH(),
                    tx.getTenOTO(),
                    tx.getTongtien(), // Giữ nguyên logic của bạn
                    tx.getNgayGD()
            };
            tableModel.addRow(rowData);
        }
    }

    public void formThemsp(TransactionController controller,
                           List<CustomerModel> dsKhachHang,
                           List<StaffModel> dsNhanVien) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        addDialog = new JDialog(parentFrame, "Thêm Giao Dịch", true);
        addDialog.setSize(450, 450);
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(new BorderLayout());
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);

        // SỬA: Áp dụng Font
        JLabel l1 = new JLabel("Mã Giao Dịch:");
        l1.setFont(formLabelFont);
        l1.setBounds(20, 20, 100, 30);
        formPanel.add(l1);
        JTextField txtMaGD = new JTextField();
        txtMaGD.setFont(formFieldFont);
        txtMaGD.setText("Mã giao dịch");
        txtMaGD.setEditable(false);
        txtMaGD.setBackground(Color.LIGHT_GRAY);
        txtMaGD.setBounds(130, 20, 280, 30);
        formPanel.add(txtMaGD);

        JLabel l2 = new JLabel("Khách Hàng:");
        l2.setFont(formLabelFont);
        l2.setBounds(20, 60, 100, 30);
        formPanel.add(l2);
        cbKhachHang = new JComboBox<>(dsKhachHang.toArray(new CustomerModel[0]));
        cbKhachHang.setFont(formFieldFont);
        cbKhachHang.setBounds(130, 60, 170, 30);
        formPanel.add(cbKhachHang);

        JButton btnThemKH = new JButton("Mới...");
        btnThemKH.setFont(formButtonFont);
        btnThemKH.setBackground(buttonColor);
        btnThemKH.setForeground(Color.WHITE);
        btnThemKH.setBounds(310, 60, 100, 30);
        formPanel.add(btnThemKH);

        JLabel l3 = new JLabel("Nhân Viên:");
        l3.setFont(formLabelFont);
        l3.setBounds(20, 100, 100, 30);
        formPanel.add(l3);
        cbNhanVien = new JComboBox<>(dsNhanVien.toArray(new StaffModel[0]));
        cbNhanVien.setFont(formFieldFont);
        cbNhanVien.setBounds(130, 100, 280, 30);
        formPanel.add(cbNhanVien);

        JLabel l4 = new JLabel("Ô tô:");
        l4.setFont(formLabelFont);
        l4.setBounds(20, 140, 100, 30);
        formPanel.add(l4);
        txtMaOTO = new JTextField();
        txtMaOTO.setFont(formFieldFont);
        txtMaOTO.setBounds(130, 140, 170, 30);
        txtMaOTO.setEditable(false);
        formPanel.add(txtMaOTO);

        JButton btnChonOto = new JButton("Chọn...");
        btnChonOto.setFont(formButtonFont);
        btnChonOto.setBackground(buttonColor);
        btnChonOto.setForeground(Color.WHITE);
        btnChonOto.setBounds(310, 140, 100, 30);
        formPanel.add(btnChonOto);

        JLabel l5 = new JLabel("Số Lượng:");
        l5.setFont(formLabelFont);
        l5.setBounds(20, 180, 100, 30);
        formPanel.add(l5);
        spinnerSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        spinnerSoLuong.setFont(formFieldFont);
        spinnerSoLuong.setBounds(130, 180, 280, 30);
        formPanel.add(spinnerSoLuong);

        JLabel l6 = new JLabel("Tổng Tiền:");
        l6.setFont(formLabelFont);
        l6.setBounds(20, 220, 100, 30);
        formPanel.add(l6);
        txtTongTien = new JTextField("0");
        txtTongTien.setFont(formFieldFont);
        txtTongTien.setBounds(130, 220, 280, 30);
        txtTongTien.setEditable(false);
        formPanel.add(txtTongTien);

        JLabel l7 = new JLabel("Ngày Giao Dịch:");
        l7.setFont(formLabelFont);
        l7.setBounds(20, 260, 100, 30);
        formPanel.add(l7);
        JTextField txtNgayGD = new JTextField();
        txtNgayGD.setFont(formFieldFont);
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
        btnSave.setFont(formButtonFont);
        btnSave.setBackground(buttonColor);
        btnSave.setForeground(Color.WHITE);
        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(formButtonFont);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        btnThemKH.addActionListener(e -> controller.moCuaSoThemKhachHang());
        btnCancel.addActionListener(e -> addDialog.dispose());

        btnSave.addActionListener(e -> {
            try {
                // (Giữ nguyên logic của bạn)
                CustomerModel kh = (CustomerModel) cbKhachHang.getSelectedItem();
                StaffModel nv = (StaffModel) cbNhanVien.getSelectedItem();
                if (kh == null || nv == null || sanPhamDaChon == null) {
                    showErrorMessage("Vui lòng nhập đầy đủ thông tin!");
                    return;
                }
                String maGD = txtMaGD.getText();
                String maKH = kh.getMaKH();
                String maNV = nv.getMaNV();
                String maOTO = sanPhamDaChon.getMaOto();
                double tongTien = Double.parseDouble(txtTongTien.getText());
                String ngayGD = txtNgayGD.getText();
                int soLuong = (int) spinnerSoLuong.getValue();
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


    public void formThemKhachHang(TransactionController controller) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this.addDialog);
        addCustomerDialog = new JDialog(parentFrame, "Thêm Khách Hàng Mới", true);
        addCustomerDialog.setSize(400, 250);
        addCustomerDialog.setLocationRelativeTo(parentFrame);
        addCustomerDialog.setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(null);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // SỬA: Áp dụng Font
        JLabel l1 = new JLabel("Tên Khách Hàng:");
        l1.setFont(formLabelFont);
        l1.setBounds(20, 20, 100, 30);
        formPanel.add(l1);
        JTextField txtTenKH = new JTextField();
        txtTenKH.setFont(formFieldFont);
        txtTenKH.setBounds(130, 20, 230, 30);
        formPanel.add(txtTenKH);

        JLabel l2 = new JLabel("Số Điện Thoại:");
        l2.setFont(formLabelFont);
        l2.setBounds(20, 60, 100, 30);
        formPanel.add(l2);
        JTextField txtSdtKH = new JTextField();
        txtSdtKH.setFont(formFieldFont);
        txtSdtKH.setBounds(130, 60, 230, 30);
        formPanel.add(txtSdtKH);

        JLabel l3 = new JLabel("Địa Chỉ:");
        l3.setFont(formLabelFont);
        l3.setBounds(20, 100, 100, 30);
        formPanel.add(l3);
        JTextField txtDiaChi = new JTextField();
        txtDiaChi.setFont(formFieldFont);
        txtDiaChi.setBounds(130, 100, 230, 30);
        formPanel.add(txtDiaChi);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Lưu");
        btnSave.setFont(formButtonFont);
        btnSave.setBackground(buttonColor);
        btnSave.setForeground(Color.WHITE);
        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(formButtonFont);

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
        editDialog.setSize(450, 530);
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);

        // SỬA: Áp dụng Font
        JLabel l1 = new JLabel("Mã Giao Dịch:");
        l1.setFont(formLabelFont);
        l1.setBounds(20, 20, 100, 30);
        formPanel.add(l1);
        JTextField txtMaGD = new JTextField();
        txtMaGD.setFont(formFieldFont);
        txtMaGD.setBounds(130, 20, 280, 30);
        txtMaGD.setText(tx.getMaGD());
        txtMaGD.setEditable(false);
        txtMaGD.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtMaGD);

        JLabel l2 = new JLabel("Khách Hàng:");
        l2.setFont(formLabelFont);
        l2.setBounds(20, 60, 100, 30);
        formPanel.add(l2);
        JComboBox<CustomerModel> cbKH = new JComboBox<>(dsKhachHang.toArray(new CustomerModel[0]));
        cbKH.setFont(formFieldFont);
        for (CustomerModel kh : dsKhachHang) {
            if (kh.getMaKH().equals(khachHangDaChon.getMaKH())) {
                cbKH.setSelectedItem(kh);
                break;
            }
        }
        cbKH.setBounds(130, 60, 280, 30);
        formPanel.add(cbKH);

        JLabel l_diaChi = new JLabel("Địa chỉ:");
        l_diaChi.setFont(formLabelFont);
        l_diaChi.setBounds(20, 100, 100, 30);
        formPanel.add(l_diaChi);
        final JTextField txtDiaChi = new JTextField();
        txtDiaChi.setFont(formFieldFont);
        txtDiaChi.setBounds(130, 100, 280, 30);
        txtDiaChi.setText(khachHangDaChon.getDcKH());
        formPanel.add(txtDiaChi);
        cbKH.addActionListener(e -> {
            CustomerModel selectedCustomer = (CustomerModel) cbKH.getSelectedItem();
            if (selectedCustomer != null) {
                txtDiaChi.setText(selectedCustomer.getDcKH());
            }
        });

        JLabel l3 = new JLabel("Nhân Viên:");
        l3.setFont(formLabelFont);
        l3.setBounds(20, 140, 100, 30);
        formPanel.add(l3);
        JComboBox<StaffModel> cbNV = new JComboBox<>(dsNhanVien.toArray(new StaffModel[0]));
        cbNV.setFont(formFieldFont);
        for (StaffModel nv : dsNhanVien) {
            if (nv.getMaNV().equals(nhanVienDaChon.getMaNV())) {
                cbNV.setSelectedItem(nv);
                break;
            }
        }
        cbNV.setBounds(130, 140, 280, 30);
        formPanel.add(cbNV);

        JLabel l4 = new JLabel("Ô tô:");
        l4.setFont(formLabelFont);
        l4.setBounds(20, 180, 100, 30);
        formPanel.add(l4);
        JTextField txtOto = new JTextField();
        txtOto.setFont(formFieldFont);
        txtOto.setBounds(130, 180, 280, 30);
        txtOto.setText(sanPhamDaChon != null ? sanPhamDaChon.getTenOto() : "Không tìm thấy");
        txtOto.setEditable(false);
        txtOto.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtOto);

        JLabel l5 = new JLabel("Số Lượng:");
        l5.setFont(formLabelFont);
        l5.setBounds(20, 220, 100, 30);
        formPanel.add(l5);
        JTextField txtSoLuong = new JTextField();
        txtSoLuong.setFont(formFieldFont);
        txtSoLuong.setBounds(130, 220, 280, 30);
        txtSoLuong.setText(String.valueOf(tx.getSoLuong()));
        txtSoLuong.setEditable(false);
        txtSoLuong.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtSoLuong);

        JLabel l6 = new JLabel("Tổng Tiền:");
        l6.setFont(formLabelFont);
        l6.setBounds(20, 260, 100, 30);
        formPanel.add(l6);
        JTextField txtTien = new JTextField();
        txtTien.setFont(formFieldFont);
        txtTien.setBounds(130, 260, 280, 30);
        txtTien.setText(String.valueOf(tx.getTongtien())); // Giữ nguyên logic của bạn
        txtTien.setEditable(false);
        txtTien.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtTien);

        JLabel l7 = new JLabel("Ngày Giao Dịch:");
        l7.setFont(formLabelFont);
        l7.setBounds(20, 300, 100, 30);
        formPanel.add(l7);
        JTextField txtNgayGD = new JTextField();
        txtNgayGD.setFont(formFieldFont);
        txtNgayGD.setBounds(130, 300, 280, 30);
        txtNgayGD.setText(tx.getNgayGD());
        formPanel.add(txtNgayGD);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Lưu");
        btnSave.setFont(formButtonFont);
        btnSave.setBackground(buttonColor);
        btnSave.setForeground(Color.WHITE);
        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(formButtonFont);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        btnCancel.addActionListener(e -> editDialog.dispose());

        btnSave.addActionListener(e -> {
            try {
                // (Giữ nguyên logic của bạn)
                String maGD = txtMaGD.getText();
                String ngayGDMoi = txtNgayGD.getText();
                double tongTien = tx.getTongtien();
                String maOto = tx.getMaOTO();
                int soLuong = tx.getSoLuong();
                CustomerModel khMoi = (CustomerModel) cbKH.getSelectedItem();
                StaffModel nvMoi = (StaffModel) cbNV.getSelectedItem();
                String diaChiMoi = txtDiaChi.getText();
                khMoi.setDcKH(diaChiMoi);
                TransactionModel txMoi = new TransactionModel(maGD, khMoi.getMaKH(), nvMoi.getMaNV(), maOto, tongTien, ngayGDMoi, soLuong);

                controller.suaGiaoDich(txMoi, khMoi); // Giữ nguyên tên hàm của bạn
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

    //chi tiết giao dịch (Giữ nguyên tên hàm của bạn)
    public void showDetail(TransactionModel tx, CustomerModel kh, StaffModel nv, ProductModel sp) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog detailDialog = new JDialog(parentFrame, "Chi Tiết Giao Dịch", true);
        detailDialog.setSize(450, 520);
        detailDialog.setLocationRelativeTo(this);
        detailDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String tenKH = (kh != null) ? kh.getTenKH() : "Không tìm thấy (" + tx.getMaKH() + ")";
        String sdtKH = (kh != null) ? kh.getSdtKH() : "Chưa có sdt";
        String diaChi = (kh != null) ? kh.getDcKH() : "chưa thêm địa chỉ";
        String tenNV = (nv != null) ? nv.getTenNV() : "Không tìm thấy (" + tx.getMaNV() + ")";
        String tenOto = (sp != null) ? sp.getTenOto() : "Không tìm thấy (" + tx.getMaOTO() + ")";

        String[] labels = {
                "Mã Giao Dịch:", "Khách Hàng:", "Địa chỉ:", "Số Điện Thoại:",
                "Nhân Viên:", "Sản Phẩm:", "Số Lượng:", "Tổng Tiền:", "Ngày Giao Dịch:"
        };
        String[] values = {
                tx.getMaGD(), tenKH, diaChi, sdtKH, tenNV, tenOto,
                String.valueOf(tx.getSoLuong()),
                String.valueOf(tx.getTongtien()), // Giữ nguyên logic của bạn
                tx.getNgayGD()
        };

        //form hiển thị
        int y_pos = 20;
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(formLabelFont); // SỬA: Áp dụng Font
            label.setBounds(20, y_pos, 120, 30);
            formPanel.add(label);

            JTextField textField = new JTextField(values[i]);
            textField.setFont(formFieldFont); // SỬA: Áp dụng Font
            textField.setBounds(150, y_pos, 250, 30);
            textField.setEditable(false);
            textField.setBackground(Color.LIGHT_GRAY);
            formPanel.add(textField);

            y_pos += 40;
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnClose = new JButton("Đóng");
        btnClose.setFont(formButtonFont); // SỬA: Áp dụng Font
        buttonPanel.add(btnClose);
        btnClose.addActionListener(e -> detailDialog.dispose());

        detailDialog.add(formPanel, BorderLayout.CENTER);
        detailDialog.add(buttonPanel, BorderLayout.SOUTH);
        detailDialog.setVisible(true);
    }


    public void closeAddCustomerDialog() {
        if (addCustomerDialog != null) {
            addCustomerDialog.dispose();
        }

    }

    // Giữ nguyên tên hàm của bạn
    public void refreshCustomer(List<CustomerModel> dsKhachHang, CustomerModel khachHangMoi) {
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

    public TransactionModel getSelectedTransaction() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một giao dịch để thực hiện!", "Chưa chọn hàng", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        try {
            int modelIndex = transactionTable.convertRowIndexToModel(selectedRow);
            return this.fullTransactionList.get(modelIndex);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Không thể lấy dữ liệu từ hàng đã chọn.");
            return null;
        }
    }

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