package controller;

import dao.ProductDAO;
import model.CarManageModel;
import java.util.ArrayList;
import java.util.List;

public class CarManageController {
    private ProductDAO productDAO;

    public CarManageController() {
        this.productDAO = ProductDAO.getInstance();
    }

    // Cập nhật thông tin ô tô
    public boolean updateCar(CarManageModel car) {
        int result = productDAO.update(car);
        return result > 0;
    }

    // Lấy thông tin ô tô theo mã
    public CarManageModel getCarByMaOTO(String maOTO) {
        return productDAO.selectById(maOTO);
    }

    // Lấy danh sách ô tô
    public List<CarManageModel> getCarList() {
        return productDAO.selectAll();
    }

    // Validation dữ liệu
    public boolean validateCarData(String tenOTO, String gia, String soLuong, String loaiOTO, String maHang) {
        if (tenOTO == null || tenOTO.trim().isEmpty()) return false;
        if (gia == null || gia.trim().isEmpty()) return false;
        if (soLuong == null || soLuong.trim().isEmpty()) return false;
        if (loaiOTO == null || loaiOTO.trim().isEmpty()) return false;
        if (maHang == null || maHang.trim().isEmpty()) return false;

        try {
            Double.parseDouble(gia);
            Integer.parseInt(soLuong);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
    public ArrayList<CarManageModel> searchByMaOto(String keyword) {
        return productDAO.searchByMaOto(keyword);
    }

    public ArrayList<CarManageModel> searchByTenOto(String keyword) {
        return productDAO.searchByTenOto(keyword);
    }

    public ArrayList<CarManageModel> searchByLoaiOto(String keyword) {
        return productDAO.searchByLoaiOto(keyword);
    }

    public ArrayList<CarManageModel> searchAllFields(String keyword) {
        return productDAO.searchAllFields(keyword);
    }
}