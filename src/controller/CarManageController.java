package controller;

import model.CarManageDAO;
import model.CarManageModel;
import view.CarManageView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List; // Thêm import này

public class CarManageController {
    private CarManageView view;
    private CarManageDAO dao;

    public CarManageController(CarManageView view, CarManageDAO dao) {
        this.view = view;
        this.dao = dao;

        // Gắn bộ lắng nghe cho nút "Thêm"
        this.view.addAddCarListener(new AddCarListener());

        // === THÊM DÒNG NÀY ===
        // Tải dữ liệu ban đầu khi chương trình khởi chạy
        refreshCarList();
    }

    /**
     * HÀM MỚI: Lấy dữ liệu từ DAO và yêu cầu View hiển thị
     */
    private void refreshCarList() {
        // 1. Gọi DAO để lấy danh sách
        List<CarManageModel> carList = dao.getAllCars(); //

        // 2. Yêu cầu View hiển thị danh sách này
        view.displayCarList(carList);
    }

    /**
     * Lớp nội (inner class) để lắng nghe sự kiện nhấn nút "Thêm"
     */
    class AddCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.showAddCarDialog(CarManageController.this);
        }
    }

    /**
     * Hàm này được View gọi khi người dùng nhấn "Lưu"
     */
    public void saveNewCar(String maOto, String tenOto, String loaiOto, String giaStr, String soLuongStr, String maHang, String moTa) {
        try {
            // (Code xử lý, kiểm tra dữ liệu giữ nguyên)
            double gia = Double.parseDouble(giaStr);
            int soLuong = Integer.parseInt(soLuongStr);

            if (maOto.isEmpty() || tenOto.isEmpty() || maHang.isEmpty()) {
                view.showErrorMessage("Mã, Tên và Mã Hãng không được để trống!");
                return;
            }

            CarManageModel newCar = new CarManageModel(gia, loaiOto, maOto, moTa, soLuong, tenOto, 0, maHang);
            boolean success = dao.addCar(newCar); //

            if (success) {
                view.showSuccessMessage("Thêm sản phẩm thành công!");
                view.closeAddDialog();

                // === THÊM DÒNG NÀY ===
                // Tải lại bảng sau khi thêm thành công
                refreshCarList();
            } else {
                view.showErrorMessage("Thêm thất bại! (Có thể trùng Mã Ô tô)");
            }

        } catch (NumberFormatException ex) {
            view.showErrorMessage("Giá và Số lượng phải là số!");
        }
    }
}