package controller;

import model.CarManageDAO;
import model.CarManageModel;
import view.CarManageView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarManageController {
    private CarManageView view;
    private CarManageDAO dao;

    public CarManageController(CarManageView view, CarManageDAO dao) {
        this.view = view;
        this.dao = dao;
        this.view.addAddCarListener(new AddCarListener());
        hienThiDB();
    }


    public void hienThiDB() {
        List<CarManageModel> carList = dao.selectAll();
        view.hienthidulieu(carList);
    }


    class AddCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.formThemsp(CarManageController.this);
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
            CarManageModel newCar = new CarManageModel(gia, loaiOto, maOto, moTa, soLuong, tenOto, 0, maHang);
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