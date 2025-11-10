package dao;

import java.sql.*;
import java.util.ArrayList;
import model.StaffModel;
import until.DatabaseConnect;

public class StaffDAO {

    private static StaffDAO instance;

    public static StaffDAO getInstance() {
        if (instance == null) {
            instance = new StaffDAO();
        }
        return instance;
    }

    public StaffDAO(){

    }

    /**
     * Lấy mã nhân viên tiếp theo (NV001, NV002,...)
     */
    public String getNextMaNV() {
        String nextMaNV = "NV001";
        Connection connection = null;
        try {
            connection = DatabaseConnect.getConnection();
            String sql = "SELECT maNV FROM nhanvien ORDER BY LENGTH(maNV), maNV DESC LIMIT 1";

            try (PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    String lastMaNV = rs.getString("maNV");
                    if (lastMaNV != null && lastMaNV.startsWith("NV")) {
                        try {
                            int lastNumber = Integer.parseInt(lastMaNV.substring(2));
                            nextMaNV = "NV" + String.format("%03d", lastNumber + 1);
                        } catch (NumberFormatException e) {
                            System.out.println("Lỗi khi phân tích mã NV: " + e.getMessage());
                            // Nếu không parse được, tìm số lớn nhất
                            nextMaNV = findMaxMaNV(connection);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy mã NV tiếp theo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DatabaseConnect.closeConnection(connection);
            }
        }
        return nextMaNV;
    }

    private String findMaxMaNV(Connection connection) {
        String maxMaNV = "NV001";
        try {
            String sql = "SELECT MAX(CAST(SUBSTRING(maNV, 3) AS UNSIGNED)) as max_num FROM nhanvien WHERE maNV REGEXP '^NV[0-9]+$'";
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getObject("max_num") != null) {
                    int maxNum = rs.getInt("max_num");
                    maxMaNV = "NV" + String.format("%03d", maxNum + 1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm mã NV lớn nhất: " + e.getMessage());
        }
        return maxMaNV;
    }

    public boolean insert(StaffModel staff) {
        Connection connection = null;
        try {
            connection = DatabaseConnect.getConnection();
            String sql = "INSERT INTO nhanvien (maNV, tenNV, luongNV, sdtNV, chucVu, tenDangNhap, matKhau) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, staff.getMaNV());
                stmt.setString(2, staff.getTenNV());
                // stmt.setString(3, staff.getLuongNV());
                stmt.setLong(3, staff.getLuongNV());
                stmt.setString(4, staff.getSdtNV());
                stmt.setInt(5, staff.getChucVu());
                stmt.setString(6, staff.getTenDangNhap());
                stmt.setString(7, staff.getMatKhau());

                int result = stmt.executeUpdate();
                System.out.println("INSERT result: " + result);
                return result > 0;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm nhân viên: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                DatabaseConnect.closeConnection(connection);
            }
        }
    }

    public boolean update(StaffModel staff) {
        Connection connection = null;
        try {
            connection = DatabaseConnect.getConnection();
            String sql = "UPDATE nhanvien SET tenNV = ?, luongNV = ?, sdtNV = ?, chucVu = ?, tenDangNhap = ?, matKhau = ? WHERE maNV = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, staff.getTenNV());
                // stmt.setString(2, staff.getLuongNV());
                stmt.setLong(2, staff.getLuongNV());
                stmt.setString(3, staff.getSdtNV());
                stmt.setInt(4, staff.getChucVu());
                stmt.setString(5, staff.getTenDangNhap());
                stmt.setString(6, staff.getMatKhau());
                stmt.setString(7, staff.getMaNV());

                int result = stmt.executeUpdate();
                System.out.println("UPDATE result: " + result);
                return result > 0;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật nhân viên: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                DatabaseConnect.closeConnection(connection);
            }
        }
    }

    public boolean delete(String maNV) {
        Connection connection = null;
        try {
            connection = DatabaseConnect.getConnection();
            String sql = "DELETE FROM nhanvien WHERE maNV = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, maNV);
                int result = stmt.executeUpdate();
                System.out.println("DELETE result: " + result);
                return result > 0;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa nhân viên: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                DatabaseConnect.closeConnection(connection);
            }
        }
    }

    public StaffModel selectById(String maNV) {
        Connection connection = null;
        try {
            connection = DatabaseConnect.getConnection();
            String sql = "SELECT * FROM nhanvien WHERE maNV = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, maNV);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return mapResultSetToStaff(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy nhân viên theo ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DatabaseConnect.closeConnection(connection);
            }
        }
        return null;
    }

    public ArrayList<StaffModel> selectAll() {
        ArrayList<StaffModel> staffList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DatabaseConnect.getConnection();
            String sql = "SELECT * FROM nhanvien ORDER BY LENGTH(maNV), maNV";

            try (PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    staffList.add(mapResultSetToStaff(rs));
                }
                System.out.println("Số nhân viên lấy được: " + staffList.size());
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy tất cả nhân viên: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DatabaseConnect.closeConnection(connection);
            }
        }
        return staffList;
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

    // public StaffModel selectById(int maNV) {
    //     return getStaff(String.valueOf(maNV));
    // }

    //hiển thị danh sách nhân viên
    // public ArrayList<StaffModel> selectAll() {
    //     ArrayList<StaffModel> ketQua = new ArrayList<>();
    //     String sql = "SELECT * FROM nhanvien";

    //     try (Connection conn = DatabaseConnect.getConnection();
    //          PreparedStatement ps = conn.prepareStatement(sql);
    //          ResultSet rs = ps.executeQuery()) {

    //         while (rs.next()) {
    //             String maNV = rs.getString("maNV");
    //             String tenNV = rs.getString("tenNV");
    //             long luongNV = rs.getLong("luongNV");
    //             String sdtNV = rs.getString("sdtNV");
    //             int chucVu = rs.getInt("chucVu");
    //             String tenDangNhap = rs.getString("tenDangNhap");
    //             String matKhau = rs.getString("matKhau");

    //             StaffModel staff = new StaffModel(maNV, tenNV, luongNV, sdtNV, chucVu, tenDangNhap, matKhau);
    //             ketQua.add(staff);
    //         }
    //         System.out.println("Đã lấy " + ketQua.size() + " nhân viên từ database");

    //     } catch (SQLException e) {
    //         System.out.println("Lỗi khi lấy tất cả nhân viên: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    //     return ketQua;
    // }

    public ArrayList<StaffModel> searchAllFields(String keyword) {
        ArrayList<StaffModel> staffList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DatabaseConnect.getConnection();
            String sql = "SELECT * FROM nhanvien WHERE maNV LIKE ? OR tenNV LIKE ? OR sdtNV LIKE ? OR tenDangNhap LIKE ? ORDER BY LENGTH(maNV), maNV";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                String searchPattern = "%" + keyword + "%";
                stmt.setString(1, searchPattern);
                stmt.setString(2, searchPattern);
                stmt.setString(3, searchPattern);
                stmt.setString(4, searchPattern);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        staffList.add(mapResultSetToStaff(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm tổng hợp: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DatabaseConnect.closeConnection(connection);
            }
        }
        return staffList;
    }

    public ArrayList<StaffModel> searchByMaNV(String keyword) {
        return searchByField("maNV", keyword);
    }

    public ArrayList<StaffModel> searchByTenNV(String keyword) {
        return searchByField("tenNV", keyword);
    }

    public ArrayList<StaffModel> searchBySDT(String keyword) {
        return searchByField("sdtNV", keyword);
    }

     public ArrayList<StaffModel> searchByChucVu(String keyword) {
        ArrayList<StaffModel> staffList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DatabaseConnect.getConnection();
            String sql = "SELECT * FROM nhanvien WHERE chucVu = ? ORDER BY LENGTH(maNV), maNV";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                int chucVuValue = keyword.equalsIgnoreCase("Quản lý") || keyword.equals("0") ? 0 : 1;
                stmt.setInt(1, chucVuValue);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        staffList.add(mapResultSetToStaff(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm theo chức vụ: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DatabaseConnect.closeConnection(connection);
            }
        }
        return staffList;
    }

    public boolean isTenDangNhapExists(String tenDangNhap) {
        Connection connection = null;
        try {
            connection = DatabaseConnect.getConnection();
            String sql = "SELECT COUNT(*) FROM nhanvien WHERE tenDangNhap = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, tenDangNhap);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi kiểm tra tên đăng nhập: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DatabaseConnect.closeConnection(connection);
            }
        }
        return false;
    }

    public boolean isSdtExists(String sdtNV) {
        Connection connection = null;
        try {
            connection = DatabaseConnect.getConnection();
            String sql = "SELECT COUNT(*) FROM nhanvien WHERE sdtNV = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, sdtNV);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi kiểm tra số điện thoại: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DatabaseConnect.closeConnection(connection);
            }
        }
        return false;
    }

    private ArrayList<StaffModel> searchByField(String fieldName, String keyword) {
        ArrayList<StaffModel> staffList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DatabaseConnect.getConnection();
            String sql = "SELECT * FROM nhanvien WHERE " + fieldName + " LIKE ? ORDER BY LENGTH(maNV), maNV";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, "%" + keyword + "%");

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        staffList.add(mapResultSetToStaff(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm theo " + fieldName + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DatabaseConnect.closeConnection(connection);
            }
        }
        return staffList;
    }

    private StaffModel mapResultSetToStaff(ResultSet rs) throws SQLException {
        StaffModel staff = new StaffModel();
        staff.setMaNV(rs.getString("maNV"));
        staff.setTenNV(rs.getString("tenNV"));
        // staff.setLuongNV(rs.getString("luongNV"));
        staff.setLuongNV(rs.getLong("luongNV")); 
        staff.setSdtNV(rs.getString("sdtNV"));
        staff.setChucVu(rs.getInt("chucVu"));
        staff.setTenDangNhap(rs.getString("tenDangNhap"));
        staff.setMatKhau(rs.getString("matKhau"));
        return staff;
    }

}