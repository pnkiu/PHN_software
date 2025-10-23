package controller;

import model.CarManageDAO;
import model.CarManageModel;
import view.CarManageView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; // SỬA Ở ĐÂY (hoặc giữ List cũng được, nhưng ArrayList rõ ràng hơn)
import java.util.List;

public class CarManageController {
    private CarManageView view;
    private CarManageDAO dao;

    public CarManageController(CarManageView view, CarManageDAO dao) {
        this.view = view;
        this.dao = dao;
        this.view.addAddCarListener(new AddCarListener());
        refreshCarList();
    }

    private void refreshCarList() {
        // SỬA Ở ĐÂY: Gọi hàm selectAll() thay vì getAllCars()
        // Ghi chú: View.displayCarList(List<T>) vẫn nhận ArrayList<T>
        List<CarManageModel> carList = dao.selectAll();
        view.displayCarList(carList);
    }


    class AddCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.showAddCarDialog(CarManageController.this);
        }
    }

    public void saveNewCar(String maOto, String tenOto, String loaiOto, String giaStr, String soLuongStr, String maHang, String moTa) {
        try {
            double gia = Double.parseDouble(giaStr);
            int soLuong = Integer.parseInt(soLuongStr);
            if (maOto.isEmpty() || tenOto.isEmpty() || maHang.isEmpty()) {
                view.showErrorMessage("Mã, Tên và Mã Hãng không được để trống!");
                return;
            }
            CarManageModel newCar = new CarManageModel(gia, loaiOto, maOto, moTa, soLuong, tenOto, 0, maHang);
            int rowsAffected = dao.insert(newCar);
            if (rowsAffected > 0) {
                view.showSuccessMessage("Thêm sản phẩm thành công!");
                view.closeAddDialog();
                refreshCarList();
            } else {
                view.showErrorMessage("Thêm thất bại!");
            }

        } catch (NumberFormatException ex) {
            view.showErrorMessage("Giá và Số lượng phải là số!");
        }
    }
}