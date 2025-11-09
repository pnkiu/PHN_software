package controller;

import dao.ProductDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
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
        this.view.addEditCarListener(new EditCarListener());
        this.view.addSearchCarListener(new SearchCarListener());
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
    class DeleteCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            xoaOTo();
        }
    }
    class EditCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.formSuasp(ProductController.this);
        }
    }
    class SearchCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timKiem();
        }
    }

    public void them(String tenOto, String loaiOto, String giaStr, String soLuongStr, String maHang, String moTa) {
        try {
            BigDecimal gia = BigDecimal.valueOf(Long.parseLong(giaStr));
            int soLuong = Integer.parseInt(soLuongStr);
            if (tenOto.isEmpty() || maHang.isEmpty()) {
                view.showErrorMessage("Tên và Mã Hãng không được để trống!");
                return;
            }
            String maOto = dao.newMaOTO();
            ProductModel newCar = new ProductModel(gia, loaiOto, maOto, moTa, soLuong, tenOto, 0, maHang);
            int rowsAffected = dao.insert(newCar);
            if (rowsAffected > 0) {
                view.showSuccessMessage("Thêm sản phẩm thành công! Mã SP là: " + maOto);
                view.closeAddDialog();
                hienThiDB();
            } else {
                view.showErrorMessage("Thêm thất bại!");
            }
        } catch (NumberFormatException ex) {
            view.showErrorMessage("Giá và Số lượng phải là số!");
        } catch (SQLException ex) {
            view.showErrorMessage("Đã xảy ra lỗi CSDL khi tạo mã: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void sua(String maOto, String tenOto, String loaiOto, String giaStr, String soLuongStr, String maHang, String moTa) {
        try {
            String giaClean = giaStr.replaceAll("[.,]", "");
            String soLuongClean = soLuongStr.replaceAll("[.,]", "");
            BigDecimal gia = BigDecimal.valueOf(Long.parseLong(giaClean));
            int soLuong = Integer.parseInt(soLuongClean);
            if (maOto.isEmpty() || tenOto.isEmpty() || maHang.isEmpty()) {
                view.showErrorMessage("Mã, Tên và Mã Hãng không được để trống!");
                return;
            }
            ProductModel updatedCar = new ProductModel(gia, loaiOto, maOto, moTa, soLuong, tenOto, 0, maHang);
            int rowsAffected = dao.update(updatedCar);
            if (rowsAffected > 0) {
                view.showSuccessMessage("Cập nhật sản phẩm thành công!");
                view.closeEditDialog();
                hienThiDB();
            } else {
                view.showErrorMessage("Cập nhật thất bại!");
            }
        } catch (NumberFormatException ex) {
            view.showErrorMessage("Giá và Số lượng phải là số nguyên!\n" +
                    "Vui lòng không nhập chữ, dấu chấm, hoặc dấu phẩy.\n");
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

    public void timKiem() {
        String keyword = view.getSearchKeyword();
        if (keyword == null || keyword.trim().isEmpty()) {
            hienThiDB();
        } else {
            List<ProductModel> results = dao.searchByName(keyword);
            if (results.isEmpty()) {
                view.showSuccessMessage("Không tìm thấy sản phẩm nào với từ khóa: '" + keyword + "'");
            }
            view.hienthidulieu(results);
        }
    }
}