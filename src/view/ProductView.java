package view;

import controller.ProductController;
import dao.ProductDAO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.ProductModel;
import java.awt.event.ActionEvent;


public class ProductView extends JPanel {
    private JButton btnThongKe;
    private JButton btnSanPham;
    private JButton btnKhachHang;
    private JButton btnNhanVien;
    private JButton btnGiaoDich;
    private JButton btnSua;
    private JButton btnDong;
    private JDialog addDialog;
    private JDialog editDialog;
    private JTable dialogTable;
    private DefaultTableModel dialogTableModel;
    private JTextField txtMaOtoEdit, txtTenOtoEdit, txtLoaiOtoEdit, txtGiaEdit, txtSoLuongEdit, txtMaHangEdit;
    private JButton jButton_add;
    private JButton jButton_edit;
    private JButton jButton_delete;
    private JTextField jTextField_search;
    private JButton jButton_search;
    private JTextArea txtMoTaEdit;
    private ProductController controller;
    private JTable carTable;
    private DefaultTableModel tableModel;
    private JButton jButton_reset;
    

    public ProductView() {
        this.init();
    }

    public void init() {
        Font font = new Font("Arial", Font.BOLD, 40);
        Font fonts = new Font("Arial", Font.BOLD, 17);

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        JPanel jPanel_right = new JPanel(new BorderLayout());
        jPanel_right.setBackground(Color.WHITE);

        JLabel jLabel_header = new JLabel("QUẢN LÝ SẢN PHẨM", SwingConstants.CENTER);
        jLabel_header.setForeground(Color.BLACK);
        jLabel_header.setFont(font);
        jLabel_header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel jPanel_action = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        jPanel_action.setBackground(Color.WHITE);

        jButton_add = new JButton("Thêm sản phẩm");
        jButton_edit = new JButton("Sửa");
        jButton_delete = new JButton("Xóa");
        jTextField_search = new JTextField(20);
        jButton_search = new JButton("Tìm kiếm");
        jButton_reset = new JButton("Làm mới");

        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        for (JButton btn : new JButton[]{jButton_add, jButton_edit, jButton_delete, jButton_search, jButton_reset}) {
            btn.setFont(buttonFont);
            btn.setBackground(new Color(65, 105, 225));
            btn.setForeground(Color.WHITE);
        }

        jPanel_action.add(jButton_add);
        jPanel_action.add(jButton_edit);
        jPanel_action.add(jButton_delete);
        jPanel_action.add(jButton_reset);
        jPanel_action.add(jTextField_search);
        jPanel_action.add(jButton_search);

        jButton_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi hàm làm mới dữ liệu
                controller.hienThiDB();
                
                // (Tùy chọn) Xóa nội dung ô tìm kiếm
                jTextField_search.setText(""); 
                
                // (Tùy chọn) Hiển thị thông báo
                showSuccessMessage("Đã tải lại danh sách sản phẩm."); 
            }
        });

        JPanel jPanel_north_wrapper = new JPanel(new BorderLayout());
        jPanel_north_wrapper.setBackground(Color.WHITE);
        jPanel_north_wrapper.add(jLabel_header, BorderLayout.NORTH);
        jPanel_north_wrapper.add(jPanel_action, BorderLayout.CENTER);

        

        jPanel_right.add(jPanel_north_wrapper, BorderLayout.NORTH);

        String[] columnNames = {"Mã Ô Tô", "Tên Ô Tô", "Giá", "Loại Ô Tô", "Số Lượng", "Mô Tả", "Mã Hãng", "Số Lượt Bán"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        carTable = new JTable(tableModel);
        carTable.setFont(new Font("Arial", Font.PLAIN, 14));
        carTable.setRowHeight(25);
        carTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        carTable.getTableHeader().setBackground(new Color(52, 73, 94));
        carTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(carTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel_right.add(scrollPane, BorderLayout.CENTER);

        this.add(jPanel_right, BorderLayout.CENTER);

        // Khởi tạo controller (khi JPanel được load)
        controller = new ProductController(this, new ProductDAO());
        // controller.hienThiDB();
    }

    public void addAddCarListener(ActionListener listener) { jButton_add.addActionListener(listener); }
    public void addDeleteCarListener(ActionListener listener) { jButton_delete.addActionListener(listener); }
    public void addEditCarListener(ActionListener listener) { jButton_edit.addActionListener(listener); }
    public void addSearchCarListener(ActionListener listener) {
        jButton_search.addActionListener(listener);
        jTextField_search.addActionListener(listener);
    }

    public void hienthidulieu(List<ProductModel> carList) {
        tableModel.setRowCount(0);
        for (ProductModel car : carList) {
            Object[] rowData = {
            car.getMaOto(),
            car.getTenOto(),
            car.getGia(),
            car.getLoaiOto(),
            car.getSoLuong(),
            car.getMoTa(),
            car.getMaHang(),
            car.getSoLuotBan()
            };
            tableModel.addRow(rowData);
        }
    }

    public void formThemsp(ProductController controller) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        addDialog = new JDialog(parentFrame, "Thêm sản phẩm Ô tô", true);
        addDialog.setSize(400, 500);
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel l2 = new JLabel("Tên Ô tô:"); l2.setBounds(20, 20, 100, 30); formPanel.add(l2);
        JTextField txtTenOto = new JTextField(); txtTenOto.setBounds(130, 20, 230, 30); formPanel.add(txtTenOto);

        JLabel l3 = new JLabel("Loại Ô tô:"); l3.setBounds(20, 60, 100, 30); formPanel.add(l3);
        JTextField txtLoaiOto = new JTextField(); txtLoaiOto.setBounds(130, 60, 230, 30); formPanel.add(txtLoaiOto);

        JLabel l4 = new JLabel("Giá:"); l4.setBounds(20, 100, 100, 30); formPanel.add(l4);
        JTextField txtGia = new JTextField(); txtGia.setBounds(130, 100, 230, 30); formPanel.add(txtGia);

        JLabel l5 = new JLabel("Số lượng:"); l5.setBounds(20, 140, 100, 30); formPanel.add(l5);
        JTextField txtSoLuong = new JTextField(); txtSoLuong.setBounds(130, 140, 230, 30); formPanel.add(txtSoLuong);

        JLabel l6 = new JLabel("Mã Hãng:"); l6.setBounds(20, 180, 100, 30); formPanel.add(l6);
        JTextField txtMaHang = new JTextField(); txtMaHang.setBounds(130, 180, 230, 30); formPanel.add(txtMaHang);

        JLabel l7 = new JLabel("Mô tả:"); l7.setBounds(20, 220, 100, 30); formPanel.add(l7);
        JTextArea txtMoTa = new JTextArea();
        txtMoTa.setLineWrap(true); txtMoTa.setWrapStyleWord(true);
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa); scrollMoTa.setBounds(130, 220, 230, 150); formPanel.add(scrollMoTa);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        btnCancel.addActionListener(e -> addDialog.dispose());

        btnSave.addActionListener(e -> controller.them(
                txtTenOto.getText(),
                txtLoaiOto.getText(),
                txtGia.getText(),
                txtSoLuong.getText(),
                txtMaHang.getText(),
                txtMoTa.getText()
        ));

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);
        addDialog.setVisible(true);
    }

    private JPanel createEditFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã ô tô*:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtMaOtoEdit = new JTextField();
        txtMaOtoEdit.setEditable(false);
        txtMaOtoEdit.setBackground(new Color(240, 240, 240));
        formPanel.add(txtMaOtoEdit, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Tên ô tô*:"), gbc);
        gbc.gridx = 1;
        txtTenOtoEdit = new JTextField();
        formPanel.add(txtTenOtoEdit, gbc);


        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Loại ô tô*:"), gbc);
        gbc.gridx = 1;
        txtLoaiOtoEdit = new JTextField();
        formPanel.add(txtLoaiOtoEdit, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Giá*:"), gbc);
        gbc.gridx = 1;
        txtGiaEdit = new JTextField();
        formPanel.add(txtGiaEdit, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Số lượng*:"), gbc);
        gbc.gridx = 1;
        txtSoLuongEdit = new JTextField();
        formPanel.add(txtSoLuongEdit, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Mã hãng*:"), gbc);
        gbc.gridx = 1;
        txtMaHangEdit = new JTextField();
        formPanel.add(txtMaHangEdit, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
        txtMoTaEdit = new JTextArea(4, 20);
        txtMoTaEdit.setLineWrap(true);
        txtMoTaEdit.setWrapStyleWord(true);
        JScrollPane scrollMoTa = new JScrollPane(txtMoTaEdit);
        formPanel.add(scrollMoTa, gbc);

        return formPanel;
    }
    
    public void formSuasp(ProductController controller) {
        if (editDialog != null && editDialog.isVisible()) {
            editDialog.toFront();
            return;
        }

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        editDialog = new JDialog(parentFrame, "Sửa thông tin sản phẩm", true);
        editDialog.setSize(850, 650);
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new BorderLayout(10, 10));

        // 1. Tạo Panel Form
        JPanel formPanel = createEditFormPanel();
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));
        formPanel.setPreferredSize(new Dimension(350, 0));

        // 2. Tạo Panel Bảng
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Chọn xe để sửa (nháy đúp chuột)"));

        String[] dialogColumnNames = {"Mã Ô Tô", "Tên", "Giá", "Loại", "SL", "Mô Tả", "Mã Hãng"};
        dialogTableModel = new DefaultTableModel(dialogColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dialogTable = new JTable(dialogTableModel);
        dialogTable.getColumnModel().getColumn(2).setCellRenderer(new PriceRenderer());

        JScrollPane scrollPane = new JScrollPane(dialogTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSua = createStyledButton("Lưu thay đổi", new Color(40, 167, 69));
        btnDong = createStyledButton("Đóng", new Color(108, 117, 125));
        buttonPanel.add(btnSua);
        buttonPanel.add(btnDong);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tablePanel);


        editDialog.add(splitPane, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);

        loadCarDataToDialogTable();
        addDialogEventListeners(controller);

        editDialog.setVisible(true);
    }

    // public JPanel formSuasp(ProductController controller) {
    //     JPanel formPanel = new JPanel(new GridBagLayout());
    //     formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    //     GridBagConstraints gbc = new GridBagConstraints();
    //     gbc.insets = new Insets(5, 5, 5, 5);
    //     gbc.fill = GridBagConstraints.HORIZONTAL;
    //     gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
    //     formPanel.add(new JLabel("Mã Ô tô:"), gbc);
    //     gbc.gridx = 1; gbc.weightx = 1.0;
    //     txtMaOtoEdit = new JTextField();
    //     txtMaOtoEdit.setEditable(false);
    //     txtMaOtoEdit.setBackground(Color.LIGHT_GRAY);
    //     formPanel.add(txtMaOtoEdit, gbc);

    //     gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
    //     formPanel.add(new JLabel("Tên Ô tô:"), gbc);
    //     gbc.gridx = 1; gbc.weightx = 1.0;
    //     txtTenOtoEdit = new JTextField();
    //     formPanel.add(txtTenOtoEdit, gbc);

    //     gbc.gridx = 0; gbc.gridy = 2;
    //     formPanel.add(new JLabel("Loại Ô tô:"), gbc);
    //     gbc.gridx = 1;
    //     txtLoaiOtoEdit = new JTextField();
    //     formPanel.add(txtLoaiOtoEdit, gbc);

    //     gbc.gridx = 0; gbc.gridy = 3;
    //     formPanel.add(new JLabel("Giá:"), gbc);
    //     gbc.gridx = 1;
    //     txtGiaEdit = new JTextField();
    //     formPanel.add(txtGiaEdit, gbc);

    //     gbc.gridx = 0; gbc.gridy = 4;
    //     formPanel.add(new JLabel("Số lượng:"), gbc);
    //     gbc.gridx = 1;
    //     txtSoLuongEdit = new JTextField();
    //     formPanel.add(txtSoLuongEdit, gbc);

    //     gbc.gridx = 0; gbc.gridy = 5;
    //     formPanel.add(new JLabel("Mã Hãng:"), gbc);
    //     gbc.gridx = 1;
    //     txtMaHangEdit = new JTextField();
    //     formPanel.add(txtMaHangEdit, gbc);

    //     gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.NORTHWEST;
    //     formPanel.add(new JLabel("Mô tả:"), gbc);
    //     gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
    //     txtMoTaEdit = new JTextArea(5, 20);
    //     txtMoTaEdit.setLineWrap(true); txtMoTaEdit.setWrapStyleWord(true);
    //     JScrollPane moTaScroll = new JScrollPane(txtMoTaEdit);
    //     formPanel.add(moTaScroll, gbc);


    //     return formPanel;
    // }

    // public void openEditDialog(ProductController controller) {
    //     // 1. Kiểm tra xem người dùng đã chọn xe chưa
    //     ProductModel selectedCar = getSelectedOto();
    //     if (selectedCar == null) {
    //         showErrorMessage("Vui lòng chọn một sản phẩm trong bảng để sửa!");
    //         return;
    //     }

    //     // 2. Tạo JDialog
    //     JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    //     editDialog = new JDialog(parentFrame, "Sửa sản phẩm Ô tô", true);
    //     editDialog.setSize(450, 550); // Đặt kích thước
    //     editDialog.setLocationRelativeTo(this);
    //     editDialog.setLayout(new BorderLayout());

    //     // 3. Gọi hàm formSuasp() của bạn để lấy JPanel
    //     JPanel formPanel = formSuasp(controller);

    //     // 4. Điền thông tin của xe đã chọn vào các ô JTextField
    //     txtMaOtoEdit.setText(selectedCar.getMaOto());
    //     txtTenOtoEdit.setText(selectedCar.getTenOto());
    //     txtLoaiOtoEdit.setText(selectedCar.getLoaiOto());
    //     txtGiaEdit.setText(selectedCar.getGia().toString());
    //     txtSoLuongEdit.setText(String.valueOf(selectedCar.getSoLuong()));
    //     txtMaHangEdit.setText(selectedCar.getMaHang());
    //     txtMoTaEdit.setText(selectedCar.getMoTa());
        
    //     // 5. Tạo các nút bấm cho dialog Sửa
    //     JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    //     JButton btnUpdate = createStyledButton("Cập nhật", new Color(0, 123, 255)); 
    //     JButton btnCancel = createStyledButton("Hủy", new Color(108, 117, 125));
    //     buttonPanel.add(btnUpdate);
    //     buttonPanel.add(btnCancel);

    //     // 6. Gán sự kiện cho các nút
    //     btnCancel.addActionListener(e -> editDialog.dispose());
        
    //     btnUpdate.addActionListener(e -> {
    //         // Gọi hàm sua() trong controller
    //         controller.sua(
    //             txtMaOtoEdit.getText(),
    //             txtTenOtoEdit.getText(),
    //             txtLoaiOtoEdit.getText(),
    //             txtGiaEdit.getText(),
    //             txtSoLuongEdit.getText(),
    //             txtMaHangEdit.getText(),
    //             txtMoTaEdit.getText()
    //         );
    //     });

    //     // 7. Thêm panel và hiển thị dialog
    //     editDialog.add(formPanel, BorderLayout.CENTER);
    //     editDialog.add(buttonPanel, BorderLayout.SOUTH);
    //     editDialog.setVisible(true); // <-- DÒNG QUAN TRỌNG NHẤT
    // }   
    private JButton createStyledButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }
    private void loadCarDataToDialogTable() {
        dialogTableModel.setRowCount(0);
        List<ProductModel> carList = ProductDAO.getInstance().selectAll();
        for (ProductModel car : carList) {
            Object[] rowData = {
                    car.getMaOto(),
                    car.getTenOto(),
                    car.getGia(),
                    car.getLoaiOto(),
                    car.getSoLuong(),
                    car.getMoTa(),
                    car.getMaHang()
            };
            dialogTableModel.addRow(rowData);
        }
    }
    private void addDialogEventListeners(ProductController controller) {
        btnDong.addActionListener(e -> editDialog.dispose());
        btnSua.addActionListener(e -> controller.sua(
                txtMaOtoEdit.getText(),
                txtTenOtoEdit.getText(),
                txtLoaiOtoEdit.getText(),
                txtGiaEdit.getText(),
                txtSoLuongEdit.getText(),
                txtMaHangEdit.getText(),
                txtMoTaEdit.getText()
        ));

        dialogTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = dialogTable.getSelectedRow();
                    if (row != -1) {
                        txtMaOtoEdit.setText(dialogTableModel.getValueAt(row, 0).toString());
                        txtTenOtoEdit.setText(dialogTableModel.getValueAt(row, 1).toString());
                        txtGiaEdit.setText(dialogTableModel.getValueAt(row, 2).toString());
                        txtLoaiOtoEdit.setText(dialogTableModel.getValueAt(row, 3).toString());
                        txtSoLuongEdit.setText(dialogTableModel.getValueAt(row, 4).toString());
                        txtMoTaEdit.setText(dialogTableModel.getValueAt(row, 5).toString());
                        txtMaHangEdit.setText(dialogTableModel.getValueAt(row, 6).toString());
                    }
                }
            }
        });
    }
    public void closeAddDialog() {
        if (addDialog != null) addDialog.dispose();
    }
    public void closeEditDialog() {
        if (editDialog != null) editDialog.dispose();
    }
    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    // public ProductModel getSelectedOto() {
    //     int selectedRow = carTable.getSelectedRow();
    //     if (selectedRow == -1) {
    //         return null; // chưa chọn hàng nào
    //     }
    // String maOto = carTable.getValueAt(selectedRow, 0).toString();
    // String tenOto = carTable.getValueAt(selectedRow, 1).toString();
    // double gia = Double.parseDouble(carTable.getValueAt(selectedRow, 2).toString());
    // String loaiOto = carTable.getValueAt(selectedRow, 3).toString();
    // int soLuong = Integer.parseInt(carTable.getValueAt(selectedRow, 4).toString());
    // String moTa = carTable.getValueAt(selectedRow, 5).toString();
    // String maHang = carTable.getValueAt(selectedRow, 6).toString();
    // int soLuotBan = Integer.parseInt(carTable.getValueAt(selectedRow, 7).toString());

    // return new ProductModel(gia, loaiOto, maOto, moTa, soLuong, tenOto, soLuotBan, maHang);
    // }

    public ProductModel getSelectedOto() {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }

        try {
            String maOto = carTable.getValueAt(selectedRow, 0).toString();
            String tenOto = carTable.getValueAt(selectedRow, 1).toString();

            BigDecimal gia = (BigDecimal) carTable.getValueAt(selectedRow, 2);

            String loaiOto = carTable.getValueAt(selectedRow, 3).toString();
            int soLuong = Integer.parseInt(carTable.getValueAt(selectedRow, 4).toString());
            String moTa = carTable.getValueAt(selectedRow, 5).toString();
            String maHang = carTable.getValueAt(selectedRow, 6).toString();
            int soLuotBan = Integer.parseInt(carTable.getValueAt(selectedRow, 7).toString());

            return new ProductModel(gia, loaiOto, maOto, moTa, soLuong, tenOto, soLuotBan, maHang);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Lỗi khi lấy dữ liệu xe: " + e.getMessage());
            return null;
        }
    }
    public String getSearchKeyword() {
        return jTextField_search.getText();
    }
    class PriceRenderer extends DefaultTableCellRenderer {
        private NumberFormat formatter;

        public PriceRenderer() {
            Locale localeVN = new Locale("vi", "VN");
            formatter = NumberFormat.getNumberInstance(localeVN);
            setHorizontalAlignment(SwingConstants.RIGHT);
        }

        @Override
        public void setValue(Object value) {
            if (value instanceof Number) {
                setText(formatter.format(value));
            } else {
                super.setValue(value);
            }
        }
    }
}
