package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.CustomerModel;
import until.DatabaseConnect;

public class CustomerDAO {
    // private CarManageView view;
    private static CustomerDAO instance;

    public List<CustomerModel> getTopKhachHangTheoTongTien(Date start, Date end) {
    List<CustomerModel> list = new ArrayList<>();
    try (Connection conn = DatabaseConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(
            "SELECT kh.maKH, kh.tenKH, SUM(gd.tongTien) AS tongChiTieu " +
            "FROM khachhang kh " +
            "JOIN giaodich gd ON kh.maKH = gd.maKH " +
            "WHERE gd.ngayGD BETWEEN ? AND ? " +
            "GROUP BY kh.maKH, kh.tenKH " +
            "ORDER BY tongChiTieu DESC LIMIT 5")) {
        ps.setDate(1, new java.sql.Date(start.getTime()));
        ps.setDate(2, new java.sql.Date(end.getTime()));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            CustomerModel kh = new CustomerModel();
            kh.setMaKH(rs.getString("maKH"));
            kh.setTenKH(rs.getString("tenKH"));
            kh.setTongChiTieu(rs.getDouble("tongChiTieu")); // cũng không cần có cột thật
            list.add(kh);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


public List<CustomerModel> getTopKhachHangTheoSoLanMua(Date start, Date end) {
    List<CustomerModel> list = new ArrayList<>();
    try (Connection conn = DatabaseConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(
            "SELECT kh.maKH, kh.tenKH, COUNT(gd.maGD) AS soLanMua " +
            "FROM khachhang kh " +
            "JOIN giaodich gd ON kh.maKH = gd.maKH " +
            "WHERE gd.ngayGD BETWEEN ? AND ? " +
            "GROUP BY kh.maKH, kh.tenKH " +
            "ORDER BY soLanMua DESC LIMIT 5")) {
        ps.setDate(1, new java.sql.Date(start.getTime()));
        ps.setDate(2, new java.sql.Date(end.getTime()));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            CustomerModel kh = new CustomerModel();
            kh.setMaKH(rs.getString("maKH"));
            kh.setTenKH(rs.getString("tenKH"));
            kh.setSoLanMua(rs.getInt("soLanMua")); // mặc dù bảng KH không có cột này
            list.add(kh);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


    public static CustomerDAO getInstance() {
    if (instance == null) {
        instance = new CustomerDAO();
    }
    return instance;
}



}
