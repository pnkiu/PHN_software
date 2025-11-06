package dao;

import model.CustomerModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Lấy mã khách hàng tiếp theo (tự động sinh theo định dạng KH001, KH002,...)
     */
    public String getNextMaKH() throws SQLException {
        String sql = "SELECT maKH FROM khachhang ORDER BY maKH DESC LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastMaKH = rs.getString("maKH");
                if (lastMaKH != null && lastMaKH.matches("^KH\\d+$")) {
                    try {
                        int lastNumber = Integer.parseInt(lastMaKH.substring(2));
                        int nextNumber = lastNumber + 1;
                        return "KH" + String.format("%03d", nextNumber);
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi khi phân tích mã KH: " + e.getMessage());
                    }
                }
            }
        }
        return "KH001";
    }

    // Lấy tất cả khách hàng
    public List<CustomerModel> getAllCustomers() throws SQLException {
        List<CustomerModel> customers = new ArrayList<>();
        String sql = "SELECT * FROM khachhang ORDER BY maKH ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CustomerModel customer = new CustomerModel();
                customer.setMaKH(rs.getString("maKH"));
                customer.setTenKH(rs.getString("tenKH"));
                customer.setDckH(rs.getString("dckH"));
                customer.setSdtKH(rs.getString("sdtKH"));
                customer.setTongChiTieu(rs.getLong("tongChiTieu"));
                customer.setSoLanMua(rs.getInt("soLanMua"));
                customers.add(customer);
            }
        }
        return customers;
    }

    // Lấy khách hàng theo mã
    public CustomerModel getCustomerByMaKH(String maKH) throws SQLException {
        String sql = "SELECT * FROM khachhang WHERE maKH = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, maKH);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CustomerModel customer = new CustomerModel();
                    customer.setMaKH(rs.getString("maKH"));
                    customer.setTenKH(rs.getString("tenKH"));
                    customer.setDckH(rs.getString("dckH"));
                    customer.setSdtKH(rs.getString("sdtKH"));
                    customer.setTongChiTieu(rs.getLong("tongChiTieu"));
                    customer.setSoLanMua(rs.getInt("soLanMua"));
                    return customer;
                }
            }
        }
        return null;
    }

    // Thêm khách hàng mới với mã tự động
    public boolean addCustomerWithAutoMaKH(CustomerModel customer) throws SQLException {
        String nextMaKH = getNextMaKH();
        customer.setMaKH(nextMaKH);

        String sql = "INSERT INTO khachhang (maKH, tenKH, dckH, sdtKH, tongChiTieu, soLanMua) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getMaKH());
            stmt.setString(2, customer.getTenKH());
            stmt.setString(3, customer.getDckH());
            stmt.setString(4, customer.getSdtKH());
            stmt.setLong(5, customer.getTongChiTieu());
            stmt.setInt(6, customer.getSoLanMua());

            return stmt.executeUpdate() > 0;
        }
    }

    // Thêm khách hàng với mã cụ thể
    public boolean addCustomer(CustomerModel customer) throws SQLException {
        String sql = "INSERT INTO khachhang (maKH, tenKH, dckH, sdtKH, tongChiTieu, soLanMua) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getMaKH());
            stmt.setString(2, customer.getTenKH());
            stmt.setString(3, customer.getDckH());
            stmt.setString(4, customer.getSdtKH());
            stmt.setLong(5, customer.getTongChiTieu());
            stmt.setInt(6, customer.getSoLanMua());

            return stmt.executeUpdate() > 0;
        }
    }

    // Cập nhật khách hàng
    public boolean updateCustomer(CustomerModel customer) throws SQLException {
        String sql = "UPDATE khachhang SET tenKH = ?, dckH = ?, sdtKH = ?, tongChiTieu = ?, soLanMua = ? WHERE maKH = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getTenKH());
            stmt.setString(2, customer.getDckH());
            stmt.setString(3, customer.getSdtKH());
            stmt.setLong(4, customer.getTongChiTieu());
            stmt.setInt(5, customer.getSoLanMua());
            stmt.setString(6, customer.getMaKH());

            return stmt.executeUpdate() > 0;
        }
    }

    // XÓA KHÁCH HÀNG - PHIÊN BẢN ĐƠN GIẢN VÀ HIỆU QUẢ
    public boolean deleteCustomer(String maKH) throws SQLException {
        // Debug thông tin
        System.out.println("DAO: Attempting to delete customer with maKH: " + maKH);

        String sql = "DELETE FROM khachhang WHERE maKH = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, maKH);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("DAO: Rows affected: " + rowsAffected);

            return rowsAffected > 0;
        }
    }

    // Kiểm tra xem khách hàng có tồn tại không
    public boolean isCustomerExists(String maKH) throws SQLException {
        String sql = "SELECT COUNT(*) FROM khachhang WHERE maKH = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, maKH);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Kiểm tra ràng buộc khóa ngoại (nếu có bảng nào tham chiếu đến khachhang)
    public boolean hasForeignConstraints(String maKH) throws SQLException {
        // Kiểm tra các bảng có thể tham chiếu đến khachhang
        // Ví dụ: nếu có bảng hoadon, donhang, etc.
        String[] tablesToCheck = {"hoadon", "donhang", "phieuthutien"}; // Thêm các bảng cần kiểm tra

        for (String table : tablesToCheck) {
            try {
                String sql = "SELECT COUNT(*) FROM " + table + " WHERE maKH = ?";
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, maKH);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            System.out.println("Foreign constraint found in table: " + table);
                            return true;
                        }
                    }
                }
            } catch (SQLException e) {
                // Bảng không tồn tại, bỏ qua
                System.out.println("Table " + table + " doesn't exist or cannot be checked");
            }
        }
        return false;
    }

    // Xóa khách hàng với kiểm tra ràng buộc
    public String deleteCustomerWithCheck(String maKH) throws SQLException {
        System.out.println("Checking constraints for: " + maKH);

        // Kiểm tra khách hàng có tồn tại không
        if (!isCustomerExists(maKH)) {
            return "Không tìm thấy khách hàng với mã: " + maKH;
        }

        // Kiểm tra ràng buộc khóa ngoại
        if (hasForeignConstraints(maKH)) {
            return "Không thể xóa khách hàng vì có dữ liệu liên quan (hóa đơn, đơn hàng, v.v.)";
        }

        // Thực hiện xóa
        boolean success = deleteCustomer(maKH);
        if (success) {
            return "Xóa khách hàng thành công";
        } else {
            return "Xóa khách hàng thất bại";
        }
    }

    // Tìm kiếm theo tất cả trường
    public List<CustomerModel> searchAllFields(String keyword) throws SQLException {
        List<CustomerModel> customers = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE maKH LIKE ? OR tenKH LIKE ? OR dckH LIKE ? OR sdtKH LIKE ? ORDER BY maKH ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CustomerModel customer = new CustomerModel();
                    customer.setMaKH(rs.getString("maKH"));
                    customer.setTenKH(rs.getString("tenKH"));
                    customer.setDckH(rs.getString("dckH"));
                    customer.setSdtKH(rs.getString("sdtKH"));
                    customer.setTongChiTieu(rs.getLong("tongChiTieu"));
                    customer.setSoLanMua(rs.getInt("soLanMua"));
                    customers.add(customer);
                }
            }
        }
        return customers;
    }

    // Tìm kiếm theo mã KH
    public List<CustomerModel> searchByMaKH(String keyword) throws SQLException {
        List<CustomerModel> customers = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE maKH LIKE ? ORDER BY maKH ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CustomerModel customer = new CustomerModel();
                    customer.setMaKH(rs.getString("maKH"));
                    customer.setTenKH(rs.getString("tenKH"));
                    customer.setDckH(rs.getString("dckH"));
                    customer.setSdtKH(rs.getString("sdtKH"));
                    customer.setTongChiTieu(rs.getLong("tongChiTieu"));
                    customer.setSoLanMua(rs.getInt("soLanMua"));
                    customers.add(customer);
                }
            }
        }
        return customers;
    }

    // Tìm kiếm theo tên KH
    public List<CustomerModel> searchByTenKH(String keyword) throws SQLException {
        List<CustomerModel> customers = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE tenKH LIKE ? ORDER BY maKH ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CustomerModel customer = new CustomerModel();
                    customer.setMaKH(rs.getString("maKH"));
                    customer.setTenKH(rs.getString("tenKH"));
                    customer.setDckH(rs.getString("dckH"));
                    customer.setSdtKH(rs.getString("sdtKH"));
                    customer.setTongChiTieu(rs.getLong("tongChiTieu"));
                    customer.setSoLanMua(rs.getInt("soLanMua"));
                    customers.add(customer);
                }
            }
        }
        return customers;
    }

    // Tìm kiếm theo số điện thoại
    public List<CustomerModel> searchBySDT(String keyword) throws SQLException {
        List<CustomerModel> customers = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE sdtKH LIKE ? ORDER BY maKH ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CustomerModel customer = new CustomerModel();
                    customer.setMaKH(rs.getString("maKH"));
                    customer.setTenKH(rs.getString("tenKH"));
                    customer.setDckH(rs.getString("dckH"));
                    customer.setSdtKH(rs.getString("sdtKH"));
                    customer.setTongChiTieu(rs.getLong("tongChiTieu"));
                    customer.setSoLanMua(rs.getInt("soLanMua"));
                    customers.add(customer);
                }
            }
        }
        return customers;
    }

    // Tìm kiếm theo địa chỉ
    public List<CustomerModel> searchByDiaChi(String keyword) throws SQLException {
        List<CustomerModel> customers = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE dckH LIKE ? ORDER BY maKH ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CustomerModel customer = new CustomerModel();
                    customer.setMaKH(rs.getString("maKH"));
                    customer.setTenKH(rs.getString("tenKH"));
                    customer.setDckH(rs.getString("dckH"));
                    customer.setSdtKH(rs.getString("sdtKH"));
                    customer.setTongChiTieu(rs.getLong("tongChiTieu"));
                    customer.setSoLanMua(rs.getInt("soLanMua"));
                    customers.add(customer);
                }
            }
        }
        return customers;
    }

    // Kiểm tra số điện thoại đã tồn tại chưa (trừ mã KH hiện tại)
    public boolean isPhoneNumberExists(String sdtKH, String excludeMaKH) throws SQLException {
        String sql = "SELECT COUNT(*) FROM khachhang WHERE sdtKH = ? AND maKH != ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sdtKH);
            stmt.setString(2, excludeMaKH);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Kiểm tra số điện thoại đã tồn tại chưa (cho thêm mới)
    public boolean isPhoneNumberExists(String sdtKH) throws SQLException {
        String sql = "SELECT COUNT(*) FROM khachhang WHERE sdtKH = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sdtKH);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Kiểm tra mã khách hàng đã tồn tại chưa
    public boolean isMaKHExists(String maKH) throws SQLException {
        String sql = "SELECT COUNT(*) FROM khachhang WHERE maKH = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, maKH);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}