package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        try {
            //B1
        Connection connection = DatabaseConnect.getConnection();

        //B2
        Statement st = connection.createStatement();

        //B3
        String sql = "DELETE from oto "
					+"WHERE maOTO = '"+car.getMaOto()+"'";
        ketQua = st.executeUpdate(sql);

        //B4
        System.out.println("Bạn đã thực thi "+ sql);
		System.out.println("Có "+ketQua+" dòng bị thay đổi");

        //B5
        DatabaseConnect.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
        
    }
}