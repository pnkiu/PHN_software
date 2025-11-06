package controller;

import dao.ProductDAO;
import model.ProductModel;
import java.util.ArrayList;
import java.util.List;

public class CarManageController {
    private ProductDAO productDAO;

    public CarManageController() {
        this.productDAO = ProductDAO.getInstance();
    }

    public CarManageController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    // Thêm ô tô mới
    public boolean addCar(ProductModel car) {
        int result = productDAO.insert(car);
        return result > 0;
    }

    // Cập nhật thông tin ô tô
    public boolean updateCar(ProductModel car) {
        int result = productDAO.update(car);
        return result > 0;
    }

    // Xóa ô tô
    public boolean deleteCar(ProductModel car) {
        int result = productDAO.delete(car);
        return result > 0;
    }

    // Lấy thông tin ô tô theo mã
    public ProductModel getCarByMaOTO(String maOTO) {
        return productDAO.selectById(maOTO);
    }

    // Lấy danh sách ô tô
    public List<ProductModel> getCarList() {
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

    public ArrayList<ProductModel> searchByMaOto(String keyword) {
        return productDAO.searchByMaOto(keyword);
    }

    public ArrayList<ProductModel> searchByTenOto(String keyword) {
        return productDAO.searchByTenOto(keyword);
    }

    public ArrayList<ProductModel> searchByLoaiOto(String keyword) {
        return productDAO.searchByLoaiOto(keyword);
    }

    public ArrayList<ProductModel> searchAllFields(String keyword) {
        return productDAO.searchAllFields(keyword);
    }


    public boolean addCar(String text, String text1, String text2, String text3, String text4, String text5, String text6) {
        return false;
    }
}
