package dao;

import model.StaffModel;
import until.DatabaseConnect;

import java.sql.*;
import java.util.ArrayList;

public class StaffDAO {

    public static StaffDAO getInstance() {
        return new StaffDAO();
    }

    public int insert(StaffModel staff) {
        String sql = "INSERT INTO nhanvien (maNV, tenNV, luongNV, sdtNV, chucVu, tenDangNhap, matKhau) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int ketQua = 0;

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, staff.getMaNV());
            ps.setString(2, staff.getTenNV());
            ps.setLong(3, staff.getLuongNV());
            ps.setString(4, staff.getSdtNV());
            ps.setInt(5, staff.getChucVu());
            ps.setString(6, staff.getTenDangNhap());
            ps.setString(7, staff.getMatKhau());

            ketQua = ps.executeUpdate();
            System.out.println("Có " + ketQua + " dòng bị thay đổi (insert)");

        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm nhân viên: " + e.getMessage());
            e.printStackTrace();
        }
        return ketQua;
    }

    public int update(StaffModel staff) {
        String sql = "UPDATE nhanvien SET tenNV = ?, luongNV = ?, sdtNV = ?, chucVu = ?, tenDangNhap = ?, matKhau = ? WHERE maNV = ?";
        int ketQua = 0;

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, staff.getTenNV());
            ps.setLong(2, staff.getLuongNV());
            ps.setString(3, staff.getSdtNV());
            ps.setInt(4, staff.getChucVu());
            ps.setString(5, staff.getTenDangNhap());
            ps.setString(6, staff.getMatKhau());
            ps.setString(7, staff.getMaNV());

            ketQua = ps.executeUpdate();
            System.out.println("Có " + ketQua + " dòng bị thay đổi (update)");

        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật nhân viên: " + e.getMessage());
            e.printStackTrace();
        }
        return ketQua;
    }

    public int delete(String maNV) {
        String sql = "DELETE FROM nhanvien WHERE maNV = ?";
        int ketQua = 0;

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);
            ketQua = ps.executeUpdate();
            System.out.println("Có " + ketQua + " dòng bị thay đổi (delete)");

        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa nhân viên: " + e.getMessage());
            e.printStackTrace();
        }
        return ketQua;
    }

    public StaffModel getStaffByMaNV(String maNV) {
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
        return getStaffByMaNV(String.valueOf(maNV));
    }

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