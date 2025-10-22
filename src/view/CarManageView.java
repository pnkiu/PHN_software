package view;

import javax.swing.*;
import java.awt.*;

public class CarManageView extends JFrame {
    public CarManageView() {
        this.init();
    }

    public void init(){
        this.setTitle("Car Manage Software");
        this.setSize(1050,620);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout()); // chuyển lên đầu để không reset layout

        Font font = new Font("Arial", Font.BOLD, 40);
        Font fonts = new Font("Arial", Font.BOLD, 17);

        //menu bên trái
        JPanel jPanel_menu = new JPanel();
        jPanel_menu.setLayout(new BoxLayout(jPanel_menu, BoxLayout.Y_AXIS));
        jPanel_menu.setPreferredSize(new Dimension(240, 0)); // Đặt chiều rộng
        jPanel_menu.setBackground(new Color(190,190,190,190)); // Màu xám nhạt
        jPanel_menu.setBorder(BorderFactory.createEmptyBorder(35, 10, 15, 10)); // Thêm padding

        // Tiêu đề
        JLabel jLabel_title = new JLabel("ADMIN", SwingConstants.CENTER);
        jLabel_title.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel_title.setForeground(Color.BLACK);
        jLabel_title.setFont(font);
        jPanel_menu.add(jLabel_title);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 50))); //k/cách tiêu đề

        //các list role
        JButton jButton_TK = new JButton("Thống kê");
        jButton_TK.setFont(fonts);
        jButton_TK.setBackground(new Color(70,130,180));
        jButton_TK.setForeground(Color.white);
        jButton_TK.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_TK.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_TK);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton jButton_QLSP = new JButton("QUẢN LÝ SẢN PHẨM");
        jButton_QLSP.setFont(fonts);
        jButton_QLSP.setBackground(new Color(70,130,180));
        jButton_QLSP.setForeground(Color.white);
        jButton_QLSP.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_QLSP.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_QLSP);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton jButton_QLGD = new JButton("QUẢN LÝ GIAO DỊCH");
        jButton_QLGD.setFont(fonts);
        jButton_QLGD.setBackground(new Color(70,130,180));
        jButton_QLGD.setForeground(Color.white);
        jButton_QLGD.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_QLGD.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_QLGD);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton jButton_QLKH = new JButton("QUẢN LÝ KHÁCH HÀNG");
        jButton_QLKH.setFont(fonts);
        jButton_QLKH.setBackground(new Color(70,130,180));
        jButton_QLKH.setForeground(Color.white);
        jButton_QLKH.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_QLKH.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_QLKH);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton jButton_QLNV = new JButton("QUẢN LÝ NHÂN VIÊN");
        jButton_QLNV.setFont(fonts);
        jButton_QLNV.setBackground(new Color(70,130,180));
        jButton_QLNV.setForeground(Color.white);
        jButton_QLNV.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_QLNV.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_QLNV);
        jPanel_menu.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton jButton_out = new JButton("Đăng xuất");
        jButton_out.setFont(fonts);
        jButton_out.setBackground(new Color(70,130,180));
        jButton_out.setForeground(Color.white);
        jButton_out.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton_out.setMaximumSize(new Dimension(200, 45));
        jPanel_menu.add(jButton_out);

        //bên phải
        JPanel jPanel_right = new JPanel(new BorderLayout());
        jPanel_right.setBackground(Color.WHITE);

        JLabel jLabel_header = new JLabel("QUẢN LÝ SẢN PHẨM", SwingConstants.CENTER);
        jLabel_header.setForeground(new Color(65,105,225));
        jLabel_header.setFont(font);
        jLabel_header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        jPanel_right.add(jLabel_header, BorderLayout.NORTH);

        // role
        JPanel jPanel_action = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        jPanel_action.setBackground(Color.WHITE);

        JButton jButton_add = new JButton("Thêm sản phẩm");
        JButton jButton_edit = new JButton("Sửa");
        JButton jButton_delete = new JButton("Xóa");
        JTextField jTextField_search = new JTextField(20);
        JButton jButton_search = new JButton("Tìm kiếm");

        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        for (JButton btn : new JButton[]{jButton_add, jButton_edit, jButton_delete, jButton_search}) {
            btn.setFont(buttonFont);
            btn.setBackground(new Color(65,105,225));
            btn.setForeground(Color.WHITE);
        }

        jPanel_action.add(jButton_add);
        jPanel_action.add(jButton_edit);
        jPanel_action.add(jButton_delete);
        jPanel_action.add(jTextField_search);
        jPanel_action.add(jButton_search);



        // thêm phần action vào giữa (CENTER)
        jPanel_right.add(jPanel_action, BorderLayout.NORTH);

        this.add(jPanel_menu, BorderLayout.WEST);
        this.add(jPanel_right, BorderLayout.CENTER);

        this.setVisible(true);
    }
}
