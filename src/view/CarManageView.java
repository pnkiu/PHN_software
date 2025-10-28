package view;

import controller.CarManageController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.Oto;

public class CarManageView extends JFrame {
    private JTable table;
    public CarManageView(){
        this.init();
    }

    private void init() {
        this.setTitle("Car Manage Software");
        this.setSize(1050, 620);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        
        CarManageController controller = new CarManageController(this);
        Font font_title = new Font("Segoe UI", Font.BOLD, 26);
        JLabel jLabel_title = new JLabel("HỆ THỐNG QUẢN LÝ CỬA HÀNG BÁN XE Ô TÔ");
        jLabel_title.setHorizontalAlignment(SwingConstants.CENTER); // can giua
        jLabel_title.setFont(font_title);
        jLabel_title.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel_title.setForeground(Color.RED);
        jLabel_title.setBackground(Color.BLACK); 
        jLabel_title.setOpaque(true);
        jLabel_title.setBorder(new EmptyBorder(15, 0, 15, 0));

        Font font_button = new Font("Segoe UI", Font.BOLD, 15);
        JButton jbutton_them = new JButton("Thêm");
        jbutton_them.setForeground(Color.RED);
        jbutton_them.setBackground(Color.BLACK);
        jbutton_them.setFont(font_button);
        JButton jbutton_sua = new JButton("Sửa");
        jbutton_sua.setForeground(Color.RED);
        jbutton_sua.setBackground(Color.BLACK);
        jbutton_sua.setFont(font_button);
        JButton jbutton_xoa = new JButton("Xóa");
        jbutton_xoa.addActionListener(controller);
        jbutton_xoa.setForeground(Color.RED);
        jbutton_xoa.setBackground(Color.BLACK);
        jbutton_xoa.setFont(font_button);
        
        JPanel jPanel_title = new JPanel();
        jPanel_title.setLayout(new BorderLayout());
        jPanel_title.setBorder(new EmptyBorder(10, 0, 0, 0)); // padding 
        jPanel_title.setBackground(new Color(192,192,192));

        JPanel jPanel_button = new JPanel();
        jPanel_button.setLayout(new GridLayout(1,3, 15 ,5));
        jPanel_button.setBorder(new EmptyBorder(10, 13, 10, 10));
        jPanel_button.add(jbutton_them);
        jPanel_button.add(jbutton_sua);
        jPanel_button.add(jbutton_xoa);
        jPanel_button.setBackground(new Color(192,192,192));

        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        
        JPanel jPanel_table = new JPanel();
        jPanel_table.setLayout(new BorderLayout());
        jPanel_table.add(jPanel_button, BorderLayout.NORTH);
        jPanel_table.add(new JScrollPane(table), BorderLayout.CENTER);



        Font font_catalog_title = new Font("Arial", Font.BOLD, 30);
        JLabel jLabel_catalog_title = new JLabel("ADMIN");
        jLabel_catalog_title.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel_catalog_title.setFont(font_catalog_title);

        Font font_catalog_button = new Font("Arial",Font.BOLD ,16);
        JButton jButton_thongke = new JButton("THỐNG KÊ");
        jButton_thongke.setFont(font_catalog_button);
        jButton_thongke.setForeground(Color.RED);
        jButton_thongke.setBackground(Color.BLACK);
        JButton jButton_qlsanpham = new JButton("QUẢN LÝ SẢN PHẨM");
        jButton_qlsanpham.setFont(font_catalog_button);    
        jButton_qlsanpham.setForeground(Color.RED);
        jButton_qlsanpham.setBackground(Color.BLACK);    
        JButton jButton_qlkhachhang = new JButton("QUẢN LÝ KHÁCH HÀNG");
        jButton_qlkhachhang.setFont(font_catalog_button);
        jButton_qlkhachhang.setForeground(Color.RED);
        jButton_qlkhachhang.setBackground(Color.BLACK);  
        JButton jButton_qlgiaodich = new JButton("QUẢN LÝ GIAO DỊCH");
        jButton_qlgiaodich.setFont(font_catalog_button);
        jButton_qlgiaodich.setForeground(Color.RED);
        jButton_qlgiaodich.setBackground(Color.BLACK);  
        JButton jButton_qlnhanvien = new JButton("QUẢN LÝ NHÂN VIÊN");
        jButton_qlnhanvien.setFont(font_catalog_button);
        jButton_qlnhanvien.setForeground(Color.RED);
        jButton_qlnhanvien.setBackground(Color.BLACK);  
        JButton jButton_dangxuat = new JButton("ĐĂNG XUẤT");
        jButton_dangxuat.setFont(font_catalog_button);
        jButton_dangxuat.setForeground(Color.RED);
        jButton_dangxuat.setBackground(Color.BLACK);  

        JPanel jPanel_catalog = new JPanel();
        jPanel_catalog.setLayout(new GridLayout(8,1,15 ,10));
        jPanel_catalog.setBorder(new EmptyBorder(10, 13, 10, 10)); // padding 
        jPanel_catalog.setBackground(new Color(192,192,192));

        jPanel_title.add(jLabel_title, BorderLayout.NORTH);
        jPanel_title.add(jPanel_table, BorderLayout.CENTER);

        jPanel_catalog.add(jLabel_catalog_title);
        jPanel_catalog.add(jButton_thongke);
        jPanel_catalog.add(jButton_qlsanpham);
        jPanel_catalog.add(jButton_qlkhachhang);
        jPanel_catalog.add(jButton_qlgiaodich);
        jPanel_catalog.add(jButton_qlnhanvien);
        jPanel_catalog.add(new JLabel());
        jPanel_catalog.add(jButton_dangxuat);


        this.setBackground(new Color(192,192,192));
        this.setLayout(new BorderLayout(10,10));
        this.add(jPanel_title, BorderLayout.CENTER);
        this.add(jPanel_catalog, BorderLayout.WEST);


        this.setVisible(true);
        controller.hienThiXeBanChay();
    }

    public void hienThiDuLieu(ArrayList<Oto> list) {
        String[] columnNames = {"Mã Ô Tô", "Tên Ô Tô", "Giá", "Loại Ô Tô", "Số Lượng", "Mô Tả", "Mã Hãng", "Số Lượt Bán"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Oto oto : list) {
            Object[] row = {
                oto.getMaOTO(),
                oto.getTenOTO(),
                oto.getGia(),
                oto.getLoaiOTO(),
                oto.getSoLuong(),
                oto.getMoTa(),
                oto.getMaHang(),
                oto.getSoLuotBan()
            };
            model.addRow(row);
        }
        table.setModel(model);
    }

    public void hienThiXeBanChayNhat(ArrayList<Oto> list) {
    String[] columnNames = {"Tên Xe", "Tên Hãng", "Số Lượt Bán"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

    for (Oto o : list) {
        Object[] row = { o.getTenOTO(), o.getMaHang(), o.getSoLuotBan() };
        model.addRow(row);
    }
    table.setModel(model);
}


    public Oto getSelectedOto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return null; // chưa chọn hàng nào
        }
        String maOTO = table.getValueAt(selectedRow, 0).toString();
        String tenOTO = table.getValueAt(selectedRow, 1).toString();
        float gia = Float.parseFloat(table.getValueAt(selectedRow, 2).toString());
        String loaiOTO = table.getValueAt(selectedRow, 3).toString();
        int soLuong = Integer.parseInt(table.getValueAt(selectedRow, 4).toString());
        String moTa = table.getValueAt(selectedRow, 5).toString();
        String maHang = table.getValueAt(selectedRow, 6).toString();
        int soLuotBan = Integer.parseInt(table.getValueAt(selectedRow, 7).toString());
        return new Oto(maOTO, tenOTO, gia, loaiOTO, soLuong, moTa, maHang, soLuotBan);
    }


}
