package view;

import controller.CustomerController;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import model.CustomerModel;

public class CustomerView extends JPanel {
    private JButton jButton_add;
    private JButton jButton_edit;
    private JButton jButton_delete;
    private JTextField jTextField_search;
    private JButton jButton_search;

    private JTable customerTable;
    private DefaultTableModel tableModel;

    private JDialog addDialog;
    private JDialog editDialog;

    private CustomerController controller;

    public CustomerView() {
        this.init();
    }

    public void init() {

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JPanel jPanel_right = new JPanel(new BorderLayout());
        jPanel_right.setBackground(Color.WHITE);
        JLabel jLabel_header = new JLabel("QUẢN LÝ KHÁCH HÀNG", SwingConstants.CENTER);
        jLabel_header.setForeground(Color.BLACK);
        jLabel_header.setFont(new Font("Arial", Font.BOLD, 40));
        jLabel_header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        JPanel jPanel_action = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        jPanel_action.setBackground(Color.WHITE);
        jButton_add = new JButton("Thêm Khách Hàng");
        jButton_edit = new JButton("Sửa");
        jButton_delete = new JButton("Xóa");
        jTextField_search = new JTextField(20);
        jButton_search = new JButton("Tìm kiếm");
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        for (JButton btn : new JButton[]{jButton_add, jButton_edit, jButton_delete, jButton_search}) {
            btn.setFont(buttonFont);
            btn.setBackground(new Color(65, 105, 225));
            btn.setForeground(Color.WHITE);
        }
        jPanel_action.add(jButton_add);
        jPanel_action.add(jButton_edit);
        jPanel_action.add(jButton_delete);
        jPanel_action.add(jTextField_search);
        jPanel_action.add(jButton_search);
        JPanel jPanel_north_wrapper = new JPanel(new BorderLayout());
        jPanel_north_wrapper.setBackground(Color.WHITE);
        jPanel_north_wrapper.add(jLabel_header, BorderLayout.NORTH);
        jPanel_north_wrapper.add(jPanel_action, BorderLayout.CENTER);
        jPanel_right.add(jPanel_north_wrapper, BorderLayout.NORTH);
        String[] columnNames = {"Mã KH", "Tên Khách Hàng", "Địa Chỉ", "SĐT", "Tổng Chi Tiêu", "Số Lần Mua"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        customerTable = new JTable(tableModel);
        customerTable.getColumnModel().getColumn(4).setCellRenderer(new PriceRenderer());
        customerTable.setFont(new Font("Arial", Font.PLAIN, 14));
        customerTable.setRowHeight(25);
        customerTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        customerTable.getTableHeader().setBackground(new Color(52, 73, 94));
        customerTable.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel_right.add(scrollPane, BorderLayout.CENTER);
        this.add(jPanel_right, BorderLayout.CENTER);

        controller = new CustomerController(this);
    }

    public void addAddCustomerListener(ActionListener listener) {
        jButton_add.addActionListener(listener);
    }
    public void addDeleteCustomerListener(ActionListener listener) {
        jButton_delete.addActionListener(listener);
    }
    public void addEditCustomerListener(ActionListener listener) {
        jButton_edit.addActionListener(listener);
    }
    public void addSearchCustomerListener(ActionListener listener) {
        jButton_search.addActionListener(listener);
        jTextField_search.addActionListener(listener);
    }

    public void hienthidulieu(List<CustomerModel> customerList) {
        tableModel.setRowCount(0);
        for (CustomerModel kh : customerList) {
            Object[] rowData = {
                    kh.getMaKH(),
                    kh.getTenKH(),
                    kh.getDcKH(),
                    kh.getSdtKH(),
                    kh.getTongChiTieu(),
                    kh.getSoLanMua()
            };
            tableModel.addRow(rowData);
        }
    }

    public void formThemKH(CustomerController controller) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        addDialog = new JDialog(parentFrame, "Thêm Khách Hàng", true);
        addDialog.setSize(400, 250);
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        formPanel.add(new JLabel("Tên KH:"));
        JTextField txtTenKH = new JTextField();
        formPanel.add(txtTenKH);

        formPanel.add(new JLabel("Địa chỉ:"));
        JTextField txtDcKH = new JTextField();
        formPanel.add(txtDcKH);

        formPanel.add(new JLabel("SĐT:"));
        JTextField txtSdtKH = new JTextField();
        formPanel.add(txtSdtKH);

        formPanel.add(new JLabel("")); // Label rỗng

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        btnCancel.addActionListener(e -> addDialog.dispose());

        btnSave.addActionListener(e -> controller.them(
                txtTenKH.getText(),
                txtDcKH.getText(),
                txtSdtKH.getText()
        ));

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);
        addDialog.setVisible(true);
    }

    public void formSuaKH(CustomerController controller, CustomerModel kh) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        editDialog = new JDialog(parentFrame, "Sửa Khách Hàng", true);
        editDialog.setSize(400, 300);
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Mã KH:"));
        JTextField txtMaKH = new JTextField(kh.getMaKH());
        txtMaKH.setEditable(false);
        txtMaKH.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtMaKH);

        formPanel.add(new JLabel("Tên KH:"));
        JTextField txtTenKH = new JTextField(kh.getTenKH());
        formPanel.add(txtTenKH);

        formPanel.add(new JLabel("Địa chỉ:"));
        JTextField txtDcKH = new JTextField(kh.getDcKH());
        formPanel.add(txtDcKH);

        formPanel.add(new JLabel("SĐT:"));
        JTextField txtSdtKH = new JTextField(kh.getSdtKH());
        formPanel.add(txtSdtKH);

        formPanel.add(new JLabel(""));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        btnCancel.addActionListener(e -> editDialog.dispose());

        btnSave.addActionListener(e -> controller.sua(
                txtMaKH.getText(),
                txtTenKH.getText(),
                txtDcKH.getText(),
                txtSdtKH.getText()
        ));

        editDialog.add(formPanel, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);
        editDialog.setVisible(true);
    }

    public String getSearchKeyword() {
        return jTextField_search.getText();
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

    public CustomerModel getSelectedCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        try {
            String maKH = customerTable.getValueAt(selectedRow, 0).toString();
            String tenKH = customerTable.getValueAt(selectedRow, 1).toString();
            String dcKH = customerTable.getValueAt(selectedRow, 2).toString();
            String sdtKH = customerTable.getValueAt(selectedRow, 3).toString();
            Object tongChiTieuValue = customerTable.getValueAt(selectedRow, 4);
            Object soLanMuaValue = customerTable.getValueAt(selectedRow, 5);

            BigDecimal tongChiTieu = (BigDecimal) tongChiTieuValue;
            int soLanMua = (Integer) soLanMuaValue;

            return new CustomerModel(maKH, tenKH, dcKH, sdtKH, tongChiTieu, soLanMua);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Lỗi khi lấy dữ liệu khách hàng: " + e.getMessage());
            return null;
        }
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
