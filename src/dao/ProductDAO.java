package dao;

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

    public int insert(ProductModel car) {
        int ketQua = 0;
        try {
            //b1
            Connection connection = DatabaseConnect.getConnection();
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
            DatabaseConnect.closeConnection(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    //hiển thị dữ liệu sau khi thêm
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
                double gia = rs.getDouble("gia");
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
    public int delete(ProductModel car) {
               int ketQua = 0;
    String sql = "UPDATE oto SET soLuong = 0 WHERE maOTO = ?";

    try (Connection connection = DatabaseConnect.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {

        ps.setString(1, car.getMaOto());

        ketQua = ps.executeUpdate();

        System.out.println("Bạn đã thực thi: " + ps.toString()); // .toString() cho PreparedStatement
        System.out.println("Có " + ketQua + " dòng bị thay đổi");

    } catch (SQLException e) {
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
// 1. Xe bán chạy nhất trong khoảng thời gian
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
                    double gia = rs.getDouble("gia");
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

}