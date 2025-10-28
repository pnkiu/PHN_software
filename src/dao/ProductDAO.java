//package dao;
//
//import model.CarManageModel;
//import database.JDBC_Util;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//
//public class ProductDAO {
//
//    public static ProductDAO getInstance() {
//        return new ProductDAO();
//    }
//
//    public int insert(CarManageModel car) {
//        int ketQua = 0;
//        try {
//            //b1
//            Connection connection = JDBC_Util.getConnection();
//            //b2
//            Statement st = connection.createStatement();
//            String sql = "INSERT INTO oto (maOTO, tenOTO, gia, loaiOTO, soLuong, moTa, maHang, soLuotBan)"
//                    + " VALUES ('" + car.getMaOto() + "'"
//                    + " , '" + car.getTenOto() + "'"
//                    + " , " + car.getGia()
//                    + " , '" + car.getLoaiOto() + "'"
//                    + " , " + car.getSoLuong()
//                    + " , '" + car.getMoTa() + "'"
//                    + " , '" + car.getMaHang() + "'"
//                    + " , 0)";
//            ketQua = st.executeUpdate(sql);
//            System.out.println("Bạn đã thực thi " + sql);
//            System.out.println("Có " + ketQua + " dòng bị thay đổi");
//            JDBC_Util.closeConnection(connection);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ketQua;
//    }
//
//    // PHƯƠNG THỨC SỬA (UPDATE)
//    public int update(CarManageModel car) {
//        int ketQua = 0;
//        try {
//            Connection connection = JDBC_Util.getConnection();
//            Statement st = connection.createStatement();
//            String sql = "UPDATE oto SET "
//                    + " tenOTO = '" + car.getTenOto() + "'"
//                    + " , gia = " + car.getGia()
//                    + " , loaiOTO = '" + car.getLoaiOto() + "'"
//                    + " , soLuong = " + car.getSoLuong()
//                    + " , moTa = '" + car.getMoTa() + "'"
//                    + " , maHang = '" + car.getMaHang() + "'"
//                    + " WHERE maOTO = '" + car.getMaOto() + "'";
//
//            ketQua = st.executeUpdate(sql);
//            System.out.println("Bạn đã thực thi " + sql);
//            System.out.println("Có " + ketQua + " dòng bị thay đổi");
//            JDBC_Util.closeConnection(connection);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ketQua;
//    }
//
//    // PHƯƠNG THỨC LẤY THÔNG TIN Ô TÔ THEO MÃ
//    public CarManageModel selectById(String maOTO) {
//        CarManageModel ketQua = null;
//        try {
//            Connection connection = JDBC_Util.getConnection();
//            Statement st = connection.createStatement();
//            String sql = "SELECT * FROM oto WHERE maOTO = '" + maOTO + "'";
//            ResultSet rs = st.executeQuery(sql);
//
//            while (rs.next()) {
//                String maOto = rs.getString("maOTO");
//                String tenOto = rs.getString("tenOTO");
//                String loaiOto = rs.getString("loaiOTO");
//                double gia = rs.getDouble("gia");
//                int soLuong = rs.getInt("soLuong");
//                String moTa = rs.getString("moTa");
//                String maHang = rs.getString("maHang");
//                int soLuotBan = rs.getInt("soLuotBan");
//
//                ketQua = new CarManageModel(gia, loaiOto, maOto, moTa,
//                        soLuong, tenOto, soLuotBan, maHang);
//            }
//            JDBC_Util.closeConnection(connection);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return ketQua;
//    }
//
//    //hiển thị dữ liệu sau khi thêm
//    public ArrayList<CarManageModel> selectAll() {
//        ArrayList<CarManageModel> ketQua = new ArrayList<>();
//        try {
//            Connection connection = JDBC_Util.getConnection();
//            Statement st = connection.createStatement();
//            String sql = "SELECT * FROM oto";
//            ResultSet rs = st.executeQuery(sql);
//            while (rs.next()) {
//                String maOto = rs.getString("maOTO");
//                String tenOto = rs.getString("tenOTO");
//                String loaiOto = rs.getString("loaiOTO");
//                double gia = rs.getDouble("gia");
//                int soLuong = rs.getInt("soLuong");
//                String moTa = rs.getString("moTa");
//                String maHang = rs.getString("maHang");
//                int soLuotBan = rs.getInt("soLuotBan");
//                CarManageModel car = new CarManageModel(gia, loaiOto, maOto, moTa,
//                        soLuong, tenOto, soLuotBan, maHang);
//                ketQua.add(car);
//            }
//            JDBC_Util.closeConnection(connection);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return ketQua;
//    }
//}

package dao;

import model.CarManageModel;
import database.JDBC_Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductDAO {

    public static ProductDAO getInstance() {
        return new ProductDAO();
    }

    public int insert(CarManageModel car) {
        int ketQua = 0;
        try {
            //b1
            Connection connection = JDBC_Util.getConnection();
            //b2
            Statement st = connection.createStatement();
            String sql = "INSERT INTO oto (maOTO, tenOTO, gia, loaiOTO, soLuong, moTa, maHang, soLuotBan)"
                    + " VALUES ('" + car.getMaOto() + "'"
                    + " , '" + car.getTenOto() + "'"
                    + " , " + car.getGia()
                    + " , '" + car.getLoaiOto() + "'"
                    + " , " + car.getSoLuong()
                    + " , '" + car.getMoTa() + "'"
                    + " , '" + car.getMaHang() + "'"
                    + " , 0)";
            ketQua = st.executeUpdate(sql);
            System.out.println("Bạn đã thực thi " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi");
            JDBC_Util.closeConnection(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    // PHƯƠNG THỨC SỬA (UPDATE) - ĐÃ SỬA ĐỔI ĐỂ CỘNG DỒN SỐ LƯỢNG
    public int update(CarManageModel car) {
        int ketQua = 0;
        try {
            Connection connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();

            // Lấy số lượng hiện tại từ database
            String getCurrentQuantitySQL = "SELECT soLuong FROM oto WHERE maOTO = '" + car.getMaOto() + "'";
            ResultSet rs = st.executeQuery(getCurrentQuantitySQL);

            int currentQuantity = 0;
            if (rs.next()) {
                currentQuantity = rs.getInt("soLuong");
            }

            // Tính tổng số lượng mới = số lượng hiện tại + số lượng nhập vào
            int newQuantity = currentQuantity + car.getSoLuong();

            String sql = "UPDATE oto SET "
                    + " tenOTO = '" + car.getTenOto() + "'"
                    + " , gia = " + car.getGia()
                    + " , loaiOTO = '" + car.getLoaiOto() + "'"
                    + " , soLuong = " + newQuantity  // Sử dụng tổng số lượng mới
                    + " , moTa = '" + car.getMoTa() + "'"
                    + " , maHang = '" + car.getMaHang() + "'"
                    + " WHERE maOTO = '" + car.getMaOto() + "'";

            ketQua = st.executeUpdate(sql);
            System.out.println("Bạn đã thực thi " + sql);
            System.out.println("Số lượng cũ: " + currentQuantity +
                    ", Số lượng nhập thêm: " + car.getSoLuong() +
                    ", Tổng số lượng mới: " + newQuantity);
            System.out.println("Có " + ketQua + " dòng bị thay đổi");
            JDBC_Util.closeConnection(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    // PHƯƠNG THỨC LẤY THÔNG TIN Ô TÔ THEO MÃ
    public CarManageModel selectById(String maOTO) {
        CarManageModel ketQua = null;
        try {
            Connection connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM oto WHERE maOTO = '" + maOTO + "'";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String maOto = rs.getString("maOTO");
                String tenOto = rs.getString("tenOTO");
                String loaiOto = rs.getString("loaiOTO");
                double gia = rs.getDouble("gia");
                int soLuong = rs.getInt("soLuong");
                String moTa = rs.getString("moTa");
                String maHang = rs.getString("maHang");
                int soLuotBan = rs.getInt("soLuotBan");

                ketQua = new CarManageModel(gia, loaiOto, maOto, moTa,
                        soLuong, tenOto, soLuotBan, maHang);
            }
            JDBC_Util.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    //hiển thị dữ liệu sau khi thêm
    public ArrayList<CarManageModel> selectAll() {
        ArrayList<CarManageModel> ketQua = new ArrayList<>();
        try {
            Connection connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM oto";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String maOto = rs.getString("maOTO");
                String tenOto = rs.getString("tenOTO");
                String loaiOto = rs.getString("loaiOTO");
                double gia = rs.getDouble("gia");
                int soLuong = rs.getInt("soLuong");
                String moTa = rs.getString("moTa");
                String maHang = rs.getString("maHang");
                int soLuotBan = rs.getInt("soLuotBan");
                CarManageModel car = new CarManageModel(gia, loaiOto, maOto, moTa,
                        soLuong, tenOto, soLuotBan, maHang);
                ketQua.add(car);
            }
            JDBC_Util.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }
}