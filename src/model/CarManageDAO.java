package model;

import until.DatabaseConnect;
import until.DatabaseConnect; // Lớp kết nối CSDL bạn đã tạo
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarManageDAO {

    /**
     * CHỨC NĂNG 1: HIỂN THỊ DANH SÁCH SẢN PHẨM
     * Lấy tất cả xe từ bảng 'oto'
     */
    public List<CarManageModel> getAllCars() {
        List<CarManageModel> carList = new ArrayList<>();
        String sql = "SELECT * FROM oto"; // Lấy từ bảng oto

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Lặp qua từng dòng kết quả
            while (rs.next()) {
                // Lấy dữ liệu từ CSDL
                String maOto = rs.getString("maOTO");
                String tenOto = rs.getString("tenOTO");
                String loaiOto = rs.getString("loaiOTO");
                double gia = rs.getDouble("gia");
                int soLuong = rs.getInt("soLuong");
                String moTa = rs.getString("moTa");
                String maHang = rs.getString("maHang");
                int soLuotBan = rs.getInt("soLuotBan");

                // Tạo đối tượng Model
                // (Sử dụng constructor đầy đủ, giả sử bạn đã cập nhật CarManageModel)
                CarManageModel car = new CarManageModel(gia, loaiOto, maOto, moTa,
                        soLuong, tenOto, soLuotBan, maHang);
                carList.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý lỗi (ví dụ: hiển thị thông báo)
        }
        return carList;
    }

    /**
     * CHỨC NĂNG 2: THÊM SẢN PHẨM
     * Thêm một xe mới vào bảng 'oto'
     */
    public boolean addCar(CarManageModel car) {
        // 'soLuotBan' sẽ mặc định là 0 khi thêm mới
        String sql = "INSERT INTO oto (maOTO, tenOTO, gia, loaiOTO, soLuong, moTa, maHang, soLuotBan) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, 0)";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, car.getMaOto());
            pstmt.setString(2, car.getTenOto());
            pstmt.setDouble(3, car.getGia());
            pstmt.setString(4, car.getLoaiOto());
            pstmt.setInt(5, car.getSoLuong());
            pstmt.setString(6, car.getMoTa());
            pstmt.setString(7, car.getMaHang()); // Thêm maHang

            int rowsAffected = pstmt.executeUpdate();

            // Trả về true nếu có 1 dòng bị ảnh hưởng (thêm thành công)
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi (ví dụ trùng khóa chính)
        }
    }

    // (Các hàm deleteCar, updateCar, searchCar... sẽ ở đây)
}