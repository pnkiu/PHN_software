package dao;

import java.sql.*;
import java.util.ArrayList;
import model.StaffModel;
import until.DatabaseConnect;

public class StaffDAO {

    public static StaffDAO getInstance() {
        return new StaffDAO();
    }


    //lấy thông tin nhân viên để hiển thị cho form thêm gd
    public StaffModel getStaff(String maNV) {
        String sql = "SELECT * FROM nhanvien WHERE maNV = ?";
        StaffModel ketQua = null;

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String tenNV = rs.getString("tenNV");
                    long luongNV = rs.getLong("luongNV");
                    String sdtNV = rs.getString("sdtNV");
                    int chucVu = rs.getInt("chucVu");
                    String tenDangNhap = rs.getString("tenDangNhap");
                    String matKhau = rs.getString("matKhau");
                    ketQua = new StaffModel(maNV, tenNV, luongNV, sdtNV, chucVu, tenDangNhap, matKhau);
                }

            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy nhân viên theo ID (String): " + e.getMessage());
            e.printStackTrace();
        }
        return ketQua;
    }

    public StaffModel selectById(int maNV) {
        return getStaff(String.valueOf(maNV));
    }

    //hiển thị danh sách nhân viên
    public ArrayList<StaffModel> selectAll() {
        ArrayList<StaffModel> ketQua = new ArrayList<>();
        String sql = "SELECT * FROM nhanvien";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String tenNV = rs.getString("tenNV");
                long luongNV = rs.getLong("luongNV");
                String sdtNV = rs.getString("sdtNV");
                int chucVu = rs.getInt("chucVu");
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");

                StaffModel staff = new StaffModel(maNV, tenNV, luongNV, sdtNV, chucVu, tenDangNhap, matKhau);
                ketQua.add(staff);
            }
            System.out.println("Đã lấy " + ketQua.size() + " nhân viên từ database");

        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy tất cả nhân viên: " + e.getMessage());
            e.printStackTrace();
        }
        return ketQua;
    }

}