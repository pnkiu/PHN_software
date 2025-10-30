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

    // Lấy tất cả khách hàng
    public List<CustomerModel> getAllCustomers() throws SQLException {
        List<CustomerModel> customers = new ArrayList<>();
        String sql = "SELECT * FROM khachhang ORDER BY maKH ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CustomerModel customer = new CustomerModel();
                customer.setMaKH(rs.getInt("maKH"));
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
    public CustomerModel getCustomerByMaKH(int maKH) throws SQLException {
        String sql = "SELECT * FROM khachhang WHERE maKH = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maKH);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CustomerModel customer = new CustomerModel();
                    customer.setMaKH(rs.getInt("maKH"));
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

    // Thêm khách hàng mới
    public boolean addCustomer(CustomerModel customer) throws SQLException {
        String sql = "INSERT INTO khachhang (tenKH, dckH, sdtKH, tongChiTieu, soLanMua) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getTenKH());
            stmt.setString(2, customer.getDckH());
            stmt.setString(3, customer.getSdtKH());
            stmt.setLong(4, customer.getTongChiTieu());
            stmt.setInt(5, customer.getSoLanMua());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // Lấy mã KH tự động tạo
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customer.setMaKH(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
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
            stmt.setInt(6, customer.getMaKH());

            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa khách hàng
    public boolean deleteCustomer(int maKH) throws SQLException {
        String sql = "DELETE FROM khachhang WHERE maKH = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, maKH);
            return stmt.executeUpdate() > 0;
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
                    customer.setMaKH(rs.getInt("maKH"));
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
                    customer.setMaKH(rs.getInt("maKH"));
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
                    customer.setMaKH(rs.getInt("maKH"));
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
                    customer.setMaKH(rs.getInt("maKH"));
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
                    customer.setMaKH(rs.getInt("maKH"));
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
    public boolean isPhoneNumberExists(String sdtKH, int excludeMaKH) throws SQLException {
        String sql = "SELECT COUNT(*) FROM khachhang WHERE sdtKH = ? AND maKH != ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sdtKH);
            stmt.setInt(2, excludeMaKH);

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
}