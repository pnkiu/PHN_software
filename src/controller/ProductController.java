package controller;

import dao.ProductDAO;
import model.ProductModel;
import view.ProductView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductController {
    private ProductView view;
    private ProductDAO dao;

    public ProductController(ProductView view, ProductDAO dao) {
        this.view = view;
        this.dao = dao;
        this.view.addAddCarListener(new AddCarListener());
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
}