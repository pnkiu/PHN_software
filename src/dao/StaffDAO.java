package dao;

import model.StaffModel;
import database.JDBC_Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StaffDAO {

    public static StaffDAO getInstance() {
        return new StaffDAO();
    }

    // ============================ CRUD OPERATIONS ============================

    /**
     * Thêm nhân viên mới
     */
    public int insert(StaffModel staff) {
        int ketQua = 0;
        Connection connection = null;
        try {
            connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();

            String sql = "INSERT INTO nhanvien (maNV, tenNV, luongNV, sdtNV, chucVu, tenDangNhap, matKhau) "
                    + "VALUES (" + staff.getMaNV()
                    + ", '" + staff.getTenNV() + "'"
                    + ", " + staff.getLuongNV()
                    + ", '" + staff.getSdtNV() + "'"
                    + ", " + staff.getChucVu()
                    + ", '" + staff.getTenDangNhap() + "'"
                    + ", '" + staff.getMatKhau() + "')";

            ketQua = st.executeUpdate(sql);
            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi");

        } catch (Exception e) {
            System.out.println("Lỗi khi thêm nhân viên: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                JDBC_Util.closeConnection(connection);
            }
        }
        return ketQua;
    }

    /**
     * Cập nhật thông tin nhân viên
     */
    public int update(StaffModel staff) {
        int ketQua = 0;
        Connection connection = null;
        try {
            connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();

            String sql = "UPDATE nhanvien SET "
                    + "tenNV = '" + staff.getTenNV() + "'"
                    + ", luongNV = " + staff.getLuongNV()
                    + ", sdtNV = '" + staff.getSdtNV() + "'"
                    + ", chucVu = " + staff.getChucVu()
                    + ", tenDangNhap = '" + staff.getTenDangNhap() + "'"
                    + ", matKhau = '" + staff.getMatKhau() + "'"
                    + " WHERE maNV = " + staff.getMaNV();

            ketQua = st.executeUpdate(sql);
            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi");

        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật nhân viên: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                JDBC_Util.closeConnection(connection);
            }
        }
        return ketQua;
    }

    /**
     * Xóa nhân viên
     */
    public int delete(int maNV) {
        int ketQua = 0;
        Connection connection = null;
        try {
            connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();

            String sql = "DELETE FROM nhanvien WHERE maNV = " + maNV;

            ketQua = st.executeUpdate(sql);
            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi");

        } catch (Exception e) {
            System.out.println("Lỗi khi xóa nhân viên: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                JDBC_Util.closeConnection(connection);
            }
        }
        return ketQua;
    }

    /**
     * Lấy thông tin nhân viên theo mã
     */
    public StaffModel selectById(int maNV) {
        StaffModel ketQua = null;
        Connection connection = null;
        try {
            connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();

            String sql = "SELECT * FROM nhanvien WHERE maNV = " + maNV;
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                int maNhanVien = rs.getInt("maNV");
                String tenNV = rs.getString("tenNV");
                long luongNV = rs.getLong("luongNV");
                String sdtNV = rs.getString("sdtNV");
                int chucVu = rs.getInt("chucVu");
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");

                ketQua = new StaffModel(maNhanVien, tenNV, luongNV, sdtNV, chucVu, tenDangNhap, matKhau);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy nhân viên theo ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                JDBC_Util.closeConnection(connection);
            }
        }
        return ketQua;
    }

    /**
     * Lấy tất cả nhân viên
     */
    public ArrayList<StaffModel> selectAll() {
        ArrayList<StaffModel> ketQua = new ArrayList<>();
        Connection connection = null;
        try {
            connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();

            String sql = "SELECT * FROM nhanvien";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int maNV = rs.getInt("maNV");
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
        } finally {
            if (connection != null) {
                JDBC_Util.closeConnection(connection);
            }
        }
        return ketQua;
    }

    // ============================ SEARCH OPERATIONS ============================

    /**
     * Tìm kiếm theo mã nhân viên
     */
    public ArrayList<StaffModel> searchByMaNV(String keyword) {
        ArrayList<StaffModel> ketQua = new ArrayList<>();
        Connection connection = null;
        try {
            connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();

            String sql = "SELECT * FROM nhanvien WHERE CAST(maNV AS CHAR) LIKE '%" + keyword + "%'";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int maNV = rs.getInt("maNV");
                String tenNV = rs.getString("tenNV");
                long luongNV = rs.getLong("luongNV");
                String sdtNV = rs.getString("sdtNV");
                int chucVu = rs.getInt("chucVu");
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");

                StaffModel staff = new StaffModel(maNV, tenNV, luongNV, sdtNV, chucVu, tenDangNhap, matKhau);
                ketQua.add(staff);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm theo mã NV: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                JDBC_Util.closeConnection(connection);
            }
        }
        return ketQua;
    }

    /**
     * Tìm kiếm theo tên nhân viên
     */
    public ArrayList<StaffModel> searchByTenNV(String keyword) {
        ArrayList<StaffModel> ketQua = new ArrayList<>();
        Connection connection = null;
        try {
            connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();

            String sql = "SELECT * FROM nhanvien WHERE tenNV LIKE '%" + keyword + "%'";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int maNV = rs.getInt("maNV");
                String tenNV = rs.getString("tenNV");
                long luongNV = rs.getLong("luongNV");
                String sdtNV = rs.getString("sdtNV");
                int chucVu = rs.getInt("chucVu");
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");

                StaffModel staff = new StaffModel(maNV, tenNV, luongNV, sdtNV, chucVu, tenDangNhap, matKhau);
                ketQua.add(staff);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm theo tên NV: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                JDBC_Util.closeConnection(connection);
            }
        }
        return ketQua;
    }

    /**
     * Tìm kiếm theo số điện thoại
     */
    public ArrayList<StaffModel> searchBySDT(String keyword) {
        ArrayList<StaffModel> ketQua = new ArrayList<>();
        Connection connection = null;
        try {
            connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();

            String sql = "SELECT * FROM nhanvien WHERE sdtNV LIKE '%" + keyword + "%'";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int maNV = rs.getInt("maNV");
                String tenNV = rs.getString("tenNV");
                long luongNV = rs.getLong("luongNV");
                String sdtNV = rs.getString("sdtNV");
                int chucVu = rs.getInt("chucVu");
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");

                StaffModel staff = new StaffModel(maNV, tenNV, luongNV, sdtNV, chucVu, tenDangNhap, matKhau);
                ketQua.add(staff);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm theo SDT: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                JDBC_Util.closeConnection(connection);
            }
        }
        return ketQua;
    }

    /**
     * Tìm kiếm theo chức vụ
     */
    public ArrayList<StaffModel> searchByChucVu(String keyword) {
        ArrayList<StaffModel> ketQua = new ArrayList<>();
        Connection connection = null;
        try {
            connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();

            // Chuyển đổi từ text sang số
            int chucVuValue = 1; // Mặc định là nhân viên
            if (keyword.equalsIgnoreCase("Quản lý") || keyword.equals("0")) {
                chucVuValue = 0;
            }

            String sql = "SELECT * FROM nhanvien WHERE chucVu = " + chucVuValue;
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int maNV = rs.getInt("maNV");
                String tenNV = rs.getString("tenNV");
                long luongNV = rs.getLong("luongNV");
                String sdtNV = rs.getString("sdtNV");
                int chucVu = rs.getInt("chucVu");
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");

                StaffModel staff = new StaffModel(maNV, tenNV, luongNV, sdtNV, chucVu, tenDangNhap, matKhau);
                ketQua.add(staff);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm theo chức vụ: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                JDBC_Util.closeConnection(connection);
            }
        }
        return ketQua;
    }

    /**
     * Tìm kiếm tổng hợp (theo nhiều tiêu chí)
     */
    public ArrayList<StaffModel> searchAllFields(String keyword) {
        ArrayList<StaffModel> ketQua = new ArrayList<>();
        Connection connection = null;
        try {
            connection = JDBC_Util.getConnection();
            Statement st = connection.createStatement();

            String sql = "SELECT * FROM nhanvien WHERE "
                    + "CAST(maNV AS CHAR) LIKE '%" + keyword + "%' OR "
                    + "tenNV LIKE '%" + keyword + "%' OR "
                    + "sdtNV LIKE '%" + keyword + "%' OR "
                    + "tenDangNhap LIKE '%" + keyword + "%'";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int maNV = rs.getInt("maNV");
                String tenNV = rs.getString("tenNV");
                long luongNV = rs.getLong("luongNV");
                String sdtNV = rs.getString("sdtNV");
                int chucVu = rs.getInt("chucVu");
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");

                StaffModel staff = new StaffModel(maNV, tenNV, luongNV, sdtNV, chucVu, tenDangNhap, matKhau);
                ketQua.add(staff);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm tổng hợp: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                JDBC_Util.closeConnection(connection);
            }
        }
        return ketQua;
    }
}