package controller;

import dao.ProductDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import model.ProductModel;
import view.ProductView;

public class ProductController {
    private ProductView view;
    private ProductDAO dao;

    public ProductController(ProductView view, ProductDAO dao) {
        this.view = view;
        this.dao = dao;
        this.view.addAddCarListener(new AddCarListener());
        this.view.addDeleteCarListener(new DeleteCarListener());
        hienThiDB();
    }


    public void hienThiDB() {
        List<ProductModel> carList = dao.selectAll();
        view.hienthidulieu(carList);
    }


    class AddCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.formThemsp(ProductController.this);
            view.addDeleteCarListener(new DeleteCarListener());
        }
    }
    class DeleteCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            xoaOTo();
    }
}

    public void them(String maOto, String tenOto, String loaiOto, String giaStr, String soLuongStr, String maHang, String moTa) {
        try {
            double gia = Double.parseDouble(giaStr);
            int soLuong = Integer.parseInt(soLuongStr);
            if (maOto.isEmpty() || tenOto.isEmpty() || maHang.isEmpty()) {
                view.showErrorMessage("Mã, Tên và Mã Hãng không được để trống!");
                return;
            }
            ProductModel newCar = new ProductModel(gia, loaiOto, maOto, moTa, soLuong, tenOto, 0, maHang);
            int rowsAffected = dao.insert(newCar);
            if (rowsAffected > 0) {
                view.showSuccessMessage("Thêm sản phẩm thành công!");
                view.closeAddDialog();
                hienThiDB();
            } else {
                view.showErrorMessage("Thêm thất bại!");
            }
        } catch (NumberFormatException ex) {
            view.showErrorMessage("Giá và Số lượng phải là số!");
        }
    }
            private void xoaOTo() {
            ProductModel selected = view.getSelectedOto();
            if (selected == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn 1 xe để xóa!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(view, 
            "Bạn có chắc muốn xóa xe " + selected.getTenOto() + " không?", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
            int kq = ProductDAO.getInstance().delete(selected);
            if (kq > 0) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                hienThiDB();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!");
            }
        }
}
}