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
        // Thêm listener cho các nút
        this.view.addAddCarListener(new AddCarListener());
        this.view.addDeleteCarListener(new DeleteCarListener());
        this.view.addEditCarListener(new EditCarListener()); // <-- Thêm listener cho nút Sửa

        // Hiển thị dữ liệu khi khởi tạo
        hienThiDB();
    }

    // Hiển thị toàn bộ dữ liệu lên bảng
    public void hienThiDB() {
        List<ProductModel> carList = dao.selectAll();
        view.hienthidulieu(carList);
    }

    // Lớp lắng nghe sự kiện nút Thêm
    class AddCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.formThemsp(ProductController.this);
            // Không cần add lại delete listener ở đây
        }
    }

    // Lớp lắng nghe sự kiện nút Xóa
    class DeleteCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            xoaOTo();
        }
    }

    // Lớp lắng nghe sự kiện nút Sửa
    class EditCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Hiển thị form sửa, truyền controller này vào
            view.formSuasp(ProductController.this);
        }
    }

    // Phương thức xử lý logic Thêm
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
                hienThiDB(); // Cập nhật lại bảng
            } else {
                view.showErrorMessage("Thêm thất bại!");
            }
        } catch (NumberFormatException ex) {
            view.showErrorMessage("Giá và Số lượng phải là số!");
        }
    }

    // Phương thức xử lý logic Sửa
    public void sua(String maOto, String tenOto, String loaiOto, String giaStr, String soLuongStr, String maHang, String moTa) {
        try {
            double gia = Double.parseDouble(giaStr);
            int soLuong = Integer.parseInt(soLuongStr);

            // Validate (Tương tự như khi thêm)
            if (maOto.isEmpty() || tenOto.isEmpty() || maHang.isEmpty()) {
                view.showErrorMessage("Mã, Tên và Mã Hãng không được để trống!");
                return;
            }

            // Tạo đối tượng ProductModel với thông tin đã cập nhật
            // (soLuotBan không được chỉnh sửa ở đây, nên ta có thể để là 0,
            // vì DAO.update không cập nhật trường này)
            ProductModel updatedCar = new ProductModel(gia, loaiOto, maOto, moTa, soLuong, tenOto, 0, maHang);

            int rowsAffected = dao.update(updatedCar);

            if (rowsAffected > 0) {
                view.showSuccessMessage("Cập nhật sản phẩm thành công!");
                view.closeEditDialog(); // Đóng dialog sửa
                hienThiDB(); // Cập nhật lại bảng chính
            } else {
                view.showErrorMessage("Cập nhật thất bại!");
            }
        } catch (NumberFormatException ex) {
            view.showErrorMessage("Giá và Số lượng phải là số!");
        }
    }

    // Phương thức xử lý logic Xóa
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
                hienThiDB(); // Cập nhật lại bảng
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!");
            }
        }
    }
}