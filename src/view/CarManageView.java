package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class CarManageView extends JFrame {
    public CarManageView(){
        this.init();
    }

    private void init() {
        this.setTitle("Car Manage Software");
        this.setSize(1050, 620);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        Font font_title = new Font("Arial", Font.BOLD, 20);
        JLabel jLabel_title = new JLabel("HỆ THỐNG QUẢN LÝ CỬA HÀNG BÁN XE Ô TÔ");
        jLabel_title.setHorizontalAlignment(SwingConstants.CENTER); // can giua
        jLabel_title.setFont(font_title);
        
        JPanel jPanel_title = new JPanel();
        jPanel_title.setLayout(new BorderLayout());
        jPanel_title.setBorder(new EmptyBorder(10, 0, 0, 0)); // padding 



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

        jPanel_catalog.add(jLabel_catalog_title);
        jPanel_catalog.add(jButton_thongke);
        jPanel_catalog.add(jButton_qlsanpham);
        jPanel_catalog.add(jButton_qlkhachhang);
        jPanel_catalog.add(jButton_qlgiaodich);
        jPanel_catalog.add(jButton_qlnhanvien);
        jPanel_catalog.add(new JLabel());
        jPanel_catalog.add(jButton_dangxuat);


        this.setLayout(new BorderLayout(10,10));
        this.add(jPanel_title, BorderLayout.CENTER);
        this.add(jPanel_catalog, BorderLayout.WEST);



        
        this.setVisible(true);
    }

}
