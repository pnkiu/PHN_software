package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import model.ProductModel;
import until.DatabaseConnect;

public class ProductDAO {

    public static ProductDAO getInstance() {
        return new ProductDAO();
    }
    public String newMaOTO() throws SQLException {
        String newMaOTO = "OTO001";
        String sql = "SELECT MAX(CAST(SUBSTRING(maOTO, 4) AS UNSIGNED)) FROM oto";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int maxId = rs.getInt(1);
                if (maxId > 0) {
                    int nextId = maxId + 1;
                    newMaOTO = String.format("OTO%03d", nextId);
                }
            }
        }
        return newMaOTO;
    }

    public int insert(ProductModel car) {
        int ketQua = 0;
        String sql = "INSERT INTO oto (maOTO, tenOTO, gia, loaiOTO, soLuong, moTa, maHang, soLuotBan)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, 0)";
        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, car.getMaOto());
            ps.setString(2, car.getTenOto());
            ps.setBigDecimal(3, car.getGia());
            ps.setString(4, car.getLoaiOto());
            ps.setInt(5, car.getSoLuong());
            ps.setString(6, car.getMoTa());
            ps.setString(7, car.getMaHang());
            ketQua = ps.executeUpdate();
            System.out.println("Có " + ketQua + " dòng bị thay đổi (Insert Oto)");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<ProductModel> selectAll() {
        ArrayList<ProductModel> ketQua = new ArrayList<>();
        try {
            Connection connection = DatabaseConnect.getConnection();
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM oto";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String maOto = rs.getString("maOTO");
                String tenOto = rs.getString("tenOTO");
                String loaiOto = rs.getString("loaiOTO");
                BigDecimal gia = rs.getBigDecimal("gia");
                int soLuong = rs.getInt("soLuong");
                String moTa = rs.getString("moTa");
                String maHang = rs.getString("maHang");
                int soLuotBan = rs.getInt("soLuotBan");
                ProductModel car = new ProductModel(gia, loaiOto, maOto, moTa,
                        soLuong, tenOto, soLuotBan, maHang);
                ketQua.add(car);
            }
            DatabaseConnect.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int update(ProductModel car) {
        int ketQua = 0;

        // Chỉ cần 1 câu lệnh SQL
        String sql = "UPDATE oto SET tenOTO = ?, gia = ?, loaiOTO = ?, soLuong = ?, moTa = ?, maHang = ? "
                + " WHERE maOTO = ?";

        // Gộp 2 try-with-resources lại làm 1
        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement psUpdate = connection.prepareStatement(sql)) {

            // TOÀN BỘ KHỐI try (PreparedStatement psGet...) ĐÃ BỊ XÓA VÌ KHÔNG CẦN THIẾT

            psUpdate.setString(1, car.getTenOto());
            psUpdate.setBigDecimal(2, car.getGia());
            psUpdate.setString(3, car.getLoaiOto());
            psUpdate.setInt(4, car.getSoLuong()); // Dùng trực tiếp số lượng MỚI từ đối tượng car
            psUpdate.setString(5, car.getMoTa());
            psUpdate.setString(6, car.getMaHang());
            psUpdate.setString(7, car.getMaOto());

            ketQua = psUpdate.executeUpdate();

            // XÓA CÂU LỆNH LOG GÂY HIỂU NHẦM
            System.out.println("Có " + ketQua + " dòng bị thay đổi (Update Oto)");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int delete(ProductModel car) {
        int ketQua = 0;
        String sql = "DELETE FROM oto WHERE maOTO = ?";
        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, car.getMaOto());
            ketQua = ps.executeUpdate();
            System.out.println("Có "+ketQua+" dòng bị thay đổi (Delete Oto)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    public ArrayList<ProductModel> selectXeBanChayNhat() {
        ArrayList<ProductModel> list = new ArrayList<>();
        try {
            Connection connection = DatabaseConnect.getConnection();

            String sql = "SELECT o.tenOTO, h.tenHang, o.soLuotBan " +
                    "FROM oto o " +
                    "JOIN hangoto h ON o.maHang = h.maHang " +
                    "ORDER BY o.soLuotBan DESC " +
                    "LIMIT 5";

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tenOTO = rs.getString("tenOTO");
                String tenHang = rs.getString("tenHang");
                int soLuotBan = rs.getInt("soLuotBan");
                ProductModel oto = new ProductModel();
                oto.setTenOto(tenOTO);
                oto.setMaHang(tenHang);
                oto.setSoLuotBan(soLuotBan);

                list.add(oto);
            }
            DatabaseConnect.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ProductModel getProductByMaOto(String maOTO) {
        String sql = "SELECT * FROM oto WHERE maOTO = ?";
        ProductModel ketQua = null;
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maOTO);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String tenOto = rs.getString("tenOTO");
                    String loaiOto = rs.getString("loaiOTO");
                    BigDecimal gia = rs.getBigDecimal("gia");
                    int soLuong = rs.getInt("soLuong");
                    String moTa = rs.getString("moTa");
                    String maHang = rs.getString("maHang");
                    int soLuotBan = rs.getInt("soLuotBan");
                    ketQua = new ProductModel(gia, loaiOto, maOTO, moTa,
                            soLuong, tenOto, soLuotBan, maHang);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy sản phẩm theo ID: " + e.getMessage());
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<ProductModel> searchByName(String tenOto) {
        ArrayList<ProductModel> ketQua = new ArrayList<>();
        String sql = "SELECT * FROM oto WHERE tenOTO LIKE ?";
        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + tenOto + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maOto = rs.getString("maOTO");
                    String ten = rs.getString("tenOTO");
                    String loaiOto = rs.getString("loaiOTO");
                    BigDecimal gia = rs.getBigDecimal("gia");
                    int soLuong = rs.getInt("soLuong");
                    String moTa = rs.getString("moTa");
                    String maHang = rs.getString("maHang");
                    int soLuotBan = rs.getInt("soLuotBan");
                    ProductModel car = new ProductModel(gia, loaiOto, maOto, moTa,
                            soLuong, ten, soLuotBan, maHang);
                    ketQua.add(car);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    public ArrayList<ProductModel> getXeBanChayNhat(Date start, Date end) {
        ArrayList<ProductModel> list = new ArrayList<>();
        String sql = "SELECT o.tenOto, o.maHang, COUNT(gd.maOto) AS soLuotBan " +
                "FROM giaodich gd JOIN oto o ON gd.maOto = o.maOto " +
                "WHERE gd.ngayGD BETWEEN ? AND ? " +
                "GROUP BY o.tenOto, o.maHang " +
                "ORDER BY soLuotBan DESC ";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(start.getTime()));
            ps.setDate(2, new java.sql.Date(end.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductModel p = new ProductModel();
                p.setTenOto(rs.getString("tenOto"));
                p.setMaHang(rs.getString("maHang"));
                p.setSoLuotBan(rs.getInt("soLuotBan"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}