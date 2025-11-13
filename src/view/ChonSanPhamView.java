package view;

import controller.TransactionController;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.ProductModel;

//file này là file con của transactionview tách ra cho thuận tiện

public class ChonSanPhamView extends JDialog {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private TransactionController controller;
    

    public ChonSanPhamView(JFrame parent, TransactionController controller, List<ProductModel> dsSanPham) {
        super(parent, "Chọn Sản Phẩm Ô Tô", true);
        this.controller = controller;

        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        String[] columnNames = {"Mã Ô Tô", "Tên Ô Tô", "Giá Bán", "Số Lượng Tồn"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(tableModel);
        productTable.getColumnModel().getColumn(2).setCellRenderer(new PriceRenderer());
        updateTable(dsSanPham);

        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Hủy");
        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> setVisible(false));

        btnOK.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //lấy sản phẩm từ danh sách
            ProductModel selectedProduct = dsSanPham.get(productTable.convertRowIndexToModel(selectedRow));
            controller.setSanPhamSauKhiChon(selectedProduct);
            setVisible(false);
        });
    }

    public void updateTable(List<ProductModel> dsSanPham) {
        tableModel.setRowCount(0);
        for (ProductModel sp : dsSanPham) {
            Object[] row = {
                    sp.getMaOto(),
                    sp.getTenOto(),
                    sp.getGia(),
                    sp.getSoLuong()
            };
            tableModel.addRow(row);
        }
    }
    class PriceRenderer extends DefaultTableCellRenderer {
        private NumberFormat formatter;

        public PriceRenderer() {
            Locale localeVN = new Locale("vi", "VN");
            formatter = NumberFormat.getNumberInstance(localeVN);
            setHorizontalAlignment(JLabel.RIGHT);
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