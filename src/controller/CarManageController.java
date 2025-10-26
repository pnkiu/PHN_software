package controller;

import model.CarManageDAO;
import model.CarManageModel;
import java.util.ArrayList;

public class CarManageController {
    private CarManageDAO carDAO;

    public CarManageController() {
        this.carDAO = CarManageDAO.getInstance();
    }

    public int addCar(String maOto, String tenOto, double gia, String loaiOto, 
                     int soLuong, String moTa, String maHang) {
        CarManageModel car = new CarManageModel(gia, loaiOto, maOto, moTa, 
                                               soLuong, tenOto, 0, maHang);
        return carDAO.insert(car);
    }

    public int updateCar(String maOto, String tenOto, double gia, String loaiOto, 
                        int soLuong, String moTa, String maHang, int soLuotBan) {
        CarManageModel car = new CarManageModel(gia, loaiOto, maOto, moTa, 
                                               soLuong, tenOto, soLuotBan, maHang);
        return carDAO.update(car);
    }

    public int deleteCar(String maOto) {
        return carDAO.delete(maOto);
    }

    public CarManageModel findCarById(String maOto) {
        return carDAO.selectById(maOto);
    }

    public ArrayList<CarManageModel> getAllCars() {
        return carDAO.selectAll();
    }

    public boolean validateCarData(String maOto, String tenOto, String giaStr, 
                                  String soLuongStr, String loaiOto, String maHang) {
        if (maOto == null || maOto.trim().isEmpty()) {
            return false;
        }
        if (tenOto == null || tenOto.trim().isEmpty()) {
            return false;
        }
        if (loaiOto == null || loaiOto.trim().isEmpty()) {
            return false;
        }
        if (maHang == null || maHang.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(giaStr);
            Integer.parseInt(soLuongStr);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // Thêm phương thức kiểm tra trùng mã ô tô
    public boolean isMaOtoExists(String maOto) {
        CarManageModel car = carDAO.selectById(maOto);
        return car != null;
    }
}