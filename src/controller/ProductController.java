package controller;

public class ProductController {

    public ProductController() {
        // Constructor
    }

    public boolean validateCarData(String maOto, String tenOto, String gia, String soLuong, String loaiOto, String maHang) {
        return !maOto.isEmpty() && !tenOto.isEmpty() && !gia.isEmpty() &&
                !soLuong.isEmpty() && !loaiOto.isEmpty() && !maHang.isEmpty();
    }

    // Các phương thức khác có thể được thêm vào sau
}