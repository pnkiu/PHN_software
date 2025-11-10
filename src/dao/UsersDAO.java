package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.UsersModel;
import until.DatabaseConnect;

public class UsersDAO {

    public UsersModel getUserByUsername(String tenDangNhap) {
        UsersModel user = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnect.getConnection();
            String sql = "SELECT * FROM nhanvien WHERE tenDangNhap = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, tenDangNhap);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new UsersModel();
                user.setMaNV(rs.getString("maNV"));
                user.setTenNV(rs.getString("tenNV"));
                user.setLuongNV(rs.getDouble("luongNV"));
                user.setSdtNV(rs.getString("sdtNV"));
                user.setChucVu(rs.getInt("chucVu"));
                user.setTenDangNhap(rs.getString("tenDangNhap"));
                user.setMatKhau(rs.getString("matKhau"));

                System.out.println("✓ Đã tìm thấy nhân viên: " + user.getTenNV() + " - " + user.getRoleString());
            } else {
                System.out.println("✗ Không tìm thấy nhân viên với tên đăng nhập: " + tenDangNhap);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("✗ Lỗi khi truy vấn nhân viên: " + e.getMessage());
        } finally {
            closeResources(rs, ps, conn);
        }

        return user;
    }

    public boolean validateUser(String tenDangNhap, String matKhau) {
        UsersModel user = getUserByUsername(tenDangNhap);
        if (user != null) {
            boolean isValid = user.getMatKhau().equals(matKhau);
            if (isValid) {
                System.out.println("✓ Xác thực thành công cho nhân viên: " + tenDangNhap);
            } else {
                System.out.println("✗ Mật khẩu không đúng cho nhân viên: " + tenDangNhap);
            }
            return isValid;
        }
        System.out.println("✗ Nhân viên không tồn tại: " + tenDangNhap);
        return false;
    }

    // Lấy tất cả nhân viên để hiển thị thông tin
    public List<UsersModel> getAllUsers() {
        List<UsersModel> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnect.getConnection();
            String sql = "SELECT * FROM nhanvien ORDER BY maNV";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                UsersModel user = new UsersModel();
                user.setMaNV(rs.getString("maNV"));
                user.setTenNV(rs.getString("tenNV"));
                user.setLuongNV(rs.getDouble("luongNV"));
                user.setSdtNV(rs.getString("sdtNV"));
                user.setChucVu(rs.getInt("chucVu"));
                user.setTenDangNhap(rs.getString("tenDangNhap"));
                user.setMatKhau(rs.getString("matKhau"));
                users.add(user);
            }

            System.out.println("✓ Đã tải " + users.size() + " nhân viên từ database");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("✗ Lỗi khi lấy danh sách nhân viên: " + e.getMessage());
        } finally {
            closeResources(rs, ps, conn);
        }

        return users;
    }

    public boolean isUsernameExists(String tenDangNhap) {
        return getUserByUsername(tenDangNhap) != null;
    }

    // Kiểm tra xem bảng nhanvien có tồn tại và có dữ liệu không
    public boolean checkNhanVienTable() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnect.getConnection();
            String sql = "SELECT COUNT(*) as count FROM nhanvien WHERE tenDangNhap IS NOT NULL";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                System.out.println("✓ Tìm thấy " + count + " nhân viên trong bảng nhanvien");
                return count > 0;
            }

        } catch (SQLException e) {
            System.err.println("✗ Lỗi kiểm tra bảng nhanvien: " + e.getMessage());
            return false;
        } finally {
            closeResources(rs, ps, conn);
        }
        return false;
    }

    // Phương thức đóng tài nguyên
    private void closeResources(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) DatabaseConnect.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}