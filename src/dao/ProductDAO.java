package dao;

import model.ProductModel;
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

    public int insert(ProductModel car) {
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
    public int update(ProductModel car) {
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

    // PHƯƠNG THỨC XÓA (DELETE)
    public int delete(ProductModel car) {
        int ketQua = 0;
        try {
            //B1
            Connection connection = JDBC_Util.getConnection();
            //B2
            Statement st = connection.createStatement();
            //B3
            String sql = "DELETE from oto "
                    + "WHERE maOTO = '" + car.getMaOto() + "'";
            ketQua = st.executeUpdate(sql);
            //B4
            System.out.println("Bạn đã thực thi " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi");
            //B5
            JDBC_Util.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    // PHƯƠNG THỨC LẤY THÔNG TIN Ô TÔ THEO MÃ
    public ProductModel selectById(String maOTO) {
        ProductModel ketQua = null;
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

                ketQua = new ProductModel(gia, loaiOto, maOto, moTa,
                        soLuong, tenOto, soLuotBan, maHang);
            }
            JDBC_Util.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    // HIỂN THỊ DỮ LIỆU SAU KHI THÊM
    public ArrayList<ProductModel> selectAll() {
        ArrayList<ProductModel> ketQua = new ArrayList<>();
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
                ProductModel car = new ProductModel(gia, loaiOto, maOto, moTa,
                        soLuong, tenOto, soLuotBan, maHang);
                ketQua.add(car);
            }
            JDBC_Util.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    // Tìm kiếm theo mã ô tô
    public ArrayList<ProductModel> searchByMaOto(String keyword) {
        ArrayList<ProductModel> ketQua = new ArrayList<>();
        try {
            Connection connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM oto WHERE maOTO LIKE '%" + keyword + "%'";
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
                ProductModel car = new ProductModel(gia, loaiOto, maOto, moTa,
                        soLuong, tenOto, soLuotBan, maHang);
                ketQua.add(car);
            }
            JDBC_Util.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    // Tìm kiếm theo tên ô tô
    public ArrayList<ProductModel> searchByTenOto(String keyword) {
        ArrayList<ProductModel> ketQua = new ArrayList<>();
        try {
            Connection connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM oto WHERE tenOTO LIKE '%" + keyword + "%'";
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
                ProductModel car = new ProductModel(gia, loaiOto, maOto, moTa,
                        soLuong, tenOto, soLuotBan, maHang);
                ketQua.add(car);
            }
            JDBC_Util.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    // Tìm kiếm theo loại ô tô
    public ArrayList<ProductModel> searchByLoaiOto(String keyword) {
        ArrayList<ProductModel> ketQua = new ArrayList<>();
        try {
            Connection connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM oto WHERE loaiOTO LIKE '%" + keyword + "%'";
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
                ProductModel car = new ProductModel(gia, loaiOto, maOto, moTa,
                        soLuong, tenOto, soLuotBan, maHang);
                ketQua.add(car);
            }
            JDBC_Util.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    // Tìm kiếm tổng hợp (theo nhiều tiêu chí)
    public ArrayList<ProductModel> searchAllFields(String keyword) {
        ArrayList<ProductModel> ketQua = new ArrayList<>();
        try {
            Connection connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM oto WHERE " +
                    "maOTO LIKE '%" + keyword + "%' OR " +
                    "tenOTO LIKE '%" + keyword + "%' OR " +
                    "loaiOTO LIKE '%" + keyword + "%' OR " +
                    "maHang LIKE '%" + keyword + "%' OR " +
                    "moTa LIKE '%" + keyword + "%'";
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
                ProductModel car = new ProductModel(gia, loaiOto, maOto, moTa,
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